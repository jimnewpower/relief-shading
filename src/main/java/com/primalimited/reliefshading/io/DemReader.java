package com.primalimited.reliefshading.io;

import com.primalimited.reliefshading.grid.Grid;

import java.nio.file.Path;

public interface DemReader {
    static DemReader shuttleRadarTopographyMissionHGT(Path path) {
        return new DemReaderSRTM(path);
    }

    Grid read();
}
