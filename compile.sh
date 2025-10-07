#!/bin/bash
echo "Compiling Bill Payment Service..."

# Create bin directory if it doesn't exist
mkdir -p bin

# Compile main source files
javac -d bin src/Main.java src/core/*.java src/commands/*.java src/repository/*.java src/utils/*.java

# Compile test files with JUnit
javac -cp "bin;lib/junit-platform-console-standalone-1.9.2.jar" -d bin -sourcepath src src/tests/*.java

echo "Compilation completed."