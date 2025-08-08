# Module Documentation

## Overview
This document provides detailed documentation for each module and component in the Brink POS Test Automation Framework.

## Base Package Modules

### BaseTest.java
**Purpose**: Foundation class for all test classes, handles WebDriver lifecycle and configuration.

**Key Responsibilities**:
- WebDriver initialization and configuration
- Browser setup with Chrome options
- Download directory management
- Test setup and teardown

**Key Methods**:
```java
@BeforeMethod
public void setUp()
```
- Initializes WebDriver with Chrome browser
- Configures window size (1024x678)
- Sets download directory to "./PDF/"
- Configures browser options

```java
@AfterMethod  
public void tearDown()
```
- Safely quits WebDriver instance
- Cleans up browser resources

**Configuration Options**:
- Window size: 1024x678 (maximized)
- Download path: Configurable via folder path
- Browser options: Extensible through ChromeOptions

---

### Utils.java (1141 lines)
**Purpose**: Comprehensive utility class providing 50+ helper methods for test automation.

**Major Function Categories**:

#### 1. Element Interaction Utilities
```java
public String booleanWaitForElementVisibility(By locator, int timeoutInSeconds)
public void clickElement(By locator)
public void typeText(By locator, String text)
```

#### 2. Wait Mechanisms
```java
public void waitForElementToBeClickable(By locator, int timeout)
public void waitForPageLoad()
public void waitForDownload(String filePath)
```

#### 3. Date and Time Operations
```java
public String getCurrentDate()
public String getFormattedDate(String format)
public LocalDate calculateDateRange(int days)
```

#### 4. File Operations
```java
public boolean isFileDownloaded(String fileName)
public void cleanupDownloadDirectory()
public String getLatestDownloadedFile()
```

#### 5. Data Extraction
```java
public String extractTextFromElement(By locator)
public List<String> extractTableData(By tableLocator)
public Map<String, String> extractKeyValuePairs(By containerLocator)
```

#### 6. PDF Processing
```java
public String extractPDFText(String filePath)
public boolean validatePDFContent(String filePath, String expectedText)
```

#### 7. Excel Operations
```java
public void writeToExcel(String filePath, String data)
public String readFromExcel(String filePath, int row, int column)
```

**Best Practices**:
- All methods include proper exception handling
- Configurable timeout values
- Comprehensive logging for debugging

---

### ExcelUtils.java
**Purpose**: Specialized utility class for Excel file operations.

**Key Methods**:
```java
public static void writeDataToExcel(String filePath, Object[][] data)
public static String[][] readDataFromExcel(String filePath)
public static void formatExcelCell(Cell cell, String format)
```

**Features**:
- Read/write Excel files (.xlsx format)
- Data formatting and styling
- Dynamic data handling
- Error handling for file operations

---

### ExcelUtilsTwo.java (342 lines)
**Purpose**: Extended Excel utilities with advanced features.

**Advanced Features**:
- Complex data transformations
- Multi-sheet operations
- Advanced formatting options
- Data validation utilities

**Key Capabilities**:
```java
public void processMultipleSheets(String filePath)
public void validateDataIntegrity(String filePath)
public void generateSummaryReport(String sourceFile, String targetFile)
```

---

### BaseFileUtils.java
**Purpose**: File system operations and directory management.

**Key Methods**:
```java
public static void initializeDownloadDirectory(String downloadDirectory)
public static boolean createDirectory(String path)
public static void cleanupOldFiles(String directory, int maxAge)
```

**Features**:
- Directory creation and cleanup
- File age management
- Path utilities
- Cross-platform compatibility

---

### PropertiesUtil.java
**Purpose**: Configuration management and properties handling.

**Functionality**:
- Load configuration from properties files
- Environment-specific settings
- Dynamic property resolution

---

## Page Objects Module

### BrinkPage.java
**Purpose**: Page Object Model implementation for the Brink application.

**Element Categories**:

#### 1. Login Elements
```java
public By username = By.xpath("//input[@id='Username']");
public By password = By.xpath("//input[@id='Password']");  
public By signIn = By.xpath("//button[text()='Continue']");
```

#### 2. Navigation Elements
```java
public By reports = By.xpath("//a[@href='/Reports/Reports/']");
public By searchField = By.xpath("//input[@id='reportSearchInput']");
```

#### 3. Report Generation Elements
```java
public By salesSummaries = By.xpath("//a[text()='Sales Summary']");
public By salesAndLabor = By.xpath("//a[text()[normalize-space()='Sales And Labor Summary By Location']]");
public By selectLocations = By.xpath("//span//span[@title='Select Locations']");
```

