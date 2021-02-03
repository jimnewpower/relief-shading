package com.primalimited.reliefshading.bounds;

import com.primalimited.reliefshading.number.Invalid;

import java.text.NumberFormat;
import java.util.Arrays;
import java.util.DoubleSummaryStatistics;
import java.util.Objects;
import java.util.OptionalDouble;
import java.util.stream.DoubleStream;

/**
 * Represents bounding values (min, max).  Valid bounds are min &lt;= max and range &gt;= 0.
 *
 * @author Jim Newpower
 */
public interface Bounds {
  static final String UNKNOWN_VALUE_TEXT = "Unknown";
  
  public static final Bounds FRACTION = Bounds.of(0, 1);
  public static final Bounds PERCENT = Bounds.of(0, 100);
  public static final Bounds DEGREES = Bounds.of(0, 360);
  public static final Bounds LATITUDE = Bounds.of(-90, 90);
  public static final Bounds LONGITUDE = Bounds.of(-180, 180);
  public static final Bounds RGB_8_BIT = Bounds.of(0, 255);

  public static Bounds of(double min, double max) {
    return immutable(min, max);
  }

  public static Bounds immutable(double min, double max) {
    return ImmutableBounds.of(min, max);
  }

  public static Bounds empty() {
    return EmptyBounds.create();
  }

  public static Bounds nullBounds() {
    return new NullBounds();
  }

  public static Bounds of(double[] arrayParam) {
    double[] array = Objects.requireNonNull(arrayParam, "array cannot be null");
    if (arrayParam == null || arrayParam.length == 0)
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

  public static boolean valid(double min, double max) {
    if (Invalid.test(min))
      return false;
    if (Invalid.test(max))
      return false;
    if (min > max)
      return false;
    return true;
  }

  /**
   * Determine the common bounds for a set of bounds
   *
   * @param all set of bounds
   * @return new instance of Bounds that represents the least common bounds for all
   */
  public static Bounds common(Bounds...all) {
    Objects.requireNonNull(all);
    if (all.length == 0)
      throw new IllegalArgumentException();

    if (!allOverlap(all))
      throw new IllegalArgumentException("not all bounds overlap");

    OptionalDouble highestMin =
        Arrays.stream(all).flatMapToDouble(b -> DoubleStream.of(b.min())).max();
    OptionalDouble lowestMax =
        Arrays.stream(all).flatMapToDouble(b -> DoubleStream.of(b.max())).min();
    return of(highestMin.getAsDouble(), lowestMax.getAsDouble());
  }

  static boolean allOverlap(Bounds...all) {
    Objects.requireNonNull(all);
    if (all.length == 0)
      throw new IllegalArgumentException();

    for (Bounds bounds : all) {
      if (!Arrays.stream(all).allMatch(b -> b.overlaps(bounds)))
        return false;
    }
    return true;
  }

  double min();
  double max();
  double range();

  default String text() {
    NumberFormat nf = NumberFormat.getInstance();
    String min = Invalid.test(min()) ? UNKNOWN_VALUE_TEXT : nf.format(min());
    String max = Invalid.test(max()) ? UNKNOWN_VALUE_TEXT : nf.format(max());
    return "[" + min + ".." + max + "]";
  }

  default void validateArguments(double min, double max) {
    if (Invalid.test(min))
      throw new IllegalArgumentException("min is invalid (" + min + ")");
    if (Invalid.test(max))
      throw new IllegalArgumentException("max is invalid (" + max + ")");
    if (min > max)
      throw new IllegalArgumentException("min (" + min + ") > max (" + max + ")");
  }

  default boolean isValid() {
    return valid(min(), max());
  }

  default boolean rangeIsZero() {
    return isValid() && Double.compare(min(), max()) == 0;
  }

  default boolean overlaps(Bounds other) {
    return (min() >= other.min() && min() <= other.max())
        || (max() >= other.min() && max() <= other.max())
        || (other.min() >= min() && other.min() <= max())
        || (other.max() >= min() && other.max() <= max());
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
    if (nBins <= 0 || !Invalid.test(nBins))
      return -1;
    if (value < min() || value > max())
      return -1;
    int bin = (int)Math.floor(getFractionBetween(value) * (nBins));
    return Math.max(0, Math.min(nBins-1, bin));
  }

  /**
   * Get fraction between bounds for value
   *
   * @param value value
   * @return fraction between bounds endpoints, or Dval if value not within bounds
   */
  default double getFractionBetween(double value) {
    if (value < min() || value > max())
      return Invalid.INVALID_DOUBLE;
    if (Double.compare(min(), max()) == 0) {
      if (Double.compare(min(), value) == 0)
        return 0.0;
      return Invalid.INVALID_DOUBLE;
    }
    return (value - min()) / range();
  }

  default String format() {
    return String.format(
      "min=%s max=%s range=%s",
      Invalid.test(min()) ? UNKNOWN_VALUE_TEXT : String.format("%10.3f", min()),
      Invalid.test(max()) ? UNKNOWN_VALUE_TEXT : String.format("%10.3f", max()),
      Invalid.test(range()) ? UNKNOWN_VALUE_TEXT : String.format("%10.3f", range())
    );
  }

  default boolean isNull() {
    return Invalid.test(min()) && Invalid.test(max()) && !isValid();
  }

  default boolean contains(double value) {
    if (value >= min() && value <= max())
      return true;
    return false;
  }

  default double bound(double value) {
    return Math.min(max(), Math.max(min(), value));
  }

  default int bound(int value) {
    return (int)Math.min(max(), Math.max(min(), value));
  }
}
