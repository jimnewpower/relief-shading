package com.primalimited.reliefshading.algorithm;

public interface ZFactorDem {
    /**
     * Return the number of ground (x,y) units in one surface elevation unit.
     *
     * @return the number of ground (x,y) units in one surface elevation unit.
     */
    default float getZFactor() {
        return 1.0f;
    }
}
