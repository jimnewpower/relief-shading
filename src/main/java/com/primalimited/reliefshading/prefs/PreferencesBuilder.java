package com.primalimited.reliefshading.prefs;

import com.primalimited.reliefshading.bounds.Bounds;

public class PreferencesBuilder {
    private int azimuthDegrees = Preferences.AZIMUTH_DEGREES_DEFAULT;
    private int altitudeDegrees = Preferences.ALTITUDE_DEGREES_DEFAULT;
    private int opacityPercent = Preferences.OPACITY_PERCENT_DEFAULT;

    public PreferencesBuilder() {
    }

    public PreferencesBuilder azimuthDegrees(int azimuthDegrees) {
        this.azimuthDegrees = (int) Bounds.DEGREES.constrain(azimuthDegrees);
        return this;
    }

    public PreferencesBuilder altitudeDegrees(int altitudeDegrees) {
        this.altitudeDegrees = (int) Bounds.ALTITUDE.constrain(altitudeDegrees);
        return this;
    }

    public PreferencesBuilder opacityPercent(int opacityPercent) {
        this.opacityPercent = (int) Bounds.PERCENT.constrain(opacityPercent);
        return this;
    }

    public Preferences build() {
        return Preferences.with(azimuthDegrees, altitudeDegrees, opacityPercent);
    }
}
