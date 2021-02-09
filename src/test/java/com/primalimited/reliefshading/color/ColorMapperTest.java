package com.primalimited.reliefshading.color;

import com.primalimited.reliefshading.bounds.Bounds;
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
}