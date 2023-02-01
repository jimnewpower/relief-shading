package com.primalimited.reliefshading.image;

import com.primalimited.reliefshading.algorithm.ZFactorDem;
import com.primalimited.reliefshading.bounds.Bounds;
import com.primalimited.reliefshading.color.ColorMapper;
import com.primalimited.reliefshading.color.ColorPalette;
import com.primalimited.reliefshading.grid.Grid;
import com.primalimited.reliefshading.prefs.Preferences;

import java.awt.image.BufferedImage;

/**
 * Classify grid z values into e.g. colors or shaded relief values.
 */
@FunctionalInterface
public interface GridClassifier {
    /**
     * Classify the given grid into an image (raster).
     *
     * @param grid the grid input.
     * @return the classified grid as an image.
     */
    BufferedImage classify(Grid grid);

    static GridClassifier color(ColorPalette colorPalette) {
        return GridClassifierColor.of(colorPalette);
    }

    static GridClassifier color(ColorPalette colorPalette, Bounds zBounds) {
        return GridClassifierColor.with(colorPalette, zBounds);
    }

    static GridClassifier shaded(Preferences preferences, ZFactorDem zFactorDem) {
        return GridClassifierShadedRelief.of(preferences, zFactorDem);
    }
}
