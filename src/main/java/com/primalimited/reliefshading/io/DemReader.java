package com.primalimited.reliefshading.io;

import com.primalimited.reliefshading.grid.Grid;

import java.io.IOException;
import java.nio.file.Path;

/**
 * Interface for reading DEM files into Grid structures.
 */
public interface DemReader {
    /**
     * Create reader for SRTM .hgt files.
     *
     * @param path path to SRTM .hgt file
     * @return reader for SRTM .hgt files.
     */
    static DemReader shuttleRadarTopographyMissionHGT(Path path) {
        return new DemReaderSRTM(path);
    }

    /**
     * Read file, populating a grid.
     *
     * @return grid from the DEM file.
     * @throws IOException
     */
    Grid read() throws IOException;
}
