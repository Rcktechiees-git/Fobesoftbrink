# Brink-FobeSoft Data Synchronization Automation

## Introduction 
This project automates the daily synchronization of financial data between Brink POS (Point of Sale) system and FobeSoft analytics platform. The automation eliminates manual data entry by automatically extracting sales and labor data from Brink and transferring it to FobeSoft for business analytics and reporting.

## 📋 BrinkTest.java Workflow Analysis

The main automation is implemented in `src/main/java/tests/BrinkTest.java`. For a complete understanding of the workflow:

- **📖 [Detailed Workflow Analysis](WORKFLOW_ANALYSIS.md)** - Comprehensive technical documentation
- **📊 [Visual Workflow Diagram](WORKFLOW_DIAGRAM.md)** - Step-by-step flow charts and diagrams

### Quick Overview
The automation performs these key functions:
1. **Extracts** yesterday's Net Sales and Total Pay from Brink POS reports
2. **Transfers** this data to FobeSoft's Revenue and Labor sections
3. **Validates** successful updates with confirmation messages
4. **Runs daily** to maintain data consistency between systems

## Getting Started

### Prerequisites
- Java 8 or higher
- Maven 3.6+
- Chrome browser
- Access to both Brink POS and FobeSoft systems

### Installation Process
1. Clone the repository
2. Configure Excel data file: `data/Scarlett.xlsx` with system credentials
3. Install dependencies: `mvn clean compile`

### Software Dependencies
- **Selenium WebDriver 3.141.59** - Browser automation
- **TestNG 7.10.2** - Test framework
- **Apache POI 5.0.0** - Excel file operations
- **WebDriverManager 5.7.0** - Chrome driver management

## Build and Test

### Build the Project
```bash
mvn clean compile
```

### Run the Automation
```bash
# Run all tests
mvn test

# Run specific test
mvn test -Dtest=BrinkTest
```

### Configuration
Update `data/Scarlett.xlsx` with:
- Brink POS URL and credentials
- FobeSoft URL and credentials
- Any additional configuration parameters

## Project Structure
```
src/main/java/
├── base/           # Framework utilities
├── pages/          # Page Object Model classes  
└── tests/          # Test implementations
data/               # Excel configuration files
testng.xml         # TestNG suite configuration
```

## Key Features
- ✅ **Automated Data Sync** - Daily transfer of financial data
- ✅ **Error Handling** - Robust exception management and retries
- ✅ **Date Management** - Automatic yesterday date calculation
- ✅ **Multi-System Integration** - Seamless interaction between platforms
- ✅ **Data Validation** - Confirmation of successful updates

## Contribute
To improve this automation:
1. Review the [workflow analysis](WORKFLOW_ANALYSIS.md) for architecture understanding
2. Follow Page Object Model patterns for new UI elements
3. Add comprehensive error handling for new features
4. Update documentation for any workflow changes

For detailed technical information and maintenance recommendations, see the [complete workflow analysis](WORKFLOW_ANALYSIS.md).