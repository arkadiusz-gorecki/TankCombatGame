package com.goreckia.game.level;

import com.goreckia.game.utils.Textures;
import com.goreckia.game.level.obstacles.Base;
import com.goreckia.game.level.obstacles.Border;
import com.goreckia.game.level.obstacles.Obstacle;
import com.goreckia.game.level.tanks.EnemyTank;
import com.goreckia.game.level.tanks.PlayerTank;
import com.goreckia.game.level.tanks.Shell;
import com.goreckia.game.level.tanks.Tank;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import static com.goreckia.game.main.Constants.*;

public class Level implements Serializable {
    public PlayerTank playerTank;
    public Base base;
    private List<Obstacle> obstacles;
    private List<Obstacle> destroyedObstacles = new ArrayList<>();
    private Queue<EnemyTank> enemies; // enemy tanks that are assigned to this level
    private List<EnemyTank> currentEnemies = new ArrayList<>(); // enemy tank that are currently on the map
    private List<Tank> destroyedEnemies = new ArrayList<>();
    private List<Shell> currentShells = new ArrayList<>();
    private List<Shell> destroyedShells = new ArrayList<>();

    private int score = 0;
    private boolean playerDestroyed = false;
    private boolean baseDestroyed = false;
    private boolean levelLost = false;
    private boolean levelFinished = false;
    private static final int enemySpawnInterval = 2 * FPS; // every 2 seconds one new enemy spawns
    private int enemySpawnTimer = enemySpawnInterval;
    private static final int playerSpawnInterval = 2 * FPS; // player respawns in 2 seconds after death
    private int playerSpawnTimer = playerSpawnInterval;
    private boolean playerExists = false;

    public int getScore() {
        return score;
    }
    public List<Obstacle> getObstacles() {
        return obstacles;
    }
    public void addDestroyedObstacle(Obstacle obs) {
        destroyedObstacles.add(obs);
    }
    public List<EnemyTank> getEnemyTanks() {
        return currentEnemies;
    }
    public void addDestroyedEnemyTank(Tank tank) {
        destroyedEnemies.add(tank);
    }
    public void addShell(Shell s) {
        currentShells.add(s);
    }
    public void addDestroyedShell(Shell shell) {
        destroyedShells.add(shell);
    }
    public void setPlayerDestroyed() {
        playerDestroyed = true;
    }
    public void setBaseDestroyed() {
        baseDestroyed = true;
    }

    public Level(PlayerTank playerTank, Queue<EnemyTank> enemies, List<Obstacle> obstacles, Textures textures) {
        this.playerTank = playerTank;
        this.obstacles = obstacles;
        this.enemies = enemies;
        addBordersAndBase(obstacles, textures);
    }

    private void addBordersAndBase(List<Obstacle> obstacles, Textures textures) {
        obstacles.add(new Border(-MAP_HALF_CELLS, 0));
        obstacles.add(new Border(MAP_HALF_CELLS, 0));
        obstacles.add(new Border(0, -MAP_HALF_CELLS));
        obstacles.add(new Border(0, MAP_HALF_CELLS));
        obstacles.add(base = new Base(textures));
    }

    public void tick() {
        for (Shell shell : currentShells)
            shell.action(this);
        currentShells.removeAll(destroyedShells);
        destroyedShells.clear();

        currentEnemies.removeAll(destroyedEnemies);
        destroyedEnemies.clear();

        if (playerDestroyed) { // players tank got destroyed
            playerDestroyed = false;
            playerTank.loseOneLife();
            playerSpawnTimer = playerSpawnInterval;
            playerTank.setStartingPosition();
            playerExists = false; // players tank stops existing on a map until he respawns (spawn timer ends)
        }

        if (playerExists)
            playerTank.action(this);
        for (EnemyTank enemy : currentEnemies)
            enemy.action(this);

        checkIfLevelFinished();

        obstacles.removeAll(destroyedObstacles);
        destroyedObstacles.clear();
        updateSpawnTimers();
    }

    private void checkIfLevelFinished() {
        if (baseDestroyed)
            levelFinished = levelLost = true;

        if (playerTank.getRemainingLives() <= 0)
            levelFinished = levelLost = true;

        if (currentEnemies.isEmpty() && enemies.isEmpty()) // every enemy tank destroyed
            levelFinished = true;
    }

    private void updateSpawnTimers() {
        if (currentEnemies.size() >= MAX_ENEMIES) // no more than MAX_ENEMIES enemies on the map at the same time
            enemySpawnTimer = enemySpawnInterval; // reset timer
        if (enemySpawnTimer-- <= 0) {
            spawnEnemyTank();
            enemySpawnTimer = enemySpawnInterval;
        }
        if (playerSpawnTimer-- == 0)
            playerExists = true;
    }

    private void spawnEnemyTank() {
        if (!enemies.isEmpty())
            currentEnemies.add(enemies.remove());
    }

    public boolean levelFinished() {
        return levelFinished;
    }

    public boolean levelLost() {
        return levelLost;
    }

    public void keyPressed(int k) {
        playerTank.keyPressed(k);
    }

    public void keyReleased(int k) {
        playerTank.keyReleased(k);
    }

    public void draw(Graphics g) {
        g.setColor(Color.BLACK); // black background;
        g.fillRect(0, 0, MAP_SIZE, MAP_SIZE); // clear
        for (Obstacle obs : obstacles)
            obs.draw(g);
        for (Shell shell : currentShells)
            shell.draw(g);
        for (EnemyTank enemy : currentEnemies)
            enemy.draw(g);
        if (playerExists)
            playerTank.draw(g);
    }
}
