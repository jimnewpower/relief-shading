package com.primalimited.reliefshading.grid;

import com.primalimited.reliefshading.bounds.Bounds;
import com.primalimited.reliefshading.bounds.Bounds2D;

public interface Grid<T> {
    static <T> Grid<T> createRowMajorSWOrigin(int rows, int columns, Bounds2D bounds, T[] values) {
        return new RowMajorSWOriginGrid<>(rows, columns, bounds, values);
    }

    static <T> Grid<T> createRowMajorSWOriginWithZBounds(
            int rows,
            int columns,
            Bounds2D bounds,
            Bounds zBounds,
            T[] values
    ) {
        return new RowMajorSWOriginGrid<>(rows, columns, bounds, zBounds, values);
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
     * Return the z bounds, if applicable.
     * @return the bounds for the grid z values.
     */
    Bounds zBounds();

    /**
     * Return the index into the grid array.
     *
     * @param row the row of the grid.
     * @param column the column of the grid.
     * @return the index into the grid array.
     */
    int index(int row, int column);

    /**
     * Compute row from cell (array) index.
     *
     * @param index index into values array
     * @return row computed from cell index, or -1 if index is out of range.
     */
    int rowFromCellIndex(int index);

    /**
     * Compute column from cell (array) index.
     *
     * @param index index into values array
     * @return column computed from cell index, or -1 of index is out of range.
     */
    int columnFromCellIndex(int index);

    /**
     * Return the index into the grid array for the x,y location.
     *
     * @param x the x location
     * @param y the y location
     * @return  the index into the grid array for the x,y location.
     */
    int index(double x, double y);

    /**
     * Return the value at the grid node represented by row, column.
     *
     * @param row the row of the grid.
     * @param column the column of the grid.
     * @return the value at the grid node represented by row, column.
     */
    T value(int row, int column);
}