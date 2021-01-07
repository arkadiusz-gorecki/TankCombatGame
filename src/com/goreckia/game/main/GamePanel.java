package com.goreckia.game.main;

import com.goreckia.game.states.GameStateManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GamePanel extends JPanel implements Runnable, KeyListener {
    private GameStateManager gsm;
    private boolean isRunning = false;
    private static final int FPS = 30;
    private long loopTime = 1000 / FPS;

    public static final int WIDTH = 1138; // 16:9 ratio
    public static final int HEIGHT = 640;

    public GamePanel() {
        gsm = new GameStateManager();
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        addKeyListener(this);
        setFocusable(true);
        Thread th = new Thread(this);
        th.start();
        isRunning = true;
    }

    @Override
    public void run() {
        long start, elapsed, wait;
        while (isRunning) {
            start = System.nanoTime();

            tick();
            repaint();

            elapsed = System.nanoTime() - start;
            wait = loopTime - elapsed / 1000000;
            if (wait > 0)
                try {
                    Thread.sleep(wait);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
        }
    }

    public void tick() {
        gsm.tick();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.clearRect(0, 0, this.getWidth(), this.getHeight());
        gsm.draw(g);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        gsm.keyPressed(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        gsm.keyReleased(e.getKeyCode());
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
}
