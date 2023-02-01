package com.primalimited.reliefshading.algorithm;

import com.primalimited.reliefshading.grid.Grid;
import com.primalimited.reliefshading.io.DemReader;
import com.primalimited.reliefshading.io.DemReaderTest;
import com.primalimited.reliefshading.io.FilenameSRTM;
import com.primalimited.reliefshading.prefs.Preferences;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ReliefShaderTest {
    private static final String DEMO_FILENAME = "N37W108.hgt";

    @Test
    public void reliefShader() throws IOException, URISyntaxException {
        URI uri = DemReader.class.getResource(DEMO_FILENAME).toURI();
        String filePath = Paths.get(uri).toString();
        Path path = Paths.get(filePath);

        Grid grid = DemReader.shuttleRadarTopographyMissionHGT(path).read();

        ReliefShader reliefShader = ReliefShader
                .create(Preferences.createDefault());

        ZFactorDem zFactorDem = new ZFactorSrtmDem(FilenameSRTM.create(DEMO_FILENAME));

        Grid shaded = reliefShader.apply(grid, zFactorDem.getZFactor());

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