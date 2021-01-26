package com.goreckia.game.main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class GameFrame extends JFrame {

    public GameFrame(String gameTitle) {
        setLayout(new BorderLayout());
        setTitle(gameTitle);
        try {
            setIconImage(ImageIO.read(new File("src/com/goreckia/game/resources/textures/player_tank.bmp")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        add(new GamePanel(), BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(null); // center the window; should be called after you either use setSize(x,y), or use pack()
        setResizable(false);
        setVisible(true);
    }
}
