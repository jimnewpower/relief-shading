package com.primalimited.reliefshading.image;

import com.primalimited.reliefshading.color.ColorPalette;
import com.primalimited.reliefshading.grid.Grid;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class GridClassifierColor implements GridClassifier {
    private static final int IMAGE_TYPE_DEFAULT = BufferedImage.TYPE_INT_ARGB;

    public static GridClassifier of(ColorPalette colorPalette) {
        return new GridClassifierColor(colorPalette);
    }

    private final transient ColorPalette colorPalette;

    private GridClassifierColor(ColorPalette colorPalette) {
        this.colorPalette = Objects.requireNonNull(colorPalette, "color palette");
    }

    @Override
    public BufferedImage classify(Grid grid) {
        BufferedImage image = new BufferedImage(grid.columns(), grid.rows(), IMAGE_TYPE_DEFAULT);
        return image;
    }
}
