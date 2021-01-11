package com.goreckia.game.states.tanks;

import com.goreckia.game.states.Constants;
import com.goreckia.game.states.PlayingState;
import com.goreckia.game.states.level.obstacles.Obstacle;

import java.awt.*;
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

    public Tank()
    {
    }

    private class Shell {

    }

    public abstract void action(List<Obstacle> obstacles);

    public void draw(Graphics g) {
        switch (direction)
        {
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
