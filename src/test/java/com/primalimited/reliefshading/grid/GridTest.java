package com.primalimited.reliefshading.grid;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.primalimited.reliefshading.bounds.Bounds;
import com.primalimited.reliefshading.bounds.Bounds2D;
import com.primalimited.reliefshading.number.Invalid;
import org.junit.jupiter.api.Test;

public class GridTest {
    @Test
    public void factoryMethodInvalidRows() {
        // rows < 0
        assertThrows(IllegalArgumentException.class,
                () -> Grid.createRowMajorSWOrigin(-1, 10, mockBounds(), mockValues(2, 2)));
        // rows invalid
        assertThrows(IllegalArgumentException.class,
                () -> Grid.createRowMajorSWOrigin(Invalid.INVALID_INT, 10, mockBounds(), mockValues(2, 2)));
    }

    @Test
    public void factoryMethodInvalidColumns() {
        // columns < 0
        assertThrows(IllegalArgumentException.class,
                () -> Grid.createRowMajorSWOrigin(10, -1, mockBounds(), mockValues(2, 2)));
        // columns invalid
        assertThrows(IllegalArgumentException.class,
                () -> Grid.createRowMajorSWOrigin(10, Invalid.INVALID_INT, mockBounds(), mockValues(2, 2)));
    }

    @Test
    public void factoryMethodInvalidBounds() {
        // null bounds
        assertThrows(IllegalArgumentException.class,
                () -> Grid.createRowMajorSWOrigin(10, 10, null, mockValues(10, 10)));

        // invalid bounds
        assertThrows(IllegalArgumentException.class,
                () -> Grid.createRowMajorSWOrigin(10, 10, Bounds2D.empty(), mockValues(10, 10)));
    }

    @Test
    public void factoryMethodInvalidValues() {
        // null values
        assertThrows(IllegalArgumentException.class,
                () -> Grid.createRowMajorSWOrigin(10, 10, mockBounds(), null));

        // values with wrong length
        assertThrows(IllegalArgumentException.class,
                () -> Grid.createRowMajorSWOrigin(10, 10, mockBounds(), mockValues(3, 4)));
    }

    @Test
    public void rowsAndColumns() {
        int rows = 21;
        int columns = 34;
        Grid<Double> grid = Grid.createRowMajorSWOrigin(
                rows,
                columns,
                Bounds2D.create(Bounds.FRACTION, Bounds.FRACTION),
                new Double[rows*columns]
        );
        assertEquals(rows, grid.rows(), "n rows");
        assertEquals(columns, grid.columns(), "n columns");
    }

    @Test
    public void bounds() {
        int rows = 21;
        int columns = 34;
        Grid<Double> grid = Grid.createRowMajorSWOrigin(
                rows,
                columns,
                Bounds2D.create(Bounds.FRACTION, Bounds.FRACTION),
                new Double[rows*columns]
        );
        final double tolerance = 1e-10;
        assertEquals(0, grid.bounds().minX(), tolerance);
        assertEquals(1, grid.bounds().maxX(), tolerance);
        assertEquals(0, grid.bounds().minY(), tolerance);
        assertEquals(1, grid.bounds().maxY(), tolerance);
    }

    @Test
    public void value() {
        int rows = 2;
        int columns = 3;

        // create grid values where value == index
        Double[] values = new Double[rows*columns];
        for (int index = 0; index < values.length; index++)
            values[index] = (double) index;

        Grid<Double> grid = Grid.createRowMajorSWOrigin(
                rows,
                columns,
                Bounds2D.create(Bounds.FRACTION, Bounds.FRACTION),
                values
        );

        final double tolerance = 1e-10;

        int row = 0;
        int col = 0;

        assertEquals(0, grid.value(row, col++), tolerance);
        assertEquals(1, grid.value(row, col++), tolerance);
        assertEquals(2, grid.value(row, col++), tolerance);

        row++;
        col = 0;

        assertEquals(3, grid.value(row, col++), tolerance);
        assertEquals(4, grid.value(row, col++), tolerance);
        assertEquals(5, grid.value(row, col++), tolerance);

        // invalid arguments
        assertThrows(IllegalArgumentException.class,
                () -> grid.value(-1, 1));
        assertThrows(IllegalArgumentException.class,
                () -> grid.value(99000, 1));
        assertThrows(IllegalArgumentException.class,
                () -> grid.value(Invalid.INVALID_INT, 1));
        assertThrows(IllegalArgumentException.class,
                () -> grid.value(1, -1));
        assertThrows(IllegalArgumentException.class,
                () -> grid.value(1, 99000));
        assertThrows(IllegalArgumentException.class,
                () -> grid.value(1, Invalid.INVALID_INT));
    }

