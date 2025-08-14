# SeekHelp+ Medical Assistance Platform Launcher
# Alpha Version 1.0

Write-Host "Starting SeekHelp+ Medical Assistance Platform..." -ForegroundColor Green
Write-Host "Alpha Version 1.0" -ForegroundColor Yellow
Write-Host ""

# Check if Java is installed
try {
    $javaVersion = java -version 2>&1
    Write-Host "Java version detected:" -ForegroundColor Cyan
    Write-Host $javaVersion[0] -ForegroundColor White
} catch {
    Write-Host "ERROR: Java is not installed or not in PATH" -ForegroundColor Red
    Write-Host "Please install Java 17 or later from https://adoptium.net/" -ForegroundColor Yellow
    Read-Host "Press Enter to exit"
    exit 1
}

# Check if the JAR file exists
$jarPath = "target\seekhelp-desktop-1.0.0.jar"
if (-not (Test-Path $jarPath)) {
    Write-Host "ERROR: Application JAR file not found at: $jarPath" -ForegroundColor Red
    Write-Host "Please build the application first using: mvn clean package" -ForegroundColor Yellow
    Read-Host "Press Enter to exit"
    exit 1
}

# Launch the application
Write-Host "Launching SeekHelp+..." -ForegroundColor Green
try {
    java -jar $jarPath
} catch {
    Write-Host "Application exited with an error" -ForegroundColor Red
    Read-Host "Press Enter to exit"
}
