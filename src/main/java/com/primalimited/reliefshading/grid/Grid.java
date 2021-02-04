package com.primalimited.reliefshading.grid;

import com.primalimited.reliefshading.bounds.Bounds2D;

public interface Grid<T> {
    static <T> Grid<T> createRowMajorSWOrigin(int rows, int columns, Bounds2D bounds, T[] values) {
        return new RowMajorSWOriginGrid<>(rows, columns, bounds, values);
    }

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

    /**
     * Return the index into the grid array.
     *
     * @param row the row of the grid.
     * @param column the column of the grid.
     * @return the index into the grid array.
     */
    int index(int row, int column);

    /**
     * Return the value at the grid node represented by row, column.
     *
     * @param row the row of the grid.
     * @param column the column of the grid.
     * @return the value at the grid node represented by row, column.
     */
    T value(int row, int column);
}