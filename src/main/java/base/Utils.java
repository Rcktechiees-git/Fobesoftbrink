package base;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.Toolkit;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.Set;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.Assert;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.concurrent.TimeoutException;
import java.util.regex.Pattern;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;


import com.google.common.collect.Multiset.Entry;



import org.openqa.selenium.Point;
import java.util.Calendar;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.text.SimpleDateFormat;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.awt.Toolkit;
public class Utils {
	 protected WebDriver driver;
    public Utils(WebDriver driver) {
        this.driver = driver;
    }
    
    public static void initializeDownloadDirectory(String downloadDirectory) {
        File directory = new File(downloadDirectory);

        if (directory.exists()) {
            try {
                org.apache.commons.io.FileUtils.deleteDirectory(directory);
                System.out.println("Existing download directory deleted: " + downloadDirectory);
            } catch (IOException e) {
                System.out.println("Failed to delete the existing download directory: " + downloadDirectory);
                e.printStackTrace();
            }
        }

        if (directory.mkdirs()) {
            System.out.println("Download directory created: " + downloadDirectory);
        } else {
            System.out.println("Failed to create the download directory: " + downloadDirectory);
        }
    }
       
    public String booleanWaitForElementVisibility(By locator, int timeoutInSeconds) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            return "a";
        } catch (Exception e) {
            return "b"; 
        }
    }
    
    public int getHttpStatus(String url) {
        try {
            URL urlObj = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) urlObj.openConnection();         
            connection.setRequestMethod("GET");
            connection.connect();
            return connection.getResponseCode();
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
    }
     
    public WebElement retryUntilInteractable( By locator, int maxAttempts, int waitDurationInSeconds) {
        WebElement element = null;

        for (int attempt = 0; attempt < maxAttempts; attempt++) {
            try {
                WebDriverWait wait = new WebDriverWait(driver, waitDurationInSeconds);
                element = wait.until(ExpectedConditions.elementToBeClickable(locator));
                break;
            } catch (StaleElementReferenceException | ElementNotInteractableException e) {
                // Element is not yet interactable, continue to retry
            }
        }

        if (element == null) {
            throw new NoSuchElementException("Element not found or not interactable after multiple retries.");
        }

        return element;
    }
    
    public  boolean isElementPresent(WebDriver driver, By locator) {
        try {
            driver.findElement(locator);
            return true; // Element found
        } catch (NoSuchElementException e) {
            return false; // Element not found
        }
    }
    
    public boolean verifyDate(String dateStr, int number) {
    	  LocalDate currentDate = LocalDate.now();
          LocalDate date = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("MM/dd/yyyy"));

          if (number == 1) {
              return date.isEqual(currentDate);
          } else if (number == 2) {
              LocalDate yesterday = currentDate.minusDays(1);
              return date.isEqual(currentDate) || date.isEqual(yesterday);
          } else if (number == 3) {
              LocalDate yesterday = currentDate.minusDays(1);
              LocalDate dayBeforeYesterday = currentDate.minusDays(2);
              return date.isEqual(currentDate) || date.isEqual(yesterday) || date.isEqual(dayBeforeYesterday);
          }

          return false; // Invalid number
        }
    
    public void scrollWithinTheElement(WebDriver driver, WebElement container, WebElement element) {
    	// Calculate the relative Y offset of the element within the container
        int offsetY = element.getLocation().getY() - container.getLocation().getY();
        
        // Scroll the container to the element's position
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollTop = arguments[1]", container, offsetY);

        // Wait for the element to be clickable
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.or(
            ExpectedConditions.elementToBeClickable(element),
            ExpectedConditions.visibilityOf(element)
        ));
        
        // If element is still not clickable, scroll further and recheck
        while (!element.isEnabled()) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollTop += arguments[1]", container, container.getSize().getHeight());
            wait.until(ExpectedConditions.or(
                ExpectedConditions.elementToBeClickable(element),
                ExpectedConditions.visibilityOf(element)
            ));
        }
    }

    
    public int getElementCount(By locator) {
        List<WebElement> elements = driver.findElements(locator);
        return elements.size();
    } 
    
    public  WebElement waitForElementClickable(WebDriver driver, By locator, int timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }
    
    public  void waitForElementToDisappear(WebDriver driver, By locator, int timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }
    
    public  void setScreenZoom(WebDriver driver, String zoomValue) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("document.body.style.zoom='" + zoomValue + "'");
    }
    
    public static int extractNumber(String input) {
        // Find the index of the first occurrence of a digit
        int startIndex = 0;
        for (int i = 0; i < input.length(); i++) {
            if (Character.isDigit(input.charAt(i))) {
                startIndex = i;
                break;
            }
        }      
        // Find the index of the first occurrence of a non-digit character after the number
        int endIndex = input.length();
        for (int i = startIndex + 1; i < input.length(); i++) {
            if (!Character.isDigit(input.charAt(i))) {
                endIndex = i;
                break;
            }
        }

        // Extract the number substring and convert it to an integer
        String numberString = input.substring(startIndex, endIndex);
        return Integer.parseInt(numberString);
    }
    public void switchToTab(int tabIndex) {
        ArrayList<String> tabHandles = new ArrayList<>(driver.getWindowHandles());
        
        if (tabIndex >= 0 && tabIndex < tabHandles.size()) {
            String desiredTabHandle = tabHandles.get(tabIndex);
            driver.switchTo().window(desiredTabHandle);
        } else {
            System.out.println("Invalid tab index: " + tabIndex);
        }
    }
    public void waitForElement(By locator) {
    	WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
    
    public void clickElement(By locator) {
        waitForElement(locator);
        driver.findElement(locator).click();
    }

    public boolean clickWithRetry(By locator, int maxAttempts, int waitDuration) {
        int attempts = 0;
        boolean clicked = false;

        while (attempts < maxAttempts) {
            try {
                WebElement element = driver.findElement(locator);
                element.click();
                clicked = true;
                break; // Exit the loop when click operation is successful
            } catch (ElementNotInteractableException | NoSuchElementException e) {
                // Element click was intercepted, not interactable, or not found,
                // increment the attempts counter
                attempts++;
                // Wait for the specified duration before retrying the click
                try {
                    Thread.sleep(waitDuration);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            }
        }

        return clicked;
    }

    public void sendKeysToElement(By locator, String text) {
        waitForElement(locator);
        WebElement element = driver.findElement(locator);
        element.clear();
        element.sendKeys(text);
    }
    
    public void verifyText(By locator, String expectedText) {
        try {
            waitForElement(locator);
            String actualText = driver.findElement(locator).getText();
            System.out.println(actualText);
            if (!actualText.equals(expectedText)) {
                throw new AssertionError("Text verification failed. Expected: " + expectedText + ", Actual: " +actualText);
            }
        } catch (Exception e) {
            throw new AssertionError("An exception occurred while verifying text: " +expectedText);
        }
    }
    
    public boolean verifyElementTextAfterTrim( By locator, String expectedText) {
    	waitForElement(locator);
        String actualText = driver.findElement(locator).getText().trim();
        String expectedTextTrimmed = expectedText.trim();
        return actualText.equals(expectedTextTrimmed);
    }
    
    public static Properties loadProperties(String filePath) {
        Properties properties = new Properties();
        try {
            FileInputStream fileInputStream = new FileInputStream(filePath);
            properties.load(fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }
    
    public void getElementPosition(By locator) {
        WebElement element = driver.findElement(locator);
        Point elementLocation = element.getLocation();
        System.out.println(elementLocation); 
    }
    
    public  void assertElementPosition(By locator, int expectedXPosition, int expectedYPosition) {
        WebElement element = driver.findElement(locator);
        Point elementPosition = element.getLocation();

        Assert.assertEquals(elementPosition.getX(), expectedXPosition);
        Assert.assertEquals(elementPosition.getY(), expectedYPosition);
    }
    
    public void verifyImage(By locator) {
        WebElement imageElement = driver.findElement(locator);
        String imageUrl = imageElement.getAttribute("src");

        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet httpGet = new HttpGet(imageUrl);

        try {
            HttpResponse response = httpClient.execute(httpGet);
            int statusCode = response.getStatusLine().getStatusCode();
            Assert.assertEquals(statusCode, 200, "Image verification failed. Image URL: " + imageUrl);
        } catch (IOException e) {
            Assert.fail("Exception occurred while verifying the image: " + e.getMessage());
        }
    }

    public  void selectOptionByVisibleText(By locator, String optionText) {
    	WebElement element = driver.findElement(locator);
        Select dropdown = new Select(element);
        dropdown.selectByVisibleText(optionText);
    }

    public boolean isElementDisplayed(By locator, String expectedText) {
        try {
            waitForElement(locator);
            return driver.findElement(locator).isDisplayed();
        } catch (Exception e) {
            throw new AssertionError(expectedText+ " not present");
        }
    }
    
    public  void assertURL(String expectedURL) {
    	String actualURL = driver.getCurrentUrl();
        Assert.assertEquals(actualURL, expectedURL, "URL Assertion Failed");
    }
    
    public boolean isTextFieldEditable(By locator) {
    	WebElement textField = driver.findElement(locator);
        return textField.isEnabled() && textField.getAttribute("readonly") == null;
    }
    
    public  boolean isButtonClickable( By locator) {
        WebElement button = driver.findElement(locator);
        return button.isEnabled();
    }
    
    public void waitForElementVisibility(By locator, int timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
    
    public void waitForElementVisibility(By locator, int timeoutInSeconds, int maxRetries) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        int retryCount = 0;

        while (retryCount < maxRetries) {
            try {
                wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
                return; // Element is found, exit the loop
            } catch (StaleElementReferenceException e) {
                System.out.println("StaleElementReferenceException occurred. Retrying...");
                retryCount++;
            }
        }

        System.out.println("Element not found even after retries.");
        // Handle the failure case or throw an exception as needed
    }
    
    public void  waitForElementToBeClickable(By locator, int timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.until(ExpectedConditions.elementToBeClickable(locator));
    }
    
    public static void scrollToBottom(WebDriver driver) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("window.scrollTo(0, document.body.scrollHeight);");
    }
    
    public static void setScreenResolution(WebDriver driver, int widthPercentage, int heightPercentage) {
        Dimension screenSize = driver.manage().window().getSize();
        int newWidth = (int) (screenSize.getWidth() * (widthPercentage / 100.0));
        int newHeight = (int) (screenSize.getHeight() * (heightPercentage / 100.0));
        Dimension newResolution = new Dimension(newWidth, newHeight);
        driver.manage().window().setSize(newResolution);
    }
    
    public void scrollToElement(WebElement element) {
        try {
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({ behavior: 'smooth', block: 'center', inline: 'center' });", element);
            // Add a small delay to ensure the scroll is completed before proceeding
            Thread.sleep(500);
            
            // Scroll within the element using the scroll method
            ((JavascriptExecutor) driver).executeScript("arguments[0].scroll(0, arguments[0].scrollHeight);", element);
            // Add a small delay after scrolling
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public void scrollToTop() {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("window.scrollTo(0, 0);");
    }
    
    public boolean isElementNotDisplayed(By locator) {
        try {
            WebElement element = driver.findElement(locator);
            return !element.isDisplayed();
        } catch (NoSuchElementException e) {
            return true; // Element not found, so it is not displayed
        }
    }

    public  void clickCheckbox(By locator) {
        WebElement checkbox = driver.findElement(locator);
        
        if (!checkbox.isSelected()) {
            checkbox.click();
        }
    }
    
    //Frame
    public void switchToFrame(String frameNameOrId) {
        driver.switchTo().frame(frameNameOrId);
    }
    
    public void switchToFrame(int frameIndex) {
        driver.switchTo().frame(frameIndex);
    }
    
    public void switchToFrame(WebElement frameElement) {
        driver.switchTo().frame(frameElement);
    }
    
    public void navigateBack() {
        driver.navigate().back();
    }

    public void switchToDefaultContent() {
        driver.switchTo().defaultContent();
    }
    
    public int extractApprovedNumberFromString(String input) {
        // Find the opening parenthesis index
        int openingParenIndex = input.indexOf("(");
        // Find the closing parenthesis index
        int closingParenIndex = input.indexOf(")");
        
        // Extract the substring between the parentheses
        String numberString = input.substring(openingParenIndex + 1, closingParenIndex);
        
        // Convert the extracted substring to an integer
        int number = Integer.parseInt(numberString);
        
        return number;
    }
    
    public void switchToTabByIndex(int tabIndex) {
        // Get all window handles
        Set<String> windowHandles = driver.getWindowHandles();
        
        // Check if the requested tab index is valid
        if (tabIndex >= 0 && tabIndex < windowHandles.size()) {
            // Convert the set of window handles to a list
            List<String> handlesList = new ArrayList<>(windowHandles);
            
            // Switch to the tab at the specified index
            driver.switchTo().window(handlesList.get(tabIndex));
        } else {
            // Handle invalid tab index
            System.out.println("Invalid tab index: " + tabIndex);
        }
    }

    public  String getLastThursday() {
	    LocalDate currentDate = LocalDate.now();
	    LocalDate lastThursday = currentDate.minusDays(1); // Start from the previous day

	    while (lastThursday.getDayOfWeek() != DayOfWeek.THURSDAY) {
	        lastThursday = lastThursday.minusDays(1);
	    }

	    String formattedDate = lastThursday.format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));

	    return formattedDate;
	}

    public String getLastWednesday() {
        LocalDate currentDate = LocalDate.now();
        LocalDate lastWednesday = currentDate;
        
        do {
            lastWednesday = lastWednesday.minusDays(1);
        } while (lastWednesday.getDayOfWeek() != DayOfWeek.WEDNESDAY); 

        String formattedDate = lastWednesday.format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));

        return formattedDate;
    }
    
    public  String getYesterdayFormattedDate() {
        // Get the current date
        LocalDate currentDate = LocalDate.now();

        // Get yesterday's date by subtracting one day
        LocalDate yesterdayDate = currentDate.minusDays(1);

        // Create a DateTimeFormatter to format the date in your desired format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy");

        // Format yesterday's date
        String yesterdayFormatted = yesterdayDate.format(formatter);

        return yesterdayFormatted;
    }
    public  String getYesterdayFormattedDays() {
        // Get the current date
        LocalDate currentDate = LocalDate.now();

        // Get yesterday's date by subtracting one day
        LocalDate yesterdayDate = currentDate.minusDays(2);

        // Create a DateTimeFormatter to format the date in your desired format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy");

        // Format yesterday's date
        String yesterdayFormatted = yesterdayDate.format(formatter);

        return yesterdayFormatted;
    }
    
    public  String getYesterdayFormattedDay() {
        // Get the current date
        LocalDate currentDate = LocalDate.now();

        // Get yesterday's date by subtracting one day
        LocalDate yesterdayDate = currentDate.minusDays(2);

        // Create a DateTimeFormatter to format the date in your desired format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy");

        // Format yesterday's date
        String yesterdayFormatted = yesterdayDate.format(formatter);

        return yesterdayFormatted;
    }
    
    
    public String getPreviousMonthAndLastDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);

        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        SimpleDateFormat sdf = new SimpleDateFormat("MMMM yyyy");
        String previousMonthAndLastDate = sdf.format(calendar.getTime());

        return previousMonthAndLastDate;
    }
    public  String getScreenResolution() {
        // Get the default toolkit
        Toolkit toolkit = Toolkit.getDefaultToolkit();

        // Get the screen size
        java.awt.Dimension screenSize = toolkit.getScreenSize();

        // Get the screen width and height
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;

        // Return the screen resolution as a string
        return screenWidth + "x" + screenHeight;
    }

    public  void main(String[] args) {
        // Example usage
        String resolution = getScreenResolution();
        System.out.println("Screen Resolution: " + resolution);
    }
    public  void setWindowSize(WebDriver driver, int width, int height) {
        if (driver instanceof JavascriptExecutor) {
            JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
            String setWindowSizeScript = "window.innerWidth = arguments[0]; window.innerHeight = arguments[1];";
            jsExecutor.executeScript(setWindowSizeScript, width, height);
        } else {
            System.out.println("WebDriver is not capable of executing JavaScript. Unable to set window size.");
        }
    }
    public void setWindowSize(int width, int height) {
	    if (driver instanceof JavascriptExecutor) {
	        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
	        String setWindowSizeScript = "window.innerWidth = arguments[0]; window.innerHeight = arguments[1];";
	        jsExecutor.executeScript(setWindowSizeScript, width, height);
	    } else {
	        System.out.println("WebDriver is not capable of executing JavaScript. Unable to set window size.");
	    }
	}
    // String awsAccessKey = "AKIA2JPUCIF2U5DNQPKN";

    // String awsSecretKey = "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";

    // String region = "ap-south-1"; 

    // String topicArn = "arn:aws:sns:ap-south-1:707570844021:pipeline_alert"; 

    // String message = "Pipeline failed.Please check....";
    // public void sendNotifications(String awsAccessKey, String awsSecretKey, String region, String topicArn, String message) {
    //     try {
    //         SnsClient snsClient = SnsClient.builder()
    //                 .region(Region.of(region))
    //                 .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(awsAccessKey, awsSecretKey)))
    //                 .build();

    //         PublishRequest publishRequest = PublishRequest.builder()
    //                 .topicArn(topicArn)
    //                 .message(message)
    //                 .build();

    //         PublishResponse publishResult = snsClient.publish(publishRequest);
    //         System.out.println("Message sent successfully. Message ID: " + publishResult.messageId());
    //     } catch (Exception e) {
    //         System.out.println("Error publishing message: " + e.getMessage());
    //     }
    // }
    public  String getYesterdayFormattedDates() {
        // Get the current date
        LocalDate currentDate = LocalDate.now();

        // Get yesterday's date by subtracting one day
        LocalDate yesterdayDate = currentDate.minusDays(2);

        // Create a DateTimeFormatter to format the date in your desired format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy");

        // Format yesterday's date
        String yesterdayFormatted = yesterdayDate.format(formatter);

        return yesterdayFormatted;
    }

    public  void clickElementWithJS(WebDriver driver, WebElement element) {
      	 
        JavascriptExecutor js = (JavascriptExecutor) driver;
        
        js.executeScript("arguments[0].click()", element);
   }
    public  void openNewTab(WebDriver driver, String url) throws InterruptedException {
        ((JavascriptExecutor) driver).executeScript("window.open();");
        Thread.sleep(1000); // Wait for the new tab to open

        Set<String> allTabs = driver.getWindowHandles();
        ArrayList<String> tabs = new ArrayList<>(allTabs);
        driver.switchTo().window(tabs.get(tabs.size() - 1));
        driver.get(url);
        Thread.sleep(1000); // Wait for the page to load
    }

    public  void switchToTab(WebDriver driver, int tabIndex) {
        Set<String> allTabs = driver.getWindowHandles();
        ArrayList<String> tabs = new ArrayList<>(allTabs);
        driver.switchTo().window(tabs.get(tabIndex));
    }
    public  void closeCurrentTabAndSwitchBack(WebDriver driver) {
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.close();
        driver.switchTo().window(tabs.get(tabs.size() - 2));  // Switch to the previous tab
    }
    public  String calculateDayOfWeek(String month, int day, int year) {
        // Convert month string to LocalDate object
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM");
        LocalDate date = LocalDate.of(year, Month.valueOf(month.toLowerCase()), day);

        // Get the day of the week
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        return dayOfWeek.name(); // Return the day of the week as a string
    }
    public  String capitalizeFirstLetter(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        return input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
    }
    public  Set<String> getLastSevenDays() {
        Set<String> dates = new HashSet<>();
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d");

        for (int i = 1; i <= 7; i++) {
            dates.add(currentDate.minusDays(i).format(formatter));
        }
        return dates;
    }
    public  void compareMaps(Map<String, Double> map1, Map<String, Double> map2) {
        // Check for entries in map1
        for (Map.Entry<String, Double> entry : map1.entrySet()) {
            String key = entry.getKey();
            Double value1 = entry.getValue();
            Double value2 = map2.get(key);

            if (value2 == null) {
              //  System.out.println("Key '" + key + "' is not present in the second map.");
            } else if (!value1.equals(value2)) {
                System.out.println("Value for key '" + key + "' is different. Map1: " + value1 + ", Map2: " + value2);
            }
        }

        // Check for entries in map2 that are not in map1
        for (String key : map2.keySet()) {
            if (!map1.containsKey(key)) {
                //System.out.println("Key '" + key + "' is not present in the first map.");
            }
        }
    }
    
    public  int calculateDayOfWeek(String month, int day) {
        // Convert month string to Month enum
        Month monthEnum = Month.valueOf(month.toUpperCase());

        // Create a LocalDate object for the specified month and day in the year 2024
        LocalDate date = LocalDate.of(2024, monthEnum, day);

        // Get the day of the week index (0 for Saturday, 1 for Sunday, ..., 6 for Friday)
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        return dayOfWeek.getValue(); // Return day of week index (0-6)
    }

    public  class DateInfo {
        private LocalDate date;
        private String month;
        private String day;

        public DateInfo(LocalDate date, String month, String day) {
            this.date = date;
            this.month = month;
            this.day = day;
        }

        public LocalDate getDate() {
            return date;
        }

        public String getMonth() {
            return month;
        }

        public String getDay() {
            return day;
        }

        @Override
        public String toString() {
            return month + " " + day + ", " + date.getYear();
        }

		
    }

    public  List<DateInfo> getLastSevenDay() {
        LocalDate currentDate = LocalDate.now();
        List<DateInfo> lastSevenDays = new ArrayList<>();
        DateTimeFormatter monthFormatter = DateTimeFormatter.ofPattern("MMM", Locale.ENGLISH);
        DateTimeFormatter dayFormatter = DateTimeFormatter.ofPattern("d", Locale.ENGLISH);

        for (int i = 0; i < 7; i++) {
            LocalDate date = currentDate.minus(i, ChronoUnit.DAYS);
            String month = monthFormatter.format(date);
            String day = dayFormatter.format(date);
            lastSevenDays.add(new DateInfo(date, month, day));
        }

        return lastSevenDays;
    }
    
    public  List<DateInfo> getLastSevenDaysLabor() {
        List<DateInfo> lastSevenDays = new ArrayList<>();

        LocalDate currentDate = LocalDate.now();

        for (int i = 1; i <= 7; i++) {
            LocalDate date = currentDate.minusDays(i);

            String dayOfWeek = date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
            String formattedDate = date.format(DateTimeFormatter.ofPattern("MMMM dd, yyyy"));

            DateInfo dateInfo = new DateInfo(date, dayOfWeek, formattedDate);
            lastSevenDays.add(dateInfo);
        }

        return lastSevenDays;
    }
    public  List<DateInfo> getLastWeekDays() {
        List<DateInfo> weekDays = new ArrayList<>();

        // Get current date
        LocalDate currentDate = LocalDate.now();

        // Find last Monday and current Sunday
        LocalDate lastMonday = currentDate.with(TemporalAdjusters.previous(DayOfWeek.SUNDAY));
        LocalDate currentSunday = currentDate.with(TemporalAdjusters.nextOrSame(DayOfWeek.SATURDAY));

        // Iterate from last Monday to current Sunday
        LocalDate date = lastMonday;
        while (!date.isAfter(currentSunday)) {
            String dayOfWeek = date.getDayOfWeek().toString();
            String formattedDate = date.format(DateTimeFormatter.ofPattern("MMMM dd, yyyy"));

            weekDays.add(new DateInfo(date, dayOfWeek, formattedDate));
            date = date.plusDays(1);
        }

        return weekDays;
    }
    public  List<String> getLastWeekDatesWithDayAndMonth() {
        List<String> lastWeekDates = new ArrayList<>();
        LocalDate currentDate = LocalDate.now();
        LocalDate mondayOfLastWeek = currentDate.minusWeeks(2).with(DayOfWeek.SUNDAY);
        LocalDate sundayOfCurrentWeek = currentDate.minusWeeks(1).with(DayOfWeek.SATURDAY);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy");

        while (!mondayOfLastWeek.isAfter(sundayOfCurrentWeek)) {
            lastWeekDates.add(mondayOfLastWeek.format(formatter));
            mondayOfLastWeek = mondayOfLastWeek.plusDays(1);
        }

        return lastWeekDates;
    }
    public  List<SimpleEntry<String, Integer>> getLastWeekDatesWithMonthAndDaySeparate() {
        List<SimpleEntry<String, Integer>> lastWeekDates = new ArrayList<>();
        LocalDate currentDate = LocalDate.now();
        LocalDate mondayOfLastWeek = currentDate.minusWeeks(2).with(DayOfWeek.SUNDAY);
        LocalDate sundayOfCurrentWeek = currentDate.minusWeeks(1).with(DayOfWeek.SATURDAY);

        DateTimeFormatter monthFormatter = DateTimeFormatter.ofPattern("MMMM");
        DateTimeFormatter dayFormatter = DateTimeFormatter.ofPattern("d");

        while (!mondayOfLastWeek.isAfter(sundayOfCurrentWeek)) {
            String month = mondayOfLastWeek.format(monthFormatter);
            int day = Integer.parseInt(mondayOfLastWeek.format(dayFormatter));
            lastWeekDates.add(new SimpleEntry<>(month, day));
            mondayOfLastWeek = mondayOfLastWeek.plusDays(1);
        }

        return lastWeekDates;
    }
    public  List<String> getLastWeekDatesWithFormats() {
        List<String> lastWeekDates = new ArrayList<>();
        LocalDate currentDate = LocalDate.now();
        LocalDate mondayOfLastWeek = currentDate.minusWeeks(2).with(DayOfWeek.SUNDAY);
        LocalDate sundayOfCurrentWeek = currentDate.minusWeeks(1).with(DayOfWeek.SATURDAY);

        DateTimeFormatter fullDateFormatter = DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy");
        DateTimeFormatter dayMonthFormatter = DateTimeFormatter.ofPattern("MMMM d");

        while (!mondayOfLastWeek.isAfter(sundayOfCurrentWeek)) {
            String fullDate = mondayOfLastWeek.format(fullDateFormatter);
            String month = mondayOfLastWeek.format(DateTimeFormatter.ofPattern("MMMM"));
            String day = mondayOfLastWeek.format(DateTimeFormatter.ofPattern("d"));
            lastWeekDates.add(fullDate);
            mondayOfLastWeek = mondayOfLastWeek.plusDays(1);
        }

        return lastWeekDates;
    }
    public List<String> getDateRangeWithFormats(LocalDate startDate, LocalDate endDate) {
        List<String> dateRange = new ArrayList<>();
        DateTimeFormatter fullDateFormatter = DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy");
        DateTimeFormatter monthFormatter = DateTimeFormatter.ofPattern("MMMM");
        DateTimeFormatter dayFormatter = DateTimeFormatter.ofPattern("d");

        while (!startDate.isAfter(endDate)) {
            String fullDate = startDate.format(fullDateFormatter);
            String month = startDate.format(monthFormatter);
            String day = startDate.format(dayFormatter);
            dateRange.add(fullDate);
            startDate = startDate.plusDays(1);
        }

        return dateRange;
    }
    public  List<String> getLastWeekDatesWithFormatsTwo() {
        List<String> lastWeekDates = new ArrayList<>();
        LocalDate currentDate = LocalDate.now();
        LocalDate mondayOfLastWeek = currentDate.minusWeeks(2).with(DayOfWeek.SUNDAY);
        LocalDate sundayOfCurrentWeek = currentDate.minusWeeks(1).with(DayOfWeek.SATURDAY);

        DateTimeFormatter fullDateFormatter = DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy");
        DateTimeFormatter dayMonthFormatter = DateTimeFormatter.ofPattern("MMMM d");

        while (!mondayOfLastWeek.isAfter(sundayOfCurrentWeek)) {
            String fullDate = mondayOfLastWeek.format(fullDateFormatter);
            String month = mondayOfLastWeek.format(DateTimeFormatter.ofPattern("MMMM"));
            String day = mondayOfLastWeek.format(DateTimeFormatter.ofPattern("d"));
            lastWeekDates.add(fullDate);
            mondayOfLastWeek = mondayOfLastWeek.plusDays(1);
        }

        return lastWeekDates;
    }
