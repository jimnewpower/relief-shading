package com.primalimited.reliefshading.bounds;

import java.util.Objects;

/**
 * Immutable implementation of Bounds.
 */
final class ImmutableBounds implements Bounds {
    private final double minimum;
    private final double maximum;

    static ImmutableBounds of(double minimum, double maximum) {
        return new ImmutableBounds(minimum, maximum);
    }

    private ImmutableBounds(double minimum, double maximum) {
        validateArguments(minimum, maximum);
        this.minimum = minimum;
        this.maximum = maximum;
    }

    private ImmutableBounds(Bounds bounds) {
        this(bounds.min(), bounds.max());
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " " + format();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;

        if (this == obj)
            return true;

        if (obj instanceof Bounds) {
            Bounds other = (Bounds) obj;
            if (Double.compare(this.min(), other.min()) == 0
                    && Double.compare(this.max(), other.max()) == 0)
                return true;
        }

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(min(), max());
    }

    @Override
    public double min() {
        return minimum;
    }

    @Override
    public double max() {
        return maximum;
    }

    @Override
    public double range() {
        return max() - min();
    }
}
