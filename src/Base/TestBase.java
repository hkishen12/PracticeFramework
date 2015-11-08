package Base;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeTest;

import Util.Xls_Reader;

public class TestBase {
	
	public static Logger APP_LOGS=null;
	public static Properties CONFIG=null;
	public static Properties OR=null;
	public static Properties Data=null;
	public static Properties CONFIG_ENV=null;
	public static Properties Expected_string=null;
	public static boolean isBrowserOpened=false;
	public static WebDriver driver =null;
	public static WebDriver dr=null;
	public static boolean isLoggedIn=false;
	public static boolean isInitalized=false;
	public static Xls_Reader suiteXls=null;
	public static String idforQSSearch=null;
	public static boolean setSkipFlag=false;
	public static WebDriverWait wait =null;
	
	
	// initializing the Tests
		public static void initialize() throws Exception
		{
			// logs
			if(!isInitalized)
			{
				PropertyConfigurator.configure("src/Logs/log4j.properties");// this line is optional if log4j.properties file is kept at src
				//PropertyConfigurator.configure("src/Logs/log4j.xml"); //can use xml to configure instead of properties file.

				APP_LOGS = Logger.getLogger("ExamLogger");
				APP_LOGS.debug("Initialized log file successfully!!");

				// to suppress warning message related to log4j initialization in system
				System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.Jdk14Logger");
				APP_LOGS.debug("Initialized log4j in system properly!!");

				//Reading the Excel and property file			
				APP_LOGS.debug("Loading Property files");
				
				// LOad the Excel file		
				APP_LOGS.debug("Loading Excel files");
				suiteXls = new Xls_Reader(System.getProperty("user.dir")+"//src//com//xls//Suite.xlsx");
				APP_LOGS.debug("Loaded Excel Files successfully");

				isInitalized=true;
	     }
			
	 }
		//find the test suite is runnable
		public static boolean isSuiteRunnable(Xls_Reader xls , String suiteName)
		{
			boolean isexecutable = false;
			for(int i=2; i<=xls.getRowCount("Test Suite"); i++)
			{

				if((xls.getCellData("Test Suite", "TSID", i)).equals(suiteName))
				{
					
					if((xls.getCellData("Test Suite", "Runmode", i)).equalsIgnoreCase("Y"))
					{
						isexecutable =  true;
						break;
					}
					else
					{
						isexecutable = false;				}
				}
			}
			xls= null;
			return isexecutable;

		}
		
		/// returns true if runmode of the test is equal to Y
		public static boolean isTestCaseRunnable(Xls_Reader xls,String SheetName, String testCaseName){
			boolean isExecutable=false;
			for(int i=2; i<= xls.getRowCount(SheetName) ; i++){

				if(xls.getCellData(SheetName, "testCaseName", i).equalsIgnoreCase(testCaseName)){
					if(setSkipFlag){
						xls.setCellData(SheetName, "Runmode", i, "N");
					}else if(xls.getCellData(SheetName, "Runmode", i).equalsIgnoreCase("Y")){
						isExecutable= true;
					}else{
						isExecutable= false;
					}
				}
			}

			return isExecutable;

		}
		
		
		
		// selenium Webdriver open a browser if its not opened		
		public static void openBrowser()
		{
			if(!isBrowserOpened)
			{
				FirefoxProfile profile = new FirefoxProfile();
				DesiredCapabilities dc=new DesiredCapabilities();
				dc.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR,UnexpectedAlertBehaviour.ACCEPT);
				dc.setCapability(FirefoxDriver.PROFILE, profile);

				profile.setEnableNativeEvents(true);

				profile.setPreference("browser.download.folderList", 2);
				profile.setPreference("browser.download.manager.showWhenStarting", false);
				profile.setPreference("browser.download.dir", System.getProperty("user.dir")+"\\src\\Download");
				profile.setPreference("browser.download.downloadDir", System.getProperty("user.dir")+"\\src\\Download");
				profile.setPreference("browser.download.defaultFolder", System.getProperty("user.dir")+"\\src\\Download");
				profile.setPreference("browser.download.manager.closeWhenDone", true);
				profile.setPreference("browser.helperApps.neverAsk.saveToDisk", "application/zip");

				if(CONFIG.getProperty("browserType").equalsIgnoreCase("Firefox") ||CONFIG.getProperty("browserType").equalsIgnoreCase("FF") ){
					driver = new FirefoxDriver(profile);
				}
				else if (CONFIG.getProperty("browserType").equals("InternetExplorer")||CONFIG.getProperty("browserType").equalsIgnoreCase("IE")){
					System.setProperty("webdriver.ie.driver", System.getProperty("user.dir")+"\\src\\drivers\\IEDriverServer.exe");
					driver = new InternetExplorerDriver();
				}
				else if (CONFIG.getProperty("browserType").equals("GoogleChrome")||CONFIG.getProperty("browserType").equalsIgnoreCase("CHROME")){
					System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"\\src\\drivers\\chromedriver.exe");
					driver = new ChromeDriver();
				}
				driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
				wait = new WebDriverWait(driver, 2);
				driver.manage().window().maximize();
				driver.manage().deleteAllCookies();
				isBrowserOpened=true;
				driver.get(CONFIG.getProperty("baseUrl"));
				APP_LOGS.debug("Selecting the Browser");

			}
		}	

   }
