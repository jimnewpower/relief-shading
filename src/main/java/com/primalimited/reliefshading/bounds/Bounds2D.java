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
        return new Bounds2D(x.min(), x.max(), y.min(), y.max());
    }

    protected Bounds2D() {
    }

    private Bounds2D(double xMin, double xMax, double yMin, double yMax) {
        this.xBounds = Bounds.of(xMin, xMax);
        this.yBounds = Bounds.of(yMin, yMax);
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
     * Returns true if and only if bounds arg is entirely inside this bounds
     *
     * @param bounds bounds
     * @return true if and only if bounds arg is entirely inside this bounds
     */
    public boolean contains(Bounds2D bounds) {
        if (!isValid())
            return false;
        if (!bounds.isValid())
            return false;

        if (bounds.minX() < minX())
            return false;
        if (bounds.maxX() > maxX())
            return false;
        if (bounds.minY() < minY())
            return false;
        if (bounds.maxY() > maxY())
            return false;

        return true;
    }

    public boolean contains(double x, double y) {
        if (!isValid())
            return false;

        return minX() <= x && x <= maxX()
            && minY() <= y && y <= maxY();
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