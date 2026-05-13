package com.spaceinvaders.game;
import java.awt.Color;

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

  /**
   * ===========================================================================
   * COLORS
   * ===========================================================================
   */
  public static class Colors {
    public static final int BG_R = 30;
    public static final int BG_G = 30;
    public static final int BG_B = 30;

    public static final Color BURNT_SIENNA = new Color(241, 79, 80);
    public static final Color WHITE = new Color(255,255,255);
    public static final Color NERO = new Color(30,30,30);

    public static final Color OBSTACLE = Settings.Colors.BURNT_SIENNA;
    public static final Color LASER = Settings.Colors.WHITE;
    public static final Color TEXT = Settings.Colors.WHITE;
    public static final Color BACKGROUND = Settings.Colors.NERO;
  }

  /**
   * ===========================================================================
   * SCREEN & DISPLAY SETTINGS
   * ===========================================================================
   */
  public static class Window {
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    public static final String GAME_TITLE = "Space Invaders - Java Edition";
    
    // Frame pacing. 60 FPS -> ~16 ms between frames. Swing's Timer takes
    // milliseconds, so we store the period directly to avoid arithmetic later.
    public static final int FPS = 60;
    public static final int FRAME_DELAY_MS = 1000 / FPS;

    // CRT Settings
    public static final int CRT_SCANLINE_HEIGHT = 3;
    // min/max alpha for CRT flicker each frame
    public static final int CRT_ALPHA_MIN = 75;
    public static final int CRT_ALPHA_MAX = 90;
  }

  /**
   * ===========================================================================
   * CONTROLLER MAPPING
   * ===========================================================================
   */
  public static class Controller {
    public static final int A_BUTTON = 0;
    public static final int BACK_BUTTON = 6;  // also known as SELECT on the cabinet panel
    public static final int START_BUTTON = 7;
    public static final int L1_BUTTON = 4;
    public static final int R1_BUTTON = 5;

    // Held simultaneously, this chord exits the game from anywhere, matching
    // the cabinet panel labels (L1 + R1 + START + SELECT).
    // But will this work in Java? Alternative to tuples
    public static final int[] QUIT_COMBO = {START_BUTTON, BACK_BUTTON, L1_BUTTON, R1_BUTTON};

    public static final double JOYSTICK_DEADZONE = 0.4;  // analog input below this magnitude is ignored
  }

  /**
   * ===========================================================================
   * PLAYER
   * ===========================================================================
   */
  public static class Player {
    public static final int SPEED = 5;
    public static final int LIVES = 3;
    public static final int LASER_COOLDOWN = 600;  // milliseconds between consecutive shots
    public static final double LASER_SFX_VOLUME = 0.5;
  }

  /**
   * ===========================================================================
   * ALIENS
   * ===========================================================================
   */
  public static class Aliens {
    public static final int ROWS = 6;
    public static final int COLS = 8;
    public static final int X_DISTANCE = 60;  // horizontal spacing between aliens in pixels
    public static final int Y_DISTANCE = 48;  // vertical spacing between alien rows in pixels
    public static final int X_OFFSET = 70;  // left padding of the alien grid in pixels
    public static final int Y_OFFSET = 100;  // top padding of the alien grid in pixels
    public static final int DESCEND_DISTANCE = 2;  // pixels dropped when the wave touches a screen edge

    // Per-color point values; row 0 is yellow, rows 1-2 are green, rest are red.
    public static final int POINTS_YELLOW = 300;
    public static final int POINTS_GREEN = 200;
    public static final int POINTS_RED = 100;

    public static final int LASER_INTERVAL_MS = 800;  // alien laser timer period
  }

  /**
   * ===========================================================================
   * EXTRA (UFO)
   * ===========================================================================
   */
  public static class Extra {
    public static final int SPEED = 3;  // pixels per frame
    public static final int Y_POSITION = 80;  // vertical pixel position the UFO streaks across at
    
    // with this work in Java? Alternative to tuples
    public static final int[] SPAWN_FRAMES_INITIAL = {40, 80};  // range used for first spawn timer
    public static final int[] SPAWN_FRAMES_NEXT = {400, 800};  // range used for subsequent spawns
    
    public static final int POINTS = 500;
  }

  /**
   * ===========================================================================
   * LASERS
   * ===========================================================================
   */
  public static class Lasers {
    public static final int WIDTH = 4;
    public static final int HEIGHT = 20;
    public static final int PLAYER_SPEED = -8;  // negative = travels up the screen
    public static final int ALIEN_SPEED = 6;  // positive = travels down the screen
    public static final int OFFSCREEN_MARGIN = 50;  // pixels past the screen edge before a laser kills itself
  }

  /**
   * ===========================================================================
   * OBSTACLES
   * ===========================================================================
   */
  public static class Obstacles {
    public static final int BLOCK_SIZE = 6;  // pixel size of one bunker brick
    public static final int AMOUNT = 4;  // number of bunkers evenly spaced along the bottom
    public static final int Y_START = 480;  // top pixel row of every bunker
    public static final int X_START_DIVISOR = 15;  // WIDTH / DIVISOR sets the per-bunker left padding

    // String array for the shape
    public static final String[] SHAPE = {
        "  xxxxxxx",
        " xxxxxxxxx",
        "xxxxxxxxxxx",
        "xxxxxxxxxxx",
        "xxxxxxxxxxx",
        "xxx     xxx",
        "xx       xx"
    };
  }

  /**
   * ===========================================================================
   * AUDIO
   * ===========================================================================
   */
  public static class Audio {
    public static final double MUSIC_VOLUME = 0.2;
    public static final double LASER_VOLUME = 0.5;
    public static final double EXPLOSION_VOLUME = 0.3;

    // this was the pygame.mixer flag for infinite looping
    // figure out how this will work in Java
    public static final int MUSIC_LOOPS = -1;
  }
  /**
   * ===========================================================================
   * FONT / HUD
   * ===========================================================================
   */
  public static class Font {
    public static final int SCORE_SIZE = 20;
    public static final int PAUSE_SIZE = 20;
    public static final int[] SCORE_TOPLEFT = {10, -10};  // negative y nudge keeps the cap-height aligned
    public static final int LIVES_TOP_MARGIN = 8;  // vertical pixel offset of life icons
    public static final int LIVES_SPACING = 10;  // horizontal pixels between two life icons
    public static final int LIVES_RIGHT_PADDING = 20;  // pixels of right padding before the first life icon
  }
  /**
   * ===========================================================================
   * RESOURCE PATHS
   * ===========================================================================
   */
  public static class Paths {
    public static final String RESOURCE_DIR = "src/main/resources//";

    // Subdirectories for organization; these are concatenated with RESOURCE_DIR to
    // form the full path to each asset.
    public static final String AUDIO_DIR = RESOURCE_DIR + "audio/";
    public static final String FONT_DIR = RESOURCE_DIR + "font/";
    public static final String GRAPHICS_DIR = RESOURCE_DIR + "graphics/";

    // Audio
    public static final String MUSIC = AUDIO_DIR + "music.wav";
    public static final String LASER = AUDIO_DIR + "laser.wav";
    public static final String EXPLOSION = AUDIO_DIR + "explosion.wav";

    // Graphics
    public static final String FONT = FONT_DIR + "Pixeled.ttf";
    public static final String PLAYER = GRAPHICS_DIR + "player.png";
    public static final String RED_ALIEN = GRAPHICS_DIR + "red.png";
    public static final String GREEN_ALIEN = GRAPHICS_DIR + "green.png";
    public static final String YELLOW_ALIEN = GRAPHICS_DIR + "yellow.png";
    public static final String UFO = GRAPHICS_DIR + "extra.png";
    public static final String TV = GRAPHICS_DIR + "tv.png";
  }

  /** Private constructor:
   * without this Java would generate a default public constructor,
   * and then anyone could instantiate Settings.
   * */
  private Settings() {}
}
