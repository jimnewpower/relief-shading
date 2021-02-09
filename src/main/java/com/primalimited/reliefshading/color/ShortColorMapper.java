package com.primalimited.reliefshading.color;

import com.primalimited.reliefshading.bounds.Bounds;

import java.util.Objects;

class ShortColorMapper implements ColorMapper<Short> {
    static ColorMapper<Short> create(Bounds bounds) {
        return new ShortColorMapper(bounds);
    }

    private transient final Bounds bounds;

    ShortColorMapper(Bounds bounds) {
        this.bounds = Objects.requireNonNull(bounds, "bounds");
        if (!bounds.isValid())
            throw new IllegalArgumentException("Bounds (" + bounds.toString() + ") must be valid.");
    }

    @Override
    public int rgb(Short value) {
        return 0;
    }
}
