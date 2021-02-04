package com.primalimited.reliefshading.grid;

import com.primalimited.reliefshading.bounds.Bounds;
import com.primalimited.reliefshading.bounds.Bounds2D;
import com.primalimited.reliefshading.number.Invalid;

import java.util.Objects;

class RowMajorSWOriginGrid<T> implements Grid<T> {
    public static final String MUST_BE_WITHIN = ". Must be within ";
    public static final String INVALID_ROW = "Invalid row: ";
    public static final String INVALID_COLUMN = "Invalid column: ";

    @SuppressWarnings("PMD.AvoidFieldNameMatchingMethodName")
    private transient final int rows;

    @SuppressWarnings("PMD.AvoidFieldNameMatchingMethodName")
    private transient final int columns;

    @SuppressWarnings("PMD.AvoidFieldNameMatchingMethodName")
    private transient final Bounds2D bounds;

    private transient final T[] values;

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
                    INVALID_ROW + row + MUST_BE_WITHIN + Bounds.of(0, rows()).format()
            );

        if (column < 0 || column >= columns())
            throw new IllegalArgumentException(
                    INVALID_COLUMN + column + MUST_BE_WITHIN + Bounds.of(0, columns()).format()
            );

        return (row * columns) + column;
    }

    @Override
    public int index(double x, double y) {
        int column = bounds.histogramBinX(x, columns());
        if (column < 0 || Invalid.intInstance().invalid(column))
            return -1;

        int row = bounds.histogramBinY(y, rows());
        if (row < 0 || Invalid.intInstance().invalid(row))
            return -1;

        return index(row, column);
    }

    @Override
    public T value(int row, int column) {
        if (row < 0 || row >= rows())
            throw new IllegalArgumentException(
                    INVALID_ROW + row + MUST_BE_WITHIN + Bounds.of(0, rows()).format()
            );

        if (column < 0 || column >= columns())
            throw new IllegalArgumentException(
                    INVALID_COLUMN + column + MUST_BE_WITHIN + Bounds.of(0, columns()).format()
            );

        return values[index(row, column)];
    }
}
