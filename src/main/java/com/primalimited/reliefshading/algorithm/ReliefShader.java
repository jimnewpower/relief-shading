package com.primalimited.reliefshading.algorithm;

import com.primalimited.reliefshading.grid.Grid;
import com.primalimited.reliefshading.prefs.Preferences;

/**
 * Interface for relief shading algorithms.
 *
 * @author Jim Newpower
 */
public interface ReliefShader {
    static ReliefShader create(Preferences preferences) {
        return ReliefShaderImpl.create(preferences);
    }

    Grid apply(Grid grid);
}
