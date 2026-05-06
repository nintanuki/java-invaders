# Copilot Instructions for Java Invaders

## Before making any change
1. Read `docs/CHANGELOG.md` to understand the current state of the codebase.
2. Read `docs/TESTING.md` and follow every rule under **Refactoring Rules**.

## After making any change
- Append an entry to `docs/CHANGELOG.md` following the exact format specified in that file (ISO 8601 timestamp with timezone, file path, line numbers at time of edit, before/after code, why, and editor name including the AI model used).

# Refactoring Rules for Java Invaders

## General Principles
- **Standard Compliance**: All code must be **Google Java Style Guide** compliant (e.g., K&R style braces, 2-space indentation, CamelCase).
- **Simplicity**: Less code is better; clean and readable is best.
- **Maintenance**: Keep code clean of dead imports, unused variables, and legacy code.

## Architecture & Communication
- **Encapsulation**: Keep fields `private` unless there is a specific reason for them to be `protected`. Use Getters/Setters only when necessary.
- **Dependency Injection**: Classes should receive the objects they need to interact with via their **Constructors** rather than routing every communication through the `Main` class.
- **Lightweight Main**: The main class/entry point must be light—offload responsibilities to specialized manager or entity classes.

## Logic & Constants
- **No Magic Numbers**: All constants must be defined in `Settings.java`.
- **Method Organization**: Group methods by role. Core logic goes first; `update()` and `render()` functions go last.
- **Immutability**: Do not change method signatures or variable names unless their role has fundamentally changed.

## Documentation & Comments
- **Javadoc**: All classes and public methods must have **Javadoc** blocks (`/** ... */`). Include `@param` and `@return` tags where applicable.
- **Commenting Style**: Comments must explain *why*, not *what*. Do not remove existing comments.
- **No Revision History**: Do not leave comments noting that a change was made unless fixing a non-obvious bug or explaining unconventional code. (Use `CHANGELOG.md` for history instead).

## Testing checklist (run mentally after major changes)
- 