#### 4. Date Selection Elements
```java
public By dayDropdown = By.xpath("(//span[@class='select2-selection select2-selection--single'])[2]//span[2]");
public By dateRange = By.xpath("//ul//li[text()='Date Range']");
public By startDate = By.xpath("//input[@id='DateRangeModel_Start']");
public By endDate = By.xpath("//input[@id='DateRangeModel_End']");
```

#### 5. Data Extraction Elements
```java
public By netSales = By.xpath("//div[@id='report_div']//table//tbody//tr//td//nobr[text()='Totals']/following::td[2]");
public By totalPay = By.xpath("//div[@id='report_div']//table//tbody//tr//td//nobr[text()='Totals']/following::td[8]");
```

**Design Principles**:
- Descriptive element naming
- XPath-based locators for stability
- Grouped by functionality
- Integrated with Utils class

---

## Test Modules

### BrinkTest.java (286 lines)
**Purpose**: Primary test suite for core Brink functionality.

**Test Scenarios**:
1. User authentication and login
2. Navigation to reports section
3. Sales summary report generation
4. Data extraction and validation
5. File download verification

**Key Features**:
- Data-driven testing support
- Parameterized test methods
- Comprehensive assertions
- Error handling and recovery

---

### BrinkTestProvo.java (286 lines)
**Purpose**: Location-specific tests for Provo restaurant.

**Specialized Features**:
- Provo-specific data validation
- Location-based configuration
- Custom reporting for Provo location
- Tailored test scenarios

---

### BrinkTwoTest.java (512 lines)
**Purpose**: Extended test suite with advanced scenarios.

**Advanced Features**:
- Complex workflow testing
- Multi-step data processing
- Advanced data validation
- Integration testing scenarios

**Key Capabilities**:
- End-to-end workflow testing
- Cross-functional test scenarios
- Performance validation
- Data integrity verification

---

## Data Management Modules

### LaborDataMapping Configuration
**Purpose**: JSON-based configuration for labor category mapping.

**Structure Example**:
```json
{
  "Server": "Front",
  "Host": "Front",
  "Dish": "Kitchen", 
  "Bartender": "Front",
  "Manager": "Front",
  "Kitchen": "Kitchen"
}
```

**Locations Supported**:
- Marita
- Augusta  
- The Landing
- Aiken Fish

### MappingData Configuration
**Purpose**: Daily summary data mapping for different locations.

**Locations Supported**:
- Marita
- Gonzaga
- Provo
- Augusta
- The Landing
- South Hill

---

## Integration Points

### 1. Maven Integration
- Dependency management through `pom.xml`
- Build lifecycle integration
- Test execution configuration

### 2. TestNG Integration
- Test suite organization via `testng.xml`
- Parallel execution support
- Test grouping and categorization

### 3. Reporting Integration
- ExtentReports for HTML reporting
- TestNG native reporting
- Custom report generation

---

## Usage Examples

### Basic Test Execution
```java
public class SampleTest extends BaseTest {
    private BrinkPage brinkPage;
    
    @BeforeMethod
    public void initializePages() {
        brinkPage = new BrinkPage(driver);
    }
    
    @Test
    public void testLogin() {
        brinkPage.utils.typeText(brinkPage.username, "testuser");
        brinkPage.utils.typeText(brinkPage.password, "password");
        brinkPage.utils.clickElement(brinkPage.signIn);
    }
}
```

### Data Processing Example
```java
@Test
public void processLaborData() {
    String data = brinkPage.utils.extractTextFromElement(brinkPage.totalPay);
    ExcelUtils.writeDataToExcel("results.xlsx", data);
    
    // Validate against mapping
    String mapping = loadLaborMapping("Marita");
    Assert.assertTrue(validateData(data, mapping));
}
```

---

## Module Dependencies

```
Tests
  ↓
Pages (BrinkPage)
  ↓
Base (Utils, BaseTest)
  ↓
External Libraries (Selenium, TestNG, POI)
```

## Performance Considerations

1. **Memory Management**: Large Utils class should be refactored
2. **Loading Optimization**: Lazy loading of page elements
3. **Resource Cleanup**: Proper WebDriver and file resource management
4. **Parallel Execution**: Thread-safe implementations needed

## Maintenance Guidelines

1. **Regular Updates**: Keep dependency versions current
2. **Code Reviews**: Ensure adherence to patterns
3. **Refactoring**: Break down large classes into focused modules  
4. **Documentation**: Keep module docs updated with changes