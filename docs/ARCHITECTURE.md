# Architecture

This document explains *how the code is wired together*. For a step-by-step
plan of features to add, see [`ROADMAP.md`](ROADMAP.md). For a Java
crash-course aimed at someone coming from Python, see
[`JAVA_NOTES.md`](JAVA_NOTES.md).

## File map: Python original → Java port

| Python (`space-invaders/`)         | Java (`java-invaders/`)                                     | Status |
| ---------------------------------- | ----------------------------------------------------------- | ------ |
| `main.py` (`Game` class + entry)   | `Main.java` (entry) + `GameManager.java` (the `Game` class) | done   |
| `settings.py`                      | `Settings.java`                                             | partial — only window/FPS/bg constants ported so far |
| `core/sprites.py` (Player, Alien…) | `core/` Java package (one class per sprite)                 | not started |
| `ui/crt.py`                        | `ui/Crt.java`                                               | not started |
| `assets/`                          | `src/main/resources/` (audio, font, graphics)               | files already in place; loader code not started |

The split of `main.py` into two Java files is intentional: in Java, the JVM
entry point (`public static void main(String[] args)`) is conventionally kept
in its own tiny class. The actual game lives in `GameManager`, the way the
`Game` class lives inside `main.py` in the Python version.

## Runtime flow

```
JVM starts
   │
   ▼
Main.main(args)
   │  schedules onto the Swing event-dispatch thread (EDT)
   ▼
Main.createAndShowWindow()
   ├── new JFrame(title)         ← the OS window
   ├── new GameManager()          ← the JPanel that draws everything
   │     ├── setPreferredSize(...)
   │     ├── setBackground(...)
   │     └── new Timer(FRAME_DELAY_MS, e -> tick())   ← stopped, not started yet
   ├── window.add(game)
   ├── window.pack(); window.setVisible(true)
   └── game.start()               ← starts the frame timer
              │
              ▼
        every FRAME_DELAY_MS ms, on the EDT:
              tick()
                ├── update()        ← (currently empty)
                └── repaint()       ← Swing schedules paintComponent
                          │
                          ▼
                  paintComponent(g)
                       ├── super.paintComponent(g)  ← clears to background
                       └── (sprite drawing will go here)
```

## Why two threads to think about?

There's only one thread you'll ever touch by name — the **Event Dispatch
Thread (EDT)**. Swing is single-threaded: every UI mutation (creating
widgets, calling `repaint()`, handling key events) must happen on the EDT.

The Python equivalent is "everything happens in `Game.run()` on the main
thread", which is simpler because pygame is not thread-aware. In Swing you
get the same single-threaded behavior, but you have to *opt in* by wrapping
your startup code in `SwingUtilities.invokeLater(...)`. We do that once in
`Main.main`, and after that point `javax.swing.Timer` already fires on the
EDT, so the rest of the code can ignore threading.

## Why Swing's Timer instead of a `while (true)` loop?

The Python version uses:

```python
while True:
    handle_events()
    update()
    render()
    clock.tick(FPS)
```

You *could* write the same `while (true)` loop in Java, but it would block
the EDT, and a blocked EDT means the window stops responding to mouse
clicks, key presses, and close-window events. `javax.swing.Timer` solves
that for free: it fires a callback every N ms on the EDT, interleaved with
event handling. That's why `GameManager` looks event-driven instead of
loop-driven.

## What lives where (Java package layout)

Right now everything is in one package: `com.spaceinvaders.game`. As the
port grows, the Python folder layout maps onto Java packages like this:

```
com.spaceinvaders.game         ← Main, GameManager, Settings  (= main.py + settings.py)
com.spaceinvaders.game.core    ← Player, Alien, Laser, Block… (= core/sprites.py)
com.spaceinvaders.game.ui      ← Crt                          (= ui/crt.py)
```

A **package** in Java is a folder + a `package` declaration at the top of
every file inside it. There's no `__init__.py`; the folder name and the
package name simply have to match. We'll create the sub-packages when we
actually need them (see the roadmap).

## Resources / assets

The `pom.xml` puts `src/main/resources/` on the classpath at build time.
That means once we add image loading we'll do:

```java
URL url = getClass().getResource("/graphics/player.png");
BufferedImage img = ImageIO.read(url);
```

The leading `/` means "look at the root of the resources folder", so
`/graphics/player.png` resolves to
`src/main/resources/graphics/player.png`. The asset folder is already
populated — we just haven't written the loader yet.
