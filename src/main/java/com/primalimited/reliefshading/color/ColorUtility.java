package com.primalimited.reliefshading.color;

import com.primalimited.reliefshading.bounds.Bounds;

public class ColorUtility {

    /**
     * Convert a percent value to an 8-bit value (e.g. 0 == 0,
     * 50 == 128, 100 == 255).
     *
     * @param percent percentage to convert
     * @return the percentage represented as an 8-bit value.
     */
    public static int percentTo8BitValue(int percent) {
        return (int) Bounds.UNSIGNED_BYTE_BOUNDS.constrain(
                Math.round(Bounds.PERCENT.constrain(percent) * 2.55f)
        );
    }
}
