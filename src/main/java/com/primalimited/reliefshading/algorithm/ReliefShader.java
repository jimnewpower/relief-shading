package com.primalimited.reliefshading.algorithm;

import com.primalimited.reliefshading.grid.Grid;
import com.primalimited.reliefshading.prefs.Preferences;

import java.util.function.BiFunction;

/**
 * Interface for relief shading algorithms.
 *
 * @author Jim Newpower
 */
public interface ReliefShader extends BiFunction<Grid, Float, Grid> {
    /**
     * Create default relief shader.
     *
     * @param preferences relief shading preferences
     * @return instance of relief shader.
     */
    static ReliefShader create(Preferences preferences) {
        return ReliefShaderImpl.create(preferences);
    }
}
