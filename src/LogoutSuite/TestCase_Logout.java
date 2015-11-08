package LogoutSuite;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import Util.TestUtil;

public class TestCase_Logout {
	
	@BeforeTest
	public void checkTestSkip() throws Exception
	 {	 
		TestUtil.checkTestSkip("LogoutSuite","TestCase_Logout");
	 }	 
	
	@Test
	public void logout() {
		System.out.println("Application Logout link is Clicked");
	}

	@AfterMethod 
	public void DefaultResutl(ITestResult result) throws InterruptedException {
	  System.out.println("method name:" + result.getMethod().getMethodName());
	  System.out.println("Sucess %:" + result.isSuccess());
	  if(!result.isSuccess()){
		  TestUtil.takeScreenShot(result.getMethod().getMethodName());
		
	  }
	  else{
		  System.out.println("Method is pass Not need to call Exit");  
		  
	  }
	}
}
