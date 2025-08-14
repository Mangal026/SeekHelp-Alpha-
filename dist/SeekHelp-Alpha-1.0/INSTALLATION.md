# SeekHelp+ Medical Assistance Platform - Alpha 1.0

## Installation Guide

### System Requirements
- **Operating System**: Windows 10/11 (64-bit)
- **Java Runtime**: Java 17 or later (OpenJDK or Oracle JDK)
- **Memory**: Minimum 4GB RAM (8GB recommended)
- **Storage**: 500MB free disk space
- **Display**: 1200x800 minimum resolution

### Quick Installation

#### Option 1: Using the Launcher Scripts (Recommended)

1. **Download and Extract**
   - Download the SeekHelp+ Alpha 1.0 package
   - Extract to a folder on your desktop (e.g., `C:\SeekHelp`)

2. **Install Java (if not already installed)**
   - Download Java 17+ from [Eclipse Temurin](https://adoptium.net/)
   - Run the installer and follow the setup wizard
   - Verify installation by opening Command Prompt and typing: `java -version`

3. **Launch the Application**
   - **Double-click** `SeekHelp.bat` (Windows Batch)
   - **OR** Right-click `SeekHelp.ps1` → "Run with PowerShell" (PowerShell)

#### Option 2: Command Line Installation

1. **Open Command Prompt** as Administrator
2. **Navigate** to the SeekHelp folder:
   ```cmd
   cd C:\SeekHelp
   ```
3. **Build the application** (first time only):
   ```cmd
   mvn clean package
   ```
4. **Run the application**:
   ```cmd
   java -jar target\seekhelp-desktop-1.0.0.jar
   ```

### Creating Desktop Shortcut

1. **Right-click** on `SeekHelp.bat`
2. Select **"Create shortcut"**
3. **Move** the shortcut to your Desktop
4. **Rename** it to "SeekHelp+"
5. **Right-click** the shortcut → Properties
6. **Change Icon** → Browse → Select an appropriate icon
7. Click **OK**

### Troubleshooting

#### Common Issues

**"Java is not recognized"**
- Install Java 17+ from [Eclipse Temurin](https://adoptium.net/)
- Add Java to PATH environment variable
- Restart Command Prompt

**"Application won't start"**
- Ensure Java 17+ is installed
- Check if port 8080 is available (close other applications using this port)
- Run as Administrator if needed

**"Out of Memory Error"**
- Increase Java heap size: `java -Xmx2g -jar target\seekhelp-desktop-1.0.0.jar`
- Close other applications to free up memory

**"Permission Denied"**
- Run Command Prompt as Administrator
- Check Windows Defender/Firewall settings
- Ensure antivirus isn't blocking the application

#### Getting Help

- **Documentation**: Check the application's built-in help section
- **Support**: Use the Message Center → Customer Care feature
- **Bug Reports**: Create a support ticket through the application

### Features (Alpha 1.0)

✅ **Core Features**
- User Authentication & Registration
- Hospital Finder with Real-time Location
- Blood Donor Management
- Symptom Checker
- AI Medical Assistant
- Emergency Services
- Message Center & Customer Support
- Settings & Preferences

🔄 **Alpha Features** (Limited)
- Basic data persistence
- Sample data for demonstration
- Local database (H2)
- Basic security implementation

### Version Information

- **Version**: Alpha 1.0
- **Release Date**: August 2025
- **Build**: Development Build
- **License**: Proprietary (Development Use Only)

### Development Notes

This is an **Alpha version** intended for:
- Testing and feedback
- Feature demonstration
- Development purposes

**Not recommended for production use** or handling real patient data.

---

**© 2025 SeekHelp+ Development Team**
*Medical Assistance Platform - Alpha 1.0*
