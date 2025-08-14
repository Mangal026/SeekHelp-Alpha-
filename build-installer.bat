@echo off
title SeekHelp+ Alpha 1.0 - Build Installer
echo ========================================
echo SeekHelp+ Medical Assistance Platform
echo Alpha Version 1.0 - Build Script
echo ========================================
echo.

REM Check if Maven is available
call .\apache-maven-3.9.6\bin\mvn.cmd -version >nul 2>&1
if %errorlevel% neq 0 (
    echo ERROR: Maven is not available
    echo Please ensure apache-maven-3.9.6 is in the current directory
    pause
    exit /b 1
)

REM Check if Java is available
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo ERROR: Java is not installed or not in PATH
    echo Please install Java 17 or later from https://adoptium.net/
    pause
    exit /b 1
)

echo Building SeekHelp+ Application...
echo.

REM Clean and package the application
echo [1/4] Cleaning previous builds...
call .\apache-maven-3.9.6\bin\mvn.cmd clean

echo [2/4] Compiling source code...
call .\apache-maven-3.9.6\bin\mvn.cmd compile

echo [3/4] Creating executable JAR...
call .\apache-maven-3.9.6\bin\mvn.cmd package -DskipTests

echo [4/4] Creating distribution package...
echo.

REM Create distribution directory
if exist "dist" rmdir /s /q "dist"
mkdir "dist"
mkdir "dist\SeekHelp-Alpha-1.0"

REM Copy application files
echo Copying application files...
copy "target\seekhelp-desktop-1.0.0.jar" "dist\SeekHelp-Alpha-1.0\"
copy "SeekHelp.bat" "dist\SeekHelp-Alpha-1.0\"
copy "SeekHelp.ps1" "dist\SeekHelp-Alpha-1.0\"
copy "SeekHelp.manifest" "dist\SeekHelp-Alpha-1.0\"
copy "INSTALLATION.md" "dist\SeekHelp-Alpha-1.0\"
copy "README.md" "dist\SeekHelp-Alpha-1.0\"

REM Create a simple installer script
echo Creating installer...
(
echo @echo off
echo title SeekHelp+ Alpha 1.0 - Installer
echo echo ========================================
echo echo SeekHelp+ Medical Assistance Platform
echo echo Alpha Version 1.0 - Installer
echo echo ========================================
echo echo.
echo echo This will install SeekHelp+ to your desktop.
echo echo.
echo set /p choice="Do you want to continue? (Y/N): "
echo if /i "%%choice%%"=="Y" goto install
echo if /i "%%choice%%"=="y" goto install
echo echo Installation cancelled.
echo pause
echo exit /b 0
echo.
echo :install
echo echo Installing SeekHelp+...
echo.
echo REM Create desktop shortcut
echo if not exist "%%USERPROFILE%%\Desktop\SeekHelp+.lnk" (
echo     echo Creating desktop shortcut...
echo     powershell -Command "$WshShell = New-Object -comObject WScript.Shell; $Shortcut = $WshShell.CreateShortcut('%%USERPROFILE%%\Desktop\SeekHelp+.lnk'); $Shortcut.TargetPath = '%%CD%%\SeekHelp.bat'; $Shortcut.WorkingDirectory = '%%CD%%'; $Shortcut.Description = 'SeekHelp+ Medical Assistance Platform'; $Shortcut.Save()"
echo )
echo.
echo echo Installation completed!
echo echo.
echo echo SeekHelp+ has been installed to your desktop.
echo echo Double-click the desktop shortcut to launch the application.
echo echo.
echo echo For more information, see INSTALLATION.md
echo echo.
echo pause
) > "dist\SeekHelp-Alpha-1.0\install.bat"

REM Create ZIP file
echo Creating ZIP package...
cd dist
powershell -Command "Compress-Archive -Path 'SeekHelp-Alpha-1.0' -DestinationPath 'SeekHelp-Alpha-1.0.zip' -Force"
cd ..

echo.
echo ========================================
echo BUILD COMPLETED SUCCESSFULLY!
echo ========================================
echo.
echo Distribution package created at:
echo dist\SeekHelp-Alpha-1.0.zip
echo.
echo To install on another computer:
echo 1. Extract the ZIP file
echo 2. Run install.bat
echo 3. Double-click the desktop shortcut
echo.
echo Press any key to exit...
pause >nul
