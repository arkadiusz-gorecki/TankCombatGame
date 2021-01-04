package com.goreckia.game.main;

import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {
    private GamePanel gp;

    public GameFrame(String title) {
        setLayout(new BorderLayout());
        setTitle(title);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        add(new GamePanel(), BorderLayout.CENTER);
        pack();
        setResizable(false);
        setVisible(true);
    }
}
