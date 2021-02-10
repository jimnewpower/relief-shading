package com.primalimited.reliefshading.color;

/**
 * Essentially an array of color values.
 */
public interface ColorPalette {
    static ColorPalette solid(int rgb) {
        return new ColorPaletteImpl(1, new ControlPoint(0, rgb));
    }

    static ColorPalette create(int nColors, ControlPoint...controlPoints) {
        return new ColorPaletteImpl(nColors, controlPoints);
    }

    int nColors();
    int rgb(int index);
}
