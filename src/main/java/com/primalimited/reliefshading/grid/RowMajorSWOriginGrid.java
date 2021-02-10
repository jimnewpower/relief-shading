package com.primalimited.reliefshading.grid;

import com.primalimited.reliefshading.bounds.Bounds;
import com.primalimited.reliefshading.bounds.Bounds2D;
import com.primalimited.reliefshading.number.Invalid;

import java.util.Objects;

@SuppressWarnings("PMD.AvoidFieldNameMatchingMethodName")
class RowMajorSWOriginGrid implements Grid {
    public static final String MUST_BE_WITHIN = ". Must be within ";
    public static final String INVALID_ROW = "Invalid row: ";
    public static final String INVALID_COLUMN = "Invalid column: ";

    private transient final int rows;
    private transient final int columns;
    private transient final Bounds2D bounds;
    private transient Bounds zBounds = Bounds.nullBounds();
    private transient final Number[] values;

    RowMajorSWOriginGrid(int rows, int columns, Bounds2D bounds, Bounds zBounds, Number[] values) {
        this(rows, columns, bounds, values);
        this.zBounds = Objects.requireNonNull(zBounds, "z bounds");
    }

    RowMajorSWOriginGrid(int rows, int columns, Bounds2D bounds, Number[] values) {
        if (rows <= 0)
            throw new IllegalArgumentException("Rows must be > 0.");
        if (Invalid.intInstance().invalid(rows))
            throw new IllegalArgumentException("Rows not specified.");

        if (columns <= 0)
            throw new IllegalArgumentException("Columns must be > 0.");
        if (Invalid.intInstance().invalid(columns))
            throw new IllegalArgumentException("Columns not specified.");

        if (bounds == null)
            throw new IllegalArgumentException("Bounds not specified.");
        if (!bounds.isValid())
            throw new IllegalArgumentException("Bounds invalid: " + bounds.toString());

        if (values == null)
            throw new IllegalArgumentException("Values not specified.");
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
    public Bounds zBounds() {
        return zBounds;
    }

    @Override
    public int index(int row, int column) {
        if (row < 0 || row >= rows())
            throw new IllegalArgumentException(
                    INVALID_ROW + row + MUST_BE_WITHIN + Bounds.of(0, rows()-1).format()
            );

        if (column < 0 || column >= columns())
            throw new IllegalArgumentException(
                    INVALID_COLUMN + column + MUST_BE_WITHIN + Bounds.of(0, columns()-1).format()
            );

        return (row * columns) + column;
    }

    @Override
    public int rowFromCellIndex(int index) {
        return index < 0
                ? -1
                : index >= rows() * columns()
                ? -1
                : index < columns()
                ? 0
                : index / columns();
    }

    @Override
    public int columnFromCellIndex(int index) {
        return index < 0
                ? -1
                : index >= rows() * columns()
                ? -1
                : index < columns()
                ? index
                : index % columns();
    }

    @Override
    public int index(double x, double y) {
        int column = bounds.histogramBinX(x, columns());
        if (column == -1)
            return -1;

        int row = bounds.histogramBinY(y, rows());
        if (row == -1)
            return -1;

        return index(row, column);
    }

    @Override
    public Number value(int row, int column) {
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
