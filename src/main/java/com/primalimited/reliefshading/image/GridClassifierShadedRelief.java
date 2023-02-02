package com.primalimited.reliefshading.image;

import com.primalimited.reliefshading.algorithm.ReliefShader;
import com.primalimited.reliefshading.algorithm.ZFactorDem;
import com.primalimited.reliefshading.bounds.Bounds;
import com.primalimited.reliefshading.color.ColorMapper;
import com.primalimited.reliefshading.color.ColorPalette;
import com.primalimited.reliefshading.color.ColorPaletteDefaults;
import com.primalimited.reliefshading.grid.Grid;
import com.primalimited.reliefshading.prefs.Preferences;
import com.primalimited.reliefshading.prefs.PreferencesBuilder;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Objects;

public class GridClassifierShadedRelief implements GridClassifier {
    private static final int IMAGE_TYPE_DEFAULT = BufferedImage.TYPE_INT_ARGB;
    private static final int TRANSPARENT = new Color(255, 255, 255, 0).getRGB();

    public static GridClassifier of(Preferences preferences, ZFactorDem zFactorDem) {
        return new GridClassifierShadedRelief(preferences, zFactorDem);
    }

    private final Preferences preferences;
    private final ZFactorDem zFactorDem;

    private ColorPalette colorPalette = ColorPaletteDefaults.RELIEF_SHADE_DYNAMIC.colorPalette();

    private GridClassifierShadedRelief(Preferences preferences, ZFactorDem zFactorDem) {
        this.preferences = Objects.requireNonNull(preferences, "preferences");
        this.zFactorDem = Objects.requireNonNull(zFactorDem, "z-factor");
    }

    /**
     * Classify the given grid into an image (raster).
     *
     * @param grid the grid input.
     * @return the classified grid as an image.
     */
    @Override
    public BufferedImage apply(Grid grid) {
        Objects.requireNonNull(grid, "grid");

        BufferedImage image = new BufferedImage(grid.columns(), grid.rows(), IMAGE_TYPE_DEFAULT);
        int[] rgbValues = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
        int size = grid.rows() * grid.columns();

        Preferences prefs = flipAzimuthForSouthOriginGrids(grid);
        ReliefShader reliefShader = ReliefShader.create(prefs);
        Grid shaded = reliefShader.apply(grid, zFactorDem.getZFactor());

        ColorMapper<Number> colorMapper = ColorMapper.numeric(colorPalette, Bounds.RGB_8_BIT);

        int opacityPercent = (int) Bounds.PERCENT.constrain(prefs.opacityPercent());
        int alpha8Bit = (int) Bounds.RGB_8_BIT.constrain(Math.round(opacityPercent * 2.55f));

        for (int index = 0; index < size; index++) {
            rgbValues[index] = TRANSPARENT;

            int row = shaded.rowFromCellIndex(index);
            int column = shaded.columnFromCellIndex(index);

            Number value = shaded.value(row, column);
            if (Bounds.RGB_8_BIT.contains(value.doubleValue())) {
                if (prefs.opacityPercent() < 100) {
                    Color color = colorMapper.color(value);
                    color = new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha8Bit);
                    rgbValues[index] = color.getRGB();
                } else {
                    rgbValues[index] = colorMapper.rgb(value);
                }
            }
        }

        return shaded.isOriginNorth()
                ? image
                : ImageUtility.flipImageVertically(image);
    }

    private Preferences flipAzimuthForSouthOriginGrids(Grid grid) {
        if (grid.isOriginNorth())
            return preferences;

        int azimuth = preferences.azimuthDegrees();
        int adjust = azimuth >= 180 ? -180 : 180;
        azimuth = (int) Bounds.DEGREES.constrain(azimuth + adjust);
        return new PreferencesBuilder()
                .azimuthDegrees(azimuth)
                .altitudeDegrees(preferences.altitudeDegrees())
                .opacityPercent(preferences.opacityPercent())
                .build();
    }
}
