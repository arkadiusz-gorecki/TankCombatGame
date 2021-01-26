package com.goreckia.game.level.obstacles;

import com.goreckia.game.main.Constants;

public class Border extends Obstacle {

    public Border(int xHalfCells, int yHalfCells) {
        super(xHalfCells, yHalfCells);
        isDestroyable = false;
        size = Constants.MAP_SIZE;
    }
}
