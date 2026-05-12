package com.spaceinvaders.game;

import javax.swing.JFrame; // The main window class in Swing. It can hold other components and has a title bar, borders, etc.
import javax.swing.SwingUtilities;

/**
 * Application entry point.
 *
 * <p>This file is the Java equivalent of {@code if __name__ == '__main__':} at
 * the bottom of {@code main.py}. The JVM looks for a {@code public static void
 * main(String[] args)} method and runs it; nothing else happens automatically.
 *
 * <p>Responsibilities are intentionally tiny:
 * <ol>
 *   <li>Create the window ({@link JFrame}).</li>
 *   <li>Drop the game panel ({@link GameManager}) inside it.</li>
 *   <li>Show the window and let the game's own timer drive every frame.</li>
 * </ol>
 *
 * <p>All gameplay logic lives in {@link GameManager}, the way the {@code Game}
 * class in {@code main.py} owns the loop in the Python version.
 */
public class Main {

  /**
   * JVM entry point.
   *
   * @param args command-line arguments (unused for now)
   */
  public static void main(String[] args) {
    // Swing is not thread-safe. The convention is to build and show the window
    // on the "Event Dispatch Thread" (EDT) by handing the work to
    // SwingUtilities.invokeLater. Think of it as Swing's asyncio loop: every UI
    // interaction has to happen on that one thread.
    SwingUtilities.invokeLater(Main::createAndShowWindow);
  }

  /** Build the JFrame, attach the game panel, and display it. */
  private static void createAndShowWindow() {
    JFrame window = new JFrame(Settings.GAME_TITLE);
    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    window.setResizable(false);

    // GameManager is a JPanel that knows how to draw itself and tick a timer.
    // Adding it to the window is the Swing equivalent of "screen = ..." +
    // "while True: screen.fill(...); pygame.display.flip()" — once it's
    // attached, Swing repaints it whenever GameManager asks.
    GameManager game = new GameManager();
    window.add(game);

    // pack() resizes the window to fit its contents (the GameManager's
    // preferred size, which we set in its constructor). This is cleaner than
    // setting a hard pixel size on the frame, because window decorations
    // (title bar, borders) vary by OS.
    window.pack();
    window.setLocationRelativeTo(null); // center on screen
    window.setVisible(true);

    // Kick off the game loop after the window is shown.
    game.start();
  }
}
