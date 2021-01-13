package com.goreckia.game.states;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Textures {
    private Map<String, Image> texturesMap;

    public Textures(String folderPath) {
        texturesMap = new HashMap();
        try {
            texturesMap.put("Brick", ImageIO.read(new File(folderPath + "\\brick.bmp")));
            texturesMap.put("EnemyTank", ImageIO.read(new File(folderPath + "\\enemy_tank.bmp")));
            texturesMap.put("PlayerTank", ImageIO.read(new File(folderPath + "\\player_tank.bmp")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Image getTexture(String className) {
        return texturesMap.get(className);
    }
}