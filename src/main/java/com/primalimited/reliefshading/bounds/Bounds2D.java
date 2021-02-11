package com.primalimited.reliefshading.bounds;

import com.primalimited.reliefshading.number.Invalid;

import java.util.Objects;

/**
 * 2D bounding values.
 *
 * @author Jim Newpower
 */
public class Bounds2D {
    private transient Bounds xBounds = Bounds.nullBounds();
    private transient Bounds yBounds = Bounds.nullBounds();

    /**
     * Create empty bounds
     * @return empty bounds
     */
    public static Bounds2D empty() {
        return new Bounds2D();
    }

    /**
     * Create 2D bounding values
     *
     * @param x x bounds
     * @param y y bounds
     * @return instance of Bounds2D
     */
    public static Bounds2D create(Bounds x, Bounds y) {
        Objects.requireNonNull(x, "x bounds cannot be null");
        Objects.requireNonNull(y, "y bounds cannot be null");
        return new Bounds2D(x, y);
    }

    private Bounds2D() {
    }

    private Bounds2D(Bounds x, Bounds y) {
        this.xBounds = Bounds.of(x.min(), x.max());
        this.yBounds = Bounds.of(y.min(), y.max());
    }

    @Override
    public String toString() {
        String x = xBounds.format();
        String y = yBounds.format();
        return getClass().getSimpleName() + " x=" + x + ", y=" + y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bounds2D bounds2D = (Bounds2D) o;
        return xBounds.equals(bounds2D.xBounds) && yBounds.equals(bounds2D.yBounds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(xBounds, yBounds);
    }

    /**
     * Returns true if the two rectangles have no intersections
     *
     * @param bounds bounds to test
     * @return true if the two rectangles have no intersections, false otherwise
     */
    public boolean disjoint(Bounds2D bounds) {
        Objects.requireNonNull(bounds);
        // only need 1 of x or y to be disjoint
        return xBounds.disjoint(bounds.xBounds) || yBounds.disjoint(bounds.yBounds);
    }

    /**
     * Return the maximum y value
     * @return the maximum y value
     */
    public double maxY() {
        return yBounds.max();
    }

    /**
     * Return the minimum y value
     * @return the minimum y value
     */
    public double minY() {
        return yBounds.min();
    }

    /**
     * Return the maximum x value
     * @return the maximum x value
     */
    public double maxX() {
        return xBounds.max();
    }

    /**
     * Return the minimum x value
     * @return the minimum x value
     */
    public double minX() {
        return xBounds.min();
    }

    /**
     * Return true if both x and y are within bounds, false otherwise.
     *
     * @param x x location
     * @param y y location
     * @return true if both x and y are within bounds, false otherwise.
     */
    public boolean contains(double x, double y) {
        if (!isValid())
            return false;

        return xBounds.contains(x) && yBounds.contains(y);
    }

    /**
     * @return width (maxX - minX)
     */
    public double width() {
        if (!xBounds.isValid())
            return Invalid.INVALID_DOUBLE;
        return xBounds.range();
    }

    /**
     * @return height (maxY - minY)
     */
    public double height() {
        if (!yBounds.isValid())
            return Invalid.INVALID_DOUBLE;
        return yBounds.range();
    }

    /**
     * Determine the histogram bin for the given x value and the
     * specified number of bins.
     *
     * @param x the x value
     * @param nBins the number of bins
     * @return which histogram bin the x value lies in, or -1 if x is out of bounds.
     */
    public int histogramBinX(double x, int nBins) {
        return xBounds.histogramBin(x, nBins);
    }

    /**
     * Determine the histogram bin for the given y value and the
     * specified number of bins.
     *
     * @param y the y value
     * @param nBins the number of bins
     * @return which histogram bin the y value lies in, or -1 if y is out of bounds.
     */
    public int histogramBinY(double y, int nBins) {
        return yBounds.histogramBin(y, nBins);
    }

    /**
     * @return false if any value (minX, minY, maxX, maxY) is == Dval.DVAL_DOUBLE,
     * OR if minX &gt;= maxX OR minY &gt;= maxY, true otherwise.
     */
    public boolean isValid() {
        return xBounds.isValid() && yBounds.isValid();
    }
}