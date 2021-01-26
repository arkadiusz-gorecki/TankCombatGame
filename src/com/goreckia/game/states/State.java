package com.goreckia.game.states;

import java.awt.*;

public abstract class State {
    public GameStateManager gsm;

    public State(GameStateManager gsm) {
        this.gsm = gsm;
    }

    public abstract void tick();

    public abstract void keyPressed(int k);

    public abstract void keyReleased(int k);

    public abstract void draw(Graphics g);

}
