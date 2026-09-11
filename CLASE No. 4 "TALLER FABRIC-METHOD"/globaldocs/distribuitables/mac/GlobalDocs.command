#!/bin/bash
# GlobalDocs Document Processor - Mac/Linux Launcher (alternative)
# Run this script from terminal: ./GlobalDocs.command

DIR="$(cd "$(dirname "$0")" && pwd)"
JAR="$DIR/globaldocs-document-processor-1.0.0.jar"

if ! command -v java &> /dev/null; then
    echo "ERROR: Java is not installed or not in PATH."
    echo "Please install Java 17+ from: https://adoptium.net/"
    exit 1
fi

java -jar "$JAR" &
