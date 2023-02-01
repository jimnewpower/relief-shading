package com.primalimited.reliefshading.image;

import com.primalimited.reliefshading.algorithm.ZFactorDem;
import com.primalimited.reliefshading.algorithm.ZFactorSrtmDem;
import com.primalimited.reliefshading.grid.Grid;
import com.primalimited.reliefshading.io.DemReader;
import com.primalimited.reliefshading.io.DemReaderTest;
import com.primalimited.reliefshading.io.FilenameSRTM;
import com.primalimited.reliefshading.io.LocalFileReader;
import com.primalimited.reliefshading.prefs.Preferences;
import com.primalimited.reliefshading.prefs.PreferencesBuilder;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class GridClassifierShadedReliefTest {
    private static final String DEMO_FILENAME = "N37W108.hgt";

    @Test
    public void shadedRelief() throws IOException, URISyntaxException {
        ZFactorDem zFactorDem = new ZFactorSrtmDem(FilenameSRTM.create(DEMO_FILENAME));

        Preferences preferences = new PreferencesBuilder()
                .altitudeDegrees(30)
                .azimuthDegrees(225)
                .build();

        GridClassifier classifier = GridClassifierShadedRelief
                .of(preferences, zFactorDem);

        BufferedImage image = classifier
                .classify(loadGrid());

        Path output = Paths
                .get("N37w108-shaded.png");
        ImageIO.write(image, "png", output.toFile());
    }

    private Grid loadGrid() throws IOException, URISyntaxException {
        Path path = new LocalFileReader().getDemoPath();
        return DemReader.shuttleRadarTopographyMissionHGT(path).read();
    }
}