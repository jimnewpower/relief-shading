package com.primalimited.reliefshading.bounds;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Bounds2DTest {
    @Test
    public void empty() {
        assertFalse(Bounds2D.empty().isValid());
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
        Bounds2D percent = Bounds2D.create(Bounds.PERCENT, Bounds.PERCENT);
        Bounds2D disjoint = Bounds2D.create(Bounds.of(200, 300), Bounds.of(200, 300));
        assertTrue(percent.disjoint(disjoint));
        assertTrue(disjoint.disjoint(percent));
    }
}