package com.primalimited.reliefshading.grid;

import com.primalimited.reliefshading.bounds.Bounds2D;

public interface Grid {
    int rows();
    int columns();
    Bounds2D bounds();
}