package com.goreckia.game.states.tanks;

import com.goreckia.game.states.Constants;
import com.goreckia.game.states.PlayingState;
import com.goreckia.game.states.Textures;
import com.goreckia.game.states.level.obstacles.Obstacle;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.List;

public abstract class Tank {
    protected enum Direction {UP, DOWN, LEFT, RIGHT}

    protected int x, y;
    protected Direction direction;
    protected int speed;
    protected Image textureUp;
    protected Image textureDown;
    protected Image textureLeft;
    protected Image textureRight;

    private class Shell {

    }

    public Tank(Textures textures) {
        String className = this.getClass().getName().substring(this.getClass().getName().lastIndexOf('.') + 1);
        textureUp = textures.getTexture(className);
        textureDown = rotateImage(copy((BufferedImage) textureUp), 180);
        textureLeft = rotateImage(copy((BufferedImage) textureUp), 270);
        textureRight = rotateImage(copy((BufferedImage) textureUp), 90);
    }

    private BufferedImage copy(BufferedImage image) {
        BufferedImage copy = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_RGB);
        Graphics g = copy.createGraphics();
        g.drawImage(image, 0, 0, null);
        return copy;
    }

    private BufferedImage rotateImage(BufferedImage sourceImage, double angle) {
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

    public abstract void action(List<Obstacle> obstacles);

    public void draw(Graphics g) {
        switch (direction) {
            case UP:
                g.drawImage(textureUp, x, y, null);
                break;
            case DOWN:
                g.drawImage(textureDown, x, y, null);
                break;
            case LEFT:
                g.drawImage(textureLeft, x, y, null);
                break;
            case RIGHT:
                g.drawImage(textureRight, x, y, null);
                break;
        }
    }

}
