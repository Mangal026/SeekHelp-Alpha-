@echo off
title SeekHelp+ Medical Assistance Platform - Alpha 1.0
echo Starting SeekHelp+ Medical Assistance Platform...
echo Alpha Version 1.0
echo.

REM Check if Java is installed
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo ERROR: Java is not installed or not in PATH
    echo Please install Java 17 or later from https://adoptium.net/
    pause
    exit /b 1
)

REM Launch the application
java -jar target\seekhelp-desktop-1.0.0.jar

REM If the application exits with an error, pause to show the error
if %errorlevel% neq 0 (
    echo.
    echo Application exited with error code: %errorlevel%
    pause
)
