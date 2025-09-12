package scripts.cancellation;

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

public class CancellationOfIDrive360Account extends TestBase {

	UserCredentials userCredentials = null;
	PageObjectManager pageObjectManager = null;
	WebDriver driver = null;
	Wait<WebDriver> wait = null;

	public CancellationOfIDrive360Account() {

	}

	public CancellationOfIDrive360Account(PageObjectManager pageObjectManager, WebDriver driver, Wait<WebDriver> wait,
			TestResultsStatus testResultsStatus) {
		this.pageObjectManager = pageObjectManager;
		this.driver = driver;
		this.wait = wait;
		this.testResultsStatus = testResultsStatus;
	}

	@Parameters({ "excelFileStatus", "browser", "headlessMode", "executionEnvironment" })
	@BeforeClass
	public void initializeEnvironment(@Optional("yes") String excelFileStatus, @Optional("chrome") String browser,
			@Optional("no") String headlessMode, @Optional("prodserver") String executionEnvironment) {
		try {

			initializeTestingEnvironment(excelFileStatus, browser, headlessMode, executionEnvironment);
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

		testResultsStatus.setTestName(CancellationOfIDrive360Account.class.getSimpleName());
		driver = threadLocalDriver.get();
		wait = threadLocalWait.get();
		pageObjectManager = new PageObjectManager(driver, wait);

	}

	@Test
	public void runLoginTest() throws InterruptedException {

		driver.get(Constants.loginURL);

		wait.until(ExpectedConditions.visibilityOf(pageObjectManager.getLoginPage().btnSignIn));
		log.info("Login Page loaded");
		// Get the required user credentials
		try {

			userCredentials = localExcelUtility.getCompleteSheetData(getWorkbookPath_AccountsForTesting(),
					"Cancellation", "cancelationofactiveaccount");
			pageObjectManager.setUserCredentials(userCredentials);
		} catch (Exception e) {
			testResultsStatus.setTestResult("Fail");
			testResultsStatus.setTestResultComment("\n" + e.getMessage());
			e.printStackTrace();
			Assert.assertTrue(false, "Reached exception while getting user details");
		}
		try {
			pageObjectManager.getLoginPage().loginToAccount(testResultsStatus, userCredentials);
			Thread.sleep(10000);
			logicForCancellationOfIDrive360Account(testResultsStatus.getTestName());
		} catch (Exception e) {
			e.printStackTrace();
			testResultsStatus.setIsLoginSuccess(false);
			testResultsStatus.setTestResult("Fail");
			testResultsStatus.setTestResultComment("\n" + e.getMessage());
			Assert.assertTrue(false, "Exception occured");
		}

	}

	public TestResultsStatus logicForCancellationOfIDrive360Account(String testName) {
		try {
			testResultsStatus.setIsLoginSuccess(true);

			pageObjectManager.getHeaderFunctionalityPage().navigateToMyAccount(testResultsStatus);

			pageObjectManager.getAccountPage().appearanceAndDisappearenceOfLoader(testResultsStatus);

			pageObjectManager.getAccountPage().waitAndClickCancelMyAccount(testResultsStatus);

			// Enter data and click Cancel
			pageObjectManager.getAccountPage().enterDataAndClickCancelMyAccount(userCredentials, testResultsStatus);
			// start time
			testResultsStatus.setTestStartTime(System.currentTimeMillis());

			pageObjectManager.getAccountPage().clickbtnCancelMyAccountFromPopup(testResultsStatus);

			wait.until(ExpectedConditions.visibilityOf(pageObjectManager.getLoginPage().cancelledAccountToastMsg));
			String msg = pageObjectManager.getLoginPage().cancelledAccountToastMsg.getText();

			// end time
			testResultsStatus.setTestEndTime(System.currentTimeMillis());
			testResultsStatus.setTestExecutionTime(testResultsStatus.getTestEndTime(),
					testResultsStatus.getTestStartTime());
			log.info(msg);

			if (msg.equals(getMessage("cancel.cancellationofidrive360account"))) {
				testResultsStatus.setIsLoginSuccess(false);
				testResultsStatus.setTestResult("Pass");
				testResultsStatus.setTestResultComment("Admin user account has been cancelled.\n" + msg);
			} else if (msg.equals(getMessage("login.canceledMaccountMessage"))) {
				testResultsStatus.setIsLoginSuccess(false);
				testResultsStatus.setTestResult("Pass");
				testResultsStatus.setTestResultComment("Sub user account has been cancelled.\n" + msg);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Waited for element and as element did not appear, timed out");
			testResultsStatus.setTestResult("Fail");
			testResultsStatus.setTestResultComment("Account is not cancelled " + e.getMessage());
			wait.until(ExpectedConditions.visibilityOf(pageObjectManager.getLoginPage().txtbxEmail));
			Assert.assertTrue(false, "Reached exception while waiting, post login");
		}
		return testResultsStatus;
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
