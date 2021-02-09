package com.primalimited.reliefshading.color;

import com.primalimited.reliefshading.bounds.Bounds;
import com.primalimited.reliefshading.number.Invalid;

import java.util.Objects;

class ColorMapperNumeric implements ColorMapper<Number> {

    private transient final ColorPalette colorPalette;
    private transient final Bounds bounds;

    ColorMapperNumeric(ColorPalette colorPalette, Bounds bounds) {
        this.colorPalette = Objects.requireNonNull(colorPalette, "color palette");
        this.bounds = Objects.requireNonNull(bounds, "bounds");
        if (!bounds.isValid())
            throw new IllegalArgumentException("Bounds (" + bounds.toString() + ") must be valid.");
    }

    @Override
    public int rgb(Number value) {
        int bin = bounds.histogramBin(value.doubleValue(), colorPalette.nColors());
        return bin == -1
                ? Invalid.INVALID_INT
                : colorPalette.rgb(bin);
    }
}
