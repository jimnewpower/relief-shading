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
        Grid grid = new RowMajorSWOriginGrid(rows, columns, Bounds2D.create(Bounds.FRACTION, Bounds.FRACTION));
        assertEquals(rows, grid.rows(), "n rows");
        assertEquals(columns, grid.columns(), "n columns");
    }

    @Test
    public void bounds() {
        int rows = 21;
        int columns = 34;
        Grid grid = new RowMajorSWOriginGrid(rows, columns, Bounds2D.create(Bounds.FRACTION, Bounds.FRACTION));
        final double tolerance = 1e-10;
        assertEquals(0, grid.bounds().minX(), tolerance);
        assertEquals(1, grid.bounds().maxX(), tolerance);
        assertEquals(0, grid.bounds().minY(), tolerance);
        assertEquals(1, grid.bounds().maxY(), tolerance);
    }
}
