package com.goreckia.game.level.tanks;

import com.goreckia.game.utils.Direction;
import com.goreckia.game.utils.Textures;
import com.goreckia.game.level.Level;

import java.awt.event.KeyEvent;

import static com.goreckia.game.main.Constants.*;
import static com.goreckia.game.utils.Direction.UP;

public class PlayerTank extends Tank {
    private int remainingLives = 3;

    public void loseOneLife() {
        remainingLives--;
    }

    public int getRemainingLives() {
        return remainingLives;
    }

    public void setStartingPosition() {
        direction = UP;
        x = PLAYER_STARTING_POSITION_X;
        y = PLAYER_STARTING_POSITION_Y;
    }

    private static class Keyboard // knows which keys are currently pressed
    {
        private static boolean keyUp;
        private static boolean keyDown;
        private static boolean keyLeft;
        private static boolean keyRight;
        private static boolean keyShoot;

        public static boolean isKeyDown(int k) { // FIXME hashmap instead of switch case
            switch (k) {
                case KeyEvent.VK_UP:
                case KeyEvent.VK_W:
                    return keyUp;
                case KeyEvent.VK_DOWN:
                case KeyEvent.VK_S:
                    return keyDown;
                case KeyEvent.VK_LEFT:
                case KeyEvent.VK_A:
                    return keyLeft;
                case KeyEvent.VK_RIGHT:
                case KeyEvent.VK_D:
                    return keyRight;
                case KeyEvent.VK_SPACE:
                    return keyShoot;
                default:
                    return false;
            }
        }

        public static void pressKey(int k) {
            switch (k) {
                case KeyEvent.VK_UP:
                case KeyEvent.VK_W:
                    keyUp = true;
                    break;
                case KeyEvent.VK_DOWN:
                case KeyEvent.VK_S:
                    keyDown = true;
                    break;
                case KeyEvent.VK_LEFT:
                case KeyEvent.VK_A:
                    keyLeft = true;
                    break;
                case KeyEvent.VK_RIGHT:
                case KeyEvent.VK_D:
                    keyRight = true;
                    break;
                case KeyEvent.VK_SPACE:
                    keyShoot = true;
                    break;
            }
        }

        public static void releaseKey(int k) {
            switch (k) {
                case KeyEvent.VK_UP:
                case KeyEvent.VK_W:
                    keyUp = false;
                    break;
                case KeyEvent.VK_DOWN:
                case KeyEvent.VK_S:
                    keyDown = false;
                    break;
                case KeyEvent.VK_LEFT:
                case KeyEvent.VK_A:
                    keyLeft = false;
                    break;
                case KeyEvent.VK_RIGHT:
                case KeyEvent.VK_D:
                    keyRight = false;
                    break;
                case KeyEvent.VK_SPACE:
                    keyShoot = false;
                    break;
            }
        }
    }

    public PlayerTank(Textures textures) {
        super(textures);
        shell = new PlayerShell(textures);

        speed = 3;
        x = PLAYER_STARTING_POSITION_X;
        y = PLAYER_STARTING_POSITION_Y;
        direction = UP;
    }

    @Override
    public void action(Level level) {
        // check for collisions and move tank if there arent any
        if (Keyboard.isKeyDown(KeyEvent.VK_UP)) {
            direction = UP;
            moveTank(level);
        } else if (Keyboard.isKeyDown(KeyEvent.VK_DOWN)) {
            direction = Direction.DOWN;
            moveTank(level);
        } else if (Keyboard.isKeyDown(KeyEvent.VK_LEFT)) {
            direction = Direction.LEFT;
            moveTank(level);
        } else if (Keyboard.isKeyDown(KeyEvent.VK_RIGHT)) {
            direction = Direction.RIGHT;
            moveTank(level);
        }

        updateShootTimer();
        if (Keyboard.isKeyDown(KeyEvent.VK_SPACE))
            shoot(level); // try to shoot
    }

    public void keyPressed(int k) {
        Keyboard.pressKey(k);
    }

    public void keyReleased(int k) {
        Keyboard.releaseKey(k);
    }
}
