package scripts.backupPlan;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
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
import scripts.upgrade.UpgradeToYearlyPaidAccount;

public class EditBackupPlanOnHover extends TestBase {
	UserCredentials userCredentials = null;
	PageObjectManager pageObjectManager = null;
	WebDriver driver = null;
	Wait<WebDriver> wait = null;

	public EditBackupPlanOnHover() {

	}

	public EditBackupPlanOnHover(PageObjectManager pageObjectManager, WebDriver driver, Wait<WebDriver> wait,
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
		testResultsStatus.setTestName(EditBackupPlanOnHover.class.getSimpleName());
	}

	@AfterTest
	public void endTest() {
		// Write the test results to the test results workbook and close the browser.
		log.info("updating test results from after test");
		log.info(testResultsStatus.getTestResultComment());
		try {
			localExcelUtility.writeTestResultsStatus(testResultsStatus.getTestName(), testResultsStatus.getTestResult(),
					testResultsStatus.getTestResultComment(), testResultsStatus.getTestExecutionTime().intValue());
		} catch (Exception e) {
			log.error("Reached exception while writing testresults.");
			e.printStackTrace();
			Assert.assertTrue(false, "Reached exception while waiting, post login");
		}

		// logout of the application
		log.info("Logging out of the application");
		pageObjectManager.getHeaderFunctionalityPage().logoutOfApplication();
		driver.quit();
	}

	@Test
	public void startTest() {
		driver.get(Constants.loginURL);

		// Get the required user credentials
		try {
			userCredentials = localExcelUtility.getCompleteSheetData(getWorkbookPath_AccountsForTesting(), "BackupPlan",
					"EditBackupPlanOnHover");
			log.info(userCredentials.emailID + "  " + userCredentials.password);
			pageObjectManager.setUserCredentials(userCredentials);

		} catch (Exception e1) {
			e1.printStackTrace();
		}

		pageObjectManager.getLoginPage().loginToAccount(testResultsStatus, userCredentials);
		// Click management section from LHS
		pageObjectManager.getLhsOptionsPage().clickBackupPlan(testResultsStatus);

		logicForPropogateplan();
	}

	public TestResultsStatus logicForPropogateplan() {
		pageObjectManager.getBackupPlanPage().selectBackupPlan(userCredentials.backupPlanName);
		pageObjectManager.getBackupPlanPage().moveToEditPlanandClick(testResultsStatus, userCredentials.backupPlanName);
		log.info("Updating what to backup");
		pageObjectManager.getBackupPlanPage().clickEditSpecifyLinkInSlider(testResultsStatus);
		pageObjectManager.getBackupPlanPage().addRuleWhatToBackup(userCredentials.ruleToBeAdded);
		pageObjectManager.getBackupPlanPage().doneButtonSpecifyRule.click();
		log.info("Rule added to " + userCredentials.ruleToBeAdded);

		// Click Update in slider.
		pageObjectManager.getBackupPlanPage().CreatebuttonInSlider.click();
		log.info("Update backup plan clicked.");

		String successMessage = pageObjectManager.getBackupPlanPage().waitForSuccessMessage();
		String ExpectedMessage = getMessage("backupplans.updateSuccMessage");
		try {
			Assert.assertEquals(successMessage, ExpectedMessage);
			testResultsStatus.setTestResult("Pass");
			testResultsStatus.setTestResultComment(successMessage);
			log.info("Test passed");
		} catch (AssertionError ae) {
			String errorMessage = "Error asserting success message, Expected message is : " + ExpectedMessage
					+ ", but received message is: " + successMessage;
			log.error(errorMessage);
			ae.printStackTrace();
			testResultsStatus.setTestResult("Fail");
			testResultsStatus.setTestResultComment(errorMessage);
			Assert.assertTrue(false);
		}

		pageObjectManager.getActivityLogsPage().AssertActivityLogs(getMessage("activitylogs.BackupPlanUpdated")
				.replace("[BackupPlanName]", userCredentials.backupPlanName), testResultsStatus);

		return testResultsStatus;
	}

}
