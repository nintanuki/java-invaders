package com.spaceinvaders.game; // This line declares the package name. It helps organize your code and avoid naming conflicts.

import com.badlogic.gdx.ApplicationAdapter; // This is the base class for our game. It provides methods like create(), render(), and dispose() that we can override to define our game behavior.
import com.badlogic.gdx.graphics.Texture; // This class is used to load and represent images (like our player ship). It can be drawn on the screen using SpriteBatch.
import com.badlogic.gdx.graphics.g2d.SpriteBatch; // This class is used to efficiently draw 2D images (like our player ship) on the screen. It batches multiple draw calls together for better performance.
import com.badlogic.gdx.utils.ScreenUtils; // This class provides utility methods for working with the screen, such as clearing it with a specific color. We use it to clear the screen before drawing each frame.

/**
 * GameManager: The central hub for game logic and rendering.
 * Extends ApplicationAdapter to hook into the libGDX lifecycle.
 */
public class GameManager extends ApplicationAdapter {
    private SpriteBatch batch;
    private Texture playerShip;
    private boolean isRunning;

    /** Constructor: Initialize the game state. */
    public GameManager() {
        // Constructor: Initialize your "pygame" equivalent here later
        System.out.println("Initializing " + Settings.GAME_TITLE);
        this.isRunning = true;
    }

    /**
     * LIBGDX CREATE: This runs once the window/GPU is ready.
     * This is the "proper" place to load textures.
     */
    @Override
    public void create() {
        batch = new SpriteBatch();
        // LibGDX looks in src/main/resources by default
        playerShip = new Texture("graphics/player.png"); 
    }

    /** This is the main game loop, called every frame (60 times per second by default) */
    @Override
    public void render() {
        // Update
        update();

        // Render
        ScreenUtils.clear(0, 0, 0, 1); // Black background
        batch.begin();
        batch.draw(playerShip, 100, 100);
        batch.end();
    }

    /** Update game logic here (e.g., move player, check collisions) */
    private void update() {
        // Game logic goes here
    }

    /** We define our drawing here */
    private void draw() {
        // Clear screen
        // batch.begin()
        // batch.draw(playerTexture, playerX, playerY)
        // batch.end()
    }

    @Override
    public void dispose() {
        // Clean up memory
        batch.dispose();
        playerShip.dispose();
    }
}