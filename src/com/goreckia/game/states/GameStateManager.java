package com.goreckia.game.states;

import java.awt.*;

public class GameStateManager {
    private MainMenuState mainMenuState;
    private PlayingState playingState;
    private State currentState;

    public static final int MAIN_MENU = 0;
    public static final int PLAYING = 1;

    public GameStateManager() {
        mainMenuState = new MainMenuState(this);
        currentState = mainMenuState;
    }

    public void tick() {
        currentState.tick();
    }

    public void keyPressed(int k) {
        currentState.keyPressed(k);
    }

    public void keyReleased(int k) {
        currentState.keyReleased(k);
    }

    public void setStateTo(int state) {
        switch (state) {
            case MAIN_MENU:
                currentState = mainMenuState;
                break;
            case PLAYING:
                playingState = new PlayingState(this, "src/com/goreckia/game/resources/levels", "src/com/goreckia/game/resources/textures");
                currentState = playingState;
                break;
        }
    }

    public void draw(Graphics g) {
        currentState.draw(g);
    }

}
