package com.primalimited.reliefshading.io;

import com.primalimited.reliefshading.bounds.Bounds;
import com.primalimited.reliefshading.bounds.Bounds2D;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FilenameSRTMTest {
    private static final String DEMO_FILENAME = "N37W108.hgt";

    @Test
    public void filename() {
        String name = DEMO_FILENAME;
        FilenameSRTM filename = FilenameSRTM.create(name);
        assertEquals(name, filename.filename());
    }

    @Test
    public void longitude() {
        String name = DEMO_FILENAME;
        FilenameSRTM filename = FilenameSRTM.create(name);
        assertAll("Longitude for " + name,
                () -> assertTrue(filename.isWest()),
                () -> assertEquals("W", filename.getLongitudeText()),
                () -> assertEquals(-108, filename.getLongitude()),
                () -> assertEquals(Bounds.of(-108, -107), filename.getLongitudeBounds())
        );
    }

    @Test
    public void latitude() {
        String name = DEMO_FILENAME;
        FilenameSRTM filename = FilenameSRTM.create(name);
        assertAll("Latitude for " + name,
                () -> assertTrue(filename.isNorth()),
                () -> assertEquals("N", filename.getLatitudeText()),
                () -> assertEquals(37, filename.getLatitude()),
                () -> assertEquals(Bounds.of(37, 38), filename.getLatitudeBounds())
        );
    }

    @Test
    public void bounds2D() {
        String name = DEMO_FILENAME;
        FilenameSRTM filename = FilenameSRTM.create(name);
        assertEquals(
                Bounds2D.create(Bounds.of(-108, -107), Bounds.of(37, 38)),
                filename.createBounds()
        );
    }

}