package com.primalimited.reliefshading.io;

import com.primalimited.reliefshading.bounds.Bounds2D;
import com.primalimited.reliefshading.grid.Grid;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class DemReaderTest {

    @Test
    void readSRTM_HGT_DEM() throws IOException, URISyntaxException {
        Path path = new LocalFileReader().getDemoPath();
        assertNotNull(path, "Path");

        DemReader reader = DemReader.shuttleRadarTopographyMissionHGT(path);
        Grid grid = reader.read();
        assertNotNull(grid, "Grid");

        final double tolerance = 1e-8;
        Bounds2D bounds = grid.bounds();
        assertAll("Bounds checks for demo file",
                () -> assertEquals(-108.0, bounds.minX(), tolerance),
                () -> assertEquals(-107.0, bounds.maxX(), tolerance),
                () -> assertEquals(37.0, bounds.minY(), tolerance),
                () -> assertEquals(38.0, bounds.maxY(), tolerance)
        );

        // test some known values (corners, etc.)
        short expected = 2000;
        assertEquals(expected, grid.value(0, 0));
        expected = 2528;
        assertEquals(expected, grid.value(3600, 0));
        expected = 3776;
        assertEquals(expected, grid.value(3600, 3600));
        expected = 2535;
        assertEquals(expected, grid.value(0, 3600));
        expected = 1945;
        assertEquals(expected, grid.value(220, 340));
        expected = 3374;
        assertEquals(expected, grid.value(1800, 1800));
    }
}
