package com.primalimited.reliefshading.color;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ColorPaletteTransparentTest {

    @Test
    void transparentColorPalette() {
        ColorPalette opaque = ColorPaletteDefaults.DEM.colorPalette();
        ColorPalette transparent = ColorPalette.applyTransparency(opaque, 50);
        assertNotNull(transparent, "transparent color palette");

        assertEquals(2130740277, transparent.rgb(0));
        assertEquals(2134100992, transparent.rgb(100));
        assertEquals(2146758769, transparent.rgb(200));
        assertEquals(2146752347, transparent.rgb(300));
        assertEquals(2146745669, transparent.rgb(400));
        assertEquals(2143785272, transparent.rgb(500));
        assertEquals(2140759083, transparent.rgb(600));
        assertEquals(2144121494, transparent.rgb(700));
        assertEquals(2147483647, transparent.rgb(799));
    }
}