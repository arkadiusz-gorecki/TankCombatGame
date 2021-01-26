package com.goreckia.game.level.tanks;

import com.goreckia.game.main.Constants;
import com.goreckia.game.level.CollidableObject;
import com.goreckia.game.level.Level;
import com.goreckia.game.utils.Direction;
import com.goreckia.game.utils.ImageOperations;
import com.goreckia.game.utils.Textures;

import java.awt.*;
import java.awt.image.BufferedImage;

import static com.goreckia.game.main.Constants.*;

public abstract class Shell extends CollidableObject {
    protected boolean exists = false; // if shell exists on the map
    protected Direction direction;
    protected Image textureUp;
    protected Image textureDown;
    protected Image textureLeft;
    protected Image textureRight;

    public boolean exists() {
        return exists;
    }

    public Shell(Textures textures) {
        size = Constants.SHELL_SIZE;
        speed = 6;
        x = y = -CELL_SIZE;
        direction = Direction.UP;
        String className = this.getClass().getName().substring(this.getClass().getName().lastIndexOf('.') + 1);
        textureUp = textures.getTexture(className);
        textureDown = ImageOperations.rotate(ImageOperations.copy((BufferedImage) textureUp), 180);
        textureLeft = ImageOperations.rotate(ImageOperations.copy((BufferedImage) textureUp), 270);
        textureRight = ImageOperations.rotate(ImageOperations.copy((BufferedImage) textureUp), 90);
    }

    public void shoot(Tank shootingTank) {
        exists = true;
        direction = shootingTank.getDirection();

        x = shootingTank.getX();
        y = shootingTank.getY();
        switch (direction) {
            case RIGHT:
                x += TANK_SIZE - SHELL_SIZE;
                y += (TANK_SIZE - SHELL_SIZE) / 2;
                break;
            case LEFT:
                y += (TANK_SIZE - SHELL_SIZE) / 2;
                break;
            case UP:
                x += (TANK_SIZE - SHELL_SIZE) / 2;
                break;
            case DOWN:
                x += (TANK_SIZE - SHELL_SIZE) / 2;
                y += TANK_SIZE - SHELL_SIZE;
                break;
        }
    }

    public void action(Level level) {
        if (exists)
            moveShell(level);
    }

    public void moveShell(Level level) {
        switch (direction) {
            case RIGHT:
                if (goingToCollideAdjacentR(level)) { // one shell can destroy two adjacent obstacles
                    exists = false;
                    level.addDestroyedShell(this);
                }
                x += speed;
                break;
            case LEFT:
                if (goingToCollideAdjacentL(level)) {
                    exists = false;
                    level.addDestroyedShell(this);
                }
                x -= speed;
                break;
            case UP:
                if (goingToCollideAdjacentU(level)) {
                    exists = false;
                    level.addDestroyedShell(this);
                }
                y -= speed;
                break;
            case DOWN:
                if (goingToCollideAdjacentD(level)) {
                    exists = false;
                    level.addDestroyedShell(this);
                }
                y += speed;
                break;
        }
    }

    protected abstract boolean goingToCollideAdjacentR(Level level); // one shell can destroy two adjacent obstacles

    protected abstract boolean goingToCollideAdjacentL(Level level);

    protected abstract boolean goingToCollideAdjacentU(Level level);

    protected abstract boolean goingToCollideAdjacentD(Level level);

    public void draw(Graphics g) {
        if (!exists)
            return;
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
