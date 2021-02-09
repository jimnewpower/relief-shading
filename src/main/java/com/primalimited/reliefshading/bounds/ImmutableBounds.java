package com.primalimited.reliefshading.bounds;

import java.util.Objects;

/**
 * Immutable implementation of Bounds.
 */
final class ImmutableBounds implements Bounds {
    @SuppressWarnings("PMD.AvoidFieldNameMatchingMethodName")
    private transient final double min;

    @SuppressWarnings("PMD.AvoidFieldNameMatchingMethodName")
    private transient final double max;

    static ImmutableBounds of(double min, double max) {
        return new ImmutableBounds(min, max);
    }

    private ImmutableBounds(double min, double max) {
        validateArguments(min, max);
        this.min = min;
        this.max = max;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " " + format();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImmutableBounds that = (ImmutableBounds) o;
        return Double.compare(that.min, min) == 0 && Double.compare(that.max, max) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(min, max);
    }

    @Override
    public double min() {
        return min;
    }

    @Override
    public double max() {
        return max;
    }

    @Override
    public double range() {
        return max() - min();
    }
}
