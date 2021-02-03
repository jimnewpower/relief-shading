package com.primalimited.reliefshading.grid;

import com.primalimited.reliefshading.bounds.Bounds2D;

public interface Grid {
    /**
     * Return number of rows in the grid.
     * @return number of rows in the grid.
     */
    int rows();

    /**
     * Return number of columns in the grid.
     * @return number of columns in the grid.
     */
    int columns();

    /**
     * Return the spatial extents of the grid.
     * @return the spatial extents of the grid.
     */
    Bounds2D bounds();
}