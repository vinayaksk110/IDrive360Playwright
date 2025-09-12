package scripts.users.editUsers;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.openqa.selenium.WebDriver;
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

public class UpdateFirstNameFromGlobalEdit extends TestBase {

	UserCredentials userCredentials = null;
	PageObjectManager pageObjectManager = null;
	WebDriver driver = null;
	Wait<WebDriver> wait = null;

	public UpdateFirstNameFromGlobalEdit() {

	}

	public UpdateFirstNameFromGlobalEdit(PageObjectManager pageObjectManager, WebDriver driver, Wait<WebDriver> wait,
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
		testResultsStatus.setTestName(UpdateFirstNameFromGlobalEdit.class.getSimpleName());
	}

	@Test
	public void startTest() {
		driver.get(Constants.loginURL);

		// Get the required user credentials
		try {
			userCredentials = localExcelUtility.getCompleteSheetData(getWorkbookPath_AccountsForTesting(), "Users",
					"UpdateFirstNameFromGlobalEdit");
			log.info(userCredentials.emailID + "  " + userCredentials.password);
			pageObjectManager.setUserCredentials(userCredentials);

		} catch (Exception e1) {
			e1.printStackTrace();
		}

		pageObjectManager.getLoginPage().loginToAccount(testResultsStatus, userCredentials);
		// Click management section from LHS
		pageObjectManager.getLhsOptionsPage().waitForManagementSectionAndClick(testResultsStatus);

		logicForUpdateFirstNameFromGlobalEdit(pageObjectManager.getUserCredentials().userToBeEdited);

	}

	public TestResultsStatus logicForUpdateFirstNameFromGlobalEdit(String userEmail) {
		userEmail = dateNTime.emailCreationWithDate(userEmail);
		try {

			// Click on users from LHS
			pageObjectManager.getLhsOptionsPage().waitForUsersSectionVisibilityAndClick(testResultsStatus);

			// wait for the bottom add user button
			try {
				pageObjectManager.getUsersPage().waitForBottomAddUserButton();
			} catch (Exception e) {
				e.printStackTrace();
				testResultsStatus.setTestResult("Fail");
				testResultsStatus.setTestResultComment(e.getMessage());
				log.error(e.getMessage());
				log.error("error in the wait for bottom add user button");
				Assert.assertTrue(false);
			}

			// navigate to user to be updated with first name
			try {
				pageObjectManager.getUsersPage().navigateToUser(userEmail);
				log.info("Found the user to be updated with first name");
			} catch (Exception e) {
				String errorMessage = "Error while navigating to user\n";
				log.error(errorMessage);
				e.printStackTrace();
				testResultsStatus.setTestResult("Fail");
				testResultsStatus.setTestResultComment(errorMessage + e.getMessage());
				Assert.assertTrue(false);
			}
			// Move to the user to be updated with first name and perform view icon action
			pageObjectManager.getUsersPage().navigateToViewIcon(userEmail, testResultsStatus);

			// Click on Edit & Update first name
			try {
				pageObjectManager.getUsersPage().clickOnGlobalEditIcon(userEmail, testResultsStatus);
				pageObjectManager.getUsersPage().updateFirstName(userCredentials.firstName);
			} catch (Exception e) {
				e.printStackTrace();
				testResultsStatus.setTestResult("Fail");
				testResultsStatus.setTestResultComment(e.getMessage());
				log.error(e.getMessage());
				log.error("Error while updating the first name");
				Assert.assertTrue(false);
			}
			// success message
			String message = pageObjectManager.getUsersPage().waitForMessageInViewSlider();
			try {
				Assert.assertEquals(message, "User details have been updated");
				testResultsStatus.setTestResultComment("Toast message received is : " + message);
				testResultsStatus.setTestResult("Pass");
			} catch (AssertionError ae) {
				String errorMessage = "Error while asserting toast message for add user\n";
				log.error(errorMessage);
				ae.printStackTrace();
				testResultsStatus.setTestResult("Fail");
				testResultsStatus.setTestResultComment(errorMessage + ae.getMessage());
				Assert.assertTrue(false);
			}
		} catch (Exception e) {
			testResultsStatus.setTestResult("Fail");
			testResultsStatus.setTestResultComment(e.getMessage());
			log.error(e.getMessage());
		}

		pageObjectManager.getActivityLogsPage().AssertActivityLogs(
				getMessage("activityLogs.UserUpdated").replace("{companyName}", userCredentials.companyName)
						.replace("[userEmail]", userCredentials.emailAddress),
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
