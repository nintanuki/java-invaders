# java-invaders

Space Invaders made in Java — a port of the [Python/pygame
original](../arcade-cabinet/games/sponsor/tutorial/space-invaders) that lives
next door. The goal here is to learn Java by rebuilding a game I already
understand, so the structure intentionally mirrors the Python project file by
file.

> **Current state:** a blank window opens at 800x600 and runs at 60 FPS. That
> is it. From here every feature gets ported one at a time. The plan is in
> [`docs/ROADMAP.md`](docs/ROADMAP.md).

## Run it

You need Java 11 or newer and Maven installed.

```bash
mvn compile exec:java
```

That single command compiles the source and launches the game. Close the
window (or hit the X) to stop it.

If you do not want to use Maven, you can compile and run by hand:

```bash
# from the project root
javac -d target/classes src/main/java/com/spaceinvaders/game/*.java
java  -cp target/classes com.spaceinvaders.game.Main
```

## Project layout

```
java-invaders/
├── pom.xml                  # Maven project file (zero external deps right now)
├── src/main/java/           # All Java source code goes here
│   └── com/spaceinvaders/game/
│       ├── Main.java        # JVM entry point — creates the window
│       ├── GameManager.java # The game (window panel, frame timer, drawing)
│       └── Settings.java    # All tuning constants in one place
├── src/main/resources/      # Images, fonts, audio — loaded at runtime
│   ├── audio/
│   ├── font/
│   └── graphics/
└── docs/
    ├── ARCHITECTURE.md      # How the pieces fit together
    ├── ROADMAP.md           # Step-by-step plan to feature parity
    ├── JAVA_NOTES.md        # Java concepts explained in Python terms
    ├── CHANGELOG.md         # History of meaningful changes
    └── TODO.md              # Scratch list
```

## Why no libGDX?

The first attempt at this project pulled in
[libGDX](https://libgdx.com/), a third-party game framework. libGDX is great,
but for "open a window and draw an image" it brings a lot of new vocabulary
(`ApplicationAdapter`, `SpriteBatch`, `Lwjgl3Application`, native libraries,
asset bundling…) on top of the Java and Maven vocabulary I'm still learning.

Java already includes a 2D graphics library, **Swing/AWT**, built into the
JDK. It is enough to render every sprite in the original game and it is the
direct conceptual analogue of pygame:

| Pygame                     | Java Swing/AWT                       |
| -------------------------- | ------------------------------------ |
| `pygame.display.set_mode`  | `JFrame` + `JPanel`                  |
| `screen.fill(color)`       | `JPanel`'s background + `paintComponent` |
| `pygame.time.Clock`        | `javax.swing.Timer`                  |
| `screen.blit(image, pos)`  | `Graphics2D.drawImage(image, x, y, this)` |
| `pygame.image.load(path)`  | `ImageIO.read(...)`                  |
| `pygame.event.get()`       | `KeyListener` / `KeyAdapter`         |
| `pygame.display.flip()`    | (not needed — Swing double-buffers)  |

Once the game is feature-complete in Swing, we'll have learned enough Java to
*maybe* port it to libGDX later if we want hardware-accelerated rendering. But
that's a deliberate choice for later, not the starting point.

## Where to go next

Open [`docs/ROADMAP.md`](docs/ROADMAP.md) — it walks through every milestone
between "blank window" and "fully playable Space Invaders" in order.
