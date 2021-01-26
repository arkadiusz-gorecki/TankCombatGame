package com.goreckia.game.states;

import com.goreckia.game.level.Level;
import com.goreckia.game.level.obstacles.Obstacle;
import com.goreckia.game.level.tanks.EnemyTank;
import com.goreckia.game.level.tanks.PlayerTank;
import com.goreckia.game.utils.Pair;
import com.goreckia.game.utils.Textures;

import java.awt.*;
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

import static com.goreckia.game.main.Constants.CELL_SIZE;

public class PlayingState extends State {
    private Queue<Level> levels;
    private Level currentLevel;
    private PlayerTank playerTank;
    private int wholeGameScore = 0;
    private int enemySpawnNumber = 0;

    public PlayingState(GameStateManager gsm, String folderPath, String texturesPath) {
        super(gsm);
        Textures textures = loadTextures(texturesPath);
        playerTank = new PlayerTank(textures);
        levels = loadLevels(folderPath, textures);
        nextLevel(); // load first level
    }

    private Textures loadTextures(String texturesPath) {
        return new Textures(texturesPath);
    }

    private Queue<Level> loadLevels(String folderPath, Textures textures) {
        Queue<Level> result = new LinkedList<>();

        Consumer<Path> createLevelFromFile = path -> {
            try {
                result.add(levelFromString(Files.readString(path), textures));
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
        return result;
    }

    private Level levelFromString(String fileString, Textures textures) {
        fileString = fileString.replaceAll("\\r\\n|\\r", "\n");
        String[] data = fileString.split("#\n");
        String[] enemiesStrings = data[0].split("\n");
        String[] obstaclesStrings = data[1].split("\n");

        Queue<EnemyTank> enemies = new LinkedList<>();
        List<Obstacle> obstacles = new ArrayList<>();
        Class cl;
        Class[] params = new Class[]{Textures.class, Pair.class};
        for (String enemyString : enemiesStrings) {
            try {
                cl = Class.forName("com.goreckia.game.level.tanks." + enemyString);
                Constructor con = cl.getConstructor(params);
                enemies.add((EnemyTank) con.newInstance(textures, nextEnemySpawnPosition()));
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
                cl = Class.forName("com.goreckia.game.level.obstacles." + className);
                Constructor con = cl.getConstructor(params);

                obstacles.add((Obstacle) con.newInstance(xHalfCells, yHalfCells, textures));
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return new Level(playerTank, enemies, obstacles, textures);
    }

    private Pair<Integer, Integer> nextEnemySpawnPosition() {
        Pair<Integer, Integer> result = new Pair<>(enemySpawnNumber * 6 * CELL_SIZE, 0);
        enemySpawnNumber++;
        enemySpawnNumber %= 3;
        return result;
    }

    private void nextLevel() {
        currentLevel = levels.remove();
    }

    @Override
    public void tick() {
        currentLevel.tick();
        if (currentLevel.levelFinished()) {
            if (currentLevel.levelLost() || levels.isEmpty()) {
                gsm.setStateTo(GameStateManager.MAIN_MENU);
                wholeGameScore += currentLevel.getScore();
                return;
            }
            nextLevel();
        }
    }

    @Override
    public void keyPressed(int k) {
        currentLevel.keyPressed(k);
    }

    @Override
    public void keyReleased(int k) {
        currentLevel.keyReleased(k);
    }

    @Override
    public void draw(Graphics g) {
        currentLevel.draw(g);
    }

}
