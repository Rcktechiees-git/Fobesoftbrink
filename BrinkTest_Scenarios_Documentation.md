# BrinkTest.java - Test Case Scenarios Documentation

## Overview
The `BrinkTest.java` file contains automated integration tests for validating data synchronization between **Brink POS (Point of Sale) system** and **Fobesoft system**. This test suite ensures seamless transfer of sales and labor data between the two platforms.

## Test Framework Details
- **Framework**: Selenium WebDriver with TestNG
- **Browser**: Chrome (with ChromeDriver)
- **Pattern**: Page Object Model
- **Data Source**: Excel file (`Scarlett.xlsx`)
- **Base Class**: Extends `BaseTest`

## Test Case Scenarios

### TC001: Brink POS Entry - End-to-End Integration Test

**Test ID**: TC001  
**Description**: Brink pos entry  
**Type**: Integration Test  
**Data Provider**: credentialsProvider (reads from Excel)

#### Test Parameters (from Excel data):
- `BrinkURL` - Brink POS system URL
- `BrinkUsername` - Brink login username
- `BrinkPassword` - Brink login password
- `mappingfile` - Data mapping configuration file
- `fobeurl` - Fobesoft system URL
- `fobeemail` - Fobesoft login email
- `fobepassword` - Fobesoft login password

#### Detailed Test Steps:

##### Phase 1: Brink POS System Operations
1. **System Access**
   - Navigate to Brink POS URL
   - Wait for login page to load
   - Enter username and password
   - Click sign-in button

2. **Navigation to Reports**
   - Wait for dashboard to load
   - Navigate to Reports section
   - Access Sales and Labor reports

3. **Report Configuration**
   - Select "All Locations" from location dropdown
   - Configure date range to yesterday's date (current date - 1 day)
   - Generate the sales and labor report

4. **Data Extraction**
   - Switch to report iframe
   - Extract **Net Sales** amount from totals row
   - Extract **Total Pay** amount from totals row
   - Clean and format extracted amounts (remove special characters)

##### Phase 2: Fobesoft System Operations
1. **System Access**
   - Open Fobesoft URL in new browser tab
   - Enter email and password credentials
   - Login to Fobesoft system

2. **Popup Handling**
   - Check for and dismiss any modal popups
   - Handle "xmark" close icons if present
   - Continue with main workflow

3. **Date Navigation**
   - Navigate to yesterday's date in the calendar
   - Select the appropriate date for data entry

##### Phase 3: Data Synchronization
1. **Revenue Data Mapping**
   - Locate Food revenue field
   - Double-click to edit the field
   - Clear existing value
   - Enter Net Sales amount from Brink POS
   - Save revenue data
   - Verify "Revenue updated" confirmation message

2. **Labor Data Mapping**
   - Navigate to Labor section
   - Locate Hourly Labor field
   - Double-click to edit the field
   - Clear existing value
   - Enter Total Pay amount from Brink POS
   - Save labor data
   - Verify "Labor updated" confirmation message

#### Test Validations:
- ✅ Successful login to Brink POS system
- ✅ Successful navigation to reports section
- ✅ Successful report generation with correct date range
- ✅ Successful data extraction from Brink reports
- ✅ Successful login to Fobesoft system
- ✅ Successful popup handling (if applicable)
- ✅ Successful date selection in Fobesoft
- ✅ Successful revenue data update with confirmation
- ✅ Successful labor data update with confirmation

#### Error Handling:
- **NoSuchElementException**: Handles missing popup elements gracefully
- **General Exception**: Catches and logs unexpected exceptions
- **Element Wait Timeouts**: Uses explicit waits with retry mechanisms
- **Frame Switching**: Proper iframe handling for report data

## Test Infrastructure

### Setup (@BeforeMethod)
- Initializes download directories for Excel and PDF files
- Configures ChromeDriver with WebDriverManager
- Sets Chrome preferences for file downloads
- Maximizes browser window

### Cleanup (@AfterMethod)
- Safely quits the WebDriver instance
- Cleans up browser resources

### Data Provider
- **Method**: `credentialsProvider()`
- **Source**: Excel file (`Data/Scarlett.xlsx`, Sheet1)
- **Purpose**: Provides test parameters for multiple test runs

## Dependencies
- **Selenium WebDriver**: Browser automation
- **TestNG**: Test framework and annotations
- **WebDriverManager**: Automatic driver management
- **Apache POI**: Excel file reading
- **Actions Class**: Advanced user interactions (double-click, drag-drop)
- **JavascriptExecutor**: JavaScript execution for complex interactions

## Key Features
1. **Cross-Platform Integration**: Tests integration between two different systems
2. **Data-Driven Testing**: Uses Excel for test parameters
3. **Robust Error Handling**: Graceful handling of various exception scenarios
4. **Dynamic Date Handling**: Automatically works with yesterday's date
5. **File Management**: Handles downloads and file operations
6. **Multi-Tab Operations**: Manages multiple browser tabs
7. **Wait Strategies**: Implements various wait conditions for reliability

## Business Value
This test ensures:
- **Data Accuracy**: Sales and labor data is correctly transferred
- **System Integration**: Both systems work together seamlessly
- **Process Automation**: Manual data entry is replaced with automated synchronization
- **Data Consistency**: Same date ranges are used across both systems
- **Error Prevention**: Validates successful data updates before proceeding

## Maintenance Notes
- Update Excel data file (`Scarlett.xlsx`) with new credentials as needed
- Monitor for UI changes in either Brink POS or Fobesoft systems
- Adjust wait times if systems become slower/faster
- Update date formatting if system requirements change