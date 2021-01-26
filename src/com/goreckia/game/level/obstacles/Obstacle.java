package com.goreckia.game.level.obstacles;

import com.goreckia.game.level.CollidableObject;
import com.goreckia.game.main.Constants;
import com.goreckia.game.utils.Textures;

import java.awt.*;
import java.io.Serializable;

public abstract class Obstacle extends CollidableObject implements Serializable {
    protected Image texture;
    protected boolean isDestroyable;

    public boolean isDestroyable() {
        return isDestroyable;
    }

    public Obstacle(int xHalfCells, int yHalfCells) {
        this.x = xHalfCells * Constants.HALF_CELL_SIZE;
        this.y = yHalfCells * Constants.HALF_CELL_SIZE;
    }

    public Obstacle(int xHalfCells, int yHalfCells, Textures textures) {
        this.x = xHalfCells * Constants.HALF_CELL_SIZE;
        this.y = yHalfCells * Constants.HALF_CELL_SIZE;
        String className = this.getClass().getName().substring(this.getClass().getName().lastIndexOf('.') + 1);
        texture = textures.getTexture(className);
    }

    public void draw(Graphics g) {
        g.drawImage(texture, x, y, null);
    }

}
