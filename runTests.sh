#!/bin/bash
echo "Running JUnit tests..."

# Run tests using JUnit Platform Console Standalone
java -jar lib/junit-platform-console-standalone-1.9.2.jar --class-path bin --scan-class-path

echo "Tests completed."