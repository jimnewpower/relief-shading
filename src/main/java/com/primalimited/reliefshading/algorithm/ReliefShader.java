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
     * @param zFactor conversion factor that adjusts the units of measure for
     *                the elevation units when they differ from the horizontal units
     * @return result grid
     */
    Grid apply(Grid grid, float zFactor);
}
