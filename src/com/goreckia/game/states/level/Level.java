package com.goreckia.game.states.level;

import com.goreckia.game.states.level.obstacles.Obstacle;

import java.awt.*;
import java.util.List;
import java.util.Queue;

public class Level {
    private List<Obstacle> obstacles;
    private Queue<Obstacle> enemies;

    public void draw(Graphics g) {
        for (Obstacle obs : obstacles)
            obs.draw(g);
    }
}
