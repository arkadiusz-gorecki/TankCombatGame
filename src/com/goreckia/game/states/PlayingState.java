package com.goreckia.game.states;

import com.goreckia.game.states.level.Level;

import java.awt.*;
import java.util.Queue;

public class PlayingState extends State {
    private Queue<Level> levels;
    private Level currentLevel;

    public PlayingState(GameStateManager gsm) {
        super(gsm);
    }

    public void loadLevels(String path) {
        // load from file
    }

    @Override
    public void tick() {

    }

    @Override
    public void draw(Graphics g) {

    }

    @Override
    public void keyPressed(int k) {

    }

    @Override
    public void keyReleased(int k) {

    }

    private void gameLoop() {

    }
}
