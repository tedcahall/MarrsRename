# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

MARRS File Rename Utility — a JavaFX 11 desktop GUI application for batch renaming CSV files into a standardized format: `Race-{raceId}-Group-{groupNum}-results.csv`. Built for Java 11 (Amazon Corretto) with JavaFX SDK 11.0.2.

## Build Commands

The project uses Apache Ant with JavaFX. The JavaFX SDK is expected at `/home/cahall/javafx-sdk-11.0.2/lib/`.

```bash
# Build and package (default target: compile + JAR + JNLP)
ant -f build.xml

# Clean build artifacts
ant -f build.xml clean

# Compile only
ant -f build.xml compile
```

Output: `dist/MarrsRename.jar`

## Running

```bash
java --module-path /home/cahall/javafx-sdk-11.0.2/lib --add-modules javafx.controls -jar dist/MarrsRename.jar
```

## Architecture

This is a single-class JavaFX application with no external dependencies beyond JavaFX.

- **`src/application/Main.java`** — The entire application. Extends `javafx.application.Application`. The `start()` method builds all UI (BorderPane layout with directory chooser, file list grid, and rename controls) and contains all business logic inline.
- **`src/application/application.css`** — JavaFX stylesheet for the UI.
- **`build.xml`** — Primary Ant build file. Compiles to `classes/`, packages to `dist/`.
- **`buildRacing.xml`** — Legacy Ant build file with Tomcat integration (not actively used).
- **`MarrsRename.java`** (root) — Unpackaged duplicate of Main.java; not part of the build.

## Key Details

- IDE: Eclipse (`.project`, `.classpath`, `.settings/` are Eclipse config)
- No Maven/Gradle — dependencies are manually referenced JARs
- No tests exist
- The initial directory path is hardcoded to `/home/cahall/`
