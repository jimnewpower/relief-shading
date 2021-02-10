package com.primalimited.reliefshading.color;

import javax.swing.*;
import java.awt.*;

public class ColorPaletteDefaultsDemo implements Runnable {

    public static void main(String...args) {
        SwingUtilities.invokeLater(new ColorPaletteDefaultsDemo());
    }

    @Override
    public void run() {
        JFrame frame = new JFrame();
        frame.getContentPane().setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setTitle("Color Palette Defaults");
        frame.setSize(1000, 600);
        frame.setExtendedState(Frame.MAXIMIZED_BOTH);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().add(new Panel(), BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private static class Panel extends JPanel {
        public static final long serialVersionUID = 4848482121L;

        @Override
        public void paint(Graphics g) {
            super.paint(g);

            int startX = 20;
            int startY = 20;
            int textHeight = 16;

            int width = 2;
            int height = 60;
            int y = startY;

            for (ColorPaletteDefaults defaults : ColorPaletteDefaults.values()) {
                ColorPalette colorPalette = defaults.colorPalette();
                int nColors = colorPalette.nColors();

                String title = defaults.name() + " (" + nColors + ")";
                g.setColor(Color.black);
                g.drawString(title, startX, y);
                y += textHeight;

                for (int index = 0, x = 10; index < nColors; index++, x += width) {
                    Color color = new Color(colorPalette.rgb(index));
                    ((Graphics2D)g).setPaint(color);
                    g.fillRect(x, y, width, height);
                }

                y += height + textHeight * 2;
            }
        }
    }
}
