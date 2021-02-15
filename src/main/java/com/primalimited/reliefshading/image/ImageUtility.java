package com.primalimited.reliefshading.image;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class ImageUtility {

    /**
     * Flip an image vertically.
     *
     * @param image input image
     * @return vertical mirror image of the input image.
     */
    public static BufferedImage flipImageVertically(BufferedImage image) {
        AffineTransform tx = AffineTransform.getScaleInstance(1, -1);
        tx.translate(0, -image.getHeight(null));
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BICUBIC);
        return op.filter(image, null);
    }
}
