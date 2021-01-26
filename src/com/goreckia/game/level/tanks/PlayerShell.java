package com.goreckia.game.level.tanks;

import com.goreckia.game.utils.Textures;
import com.goreckia.game.level.Level;
import com.goreckia.game.level.obstacles.Obstacle;

import java.util.List;

public class PlayerShell extends Shell {
    public PlayerShell(Textures textures) {
        super(textures);
    }

    @Override
    protected boolean goingToCollideAdjacentR(Level level) {
        List<EnemyTank> enemyTanks = level.getEnemyTanks();
        for (Tank tank : enemyTanks) {
            if ((tank.goingToCollideR(this).getLeft())) {
                if (tank.receiveDamage()) //jesli zostal zniszczony w wyniku obrazen
                    level.addDestroyedEnemyTank(tank);
                //s.SetImpact(t.pos.X, s.pos.Y + s.size / 2, Direction.Right);
                return true;
            }
        }
        List<Obstacle> obstacles = level.getObstacles();
        int hitCount = 0;
        for (Obstacle obs : obstacles) {
            if ((obs.goingToCollideR(this)).getLeft()) {
                if (obs.equals(level.base)) {
                    level.setBaseDestroyed();
                    return true;
                }
                if (obs.isDestroyable()) {
                    level.addDestroyedObstacle(obs);
                }
                hitCount++;
                //s.SetImpact(p.pos.X, s.pos.Y + s.size / 2, Direction.Right);
            }
        }
        if (hitCount >= 1) // did this shell hit at least one obstacle int this tick
            return true;
        return false;
    }

    @Override
    protected boolean goingToCollideAdjacentL(Level level) {
        List<EnemyTank> enemyTanks = level.getEnemyTanks();
        for (Tank tank : enemyTanks) {
            if ((tank.goingToCollideL(this).getLeft())) {
                if (tank.receiveDamage()) //jesli zostal zniszczony w wyniku obrazen
                    level.addDestroyedEnemyTank(tank);
                //s.SetImpact(t.pos.X, s.pos.Y + s.size / 2, Direction.Right);
                return true;
            }
        }
        List<Obstacle> obstacles = level.getObstacles();
        int hitCount = 0;
        for (Obstacle obs : obstacles) {
            if ((obs.goingToCollideL(this)).getLeft()) {
                if (obs.equals(level.base)) {
                    level.setBaseDestroyed();
                    return true;
                }
                if (obs.isDestroyable()) {
                    level.addDestroyedObstacle(obs);
                }
                hitCount++;
                //s.SetImpact(p.pos.X, s.pos.Y + s.size / 2, Direction.Right);
            }
        }
        if (hitCount >= 1) // did this shell hit at least one obstacle int this tick
            return true;
        return false;
    }

    @Override
    protected boolean goingToCollideAdjacentU(Level level) {
        List<EnemyTank> enemyTanks = level.getEnemyTanks();
        for (Tank tank : enemyTanks) {
            if ((tank.goingToCollideU(this).getLeft())) {
                if (tank.receiveDamage()) //jesli zostal zniszczony w wyniku obrazen
                    level.addDestroyedEnemyTank(tank);
                //s.SetImpact(t.pos.X, s.pos.Y + s.size / 2, Direction.Right);
                return true;
            }
        }
        List<Obstacle> obstacles = level.getObstacles();
        int hitCount = 0;
        for (Obstacle obs : obstacles) {
            if ((obs.goingToCollideU(this)).getLeft()) {
                if (obs.equals(level.base)) {
                    level.setBaseDestroyed();
                    return true;
                }
                if (obs.isDestroyable()) {
                    level.addDestroyedObstacle(obs);
                }
                hitCount++;
                //s.SetImpact(p.pos.X, s.pos.Y + s.size / 2, Direction.Right);
            }
        }
        if (hitCount >= 1) // did this shell hit at least one obstacle int this tick
            return true;
        return false;
    }

    @Override
    protected boolean goingToCollideAdjacentD(Level level) {
        List<EnemyTank> enemyTanks = level.getEnemyTanks();
        for (Tank tank : enemyTanks) {
            if ((tank.goingToCollideD(this).getLeft())) {
                if (tank.receiveDamage()) //jesli zostal zniszczony w wyniku obrazen
                    level.addDestroyedEnemyTank(tank);
                //s.SetImpact(t.pos.X, s.pos.Y + s.size / 2, Direction.Right);
                return true;
            }
        }
        List<Obstacle> obstacles = level.getObstacles();
        int hitCount = 0;
        for (Obstacle obs : obstacles) {
            if ((obs.goingToCollideD(this)).getLeft()) {
                if (obs.equals(level.base)) {
                    level.setBaseDestroyed();
                    return true;
                }
                if (obs.isDestroyable()) {
                    level.addDestroyedObstacle(obs);
                }
                hitCount++;
                //s.SetImpact(p.pos.X, s.pos.Y + s.size / 2, Direction.Right);
            }
        }
        if (hitCount >= 1) // did this shell hit at least one obstacle int this tick
            return true;
        return false;
    }
}
