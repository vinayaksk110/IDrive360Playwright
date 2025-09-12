package scripts.profileActions;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import edu.emory.mathcs.backport.java.util.concurrent.TimeoutException;
import idrive360.data.UserCredentials;
import idrive360.managers.PageObjectManager;
import idrive360.testbase.Constants;
import idrive360.testbase.TestBase;
import idrive360.utilities.TestResultsStatus;

public class UpdateFirstName extends TestBase {
	UserCredentials userCredentials = null;
	PageObjectManager pageObjectManager = null;
	WebDriver driver = null;
	Wait<WebDriver> wait = null;

	public UpdateFirstName() {

	}

	public UpdateFirstName(PageObjectManager pageObjectManager, WebDriver driver, Wait<WebDriver> wait,
			TestResultsStatus testResultsStatus) {
		this.pageObjectManager = pageObjectManager;
		this.driver = driver;
		this.wait = wait;
		this.testResultsStatus = testResultsStatus;

	}

	@Parameters({ "excelFileStatus", "browser", "headlessMode", "executionEnvironment" })
	@BeforeClass
	public void initializeTest(@Optional("yes") String excelFileStatus, @Optional("chrome") String browser,
			@Optional("no") String headlessMode, @Optional("prodserver") String executionEnvironment)
			throws FileNotFoundException, IOException {
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

		driver = threadLocalDriver.get();
		wait = threadLocalWait.get();
		pageObjectManager = new PageObjectManager(driver, wait);
		testResultsStatus.setTestName(UpdateFirstName.class.getSimpleName());
	}

	@Test
	public void runUpdateFirstName() {
		driver.manage().window().maximize();
		driver.get(Constants.loginURL);

		// Get the required user credentials
		try {
			userCredentials = localExcelUtility.getCompleteSheetData(getWorkbookPath_AccountsForTesting(),
					"ProfileDetails", "UpdateProfileDetails");
			log.info(userCredentials.emailID + "  " + userCredentials.password);
			pageObjectManager.setUserCredentials(userCredentials);

		} catch (Exception e1) {
			e1.printStackTrace();
		}

		pageObjectManager.getLoginPage().loginToAccount(testResultsStatus, userCredentials);
		logicForUpdateFirstName();

	}

	public TestResultsStatus logicForUpdateFirstName() {

		pageObjectManager.getAccountPage().navigateToMyAccount(testResultsStatus);
		try {
			wait.until(ExpectedConditions.elementToBeClickable(pageObjectManager.getAccountPage().btnProfileTab));
			wait.until(ExpectedConditions.visibilityOf(pageObjectManager.getAccountPage().inputFirstName));
			pageObjectManager.getAccountPage().inputFirstName.click();
			pageObjectManager.getAccountPage().inputFirstName.clear();
			pageObjectManager.getAccountPage().inputFirstName(pageObjectManager.getUserCredentials().firstNameToUpdate);
			log.info("Passed first name to be updated");
			pageObjectManager.getAccountPage().clickSaveButton(testResultsStatus);
		} catch (Exception e) {
			e.printStackTrace();
			testResultsStatus.setTestResult("Fail");
			testResultsStatus.setTestResultComment(e.getMessage());
			log.error(e.getMessage());
			Assert.assertTrue(false);
		}
		pageObjectManager.getAccountPage().commonMessage(getMessage("profile.updatedMessage"), testResultsStatus);

		pageObjectManager.getActivityLogsPage().AssertActivityLogs(getMessage("activityLogs.ProfileUpdate")
				.replace("{companyName}", userCredentials.emailID).replace("[userEmail]", userCredentials.emailAddress),
				testResultsStatus);

		return testResultsStatus;
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

}
