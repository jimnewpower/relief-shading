package com.primalimited.reliefshading.image;

import com.primalimited.reliefshading.grid.Grid;

import java.awt.image.BufferedImage;

/**
 * Classify grid z values into e.g. colors or shaded relief values.
 */
public interface GridClassifier {
    /**
     * Classify the given grid into an image (raster).
     *
     * @param grid the grid input.
     * @return the classified grid as an image.
     */
    BufferedImage classify(Grid grid);
}
