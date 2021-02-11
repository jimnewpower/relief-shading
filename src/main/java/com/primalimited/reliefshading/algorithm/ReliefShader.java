package com.primalimited.reliefshading.algorithm;

import com.primalimited.reliefshading.grid.Grid;
import com.primalimited.reliefshading.prefs.Preferences;

/**
 * Interface for relief shading algorithms.
 *
 * @author Jim Newpower
 */
public interface ReliefShader {
    /**
     * Create default relief shader.
     *
     * @param preferences relief shading preferences
     * @return instance of relief shader.
     */
    static ReliefShader create(Preferences preferences) {
        return ReliefShaderImpl.create(preferences);
    }

    /**
     * Apply relief shading algorithm to the input grid, returning a result
     * grid of the same dimensions, but with z values representing grayscale
     * relief shading values.
     *
     * @param grid input grid
     * @return result grid
     */
    Grid apply(Grid grid);
}
