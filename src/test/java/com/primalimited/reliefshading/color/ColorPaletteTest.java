package com.primalimited.reliefshading.color;

import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class ColorPaletteTest {
    @Test
    public void nColors() {
        ColorPalette colorPalette = ColorPalette
                .create(1, new ControlPoint(0, 0));
        assertEquals(1, colorPalette.nColors());

    }

    @Test
    public void solid() {
        int rgb = Color.ORANGE.getRGB();
        ColorPalette solidPalette = ColorPalette.solid(rgb);
        assertEquals(1, solidPalette.nColors());
        assertEquals(rgb, solidPalette.rgb(0));
    }
}