package com.primalimited.reliefshading.color;

import com.primalimited.reliefshading.bounds.Bounds;
import com.primalimited.reliefshading.number.Invalid;

import java.util.Objects;

class ShortColorMapper implements ColorMapper<Short> {
    static ColorMapper<Short> create(ColorPalette colorPalette, Bounds bounds) {
        return new ShortColorMapper(colorPalette, bounds);
    }

    private transient final ColorPalette colorPalette;
    private transient final Bounds bounds;

    ShortColorMapper(ColorPalette colorPalette, Bounds bounds) {
        this.colorPalette = Objects.requireNonNull(colorPalette, "color palette");
        this.bounds = Objects.requireNonNull(bounds, "bounds");
        if (!bounds.isValid())
            throw new IllegalArgumentException("Bounds (" + bounds.toString() + ") must be valid.");
    }

    @Override
    public int rgb(Short value) {
        int bin = bounds.histogramBin(value.doubleValue(), colorPalette.nColors());
        return bin == -1
            ? Invalid.INVALID_INT
                : colorPalette.rgb(bin);
    }
}
