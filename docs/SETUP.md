# Setup and Deployment Guide

## Table of Contents
1. [Prerequisites](#prerequisites)
2. [Environment Setup](#environment-setup)
3. [Installation](#installation)
4. [Configuration](#configuration)
5. [Running Tests](#running-tests)
6. [Deployment](#deployment)
7. [CI/CD Integration](#cicd-integration)
8. [Troubleshooting](#troubleshooting)

## Prerequisites

### System Requirements
- **Operating System**: Windows 10+, macOS 10.14+, or Linux (Ubuntu 18.04+)
- **Memory**: Minimum 8GB RAM (16GB recommended)
- **Storage**: 2GB free disk space
- **Network**: Internet connection for dependency downloads

### Software Dependencies

#### Required Software
1. **Java Development Kit (JDK) 8 or higher**
   ```bash
   # Verify Java installation
   java -version
   javac -version
   ```

2. **Apache Maven 3.6.0 or higher**
   ```bash
   # Verify Maven installation
   mvn -version
   ```

3. **Google Chrome Browser (latest version)**
   - Download from: https://www.google.com/chrome/
   - Chrome WebDriver is managed automatically via WebDriverManager

4. **Git** (for version control)
   ```bash
   # Verify Git installation
   git --version
   ```

#### Optional Software
- **IDE**: IntelliJ IDEA, Eclipse, or VS Code
- **Browser**: Firefox (for cross-browser testing)
- **Docker** (for containerized execution)

---

## Environment Setup

### 1. Java Setup

#### Windows
1. Download JDK from Oracle or use OpenJDK
2. Install and set JAVA_HOME environment variable:
   ```cmd
   set JAVA_HOME=C:\Program Files\Java\jdk1.8.0_XXX
   set PATH=%JAVA_HOME%\bin;%PATH%
   ```

#### macOS
```bash
# Using Homebrew
brew install openjdk@8

# Set JAVA_HOME in ~/.bash_profile or ~/.zshrc
export JAVA_HOME=/usr/local/opt/openjdk@8
export PATH=$JAVA_HOME/bin:$PATH
```

#### Linux (Ubuntu)
```bash
# Install OpenJDK
sudo apt update
sudo apt install openjdk-8-jdk

# Set JAVA_HOME in ~/.bashrc
export JAVA_HOME=/usr/lib/jvm/java-8-openjdk-amd64
export PATH=$JAVA_HOME/bin:$PATH
```

### 2. Maven Setup

#### Windows
1. Download Maven from https://maven.apache.org/download.cgi
2. Extract to desired location
3. Set environment variables:
   ```cmd
   set MAVEN_HOME=C:\apache-maven-3.x.x
   set PATH=%MAVEN_HOME%\bin;%PATH%
   ```

#### macOS
```bash
# Using Homebrew
brew install maven
```

#### Linux (Ubuntu)
```bash
# Install Maven
sudo apt install maven
```

### 3. IDE Setup (Optional)

#### IntelliJ IDEA
1. Install IntelliJ IDEA Community or Ultimate
2. Import project:
   - File → Open → Select project directory
   - Choose "Import Maven projects automatically"
3. Install plugins:
   - TestNG
   - Maven Helper

#### Eclipse
1. Install Eclipse IDE for Java Developers
2. Import project:
   - File → Import → Existing Maven Projects
   - Browse to project directory
3. Install TestNG plugin from Eclipse Marketplace

---

## Installation

### 1. Clone Repository
```bash
# Clone the repository
git clone https://github.com/Rcktechiees-git/Fobesoftbrink.git
cd Fobesoftbrink
```

### 2. Verify Project Structure
```bash
# Check project structure
ls -la
```
Expected structure:
```
├── src/
├── pom.xml
├── testng.xml
├── README.md
├── LaborDataMapping/
├── MappingData/
└── data/
```

### 3. Install Dependencies
```bash
# Clean and install all dependencies
mvn clean install

# Alternative: compile only
mvn clean compile
```

### 4. Verify Installation
```bash
# Run compilation test
mvn clean compile

# Check if build is successful
echo $?  # Should return 0 for success
```

---

## Configuration

### 1. Browser Configuration

#### Chrome Configuration (Default)
The framework is configured to use Chrome by default. Configuration is in `BaseTest.java`:
```java
ChromeOptions options = new ChromeOptions();
options.addArguments("--window-size=1024,678");
options.addArguments("--start-maximized");
// options.addArguments("--headless");  // Uncomment for headless mode
```

#### Headless Mode
To run tests in headless mode (no GUI):
1. Edit `BaseTest.java`
2. Uncomment the headless line:
   ```java
   options.addArguments("--headless");
   ```

### 2. Download Directory Configuration
Default download directory is `./PDF/`. To change:
1. Edit `BaseTest.java`
2. Modify the `folderPath1` variable:
   ```java
   String folderPath1 = System.getProperty("user.dir") + File.separator + "YourDirectory";
   ```

### 3. Test Data Configuration

#### Location Data Mapping
Configure location-specific mappings in JSON files:
- `LaborDataMapping/`: Labor category mappings
- `MappingData/`: Daily summary mappings

Example configuration for new location:
```json
{
  "Server": "Front",
  "Host": "Front",
  "Kitchen": "Kitchen",
  "Manager": "Front"
}
```

### 4. TestNG Configuration
Modify `testng.xml` for test suite configuration:
```xml
<!DOCTYPE suite SYSTEM "https://testng.org/testng-1.0.dtd">
<suite name="Test Suite" parallel="none">
  <test name="Test Cases">
    <classes>
      <class name="tests.BrinkTest"/>
      <!-- Add more test classes here -->
    </classes>
  </test>
</suite>
```

---

## Running Tests

### 1. Command Line Execution

#### Run All Tests
```bash
mvn clean test
```

#### Run Specific Test Class
```bash
mvn test -Dtest=BrinkTest
mvn test -Dtest=BrinkTestProvo
mvn test -Dtest=BrinkTwoTest
```

#### Run Specific Test Method
```bash
mvn test -Dtest=BrinkTest#testLogin
```

#### Run with TestNG XML
```bash
mvn test -DsuiteXmlFile=testng.xml
```

### 2. IDE Execution

#### IntelliJ IDEA
1. Right-click on test class or method
2. Select "Run 'TestName'"
3. Or use TestNG plugin to run test suites

#### Eclipse
1. Right-click on test class
2. Select "Run As" → "TestNG Test"

### 3. Parallel Execution
To enable parallel execution, modify `testng.xml`:
```xml
<suite name="Test Suite" parallel="methods" thread-count="3">
```

### 4. Test Reports
After test execution, reports are generated in:
- **TestNG Reports**: `test-output/index.html`
- **Surefire Reports**: `target/surefire-reports/`

---

## Deployment

### 1. Package Application
```bash
# Create JAR file
mvn clean package

# Skip tests during packaging
mvn clean package -DskipTests
```

### 2. Docker Deployment

#### Create Dockerfile
```dockerfile
FROM openjdk:8-jdk-alpine

# Install Chrome and dependencies
RUN apk add --no-cache \
    chromium \
    chromium-chromedriver \
    maven

# Set Chrome path
ENV CHROME_BIN=/usr/bin/chromium-browser
ENV CHROME_DRIVER=/usr/bin/chromedriver

# Copy project
COPY . /app
WORKDIR /app

# Install dependencies
RUN mvn clean install -DskipTests

# Run tests
CMD ["mvn", "test"]
```

#### Build and Run Docker Container
```bash
# Build image
docker build -t brink-tests .

# Run container
docker run --rm -v $(pwd)/test-output:/app/test-output brink-tests
```

### 3. CI/CD Server Deployment

#### Jenkins Pipeline
```groovy
pipeline {
    agent any
    
    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/Rcktechiees-git/Fobesoftbrink.git'
            }
        }
        
        stage('Build') {
            steps {
                sh 'mvn clean compile'
            }
        }
        
        stage('Test') {
            steps {
                sh 'mvn test'
            }
            post {
                always {
                    publishTestResults testResultsPattern: 'target/surefire-reports/*.xml'
                    publishHTML([
                        allowMissing: false,
                        alwaysLinkToLastBuild: true,
                        keepAll: true,
                        reportDir: 'test-output',
                        reportFiles: 'index.html',
                        reportName: 'TestNG Report'
                    ])
                }
            }
        }
    }
}
```

---

## CI/CD Integration

### 1. GitHub Actions

Create `.github/workflows/tests.yml`:
```yaml
name: Test Suite

on:
  push:
    branches: [ main, develop ]
  pull_request:
    branches: [ main ]

jobs:
  test:
    runs-on: ubuntu-latest
    
    steps:
    - uses: actions/checkout@v2
    
    - name: Set up JDK 8
      uses: actions/setup-java@v2
      with:
        java-version: '8'
        distribution: 'adopt'
    
    - name: Setup Chrome
      uses: browser-actions/setup-chrome@latest
    
    - name: Cache Maven packages
      uses: actions/cache@v2
      with:
        path: ~/.m2
        key: ${{ runner.os }}-m2-${{ hashFiles('**/pom.xml') }}
    
    - name: Run tests
      run: mvn clean test
    
    - name: Upload test reports
      uses: actions/upload-artifact@v2
      if: always()
      with:
        name: test-reports
        path: test-output/
```

### 2. GitLab CI

Create `.gitlab-ci.yml`:
```yaml
image: maven:3.6.3-openjdk-8

stages:
  - test

variables:
  MAVEN_OPTS: "-Dmaven.repo.local=.m2/repository"

cache:
  paths:
    - .m2/repository/

test:
  stage: test
  before_script:
    - apt-get update -qq && apt-get install -y -qq wget gnupg
    - wget -q -O - https://dl-ssl.google.com/linux/linux_signing_key.pub | apt-key add -
    - echo "deb http://dl.google.com/linux/chrome/deb/ stable main" > /etc/apt/sources.list.d/google.list
    - apt-get update -qq && apt-get install -y -qq google-chrome-stable
  script:
    - mvn clean test
  artifacts:
    reports:
      junit: target/surefire-reports/*.xml
    paths:
      - test-output/
```

---

## Troubleshooting

### Common Issues and Solutions

#### 1. ChromeDriver Issues
**Problem**: ChromeDriver version mismatch
```
Solution:
1. Update Chrome browser to latest version
2. WebDriverManager should handle driver automatically
3. If issues persist, manually set driver path:
   System.setProperty("webdriver.chrome.driver", "/path/to/chromedriver");
```

#### 2. Maven Build Failures
**Problem**: Dependencies not downloading
```bash
# Clear Maven cache
rm -rf ~/.m2/repository

# Force update dependencies
mvn clean install -U
```

#### 3. Test Execution Failures
**Problem**: Element not found errors
```
Solution:
1. Check if application UI has changed
2. Increase timeout values in Utils.java
3. Verify element locators in BrinkPage.java
4. Check network connectivity
```

#### 4. File Download Issues
**Problem**: PDF files not downloading
```
Solution:
1. Check download directory permissions
2. Verify Chrome download settings
3. Ensure popup blockers are disabled
4. Check antivirus software blocking downloads
```

#### 5. Memory Issues
**Problem**: OutOfMemoryError during test execution
```bash
# Increase Maven memory
export MAVEN_OPTS="-Xmx2048m -XX:MaxPermSize=512m"

# Or set in pom.xml
<plugin>
  <groupId>org.apache.maven.plugins</groupId>
  <artifactId>maven-surefire-plugin</artifactId>
  <configuration>
    <argLine>-Xmx2048m</argLine>
  </configuration>
</plugin>
```

### Debug Mode
Enable debug logging:
1. Add to `testng.xml`:
   ```xml
   <suite name="Test Suite" verbose="2">
   ```

2. Enable WebDriver logging:
   ```java
   System.setProperty("webdriver.chrome.verboseLogging", "true");
   ```

### Performance Optimization
1. **Parallel Execution**: Enable in TestNG configuration
2. **Headless Mode**: Faster execution without GUI
3. **Resource Cleanup**: Proper WebDriver management
4. **Test Data**: Optimize data loading and processing

### Support and Resources
- **Documentation**: Check docs/ folder for detailed guides
- **Issues**: Create GitHub issues for bugs
- **Community**: Follow best practices and contribute improvements

---

## Maintenance Schedule

### Weekly
- Update Chrome browser
- Check for dependency updates
- Review test execution reports

### Monthly  
- Update project dependencies
- Review and optimize test performance
- Update documentation

### Quarterly
- Major dependency updates
- Architecture review
- Performance benchmarking