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

    public class Textures {
        public class Texture4Directions
        {
            private BufferedImage up;
            private BufferedImage down;
            private BufferedImage left;
            private BufferedImage right;

            public BufferedImage getUp() {
                return up;
            }
            public BufferedImage getDown() {
                return down;
            }
            public BufferedImage getLeft() {
                return left;
            }
            public BufferedImage getRight() {
                return right;
            }

            public Texture4Directions(BufferedImage texture) {
                up = texture;
                down = rotateImage(copy(texture), 180);
                left = rotateImage(copy(texture), 270);
                right = rotateImage(copy(texture), 90);
            }

            private BufferedImage copy(BufferedImage image)
            {
                BufferedImage copy = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_RGB);
                Graphics g = copy.createGraphics();
                g.drawImage(image, 0, 0, null);
                return copy;
            }

            private BufferedImage rotateImage(BufferedImage sourceImage, double angle) {
                int width = sourceImage.getWidth();
                int height = sourceImage.getHeight();
                BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
                Graphics2D g2d = result.createGraphics();

                AffineTransform transform = new AffineTransform();
                transform.rotate(angle / 180 * Math.PI, width / 2 , height / 2);
                g2d.drawRenderedImage(sourceImage, transform);

                g2d.dispose();
                return result;
            }

            private BufferedImage rotate(BufferedImage image, int angle)
            {
                double rads = Math.toRadians(angle); // angle in degrees
                double sin = Math.abs(Math.sin(rads));
                double cos = Math.abs(Math.cos(rads));
                int w = (int) Math.floor(image.getWidth(null) * cos + image.getHeight(null) * sin);
                int h = (int) Math.floor(image.getHeight(null) * cos + image.getWidth(null) * sin);
                BufferedImage result = new BufferedImage(w, h, image.getType());
                AffineTransform at = new AffineTransform();
                at.translate(w / 2, h / 2);
                at.rotate(rads,0, 0);
                at.translate(-image.getWidth(null) / 2, -image.getHeight(null) / 2);
                AffineTransformOp rotateOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
                rotateOp.filter(image, result);
                return result;
            }

        }
        private Texture4Directions playerTankTexture;
        private Texture4Directions enemyTankTexture;
        private BufferedImage brickTexture;

        public Texture4Directions getPlayerTankTextures() {
            return playerTankTexture;
        }
        public Texture4Directions getEnemyTankTextures() {
            return enemyTankTexture;
        }
        public BufferedImage getBrickTextureImage() {
            return brickTexture;
        }

        public Textures (String folderPath) {
            try {
                playerTankTexture = new Texture4Directions(ImageIO.read(new File(folderPath + "\\player_tank.bmp")));
                enemyTankTexture = new Texture4Directions(ImageIO.read(new File(folderPath + "\\enemy_tank.bmp")));
                brickTexture = ImageIO.read(new File(folderPath + "\\brick.bmp"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public PlayingState(GameStateManager gsm, String folderPath, String texturesPath) {
        super(gsm);
        textures = new Textures(".\\src\\com\\goreckia\\game\\resources\\textures");
        playerTank = new PlayerTank(textures);
        loadLevels(folderPath);
        nextLevel(); // load first level
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

    private Level levelFromString(String fileString)
    {
        String[] data = fileString.split("#\r\n"); // FIXME newline character depends on what?
        String[] enemiesStrings = data[0].split("\r\n");
        String[] obstaclesStrings = data[1].split("\r\n");

        Queue<EnemyTank> enemies = new LinkedList<>();
        List<Obstacle> obstacles = new ArrayList<>();
        Class cl;
        Class[] params = new Class[] {Textures.class};
        for (String enemyString : enemiesStrings) {
            try {
                cl = Class.forName(enemyString);
                Constructor con = cl.getConstructor(params);
                enemies.add((EnemyTank) con.newInstance(textures));
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }

        params = new Class[] {int.class, int.class, Textures.class};
        for (String obstacleString : obstaclesStrings)
        {
            String[] obstacleData = obstacleString.split(":");
            String className = obstacleData[0];
            int xHalfCells = Integer.parseInt(obstacleData[1]);
            int yHalfCells = Integer.parseInt(obstacleData[2]);
            try {
                cl = Class.forName(className);
                Constructor con = cl.getConstructor(params);

                obstacles.add((Obstacle)con.newInstance(xHalfCells, yHalfCells, textures));
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return new Level(playerTank, enemies, obstacles);
    }

//    private static class Levels {
//        private static int index = 0;
//        private static Level level1 = new Level(null, Arrays.asList(new Obstacle[]{new Brick(0, 0)}), null);
//        private static Level level2 = new Level(null, Arrays.asList(new Obstacle[]{new Brick(100, 100), new Brick(150, 100)}), null);
//
//        private static void generateLevelFiles() {
//            szerialize(level1);
//            szerialize(level2);
//        }
//
//        private static void szerialize(Level l) {
//            String fileName = l.getClass().getName();
//            try {
//                ObjectOutputStream objectOutputStream;
//                objectOutputStream = new ObjectOutputStream(new FileOutputStream("..\\level" + index + ".level"));
//                index++;
//                objectOutputStream.writeObject(l);
//                objectOutputStream.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }

//    private void loadLevels(String folderPath) {
//        levels = new LinkedList<>();
//
//        Consumer<Path> createLevelFromFile = path -> {
//            File f = new File(path.toString());
//            Properties prop = new Properties();
//            try {
//                InputStream is = new FileInputStream(f);
//                prop.loadFromXML(is);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            createLevelFromProperties(prop);
//        };
//
//        try {
//            DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(folderPath), path -> path.toString().endsWith(".xml"));
//            stream.forEach(createLevelFromFile);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        levels.add(new Level(playerTank, new ArrayList<>(), new LinkedList<>()));
//    }

//    private void createLevelFromProperties(Properties levelProperties)
//    {
//        for (Map.Entry<Object, Object> entry : levelProperties.entrySet()) {
//            String className = (String)entry.getKey();
//            String properties = (String)entry.getValue();
//
//        }
//    }


    //private void loadTextures(String path) {
    //    playerTank = new PlayerTank(path);
    //}

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
