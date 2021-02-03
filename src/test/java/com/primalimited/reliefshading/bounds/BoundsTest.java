package com.primalimited.reliefshading.bounds;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BoundsTest {
    @Test
    public void legalBounds() {
        double min = 3.5;
        double max = 987.654;
        Bounds bounds = Bounds.of(min, max);

        final double tolerance = 1e-10;

        assertAll(
                "Legal Bounds",
                () -> assertEquals(min, bounds.min(), tolerance),
                () -> assertEquals(max, bounds.max(), tolerance)
        );
    }
}
