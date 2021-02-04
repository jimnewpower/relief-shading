package com.primalimited.reliefshading.bounds;

import java.util.Objects;

/**
 * Immutable implementation of Bounds.
 */
final class ImmutableBounds implements Bounds {
    private final double min;
    private final double max;
    private final double range;

    static ImmutableBounds of(double min, double max) {
        return new ImmutableBounds(min, max);
    }

    static ImmutableBounds of(Bounds other) {
        Objects.requireNonNull(other);
        return new ImmutableBounds(other);
    }

    private ImmutableBounds(double min, double max) {
        validateArguments(min, max);
        this.min = min;
        this.max = max;
        this.range = max - min;
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
        return min;
    }

    @Override
    public double max() {
        return max;
    }

    @Override
    public double range() {
        return range;
    }
}
