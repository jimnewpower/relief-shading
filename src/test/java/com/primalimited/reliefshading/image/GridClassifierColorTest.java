package com.primalimited.reliefshading.image;

import com.primalimited.reliefshading.color.ColorPaletteDefaults;
import com.primalimited.reliefshading.grid.Grid;
import com.primalimited.reliefshading.io.DemReader;
import com.primalimited.reliefshading.io.DemReaderTest;
import com.primalimited.reliefshading.io.LocalFileReader;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class GridClassifierColorTest {

    @Test
    public void classify() throws IOException, URISyntaxException {
        GridClassifier classifier = GridClassifier
                .color(ColorPaletteDefaults.DEM.colorPalette());

        BufferedImage image = classifier.classify(loadGrid());
        assertNotNull(image);

        // Un-comment to get this image file in the project root:
        Path output = Paths.get("N37w108-color.png");
        ImageIO.write(image, "png", output.toFile());

        assertEquals(-726934, image.getRGB(0, 0));
        assertEquals(-732071, image.getRGB(100, 200));
        assertEquals(-1330110, image.getRGB(1000, 2000));
        assertEquals(-4659890, image.getRGB(2000, 3000));
        assertEquals(-13646076, image.getRGB(3000, 3600));
        assertEquals(-3895753, image.getRGB(1800, 1800));
        assertEquals(-727191, image.getRGB(3600, 3600));
    }

    private Grid loadGrid() throws IOException, URISyntaxException {
        Path path = new LocalFileReader().getDemoPath();
        return DemReader.shuttleRadarTopographyMissionHGT(path).read();
    }
}