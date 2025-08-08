package tests;

import java.io.IOException;

import java.util.List;
import java.util.NoSuchElementException;
import org.openqa.selenium.By;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import io.github.bonigarcia.wdm.WebDriverManager;

import pages.BrinkPage;


import java.io.File;



import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.util.HashMap;


import org.openqa.selenium.interactions.Actions;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import base.BaseFileUtils;
import base.BaseTest;
import base.ExcelUtils;
public class BrinkTest extends BaseTest{
	public void screenShotFolder() {
		 String screenshotFolder = System.getProperty("user.dir") + File.separator + "Excel";
		 BaseFileUtils.initializeDownloadDirectory(screenshotFolder);
	}
	@BeforeMethod
public void setUp() {
		String excelFolderPath = System.getProperty("user.dir") + File.separator + "Excel";
  BaseFileUtils.initializeDownloadDirectory(excelFolderPath);
  String pdfFolderPath = System.getProperty("user.dir") + File.separator + "Excel";
  BaseFileUtils.initializeDownloadDirectory(pdfFolderPath);
  WebDriverManager.chromedriver().setup();
  ChromeOptions options = new ChromeOptions();
  HashMap<String, Object> chromePrefs = new HashMap<>();
  chromePrefs.put("download.default_directory", excelFolderPath);
  options.setExperimentalOption("prefs", chromePrefs);
  driver = new ChromeDriver(options);
  driver.manage().window().maximize();
}

@AfterMethod
public void tearDown() {
  if (driver != null) {
  	driver.quit();
  }
}
	@Test(testName="TC001",description = "Brink pos entry",dataProvider = "credentialsProvider")
	public void homebase(String BrinkURL, String BrinkUsername, String BrinkPassword, String mappingfile, String fobeurl, String fobeemail, String fobepassword) throws InterruptedException, IOException {
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
		  
		//  brink.utils.waitForElementVisibility(brink.salesSummaries, 300);
		  
		//  brink.utils.clickElement(brink.salesSummaries);
		  
		  brink.utils.waitForElementVisibility(brink.salesAndLabor, 100);
		  
		  brink.utils.clickElement(brink.salesAndLabor);
		  
		  Thread.sleep(3000);
		  
		  brink.utils.waitForElementVisibility(brink.selectLocations, 200);
		  
		  brink.utils.clickElement(brink.selectLocations);
		  
		  brink.utils.waitForElementVisibility(brink.allLocations, 300);
		  
		  brink.utils.clickElement(brink.allLocations);
		  
		  Thread.sleep(3000);
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
		  
		  WebElement frame = driver.findElement(By.tagName("iframe"));
		  
		  driver.switchTo().frame(frame);
		  
		  
		  Thread.sleep(3000);
		 
		  
		//  brink.utils.waitForElementVisibility(brink.netSales, 200);
		  
		  String netSale = driver.findElement(By.xpath("//div[@id='report_div']//table//tbody//tr//td//nobr[text()='Totals']/following::td[2]")).getText();
		  
		String netSaleAmount = netSale.replaceAll("[^\\d.]", "").replace(",", "");
		
		System.out.println("Net sales amount is :" + netSaleAmount);
		
		  String TotalPay = driver.findElement(By.xpath("//div[@id='report_div']//table//tbody//tr//td//nobr[text()='Totals']/following::td[8]")).getText();
		  
			String TotalPayAmount = TotalPay.replaceAll("[^\\d.]", "").replace(",", "");
			
			System.out.println("Total pay amount is :" + TotalPayAmount);
			
			
		 //fobesoft
			
			
			 brink.utils.openNewTab(driver, fobeurl);
    		 
			 brink.utils.waitForElementVisibility(brink.txtFieldFobeEmail, 120);
			 
			 brink.utils.sendKeysToElement(brink.txtFieldFobeEmail, fobeemail);
			 
			 brink.utils.sendKeysToElement(brink.txtFieldFobePassword, fobepassword );
     		//Thread.sleep(3000);
     	
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
     		
     		LocalDate yesterday = LocalDate.now().minusDays(1);
    		DateTimeFormatter formatters = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    		String yesterdayStr = yesterday.format(formatters);
    		System.out.println(yesterdayStr);
    		
    		Thread.sleep(3000);
    		brink.utils.waitForElementVisibility(By.xpath("//a[contains(@class,'dp-item') and @data-moment ='"+yesterdayStr+"']"), 120);
    		brink.utils.clickElement(By.xpath("//a[contains(@class,'dp-item') and @data-moment ='"+yesterdayStr+"']"));
    		brink.utils.waitForElementVisibility(By.xpath("//a[contains(@class,'dp-item dp-selected') and @data-moment ='"+yesterdayStr+"']"), 120);
    		Thread.sleep(3000);
    		Actions actions = new Actions(driver);
    		
    		 
    		        brink.utils.waitForElementVisibility(By.xpath("(//td[@title='Food']//following-sibling::td)[1]"), 20);
    		      WebElement element = driver.findElement(By.xpath("(//td[@title='Food']//following-sibling::td)[1]"));
    		        actions.doubleClick(element).build().perform(); 
    		        brink.utils.waitForElementVisibility(By.xpath("//td[@title='Food']//following-sibling::td[1]/div/div/div/div/div/input"), 120);
    		    	WebElement element1 = driver.findElement(By.xpath("//td[@title='Food']//following-sibling::td[1]/div/div/div/div/div/input"));
    		    	Thread.sleep(1000);
    		    	element1.sendKeys(String.valueOf(Keys.DELETE));
    		    	element1.sendKeys(String.valueOf(netSaleAmount));   
    		  
    	
    		 brink.utils.waitForElementVisibility(By.id("add_revenue_yearly"), 150);
    		driver.findElement(By.id("add_revenue_yearly")).click();
    		brink.utils.waitForElementVisibility(By.xpath("//div[@role='alertdialog' and contains(text(), 'Revenue updated')]"), 20);
    		System.out.println("Revenue updated successfully");
    		
    		
    		
    		brink.utils.waitForElementVisibility(By.xpath("(//a[@class='btn ng-star-inserted' and normalize-space()='Labor'])[1]"), 120);
    		brink.utils.clickWithRetry(By.xpath("(//a[@class='btn ng-star-inserted' and normalize-space()='Labor'])[1]"), 10 , 10);
    		Thread.sleep(3000);
    		brink.utils.waitForElementVisibility(By.xpath("(//td[@class='tab-lf-tx ng-star-inserted' and text()[normalize-space()='Labor']])[2]"), 120);
WebElement element3 = driver.findElement(By.xpath("(//td[@title='Hourly Labor']//following-sibling::td)[1]"));
    		 actions.doubleClick(element3).build().perform(); 
		        brink.utils.waitForElementVisibility(By.xpath("//td[@title='Hourly Labor']//following-sibling::td[1]/div/div/div/input"), 120);
		    	WebElement element2 = driver.findElement(By.xpath("//td[@title='Hourly Labor']//following-sibling::td[1]/div/div/div/input"));
		    	Thread.sleep(1000);
		    	element2.sendKeys(String.valueOf(Keys.DELETE));
		    	element2.sendKeys(String.valueOf(TotalPayAmount));   
		 
		Thread.sleep(3000);
		driver.findElement(By.id("add_labor_yearly")).click();
		
		brink.utils.waitForElementVisibility(By.xpath("//div[@role='alertdialog' and contains(text(), 'Labor updated')]"), 20);
		System.out.println("Labor Data mapped successfully");
		
		Thread.sleep(3000);
	
	
	}
	
	@DataProvider(name = "credentialsProvider")
    public Object[][] provideCredentials() throws IOException {
		
		
		String filePath =System.getProperty("user.dir") + File.separator + "Data" + File.separator + "Scarlett.xlsx";
 
        String sheetName = "Sheet1";
        return ExcelUtils.readExcelData(filePath, sheetName);
    }
}
