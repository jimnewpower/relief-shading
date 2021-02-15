package com.primalimited.reliefshading.color;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ColorUtilityTest {

    @Test
    public void percentTo8Bit() {
        assertEquals(0, ColorUtility.percentTo8BitValue(-1));
        assertEquals(25, ColorUtility.percentTo8BitValue(10));
        assertEquals(64, ColorUtility.percentTo8BitValue(25));
        assertEquals(127, ColorUtility.percentTo8BitValue(50));
        assertEquals(191, ColorUtility.percentTo8BitValue(75));
        assertEquals(255, ColorUtility.percentTo8BitValue(100));
        assertEquals(255, ColorUtility.percentTo8BitValue(101));
    }

}