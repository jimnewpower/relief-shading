package com.primalimited.reliefshading.io;

import com.primalimited.reliefshading.grid.Grid;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LocalFileReader {

    private static final String DEMO_FILENAME = "N37W108.hgt";

    public Path getDemoPath() throws IOException, URISyntaxException {
        URI uri = LocalFileReader.class.getResource(DEMO_FILENAME).toURI();
        String filePath = Paths.get(uri).toString();
        return Paths.get(filePath);
    }

    public Grid loadGrid() throws IOException, URISyntaxException {
        Path path = getDemoPath();
        return DemReader.shuttleRadarTopographyMissionHGT(path).read();
    }

}
