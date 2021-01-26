package com.goreckia.game.level.obstacles;

import com.goreckia.game.main.Constants;
import com.goreckia.game.utils.Textures;

public class Steel extends Obstacle {
    public Steel(int xHalfCells, int yHalfCells, Textures textures) {
        super(xHalfCells, yHalfCells, textures);
        isDestroyable = false;
        size = Constants.HALF_CELL_SIZE;
    }
}
