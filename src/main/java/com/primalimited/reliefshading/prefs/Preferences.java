package com.primalimited.reliefshading.prefs;

import com.primalimited.reliefshading.bounds.Bounds;

import java.util.Objects;

@SuppressWarnings("PMD.AvoidFieldNameMatchingMethodName")
public class Preferences {
    private static final int AZIMUTH_DEGREES_DEFAULT = 315;
    private static final int ALTITUDE_DEGREES_DEFAULT = 45;
    private static final int OPACITY_PERCENT_DEFAULT = 70;

    public static Preferences createDefault() {
        return new Preferences(AZIMUTH_DEGREES_DEFAULT, ALTITUDE_DEGREES_DEFAULT, OPACITY_PERCENT_DEFAULT);
    }

    public static Preferences with(int azimuthDegrees, int altitudeDegrees, int opacityPercent) {
        return new Preferences(azimuthDegrees, altitudeDegrees, opacityPercent);
    }

    private final transient int azimuthDegrees;
    private final transient int altitudeDegrees;
    private final transient int opacityPercent;

    private Preferences(int azimuthDegrees, int altitudeDegrees, int opacityPercent) {
        Bounds bounds = Bounds.DEGREES;
        if (!bounds.contains(azimuthDegrees))
            throw new IllegalArgumentException("Azimuth must be within " + bounds.format());

        bounds = Bounds.of(0, 90);
        if (!bounds.contains(altitudeDegrees))
            throw new IllegalArgumentException("Altitude must be within " + bounds.format());

        bounds = Bounds.PERCENT;
        if (!bounds.contains(opacityPercent))
            throw new IllegalArgumentException("Opacity percent must be within " + bounds.format());

        this.azimuthDegrees = azimuthDegrees;
        this.altitudeDegrees = altitudeDegrees;
        this.opacityPercent = opacityPercent;
    }

    public int azimuthDegrees() {
        return azimuthDegrees;
    }

    public int altitudeDegrees() {
        return altitudeDegrees;
    }

    public int opacityPercent() {
        return opacityPercent;
    }

    @Override
    public String toString() {
        return "Preferences{" +
                "azimuth=" + azimuthDegrees +
                ", altitude=" + altitudeDegrees +
                ", opacityPercent=" + opacityPercent +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Preferences that = (Preferences) o;
        return azimuthDegrees == that.azimuthDegrees && altitudeDegrees == that.altitudeDegrees && opacityPercent == that.opacityPercent;
    }

    @Override
    public int hashCode() {
        return Objects.hash(azimuthDegrees, altitudeDegrees, opacityPercent);
    }
}