public  List<String> getLastWeekDatesWithFormatsLabor() {
        List<String> lastWeekDates = new ArrayList<>();
        LocalDate currentDate = LocalDate.now();
        LocalDate mondayOfLastWeek = currentDate.minusWeeks(2).with(DayOfWeek.SUNDAY);
        LocalDate sundayOfCurrentWeek = currentDate.minusWeeks(1).with(DayOfWeek.SATURDAY);

        DateTimeFormatter fullDateFormatter = DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy");
        DateTimeFormatter dayMonthFormatter = DateTimeFormatter.ofPattern("MMMM d");

        while (!mondayOfLastWeek.isAfter(sundayOfCurrentWeek)) {
            String fullDate = mondayOfLastWeek.format(fullDateFormatter);
            String month = mondayOfLastWeek.format(DateTimeFormatter.ofPattern("MMMM"));
            String day = mondayOfLastWeek.format(DateTimeFormatter.ofPattern("d"));
            lastWeekDates.add(fullDate);
            mondayOfLastWeek = mondayOfLastWeek.plusDays(1);
        }

        return lastWeekDates;
    }
    
    
/*public  List<String> getLastWeekDatesWithFormatsLabor() {
        List<String> lastWeekDates = new ArrayList<>();
        LocalDate currentDate = LocalDate.now();
        LocalDate mondayOfLastWeek = currentDate.minusWeeks(4).with(DayOfWeek.MONDAY);
        LocalDate sundayOfCurrentWeek = currentDate.minusWeeks(0).with(DayOfWeek.WEDNESDAY);

        DateTimeFormatter fullDateFormatter = DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy");
        DateTimeFormatter dayMonthFormatter = DateTimeFormatter.ofPattern("MMMM d");

        while (!mondayOfLastWeek.isAfter(sundayOfCurrentWeek)) {
            String fullDate = mondayOfLastWeek.format(fullDateFormatter);
            String month = mondayOfLastWeek.format(DateTimeFormatter.ofPattern("MMMM"));
            String day = mondayOfLastWeek.format(DateTimeFormatter.ofPattern("d"));
            lastWeekDates.add(fullDate);
            mondayOfLastWeek = mondayOfLastWeek.plusDays(1);
        }

        return lastWeekDates;
    }*/
    
 /*   public  List<String> getLastWeekDatesWithFormatsLabor() {
        List<String> lastWeekDates = new ArrayList<>();
        LocalDate currentDate = LocalDate.now();
        LocalDate mondayOfLastWeek = currentDate.minusWeeks(3).with(DayOfWeek.SUNDAY);
        LocalDate sundayOfCurrentWeek = currentDate.minusWeeks(2).with(DayOfWeek.TUESDAY);

        DateTimeFormatter fullDateFormatter = DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy");
        DateTimeFormatter dayMonthFormatter = DateTimeFormatter.ofPattern("MMMM d");

        while (!mondayOfLastWeek.isAfter(sundayOfCurrentWeek)) {
            String fullDate = mondayOfLastWeek.format(fullDateFormatter);
            String month = mondayOfLastWeek.format(DateTimeFormatter.ofPattern("MMMM"));
            String day = mondayOfLastWeek.format(DateTimeFormatter.ofPattern("d"));
            lastWeekDates.add(fullDate);
            mondayOfLastWeek = mondayOfLastWeek.plusDays(1);
        }

        return lastWeekDates;
    }*/
    public  Set<String> getMismatchedElements(Set<String> set1, Set<String> set2) {
        Set<String> mismatchedData = new LinkedHashSet<>();

       
        for (String element : set2) {
            if (!set1.contains(element)) {
                mismatchedData.add(element);
            }
        }

        return mismatchedData;
    }

    public  String escapeXPathTitle(String title) {
        return "concat('" + title.replace("'", "', \"'\", '") + "')";
    }
    public void writeCategoryPricesToExcel(String filePath, String date, String restaurantName, Map<String, Double> previousPrices, Map<String, Double> updatedPrices) {
        try {
            Workbook workbook;
            Sheet sheet;
            FileInputStream fileInputStream = null;

            // Check if file exists. If yes, open it; otherwise, create a new workbook and sheet.
            boolean fileExists = new File(filePath).exists();
            if (fileExists) {
                fileInputStream = new FileInputStream(filePath);
                workbook = new XSSFWorkbook(fileInputStream);
                sheet = workbook.getSheet("Category Prices");
                if (sheet == null) {
                    sheet = workbook.createSheet("Category Prices");
                    createHeaderRow(sheet);
                }
            } else {
                workbook = new XSSFWorkbook();
                sheet = workbook.createSheet("Category Prices");
                createHeaderRow(sheet);
            }

            int rowNum = sheet.getLastRowNum() + 1;

            // Create data rows for only differing values
            for (String category : updatedPrices.keySet()) {
                Double previousPrice = previousPrices.getOrDefault(category, 0.0);
                Double updatedPrice = updatedPrices.get(category);

                if (!previousPrice.equals(updatedPrice)) {
                    Row row = sheet.createRow(rowNum++);
                    row.createCell(0).setCellValue(date.toString());
                    row.createCell(1).setCellValue(restaurantName);
                    row.createCell(2).setCellValue(category);
                    row.createCell(3).setCellValue(previousPrice);
                    row.createCell(4).setCellValue(updatedPrice);
                }
            }

            if (fileInputStream != null) {
                fileInputStream.close();
            }

            // Write the output to the file
            try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                workbook.write(fileOut);
            }

            workbook.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createHeaderRow(Sheet sheet) {
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Date");
        headerRow.createCell(1).setCellValue("Restaurant");
        headerRow.createCell(2).setCellValue("Category");
        headerRow.createCell(3).setCellValue("Previous Price");
        headerRow.createCell(4).setCellValue("Updated Price");
    }
    public  Set<String> getLastFourDays() {
        Set<String> dates = new HashSet<>();
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d");

        for (int i = 1; i <= 4; i++) {
            dates.add(currentDate.minusDays(i).format(formatter));
        }
        return dates;
    }
    public  void storeCategories(Set<String> categories, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (String category : categories) {
                writer.write(category);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
  

   
    public  Set<String> findNewCategories(Set<String> todayCategories, Set<String> yesterdayCategories) {
        Set<String> newCategories = new HashSet<>(todayCategories);
        newCategories.removeAll(yesterdayCategories);
        return newCategories;
    }
    public String getYesterdayMonth() {
        // Get the current date
        LocalDate currentDate = LocalDate.now();

        // Get yesterday's date by subtracting one day
        LocalDate yesterdayDate = currentDate.minusDays(1);

        // Get the month name of yesterday's date
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM");

        // Format yesterday's month
        String yesterdayMonth = yesterdayDate.format(formatter);

        return yesterdayMonth;
    }
    public String getYesterdayDate() {
        // Get the current date
        LocalDate currentDate = LocalDate.now();

        // Get yesterday's date by subtracting one day
        LocalDate yesterdayDate = currentDate.minusDays(1);

        // Get the month name of yesterday's date
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d");

        // Format yesterday's month
        String yesterdayMonth = yesterdayDate.format(formatter);

        return yesterdayMonth;
    }
    public  void clickAnywhere(WebDriver driver, int x, int y) {
        // JavaScript to click at specific coordinates
        String script = String.format("document.elementFromPoint(%d, %d).click();", x, y);

        // Execute the JavaScript
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript(script);
    }
    
   

}