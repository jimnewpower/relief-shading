package com.primalimited.reliefshading.algorithm;

import com.primalimited.reliefshading.grid.Grid;
import com.primalimited.reliefshading.io.DemReader;
import com.primalimited.reliefshading.io.DemReaderTest;
import com.primalimited.reliefshading.prefs.Preferences;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ReliefShaderTest {
    private static final String DEMO_FILENAME = "N37W108.hgt";

    @Test
    public void reliefShader() throws IOException {
        URL url = DemReaderTest.class.getResource(DEMO_FILENAME);
        Path path = Path.of(url.getPath());

        DemReader.shuttleRadarTopographyMissionHGT(path);

        Grid grid = DemReader.shuttleRadarTopographyMissionHGT(path).read();

        ReliefShader reliefShader = ReliefShader
                .create(Preferences.createDefault());

        float zFactor = 0.00001036f;
        Grid shaded = reliefShader.apply(grid, zFactor);

        int row = 52;
        int col = 65;
        assertEquals(2020, grid.value(row, col).intValue());
        assertEquals(188, shaded.value(row, col).intValue());

        row = 52;
        col = 70;
        assertEquals(2021, grid.value(row, col).intValue());
        assertEquals(185, shaded.value(row, col).intValue());

    }
}