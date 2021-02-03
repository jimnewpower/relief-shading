package com.primalimited.reliefshading.grid;

import com.primalimited.reliefshading.bounds.Bounds2D;

import java.util.Objects;

class RowMajorSWOriginGrid implements Grid {
    private final int rows;
    private final int columns;
    private final Bounds2D bounds;

    RowMajorSWOriginGrid(int rows, int columns, Bounds2D bounds) {
        this.rows = rows;
        this.columns = columns;
        this.bounds = Objects.requireNonNull(bounds, "bounds");
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
}
