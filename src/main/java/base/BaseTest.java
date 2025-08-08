package base;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.awt.Dimension;
import java.io.File;
import java.util.HashMap;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;


public class BaseTest {
	 protected WebDriver driver;

	    @BeforeMethod
	    public void setUp() {
	    	String folderPath1 = System.getProperty("user.dir") + File.separator + "PDF";
	        //BaseFileUtils.initializeDownloadDirectory(folderPath1);
//	        options.addArguments("start-maximized");
//	        options.addArguments("disable-infobars");
//	        options.addArguments("--disable-extensions");
//	        options.addArguments("--disable-notifications");
//	        options.addArguments("--disable-web-security");
//	        options.addArguments("--no-proxy-server");
//	        options.addArguments("--disable-application-cache");
//	        String downloadPath = "C:\\Users\\Jetzerp\\eclipse-workspace\\PO_Upload\\new";
//	        options.addArguments("user-data-dir=" + downloadPath);
//	        options.addArguments("profile-directory=Default");
	        
	        WebDriverManager.chromedriver().setup();
	       
	        ChromeOptions options = new ChromeOptions();
	       // options.addArguments("--window-size=1024,768");
	      //  driver.manage().window().setSize(new Dimension(1024, 768));


	       
	        options.addArguments("--window-size=1024,678");
	        options.addArguments("--window-size=1024,678", "--start-maximized");
	      //  options.addArguments("--window-size=" + screenSize.getWidth() + "," + screenSize.getHeight());

	        //options.addArguments("--headless");
	     // Set the download directory
	        //String downloadDirectory = "C:\\Users\\Jetzerp\\eclipse-workspace\\PO_Upload\\data";
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
}
