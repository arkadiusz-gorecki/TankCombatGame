package com.goreckia.game.states;

import java.awt.*;

public class GameStateManager {
    private MainMenuState mainMenuState;
    private PlayingState playingState;
    private State currentState;

    public enum States {MAIN_MENU, PLAYING, SETTINGS};

    public GameStateManager() {
        mainMenuState = new MainMenuState(this);
        playingState = new PlayingState(this);
        currentState = mainMenuState;
    }

    public void tick() {
        currentState.tick();
    }

    public void draw(Graphics g) {
        currentState.draw(g);
    }

    public void keyPressed(int k) {
        currentState.keyPressed(k);
    }

    public void keyReleased(int k) {
        currentState.keyReleased(k);
    }

    public void setStateTo(States s) {
        switch (s) {
            case MAIN_MENU:
                currentState = mainMenuState;
                break;
            case PLAYING:
                currentState = playingState;
                break;
            case SETTINGS:
                break;
        }
    }
}
