package com.primalimited.reliefshading.bounds;

import com.primalimited.reliefshading.number.Invalid;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

public class BoundsTest {
    @Test
    void legalBounds() {
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
    void nonzeroRange() {
        assertEquals(27.42, Bounds.of(1000.12, 1027.54).range(), 1e-10);
    }

    @Test
    void testToString() {
        String expected = "ImmutableBounds [0..100]";
        assertEquals(expected, Bounds.PERCENT.toString());
    }

    @Test
    void immutableBoundsEquals() {
        Bounds bounds0 = Bounds.immutable(0, 5);
        Bounds bounds1 = mockNullBounds();
        assertFalse(bounds0.equals(bounds1));

        Bounds bounds = Bounds.immutable(1, 2);
        assertTrue(bounds.equals(bounds));
    }

    private Bounds mockNullBounds() {
        return null;
    }

    @Test
    void nullBounds() {
        Bounds bounds = Bounds.nullBounds();
        assertEquals("NullBounds [Unknown..Unknown]", bounds.toString());
        assertTrue(Invalid.doubleInstance().invalid(bounds.range()));
    }

    @Test
    void validateArguments() {
        assertThrows(IllegalArgumentException.class,
                () -> Bounds.immutable(Invalid.INVALID_DOUBLE, 0));
        assertThrows(IllegalArgumentException.class,
                () -> Bounds.immutable(0, Invalid.INVALID_DOUBLE));
    }

    @Test
    void testStaticValidFunction() {
        assertFalse(Bounds.valid(Invalid.INVALID_DOUBLE, 0));
        assertFalse(Bounds.valid(0, Invalid.INVALID_DOUBLE));
        assertFalse(Bounds.valid(Invalid.INVALID_DOUBLE, Invalid.INVALID_DOUBLE));
        assertFalse(Bounds.valid(1, 0));
        assertTrue(Bounds.valid(0, 1));
    }

    @Test
    void zeroRange() {
        double min = 10.25;
        double max = 10.25;
        Bounds bounds = Bounds.of(min, max);

        final double tolerance = 1e-10;

        assertAll(
                "Zero Range",
                () -> assertTrue(bounds.rangeIsZero()),
                () -> assertEquals(min, bounds.min(), tolerance),
                () -> assertEquals(max, bounds.max(), tolerance)
        );
    }

    @Test
    void illegalBounds() {
        double min = 10.0;
        double max = 7.0;
        assertThrows(IllegalArgumentException.class, () -> Bounds.of(min, max));
    }

    @Test
    void createFromArray() {
        double[] array = new double[] { 0, 1, 2, 50, 60, 100, 200 };

        final double tolerance = 1e-10;
        assertEquals(0, Bounds.of(array).min(), tolerance);
        assertEquals(200, Bounds.of(array).max(), tolerance);
    }

    @Test
    void createFromArrayWithInvalids() {
        double[] array = new double[] {Invalid.INVALID_DOUBLE, 1, 2, 50, Invalid.INVALID_DOUBLE, 100, Invalid.INVALID_DOUBLE };

        final double tolerance = 1e-10;
        assertEquals(1, Bounds.of(array).min(), tolerance);
        assertEquals(100, Bounds.of(array).max(), tolerance);
    }

    @Test
    void createFromArrayAllInvalid() {
        double[] array = new double[] {Invalid.INVALID_DOUBLE, Invalid.INVALID_DOUBLE };
        assertTrue(Bounds.of(array).isNull());
    }

    @Test
    void createFromEmptyArray() {
        double[] array = new double[] { };

        final double tolerance = 1e-10;
        assertTrue(Bounds.of(array).isNull(), "expect null bounds for empty array");
        assertFalse(Bounds.of(array).isValid(), "expect invalid bounds for empty array");
    }

    @Test
    void formatValidBounds() {
        String expected = "[16.8..32.9]";
        assertEquals(expected, Bounds.of(16.8, 32.9).format());
    }

    @Test
    void histogramBins100() {
        Bounds bounds = Bounds.of(0, 100);
        int nBins = 100;
        IntStream.range(0, 100).asDoubleStream()
                .forEach(x -> assertEquals((int) x, bounds.histogramBin(x, nBins)));

        assertEquals(0, bounds.histogramBin(0.0, 10));
        assertEquals(0, bounds.histogramBin(7.0, 10));
        assertEquals(1, bounds.histogramBin(11.0, 10));
        assertEquals(2, bounds.histogramBin(25.0, 10));
        assertEquals(9, bounds.histogramBin(90.0, 10));
        assertEquals(9, bounds.histogramBin(97.0, 10));
        assertEquals(9, bounds.histogramBin(100.0, 10));
    }

    @Test
    void histogramBins10() {
        Bounds bounds = Bounds.of(0, 100);
        int nBins = 10;
        for (int index = 0; index < nBins; index++) {
            double value = index * 10.0;
            assertEquals(index, bounds.histogramBin(value, nBins), "value: " + value);

            double random = Math.random();
            if (Double.compare(0.0, random) == 0)
                random += 1e-5;
            if (Double.compare(1.0, random) == 0)
                random -= 1e-5;
            value += random;
            assertEquals(index, bounds.histogramBin(value, nBins), "value: " + value);
        }
    }

    @Test
    void histogramBinsInvalidArgs() {
        Bounds bounds = Bounds.PERCENT;

        assertEquals(-1, Bounds.nullBounds().histogramBin(0, 10));

        assertEquals(-1, bounds.histogramBin(-3.25, 10), "Invalid value argument.");
        assertEquals(-1, bounds.histogramBin(Invalid.INVALID_DOUBLE, 10), "Invalid value argument.");

        assertEquals(-1, bounds.histogramBin(3.25, 0), "Invalid nBins argument.");
        assertEquals(-1, bounds.histogramBin(3.25, Invalid.INVALID_INT), "Invalid nBins argument.");
    }

    @Test
    void contains() {
        assertFalse(Bounds.nullBounds().contains(42));
        assertTrue(Bounds.of(5, 10).contains(6));

        assertFalse(Bounds.of(2, 3).contains(1));
        assertFalse(Bounds.of(2, 3).contains(4));
    }

    @Test
    void disjoint() {
        Bounds bounds0 = Bounds.of(0, 1);
        Bounds bounds1 = Bounds.of(1, 2);
        assertFalse(bounds0.disjoint(bounds1));

        bounds1 = Bounds.of(-1, 0);
        assertFalse(bounds0.disjoint(bounds1));

        bounds1 = Bounds.of(-2, -1);
        assertTrue(bounds0.disjoint(bounds1));

        bounds1 = Bounds.of(3, 4);
        assertTrue(bounds0.disjoint(bounds1));

        assertTrue(Bounds.nullBounds().disjoint(bounds0));
        assertTrue(bounds0.disjoint(Bounds.nullBounds()));
    }

    @Test
    void bind() {
        Bounds bounds = Bounds.of(-100, 100);
        final double tolerance = 1e-10;
        assertEquals(bounds.min(), bounds.constrain(-2293847), tolerance);
        assertEquals(bounds.max(), bounds.constrain(987345), tolerance);
    }
}
