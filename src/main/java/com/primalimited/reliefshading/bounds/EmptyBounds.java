package com.primalimited.reliefshading.bounds;

import com.primalimited.reliefshading.number.Invalid;

final class EmptyBounds implements Bounds {
  static final double DEFAULT_VALUE = Invalid.INVALID_DOUBLE;

  static EmptyBounds create() {
    return new EmptyBounds();
  }

  private EmptyBounds() {
  }

  @Override
  public String toString() {
    String text = "[null..null]";
    return getClass().getSimpleName() + " " + text;
  }

  @Override
  public double min() {
    return DEFAULT_VALUE;
  }

  @Override
  public double max() {
    return DEFAULT_VALUE;
  }

  @Override
  public double range() {
    return Double.MAX_VALUE;
  }

  @Override
  public boolean isValid() {
    return false;
  }
}
