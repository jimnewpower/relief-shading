package com.primalimited.reliefshading.color;

import com.primalimited.reliefshading.bounds.Bounds;
import com.primalimited.reliefshading.number.Invalid;

import java.awt.*;
import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Package-level (default) implementation of ColorPalette.
 *
 * Given nColors and set of control points, build array of colors,
 * interpolating between the control points.
 */
class ColorPaletteImpl implements ColorPalette {
    private final int nColors;
    private final ControlPoint[] controlPoints;
    private int[] rgb;

    ColorPaletteImpl(int nColors, ControlPoint...controlPoints) {
        if (nColors <= 0 || Invalid.intInstance().invalid(nColors))
            throw new IllegalArgumentException("nColors must be > 0");

        this.nColors = nColors;
        this.controlPoints = Objects.requireNonNull(controlPoints, "control points");
        validateControlPoints();

        rgb = PaletteBuilder.buildRGB(nColors, controlPoints);
    }

    private void validateControlPoints() {
        if (controlPoints.length == 0)
            throw new IllegalStateException("Must have at least 1 control point.");

        // 1 control point is allowed, which will result in a palette of all the same color
        if (controlPoints.length > 1) {
            // must define endpoints
            IntSummaryStatistics statistics = Arrays
                    .stream(controlPoints)
                    .mapToInt(cp -> cp.index())
                    .summaryStatistics();
            if (statistics.getMin() != 0)
                throw new IllegalStateException("Must have a control point with index == 0.");
            if (statistics.getMin() != nColors-1)
                throw new IllegalStateException("Must have a control point with index == " + (nColors-1) + ".");
        }
    }

    @Override
    public int nColors() {
        return nColors;
    }

    @Override
    public int rgb(int index) {
        if (index < 0 || index >= nColors)
            throw new IllegalArgumentException("Index is out of range: " + Bounds.of(0, nColors-1));
        return rgb[index];
    }
}
