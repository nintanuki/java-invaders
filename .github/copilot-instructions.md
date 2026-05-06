# Copilot Instructions for Java Invaders

## Before making any change
1. Read `docs/CHANGELOG.md` to understand the current state of the codebase.
2. Read `docs/TESTING.md` and follow every rule under **Refactoring Rules**.

## After making any change
- Append an entry to `docs/CHANGELOG.md` following the exact format specified in that file (ISO 8601 timestamp with timezone, file path, line numbers at time of edit, before/after code, why, and editor name including the AI model used).

## Refactoring rules (from TESTING.md — enforced always)
- All code must be Google Java Style Guide or the Oracle Code Conventions for the Java Programming Language compliant.
- Less code is better; clean and readable is best.
- Keep middlemen minimal: if A calls B and B only calls C, have A call C directly.
- Keep code clean of dead imports, unused variables/functions, and legacy code.
- The main class must be light — offload responsibilities to other classes.
- Classes should communicate through the main class where possible.
- New class and function names must clearly describe their purpose.
- All constants go in a settings file No magic numbers.
- All classes and functions must have a docstring with a summary, and Args/Returns if applicable.
- Do not change function or variable names unless their role has completely changed.
- Keep functions grouped by role. `update` and `run` functions go last and should only call other functions.
- Do not remove comments. Comments must explain *why*, not just what.
- Do not leave comments noting that a change was made, unless fixing a non-obvious bug or explaining unconventional code.

## Display text
- 

## Testing checklist (run mentally after major changes)
- 