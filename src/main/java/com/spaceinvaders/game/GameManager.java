package com.spaceinvaders.game;

import java.awt.Color; // For defining colors used in the game.
import java.awt.Dimension; // For setting the preferred size of the game panel.
import java.awt.Graphics; // For drawing on the game panel.
import java.awt.Graphics2D; // A more powerful version of Graphics that supports transformations, anti-aliasing, etc.
import javax.swing.JPanel; // The base class for the game panel. It provides a surface to draw on and can handle user input.
import javax.swing.Timer; // For creating a timer that fires events at regular intervals, which we will use for our game loop.
import com.spaceinvaders.game.core.Player; // The Player class, which represents the player's spaceship

/**
 * GameManager is the heart of the game — the rough Java equivalent of the
 * {@code Game} class in {@code main.py}.
 *
 * <p><b>What it owns right now:</b>
 * <ul>
 *   <li>A drawing surface (it extends {@link JPanel}, so anything painted onto
 *       its {@link Graphics} shows up in the window).</li>
 *   <li>A frame timer (Swing's {@link Timer}) that fires every
 *       {@link Settings#FRAME_DELAY_MS} milliseconds, exactly like pygame's
 *       {@code clock.tick(FPS)} loop.</li>
 * </ul>
 *
 * <p><b>How the loop runs each frame:</b>
 * <pre>
 *   timer fires --&gt; update()  (advance game state)
 *               --&gt; repaint() (Swing schedules a paint, which calls paintComponent)
 * </pre>
 * Right now {@link #update()} is empty and {@link #paintComponent(Graphics)}
 * just clears to the background color — i.e., a blank window. We will fill in
 * sprites, input, and collisions piece by piece. See {@code docs/ROADMAP.md}.
 */
public class GameManager extends JPanel {

  /** The frame timer. In pygame this role is played by {@code clock.tick(FPS)}. */
  private final Timer frameTimer; // Initialized in the constructor, started in start().de
  private Player player; // The player instance, which will be initialized in the constructor.

  /** Construct the panel: size it, set its background, and wire up the timer. */
  public GameManager() {
    // setPreferredSize tells the parent window how big we want to be. The
    // JFrame's pack() call in Main respects this number.
    setPreferredSize(new Dimension(Settings.Window.WIDTH, Settings.Window.HEIGHT));
    setBackground(Settings.Colors.BACKGROUND);

    // The timer fires an action every FRAME_DELAY_MS. The lambda is the
    // callback — same idea as `pygame.time.set_timer(event, ms)` plus the
    // handler, fused into one expression.
    frameTimer = new Timer(Settings.Window.FRAME_DELAY_MS, e -> tick());
    player = new Player(); // Create a new player instance when the GameManager is initialized.
  }

  /** Start the frame timer. Called by {@link Main} once the window is visible. */
  public void start() {
    frameTimer.start();
  }

  /** Called every frame by the timer. Update first, then ask Swing to redraw. */
  private void tick() {
    update();
    repaint();
  }

  /**
   * Advance game state by one frame. Empty for now — this is where player
   * movement, alien movement, laser updates, and collisions will live.
   */
  private void update() {
    // TODO: sprite updates will land here. See docs/ROADMAP.md.
  }

  /**
   * Draw one frame.
   *
   * <p>Swing calls this for us whenever the panel needs to be redrawn. We do
   * NOT call it directly; we call {@link #repaint()} and Swing schedules the
   * paint on the EDT. The {@link Graphics} argument is essentially pygame's
   * "screen surface" — we draw onto it and Swing flips it to the display.
   *
   * @param g the drawing surface Swing hands us
   */
  @Override // This is a note to the compiler saying: "I know JPanel already has a paintComponent method, but I want to replace its version with mine".
  protected void paintComponent(Graphics g) { // g is the paintbrush Swing gives us to draw on the panel
    // ALWAYS call super.paintComponent first (the original version we just replaced).
    // JPanel uses it to clear the panel to its background color (set in the constructor).
    // Skipping this call causes visual glitches (trails) when the panel is resized or revealed.
    super.paintComponent(g);  // Clears the screen

    // TODO: sprite drawing will land here. See docs/ROADMAP.md.
    // For now the background fill from super.paintComponent() is the whole
    // frame, which is exactly the "blank window" we want.
    //
    // When we start drawing sprites we'll cast g to Graphics2D for the richer
    // 2D API: `Graphics2D g2 = (Graphics2D) g;` then call g2.drawImage(...)
    // etc. Don't dispose g — Swing owns it. Java's JPanel also handles
    // double-buffering for us, so there's no equivalent of
    // `pygame.display.flip()` to call.

    // Swing gives us a Graphics object for backwards compatibility, but we want to use the more powerful Graphics2D API for drawing.
    // Cast the paintbrush to 2D to get better features
    Graphics2D g2 = (Graphics2D) g;

    // Draw the player sprite if it exists. In the future, we'll also draw aliens, lasers, and obstacles here.
    if (player != null) {
        player.draw(g2);
    }
  }
}
