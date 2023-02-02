package com.primalimited.reliefshading.image;

import com.primalimited.reliefshading.algorithm.ZFactorDem;
import com.primalimited.reliefshading.algorithm.ZFactorSrtmDem;
import com.primalimited.reliefshading.color.ColorPaletteDefaults;
import com.primalimited.reliefshading.grid.Grid;
import com.primalimited.reliefshading.io.DemReader;
import com.primalimited.reliefshading.io.FilenameSRTM;
import com.primalimited.reliefshading.io.LocalFileReader;
import com.primalimited.reliefshading.prefs.Preferences;
import com.primalimited.reliefshading.prefs.PreferencesBuilder;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.primalimited.reliefshading.io.LocalFileReader.DEMO_FILENAME;

public class OverlayTest {
    @Test
    public void overlayTest() throws IOException, URISyntaxException {
        Grid grid = loadGrid();

        GridClassifier classifier = GridClassifier
                .color(ColorPaletteDefaults.DEM.colorPalette());

        BufferedImage colorImage = classifier.apply(grid);

        ZFactorDem zFactorDem = new ZFactorSrtmDem(FilenameSRTM.create(DEMO_FILENAME));

        Preferences preferences = new PreferencesBuilder()
                .opacityPercent(30)
                .build();

        classifier = GridClassifier
                .shaded(preferences, zFactorDem);

        BufferedImage shadedImage = classifier.apply(loadGrid());

        int width = shadedImage.getWidth();
        int height = shadedImage.getHeight();

        BufferedImage combined = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        Graphics g = combined.getGraphics();
        ((Graphics2D)g).setComposite(AlphaComposite.SrcOver);
        g.drawImage(colorImage, 0, 0, null);
        g.drawImage(shadedImage, 0, 0, null);

        Path output = Paths.get("N37w108-overlay.png");
        ImageIO.write(combined, "png", output.toFile());

        g.dispose();
    }

    private Grid loadGrid() throws IOException, URISyntaxException {
        Path path = new LocalFileReader().getDemoPath();
        return DemReader.shuttleRadarTopographyMissionHGT(path).read();
    }

}
