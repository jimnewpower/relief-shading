package com.primalimited.reliefshading.algorithm;

import com.primalimited.reliefshading.units.Length;

/**
 * Compute z-factor for Cartesian coordinates.
 */
public class ZFactorCartesian implements ZFactorDem {
    private final float zFactor;

    /**
     * Constructor
     *
     * @param xyUnit unit for x,y values
     * @param zUnit unit for z values
     */
    public ZFactorCartesian(Length xyUnit, Length zUnit) {
        this.zFactor = (float) (zUnit.metersPerUnit / xyUnit.metersPerUnit);
    }

    @Override
    public float getZFactor() {
        return zFactor;
    }
}
