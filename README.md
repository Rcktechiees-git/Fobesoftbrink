# Brink POS Test Automation Framework

## Overview

This is a comprehensive Selenium-based test automation framework designed to test the Brink Point of Sale (POS) system. The framework automates critical business workflows including sales reporting, labor management, and data extraction from the Brink restaurant management platform.

### Key Features
- **Automated Testing**: Comprehensive test coverage for Brink POS system functionality
- **Data Management**: Automated extraction and validation of sales and labor data
- **Report Generation**: Automated generation and download of business reports (PDF/Excel)
- **Multi-Location Support**: Testing across multiple restaurant locations
- **Data Mapping**: JSON-based configuration for labor and sales data mapping

## Technology Stack

- **Java 8**: Primary programming language
- **Maven**: Build automation and dependency management
- **Selenium WebDriver 3.141.59**: Web automation framework
- **TestNG 7.10.2**: Testing framework with parallel execution support
- **Apache POI 5.0.0**: Excel file processing and data manipulation
- **ExtentReports 4.1.6**: Rich HTML test reporting
- **SikuliX API 2.0.5**: Image-based automation for complex UI interactions
- **WebDriverManager 5.7.0**: Automatic browser driver management

## Project Architecture

The framework follows the **Page Object Model (POM)** design pattern with a modular architecture:

```
src/main/java/
├── base/                    # Core utilities and base classes
│   ├── BaseTest.java       # Base test setup and WebDriver configuration
│   ├── Utils.java          # Comprehensive utility functions (1141 lines)
│   ├── ExcelUtils.java     # Excel file operations
│   ├── ExcelUtilsTwo.java  # Additional Excel utilities
│   ├── BaseFileUtils.java  # File management operations
│   └── PropertiesUtil.java # Configuration management
├── pages/                   # Page Object Model implementation
│   └── BrinkPage.java      # Main page object for Brink application
└── tests/                   # Test classes
    ├── BrinkTest.java      # Primary test suite
    ├── BrinkTestProvo.java # Location-specific tests (Provo)
    └── BrinkTwoTest.java   # Secondary test suite (512 lines)
```

## Data Management Structure

```
├── LaborDataMapping/        # Labor data configuration files
│   ├── MaritaLaborSummary.json
│   ├── AikenFishLaborSummary.json
│   ├── AugustaLaborSummary.json
│   └── TheLandingLaborSummary.json
├── MappingData/            # Daily summary mapping files
│   ├── MaritaDailySummary.json
│   ├── GonzagaDailySummary.json
│   ├── provoDailySummary.json
│   ├── AugustaDailySummary.json
│   ├── TheLandingDailySummary.json
│   └── SouthHillDailySummary.json
└── data/                   # Test data directory
```

## Getting Started

### Prerequisites

- **Java 8** or higher
- **Maven 3.6+**
- **Chrome Browser** (latest version)
- **Git** for version control

### Installation

1. **Clone the repository**:
   ```bash
   git clone https://github.com/Rcktechiees-git/Fobesoftbrink.git
   cd Fobesoftbrink
   ```

2. **Install dependencies**:
   ```bash
   mvn clean install
   ```

3. **Verify installation**:
   ```bash
   mvn clean compile
   ```

### Configuration

The framework uses Chrome WebDriver with the following default configuration:
- **Window Size**: 1024x678 (maximized)
- **Download Directory**: `./PDF/` (configurable)
- **Headless Mode**: Disabled (can be enabled in `BaseTest.java`)

## Running Tests

### Execute All Tests
```bash
mvn clean test
```

### Execute Specific Test Suite
```bash
mvn test -Dtest=BrinkTest
mvn test -Dtest=BrinkTestProvo
mvn test -Dtest=BrinkTwoTest
```

### Execute with TestNG XML
```bash
mvn test -DsuiteXmlFile=testng.xml
```

### Generate Test Reports
Test reports are automatically generated in:
- **TestNG Reports**: `test-output/`
- **ExtentReports**: Custom HTML reports (if configured)

## Core Functionality

### 1. Sales Report Automation
- Automated login to Brink system
- Navigation to Sales Summary reports
- Date range selection and report generation
- Data extraction from sales reports
- PDF/Excel download and processing

### 2. Labor Management Testing
- Labor summary report generation
- Location-specific labor data extraction
- Category-wise labor cost analysis
- Data validation against predefined mappings

### 3. Data Processing
- **Excel Operations**: Reading/writing Excel files with Apache POI
- **PDF Processing**: Text extraction from PDF reports
- **JSON Mapping**: Configuration-based data mapping for different locations
- **Data Validation**: Automated comparison of extracted vs expected data

### 4. Multi-Location Support
Configured locations include:
- Marita
- Augusta
- The Landing
- Aiken Fish
- Provo
- Gonzaga
- South Hill

## Build and Test

### Maven Configuration
- **Java Version**: 1.8
- **Encoding**: UTF-8
- **Surefire Plugin**: Configured for TestNG execution
- **Dependencies**: All managed through Maven Central

### Build Commands
```bash
# Clean and compile
mvn clean compile

# Run tests
mvn test

# Package application
mvn package

# Clean test outputs
mvn clean
```

## Contributing

### Development Guidelines
1. **Code Style**: Follow Java naming conventions
2. **Testing**: Write unit tests for utility functions
3. **Documentation**: Update documentation for any new features
4. **Commits**: Use descriptive commit messages
5. **Pull Requests**: Include test coverage for new functionality

### Setting Up Development Environment
1. Import project into preferred IDE (IntelliJ/Eclipse)
2. Install Maven and TestNG plugins
3. Configure Chrome WebDriver path if needed
4. Run sample tests to verify setup

## Troubleshooting

### Common Issues
1. **ChromeDriver Issues**: Ensure Chrome browser is updated
2. **Download Path Errors**: Verify write permissions for PDF directory
3. **Element Not Found**: Check if application UI has changed
4. **Timeout Errors**: Increase wait times in slow environments

### Support
For issues and questions, please create an issue in the GitHub repository.