package com.primalimited.reliefshading.io;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ZFactorSrtmDemTest {

    @Test
    public void testZFactorsSouthLatitude() {
        FilenameSRTM filename = FilenameSRTM.create("S81W108.hgt");
        ZFactorDem zFactorDem = new ZFactorSrtmDem(filename);
        assertEquals(0.00005156f, zFactorDem.getZFactor());

        filename = FilenameSRTM.create("S77W108.hgt");
        zFactorDem = new ZFactorSrtmDem(filename);
        assertEquals(0.00002619f, zFactorDem.getZFactor());

        filename = FilenameSRTM.create("S63W108.hgt");
        zFactorDem = new ZFactorSrtmDem(filename);
        assertEquals(0.00001792f, zFactorDem.getZFactor());

        filename = FilenameSRTM.create("S54W108.hgt");
        zFactorDem = new ZFactorSrtmDem(filename);
        assertEquals(0.00001395f, zFactorDem.getZFactor());

        filename = FilenameSRTM.create("S40W108.hgt");
        zFactorDem = new ZFactorSrtmDem(filename);
        assertEquals(0.00001171f, zFactorDem.getZFactor());

        filename = FilenameSRTM.create("S39W108.hgt");
        zFactorDem = new ZFactorSrtmDem(filename);
        assertEquals(0.00001036f, zFactorDem.getZFactor());

        filename = FilenameSRTM.create("S21W108.hgt");
        zFactorDem = new ZFactorSrtmDem(filename);
        assertEquals(0.00000956f, zFactorDem.getZFactor());

        filename = FilenameSRTM.create("S10W108.hgt");
        zFactorDem = new ZFactorSrtmDem(filename);
        assertEquals(0.00000912f, zFactorDem.getZFactor());

        filename = FilenameSRTM.create("S04W108.hgt");
        zFactorDem = new ZFactorSrtmDem(filename);
        assertEquals(0.00000898f, zFactorDem.getZFactor());
    }

    @Test
    public void testZFactorsNorthLatitude() {
        FilenameSRTM filename = FilenameSRTM.create("N81W108.hgt");
        ZFactorDem zFactorDem = new ZFactorSrtmDem(filename);
        assertEquals(0.00005156f, zFactorDem.getZFactor());

        filename = FilenameSRTM.create("N77W108.hgt");
        zFactorDem = new ZFactorSrtmDem(filename);
        assertEquals(0.00002619f, zFactorDem.getZFactor());

        filename = FilenameSRTM.create("N63W108.hgt");
        zFactorDem = new ZFactorSrtmDem(filename);
        assertEquals(0.00001792f, zFactorDem.getZFactor());

        filename = FilenameSRTM.create("N54W108.hgt");
        zFactorDem = new ZFactorSrtmDem(filename);
        assertEquals(0.00001395f, zFactorDem.getZFactor());

        filename = FilenameSRTM.create("N40W108.hgt");
        zFactorDem = new ZFactorSrtmDem(filename);
        assertEquals(0.00001171f, zFactorDem.getZFactor());

        filename = FilenameSRTM.create("N39W108.hgt");
        zFactorDem = new ZFactorSrtmDem(filename);
        assertEquals(0.00001036f, zFactorDem.getZFactor());

        filename = FilenameSRTM.create("N21W108.hgt");
        zFactorDem = new ZFactorSrtmDem(filename);
        assertEquals(0.00000956f, zFactorDem.getZFactor());

        filename = FilenameSRTM.create("N10W108.hgt");
        zFactorDem = new ZFactorSrtmDem(filename);
        assertEquals(0.00000912f, zFactorDem.getZFactor());

        filename = FilenameSRTM.create("N04W108.hgt");
        zFactorDem = new ZFactorSrtmDem(filename);
        assertEquals(0.00000898f, zFactorDem.getZFactor());
    }
}