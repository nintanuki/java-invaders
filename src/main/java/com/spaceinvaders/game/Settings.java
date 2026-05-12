package com.spaceinvaders.game;

/**
 * Centralized configuration for Java Invaders.
 *
 * <p>This mirrors {@code settings.py} from the Python original: every tuning
 * value the game cares about lives here so that sprites, the renderer, and the
 * main loop can read constants from one place instead of hard-coding numbers.
 *
 * <p><b>Python -> Java translation:</b>
 * <ul>
 *   <li>{@code public}  = visible to any other class (like a top-level name in Python).</li>
 *   <li>{@code static}  = belongs to the class itself, not an instance. You read
 *       it as {@code Settings.SCREEN_WIDTH}, never {@code new Settings().SCREEN_WIDTH}.</li>
 *   <li>{@code final}   = the value cannot be reassigned. Java's closest thing
 *       to a Python constant.</li>
 * </ul>
 */
public class Settings {

  // Window
  public static final String GAME_TITLE = "Space Invaders - Java Edition";
  public static final int SCREEN_WIDTH = 800;
  public static final int SCREEN_HEIGHT = 600;

  // Frame pacing. 60 FPS -> ~16 ms between frames. Swing's Timer takes
  // milliseconds, so we store the period directly to avoid arithmetic later.
  public static final int FPS = 60;
  public static final int FRAME_DELAY_MS = 1000 / FPS;

  // Background fill (R, G, B 0-255). Matches the Python ColorSettings.BG_FILL.
  public static final int BG_R = 30;
  public static final int BG_G = 30;
  public static final int BG_B = 30;

  /** Private constructor:
   * without this Java would generate a default public constructor,
   * and then anyone could instantiate Settings.
   * */
  private Settings() {}
}
