package com.goreckia.game.states.tanks;

import com.goreckia.game.states.Constants;
import com.goreckia.game.states.PlayingState;
import com.goreckia.game.states.level.obstacles.Obstacle;

import java.awt.*;
import java.util.List;

public class EnemyTank extends Tank {

    public EnemyTank(PlayingState.Textures textures) {
        speed = 5;
        x = y = 2 * Constants.CELL_SIZE;
        direction = Direction.DOWN;

        PlayingState.Textures.Texture4Directions txt4D = textures.getEnemyTankTextures();
        textureUp = txt4D.getUp().getScaledInstance(Constants.TANK_SIZE, Constants.TANK_SIZE, Image.SCALE_SMOOTH);
        textureDown = txt4D.getDown().getScaledInstance(Constants.TANK_SIZE, Constants.TANK_SIZE, Image.SCALE_SMOOTH);
        textureLeft = txt4D.getLeft().getScaledInstance(Constants.TANK_SIZE, Constants.TANK_SIZE, Image.SCALE_SMOOTH);
        textureRight = txt4D.getRight().getScaledInstance(Constants.TANK_SIZE, Constants.TANK_SIZE, Image.SCALE_SMOOTH);
    }
    @Override
    public void action(List<Obstacle> obstacles) {

    }
}
