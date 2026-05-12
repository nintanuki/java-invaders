package com.spaceinvaders.game; // This line declares the package name. It helps organize your code and avoid naming conflicts.

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application; // This is the class that creates the window and starts the game loop.
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration; // This class is used to configure the window (size, title, etc.) before launching the game.

/** Entry point of the application. */
public class Main {
    
    /**
     * Main method: This is where the program starts execution.
     * @param args Command-line arguments
     */
    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration(); // Create a new configuration object to set up our game window
        config.setTitle(Settings.GAME_TITLE); // Set the window title using our Settings class
        config.setWindowedMode(Settings.SCREEN_WIDTH, Settings.SCREEN_HEIGHT); // Set the window size using our Settings class
        
        // This launches the window and connects it to our GameManager
        new Lwjgl3Application(new GameManager(), config);
    }
}