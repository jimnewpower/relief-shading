package com.primalimited.reliefshading.io;

import com.primalimited.reliefshading.grid.Grid;

import java.nio.file.Path;
import java.util.Objects;

public class DemReaderSRTM implements DemReader {
    private transient final Path path;

    DemReaderSRTM(Path path) {
        this.path = Objects.requireNonNull(path, "Path for DEM file.");
    }

    @Override
    public Grid read() {
        return null;
    }
}
