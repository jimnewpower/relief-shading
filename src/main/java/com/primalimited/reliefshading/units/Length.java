package com.primalimited.reliefshading.units;

import com.primalimited.reliefshading.number.Invalid;

/**
 * Length unit definitions and converters.
 */
public enum Length {
    METERS(1),
    KILOMETERS(1000),
    FEET(0.3048),
    MILES(1609.34);

    public final double metersPerUnit;

    // value to convert
    private double value;

    Length(double metersPerUnit) {
        this.metersPerUnit = metersPerUnit;
    }

    /**
     * Set up a conversion from a value.
     *
     * @param value value to convert
     * @return the unit
     */
    public Length from(double value) {
        this.value = value;
        return this;
    }

    /**
     * Complete a conversion after first calling from().
     *
     * @param unit unit to convert the value to.
     * @return the converted value.
     */
    public double to(Length unit) {
        if (Invalid.doubleInstance().invalid(value))
            return value;

        double factor = metersPerUnit / unit.metersPerUnit;
        return value * factor;
    }
}
