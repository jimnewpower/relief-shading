package com.primalimited.reliefshading.color;

import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PaletteBuilderTest {
    @Test
    void oneColorOneControlPoint() {
        // when asking for 1 color, the control point with index 0 should be that color
        int[] rgb = PaletteBuilder.buildRGB(1, new ControlPoint(0, Color.PINK.getRGB()));
        assertEquals(1, rgb.length, "length should == 1");
        assertEquals(Color.PINK.getRGB(), rgb[0]);
    }

    @Test
    void oneColorMultipleControlPoints() {
        // when asking for 1 color, the control point with index 0 should be that color
        int[] rgb = PaletteBuilder.buildRGB(
                1,
                new ControlPoint(0, Color.PINK.getRGB()),
                new ControlPoint(2, Color.ORANGE.getRGB()),
                new ControlPoint(4, Color.YELLOW.getRGB()),
                new ControlPoint(6, Color.GREEN.getRGB())
        );
        assertEquals(1, rgb.length, "length should == 1");
        assertEquals(Color.PINK.getRGB(), rgb[0]);
    }

    @Test
    void constantColorPalette() {
        int nColors = 12;
        Color color = Color.CYAN;
        int[] rgb = PaletteBuilder
                .buildRGB(
                        nColors,
                        new ControlPoint(0, color.getRGB())
                );

        assertEquals(nColors, rgb.length);
        for (int rgbValue : rgb)
            assertEquals(color.getRGB(), rgbValue);
    }

    @Test
    void threeColorPalette() {
        int[] rgb = PaletteBuilder
                .buildRGB(
                        3,
                        new ControlPoint(0, Color.WHITE.getRGB()),
                        new ControlPoint(2, Color.BLACK.getRGB())
                );

        assertEquals(3, rgb.length);

        assertEquals(Color.WHITE.getRGB(), rgb[0]);
        assertEquals(-8355712, rgb[1]);
        assertEquals(Color.BLACK.getRGB(), rgb[2]);
    }

    @Test
    void threeColorPaletteSameControlPointColors() {
        int[] rgb = PaletteBuilder
                .buildRGB(
                        3,
                        new ControlPoint(0, Color.BLUE.getRGB()),
                        new ControlPoint(2, Color.BLUE.getRGB())
                );

        assertEquals(3, rgb.length);

        assertEquals(Color.BLUE.getRGB(), rgb[0]);
        assertEquals(Color.BLUE.getRGB(), rgb[1]);
        assertEquals(Color.BLUE.getRGB(), rgb[2]);
    }

    @Test
    void fiveColorPalette3ControlPoints() {
        int[] rgb = PaletteBuilder
                .buildRGB(
                        5,
                        new ControlPoint(0, Color.BLUE.getRGB()),
                        new ControlPoint(2, Color.RED.getRGB()),
                        new ControlPoint(4, Color.BLUE.getRGB())
                );

        assertEquals(5, rgb.length);
        assertEquals(Color.BLUE.getRGB(), rgb[0]);
        assertEquals(-8388480, rgb[1]);
        assertEquals(Color.RED.getRGB(), rgb[2]);
        assertEquals(-8388480, rgb[3]);
        assertEquals(Color.BLUE.getRGB(), rgb[4]);

    }
}