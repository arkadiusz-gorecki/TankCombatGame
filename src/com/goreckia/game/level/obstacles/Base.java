package com.goreckia.game.level.obstacles;

import com.goreckia.game.utils.Textures;

import static com.goreckia.game.main.Constants.*;

public class Base extends Obstacle {
    public Base(Textures textures) {
        super((MAP_HALF_CELLS - 2) / 2, MAP_HALF_CELLS - 2, textures);
        isDestroyable = true;
        size = CELL_SIZE;
    }
}
