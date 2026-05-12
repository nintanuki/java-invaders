package com.spaceinvaders.game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;
import javax.swing.Timer;

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
  private final Timer frameTimer;

  /** Construct the panel: size it, set its background, and wire up the timer. */
  public GameManager() {
    // setPreferredSize tells the parent window how big we want to be. The
    // JFrame's pack() call in Main respects this number.
    setPreferredSize(new Dimension(Settings.SCREEN_WIDTH, Settings.SCREEN_HEIGHT));
    setBackground(new Color(Settings.BG_R, Settings.BG_G, Settings.BG_B));

    // The timer fires an action every FRAME_DELAY_MS. The lambda is the
    // callback — same idea as `pygame.time.set_timer(event, ms)` plus the
    // handler, fused into one expression.
    frameTimer = new Timer(Settings.FRAME_DELAY_MS, e -> tick());
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
  @Override
  protected void paintComponent(Graphics g) {
    // ALWAYS call super.paintComponent first. JPanel uses it to clear the
    // panel to its background color (set in the constructor). Skipping this
    // call causes visual glitches when the panel is resized or revealed.
    super.paintComponent(g);

    // TODO: sprite drawing will land here. See docs/ROADMAP.md.
    // For now the background fill from super.paintComponent() is the whole
    // frame, which is exactly the "blank window" we want.
    //
    // When we start drawing sprites we'll cast g to Graphics2D for the richer
    // 2D API: `Graphics2D g2 = (Graphics2D) g;` then call g2.drawImage(...)
    // etc. Don't dispose g — Swing owns it. Java's JPanel also handles
    // double-buffering for us, so there's no equivalent of
    // `pygame.display.flip()` to call.
  }
}
