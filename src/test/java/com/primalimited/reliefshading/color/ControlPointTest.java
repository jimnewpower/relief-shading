package com.primalimited.reliefshading.color;

import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class ControlPointTest {
    @Test
    public void invalidIndexShouldThrow() {
        assertThrows(IllegalArgumentException.class,
                () -> new ControlPoint(-1, Color.WHITE.getRGB())
        );
    }

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
        assertEquals(controlPoint0, controlPoint0);

        ControlPoint controlPoint1 = new ControlPoint(0, 0);
        assertEquals(controlPoint0, controlPoint1);
        assertTrue(controlPoint0.equals(controlPoint1));
        assertTrue(controlPoint1.equals(controlPoint0));

        // verify prechecks in equals()
        ControlPoint nullPoint = null;
        assertFalse(controlPoint0.equals(nullPoint));
        assertFalse(controlPoint0.equals(Integer.valueOf(3)));
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
    public void testHashCode() {
        // same
        ControlPoint controlPoint0 = new ControlPoint(0, 0);
        ControlPoint controlPoint1 = new ControlPoint(0, 0);
        assertEquals(controlPoint0.hashCode(), controlPoint1.hashCode());

        // different index
        ControlPoint cp0 = new ControlPoint(0, 0);
        ControlPoint cp1 = new ControlPoint(1, 0);
        assertNotEquals(cp0.hashCode(), cp1.hashCode());

        // different color
        ControlPoint c0 = new ControlPoint(0, Color.GRAY.getRGB());
        ControlPoint c1 = new ControlPoint(0, Color.YELLOW.getRGB());
        assertNotEquals(c0.hashCode(), c1.hashCode());

        // different index and color
        ControlPoint p0 = new ControlPoint(0, Color.GRAY.getRGB());
        ControlPoint p1 = new ControlPoint(7, Color.YELLOW.getRGB());
        assertNotEquals(p0.hashCode(), p1.hashCode());
    }

    @Test
    public void testToString() {
        assertEquals("ControlPoint{index=0, rgb=0}", new ControlPoint(0, 0).toString());
    }
}