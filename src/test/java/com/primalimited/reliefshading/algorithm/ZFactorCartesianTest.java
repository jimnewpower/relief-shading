package com.primalimited.reliefshading.algorithm;

import com.primalimited.reliefshading.units.Length;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ZFactorCartesianTest {
    @Test
    void xyInMeters_ZInFeet() {
        ZFactorDem zFactorDem = new ZFactorCartesian(Length.METERS, Length.FEET);
        assertEquals(0.3048f, zFactorDem.getZFactor());
    }

    @Test
    void xyInKilometers_ZInMeters() {
        ZFactorDem zFactorDem = new ZFactorCartesian(Length.KILOMETERS, Length.METERS);
        assertEquals(0.001f, zFactorDem.getZFactor());
    }

    @Test
    void same() {
        assertAll(
                "Same units for x,y and z",
                () -> assertEquals(1.f, new ZFactorCartesian(Length.METERS, Length.METERS).getZFactor()),
                () -> assertEquals(1.f, new ZFactorCartesian(Length.KILOMETERS, Length.KILOMETERS).getZFactor()),
                () -> assertEquals(1.f, new ZFactorCartesian(Length.FEET, Length.FEET).getZFactor()),
                () -> assertEquals(1.f, new ZFactorCartesian(Length.MILES, Length.MILES).getZFactor())
        );
    }
}