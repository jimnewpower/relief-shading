package com.primalimited.reliefshading.color;

import com.primalimited.reliefshading.bounds.Bounds;

import java.awt.*;
import java.util.Objects;

/**
 * Computes gradients between two colors.
 */
class Gradient {

    /**
     * Given two colors, interpolate a color that is a fraction between.
     *
     * @param fraction fraction between the two colors (must be [0..1])
     * @param colorBegin begin color
     * @param colorEnd end color
     * @return interpolated color RGB value.
     */
    static int computeRGB(float fraction, Color colorBegin, Color colorEnd) {
        if (fraction < 0.f || fraction > 1.f)
            throw new IllegalArgumentException("Invalid fraction, must be within " + Bounds.FRACTION.format());
        Objects.requireNonNull(colorBegin, "color begin");
        Objects.requireNonNull(colorEnd, "color end");

        float fraction1 = 1.f - fraction;

        int red = computeColorComponent(
                fraction,
                fraction1,
                colorBegin.getRed(),
                colorEnd.getRed()
        );

        int green = computeColorComponent(
                fraction,
                fraction1,
                colorBegin.getGreen(),
                colorEnd.getGreen()
        );

        int blue = computeColorComponent(
                fraction,
                fraction1,
                colorBegin.getBlue(),
                colorEnd.getBlue()
        );

        return new Color(red, green, blue).getRGB();
    }

    private static int computeColorComponent(float fraction, float fraction1, int begin, int end) {
        return (int) Bounds.RGB_8_BIT.constrain(
                Math.round(fraction1 * begin + fraction * end)
        );
    }
}
