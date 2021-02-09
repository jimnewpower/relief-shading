package com.primalimited.reliefshading.color;

import java.util.Objects;

public class ControlPoint {
    private final int index;
    private final int rgb;

    public ControlPoint(int index, int rgb) {
        this.index = index;
        this.rgb = rgb;
    }

    public int index() {
        return index;
    }

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
