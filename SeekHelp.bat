@echo off
title SeekHelp+ Medical Assistance Platform - Alpha 1.0
echo Starting SeekHelp+ Medical Assistance Platform...
echo Alpha Version 1.0 - Enhanced Styling Edition
echo.
echo This version includes:
echo - Restored professional button fonts and styling
echo - Enhanced visual appearance with proper CSS
echo - Green color scheme maintained
echo - All features working with premium look
echo.

REM Check if Java is installed
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo ERROR: Java is not installed or not in PATH
    echo Please install Java 17 or later from https://adoptium.net/
    pause
    exit /b 1
)

REM Check if Maven is available
mvn -version >nul 2>&1
if %errorlevel% neq 0 (
    echo Maven not found in PATH, using local Maven installation...
    echo.
    
    REM Use local Maven installation
    if exist "apache-maven-3.9.6\bin\mvn.cmd" (
        echo Using local Maven installation...
        echo.
        
        REM Clean and compile first
        echo Compiling application...
        apache-maven-3.9.6\bin\mvn.cmd clean compile
        
        REM Launch the application with Maven
        echo Launching SeekHelp+ with enhanced styling...
        apache-maven-3.9.6\bin\mvn.cmd exec:java "-Dexec.mainClass=com.seekhelp.desktop.SeekHelpDesktopApplication"
    ) else (
        echo ERROR: Local Maven installation not found
        echo Please ensure apache-maven-3.9.6 folder exists
        pause
        exit /b 1
    )
) else (
    echo Using system Maven installation...
    echo.
    
    REM Clean and compile first
    echo Compiling application...
    mvn clean compile
    
    REM Launch the application with Maven
    echo Launching SeekHelp+ with enhanced styling...
    mvn exec:java "-Dexec.mainClass=com.seekhelp.desktop.SeekHelpDesktopApplication"
)

REM If the application exits with an error, pause to show the error
if %errorlevel% neq 0 (
    echo.
    echo Application exited with error code: %errorlevel%
    pause
)
