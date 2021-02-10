package com.primalimited.reliefshading.color;

import com.primalimited.reliefshading.bounds.Bounds;
import com.primalimited.reliefshading.number.Invalid;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class ColorMapperTest {
    @Test
    public void numeric() {
        ColorPalette colorPalette = ColorPalette.create(
                100,
                new ControlPoint(0, Color.WHITE.getRGB()),
                new ControlPoint(99, Color.BLACK.getRGB())
        );

        Bounds bounds = Bounds.of(0, 20000);

        ColorMapper<Number> colorMapper = ColorMapper
                .numeric(colorPalette, bounds);

        assertEquals(Color.WHITE.getRGB(), colorMapper.rgb(0));
        assertEquals(Color.BLACK.getRGB(), colorMapper.rgb(20000));
    }

    @Test
    public void invalidBounds() {
        ColorPalette colorPalette = ColorPalette.create(
                100,
                new ControlPoint(0, Color.WHITE.getRGB()),
                new ControlPoint(99, Color.BLACK.getRGB())
        );

        Bounds bounds = Bounds.nullBounds();

        assertThrows(IllegalArgumentException.class,
                () -> ColorMapper.numeric(colorPalette, bounds)
        );
    }

    @Test
    public void invalidRGBQuery() {
        ColorPalette colorPalette = ColorPalette.create(
                100,
                new ControlPoint(0, Color.WHITE.getRGB()),
                new ControlPoint(99, Color.BLACK.getRGB())
        );

        Bounds bounds = Bounds.of(0, 20000);

        ColorMapper<Number> colorMapper = ColorMapper
                .numeric(colorPalette, bounds);

        assertTrue(Invalid.intInstance().invalid(colorMapper.rgb(-25.38)));
        assertTrue(Invalid.intInstance().invalid(colorMapper.rgb(24681.2)));
    }
}