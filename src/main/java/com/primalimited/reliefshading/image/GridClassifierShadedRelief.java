package com.primalimited.reliefshading.image;

import com.primalimited.reliefshading.algorithm.ReliefShader;
import com.primalimited.reliefshading.algorithm.ZFactorDem;
import com.primalimited.reliefshading.bounds.Bounds;
import com.primalimited.reliefshading.color.ColorMapper;
import com.primalimited.reliefshading.color.ColorPalette;
import com.primalimited.reliefshading.color.ColorPaletteDefaults;
import com.primalimited.reliefshading.grid.Grid;
import com.primalimited.reliefshading.prefs.Preferences;

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

    private transient final Preferences preferences;
    private transient final ZFactorDem zFactorDem;

    private GridClassifierShadedRelief(Preferences preferences, ZFactorDem zFactorDem) {
        this.preferences = Objects.requireNonNull(preferences, "preferences");
        this.zFactorDem = Objects.requireNonNull(zFactorDem, "z-factor");
    }

    @Override
    public BufferedImage classify(Grid grid) {
        Objects.requireNonNull(grid, "grid");

        BufferedImage image = new BufferedImage(grid.columns(), grid.rows(), IMAGE_TYPE_DEFAULT);
        int[] rgbValues = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
        int size = grid.rows() * grid.columns();

        ReliefShader reliefShader = ReliefShader.create(preferences);
        Grid shaded = reliefShader.apply(grid, zFactorDem.getZFactor());

        ColorPalette grayPalette = ColorPaletteDefaults.RELIEF_SHADE.colorPalette();
        ColorMapper<Number> colorMapper = ColorMapper.numeric(grayPalette, Bounds.RGB_8_BIT);

        for (int index = 0; index < size; index++) {
            rgbValues[index] = TRANSPARENT;

            int row = shaded.rowFromCellIndex(index);
            int column = shaded.columnFromCellIndex(index);

            Number value = shaded.value(row, column);
            if (Bounds.RGB_8_BIT.contains(value.doubleValue()))
                rgbValues[index] = colorMapper.rgb(value);
        }

        return shaded.isOriginNorth()
                ? image
                : ImageUtility.flipImageVertically(image);
    }
}
