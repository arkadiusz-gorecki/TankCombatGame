package com.goreckia.game.states.tanks;

import com.goreckia.game.states.Constants;
import com.goreckia.game.states.PlayingState;
import com.goreckia.game.states.Textures;
import com.goreckia.game.states.level.obstacles.Obstacle;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.List;

public class PlayerTank extends Tank {

    private static class Keyboard // knows which keys are currently pressed
    {
        private static boolean keyUp;
        private static boolean keyDown;
        private static boolean keyLeft;
        private static boolean keyRight;

        public static boolean isKeyDown(int k) {
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
            }
        }
    }

    public PlayerTank(Textures textures) {
        super(textures);
        speed = 5;
        x = y = 2 * Constants.CELL_SIZE;
        direction = Direction.UP;
    }

    @Override
    public void action(List<Obstacle> obstacles) { // check for collisions and move tank if there arent any
        if (Keyboard.isKeyDown(KeyEvent.VK_UP)) {
            y -= speed;
            direction = Direction.UP;
            //moveTank(obstacles?);

        } else if (Keyboard.isKeyDown(KeyEvent.VK_DOWN)) {
            y += speed;
            direction = Direction.DOWN;
        } else if (Keyboard.isKeyDown(KeyEvent.VK_LEFT)) {
            x -= speed;
            direction = Direction.LEFT;
        } else if (Keyboard.isKeyDown(KeyEvent.VK_RIGHT)) {
            x += speed;
            direction = Direction.RIGHT;
        }
    }

    public void keyPressed(int k) {
        Keyboard.pressKey(k);
    }

    public void keyReleased(int k) {
        Keyboard.releaseKey(k);
    }
}
