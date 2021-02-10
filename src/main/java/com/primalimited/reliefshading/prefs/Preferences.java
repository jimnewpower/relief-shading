package com.primalimited.reliefshading.prefs;

import com.primalimited.reliefshading.bounds.Bounds;

import java.util.Objects;

@SuppressWarnings("PMD.AvoidFieldNameMatchingMethodName")
public class Preferences {
    private static final int AZIMUTH_DEFAULT = 315;
    private static final int ALTITUDE_DEFAULT = 45;
    private static final int OPACITY_PERCENT_DEFAULT = 70;

    public static Preferences createDefault() {
        return new Preferences(AZIMUTH_DEFAULT, ALTITUDE_DEFAULT, OPACITY_PERCENT_DEFAULT);
    }

    public static Preferences with(int azimuth, int altitude, int opacityPercent) {
        return new Preferences(azimuth, altitude, opacityPercent);
    }

    private final transient int azimuth;
    private final transient int altitude;
    private final transient int opacityPercent;

    private Preferences(int azimuth, int altitude, int opacityPercent) {
        Bounds bounds = Bounds.DEGREES;
        if (!bounds.contains(azimuth))
            throw new IllegalArgumentException("Azimuth must be within " + bounds.format());

        bounds = Bounds.of(0, 90);
        if (!bounds.contains(altitude))
            throw new IllegalArgumentException("Altitude must be within " + bounds.format());

        bounds = Bounds.PERCENT;
        if (!bounds.contains(opacityPercent))
            throw new IllegalArgumentException("Opacity percent must be within " + bounds.format());

        this.azimuth = azimuth;
        this.altitude = altitude;
        this.opacityPercent = opacityPercent;
    }

    public int azimuth() {
        return azimuth;
    }

    public int altitude() {
        return altitude;
    }

    public int opacityPercent() {
        return opacityPercent;
    }

    @Override
    public String toString() {
        return "Preferences{" +
                "azimuth=" + azimuth +
                ", altitude=" + altitude +
                ", opacityPercent=" + opacityPercent +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Preferences that = (Preferences) o;
        return azimuth == that.azimuth && altitude == that.altitude && opacityPercent == that.opacityPercent;
    }

    @Override
    public int hashCode() {
        return Objects.hash(azimuth, altitude, opacityPercent);
    }
}
