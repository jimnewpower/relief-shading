package com.primalimited.reliefshading.number;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

class InvalidTest {
    @Test
    void nullNumber() {
        assertTrue(Invalid.test(null));
    }

    @Test
    void unsupportedNumber() {
        assertThrows(IllegalArgumentException.class, () -> Invalid.test(new AtomicInteger()));
        assertThrows(IllegalArgumentException.class, () -> Invalid.test(BigDecimal.valueOf(0L)));
    }

    @Test
    void invalidDoubles() {
        double value = 1234567.89;
        assertFalse(Invalid.test(value));
        assertTrue(Invalid.test(Invalid.INVALID_DOUBLE));

        assertFalse(Invalid.doubleInstance().invalid(value));
        assertTrue(Invalid.doubleInstance().invalid(Invalid.INVALID_DOUBLE));
    }

    @Test
    void invalidFloats() {
        float value = 1234567.89f;
        assertFalse(Invalid.test(value));
        assertTrue(Invalid.test(Invalid.INVALID_FLOAT));

        assertFalse(Invalid.floatInstance().invalid(value));
        assertTrue(Invalid.floatInstance().invalid(Invalid.INVALID_FLOAT));
    }

    @Test
    void invalidInts() {
        int value = 123456789;
        assertFalse(Invalid.test(value));
        assertTrue(Invalid.test(Invalid.INVALID_INT));

        assertFalse(Invalid.intInstance().invalid(Integer.valueOf(value)));
        assertFalse(Invalid.intInstance().invalid(value));
        assertTrue(Invalid.intInstance().invalid(Invalid.INVALID_INT));
    }

    @Test
    void invalidShorts() {
        short value = 12345;
        assertFalse(Invalid.test(value));
        assertTrue(Invalid.test(Invalid.INVALID_SHORT));

        assertFalse(Invalid.shortInstance().invalid(value));
        assertTrue(Invalid.shortInstance().invalid(Invalid.INVALID_SHORT));
    }
}