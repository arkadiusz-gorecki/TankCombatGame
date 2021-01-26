package com.goreckia.game.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Textures {
    private Map<String, Image> texturesMap;

    public Image getTexture(String className) {
        return texturesMap.get(className);
    }

    public Textures(String folderPath) {
        texturesMap = new HashMap();
        try {
            texturesMap.put("Brick", ImageIO.read(new File(folderPath + "/brick.bmp")));
            texturesMap.put("EnemyTank", ImageIO.read(new File(folderPath + "/enemy_tank.bmp")));
            texturesMap.put("PlayerTank", ImageIO.read(new File(folderPath + "/player_tank.bmp")));
            texturesMap.put("Base", ImageIO.read(new File(folderPath + "/base.bmp")));
            texturesMap.put("PlayerShell", ImageIO.read(new File(folderPath + "/player_shell.bmp")));
            texturesMap.put("EnemyShell", ImageIO.read(new File(folderPath + "/enemy_shell.bmp")));
            texturesMap.put("Steel", ImageIO.read(new File(folderPath + "/steel.bmp")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}