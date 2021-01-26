package com.goreckia.game.states;

import com.goreckia.game.main.Constants;

import java.awt.*;
import java.awt.event.KeyEvent;


public class MainMenuState extends State {

    private final String[] options = {"PLAY", "EXIT"};
    private final String gameTitle = "TANK COMBAT";

    private int currentOption;

    public MainMenuState(GameStateManager gsm) {
        super(gsm);
    }

    @Override
    public void tick() {
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
            case KeyEvent.VK_SPACE:
                switch (currentOption) {
                    case 0 -> gsm.setStateTo(GameStateManager.PLAYING); // PLAYING
                    case 1 -> System.exit(0); // EXIT
                }
                break;
        }
    }

    @Override
    public void keyReleased(int k) {

    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.DARK_GRAY);
        g.fillRect(0, 0, Constants.PANEL_SIZE, Constants.PANEL_SIZE);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(Color.WHITE);
        int fontSize = Constants.PANEL_SIZE / gameTitle.length();
        Font font = new Font("Impact", Font.BOLD | Font.ITALIC, (int) (1.5 * fontSize));
        drawCenteredString(g, gameTitle, new Rectangle(0, 75, Constants.PANEL_SIZE, 0), font);

        font = new Font("Impact", Font.PLAIN, fontSize);
        for (int i = 0; i < options.length; i++) {
            if (i == currentOption)
                g.setColor(new Color(200, 30, 30));
            else
                g.setColor(Color.WHITE);
            drawCenteredString(g, options[i], new Rectangle(0, Constants.PANEL_SIZE / 2 + fontSize * i, Constants.PANEL_SIZE, 0), font);
        }
        // display highscore at the bottom
    }

    private void drawCenteredString(Graphics g, String text, Rectangle rect, Font font) {
        // https://stackoverflow.com/questions/27706197/how-can-i-center-graphics-drawstring-in-java
        FontMetrics metrics = g.getFontMetrics(font);
        int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
        int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
        g.setFont(font);
        g.drawString(text, x, y);
    }

}
