package com.primalimited.reliefshading.number;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InvalidTest {
    @Test
    public void testDouble() {
        double value = 1234567.89;
        assertFalse(Invalid.test(value));
        assertTrue(Invalid.test(Invalid.INVALID_DOUBLE));

        assertFalse(Invalid.doubleInstance().invalid(value));
        assertTrue(Invalid.doubleInstance().invalid(Invalid.INVALID_DOUBLE));
    }
}