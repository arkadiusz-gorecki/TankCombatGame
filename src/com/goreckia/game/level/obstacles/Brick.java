package com.goreckia.game.level.obstacles;

import com.goreckia.game.main.Constants;
import com.goreckia.game.utils.Textures;

public class Brick extends Obstacle {

    public Brick(int xHalfCells, int yHalfCells, Textures textures) {
        super(xHalfCells, yHalfCells, textures);
        isDestroyable = true;
        size = Constants.HALF_CELL_SIZE;
    }
}
