package com.goreckia.game.states.level;

import com.goreckia.game.states.Constants;
import com.goreckia.game.states.level.obstacles.Obstacle;
import com.goreckia.game.states.tanks.EnemyTank;
import com.goreckia.game.states.tanks.PlayerTank;
import com.goreckia.game.states.tanks.Tank;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class Level implements Serializable {
    private PlayerTank playerTank;
    private List<Obstacle> obstacles;
    private Queue<EnemyTank> enemies;
    private List<EnemyTank> currentEnemies = new ArrayList<>();
    private int score = 0;

    public Level(PlayerTank playerTank, Queue<EnemyTank> enemies, List<Obstacle> obstacles) {
        this.playerTank = playerTank;
        this.obstacles = obstacles;
        this.enemies = enemies;
        //currentEnemies.add(enemies.remove()); // first enemy spawns immediately;
    }

    public int getScore() {
        return score;
    }

    public void tick() {
        playerTank.action(obstacles); // action also computes this tanks shells collisions

        for (Tank enemy : currentEnemies)
            for (Obstacle obs : obstacles)
                enemy.action(obstacles);
    }

    public void draw(Graphics g) {
        g.setColor(Color.DARK_GRAY);
        g.fillRect(0, 0, Constants.PANEL_SIZE, Constants.PANEL_SIZE);

        g.setColor(Color.BLACK); // black background;
        g.fillRect(0, 0, Constants.MAP_SIZE, Constants.MAP_SIZE);
        for (Obstacle obs : obstacles)
            obs.draw(g);
        for (Tank enemy : currentEnemies)
            enemy.draw(g);
        playerTank.draw(g);
    }

    public boolean levelFinished() {
        // unfinished
        return false;
    }

    public boolean levelLost() {
        // unfinished
        return true;
    }

    public void keyPressed(int k) {
        playerTank.keyPressed(k);
    }

    public void keyReleased(int k) {
        playerTank.keyReleased(k);
    }

}
