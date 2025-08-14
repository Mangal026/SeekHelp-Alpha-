# SeekHelp+ Medical Assistance Platform Launcher
# Alpha Version 1.0 - Enhanced Styling Edition

Write-Host "Starting SeekHelp+ Medical Assistance Platform..." -ForegroundColor Green
Write-Host "Alpha Version 1.0 - Enhanced Styling Edition" -ForegroundColor Yellow
Write-Host ""
Write-Host "This version includes:" -ForegroundColor Cyan
Write-Host "- Restored professional button fonts and styling" -ForegroundColor White
Write-Host "- Enhanced visual appearance with proper CSS" -ForegroundColor White
Write-Host "- Green color scheme maintained" -ForegroundColor White
Write-Host "- All features working with premium look" -ForegroundColor White
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

# Check if Maven is available
try {
    $mvnVersion = mvn -version 2>&1
    Write-Host "Maven version detected:" -ForegroundColor Cyan
    Write-Host $mvnVersion[0] -ForegroundColor White
    $useSystemMaven = $true
} catch {
    Write-Host "Maven not found in PATH, checking for local installation..." -ForegroundColor Yellow
    $useSystemMaven = $false
}

# Launch the application
Write-Host "Launching SeekHelp+ with enhanced styling..." -ForegroundColor Green

try {
    if ($useSystemMaven) {
        # Use system Maven installation
        Write-Host "Using system Maven installation..." -ForegroundColor Cyan
        
        # Clean and compile first
        Write-Host "Compiling application..." -ForegroundColor Yellow
        mvn clean compile
        
        # Launch with Maven
        mvn exec:java "-Dexec.mainClass=com.seekhelp.desktop.SeekHelpDesktopApplication"
    } else {
        # Use local Maven installation
        $localMavenPath = "apache-maven-3.9.6\bin\mvn.cmd"
        if (Test-Path $localMavenPath) {
            Write-Host "Using local Maven installation..." -ForegroundColor Cyan
            
            # Clean and compile first
            Write-Host "Compiling application..." -ForegroundColor Yellow
            & $localMavenPath clean compile
            
            # Launch with local Maven
            & $localMavenPath exec:java "-Dexec.mainClass=com.seekhelp.desktop.SeekHelpDesktopApplication"
        } else {
            Write-Host "ERROR: Local Maven installation not found" -ForegroundColor Red
            Write-Host "Please ensure apache-maven-3.9.6 folder exists" -ForegroundColor Yellow
            Read-Host "Press Enter to exit"
            exit 1
        }
    }
} catch {
    Write-Host "Application exited with an error" -ForegroundColor Red
    Read-Host "Press Enter to exit"
}
