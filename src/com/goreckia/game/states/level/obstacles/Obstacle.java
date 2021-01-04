package com.goreckia.game.states.level.obstacles;

import java.awt.*;

public abstract class Obstacle {
    private int x, y;
    private Image texture;

    public void draw(Graphics g) {
        g.drawImage(texture, x, y, null);
    }
}
