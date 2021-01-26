package com.goreckia.game.utils;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class ImageOperations {
    public static BufferedImage copy(BufferedImage image) {
        BufferedImage copy = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics g = copy.createGraphics();
        g.drawImage(image, 0, 0, null);
        return copy;
    }

    public static BufferedImage rotate(BufferedImage sourceImage, double angle) {
        int width = sourceImage.getWidth();
        int height = sourceImage.getHeight();
        BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = result.createGraphics();

        AffineTransform transform = new AffineTransform();
        transform.rotate(angle / 180 * Math.PI, width / 2, height / 2);
        g2d.drawRenderedImage(sourceImage, transform);

        g2d.dispose();
        return result;
    }

}
