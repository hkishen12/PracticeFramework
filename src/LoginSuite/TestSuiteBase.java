package LoginSuite;

import org.testng.annotations.BeforeSuite;

import Util.TestUtil;
import Base.TestBase;

public class TestSuiteBase extends TestBase {
	@BeforeSuite
    public void checkSuiteSkip() throws Exception{
		
   	 TestUtil.checkSuiteSkip("LoginSuite");
	}
}
