package scripts.login;

import java.io.FileNotFoundException;
import java.io.IOException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import idrive360.data.UserCredentials;
import idrive360.managers.PageObjectManager;
import idrive360.testbase.Constants;
import idrive360.testbase.TestBase;
import idrive360.utilities.TestResultsStatus;

public class LoginToFreeTrialAccount extends TestBase {

	UserCredentials userCredentials = null;
	PageObjectManager pageObjectManager = null;
	WebDriver driver = null;
	Wait<WebDriver> wait = null;

	public LoginToFreeTrialAccount() {

	}

	public LoginToFreeTrialAccount(PageObjectManager pageObjectManager, WebDriver driver, Wait<WebDriver> wait,
			TestResultsStatus testResultsStatus) {

		this.pageObjectManager = pageObjectManager;
		this.driver = driver;
		this.wait = wait;
		this.testResultsStatus = testResultsStatus;

	}

	@Parameters({ "excelFileStatus", "browser", "headlessMode", "execution environment" })
	@BeforeClass

	public void initializeTest(@Optional("yes") String excelFileStatus, @Optional("chrome") String browser,
			@Optional("no") String headlessMode, @Optional("TestServer25") String executionEnvironment)
			throws FileNotFoundException, IOException {
		try {
			initializeTestingEnvironment(excelFileStatus, browser, headlessMode, executionEnvironment);
			testResultsStatus.setTestName(LoginToFreeTrialAccount.class.getSimpleName());
		} catch (FileNotFoundException fnfe) {
			log.error("Properties file could not be found");
			fnfe.printStackTrace();
		} catch (IOException ioe) {
			log.error("Path for file is not found or incorrect.");
			ioe.printStackTrace();
		} catch (TimeoutException toe) {
			log.error("Wait creation failed");
		} catch (Exception e) {
			e.printStackTrace();
		}

		driver = threadLocalDriver.get();
		wait = threadLocalWait.get();
		log.info(wait);
		pageObjectManager = new PageObjectManager(driver, wait);
		testResultsStatus.setTestName(LoginToFreeTrialAccount.class.getSimpleName());

	}

	@Test
	public void runLoginTest() {
		driver.manage().window().maximize();

		driver.get(Constants.loginURL);

		wait.until(ExpectedConditions.visibilityOf(pageObjectManager.getLoginPage().btnSignIn));
		log.info("Login Page loaded");
		// Get the required user credentials
		try {

			userCredentials = localExcelUtility.getCompleteSheetData(getWorkbookPath_AccountsForTesting(), "Login",
					"accountwithfreetrial");
			log.info(userCredentials.emailID + "  " + userCredentials.password);
			pageObjectManager.setUserCredentials(userCredentials);

		} catch (Exception e1) {
			e1.printStackTrace();
		}

		pageObjectManager.getLoginPage().enterEmail(pageObjectManager.getUserCredentials().emailID);
		pageObjectManager.getLoginPage().enterPasword(pageObjectManager.getUserCredentials().password);
		testResultsStatus.setTestStartTime(System.currentTimeMillis());
		pageObjectManager.getLoginPage().clickSignIn();
		log.info("Clicked Login button");

		logicForLoginToFreeTrialAccount();
		// Once test is completed, end the session.
		pageObjectManager.getHeaderFunctionalityPage().clickAccountSection();
		pageObjectManager.getHeaderFunctionalityPage().clickLogout();
		wait.until(ExpectedConditions.visibilityOf(pageObjectManager.getLoginPage().txtbxEmail));

	}

	public void logicForLoginToFreeTrialAccount() {
		try {
			// To confirm if login is successful,
			// Wait for the Add devices button at the center to be displayed.
			// Wait for the Free trial expires message banner shows in the header for the
			// free trial account.

			wait.until(ExpectedConditions.visibilityOf(pageObjectManager.getDevicePage().btnAddDevices));
			wait.until(ExpectedConditions.visibilityOf(pageObjectManager.getLoginPage().freeTrialUpgradebanner));
			log.info("Your free trial expires, Upgrade Now");
			testResultsStatus.setIsLoginSuccess(true);
			testResultsStatus.setTestResult("Pass");
			testResultsStatus.setTestResultComment("Add devices button appeared");
			testResultsStatus.setTestResultComment("Free Trial expired header message shown post login");
			testResultsStatus.setTestEndTime(System.currentTimeMillis());
			testResultsStatus.setTestExecutionTime(testResultsStatus.getTestEndTime(),
					testResultsStatus.getTestStartTime());
		} catch (Exception e) {
			log.error("Waited for element and as element did not appear, timed out");
			// Set the test results to fail with appropriate comments
			testResultsStatus.setTestResult("Fail");
			testResultsStatus.setTestResultComment("Add devices button did not appear post login" + e.getMessage());
			log.error(testResultsStatus.getTestResultComment());
			testResultsStatus.setTestEndTime(System.currentTimeMillis());
			testResultsStatus.setTestExecutionTime(testResultsStatus.getTestEndTime(),
					testResultsStatus.getTestStartTime());
			// Once test is completed, end the session.
			pageObjectManager.getHeaderFunctionalityPage().clickAccountSection();
			pageObjectManager.getHeaderFunctionalityPage().clickLogout();
			wait.until(ExpectedConditions.visibilityOf(pageObjectManager.getLoginPage().txtbxEmail));
		}

		pageObjectManager.getActivityLogsPage().AssertActivityLogs(getMessage("activitylogs.UserLogin")
				.replace("[userEmail]", pageObjectManager.getUserCredentials().emailID), testResultsStatus);

	}

	@AfterTest
	public void endTest() {
		// Write the test results to the test results workbook and close the browser.
		try {

			localExcelUtility.writeTestResultsStatus(testResultsStatus.getTestName(), testResultsStatus.getTestResult(),
					testResultsStatus.getTestResultComment(), 0);
		} catch (Exception e) {
			log.error("Reached exception while writing testresults.");
			e.printStackTrace();
			Assert.assertTrue(false, "Reached exception while waiting, post login");
		}
		driver.quit();
	}
}
