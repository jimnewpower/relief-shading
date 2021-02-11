package com.primalimited.reliefshading.color;

import java.util.Objects;

/**
 * Control points are used as interpolation end points for color palettes.
 */
public class ControlPoint {
    @SuppressWarnings("PMD.AvoidFieldNameMatchingMethodName")
    private transient final int index;

    @SuppressWarnings("PMD.AvoidFieldNameMatchingMethodName")
    private transient final int rgb;

    /**
     * Create a control point.
     *
     * @param index index into the master color array for the color palette
     * @param rgb rgb value for this control point
     */
    public ControlPoint(int index, int rgb) {
        if (index < 0)
            throw new IllegalArgumentException("Index must be >= 0.");

        this.index = index;
        this.rgb = rgb;
    }

    /**
     * Returns the index into the master color array for a color palette.
     * @return the index into the master color array for a color palette.
     */
    public int index() {
        return index;
    }

    /**
     * Return the rgb color value for this control point.
     *
     * @return the rgb color value for this control point.
     */
    public int rgb() {
        return rgb;
    }

    @Override
    public String toString() {
        return "ControlPoint{" +
                "index=" + index +
                ", rgb=" + rgb +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ControlPoint that = (ControlPoint) o;
        return index == that.index && rgb == that.rgb;
    }

    @Override
    public int hashCode() {
        return Objects.hash(index, rgb);
    }
}
