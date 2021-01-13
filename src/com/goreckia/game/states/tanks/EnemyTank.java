package com.goreckia.game.states.tanks;

import com.goreckia.game.states.Constants;
import com.goreckia.game.states.PlayingState;
import com.goreckia.game.states.Textures;
import com.goreckia.game.states.level.obstacles.Obstacle;

import java.util.List;

public class EnemyTank extends Tank {

    public EnemyTank(Textures textures) {
        super(textures);
        speed = 5;
        x = y = 2 * Constants.CELL_SIZE;
        direction = Direction.DOWN;
    }

    @Override
    public void action(List<Obstacle> obstacles) {

    }
}
