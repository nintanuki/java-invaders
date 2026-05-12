# Java for Pythonistas

A cheat sheet of the Java concepts that show up in this codebase, each
explained in terms you already know from Python. Not exhaustive — just
"enough to read and write this game".

## The big mental shifts

| Python                                      | Java                                                                 |
| ------------------------------------------- | -------------------------------------------------------------------- |
| Source: `foo.py`                            | Source: `Foo.java` — file name **must** match the public class name. |
| Module = a file                             | **Class** = the basic unit. Files contain (typically) one public class. |
| Package = folder with `__init__.py`         | Package = folder + `package com.foo.bar;` at the top of every file. |
| `import foo` brings in a module             | `import com.foo.Foo;` brings in a single class.                      |
| Duck typing — "if it quacks like a duck"    | Static typing — every variable has a fixed type at compile time.    |
| `pip install x` → `requirements.txt`        | `mvn` reads `pom.xml`; deps go in `<dependencies>`.                  |
| `if __name__ == "__main__":` runs the file  | The JVM looks for `public static void main(String[] args)`.          |
| Indentation defines blocks                  | `{ }` defines blocks. Indentation is for humans only.                |

## Modifier keywords you'll see everywhere

```java
public static final int SCREEN_WIDTH = 600;
```

- `public` — visible to other classes (Python's default).
- `private` — visible only inside this class (Python's `_underscore` convention, but actually enforced).
- `protected` — visible to subclasses and same-package classes.
- (no modifier) — package-private; visible to classes in the same package.
- `static` — belongs to the class, not an instance. Read as `Settings.SCREEN_WIDTH`, not `new Settings().SCREEN_WIDTH`. Python's closest match: a class attribute defined at the top of a `class`.
- `final` — cannot be reassigned. For variables, that's like Python's "all caps means don't touch". For methods/classes, it means "no subclass can override / extend this".

## Types

Java is statically typed. You declare a type before every name:

```java
int hp = 100;
String name = "Player";
double x = 12.5;
boolean isAlive = true;
```

A few that trip Python folks up:

- `int` vs `Integer` — `int` is a primitive (a raw 32-bit number, no object overhead). `Integer` is the boxed object version. Use `int` unless you need to put one in a generic container (`List<Integer>`).
- `String` is immutable, just like Python's `str`. Use `+` to concatenate or `String.format(...)` for f-string equivalents.
- `var x = 5;` (Java 10+) lets the compiler infer the type, like Python's `x = 5`. Use it when the type is obvious from the right side.

## Generics in 30 seconds

```java
List<Laser> lasers = new ArrayList<>();  // a list that only holds Lasers
Map<String, Integer> scores = new HashMap<>();
```

The `<Laser>` is a *type parameter*. Python's `list[Laser]` from `typing` is the same idea — except in Java the compiler will actually refuse to put a `Player` into a `List<Laser>`.

## Classes & instances

```java
public class Laser {
    private final int speed;
    private double x;
    private double y;

    public Laser(double startX, double startY, int speed) {  // constructor
        this.x = startX;
        this.y = startY;
        this.speed = speed;
    }

    public void update() {
        y += speed;
    }

    public double getY() { return y; }
}
```

Python equivalent:

```python
class Laser:
    def __init__(self, start_x, start_y, speed):
        self.x = start_x
        self.y = start_y
        self.speed = speed

    def update(self):
        self.y += self.speed
```

Differences:

- `Laser(...)` is the constructor — same name as the class, no `def __init__`.
- `this` is Python's `self`, but it's implicit on field reads. You only need `this.` to disambiguate from a parameter of the same name.
- Java doesn't have `@property`. The convention is `getX()` / `setX(value)` methods.

## Inheritance & interfaces

```java
public class GameManager extends JPanel { ... }
public class Player implements KeyListener { ... }
```

- `extends` — single inheritance only. Like Python's `class GameManager(JPanel):`.
- `implements` — promises this class has all the methods listed in an *interface* (like an abstract base class in Python with only abstract methods).
- A class can `extends` one class **and** `implements` any number of interfaces.

`@Override` above a method is the equivalent of a sanity check — it tells the compiler "this is supposed to override a method from the parent". If it doesn't actually override anything, the compile fails. Use it always.

## Lambdas

```java
new Timer(16, e -> tick());                 // one-liner
button.addActionListener(e -> System.out.println("clicked"));
list.removeIf(laser -> laser.getY() < 0);   // like Python's filter
```

`a -> b` is Java's lambda, equivalent to Python's `lambda a: b`. Multi-statement bodies need braces:

```java
list.forEach(laser -> {
    laser.update();
    System.out.println(laser);
});
```

## Maven mental model

`pom.xml` (Project Object Model) is your `requirements.txt` + `setup.py` + a
build script.

```bash
mvn compile             # like `python -m py_compile *.py`, but real bytecode
mvn exec:java           # runs the configured main class
mvn package             # builds a JAR — like `python setup.py sdist bdist_wheel`
mvn clean               # deletes target/
```

What Maven *actually* does:

1. Reads `pom.xml`.
2. Downloads any declared dependencies into `~/.m2/repository/`.
3. Looks for sources in `src/main/java/`.
4. Looks for resources (images, fonts, audio) in `src/main/resources/`.
5. Compiles to `target/classes/`.
6. Optionally packages a `.jar` in `target/`.

Right now our `pom.xml` has zero dependencies, so step 2 is a no-op.

## Swing in one diagram

```
JFrame  ─── the OS window (title bar, close button, borders)
 └── add(JPanel)  ─── a drawable region you fill with paintComponent()
       └── KeyListener / MouseListener attached to handle input
       └── javax.swing.Timer attached to advance the game state
```

Every Swing UI mutation must happen on the **Event Dispatch Thread (EDT)**.
You enter the EDT once at startup via `SwingUtilities.invokeLater(...)`;
after that, `javax.swing.Timer` and all `KeyListener` callbacks already run
there, so you can stop worrying about it.

## Things Python has that Java does not (and what to do instead)

| Python                                | Java workaround                                                  |
| ------------------------------------- | ---------------------------------------------------------------- |
| Tuples                                | Use a small class or `java.awt.Point` / `record` (Java 14+).     |
| f-strings: `f"x={x}"`                 | `String.format("x=%d", x)` or `"x=" + x`.                        |
| Default arguments                     | Method overloading: write two methods with the same name.        |
| Keyword arguments                     | Don't exist. Use a builder pattern or just pass positionally.    |
| `list[1:3]` slicing                   | `list.subList(1, 3)`.                                            |
| `dict.get(key, default)`              | `map.getOrDefault(key, default)`.                                |
| `for x in iterable:`                  | `for (Foo x : iterable) { ... }` — works on anything `Iterable`. |
| List comprehensions                   | Streams: `list.stream().filter(...).map(...).collect(...)`.      |
| `None`                                | `null`.                                                          |
| `try / except / finally`              | `try { } catch (FooException e) { } finally { }`.                |

## When you don't know a class name

The JDK API docs are at <https://docs.oracle.com/en/java/javase/17/docs/api/>.
Search the class name; every method has examples and the parameter types
are explicit. It's noisier than Python's docs but more complete.

## A note on style

Java's community lints heavily. The conventions you'll see in this codebase:

- `ClassNamesAreUpperCamelCase`
- `methodNamesAreLowerCamelCase`
- `CONSTANT_NAMES_ARE_SCREAMING_SNAKE`
- Two-space indentation (per Google's style guide, mentioned in
  `.github/copilot-instructions.md`).
- Opening brace on the same line: `public class Foo {` not `public class Foo\n{`.
- Every public class and public method gets a Javadoc block (`/** ... */`).
