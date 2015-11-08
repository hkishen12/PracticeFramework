package Util;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.SkipException;

import Base.TestBase;

public class TestUtil extends TestBase {
	
	public static void checkSuiteSkip(String TestSuiteName) throws Exception
	{
		initialize();
		APP_LOGS.debug("Checking Runmode of" +TestSuiteName);
		System.out.println(isSuiteRunnable(suiteXls,TestSuiteName));	

		if(!isSuiteRunnable(suiteXls, TestSuiteName))
		{
			APP_LOGS.debug("Skipped '" +TestSuiteName +" ' as the runmode was set to NO");
			throw new SkipException("Runmode of '" +TestSuiteName + "' set to no. So Skipping all tests in Suite");
		}

	}
	
	public static void checkTestSkip(String TestSuiteName, String TestcaseeName) throws Exception
	{
		initialize();
		APP_LOGS.debug("Checking Runmode of" +TestSuiteName);
		System.out.println(isTestCaseRunnable(suiteXls,TestSuiteName ,TestcaseeName ));
		if(!isTestCaseRunnable(suiteXls,TestSuiteName,TestcaseeName))
		{
			APP_LOGS.debug("Skipped '" +TestcaseeName +"' as the runmode was set to NO");
			throw new SkipException("Runmode of '" +TestcaseeName +"' set to no. So Skipping all tests in" +TestSuiteName);

		}

	}
	
	public static void takeScreenShot(String MethodName){
		System.out.println("Taking screen shot for"+MethodName);
		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);

		try {
			FileUtils.copyFile(scrFile, new File(System.getProperty("user.dir")+"\\XSLT_Reports\\output\\ScreenShot\\"+MethodName+".png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
