package com.primalimited.reliefshading.number;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InvalidTest {
    @Test
    public void invalidDoubles() {
        double value = 1234567.89;
        assertFalse(Invalid.test(value));
        assertTrue(Invalid.test(Invalid.INVALID_DOUBLE));

        assertFalse(Invalid.doubleInstance().invalid(value));
        assertTrue(Invalid.doubleInstance().invalid(Invalid.INVALID_DOUBLE));
    }

    @Test
    public void invalidFloats() {
        float value = 1234567.89f;
        assertFalse(Invalid.test(value));
        assertTrue(Invalid.test(Invalid.INVALID_FLOAT));

        assertFalse(Invalid.floatInstance().invalid(value));
        assertTrue(Invalid.floatInstance().invalid(Invalid.INVALID_FLOAT));
    }

    @Test
    public void invalidInts() {
        int value = 123456789;
        assertFalse(Invalid.test(value));
        assertTrue(Invalid.test(Invalid.INVALID_INT));

        assertFalse(Invalid.intInstance().invalid(value));
        assertTrue(Invalid.intInstance().invalid(Invalid.INVALID_INT));
    }

    @Test
    public void invalidShorts() {
        short value = 12345;
        assertFalse(Invalid.test(value));
        assertTrue(Invalid.test(Invalid.INVALID_SHORT));

        assertFalse(Invalid.shortInstance().invalid(value));
        assertTrue(Invalid.shortInstance().invalid(Invalid.INVALID_SHORT));
    }
}