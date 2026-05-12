# Roadmap

A step-by-step plan to take this project from "blank window" to "fully
playable Space Invaders, optionally re-implemented on libGDX". Each milestone
is small enough to finish in one sitting and ends with something you can
*see* working.

The rule for every milestone: **do not start the next one until the current
one runs without errors.** That's the discipline the first attempt missed.

---

## Milestone 0 — Blank window ✅ (you are here)

A 600x600 window opens, painted dark grey, ticking at 60 FPS. No sprites,
no input, no game logic. The point of this milestone is to prove the
toolchain works: `mvn compile exec:java` should produce a window.

**Files in play:** `Main.java`, `GameManager.java`, `Settings.java`.

If this doesn't run cleanly, fix it before continuing. Common failure modes:

- `mvn` not on PATH → install Maven or use the plain `javac` / `java`
  commands in the README.
- Java version mismatch → check `java -version`; bump
  `maven.compiler.source` / `target` in `pom.xml` if needed.

---

## Milestone 1 — Draw the player ship

Goal: a single player sprite shows up centered along the bottom of the
window. Nothing moves yet.

What to add:

1. A loader utility that reads a PNG from `src/main/resources/graphics/`:
   ```java
   BufferedImage img = ImageIO.read(getClass().getResource("/graphics/player.png"));
   ```
2. A `Player` class in a new `com.spaceinvaders.game.core` package that
   holds the image, its `x`/`y` position, and a `draw(Graphics2D g)` method.
3. In `GameManager`, hold a `Player` field, construct it in the constructor,
   and call `player.draw(g2)` from `paintComponent`.

What you'll have learned: how Java handles **resources** (`getResource`),
**packages** (folder + `package` declaration), and **subclassing-free
composition** (`GameManager` *has a* `Player` rather than *is a* `Player`).

The Python analogue lives in `core/sprites.py` (the `Player` class) and in
`main.py` where the player sprite is constructed.

---

## Milestone 2 — Keyboard input + player movement

Goal: left/right arrow keys move the ship, clamped to the window.

What to add:

