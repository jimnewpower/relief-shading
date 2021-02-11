package com.primalimited.reliefshading.algorithm;

import com.primalimited.reliefshading.units.Length;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ZFactorCartesianTest {
    @Test
    public void xyInMeters_ZInFeet() {
        ZFactorDem zFactorDem = new ZFactorCartesian(Length.METERS, Length.FEET);
        assertEquals(0.3048f, zFactorDem.getZFactor());
    }

    @Test
    public void xyInKilometers_ZInMeters() {
        ZFactorDem zFactorDem = new ZFactorCartesian(Length.KILOMETERS, Length.METERS);
        assertEquals(0.001f, zFactorDem.getZFactor());
    }
}