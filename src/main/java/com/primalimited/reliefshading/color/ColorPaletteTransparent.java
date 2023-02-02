package com.primalimited.reliefshading.color;

import com.primalimited.reliefshading.bounds.Bounds;

import java.awt.*;
import java.util.Objects;

public class ColorPaletteTransparent implements ColorPalette {
    @SuppressWarnings("PMD.AvoidFieldNameMatchingMethodName")
    private transient final int[] rgb;

    ColorPaletteTransparent(ColorPalette colorPalette, int opacityPercent) {
        Objects.requireNonNull(colorPalette, "color palette");

        int alpha8Bit = ColorUtility.percentTo8BitValue(opacityPercent);
        int nColors = colorPalette.nColors();
        this.rgb = new int[nColors];
        for (int index = 0; index < nColors; index++) {
            Color color = new Color(colorPalette.rgb(index));
            Color transparent = new Color(
                    color.getRed(),
                    color.getGreen(),
                    color.getBlue(),
                    alpha8Bit
            );
            rgb[index] = transparent.getRGB();
        }
    }

    @Override
    public int nColors() {
        return rgb.length;
    }

    @Override
    public int rgb(int index) {
        if (index < 0 || index >= nColors())
            throw new IllegalArgumentException("Index is out of range: " + Bounds.of(0, nColors()-1));
        return rgb[index];
    }

    @Override
    public Color color(int index) {
        return new Color(rgb(index));
    }
}
