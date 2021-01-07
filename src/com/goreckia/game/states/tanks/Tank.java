package com.goreckia.game.states.tanks;


import com.goreckia.game.states.level.obstacles.Obstacle;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.List;

public abstract class Tank {
    protected enum Direction {LEFT, RIGHT, UP, DOWN}

    protected int x, y;
    protected Image texture;
    protected Direction direction = Direction.UP;
    protected int speed;

    private class Shell {

    }

    public abstract void action(List<Obstacle> obstacles);

    public void draw(Graphics g) {
        g.drawImage(texture, x, y, null);
    }

}
