package com.primalimited.reliefshading.color;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

/**
 * Build 32-bit opaque color array given nColors and control points.
 */
class PaletteBuilder {

    /**
     * Build 32-bit opaque color array given nColors and control points.
     *
     * @param nColors number of colors for the array
     * @param controlPoints the control points (compute gradients between)
     * @return array of rgb values
     */
    static int[] buildRGB(int nColors, ControlPoint...controlPoints) {
        if (controlPoints.length == 1 || nColors == 1) {
            int[] rgb = new int[nColors];
            Arrays.fill(rgb, controlPoints[0].rgb());
            return rgb;
        }

        int[] rgb = new int[nColors];

        List<ControlPoint> sortedControlPoints = ControlPointComparator
                .getSortedList(controlPoints);
        for (int index = 1; index < sortedControlPoints.size(); index++) {
            ControlPoint first = sortedControlPoints.get(index - 1);
            ControlPoint next = sortedControlPoints.get(index);

            rgb[first.index()] = first.rgb();

            Color firstColor = new Color(first.rgb());
            Color nextColor = new Color(next.rgb());
            int nColorsBetween = next.index() - first.index() - 1;
            if (nColorsBetween > 0) {
                for (int arrayIndex = first.index() + 1, count = 1; arrayIndex < next.index(); arrayIndex++, count++) {
                    float fraction = count / (float)(nColorsBetween + 1);
                    rgb[arrayIndex] = Gradient.computeRGB(fraction, firstColor, nextColor);
                }
            }

            rgb[next.index()] = next.rgb();
        }

        return rgb;
    }
}
