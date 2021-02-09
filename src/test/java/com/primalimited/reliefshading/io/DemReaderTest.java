package com.primalimited.reliefshading.io;

import com.primalimited.reliefshading.grid.Grid;
import org.junit.jupiter.api.Test;

import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DemReaderTest {
    @Test
    public void readSRTM_HGT_DEM() {
        URL url = getClass().getResource("N37W108.hgt");
        assertNotNull(url, "url");

        Path path = Paths.get("");
        DemReader reader = DemReader.shuttleRadarTopographyMissionHGT(path);
        Grid grid = reader.read();
    }
}
