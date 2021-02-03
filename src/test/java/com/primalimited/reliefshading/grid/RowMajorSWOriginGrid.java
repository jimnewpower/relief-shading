package com.primalimited.reliefshading.grid;

import com.primalimited.reliefshading.bounds.Bounds;
import com.primalimited.reliefshading.bounds.Bounds2D;

import java.util.Objects;

class RowMajorSWOriginGrid<T> implements Grid<T> {
    private final int rows;
    private final int columns;
    private final Bounds2D bounds;
    private final T[] values;

    RowMajorSWOriginGrid(int rows, int columns, Bounds2D bounds, T[] values) {
        if (rows <= 0)
            throw new IllegalArgumentException("Rows must be > 0.");
        if (columns <= 0)
            throw new IllegalArgumentException("Columns must be > 0.");
        if (values == null)
            throw new IllegalArgumentException("Values invalid.");
        if (values.length != rows * columns)
            throw new IllegalArgumentException("Values length must be equal to (rows x columns).");

        this.rows = rows;
        this.columns = columns;
        this.bounds = Objects.requireNonNull(bounds, "bounds");
        this.values = Objects.requireNonNull(values, "values");
    }

    @Override
    public int rows() {
        return rows;
    }

    @Override
    public int columns() {
        return columns;
    }

    @Override
    public Bounds2D bounds() {
        return bounds;
    }

    @Override
    public int index(int row, int column) {
        if (row < 0 || row >= rows())
            throw new IllegalArgumentException(
                    "Invalid row: " + row + ". Must be within " + Bounds.of(0, rows()).format()
            );

        if (column < 0 || column >= columns())
            throw new IllegalArgumentException(
                    "Invalid column: " + column + ". Must be within " + Bounds.of(0, columns()).format()
            );

        return (row * columns) + column;
    }

    @Override
    public T value(int row, int column) {
        if (row < 0 || row >= rows())
            throw new IllegalArgumentException(
                    "Invalid row: " + row + ". Must be within " + Bounds.of(0, rows()).format()
            );

        if (column < 0 || column >= columns())
            throw new IllegalArgumentException(
                    "Invalid column: " + column + ". Must be within " + Bounds.of(0, columns()).format()
            );

        return values[index(row, column)];
    }
}
