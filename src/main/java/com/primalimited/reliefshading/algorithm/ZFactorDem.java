package com.primalimited.reliefshading.algorithm;

public interface ZFactorDem {
    /**
     * Return the number of ground (x,y) units in one surface z-unit.
     *
     * @return the number of ground (x,y) units in one surface z-unit.
     */
    default float getZFactor() {
        return 1.0f;
    }
}
