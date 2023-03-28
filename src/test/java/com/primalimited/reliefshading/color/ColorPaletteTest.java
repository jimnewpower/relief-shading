package com.primalimited.reliefshading.color;

import com.primalimited.reliefshading.number.Invalid;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class ColorPaletteTest {
    @Test
    void invalidNColors() {
        assertThrows(IllegalArgumentException.class,
                () -> ColorPalette.create(0));

        assertThrows(IllegalArgumentException.class,
                () -> ColorPalette.create(Invalid.INVALID_INT));
    }

    @Test
    void emptyControlPoints() {
        assertThrows(IllegalStateException.class,
                () -> ColorPalette.create(256));
    }

    @Test
    void invalidControlPoints_noZeroIndex() {
        // a valid color palette must specify control points
        // on both ends

        int nColors = 10;
        // missing control point at index 0
        ControlPoint cp0 = new ControlPoint(3, Color.BLACK.getRGB());
        ControlPoint cp1 = new ControlPoint(9, Color.BLACK.getRGB());
        assertThrows(IllegalStateException.class,
                () -> ColorPalette.create(nColors, cp0, cp1));
    }

    @Test
    void invalidControlPoints_noEndIndex() {
        // a valid color palette must specify control points
        // on both ends

        int nColors = 10;
        // missing control point at index 9 (nColors-1)
        ControlPoint cp0 = new ControlPoint(0, Color.BLACK.getRGB());
        ControlPoint cp1 = new ControlPoint(5, Color.BLACK.getRGB());
        assertThrows(IllegalStateException.class,
                () -> ColorPalette.create(nColors, cp0, cp1));
    }

    @Test
    void nColors() {
        ColorPalette colorPalette = ColorPalette
                .create(1, new ControlPoint(0, 0));
        assertEquals(1, colorPalette.nColors());

    }

    @Test
    void solid() {
        int rgb = Color.ORANGE.getRGB();
        ColorPalette solidPalette = ColorPalette.solid(rgb);
        assertEquals(1, solidPalette.nColors());
        assertEquals(rgb, solidPalette.rgb(0));
    }

    @Test
    void badIndexArgForRGBQuery() {
        int rgb = Color.ORANGE.getRGB();
        ColorPalette solidPalette = ColorPalette.solid(rgb);

        assertAll("Bad index arg for rgb()",
                () -> assertThrows(IllegalArgumentException.class, () -> solidPalette.rgb(-1)),
                () -> assertThrows(IllegalArgumentException.class, () -> solidPalette.rgb(2))
        );
    }
}