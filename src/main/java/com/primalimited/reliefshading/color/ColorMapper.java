package com.primalimited.reliefshading.color;

import com.primalimited.reliefshading.bounds.Bounds;

/**
 * Maps a generic-type value to a (rgb) color.
 *
 * @param <T> the generic-type value to map
 */
public interface ColorMapper<T> {
    /**
     * Returns the rgb value for the given value.
     *
     * @param value the query value.
     * @return the rgb value for the given value.
     */
    int rgb(T value);

    /**
     * Creates a color mapper for numeric values.
     *
     * @param colorPalette color palette (provides the colors)
     * @param bounds the bounding values for the color palette
     * @return new instance of a numeric-value color mapper for the given palette and bounds.
     */
    static ColorMapper<Number> numeric(ColorPalette colorPalette, Bounds bounds) {
        return new ColorMapperNumeric(colorPalette, bounds);
    }
}
