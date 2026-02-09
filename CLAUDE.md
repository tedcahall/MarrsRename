# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

MARRS File Rename Utility — a JavaFX 11 desktop GUI application for batch renaming CSV files into a standardized format: `Race-{raceId}-Group-{groupNum}-results.csv`. Built for Java 11 (Amazon Corretto) with JavaFX SDK 11.0.2.

## Build Commands

The project uses Gradle with the OpenJFX plugin. JavaFX dependencies are managed automatically.

```bash
# Build (compile + package)
./gradlew build

# Clean build artifacts
./gradlew clean

# Compile only
./gradlew compileJava
```

## Running

```bash
./gradlew run
```

## Architecture

This is a single-class JavaFX application with no external dependencies beyond JavaFX.

- **`src/main/java/application/Main.java`** — The entire application. Extends `javafx.application.Application`. The `start()` method builds all UI (BorderPane layout with directory chooser, file list grid, and rename controls) and contains all business logic inline.
- **`src/main/resources/application/application.css`** — JavaFX stylesheet for the UI.
- **`build.gradle`** — Gradle build file with OpenJFX plugin.
- **`build.xml`** — Legacy Ant build file (kept for reference).
- **`buildRacing.xml`** — Legacy Ant build file with Tomcat integration (not actively used).
## Key Details

- IDE: Eclipse (`.project`, `.classpath`, `.settings/` are Eclipse config)
- Gradle with OpenJFX plugin manages JavaFX dependencies
- No tests exist
- The initial directory defaults to the user's home directory via `System.getProperty("user.home")`
