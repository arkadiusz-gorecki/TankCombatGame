package com.goreckia.game.states;

import com.goreckia.game.states.level.Level;
import com.goreckia.game.states.level.obstacles.Obstacle;
import com.goreckia.game.states.tanks.EnemyTank;
import com.goreckia.game.states.tanks.PlayerTank;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;
import java.util.function.Consumer;

public class PlayingState extends State {
    private Queue<Level> levels;
    private Level currentLevel;
    private PlayerTank playerTank;
    private int wholeGameScore = 0;
    private Textures textures;

    public PlayingState(GameStateManager gsm, String folderPath, String texturesPath) {
        super(gsm);
        loadTextures(texturesPath);
        playerTank = new PlayerTank(textures);
        loadLevels(folderPath);
        nextLevel(); // load first level
    }

    private void loadTextures(String texturesPath) {
        textures = new Textures(texturesPath);
    }

    private void loadLevels(String folderPath) {
        levels = new LinkedList<>();

        Consumer<Path> createLevelFromFile = path -> {
            try {
                levels.add(levelFromString(Files.readString(path)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        };

        try {
            DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(folderPath), path -> path.toString().endsWith(".tcg"));
            stream.forEach(createLevelFromFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Level levelFromString(String fileString) {
        String[] data = fileString.split("#\r\n"); // FIXME newline character depends on what?
        String[] enemiesStrings = data[0].split("\r\n");
        String[] obstaclesStrings = data[1].split("\r\n");

        Queue<EnemyTank> enemies = new LinkedList<>();
        List<Obstacle> obstacles = new ArrayList<>();
        Class cl;
        Class[] params = new Class[]{Textures.class};
        for (String enemyString : enemiesStrings) {
            try {
                cl = Class.forName(enemyString);
                Constructor con = cl.getConstructor(params);
                enemies.add((EnemyTank) con.newInstance(textures));
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }

        params = new Class[]{int.class, int.class, Textures.class};
        for (String obstacleString : obstaclesStrings) {
            String[] obstacleData = obstacleString.split(":");
            String className = obstacleData[0];
            int xHalfCells = Integer.parseInt(obstacleData[1]);
            int yHalfCells = Integer.parseInt(obstacleData[2]);
            try {
                cl = Class.forName(className);
                Constructor con = cl.getConstructor(params);

                obstacles.add((Obstacle) con.newInstance(xHalfCells, yHalfCells, textures));
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return new Level(playerTank, enemies, obstacles);
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
