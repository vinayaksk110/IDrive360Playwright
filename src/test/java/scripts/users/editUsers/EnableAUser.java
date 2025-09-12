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

public class EnableAUser extends TestBase {
	UserCredentials userCredentials = null;
	PageObjectManager pageObjectManager = null;
	WebDriver driver = null;
	Wait<WebDriver> wait = null;

	public EnableAUser() {

	}

	public EnableAUser(PageObjectManager pageObjectManager, WebDriver driver, Wait<WebDriver> wait,
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
		testResultsStatus.setTestName(EnableAUser.class.getSimpleName());
	}

	@Test
	public void startTest() {
		driver.get(Constants.loginURL);

		// Get the required user credentials
		try {
			userCredentials = localExcelUtility.getCompleteSheetData(getWorkbookPath_AccountsForTesting(), "Users",
					"EnableAUser");
			log.info(userCredentials.emailID + "  " + userCredentials.password);
			pageObjectManager.setUserCredentials(userCredentials);

		} catch (Exception e1) {
			e1.printStackTrace();
		}

		pageObjectManager.getLoginPage().loginToAccount(testResultsStatus, userCredentials);
		// Click management section from LHS
		pageObjectManager.getLhsOptionsPage().waitForManagementSectionAndClick(testResultsStatus);
		logicForDisableAUser(pageObjectManager.getUserCredentials().userToBeEnabled);

	}

	public TestResultsStatus logicForDisableAUser(String userEmail) {
		userEmail = dateNTime.emailCreationWithDate(userEmail);
		try {

			// Click on users from LHS
			pageObjectManager.getLhsOptionsPage().waitForUsersSectionVisibilityAndClick(testResultsStatus);

			// Check user status
			try {
				log.info("User email considered for enabling is : " + userEmail);
				if (pageObjectManager.getUsersPage().VerifyUserStatus(userEmail).equals("Disabled")) {
					log.info("User status is Disabled hence continuing with Enable flow");
				} else {
					String message = "User status is not disabled hence quitting test";
					testResultsStatus.setTestResult("Fail");
					testResultsStatus.setTestResultComment(message);
					log.error(message);
					Assert.assertTrue(false);
				}
			} catch (Exception e) {
				e.printStackTrace();
				testResultsStatus.setTestResult("Fail");
				testResultsStatus.setTestResultComment(e.getMessage());
				log.error(e.getMessage());
				log.error("error while getting user status");
				Assert.assertTrue(false);
			}
			// navigate to user to be enabled
			try {
				pageObjectManager.getUsersPage().navigateToUser(userEmail);
				log.info("Found the user to be enabled");
			} catch (Exception e) {
				String errorMessage = "Error while navigating to user\n";
				log.error(errorMessage);
				e.printStackTrace();
				testResultsStatus.setTestResult("Fail");
				testResultsStatus.setTestResultComment(errorMessage + e.getMessage());
				Assert.assertTrue(false);
			}
			// Move to the user to enabled and perform Enable action
			try {
				pageObjectManager.getUsersPage().navigateToEnableIcon(userEmail);
			} catch (Exception e) {
				e.printStackTrace();
				testResultsStatus.setTestResult("Fail");
				testResultsStatus.setTestResultComment(e.getMessage());
				log.error(e.getMessage());
				log.error("Error while navigating to the Enable icon");
				Assert.assertTrue(false);
			}

			// Enable user
			try {
				pageObjectManager.getUsersPage().enableUser(userEmail);
			} catch (Exception e) {
				e.printStackTrace();
				testResultsStatus.setTestResult("Fail");
				testResultsStatus.setTestResultComment(e.getMessage());
				log.error(e.getMessage());
				log.error("Error while enabling the user");
				Assert.assertTrue(false);
			}
			// success message
			String message = pageObjectManager.getUsersPage().waitForMessage();
			try {
				Assert.assertEquals(message, "The user " + userEmail + " has been re-enabled");
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
				getMessage("activityLogs.Userenabled").replace("{companyName}", userCredentials.companyName)
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
