package com.primalimited.reliefshading.image;

import com.primalimited.reliefshading.color.ColorPaletteDefaults;
import com.primalimited.reliefshading.grid.Grid;
import com.primalimited.reliefshading.io.DemReader;
import com.primalimited.reliefshading.io.DemReaderTest;
import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class GridClassifierColorTest {
    private static final String DEMO_FILENAME = "N37W108.hgt";

    @Test
    public void classify() throws IOException {
        GridClassifier classifier = GridClassifierColor
                .of(ColorPaletteDefaults.DEM.colorPalette());

        BufferedImage image = classifier.classify(loadGrid());
        assertNotNull(image);
    }

    private Grid loadGrid() throws IOException {
        URL url = DemReaderTest.class.getResource(DEMO_FILENAME);
        Path path = Path.of(url.getPath());
        return DemReader.shuttleRadarTopographyMissionHGT(path).read();
    }
}