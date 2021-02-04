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
    }
}
