package com.primalimited.reliefshading.color;

import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class GradientTest {
    @Test
    void whiteBlack() {
        assertEquals(Color.WHITE.getRGB(), Gradient.computeRGB(0.f, Color.WHITE, Color.BLACK));
        assertEquals(Color.BLACK.getRGB(), Gradient.computeRGB(1.f, Color.WHITE, Color.BLACK));
    }

    @Test
    void gray() {
        assertEquals(
                new Color(128, 128, 128).getRGB(),
                Gradient.computeRGB(0.5f, Color.WHITE, Color.BLACK)
        );
        assertEquals(
                new Color(128, 128, 128).getRGB(),
                Gradient.computeRGB(0.5f, Color.BLACK, Color.WHITE)
        );
    }

    @Test
    void redBlue() {
        assertEquals(
                new Color(128, 0, 128).getRGB(),
                Gradient.computeRGB(0.5f, Color.RED, Color.BLUE)
        );
        assertEquals(
                new Color(128, 0, 128).getRGB(),
                Gradient.computeRGB(0.5f, Color.BLUE, Color.RED)
        );
    }

    @Test
    void invalidFraction() {
        Color begin = Color.BLACK;
        Color end = Color.WHITE;

        assertAll(
                "Invalid fraction argument",
                () -> assertThrows(
                        IllegalArgumentException.class,
                        () -> Gradient.computeRGB(-0.01f, begin, end)
                ),
                () -> assertThrows(
                        IllegalArgumentException.class,
                        () -> Gradient.computeRGB(1.01f, begin, end)
                )
        );
    }

    @Test
    void nullColorArgs() {
        assertAll(
                "Invalid color arguments",
                () -> assertThrows(
                        NullPointerException.class,
                        () -> Gradient.computeRGB(0.5f, null, Color.ORANGE)
                ),
                () -> assertThrows(
                        NullPointerException.class,
                        () -> Gradient.computeRGB(0.5f, Color.GRAY, null)
                )
        );
    }
}