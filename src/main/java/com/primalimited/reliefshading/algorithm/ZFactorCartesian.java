package com.primalimited.reliefshading.algorithm;

import com.primalimited.reliefshading.units.Length;

public class ZFactorCartesian implements ZFactorDem {
    private final float zFactor;

    public ZFactorCartesian(Length xyUnit, Length zUnit) {
        this.zFactor = (float) (zUnit.metersPerUnit / xyUnit.metersPerUnit);
    }

    @Override
    public float getZFactor() {
        return zFactor;
    }
}