1. A `KeyAdapter` (Swing's anonymous key-listener helper) attached to the
   `JPanel`. Track which arrow keys are currently held in a `boolean[]` or
   `EnumSet`.
2. Make the `JPanel` focusable (`setFocusable(true)` and
   `requestFocusInWindow()` after `setVisible`) so it actually receives key
   events.
3. Move `player.x` in `update()` based on the held-key flags and
   `Settings.PLAYER_SPEED`. Clamp to `[0, SCREEN_WIDTH - playerWidth]`.

What you'll have learned: Java's **listener pattern** (the equivalent of
pygame's `event.type == KEYDOWN`). This pattern shows up everywhere in
Swing.

---

## Milestone 3 — Lasers (player firing)

Goal: pressing space spawns a laser that travels up and disappears off-screen.

What to add:

1. A `Laser` class in `core/`.
2. A `List<Laser> lasers` field in `Player` (or `GameManager`).
3. A cooldown timer so you can't spam-fire (mirrors
   `PlayerSettings.LASER_COOLDOWN`).
4. Each frame: update all lasers, then remove any that scrolled off-screen
   (use `list.removeIf(...)` — Java's closest thing to a Python list
   comprehension filter).

What you'll have learned: Java **generics** (`List<Laser>` vs raw `List`),
**`Iterator.remove()`** vs `removeIf`, and the cost of allocating new
objects every frame.

---

## Milestone 4 — Alien grid

Goal: a static grid of alien sprites appears at the top of the screen,
colored by row, matching the Python game.

What to add:

1. An `Alien` class in `core/` that takes a color string ("yellow", "green",
   "red") and loads the matching PNG.
2. An alien-grid builder in `GameManager` that creates `rows * cols` aliens
   based on `Settings.ALIEN_ROWS` / `ALIEN_COLS`.
3. Store them in a `List<Alien>`; iterate to draw and update.

You'll be re-creating the `alien_setup()` method from the Python `Game`
class. Look at it for reference.

---

## Milestone 5 — Alien movement + edge bounce

Goal: the wave drifts horizontally, drops a row when it touches an edge.

This is a direct port of `alien_position_checker()` and `alien_move_down()`
from `main.py`.

---

## Milestone 6 — Collisions

Goal: laser hits alien → alien dies, score goes up.

What to add:

1. Each sprite needs a `Rectangle getBounds()` method. Java's
   `java.awt.Rectangle` has a built-in `intersects(Rectangle)` test — that
   replaces pygame's `spritecollide`.
2. A `collisionChecks()` method on `GameManager`.

---

## Milestone 7 — Alien lasers, player lives, game over

Mirror the rest of `main.py`: alien-shooting timer, life counter, score
HUD, "YOU WON" / "GAME OVER" overlay.

For HUD text, use `g2.setFont(new Font("Monospaced", Font.BOLD, 20))` and
`g2.drawString(...)`. Custom pixel font (`Pixeled.ttf`) loading is a small
extra step using `Font.createFont(Font.TRUETYPE_FONT, stream)`.

---

## Milestone 8 — Bunkers (destructible obstacles)

Port `ObstacleSettings.SHAPE` and the `Block` class from the Python game.
This milestone introduces a `Block` sprite and a 2D grid loop.

---

## Milestone 9 — Audio

Goal: laser, explosion, and music play.

`javax.sound.sampled.Clip` plays short WAVs; for longer music use
`SourceDataLine`. There's no built-in volume slider per clip — you use a
`FloatControl(FloatControl.Type.MASTER_GAIN)`.

---

## Milestone 10 — CRT overlay

Port `ui/crt.py`. The Python version uses a TV image with a translucent
scanline pattern. Java equivalent: load `tv.png`, draw it last in
`paintComponent`, and draw alternating semi-transparent horizontal lines on
top.

---

## Milestone 11 — Pause + fullscreen

Port the pause overlay and ESC/F11 handling from `main.py`. Fullscreen in
Swing is `GraphicsDevice.setFullScreenWindow(frame)`.

---

## Milestone 12 — Polish

Score persistence, controller support (`java.awt.event.MouseEvent` /
optional JInput library), better window icon, packaging as a runnable JAR
(`mvn package`), splash screen, etc.

At this point you'll have rewritten every feature of the Python game in
plain Java, and you'll know Java the language pretty well.

---

## Milestone 13 (optional but recommended) — Port to libGDX

Now's the time. With the game working in Swing, you can port it to libGDX
*one layer at a time* without breaking anything:

1. Create a parallel `libgdx` branch in git so you can compare side by side.
2. Re-introduce libGDX as a Maven dependency in `pom.xml`.
3. Replace `Main`'s `JFrame` with `Lwjgl3Application` (Gemini's original
   step, but now you understand what it's doing).
4. Replace `GameManager extends JPanel` with `GameManager extends
   ApplicationAdapter`.
5. Replace `paintComponent(Graphics g)` with `render()` and `SpriteBatch`.
6. Replace `BufferedImage` with `Texture`, and `KeyAdapter` with
   `Gdx.input`.
7. The `Player`/`Alien`/`Laser` classes barely change — they hold positions
   and bounds, which are framework-agnostic. Only their `draw()` method
   swaps `Graphics2D` for `SpriteBatch`.

The whole point of doing Swing first is that by this milestone the *only*
thing you're learning is libGDX itself. Java the language, your game's
architecture, and Maven are all already familiar.

If you don't end up needing libGDX (because the Swing version is fast
enough — and for a game this size it absolutely is), that's also fine. The
Swing version is a complete, shippable game.

---

## When you get stuck

For each milestone, the Python original is the spec. Open the corresponding
function in `arcade-cabinet/games/sponsor/tutorial/space-invaders/main.py`
(or `core/sprites.py`) and translate it method by method. Don't try to
"design" anything new — just port. The design work was already done in the
Python version.
