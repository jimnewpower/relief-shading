package com.primalimited.reliefshading.image;

import com.primalimited.reliefshading.grid.Grid;

import java.awt.image.BufferedImage;

/**
 * Classify grid z values into e.g. colors or shaded relief values.
 */
public interface GridClassifier {
    BufferedImage classify(Grid grid);
}
