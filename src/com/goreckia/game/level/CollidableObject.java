package com.goreckia.game.level;

import com.goreckia.game.utils.Pair;

public abstract class CollidableObject {
    protected int x;
    protected int y;
    protected int size;
    protected int speed;

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public int getSpeed() {
        return speed;
    }

    public Pair<Boolean, Integer> goingToCollideR(CollidableObject movingObj) {
        // movingObj - moving object (only tanks and shells can move)
        // calculates whether movingObj (moving in the right drection) is going to collide to this CollidableObject and also returns the distance it can move before colliding
        if (x == movingObj.x && y == movingObj.y) // if you are yourself
            return new Pair<>(false, movingObj.getSpeed()); // you cannot collide with yourself
        if (y - movingObj.y < movingObj.size && y - movingObj.y > -size &&
                x - movingObj.x <= movingObj.size + movingObj.getSpeed() && x - movingObj.x > -size) // check if movingObj is going to collide in this tick with its speed
            return new Pair<>(true, Math.abs(x - movingObj.x - movingObj.size)); // there will be a collision, return max distance it can move before colliding
        return new Pair<>(false, movingObj.getSpeed()); // no collision, movingObj moves maximum distance (equal to its speed)
    }

    public Pair<Boolean, Integer> goingToCollideL(CollidableObject movingObj) {
        // movingObj moving in the left direction
        if (x == movingObj.x && y == movingObj.y)
            return new Pair<>(false, movingObj.getSpeed());
        if (y - movingObj.y < movingObj.size && y - movingObj.y > -size &&
                x - movingObj.x >= -size - movingObj.getSpeed() && x - movingObj.x < movingObj.size)
            return new Pair<>(true, Math.abs(x - movingObj.x + size));
        return new Pair<>(false, movingObj.getSpeed());
    }

    public Pair<Boolean, Integer> goingToCollideU(CollidableObject movingObj) {
        // movingObj moving in the up direction
        if (x == movingObj.x && y == movingObj.y)
            return new Pair<>(false, movingObj.getSpeed());
        if (x - movingObj.x < movingObj.size && x - movingObj.x > -size &&
                y - movingObj.y >= -size - movingObj.getSpeed() && y - movingObj.y < movingObj.size)
            return new Pair<>(true, Math.abs(y - movingObj.y + size));
        return new Pair<>(false, movingObj.getSpeed());
    }

    public Pair<Boolean, Integer> goingToCollideD(CollidableObject movingObj) {
        // movingObj moving in the down direction
        if (x == movingObj.x && y == movingObj.y)
            return new Pair<>(false, movingObj.getSpeed());
        if (x - movingObj.x < movingObj.size && x - movingObj.x > -size &&
                y - movingObj.y <= movingObj.size + movingObj.getSpeed() && y - movingObj.y > -size)
            return new Pair<>(true, Math.abs(y - movingObj.y - movingObj.size));
        return new Pair<>(false, movingObj.getSpeed());
    }

}
