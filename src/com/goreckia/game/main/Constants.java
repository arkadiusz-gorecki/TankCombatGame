package com.goreckia.game.main;

public class Constants {
    public static final int FPS = 50;
    public static final int MAX_ENEMIES = 4; // max number of enemy tanks on the map at one time
    public static final int CELL_SIZE = 48; // size in pixels
    public static final int HALF_CELL_SIZE = CELL_SIZE / 2;
    public static final int QUARTER_CELL_SIZE = CELL_SIZE / 4;
    public static final int TANK_SIZE = CELL_SIZE;
    public static final int SHELL_SIZE = 12;
    public static final int MAP_CELLS = 13; // size of the map in cells
    public static final int MAP_HALF_CELLS = MAP_CELLS * 2;
    public static final int MAP_SIZE = MAP_CELLS * CELL_SIZE;
    public static final int PANEL_SIZE = MAP_SIZE;
    public static final int PLAYER_STARTING_POSITION_X = 9 * HALF_CELL_SIZE;
    public static final int PLAYER_STARTING_POSITION_Y = 12 * CELL_SIZE;
}
