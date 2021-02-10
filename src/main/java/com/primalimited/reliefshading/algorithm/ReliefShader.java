package com.primalimited.reliefshading.algorithm;

import com.primalimited.reliefshading.grid.Grid;
import com.primalimited.reliefshading.prefs.Preferences;

public interface ReliefShader {
    public static ReliefShader create(Preferences preferences) {
        return ReliefShaderImpl.create(preferences);
    }

    Grid apply(Grid grid);
}
