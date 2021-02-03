package com.primalimited.reliefshading.bounds;

import com.primalimited.reliefshading.number.Invalid;

import java.util.Objects;

/**
 * 2D bounding values.
 *
 * @author Jim Newpower
 */
public class Bounds2D {
  /* instance variables */
  protected Bounds xBounds = Bounds.empty();
  protected Bounds yBounds = Bounds.empty();

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

  protected Bounds2D(Bounds2D from) {
    if (from.xBounds.isValid())
      this.xBounds = Bounds.immutable(from.xBounds.min(), from.xBounds.max());
    if (from.yBounds.isValid())
      this.yBounds = Bounds.immutable(from.yBounds.min(), from.yBounds.max());
  }

  @Override public String toString() {
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
    if (!isValid())
      return false;
    if (!bounds.isValid())
      return false;

    if (contains(bounds))
      return false;
    if (bounds.contains(this))
      return false;

    boolean outsideX = false;
    if (
      (bounds.minX() < minX() && bounds.maxX() < minX()) ||
      (bounds.minX() > maxX() && bounds.maxX() > maxX())
    ) {
      outsideX = true;
    }

    boolean outsideY = false;
    if (
      (bounds.minY() < minY() && bounds.maxY() < minY()) ||
      (bounds.minY() > maxY() && bounds.maxY() > maxY())
    ) {
      outsideY = true;
    }

    return outsideX || outsideY;
  }

  private double maxY() {
    return yBounds.max();
  }

  private double minY() {
    return yBounds.min();
  }

  private double maxX() {
    return xBounds.max();
  }

  private double minX() {
    return xBounds.min();
  }

  /**
   * Returns true if and only if bounds arg is entirely inside this bounds
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
      return(false);

    if (x < minX())
      return(false);
    if (x > maxX())
      return(false);
    if (y < minY())
      return(false);
    if (y > maxY())
      return(false);

    return(true);
  }

  /**
   * @return width (maxX - minX)
   */
  public double getWidth() {
    if (!xBounds.isValid())
      return Invalid.INVALID_DOUBLE;
    return xBounds.range();
  }

  /**
   * @return height (maxY - minY)
   */
  public double getHeight() {
    if (!yBounds.isValid())
      return Invalid.INVALID_DOUBLE;
    return yBounds.range();
  }

  public double ratioXY() {
    if (!isValid() || Double.compare(0.0, getHeight()) == 0)
      return Invalid.INVALID_DOUBLE;
    return getWidth() / getHeight();
  }

  public double ratioYX() {
    if (!isValid() || Double.compare(0.0, getWidth()) == 0)
      return Invalid.INVALID_DOUBLE;
    return getHeight() / getWidth();
  }

  public double getMidpointX() {
    if (!xBounds.isValid())
      return Invalid.INVALID_DOUBLE;
    double midX = minX() + (getWidth() / 2.0);
    return midX;
  }

  public double getMidpointY() {
    if (!yBounds.isValid())
      return Invalid.INVALID_DOUBLE;
    double midY = minY() + (getHeight() / 2.0);
    return midY;
  }

  /**
   * @return false if any value (minX, minY, maxX, maxY) is == Dval.DVAL_DOUBLE,
   * OR if minX &gt;= maxX OR minY &gt;= maxY, true otherwise.
   */
  public boolean isValid() {
    return xBounds.isValid() && yBounds.isValid();
  }

  public boolean isDefault() {
    if (
        Double.compare(minX(), EmptyBounds.DEFAULT_VALUE) == 0
        && Double.compare(maxX(), -EmptyBounds.DEFAULT_VALUE) == 0
        && Double.compare(minY(), EmptyBounds.DEFAULT_VALUE) == 0
        && Double.compare(maxY(), -EmptyBounds.DEFAULT_VALUE) == 0
      )
      return true;
    return false;
  }
}