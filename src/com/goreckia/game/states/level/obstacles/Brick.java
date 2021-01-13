package com.goreckia.game.states.level.obstacles;

import com.goreckia.game.states.Constants;
import com.goreckia.game.states.PlayingState;
import com.goreckia.game.states.Textures;

import java.awt.*;

public class Brick extends Obstacle {

    public Brick(int xHalfCells, int yHalfCells, Textures textures) {
        super(xHalfCells, yHalfCells);
        String className = this.getClass().getName().substring(this.getClass().getName().lastIndexOf('.') + 1);
        texture = textures.getTexture(className);
    }
}
