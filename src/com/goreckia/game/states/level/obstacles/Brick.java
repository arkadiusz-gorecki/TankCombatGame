package com.goreckia.game.states.level.obstacles;

import com.goreckia.game.states.Constants;
import com.goreckia.game.states.PlayingState;

import java.awt.*;

public class Brick extends Obstacle {

    public Brick(int xHalfCells, int yHalfCells, PlayingState.Textures textures) {
        super(xHalfCells, yHalfCells);
        texture = textures.getBrickTextureImage().getScaledInstance(Constants.HALF_CELL_SIZE, Constants.HALF_CELL_SIZE, Image.SCALE_SMOOTH);
    }
}
