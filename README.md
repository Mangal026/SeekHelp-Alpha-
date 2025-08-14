# Seek Help+ Desktop Application

A JavaFX desktop application that replicates the functionality of the Seek Help+ React web application, providing medical assistance services for users across India.

## ✨ Enhanced Styling Edition

**Version 1.0 - Enhanced Styling Edition** includes:
- 🎨 **Restored professional button fonts and styling**
- 🌟 **Enhanced visual appearance with proper CSS**
- 💚 **Green color scheme maintained**
- 🚀 **All features working with premium look**
- 📱 **Professional UI matching original design quality**

## Features

- **Authentication System**: Login/Register functionality with admin and guest user support
- **Hospital Directory**: Find and search hospitals by location and specialty
- **Blood Donor Network**: Connect with blood donors in your area
- **Emergency Services**: Quick access to emergency medical assistance
- **AI Medical Bot**: Chat interface for medical guidance (coming soon)
- **Symptom Checker**: AI-powered symptom analysis (coming soon)
- **User Profile Management**: Manage account and preferences
- **Modern UI**: Beautiful, responsive interface matching the original React app

## Technology Stack

- **Frontend**: JavaFX 17
- **Backend**: Spring Boot 3.2.0
- **Database**: H2 (in-memory for demo)
- **Build Tool**: Maven
- **Icons**: Material Design Icons (Ikonli)
- **Styling**: Enhanced Custom CSS with Professional Appearance

## Prerequisites

- Java 17 or higher
- Maven 3.6 or higher (or use included local Maven installation)
- Git

## Quick Start

### 🚀 **Windows Users (Recommended)**

1. **Double-click `SeekHelp.bat`** - Automatically detects Maven and launches with enhanced styling
2. **Or use PowerShell**: Right-click `SeekHelp.ps1` → "Run with PowerShell"

### 🔧 **Manual Setup**

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd seekhelp-desktop
   ```

2. **Build the project**
   ```bash
   mvn clean compile
   ```

3. **Run the application**
   ```bash
   mvn exec:java -Dexec.mainClass="com.seekhelp.desktop.SeekHelpDesktopApplication"
   ```

## Launcher Scripts

### **SeekHelp.bat** (Windows Batch)
- Automatically detects Maven installation
- Falls back to local Maven if system Maven not found
- Compiles and launches with enhanced styling
- User-friendly error handling

### **SeekHelp.ps1** (PowerShell)
- Enhanced PowerShell script with color output
- Same Maven detection and fallback logic
- Professional console experience

## Usage

### Login Credentials

**Admin User:**
- Username: `admin`
- Password: `admin123`

**Guest User:**
- Username: `guest`
- Password: `guest123`

### Features Overview

1. **Authentication**
   - Login with existing credentials
   - Register new account
   - Guest login option

2. **Home Dashboard**
   - Service cards for different medical services
   - Emergency banner with quick access
   - Navigation to all features

3. **Hospital Search**
   - Search hospitals by name
   - Filter by location and specialty
   - View hospital details and contact information

4. **Blood Donors**
   - Search blood donors by blood type
   - Filter by location
   - Contact donor information

5. **Profile Management**
   - View user profile
   - Edit profile (coming soon)
   - Settings (coming soon)

6. **About Section**
   - Application information
   - Team details
   - Contact information

## Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── seekhelp/
│   │           └── desktop/
│   │               ├── SeekHelpDesktopApplication.java
│   │               ├── MainController.java
│   │               ├── AuthService.java
│   │               ├── HeaderController.java
│   │               ├── SidebarController.java
│   │               ├── AuthController.java
│   │               ├── HomeController.java
│   │               ├── BloodDonorsController.java
│   │               ├── HospitalsController.java
│   │               ├── SymptomCheckerController.java
│   │               ├── AIMedicalBotController.java
│   │               ├── ProfileController.java
│   │               └── AboutController.java
│   └── resources/
│       ├── css/
│       │   └── styles.css
│       └── application.properties
└── test/
    └── java/
        └── com/
            └── seekhelp/
                └── desktop/
```

## Development

### Adding New Features

1. Create a new controller class extending the existing pattern
2. Add navigation methods to `MainController`
3. Update the sidebar navigation if needed
4. Add corresponding CSS styles

### Database Integration

The application currently uses H2 in-memory database for demo purposes. To integrate with a real database:

1. Update `application.properties` with your database configuration
2. Create JPA entities for your data models
3. Create repository interfaces
4. Update controllers to use real data services

### Styling

The application uses custom CSS for styling. All styles are defined in `src/main/resources/css/styles.css`. The styling follows the same color scheme and design patterns as the original React application.

## API Endpoints

The Spring Boot backend provides REST API endpoints (accessible at `http://localhost:8080/api`):

- `GET /api/hospitals` - Get all hospitals
- `GET /api/blood-donors` - Get all blood donors
- `POST /api/auth/login` - User authentication
- `POST /api/auth/register` - User registration

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests if applicable
5. Submit a pull request

## License

This project is licensed under the MIT License.

## Support

For support and questions:
- Email: support@seekhelp.com
- Phone: -

## Screenshots

The application features a modern, clean interface with:
- Mint green color scheme matching the original design
- Card-based layout for services
- Responsive navigation
- Professional medical-themed styling
- Intuitive user experience

## Future Enhancements

- Real database integration (MySQL/PostgreSQL)
- AI-powered symptom checker
- Live chat with medical professionals
- Mobile app version
- Multi-language support
- Advanced search and filtering
- User reviews and ratings
- Appointment booking system
 
 $$Founder:
 Mangal Pandey