    @Test
    public void indexFromRowColumn() {
        Grid grid = mock();

        // invalid row, valid column
        assertThrows(IllegalArgumentException.class,
                () -> grid.index(-1, 0));
        assertThrows(IllegalArgumentException.class,
                () -> grid.index(50000, 0));
        assertThrows(IllegalArgumentException.class,
                () -> grid.index(Invalid.INVALID_INT, 0));

        // invalid column, valid row
        assertThrows(IllegalArgumentException.class,
                () -> grid.index(0, -1));
        assertThrows(IllegalArgumentException.class,
                () -> grid.index(0, 50000));
        assertThrows(IllegalArgumentException.class,
                () -> grid.index(0, Invalid.INVALID_INT));

        // both arguments invalid
        assertThrows(IllegalArgumentException.class,
                () -> grid.index(-1, -1));
        assertThrows(IllegalArgumentException.class,
                () -> grid.index(50000, 50000));
        assertThrows(IllegalArgumentException.class,
                () -> grid.index(Invalid.INVALID_INT, Invalid.INVALID_INT));
    }

    @Test
    public void rowColumnFromCellIndex() {
        Grid grid = mock();

        // valid rows
        assertEquals(0, grid.rowFromCellIndex(0));
        assertEquals(1, grid.rowFromCellIndex(grid.columns()));
        assertEquals(2, 2 * grid.rowFromCellIndex(grid.columns()));
        assertEquals(3, 3 * grid.rowFromCellIndex(grid.columns()));
        assertEquals(4, 4 * grid.rowFromCellIndex(grid.columns()));

        // valid columns
        assertEquals(0, grid.columnFromCellIndex(0));
        assertEquals(1, grid.columnFromCellIndex(1));
        assertEquals(2, grid.columnFromCellIndex(2));
        assertEquals(3, grid.columnFromCellIndex(3));
        assertEquals(8, grid.columnFromCellIndex(8));
        assertEquals(9, grid.columnFromCellIndex(9));
        assertEquals(0, grid.columnFromCellIndex(grid.columns()));
        assertEquals(2, grid.columnFromCellIndex(grid.columns() + 2));
        assertEquals(9, grid.columnFromCellIndex((grid.rows() * grid.columns())-1));

        // invalid indexes
        assertEquals(-1, grid.rowFromCellIndex(-1122));
        assertEquals(-1, grid.rowFromCellIndex(grid.rows() * grid.columns()));
        assertEquals(-1, grid.columnFromCellIndex(-1122));
        assertEquals(-1, grid.columnFromCellIndex(grid.rows() * grid.columns()));

    }

    @Test
    public void indexFromLocation() {
        int rows = 5;
        int columns = 10;

        // create grid values where value == index
        Double[] values = new Double[rows*columns];
        for (int index = 0; index < values.length; index++)
            values[index] = (double) index;

        Grid<Double> grid = Grid.createRowMajorSWOrigin(
                rows,
                columns,
                Bounds2D.create(Bounds.PERCENT, Bounds.PERCENT),
                values
        );

        double y = 0.5;
        assertEquals(0, grid.index(0.0, y));
        assertEquals(9, grid.index(100, y));

        assertEquals(0, grid.index(0.001, y));
        assertEquals(0, grid.index(9.998, y));
        assertEquals(1, grid.index(10.001, y));
        assertEquals(1, grid.index(19.998, y));
        assertEquals(2, grid.index(20.5, y));
        assertEquals(3, grid.index(30.5, y));
        assertEquals(4, grid.index(40.5, y));
        assertEquals(5, grid.index(50.5, y));
        assertEquals(6, grid.index(60.5, y));
        assertEquals(7, grid.index(70.5, y));
        assertEquals(8, grid.index(80.5, y));
        assertEquals(9, grid.index(90, y));
        assertEquals(9, grid.index(100, y));

        // invalid args
        assertEquals(-1, grid.index(-42.6, 50));
        assertEquals(-1, grid.index(50, -42.6));
        assertEquals(-1, grid.index(Invalid.INVALID_DOUBLE, 50));
        assertEquals(-1, grid.index(50, Invalid.INVALID_DOUBLE));
    }

    private Grid mock() {
        int rows = 5;
        int columns = 10;

        // create grid values where value == index
        Double[] values = mockValues(rows, columns);

        Grid<Double> grid = Grid.createRowMajorSWOrigin(
                rows,
                columns,
                mockBounds(),
                values
        );

        return grid;
    }

    private Bounds2D mockBounds() {
        return Bounds2D.create(Bounds.PERCENT, Bounds.PERCENT);
    }

    private Double[] mockValues(int rows, int columns) {
        Double[] values = new Double[rows*columns];
        for (int index = 0; index < values.length; index++)
            values[index] = (double) index;
        return values;
    }
}
