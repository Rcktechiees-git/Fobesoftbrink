package pages;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.openqa.selenium.By;
import base.Utils;

public class BrinkPage {
	 protected WebDriver driver;
	 public  Utils utils;
	 
	 public BrinkPage(WebDriver driver) {
	    	this.driver = driver;
	        this.utils = new Utils(driver);
	    }
	 
	 public By username=By.xpath("//input[@id='Username']");
	 
	 public By password=By.xpath("//input[@id='Password']");
	 
	 public By signIn=By.xpath("//button[text()='Continue']");
	 
	 public By reports=By.xpath("//a[@href='/Reports/Reports/']");
	 
	 public By searchField=By.xpath("//input[@id='reportSearchInput']");
	 
	 public By salesSummaries=By.xpath("//a[text()='Sales Summary']']");
	 
	// public By salesSummaries=By.xpath("//a//span[text()='Sales Summaries']");
	 
	 public By salesAndLabor=By.xpath("//a[text()[normalize-space()='Sales And Labor Summary By Location']]");
	 
	// public By salesAndLabor=By.xpath("//a//span[text()='Sales And Labor Summary By Location']");
	 
	 public By selectLocations=By.xpath("//span//span[@title='Select Locations']");
	 
	 public By allLocations=By.xpath("//ul[@class='select2-results__options']//li[text()='All Locations']");
	 
	 public By dayDropdown=By.xpath("(//span[@class='select2-selection select2-selection--single'])[2]//span[2]");
	 
	 public By dateRange=By.xpath("//ul//li[text()='Date Range']");
	 
	 public By startDate=By.xpath("//input[@id='DateRangeModel_Start']");
	 
	 public By endDate=By.xpath("//input[@id='DateRangeModel_End']");
	 
	 public By viewReport=By.xpath("//button[@id='run-report' and text()='View Report']");
	 
	 public By netSales=By.xpath("//div[@id='report_div']//table//tbody//tr//td//nobr[text()='Totals']/following::td[2]");
	 
	 public By totalPay=By.xpath("//div[@id='report_div']//table//tbody//tr//td//nobr[text()='Totals']/following::td[8]");
	 
		//Login screen 
	   	public By txtHeaderLogIn = By.className("activepage");
	   	
	   	public By txtFieldFobeEmail = By.id("EMail1");
	   	
	   	public By txtFieldFobePassword = By.id("Password1");
	   	
	   	public By btnLoginFobe = By.cssSelector("button#login_btn>span");
	   	
	   	
	   	//labor
	   	
	  // 	public By productMix=By.xpath("//span[text()='Product Mix']");
	   	
		public By productMix=By.xpath("(//a[text()='Product Mix'])[1]");
	   	
	   	public By revenueCenter=By.xpath("//a[text()[normalize-space()='By Revenue Center']]");
	   	
	   	//
	   	
	   	public By pdf=By.xpath("//input[@value='PDF']");
	   	
	   	public By pdfs=By.xpath("//tr//td[text()='XLS']");
	   	
	   	public By downloadOption=By.xpath("//img[@title='Export a report and save it to the disk']");
	   	
	   	
	   	
	   	//
	   	
	   	public By allStores=By.xpath("//div[text()='All Stores']");
	   	
	   	public By allStoresExpand=By.xpath("(//div[@class='dx-treelist-empty-space dx-treelist-collapsed'])[1]");
	   	
	   	public By laborTableCategory=By.xpath("//span[contains(text(), 'Total')]/following-sibling::span");
	   	
	   	public By laborTableAmount=By.xpath("//span[contains(text(), 'Total')]/following-sibling::span/following::td[2]");
	   	
	   	
	   	//
	   	
	   //	public By salesSummaries=By.xpath("//span[text()='Sales Summaries']");
	   	
	   	public By salesSummaryLabor=By.xpath("//span[text()='Sales Summary']");
	   	
	   	public By locationDropdown=By.xpath("(//span[@class='selection'])[1]");
}
