package com.primalimited.reliefshading.bounds;

import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class Bounds2DTest {
    @Test
    public void empty() {
        assertFalse(Bounds2D.empty().isValid());
    }

    @Test
    public void equalsAndHashCode() {
        // equal
        Bounds2D bounds0 = Bounds2D.create(Bounds.FRACTION, Bounds.FRACTION);
        Bounds2D bounds1 = Bounds2D.create(Bounds.FRACTION, Bounds.FRACTION);
        assertAll("equals() and hashCode() for identical bounds",
                () -> assertTrue(bounds0.equals(bounds1)),
                () -> assertTrue(bounds1.equals(bounds0)),
                () -> assertEquals(bounds0.hashCode(), bounds1.hashCode())
        );

        // not equal
        Bounds2D bounds2 = Bounds2D.create(Bounds.LONGITUDE, Bounds.LATITUDE);
        assertAll("equals() and hashCode() for different bounds",
                () -> assertFalse(bounds0.equals(bounds2)),
                () -> assertFalse(bounds2.equals(bounds1)),
                () -> assertNotEquals(bounds2.hashCode(), bounds1.hashCode())
        );
    }

    @Test
    public void toStringOutput() {
        Bounds2D bounds = Bounds2D.create(Bounds.LONGITUDE, Bounds.LATITUDE);
        assertEquals("Bounds2D x=[-180..180], y=[-90..90]", bounds.toString());
    }

    @Test
    public void valid() {
        Bounds2D bounds = Bounds2D.create(Bounds.PERCENT, Bounds.PERCENT);
        final double tolerance = 1e-10;
        assertAll("Percent bounds",
                () -> assertTrue(bounds.isValid(), "bounds from percent must be valid"),
                () -> assertEquals(0, bounds.minX(), tolerance),
                () -> assertEquals(100, bounds.maxX(), tolerance),
                () -> assertEquals(100, bounds.width(), tolerance),
                () -> assertEquals(0, bounds.minY(), tolerance),
                () -> assertEquals(100, bounds.maxY(), tolerance),
                () -> assertEquals(100, bounds.height(), tolerance)
        );
    }

    @Test
    public void contains() {
        Bounds2D fractionBounds = Bounds2D.create(Bounds.FRACTION, Bounds.FRACTION);
        assertTrue(fractionBounds.contains(Math.random(), Math.random()));

        // does not contain x
        assertFalse(fractionBounds.contains(3, Math.random()));
        // does not contain y
        assertFalse(fractionBounds.contains(Math.random(), 3));
    }

    @Test
    public void disjoint() {
        // disjoint, valid bounds
        Bounds2D percent = Bounds2D.create(Bounds.PERCENT, Bounds.PERCENT);
        Bounds2D disjoint = Bounds2D.create(Bounds.of(200, 300), Bounds.of(200, 300));
        assertTrue(percent.disjoint(disjoint));
        assertTrue(disjoint.disjoint(percent));

        // disjoint, one argument invalid
        assertTrue(percent.disjoint(Bounds2D.empty()));
        assertTrue(Bounds2D.empty().disjoint(percent));

        // not disjoint, identical
        Bounds2D bounds0 = Bounds2D.create(Bounds.of(0, 10), Bounds.of(0, 10));
        Bounds2D bounds1 = Bounds2D.create(Bounds.of(0, 10), Bounds.of(0, 10));
        assertFalse(bounds0.disjoint(bounds1));
        assertFalse(bounds1.disjoint(bounds0));

        // not disjoint
        bounds0 = Bounds2D.create(Bounds.of(0, 10), Bounds.of(0, 10));
        bounds1 = Bounds2D.create(Bounds.of(-5, 5), Bounds.of(9, 80));
        assertFalse(bounds0.disjoint(bounds1));
        assertFalse(bounds1.disjoint(bounds0));
    }

    @Test
    public void histogramBinX() {
        Bounds2D percent = Bounds2D.create(Bounds.PERCENT, Bounds.PERCENT);
        IntStream.range(0, 100)
                .asDoubleStream()
                .forEach(x -> assertEquals((int)x, percent.histogramBinX(x, 100)));
    }

    @Test
    public void histogramBinY() {
        Bounds2D percent = Bounds2D.create(Bounds.PERCENT, Bounds.PERCENT);
        IntStream.range(0, 100)
                .asDoubleStream()
                .forEach(y -> assertEquals((int)y, percent.histogramBinY(y, 100)));
    }
}