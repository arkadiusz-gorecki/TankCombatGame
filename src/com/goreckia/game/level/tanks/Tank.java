package com.goreckia.game.level.tanks;

import com.goreckia.game.main.Constants;
import com.goreckia.game.level.CollidableObject;
import com.goreckia.game.level.Level;
import com.goreckia.game.level.obstacles.Obstacle;
import com.goreckia.game.utils.Direction;
import com.goreckia.game.utils.ImageOperations;
import com.goreckia.game.utils.Pair;
import com.goreckia.game.utils.Textures;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

import static com.goreckia.game.main.Constants.HALF_CELL_SIZE;
import static com.goreckia.game.main.Constants.QUARTER_CELL_SIZE;

public abstract class Tank extends CollidableObject {
    protected int healthPoints;
    protected Direction direction;
    protected Image textureUp;
    protected Image textureDown;
    protected Image textureLeft;
    protected Image textureRight;
    protected Shell shell;
    protected static final int shootInterval = 20; // you cannot shoot infinitely fast (in every every tick) at close range
    protected int shootTimer;

    public Direction getDirection() {
        return direction;
    }

    public Tank(Textures textures) {
        size = Constants.TANK_SIZE;
        shootTimer = shootInterval;
        String className = this.getClass().getName().substring(this.getClass().getName().lastIndexOf('.') + 1);
        textureUp = textures.getTexture(className);
        textureDown = ImageOperations.rotate(ImageOperations.copy((BufferedImage) textureUp), 180);
        textureLeft = ImageOperations.rotate(ImageOperations.copy((BufferedImage) textureUp), 270);
        textureRight = ImageOperations.rotate(ImageOperations.copy((BufferedImage) textureUp), 90);
    }

    public abstract void action(Level level);

    protected void updateShootTimer() {
        if (shootTimer > 0)
            shootTimer--;
    }

    protected void shoot(Level level) {
        if (!shell.exists() && shootTimer <= 0) {
            shell.shoot(this);
            level.addShell(shell); // add shell to the list of existing shells
            shootTimer = shootInterval;
        }
    }

    public boolean receiveDamage() {
        return --healthPoints <= 0;
    }

    public void moveTank(Level level) {
        int realDistance; // real distance which tank will be able to move in this tick
        switch (direction) {
            case RIGHT:
                realDistance = distanceToR(level);
                x += Math.min(speed, realDistance);
                if (y % (HALF_CELL_SIZE) <= QUARTER_CELL_SIZE) // align tank to the "grid" of the map
                    y = (y / (HALF_CELL_SIZE)) * (HALF_CELL_SIZE);
                else
                    y = (y / (HALF_CELL_SIZE) + 1) * (HALF_CELL_SIZE);
                break;
            case LEFT:
                realDistance = distanceToL(level);
                x -= Math.min(speed, realDistance);
                if (y % (HALF_CELL_SIZE) <= QUARTER_CELL_SIZE)
                    y = (y / (HALF_CELL_SIZE)) * (HALF_CELL_SIZE);
                else
                    y = (y / (HALF_CELL_SIZE) + 1) * (HALF_CELL_SIZE);
                break;
            case UP:
                realDistance = distanceToU(level);
                y -= Math.min(speed, realDistance);
                if (x % (HALF_CELL_SIZE) <= QUARTER_CELL_SIZE)
                    x = (x / (HALF_CELL_SIZE)) * (HALF_CELL_SIZE);
                else
                    x = (x / (HALF_CELL_SIZE) + 1) * (HALF_CELL_SIZE);
                break;
            case DOWN:
                realDistance = distanceToD(level);
                y += Math.min(speed, realDistance);
                if (x % (HALF_CELL_SIZE) <= QUARTER_CELL_SIZE)
                    x = (x / (HALF_CELL_SIZE)) * (HALF_CELL_SIZE);
                else
                    x = (x / (HALF_CELL_SIZE) + 1) * (HALF_CELL_SIZE);
                break;
        }
    }

    public int distanceToR(Level level) {
        List<Obstacle> obstacles = level.getObstacles();
        List<EnemyTank> enemyTanks = level.getEnemyTanks();
        Tank playerTank = level.playerTank;

        Pair<Boolean, Integer> goingToCollide; // if the tank is going to collide and if yes what maximum distance it can move
        if ((goingToCollide = playerTank.goingToCollideR(this)).getLeft())
            return goingToCollide.getRight();
        for (EnemyTank enemy : enemyTanks)
            if ((goingToCollide = enemy.goingToCollideR(this)).getLeft())
                return goingToCollide.getRight();
        for (Obstacle obs : obstacles)
            if ((goingToCollide = obs.goingToCollideR(this)).getLeft())
                return goingToCollide.getRight();
        return speed;
    }

    public int distanceToL(Level level) {
        List<Obstacle> obstacles = level.getObstacles();
        List<EnemyTank> enemyTanks = level.getEnemyTanks();
        Tank playerTank = level.playerTank;

        Pair<Boolean, Integer> goingToCollide;
        if ((goingToCollide = playerTank.goingToCollideL(this)).getLeft())
            return goingToCollide.getRight();
        for (EnemyTank enemy : enemyTanks)
            if ((goingToCollide = enemy.goingToCollideL(this)).getLeft())
                return goingToCollide.getRight();
        for (Obstacle obs : obstacles)
            if ((goingToCollide = obs.goingToCollideL(this)).getLeft())
                return goingToCollide.getRight();
        return speed;
    }

    public int distanceToU(Level level) {
        List<Obstacle> obstacles = level.getObstacles();
        List<EnemyTank> enemyTanks = level.getEnemyTanks();
        Tank playerTank = level.playerTank;

        Pair<Boolean, Integer> goingToCollide;
        if ((goingToCollide = playerTank.goingToCollideU(this)).getLeft())
            return goingToCollide.getRight();
        for (EnemyTank enemy : enemyTanks)
            if ((goingToCollide = enemy.goingToCollideU(this)).getLeft())
                return goingToCollide.getRight();
        for (Obstacle obs : obstacles)
            if ((goingToCollide = obs.goingToCollideU(this)).getLeft())
                return goingToCollide.getRight();
        return speed;
    }

    public int distanceToD(Level level) {
        List<Obstacle> obstacles = level.getObstacles();
        List<EnemyTank> enemyTanks = level.getEnemyTanks();
        Tank playerTank = level.playerTank;

        Pair<Boolean, Integer> goingToCollide;
        if ((goingToCollide = playerTank.goingToCollideD(this)).getLeft())
            return goingToCollide.getRight();
        for (EnemyTank enemy : enemyTanks)
            if ((goingToCollide = enemy.goingToCollideD(this)).getLeft())
                return goingToCollide.getRight();
        for (Obstacle obs : obstacles)
            if ((goingToCollide = obs.goingToCollideD(this)).getLeft())
                return goingToCollide.getRight();
        return speed;
    }

    public void draw(Graphics g) {
        switch (direction) {
            case UP:
                g.drawImage(textureUp, x, y, null);
                break;
            case DOWN:
                g.drawImage(textureDown, x, y, null);
                break;
            case LEFT:
                g.drawImage(textureLeft, x, y, null);
                break;
            case RIGHT:
                g.drawImage(textureRight, x, y, null);
                break;
        }
    }

}
