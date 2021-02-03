package com.primalimited.reliefshading.bounds;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Bounds2DTest {
    @Test
    public void empty() {
        assertFalse(Bounds.empty().isValid());
    }
}