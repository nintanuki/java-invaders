package com.spaceinvaders.game; // This line declares the package name. It helps organize your code and avoid naming conflicts.

/** Holds game configuration constants. */
public class Settings {
    // "public" means this variable can be accessed from any other class (like GameManager)
    // "static" means you don't need to create a "new Settings()" to use it
    // "final" is like a constant in Python (it can't be changed)
    public static final String GAME_TITLE = "Space Invaders - Java Edition";
    public static final int SCREEN_WIDTH = 800;
    public static final int SCREEN_HEIGHT = 600;
}