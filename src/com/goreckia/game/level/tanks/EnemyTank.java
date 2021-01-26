package com.goreckia.game.level.tanks;

import com.goreckia.game.utils.Pair;
import com.goreckia.game.utils.Textures;
import com.goreckia.game.level.Level;

import java.util.Random;

import static com.goreckia.game.main.Constants.*;
import static com.goreckia.game.utils.Direction.*;

public class EnemyTank extends Tank {
    Random rand;
    int movingTimer; // for how many ticks remaining is this tank going to move in one direction
    int shootPause; // how long until enemy shoots

    public EnemyTank(Textures textures, Pair<Integer, Integer> startingPosition) {
        super(textures);
        shell = new EnemyShell(textures);
        speed = 2;
        rand = new Random();
        movingTimer = 0;
        x = startingPosition.getLeft();
        y = startingPosition.getRight();
        direction = DOWN;
    }

    @Override
    public void action(Level level) {
        // Artificial Intelligence logic
        randDirection();
        moveTank(level);

        updateShootTimer();
        if (randShoot())
            shoot(level);
    }

    public void randDirection() {
        // this algorithm has higher chance of choosing direction towards player base
        if (movingTimer-- <= 0) {
            movingTimer = 15 + rand.nextInt(35); //przez ile tickow ma jechac
            int r = 1 + rand.nextInt(100);
            if (y < 9 * CELL_SIZE) // if the enemy tank is in the upper part of the map
            {
                if (r <= 15)
                    direction = UP; // 15% chance
                else if (r <= 70)
                    direction = DOWN; // 55% chance
                else if (r <= 85)
                    direction = LEFT; // 15% chance
                else
                    direction = RIGHT; // 15% chance
            } else // if the enemy tank is in the lower part of the map (close to players base)
            {
                if (x <= 7 * CELL_SIZE) // left lower part of the map
                {
                    if (r <= 15)
                        direction = UP; // 15% chance
                    else if (r <= 50)
                        direction = DOWN; // 35% chance
                    else if (r <= 85)
                        direction = RIGHT; // 35% chance
                    else
                        direction = LEFT; // 15% chance
                } else // right lower part of the map
                {
                    if (r <= 15)
                        direction = UP; // 15% chance
                    else if (r <= 50)
                        direction = DOWN; // 35% chance
                    else if (r <= 65)
                        direction = RIGHT; // 15% chance
                    else
                        direction = LEFT; // 35% chance
                }

            }
        }
    }

    public boolean randShoot() {
        if (shootPause-- <= 0) // check if pause time is up
        {
            shootPause = rand.nextInt(60); // generate next pause
            return true; // try to shoot
        }
        return false; // dont shoot
    }
}
