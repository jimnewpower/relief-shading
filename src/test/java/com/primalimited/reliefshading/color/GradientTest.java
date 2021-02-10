package com.primalimited.reliefshading.color;

import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class GradientTest {
    @Test
    public void testGradient() {
        assertEquals(Color.WHITE.getRGB(), Gradient.computeRGB(0.f, Color.WHITE, Color.BLACK));
        assertEquals(Color.BLACK.getRGB(), Gradient.computeRGB(1.f, Color.WHITE, Color.BLACK));
    }
}