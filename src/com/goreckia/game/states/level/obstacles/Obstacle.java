package com.goreckia.game.states.level.obstacles;

import com.goreckia.game.states.Constants;

import java.awt.*;
import java.io.Serializable;

public abstract class Obstacle implements Serializable {
    protected int x, y;
    protected Image texture;

    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }
    public Obstacle(int xHalfCells, int yHalfCells) {
        this.x = xHalfCells * Constants.HALF_CELL_SIZE;
        this.y = yHalfCells * Constants.HALF_CELL_SIZE;
    }

    public void draw(Graphics g) {
        g.drawImage(texture, x, y, null);
    }


}
