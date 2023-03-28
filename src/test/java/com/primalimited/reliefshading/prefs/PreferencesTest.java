package com.primalimited.reliefshading.prefs;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PreferencesTest {
    @Test
    void defaults() {
        Preferences preferences = Preferences.createDefault();
        assertAll(
                "Default values",
                () -> assertEquals(315, preferences.azimuthDegrees()),
                () -> assertEquals(45, preferences.altitudeDegrees()),
                () -> assertEquals(100, preferences.opacityPercent())
        );
    }

    @Test
    void explicit() {
        int azimuth = 270;
        int altitude = 23;
        int opacityPercent = 50;
        Preferences preferences = Preferences.with(azimuth, altitude, opacityPercent);
        assertAll(
                "Explicit values",
                () -> assertEquals(azimuth, preferences.azimuthDegrees()),
                () -> assertEquals(altitude, preferences.altitudeDegrees()),
                () -> assertEquals(opacityPercent, preferences.opacityPercent())
        );
    }

    @Test
    void invalidAzimuth() {
        int azimuthTooLarge = 365;
        int azimuthTooSmall = -2;
        int altitude = 23;
        int opacityPercent = 50;
        assertAll(
                "Invalid azimuth arguments",
                () -> assertThrows(
                    IllegalArgumentException.class,
                    () -> Preferences.with(azimuthTooSmall, altitude, opacityPercent)
                ),
                () -> assertThrows(
                        IllegalArgumentException.class,
                        () -> Preferences.with(azimuthTooLarge, altitude, opacityPercent)
                )
        );
    }

    @Test
    void invalidAltitude() {
        int azimuth = 310;
        int altitudeTooSmall = -1;
        int altitudeTooLarge = 91;
        int opacityPercent = 33;
        assertAll(
                "Invalid altitude arguments",
                () -> assertThrows(
                        IllegalArgumentException.class,
                        () -> Preferences.with(azimuth, altitudeTooSmall, opacityPercent)
                ),
                () -> assertThrows(
                        IllegalArgumentException.class,
                        () -> Preferences.with(azimuth, altitudeTooLarge, opacityPercent)
                )
        );
    }

    @Test
    void invalidOpacity() {
        int azimuth = 310;
        int altitude = 50;
        int opacityPercentTooSmall = -4;
        int opacityPercentTooLarge = 103;
        assertAll(
                "Invalid opacityPercent arguments",
                () -> assertThrows(
                        IllegalArgumentException.class,
                        () -> Preferences.with(azimuth, altitude, opacityPercentTooSmall)
                ),
                () -> assertThrows(
                        IllegalArgumentException.class,
                        () -> Preferences.with(azimuth, altitude, opacityPercentTooLarge)
                )
        );
    }

    @Test
    void testToString() {
        assertEquals(
                "Preferences{azimuth=315, altitude=45, opacityPercent=100}",
                Preferences.createDefault().toString()
        );
    }

    @Test
    void testEquals() {
        // same values
        assertEquals(Preferences.createDefault(), Preferences.createDefault());

        Preferences defaults = Preferences.createDefault();

        // different values
        int azimuth = 270;
        int altitude = 23;
        int opacityPercent = 50;
        Preferences custom = Preferences.with(azimuth, altitude, opacityPercent);
        assertNotEquals(defaults, custom);

        // identical object reference
        assertEquals(defaults, defaults);

        // null object
        Preferences nullPreferences = null;
        assertNotEquals(defaults, nullPreferences);

        // different type
        assertNotEquals(defaults, Integer.valueOf(10));
    }

    @Test
    void testHashCode() {
        Preferences defaults = Preferences.createDefault();
        int azimuth = 270;
        int altitude = 23;
        int opacityPercent = 50;
        Preferences custom = Preferences.with(azimuth, altitude, opacityPercent);

        assertEquals(defaults.hashCode(), defaults.hashCode());
        assertEquals(custom.hashCode(), custom.hashCode());

        assertNotEquals(defaults.hashCode(), custom.hashCode());
    }
}