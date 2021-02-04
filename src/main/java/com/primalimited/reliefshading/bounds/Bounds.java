package com.primalimited.reliefshading.bounds;

import com.primalimited.reliefshading.number.Invalid;

import java.text.NumberFormat;
import java.util.Arrays;
import java.util.DoubleSummaryStatistics;
import java.util.Objects;

/**
 * Represents bounding values (min, max).  Valid bounds are min &lt;= max and range &gt;= 0.
 *
 * @author Jim Newpower
 */
public interface Bounds {
    /**
     * Display text for invalid values.
     */
    String UNKNOWN_VALUE_TEXT = "Unknown";

    /**
     * Fraction bounds.
     */
    Bounds FRACTION = Bounds.of(0, 1);

    /**
     * Percent bounds.
     */
    Bounds PERCENT = Bounds.of(0, 100);

    /**
     * Spherical degrees bounds.
     */
    Bounds DEGREES = Bounds.of(0, 360);

    /**
     * Latitude bounds.
     */
    Bounds LATITUDE = Bounds.of(-90, 90);

    /**
     * Longitude bounds.
     */
    Bounds LONGITUDE = Bounds.of(-180, 180);

    /**
     * 8-bit color component bounds.
     */
    Bounds UNSIGNED_BYTE_BOUNDS = Bounds.of(0, 255);
    Bounds RGB_8_BIT = UNSIGNED_BYTE_BOUNDS;

    /**
     * Create bounds from min and max values.
     *
     * @param min the minimum value
     * @param max the maximum value
     * @return bounds if min &lt;= max
     * @throws IllegalArgumentException if min &gt; max or either min or max is Invalid
     */
    static Bounds of(double min, double max) {
        return immutable(min, max);
    }

    /**
     * Create bounds from array of values, ignoring any invalid values in the array.
     *
     * @param arrayParam array of values
     * @return bounding values of the array, or null bounds if no valid values found.
     */
    static Bounds of(double[] arrayParam) {
        double[] array = Objects.requireNonNull(arrayParam, "array cannot be null");
        if (array.length == 0)
            return Bounds.nullBounds();

        DoubleSummaryStatistics stats = Arrays
                .stream(array)
                .filter(Invalid.VALID_DOUBLE)
                .summaryStatistics();

        double min = stats.getMin();
        double max = stats.getMax();

        if (!Bounds.valid(min, max))
            return Bounds.nullBounds();

        return immutable(min, max);
    }

    static Bounds immutable(double min, double max) {
        return ImmutableBounds.of(min, max);
    }

    static Bounds nullBounds() {
        return new NullBounds();
    }

    static boolean valid(double min, double max) {
        if (Invalid.test(min))
            return false;
        if (Invalid.test(max))
            return false;

        return min <= max;
    }

    /**
     * Return the minimum value of this bounds.
     * @return the minimum value of this bounds.
     */
    double min();

    /**
     * Return the maximum value of this bounds.
     * @return the maximum value of this bounds.
     */
    double max();

    /**
     * Return the range of that this bounds covers.
     * @return the range of that this bounds covers.
     */
    double range();

    /**
     * Return formatted text for the bounds.
     * @return formatted text for the bounds.
     */
    default String format() {
        NumberFormat nf = NumberFormat.getInstance();
        String min = Invalid.test(min()) ? UNKNOWN_VALUE_TEXT : nf.format(min());
        String max = Invalid.test(max()) ? UNKNOWN_VALUE_TEXT : nf.format(max());
        return "[" + min + ".." + max + "]";
    }

    /**
     * Validate arguments e.g. prior to creating bounds.
     *
     * @param min minimum value
     * @param max maximum value
     * @throws IllegalArgumentException if min &gt; max or if either min or max is invalid.
     */
    default void validateArguments(double min, double max) {
        if (Invalid.test(min))
            throw new IllegalArgumentException("min is invalid (" + min + ")");
        if (Invalid.test(max))
            throw new IllegalArgumentException("max is invalid (" + max + ")");
        if (min > max)
            throw new IllegalArgumentException("min (" + min + ") > max (" + max + ")");
    }

    /**
     * Return true if bounds are valid (min &lt;= max), false otherwise.
     * @return true if bounds are valid (min &lt;= max), false otherwise.
     */
    default boolean isValid() {
        return valid(min(), max());
    }

    /**
     * For valid bounds, return true if min == max, false otherwise.
     * @return true if bounds is valid and min == max, false otherwise.
     */
    default boolean rangeIsZero() {
        return isValid() && Double.compare(min(), max()) == 0;
    }

    /**
     * Determine a discrete bin number for a value, given nBins for range; useful for
     * histograms, color scales, value windows, etc.
     *
     * @param value value
     * @param nBins number of bins for the range
     * @return the bin, if value is within range, -1 otherwise
     */
    default int histogramBin(double value, int nBins) {
        if (!isValid())
            return -1;
        if (nBins <= 0 || Invalid.test(nBins))
            return -1;
        if (value < min() || value > max())
            return -1;

        if (Double.compare(max(), value) == 0)
            return nBins - 1;

        double numerator = value - min();
        double denominator = range() / nBins;
        return (int) (numerator / denominator);
    }

    /**
     * Return true if bounds are invalid and either min or max is invalid.
     * @return true if bounds are invalid and either min or max is invalid.
     */
    default boolean isNull() {
        return Invalid.test(min()) && Invalid.test(max()) && !isValid();
    }

    /**
     * Return true if value is within bounds (e.g. min &lt;= value &lt;= max), false otherwise.
     * @param value value to evaluate against this bounds
     * @return true if value is within bounds (e.g. min &lt;= value &lt;= max), false otherwise.
     */
    default boolean contains(double value) {
        return min() <= value && value <= max();
    }

    /**
     * Bind value to bounds, i.e. if value &lt; min, return min, if value &gt; max return max, otherwise return value.
     * @param value value to bind
     * @return if value &lt; min, return min, if value &gt; max return max, otherwise return value.
     */
    default double bind(double value) {
        return Math.min(max(), Math.max(min(), value));
    }
}
