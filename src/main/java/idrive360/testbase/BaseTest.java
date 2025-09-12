package idrive360.testbase;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.*;

import idrive360.PlaywrightFactory.PlaywrightFactory;
import idrive360.data.UserCredentials;
import idrive360.managers.ExtentManager;
import idrive360.managers.PageObjectManager;
import idrive360.utilities.LocalExcelUtility;
import idrive360.utilities.TakeScreenShot;

import java.lang.reflect.Method;

import org.testng.ITestResult;
import org.testng.annotations.*;

public class BaseTest extends PlaywrightFactory{

	protected static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();
	protected static ExtentReports extentReports;
	
	protected Page page;
	PlaywrightFactory playwrightFactory;
	protected PageObjectManager pageObjectManager;
	protected UserCredentials userCredentials;

	@BeforeSuite
	public void setupReport() {
		extentReports = ExtentManager.getInstance();
	}

	@AfterSuite
	public void tearDownReport() {
		extentReports.flush();
	}

	@Parameters({ "excelFileStatus", "browser", "headlessMode", "execution environment" })
	@BeforeClass
	public void setUp(@Optional("yes") String excelFileStatus, @Optional("chrome") String browser,
			@Optional("no") boolean headlessMode, @Optional("TestServer25") String executionEnvironment) {
		playwrightFactory = new PlaywrightFactory();
		playwrightFactory.initBrowser(excelFileStatus, browser, headlessMode, executionEnvironment);
		page = PlaywrightFactory.getPage();
		pageObjectManager = new PageObjectManager(page);
		localExcelUtility = new LocalExcelUtility();
		log.info("Page initialized? " + (page != null));
	}

	@AfterClass
	public void tearDown() {
		PlaywrightFactory.closeBrowser();
	}

	@BeforeMethod
	public void createExtentTest(Method method) {
		try {
			ExtentTest test = extentReports.createTest(method.getName());
			extentTest.set(test);
		} catch (Exception e) {
			System.err.println("❌ Failed to create ExtentTest: " + e.getMessage());
			e.printStackTrace();
		}
	}

	@AfterMethod
	public void testresult(ITestResult result) {
		try {
			ExtentTest logger = extentTest.get(); // defensive check
			if (logger != null) {
				switch (result.getStatus()) {
				case ITestResult.FAILURE:
					logger.fail(result.getThrowable());
					String screenshotPath = TakeScreenShot.capture(page, result.getName());
					if (screenshotPath != null) {
						logger.addScreenCaptureFromPath(screenshotPath);
					}
					break;

				case ITestResult.SUCCESS:
					logger.pass("✔️ Test passed");
					break;

				case ITestResult.SKIP:
					logger.skip("⚠️ Test skipped");
					break;
				}
			} else {
				System.err.println("⚠️ [ExtentTest] logger is null in @AfterMethod.");
			}
		} catch (Exception e) {
			System.err.println("⚠️ [AfterMethod] Error logging test result: " + e.getMessage());
			e.printStackTrace();
		}
	}

	public static ExtentTest getLogger() {
		return extentTest.get();
	}
}