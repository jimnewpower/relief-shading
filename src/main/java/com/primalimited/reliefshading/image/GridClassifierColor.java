package com.primalimited.reliefshading.image;

import com.primalimited.reliefshading.bounds.Bounds;
import com.primalimited.reliefshading.color.ColorMapper;
import com.primalimited.reliefshading.color.ColorPalette;
import com.primalimited.reliefshading.grid.Grid;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Objects;

/**
 * Classify a grid based on a color palette.
 */
public class GridClassifierColor implements GridClassifier {
    private static final int IMAGE_TYPE_DEFAULT = BufferedImage.TYPE_INT_ARGB;
    private static final int TRANSPARENT = new Color(255, 255, 255, 0).getRGB();

    public static GridClassifier of(ColorPalette colorPalette) {
        return new GridClassifierColor(colorPalette);
    }

    public static GridClassifier with(ColorPalette colorPalette, Bounds zBounds) {
        return new GridClassifierColor(colorPalette);
    }

    private transient final ColorPalette colorPalette;
    private transient Bounds zBounds = Bounds.nullBounds();

    private GridClassifierColor(ColorPalette colorPalette) {
        this.colorPalette = Objects.requireNonNull(colorPalette, "color palette");
    }

    private GridClassifierColor(ColorPalette colorPalette, Bounds zBounds) {
        this.colorPalette = Objects.requireNonNull(colorPalette, "color palette");
        this.zBounds = Objects.requireNonNull(zBounds, "z bounds");
    }

    @Override
    public BufferedImage classify(Grid grid) {
        Objects.requireNonNull(grid, "grid");

        if (zBounds.isNull() || !zBounds.isValid())
            zBounds = grid.zBounds();

        ColorMapper<Number> colorMapper = ColorMapper.numeric(colorPalette, zBounds);

        BufferedImage image = new BufferedImage(grid.columns(), grid.rows(), IMAGE_TYPE_DEFAULT);
        int[] rgbValues = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
        int size = grid.rows() * grid.columns();

        for (int index = 0; index < size; index++) {
            rgbValues[index] = TRANSPARENT;

            int row = grid.rowFromCellIndex(index);
            int column = grid.columnFromCellIndex(index);
            Number value = grid.value(row, column);
            if (zBounds.contains(value.doubleValue()))
                rgbValues[index] = colorMapper.rgb(value);
        }

        return grid.isOriginNorth()
                ? image
                : ImageUtility.flipImageVertically(image);
    }
}
