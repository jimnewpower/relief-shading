package com.primalimited.reliefshading.io;

import com.primalimited.reliefshading.bounds.Bounds;
import com.primalimited.reliefshading.bounds.Bounds2D;
import org.junit.jupiter.api.Test;

import java.net.URL;
import java.nio.file.Path;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

class DemReaderSRTMTest {
    private static final String DEMO_FILENAME = "N37W108.hgt";

    @Test
    public void filename() {
        assertEquals(DEMO_FILENAME.toUpperCase(Locale.US), createReader().filename());
    }

    @Test
    public void longitude() {
        assertAll("Longitude for " + DEMO_FILENAME,
                () -> assertTrue(createReader().isWest()),
                () -> assertEquals("W", createReader().getLongitudeText()),
                () -> assertEquals(-108, createReader().getLongitude()),
                () -> assertEquals(Bounds.of(-108, -107), createReader().getLongitudeBounds())
        );
    }

    @Test
    public void latitude() {
        assertAll("Latitude for " + DEMO_FILENAME,
                () -> assertTrue(createReader().isNorth()),
                () -> assertEquals("N", createReader().getLatitudeText()),
                () -> assertEquals(37, createReader().getLatitude()),
                () -> assertEquals(Bounds.of(37, 38), createReader().getLatitudeBounds())
        );
    }

    @Test
    public void bounds2D() {
        assertEquals(
                Bounds2D.create(Bounds.of(-108, -107), Bounds.of(37, 38)),
                createReader().createBounds()
        );
    }

    private DemReaderSRTM createReader() {
        URL url = getClass().getResource(DEMO_FILENAME);
        Path path = Path.of(url.getPath());
        return new DemReaderSRTM(path);
    }
}