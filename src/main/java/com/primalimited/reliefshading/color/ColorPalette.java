package com.primalimited.reliefshading.color;

/**
 * Essentially an array of color values.
 */
public interface ColorPalette {
    /**
     * Create a single-color palette.
     *
     * @param rgb rgb value for the single color.
     * @return instance of a ColorPalette with one color.
     */
    static ColorPalette solid(int rgb) {
        return new ColorPaletteImpl(1, new ControlPoint(0, rgb));
    }

    /**
     * Create a color palette given a set of control points.
     *
     * @param nColors number of colors in the palette
     * @param controlPoints control points to use as endpoints for computing gradients
     * @return instance of a color palette
     */
    static ColorPalette create(int nColors, ControlPoint...controlPoints) {
        return new ColorPaletteImpl(nColors, controlPoints);
    }

    /**
     * Apply transparency to a color palette.
     *
     * @param colorPalette input color palette
     * @param opacityPercent opacity percent
     * @return clone of the input color palette, with transparent colors.
     */
    static ColorPalette applyTransparency(ColorPalette colorPalette, int opacityPercent) {
        return new ColorPaletteTransparent(colorPalette, opacityPercent);
    }

    /**
     * Return the number of colors in the palette.
     *
     * @return the number of colors in the palette.
     */
    int nColors();

    /**
     * Return the rgb value at the specified index.
     *
     * @param index index into the internal array (must be [0..nColors()-1]
     * @return the rgb value at the specified index.
     */
    int rgb(int index);
}
