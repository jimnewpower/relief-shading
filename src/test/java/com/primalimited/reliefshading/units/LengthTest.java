package com.primalimited.reliefshading.units;

import com.primalimited.reliefshading.number.Invalid;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LengthTest {
    @Test
    public void meters() {
        final double delta = 1e-5;

        assertEquals(1, Length.METERS.from(1).to(Length.METERS), delta);
        assertEquals(4, Length.METERS.from(4000).to(Length.KILOMETERS), delta);
        assertEquals(3.28084, Length.METERS.from(1).to(Length.FEET), delta);
        assertEquals(1, Length.METERS.from(1609.34).to(Length.MILES), delta);
    }

    @Test
    public void kilometers() {
        final double delta = 1e-5;

        assertEquals(1, Length.KILOMETERS.from(1).to(Length.KILOMETERS), delta);
        assertEquals(1000, Length.KILOMETERS.from(1).to(Length.METERS), delta);
        assertEquals(3280.83989, Length.KILOMETERS.from(1).to(Length.FEET), delta);
        assertEquals(0.621371, Length.KILOMETERS.from(1).to(Length.MILES), delta);
    }

    @Test
    public void feet() {
        final double delta = 1e-5;

        assertEquals(1, Length.FEET.from(1).to(Length.FEET), delta);
        assertEquals(0.3048, Length.FEET.from(1).to(Length.METERS), delta);
        assertEquals(0.0003048, Length.FEET.from(1).to(Length.KILOMETERS), 1e-7);
        assertEquals(0.0001894, Length.FEET.from(1).to(Length.MILES), 1e-7);
    }

    @Test
    public void miles() {
        final double delta = 1e-5;

        assertEquals(1, Length.MILES.from(1).to(Length.MILES), delta);
        assertEquals(1609.34, Length.MILES.from(1).to(Length.METERS), delta);
        assertEquals(1.60934, Length.MILES.from(1).to(Length.KILOMETERS), delta);
        assertEquals(5280, Length.MILES.from(1).to(Length.FEET), 0.1);
    }

    @Test
    public void invalid() {
        assertAll("Given invalid input values, the result should be Invalid.",
                () -> assertTrue(
                        Invalid.doubleInstance().invalid(
                            Length.METERS.from(Invalid.INVALID_DOUBLE).to(Length.METERS)
                        )
                ),
                () -> assertTrue(
                        Invalid.doubleInstance().invalid(
                                Length.METERS.from(Double.MAX_VALUE).to(Length.METERS)
                        )
                ),
                () -> assertTrue(
                        Invalid.doubleInstance().invalid(
                                Length.METERS.from(Double.NaN).to(Length.METERS)
                        )
                ),
                () -> assertTrue(
                        Invalid.doubleInstance().invalid(
                                Length.METERS.from(Double.POSITIVE_INFINITY).to(Length.METERS)
                        )
                ),
                () -> assertTrue(
                        Invalid.doubleInstance().invalid(
                                Length.METERS.from(Double.NEGATIVE_INFINITY).to(Length.METERS)
                        )
                ),
                () -> assertTrue(
                        Invalid.doubleInstance().invalid(
                                Length.KILOMETERS.from(Invalid.INVALID_DOUBLE).to(Length.METERS)
                        )
                ),
                () -> assertTrue(
                        Invalid.doubleInstance().invalid(
                                Length.FEET.from(Invalid.INVALID_DOUBLE).to(Length.METERS)
                        )
                ),
                () -> assertTrue(
                        Invalid.doubleInstance().invalid(
                                Length.MILES.from(Invalid.INVALID_DOUBLE).to(Length.METERS)
                        )
                )
        );
    }
}