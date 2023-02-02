package com.primalimited.reliefshading.color;

import java.awt.*;

/**
 * Defines default color palettes.
 *
 * Example palettes can be found and copied here:
 * http://soliton.vm.bytemark.co.uk/pub/cpt-city/views/totp-svg.html
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
    DEM_PRINT {
        @Override
        public ColorPalette colorPalette() {
            final int nColors = 800;
            return ColorPalette.create(
                    nColors,
                    new ControlPoint(  0, new Color(51, 102, 0).getRGB()),
                    new ControlPoint(100, new Color(129, 195, 31).getRGB()),
                    new ControlPoint(200, new Color(255, 255, 204).getRGB()),
                    new ControlPoint(400, new Color(244, 189, 69).getRGB()),
                    new ControlPoint(500, new Color(102, 51, 12).getRGB()),
                    new ControlPoint(600, new Color(102, 51, 0).getRGB()),
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
            final int min = 64;
            final int max = 192;
            return ColorPalette.create(
                    nColors,
                    new ControlPoint(  0, new Color(min, min, min).getRGB()),
                    new ControlPoint(nColors-1, new Color(max, max, max).getRGB())
            );
        }
    },
    RELIEF_SHADE_DYNAMIC {
        @Override
        public ColorPalette colorPalette() {
            final int nColors = 256;
            final int min = 16;
            final int max = 240;
            return ColorPalette.create(
                    nColors,
                    new ControlPoint(  0, new Color(min, min, min).getRGB()),
                    new ControlPoint(nColors-1, new Color(max, max, max).getRGB())
            );
        }
    },
    RELIEF_SHADE_DARK {
        @Override
        public ColorPalette colorPalette() {
            final int nColors = 256;
            final int min = 24;
            final int max = 128;
            return ColorPalette.create(
                    nColors,
                    new ControlPoint(  0, new Color(min, min, min).getRGB()),
                    new ControlPoint(nColors-1, new Color(max, max, max).getRGB())
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
