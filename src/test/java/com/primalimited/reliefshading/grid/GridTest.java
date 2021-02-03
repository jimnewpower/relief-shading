package com.primalimited.reliefshading.grid;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.primalimited.reliefshading.bounds.Bounds;
import com.primalimited.reliefshading.bounds.Bounds2D;
import org.junit.jupiter.api.Test;

public class GridTest {
    @Test
    public void rowsAndColumns() {
        int rows = 21;
        int columns = 34;
        Grid<Double> grid = new RowMajorSWOriginGrid<Double>(
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
        Grid<Double> grid = new RowMajorSWOriginGrid<Double>(
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

        Grid<Double> grid = new RowMajorSWOriginGrid<Double>(
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
    }
}
