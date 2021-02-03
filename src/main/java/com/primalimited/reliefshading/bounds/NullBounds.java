package com.primalimited.reliefshading.bounds;

import com.primalimited.reliefshading.number.Invalid;

final class NullBounds implements Bounds {
    public static Bounds create() {
        return new NullBounds();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " " + text();
    }

    @Override
    public double min() {
        return Invalid.INVALID_DOUBLE;
    }

    @Override
    public double max() {
        return Invalid.INVALID_DOUBLE;
    }

    @Override
    public double range() {
        return Invalid.INVALID_DOUBLE;
    }

    @Override
    public boolean isValid() {
        return false;
    }
}
