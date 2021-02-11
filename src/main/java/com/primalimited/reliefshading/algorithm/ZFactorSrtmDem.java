package com.primalimited.reliefshading.algorithm;

import com.primalimited.reliefshading.bounds.Bounds;
import com.primalimited.reliefshading.io.FilenameSRTM;

public class ZFactorSrtmDem implements ZFactorDem {
    /**
     * These factors are used when x,y units are in decimal
     * degrees and elevation units are meters.
     */
    private static final float[] Z_FACTORS = new float[] {
            0.00000898f,//0
            0.00000912f,//10
            0.00000956f,//20
            0.00001036f,//30
            0.00001171f,//40
            0.00001395f,//50
            0.00001792f,//60
            0.00002619f,//70
            0.00005156f//80
    };

    private final float zFactor;

    public ZFactorSrtmDem(FilenameSRTM filenameSRTM) {
        int latitude = filenameSRTM.getLatitude();
        int index = (int) Bounds.of(0, 8).constrain(Math.abs(latitude) / 10);
        zFactor = Z_FACTORS[index];
    }

    @Override
    public float getZFactor() {
        return zFactor;
    }
}
