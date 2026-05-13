package com.spaceinvaders.game.core;

import com.spaceinvaders.game.Settings; // We will need this for colors, dimensions, and other constants.
import java.awt.Graphics; // For drawing the player sprite.
import java.awt.Graphics2D; // A more powerful version of Graphics that supports transformations, anti-aliasing, etc.
import java.awt.image.BufferedImage; // For loading and holding the player's sprite image.
import java.io.IOException; // For handling potential exceptions when loading the sprite image.

import javax.imageio.ImageIO;
/**
 * The Player class represents the player's spaceship in the game. It is responsible for
 * handling the player's position, movement, and rendering the player's sprite on the screen.
 *
 * <p>Responsibilities:
 * <ul>
 *   <li>Store the player's current position and velocity.</li>
 *   <li>Handle player input to move left or right.</li>
 *   <li>Render the player's sprite at its current position.</li>
 * </ul>
 *
 * <p>Note: This class is a work in progress and will be expanded with additional functionality
 * such as shooting lasers, handling collisions, and managing player lives.
 */
public class Player {
    private BufferedImage image;
    private int x, y; // Player's current position
    private int speed; // Player's movement speed

    /**
     * Construct a new Player instance. Loads the player's sprite and sets the initial position.
     */
    public Player() {
        // load the image
        try {
            image = ImageIO.read(getClass().getResource(Settings.Paths.PLAYER)); // Load the player sprite from resources
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Set the initial position of the player.
        // Start centered horizontally, and at the bottom vertically
        this.x = (Settings.Window.WIDTH / 2) - (image.getWidth() / 2);
        this.y = Settings.Window.HEIGHT - image.getHeight();
    }

    /**
     * Draw the player on the screen at its current position.
     * @param g2
     */
    public void draw(Graphics2D g2) {
        // The fourth argument is an ImageObserver. In the 90s, when internet was slow, this was used
        // to track images as they loaded bit-by-bit from a server. Since our images are local and tiny,
        // we pass null because we don't need to observe the loading process.
        g2.drawImage(image, x, y, null); // Draw the player's sprite at its current position
    }
}

        
