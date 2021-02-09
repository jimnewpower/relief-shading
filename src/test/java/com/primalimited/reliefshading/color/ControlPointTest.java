package com.primalimited.reliefshading.color;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ControlPointTest {
    @Test
    public void index() {
        ControlPoint controlPoint = new ControlPoint(0, 0);
        assertAll("ControlPoint constructor",
                () -> assertEquals(0, controlPoint.index()),
                () -> assertEquals(0, controlPoint.rgb()));
    }

    @Test
    public void testEquals() {
        ControlPoint controlPoint0 = new ControlPoint(0, 0);
        ControlPoint controlPoint1 = new ControlPoint(0, 0);
        assertEquals(controlPoint0, controlPoint1);
        assertTrue(controlPoint0.equals(controlPoint1));
        assertTrue(controlPoint1.equals(controlPoint0));
    }

    @Test
    public void notEquals_dueToDifferingIndex() {
        ControlPoint controlPoint0 = new ControlPoint(0, 0);
        ControlPoint controlPoint1 = new ControlPoint(1, 0);
        assertNotEquals(controlPoint0, controlPoint1);
        assertFalse(controlPoint0.equals(controlPoint1));
        assertFalse(controlPoint1.equals(controlPoint0));
    }

    @Test
    public void notEquals_dueToDifferingRGB() {
        ControlPoint controlPoint0 = new ControlPoint(0, 0);
        ControlPoint controlPoint1 = new ControlPoint(0, 1);
        assertNotEquals(controlPoint0, controlPoint1);
        assertFalse(controlPoint0.equals(controlPoint1));
        assertFalse(controlPoint1.equals(controlPoint0));
    }

    @Test
    public void testToString() {
        assertEquals("ControlPoint{index=0, rgb=0}", new ControlPoint(0, 0).toString());
    }
}