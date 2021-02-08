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

    public static Bounds2D empty() {
        return new Bounds2D();
    }

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

    public double maxY() {
        return yBounds.max();
    }

    public double minY() {
        return yBounds.min();
    }

    public double maxX() {
        return xBounds.max();
    }

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

    public int histogramBinX(double x, int nBins) {
        return xBounds.histogramBin(x, nBins);
    }

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