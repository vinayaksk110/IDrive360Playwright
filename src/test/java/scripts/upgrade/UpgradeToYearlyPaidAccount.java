package scripts.upgrade;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
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

public class UpgradeToYearlyPaidAccount extends TestBase {

	UserCredentials userCredentials = null;
	PageObjectManager pageObjectManager = null;
	WebDriver driver = null;
	Wait<WebDriver> wait = null;
	private String executionEnvironment;

	public UpgradeToYearlyPaidAccount() {

	}

	public UpgradeToYearlyPaidAccount(PageObjectManager pageObjectManager, WebDriver driver, Wait<WebDriver> wait,
			TestResultsStatus testResultsStatus) {
		this.pageObjectManager = pageObjectManager;
		this.driver = driver;
		this.wait = wait;
		this.testResultsStatus = testResultsStatus;
	}

	@Parameters({ "excelFileStatus", "browser", "headlessMode", "executionEnvironment" })
	@BeforeClass
	public void initializeTest(@Optional("yes") String excelFileStatus, @Optional("chrome") String browser,
			@Optional("no") String headlessMode, @Optional("TestServer25") String executionEnvironment)
			throws FileNotFoundException, IOException {
		try {
			initializeTestingEnvironment(excelFileStatus, browser, headlessMode, executionEnvironment);
			testResultsStatus.setTestName(UpgradeToYearlyPaidAccount.class.getSimpleName());
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
		pageObjectManager = new PageObjectManager(driver, wait);
		this.executionEnvironment = executionEnvironment;
		testResultsStatus.setTestName(UpgradeToYearlyPaidAccount.class.getSimpleName());

	}

	@Test
	public void runLoginTest() {
		driver.manage().window().maximize();
		driver.get(Constants.loginURL);
		wait.until(ExpectedConditions.visibilityOf(pageObjectManager.getLoginPage().btnSignIn));
		log.info("Login Page loaded");
		// Get the required user credentials
		try {

			userCredentials = localExcelUtility.getCompleteSheetData(getWorkbookPath_AccountsForTesting(), "Upgrade",
					"UpgradeToYearlyPaidAccount");
			log.info(userCredentials.emailID + "  " + userCredentials.password);
			pageObjectManager.setUserCredentials(userCredentials);

		} catch (Exception e) {
			testResultsStatus.setTestResult("Fail");
			testResultsStatus.setTestResultComment("\n" + e.getMessage());
			e.printStackTrace();
			Assert.assertTrue(false, "Reached exception while getting user details");
		}
		try {
			pageObjectManager.getLoginPage().enterEmail(pageObjectManager.getUserCredentials().emailID);
			pageObjectManager.getLoginPage().enterPasword(pageObjectManager.getUserCredentials().password);
			pageObjectManager.getLoginPage().clickSignIn();
			log.info("Clicked Login button");
			/**
			 * This is the method for the logic for upgrading iDrive360 account to yearly
			 * plan
			 * 
			 */
			logicForUpgradeToYearlyPaidAccount(executionEnvironment);

		} catch (Exception e) {
			e.printStackTrace();
			testResultsStatus.setIsLoginSuccess(false);
			testResultsStatus.setTestResult("Fail");
			testResultsStatus.setTestResultComment("\n" + e.getMessage());
			Assert.assertTrue(false, "Exception occured");
		}
	}

	public TestResultsStatus logicForUpgradeToYearlyPaidAccount(String executionEnvironment) {
		pageObjectManager.getHeaderFunctionalityPage().waitAndClickOnAddUpgradeFromHeader(testResultsStatus);

		pageObjectManager.getAccountPage().waitAndenterBillingInfo(userCredentials, testResultsStatus,
				executionEnvironment);

		pageObjectManager.getAccountPage().waitAndClickUpgrade(testResultsStatus);

		pageObjectManager.getAccountPage().waitAndClickConfirmUpgrade(testResultsStatus);

		pageObjectManager.getAccountPage().waitAndGetSuccessMessage(testResultsStatus);

		try {
			wait.until(ExpectedConditions.visibilityOf(pageObjectManager.getAccountPage().txtNoOfLicenses));
			log.info("Message received>>>" + pageObjectManager.getAccountPage().txtNoOfLicenses.getText());
			if (!pageObjectManager.getAccountPage().txtNoOfLicenses.getText().isBlank()
					|| !pageObjectManager.getAccountPage().txtPlanName.getText().isEmpty()) {
				wait.until((ExpectedCondition<Boolean>) d -> pageObjectManager.getAccountPage().txtNoOfLicenses
						.getText().contains("Monthly"));
				log.info(pageObjectManager.getAccountPage().txtNoOfLicenses.getText());
				testResultsStatus.setTestResult("Pass");
				testResultsStatus.setTestResultComment(
						"Upgrade to the Plan: " + pageObjectManager.getAccountPage().txtNoOfLicenses.getText());
			} else {
				log.info("The Plan was not displayed");
				log.info(pageObjectManager.getAccountPage().txtPlanName.getText());
				testResultsStatus.setTestResult("Fail");
				testResultsStatus.setTestResultComment("The required plan was not displayed on the Account Page ");
				Assert.assertTrue(false, "Failed to display the required plan");
			}
		} catch (Exception e) {
			String errorMessage = "Failed to display the plan: ";
			log.error(errorMessage + e.getMessage());
			e.printStackTrace();
			testResultsStatus.setTestResult("Fail");
			testResultsStatus.setTestResultComment(errorMessage + e.getMessage());
			Assert.assertTrue(false);
		}

		pageObjectManager.getAccountPage().clickProfileTab();

		pageObjectManager.getActivityLogsPage().AssertActivityLogs(getMessage("activityLogs.UpgradeAccount")
				.replace("{companyName}", userCredentials.emailID).replace("[userEmail]", userCredentials.emailAddress),
				testResultsStatus);

		return testResultsStatus;
	}

	@AfterTest
	public void endTest() {
		// Write the test results to the test results workbook and close the browser.
		try {
			localExcelUtility.writeTestResultsStatus(testResultsStatus.getTestName(), testResultsStatus.getTestResult(),
					testResultsStatus.getTestResultComment(), testResultsStatus.getTestExecutionTime().intValue());
		} catch (Exception e) {
			log.error("Reached exception while writing testresults.");
			e.printStackTrace();
			Assert.assertTrue(false, "Reached exception while waiting, post login");
		}
		// Once test is completed, end the session.
		pageObjectManager.getHeaderFunctionalityPage().clickAccountSection();
		pageObjectManager.getHeaderFunctionalityPage().clickLogout();
		wait.until(ExpectedConditions.visibilityOf(pageObjectManager.getLoginPage().txtbxEmail));
		driver.quit();
	}
}
