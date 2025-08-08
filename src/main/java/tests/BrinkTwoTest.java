package tests;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.testng.annotations.DataProvider;
import org.testng.Assert;
import pages.BrinkPage;
import java.util.List;
import java.util.NoSuchElementException;

import java.io.File;
import base.ExcelUtilsTwo;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Set;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import base.BaseFileUtils;
import base.BaseTest;
import base.ExcelUtils;
public class BrinkTwoTest extends BaseTest {
	@BeforeSuite
	public void screenShotFolder() {
		String screenshotFolder = System.getProperty("user.dir") + File.separator + "Screenshots";
		BaseFileUtils.initializeDownloadDirectory(screenshotFolder);
	}
 
	@BeforeMethod
	public void setUp() {
		String folderPath1 = System.getProperty("user.dir") + File.separator + "PDF";
		BaseFileUtils.initializeDownloadDirectory(folderPath1);
		WebDriverManager.chromedriver().setup();
		ChromeOptions options = new ChromeOptions();
		HashMap<String, Object> chromePrefs = new HashMap<>();
		chromePrefs.put("download.default_directory", folderPath1);
		options.setExperimentalOption("prefs", chromePrefs);
		driver = new ChromeDriver(options);
		driver.manage().window().maximize();
	}
 
	@AfterMethod
	public void tearDown() {
		// Quit WebDriver
		if (driver != null) {
			driver.quit();
		}
	}

@Test(testName="TC001",description = "Brink pos entry",dataProvider = "credentialsProvider")
public void homebase(String BrinkURL, String BrinkUsername, String BrinkPassword,String Locations, String mappingfile, String fobeurl, String fobeemail, String fobepassword ) throws InterruptedException, IOException {
	BrinkPage brink = new BrinkPage(driver);
	  JavascriptExecutor js=(JavascriptExecutor) driver;
	  
	  Actions acc=new Actions(driver);
	  
	  System.out.println("URL is :" + BrinkURL);
	  
	  System.out.println("Username is :" + BrinkUsername);
	  
	  System.out.println("Password is :" + BrinkPassword);
	  
	  driver.get(BrinkURL);
	  
	  brink.utils.waitForElementVisibility(brink.username, 5000);
	  
	  brink.utils.sendKeysToElement(brink.username, BrinkUsername);
	  
	  brink.utils.waitForElementVisibility(brink.password, 1000);
	  
	  brink.utils.sendKeysToElement(brink.password, BrinkPassword);
	  
	  
	  brink.utils.waitForElementToBeClickable(brink.signIn, 200);
	  
	  brink.utils.clickElement(brink.signIn);
	  
	  brink.utils.waitForElementVisibility(brink.reports, 300);
	  
	  brink.utils.clickElement(brink.reports);
	  
	  Thread.sleep(3000);
	  
	  //brink.utils.clickElement(brink.productMix);
	  
	  Thread.sleep(3000);
	  
	  brink.utils.waitForElementVisibility(brink.revenueCenter, 1000);
	  
	  brink.utils.clickElement(brink.revenueCenter);
	  
	  brink.utils.waitForElementVisibility(brink.selectLocations, 100);
	  
	//  brink.utils.clickElement(brink.selectLocations);
	  
	  brink.utils.waitForElementVisibility(brink.allStores, 150);
	  
	  Thread.sleep(5000);
	  
	 // brink.utils.clickElement(brink.allStoresExpand);
	  
	  WebElement expand = driver.findElement(By.xpath("(//div[@class='dx-treelist-empty-space dx-treelist-collapsed'])[1]"));
	  
	  
	  expand.click();
	  
	  Thread.sleep(3000);
	  
	WebElement locations = driver.findElement(By.xpath("//div//div[@class='dx-treelist-text-content']/div[text()='"+Locations+"']"));
	
	List<WebElement> locat = driver.findElements(By.xpath("//div[text()='All Stores']/following::span[@class='dx-checkbox-icon']/following::div[@class='dx-treelist-text-content']//div"));
	  
	
	for(int i=1;i<=2;i++) {
		
		String locate = driver.findElement(By.xpath("(//div[text()='All Stores']/following::span[@class='dx-checkbox-icon']/following::div[@class='dx-treelist-text-content']//div)[" + i + "]")).getText();
		
		System.out.println(locate);
		
		if(locate.equals(Locations)) {
			
			WebElement checkbox = driver.findElement(By.xpath("(//div[text()='All Stores']/following::span[@class='dx-checkbox-icon'])[" + i + "]"));
			
			
			checkbox.click();
			
			Thread.sleep(3000);
		}
	}
	
	
	
	  LocalDate currenDate= LocalDate.now();
	  
	  LocalDate minusDays = currenDate.minusDays(1);
	  
	  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
	  
	  String format = formatter.format(minusDays);
	  
	  System.out.println("Yesterday date is :" + format);
brink.utils.waitForElementVisibility(brink.dayDropdown, 200);
	  
	  brink.utils.clickElement(brink.dayDropdown);
	  
	  brink.utils.waitForElementVisibility(brink.dateRange, 300);
	  
	  brink.utils.clickElement(brink.dateRange);
	  
	  WebElement startField = driver.findElement(By.xpath("//input[@id='DateRangeModel_Start']"));
	  
	  startField.clear();
	  
	  Thread.sleep(1000);
	  
	  brink.utils.sendKeysToElement(brink.startDate, format);
	  
	  WebElement endField = driver.findElement(By.xpath("//input[@id='DateRangeModel_End']"));
	  
	  endField.clear();
	  
	  Thread.sleep(1000);
	  
	  brink.utils.sendKeysToElement(brink.endDate, format);
	  
	  Thread.sleep(1000);
	  
	  brink.utils.clickElement(brink.viewReport);
	  
	  Thread.sleep(3000);
	  
//	  WebElement frame = driver.findElement(By.tagName("iframe"));
//	  
//	  driver.switchTo().frame(frame);
//	  
//	  
//	  Thread.sleep(3000);
//	  
	  brink.utils.waitForElementVisibility(brink.pdf, 100);
	  
	  brink.utils.clickElement(brink.pdf);
	  
	  Thread.sleep(3000);
	  
	  brink.utils.waitForElementVisibility(brink.pdfs, 500);
	  
	  brink.utils.clickElement(brink.pdfs);
	  
	  Thread.sleep(3000);
	  
	  
	  String filePath = System.getProperty("user.dir") + File.separator + "MappingData" + File.separator + mappingfile ;

	    // Read JSON data from the file
	    String jsonData = new String(Files.readAllBytes(Paths.get(filePath)));
	 // Assuming you have already read the JSON file content into a String variable named jsonData.
	    Gson gson = new Gson();
	    TypeToken<HashMap<String, String>> typeToken = new TypeToken<HashMap<String, String>>() {};
	    HashMap<String, String> categoryMappings = gson.fromJson(jsonData, typeToken.getType());
	    // Continue with Gson parsing and creating the map as shown in the previous response.
	    System.out.println("Below is the mapping we provided for the code");
	    for (String key : categoryMappings.keySet()) {
	        String value = categoryMappings.get(key);
	        System.out.println("Key: " + key + ", Value: " + value);
	    }
	  
	  List<String> adjustedColumnADatas = new ArrayList<>();
	  
	  List<String> adjustedSummaryDatas = new ArrayList<>();
	  
	  // Create a map to store the merged data
      Map<String, String> mergedMap = new HashMap<>();
	  
	  brink.utils.clickElement(brink.downloadOption);
	  
	  List <String> columnADatas=new ArrayList<String>();
	  
	  List <String> summaryDatas=new ArrayList<String>();
	  
	  Map<String, Double> unmatchedElements = new HashMap<>();
	  
		Map<String, Double> categoryPrices = new HashMap<>();
	  
	  Thread.sleep(3000);
	   String folderPath = System.getProperty("user.dir") + File.separator + "PDF";
       
       File folder = new File(folderPath);
       File[] files = folder.listFiles((dir, name) -> name.endsWith(".xls"));

       if (files != null) {
           for (File file : files) {
               String filePaths = file.getAbsolutePath();
               System.out.println("Processing file: " + filePaths);
               
               // Extract data containing an asterisk from the current file
               List<String> columnAData = ExcelUtilsTwo.extractDataFromColumnA(filePaths);

               System.out.println("Data from Column A:");
               for (String data : columnAData) {
                   System.out.println(data);
                   columnADatas.add(data);
               }
               // Extract summary data
               List<String> summaryData = ExcelUtilsTwo.getSummaryData(filePaths,11);
               System.out.println("Summary data in file " + file.getName() + ":");
               for (String summary : summaryData) {
                   System.out.println(summary);
                   
                 //  double price = Double.parseDouble(summary.replaceAll("[^\\d.]", ""));
                   
                   summaryDatas.add(summary);
               }
           }
       } else {
           System.out.println("No .xls files found in the specified directory.");
       }
       
       
       if (columnADatas.size() > 3) { // Need at least 4 elements to skip 3
           
           for (int i = 1; i < columnADatas.size() - 1; i++) {
               if (i != 1) { // Skip the second element
                   adjustedColumnADatas.add(columnADatas.get(i));
               }
           }

           // Adjust summaryDatas by skipping the second element if necessary
         
           for (int i = 0; i < summaryDatas.size(); i++) {
              // Skip the second element (index 1)
                   adjustedSummaryDatas.add(summaryDatas.get(i));
               
           }
System.out.println("Size of the column a :" + adjustedColumnADatas.size());

System.out.println("Size of the summary datas :" + adjustedSummaryDatas.size());


System.out.println(adjustedColumnADatas);

System.out.println(adjustedSummaryDatas);
           // Ensure both adjusted lists have the same size
           if (adjustedColumnADatas.size() != adjustedSummaryDatas.size()) {
               System.out.println("Adjusted lists must be of the same size.");
               return;
           }

          

           // Merge the lists into the map
           for (int i = 0; i < adjustedColumnADatas.size(); i++) {
               mergedMap.put(adjustedColumnADatas.get(i), adjustedSummaryDatas.get(i));
           }

           // Print the resulting map
           System.out.println("Merged Map:");
           for (Map.Entry<String, String> entry : mergedMap.entrySet()) {
               System.out.println(entry.getKey() + " : " + entry.getValue());
           }
       } else {
           System.out.println("The first list does not have enough elements.");
       }
   
       System.out.println("Below is the mapping we provided for the code");

       
       for (Map.Entry<String, String> entry : mergedMap.entrySet()) {		
    	   
    	  String elementName = entry.getKey(); 
    	  
    	  System.out.println(elementName);
    	   
    	  String priceStr = entry.getValue();
    	   
    	  double price = Double.parseDouble(priceStr.replaceAll("[^\\d.]", ""));
    	   
    	  String category = null;
		    for (String keyword : categoryMappings.keySet()) {
		        if (elementName.trim().equals(keyword.trim())) {
		            category = categoryMappings.get(keyword);
		            break;
		        }
		    }

		    // Add the price to the corresponding category or store it separately
		    if (category != null) {
		        double currentPrice = categoryPrices.getOrDefault(category, 0.0);
		        categoryPrices.put(category, currentPrice + price);
		    } else {
		        unmatchedElements.put(elementName, price);
		    }
		}

 
    	   
   	for (Map.Entry<String, Double> entry : categoryPrices.entrySet()) {
	    System.out.println("Category: " + entry.getKey() + ", Total Price: " + entry.getValue());
	}

	// Print the unmatched elements
	System.out.println("Unmatched Elements:");
	for (Map.Entry<String, Double> entry : unmatchedElements.entrySet()) {
	    System.out.println("Element: " + entry.getKey() + ", Price: " + entry.getValue());
	}
	
	 if (!unmatchedElements.isEmpty()) {
            System.out.println("Unmatched Elements Found:");
            for (Map.Entry<String, Double> entry : unmatchedElements.entrySet()) {
                System.out.println("Element: " + entry.getKey() + ", Price: " + entry.getValue());
            }
            
            Assert.fail("Some conditions were not met.");
        } else {
            System.out.println("No unmatched elements found.");
        }
/********************************fobe******************************/
	 
	 brink.utils.openNewTab(driver, fobeurl);
		brink.utils.waitForElementVisibility(brink.txtFieldFobeEmail, 320);
		brink.utils.sendKeysToElement(brink.txtFieldFobeEmail, fobeemail);
		brink.utils.sendKeysToElement(brink.txtFieldFobePassword, fobepassword );
		brink.utils.waitForElementVisibility(brink.btnLoginFobe, 150);
		brink.utils.clickElement(brink.btnLoginFobe);
		Thread.sleep(3000);

	try {

				List  <WebElement> popup = driver.findElements(By.xpath("//div[@class='xmark']"));

				if (!popup.isEmpty()) {
					
				
					
					WebElement closeIcon = driver.findElement(By.xpath("//div[@class='xmark']//i"));
					
					js.executeScript("arguments[0].click();", closeIcon);

					
				} else {
					System.out.println("First Page link not found, skipping...");
				}


				Thread.sleep(3000);
				
			} catch (NoSuchElementException e) {

				System.out.println("Popup is not displayed. Continuing with remaining actions...");

			} catch (Exception ex) {

				System.out.println("An unexpected exception occurred: " + ex.getMessage());
			}
		  
		
		try {
			List<WebElement> popup = driver.findElements(By.xpath("//h1[text()='Your Account is Deactivated']"));
		
		if(popup.isEmpty()) {
		
		
		// heartland.utils.waitForElementVisibility(By.xpath("//h1[text()='Your Account is Deactivated']"), 150);
	//	String deactivated = heartland.utils.booleanWaitForElementVisibility(By.xpath("//h1[text()='Your Account is Deactivated']"), 15);
	//	if(deactivated == "b") {
		//heartland.utils.waitForElementVisibility(By.xpath("//img[@class=' syslogout' and @id='logosys']"), 120);
		//Thread.sleep(3000);
		LocalDate yesterday = LocalDate.now().minusDays(1);
		DateTimeFormatter formatters = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String yesterdayStr = yesterday.format(formatters);
		System.out.println(yesterdayStr);
		
		//Thread.sleep(5000);
		//driver.findElement(By.xpath("//a//i[@id='dp-calendar']")).click();
	/*	Thread.sleep(3000);
		driver.findElement(By.xpath("//ul[@class='pagination']//button//i[@class='fa fa-angle-left dp-nav-left']")).click();
		Thread.sleep(3000);
		driver.findElement(By.xpath("//ul[@class='pagination']//button//i[@class='fa fa-angle-left dp-nav-left']")).click();
		Thread.sleep(3000);*/
		//driver.findElement(By.xpath("//td[@class='day' and text()='13']")).click();
		Thread.sleep(3000);
		brink.utils.waitForElementVisibility(By.xpath("//a[contains(@class,'dp-item') and @data-moment ='"+yesterdayStr+"']"), 120);
		brink.utils.clickElement(By.xpath("//a[contains(@class,'dp-item') and @data-moment ='"+yesterdayStr+"']"));
		brink.utils.waitForElementVisibility(By.xpath("//a[contains(@class,'dp-item dp-selected') and @data-moment ='"+yesterdayStr+"']"), 120);
		Thread.sleep(3000);
		Actions actions = new Actions(driver);
		for (Map.Entry<String, Double> entry : categoryPrices.entrySet()) {
		    String category = entry.getKey();
		    double totalPrice = entry.getValue();
            
		    // Construct the locator based on the category
		    String locator = "(//td[@title='"+category+"']//following-sibling::td)[1]";

		    // Find the element and send the total price
		    List<WebElement> elements = driver.findElements(By.xpath(locator));
		    
		    if (elements.isEmpty()) {
		        // Handle the case when the element is not found
		        System.out.println("Element not found for category: " + category);
		    }
		    else {
		    	brink.utils.waitForElementVisibility(By.xpath("(//td[@title='"+category+"']//following-sibling::td)[1]"), 20);
		        WebElement element = elements.get(0);
		        actions.doubleClick(element).build().perform(); 
		        brink.utils.waitForElementVisibility(By.xpath("//td[@title='"+category+"']//following-sibling::td[1]/div/div/div/div/div/input"), 120);
		    	WebElement element1 = driver.findElement(By.xpath("//td[@title='"+category+"']//following-sibling::td[1]/div/div/div/div/div/input"));
		    	Thread.sleep(1000);
		    	element1.sendKeys(String.valueOf(Keys.DELETE));
		    	element1.sendKeys(String.valueOf(totalPrice));   
		    }
		
	}
		brink.utils.waitForElementVisibility(By.id("add_revenue_yearly"), 150);
		driver.findElement(By.id("add_revenue_yearly")).click();
		brink.utils.waitForElementVisibility(By.xpath("//div[@role='alertdialog' and contains(text(), 'Revenue updated')]"), 20);
		System.out.println("Revenue updated successfully");
		}else {
			System.out.println(fobeemail +" Account is deactivated");
		}
		} catch (NoSuchElementException e) {

			System.out.println("There is no data  for selected date......");

		} catch (Exception ex) {

			System.out.println("An unexpected exception occurred: " + ex.getMessage());
		}
		}
		
	
		
			
@DataProvider(name = "credentialsProvider")
public Object[][] provideCredentials() throws IOException {
	
	
	String filePath =System.getProperty("user.dir") + File.separator + "Data" + File.separator + "Scarlett.xlsx";

    String sheetName = "Sheet2";
    return ExcelUtils.readExcelData(filePath, sheetName);
}
}
