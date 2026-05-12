package com.spaceinvaders.game;

/** Entry point of the application. */
public class Main {
    
    /**
     * Main method: This is where the program starts execution.
     * @param args Command-line arguments
     */
    public static void main(String[] args) {
        // This is the Java equivalent of Python's "if __name__ == '__main__':"
        GameManager game = new GameManager(); // Create an instance of the GameManager class
        game.run(); // Call the run method to start the game
    }
}