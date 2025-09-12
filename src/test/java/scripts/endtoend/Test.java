package scripts.endtoend;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Listeners;

import idrive360.testbase.TestBase;

@Listeners(idrive360.listeners.TestResultsListeners.class)

public class Test extends TestBase{
	WebDriver driver = null;
	
	@org.testng.annotations.Test
	public void test1() throws InterruptedException {
		driver = threadLocalDriver.get();
		driver.get("https://www.google.co.in/");
		log.info("PASS");
		testResultsStatus.setTestStartTime(System.currentTimeMillis());
		Thread.sleep(2000);
		testResultsStatus.setTestEndTime(System.currentTimeMillis());
		testResultsStatus.setTestExecutionTime(testResultsStatus.getTestEndTime(),
				testResultsStatus.getTestStartTime());
		log.info("from test1: "+testResultsStatus.getTestExecutionTime());
		Assert.assertTrue(true);
		log.info("from test1 testresultstatus: "+testResultsStatus);
	}
	
	@org.testng.annotations.Test
	public void test2() throws InterruptedException {
		log.info("FAIL");
		testResultsStatus.setTestStartTime(System.currentTimeMillis());
		Thread.sleep(5000);
		testResultsStatus.setTestEndTime(System.currentTimeMillis());
		testResultsStatus.setTestExecutionTime(testResultsStatus.getTestEndTime(),
				testResultsStatus.getTestStartTime());
		log.info("from test2: "+testResultsStatus.getTestExecutionTime());
		log.error("test error");
		Assert.assertTrue(false);
	}
	
	@org.testng.annotations.Test
	public void test3() throws InterruptedException {
		log.info("PASS");
		log.info(getMessage("backupplans.createSuccMess"));
		testResultsStatus.setTestStartTime(System.currentTimeMillis());
		Thread.sleep(2000);
		testResultsStatus.setTestEndTime(System.currentTimeMillis());
		testResultsStatus.setTestExecutionTime(testResultsStatus.getTestEndTime(),
				testResultsStatus.getTestStartTime());
		log.info("from test3: "+testResultsStatus.getTestExecutionTime());
		Assert.assertTrue(true);
	}

}
