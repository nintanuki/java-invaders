# CHANGELOG

## 2026-05-12 — Reset to plain Swing, blank window only

**Why:** the previous libGDX-based code did not compile reliably and stacked
too much new vocabulary on top of "I'm still learning Java". Decided to
restart from a minimum viable state using only the JDK (Swing/AWT), then
re-add features one at a time.

**What changed:**

- `pom.xml` — removed all libGDX dependencies. Project now has zero external
  dependencies. Added `exec-maven-plugin` so `mvn compile exec:java` runs the
  game.
- `src/main/java/com/spaceinvaders/game/Main.java` — replaced libGDX
  bootstrap with a `JFrame` + `SwingUtilities.invokeLater` setup.
- `src/main/java/com/spaceinvaders/game/GameManager.java` — replaced
  `ApplicationAdapter`/`SpriteBatch`/`Texture` with `JPanel` +
  `javax.swing.Timer`. `update()` and `paintComponent()` are stubs that draw
  a blank window.
- `src/main/java/com/spaceinvaders/game/Settings.java` — trimmed to the
  values the blank-window state actually uses (title, size, FPS, background
  color).
- `README.md` — rewrote with run instructions, project layout, and a
  pygame → Swing translation table.
- `docs/ARCHITECTURE.md` — written from scratch to describe the runtime
  flow and Python → Java file mapping.
- `docs/ROADMAP.md` — new file. Step-by-step plan from blank window to
  feature parity with the Python original.
- `docs/JAVA_NOTES.md` — new file. Java concepts (classes, packages, Maven,
  static, final, generics…) explained in Python terms.
- `.gitignore` — added `target/` so Maven's build output isn't committed.

**Editor:** Frankie, assisted by Claude (Sonnet 4.6 via Cowork).
