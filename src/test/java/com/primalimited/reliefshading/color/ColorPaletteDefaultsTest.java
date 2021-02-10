package com.primalimited.reliefshading.color;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ColorPaletteDefaultsTest {
    @Test
    public void testPalette_DEM() {
        assertNotNull(ColorPaletteDefaults.DEM.colorPalette());
        assertEquals(800, ColorPaletteDefaults.DEM.colorPalette().nColors());
    }
}
