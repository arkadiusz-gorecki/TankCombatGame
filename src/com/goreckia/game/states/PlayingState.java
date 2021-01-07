package com.goreckia.game.states;

import com.goreckia.game.states.level.Level;
import com.goreckia.game.states.level.obstacles.Obstacle;
import com.goreckia.game.states.tanks.PlayerTank;
import com.goreckia.game.states.tanks.Tank;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class PlayingState extends State {
    private Queue<Level> levels;
    private Level currentLevel;
    private PlayerTank playerTank;
    private int wholeGameScore = 0;

    public PlayingState(GameStateManager gsm) {
        super(gsm);
        loadTextures(".\\src\\com\\goreckia\\game\\resources\\textures\\player_tank.bmp");
        loadLevels(".\\src\\com\\goreckia\\game\\resources\\maps.txt");
        nextLevel(); // load first level
    }

    private void loadLevels(String path) {
        levels = new LinkedList<>();
        File f = new File(path);
        // unfinished
        // add every level from file to the queue
        levels.add(new Level(playerTank, new ArrayList<>(), new LinkedList<>()));
    }

    private void loadTextures(String path) {
        playerTank = new PlayerTank(path);
    }

    private void nextLevel() {
        currentLevel = levels.remove();
    }

    @Override
    public void tick() {
        currentLevel.tick();
        if (currentLevel.levelFinished()) {
            if (currentLevel.levelLost() || levels.isEmpty()) {
                gsm.setStateTo(GameStateManager.States.MAIN_MENU);
                wholeGameScore += currentLevel.getScore();
                return;
            }
            nextLevel();
        }
    }

    @Override
    public void draw(Graphics g) {
        currentLevel.draw(g);
    }

    @Override
    public void keyPressed(int k) {
        currentLevel.keyPressed(k);
    }

    @Override
    public void keyReleased(int k) {
        currentLevel.keyReleased(k);
    }
}
