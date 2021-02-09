package com.primalimited.reliefshading.color;

import com.primalimited.reliefshading.bounds.Bounds;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

class PaletteBuilder {
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
                    double fraction0 = count / (double)(nColorsBetween + 1);
                    double fraction1 = 1.0 - fraction0;

                    int red = (int) Bounds.RGB_8_BIT.bind(
                            Math.round(
                                fraction0 * firstColor.getRed()
                                + fraction1 * nextColor.getRed()
                            )
                    );
                    int green = (int) Bounds.RGB_8_BIT.bind(
                            Math.round(
                                fraction0 * firstColor.getGreen()
                                + fraction1 * nextColor.getGreen()
                            )
                    );
                    int blue = (int) Bounds.RGB_8_BIT.bind(
                            Math.round(
                                fraction0 * firstColor.getBlue()
                                + fraction1 * nextColor.getBlue()
                            )
                    );

                    rgb[arrayIndex] = new Color(red, green, blue).getRGB();
                }
            }

            rgb[next.index()] = next.rgb();
        }

        return rgb;
    }
}
