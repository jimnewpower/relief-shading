package com.primalimited.reliefshading.bounds;

import com.primalimited.reliefshading.number.Invalid;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BoundsTest {
    @Test
    public void legalBounds() {
        double min = 3.5;
        double max = 987.654;
        Bounds bounds = Bounds.of(min, max);

        final double tolerance = 1e-10;

        assertAll(
                "Legal Bounds",
                () -> assertTrue(bounds.isValid()),
                () -> assertEquals(min, bounds.min(), tolerance),
                () -> assertEquals(max, bounds.max(), tolerance)
        );
    }

    @Test
    public void nonzeroRange() {
        assertEquals(27.42, Bounds.of(1000.12, 1027.54).range(), 1e-10);
    }

    @Test
    public void zeroRange() {
        double min = 10.25;
        double max = 10.25;
        Bounds bounds = Bounds.of(min, max);

        final double tolerance = 1e-10;

        assertAll(
                "Zero Range",
                () -> assertTrue(bounds.isValid()),
                () -> assertTrue(bounds.rangeIsZero()),
                () -> assertEquals(min, bounds.min(), tolerance),
                () -> assertEquals(max, bounds.max(), tolerance)
        );
    }

    @Test
    public void illegalBounds() {
        double min = 10.0;
        double max = 7.0;
        assertThrows(IllegalArgumentException.class, () -> Bounds.of(min, max));
    }

    @Test
    public void createFromArray() {
        double[] array = new double[] { 0, 1, 2, 50, 60, 100, 200 };

        final double tolerance = 1e-10;
        assertEquals(0, Bounds.of(array).min(), tolerance);
        assertEquals(200, Bounds.of(array).max(), tolerance);
    }

    @Test
    public void createFromArrayWithInvalids() {
        double[] array = new double[] {Invalid.INVALID_DOUBLE, 1, 2, 50, Invalid.INVALID_DOUBLE, 100, Invalid.INVALID_DOUBLE };

        final double tolerance = 1e-10;
        assertEquals(1, Bounds.of(array).min(), tolerance);
        assertEquals(100, Bounds.of(array).max(), tolerance);
    }

    @Test
    public void createFromEmptyArray() {
        double[] array = new double[] { };

        final double tolerance = 1e-10;
        assertTrue(Bounds.of(array).isNull(), "expect null bounds for empty array");
        assertFalse(Bounds.of(array).isValid(), "expect invalid bounds for empty array");
    }

    @Test
    public void formatValidBounds() {
        String expected = "[16.8..32.9]";
        assertEquals(expected, Bounds.of(16.8, 32.9).format());
    }
}
