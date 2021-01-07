package com.goreckia.game.states;

import com.goreckia.game.main.GamePanel;

import java.awt.*;
import java.awt.event.KeyEvent;

public class MainMenuState extends State {

    private String[] options = {"PLAY", "EXIT"};
    private int currentOption;

    public MainMenuState(GameStateManager gsm) {
        super(gsm);
    }

    @Override
    public void tick() {
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.DARK_GRAY);
        g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Impact", Font.BOLD | Font.ITALIC, 100));
        g.drawString("TANK COMBAT", GamePanel.WIDTH / 2 - 280, 100);

        g.setFont(new Font("Impact", Font.PLAIN, 72));
        for (int i = 0; i < options.length; i++) {
            if (i == currentOption)
                g.setColor(new Color(200, 30, 30));
            else
                g.setColor(Color.WHITE);
            g.drawString(options[i], GamePanel.WIDTH / 2 - 75, 300 + 100 * i);
        }
        // display highscore at the bottom
    }

    @Override
    public void keyPressed(int k) {
        switch (k) {
            case KeyEvent.VK_W:
            case KeyEvent.VK_UP:
                currentOption--;
                if (currentOption < 0)
                    currentOption = options.length - 1;
                break;
            case KeyEvent.VK_S:
            case KeyEvent.VK_DOWN:
                currentOption++;
                if (currentOption >= options.length)
                    currentOption = 0;
                break;
            case KeyEvent.VK_ENTER:
                switch (currentOption) {
                    case 0: // PLAYING
                        gsm.setStateTo(GameStateManager.States.PLAYING);
                        break;
                    case 1: // EXIT
                        System.exit(0);
                        break;
                }
                break;
        }
    }

    @Override
    public void keyReleased(int k) {

    }
}
