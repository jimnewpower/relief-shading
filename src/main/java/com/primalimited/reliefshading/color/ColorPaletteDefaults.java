package com.primalimited.reliefshading.color;

import java.awt.*;

/**
 * Defines default color palettes
 */
public enum ColorPaletteDefaults {
    /**
     * DEM palette
     */
    DEM {
        @Override
        public ColorPalette colorPalette() {
            final int nColors = 800;
            return ColorPalette.create(
                    nColors,
                    new ControlPoint(  0, new Color(0, 132, 53).getRGB()),
                    new ControlPoint(100, new Color(51, 204, 0).getRGB()),
                    new ControlPoint(200, new Color(244, 240, 113).getRGB()),
                    new ControlPoint(400, new Color(244, 189, 69).getRGB()),
                    new ControlPoint(600, new Color(153, 100, 43).getRGB()),
                    new ControlPoint(nColors-1, new Color(255, 255, 255).getRGB())
            );
        }
    },
    /**
     * Grayscale palette for relief-shading.
     */
    RELIEF_SHADE {
        @Override
        public ColorPalette colorPalette() {
            final int nColors = 256;
            return ColorPalette.create(
                    nColors,
                    new ControlPoint(  0, new Color(64, 64, 64).getRGB()),
                    new ControlPoint(nColors-1, new Color(192, 192, 192).getRGB())
            );
        }
    };

    /**
     * Get or create the color palette.
     *
     * @return color palette instance.
     */
    public abstract ColorPalette colorPalette();
}
