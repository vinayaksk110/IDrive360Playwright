/**
 * Author : Vinayak
 * This is a template class to identify how to use the page factory methods along with the scripts.
 */

package scripts.users.addUsers;

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

public class AddUserFromHeaderInOverview extends TestBase {
	UserCredentials userCredentials = null;
	PageObjectManager pageObjectManager = null;
	WebDriver driver = null;
	Wait<WebDriver> wait = null;

	public AddUserFromHeaderInOverview() {

	}

	public AddUserFromHeaderInOverview(PageObjectManager pageObjectManager, WebDriver driver, Wait<WebDriver> wait,
			TestResultsStatus testResultsStatus) {
		this.pageObjectManager = pageObjectManager;
		this.driver = driver;
		this.wait = wait;
		this.testResultsStatus = testResultsStatus;
	}

	@Parameters({ "excelFileStatus", "browser", "headlessMode", "executionEnvironment" })
	@BeforeClass
	public void initializeTest(@Optional("yes") String excelFileStatus, @Optional("chrome") String browser,
			@Optional("no") String headlessMode, @Optional("testserver25") String executionEnvironment)
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

		testResultsStatus.setTestName(AddUserFromHeaderInOverview.class.getSimpleName());
		driver = threadLocalDriver.get();
		wait = threadLocalWait.get();
		pageObjectManager = new PageObjectManager(driver, wait);
	}

	@Test
	public void startTest() {
		driver.get(Constants.loginURL);

		// Get the required user credentials
		try {
			userCredentials = localExcelUtility.getCompleteSheetData(getWorkbookPath_AccountsForTesting(), "Users",
					"AddUserFromHeaderInOverview");
			log.info(userCredentials.emailID + "  " + userCredentials.password);
			pageObjectManager.setUserCredentials(userCredentials);
			log.info(pageObjectManager.getUserCredentials());

		} catch (Exception e1) {
			e1.printStackTrace();
		}

		pageObjectManager.getLoginPage().loginToAccount(testResultsStatus, userCredentials);
		pageObjectManager.getLhsOptionsPage().waitForManagementSectionAndClick(testResultsStatus);
		logicForAddUnitAdmUserInDefaultUnitBelowCompanyAdm();
	}

	public TestResultsStatus logicForAddUnitAdmUserInDefaultUnitBelowCompanyAdm() {
		userCredentials.subUserEmail = dateNTime.emailCreationWithDate(userCredentials.subUserEmail);
		try {

			pageObjectManager.getHeaderFunctionalityPage().waitAndClickAddButtonFromHeader(testResultsStatus);
			try {
				pageObjectManager.getHeaderFunctionalityPage().waitAndClickOnAddUserFromHeaderUnderAddButton();
			} catch (Exception e) {
				String errorMessage = "Error while clicking on Add user button from header in management console\n";
				log.error(errorMessage);
				e.printStackTrace();
				testResultsStatus.setTestResult("Fail");
				testResultsStatus.setTestResultComment(errorMessage + e.getMessage());
				Assert.assertTrue(false);
			}
			pageObjectManager.getUsersPage().enterEmailIds(userCredentials.subUserEmail, testResultsStatus);

			wait.until(ExpectedConditions.visibilityOf(pageObjectManager.getUsersPage().byDefaultUserInDropdown));
			wait.until(ExpectedConditions
					.visibilityOf(pageObjectManager.getUsersPage().byDefaultCompanyAdmInRolesChecked));
			pageObjectManager.getUsersPage().clickCreateUserButton(testResultsStatus);

			String message = pageObjectManager.getUsersPage().waitForMessage();
			log.info("Message from toast message is >>>> " + message);
			try {
				Assert.assertEquals(message, "User(s) have been added");
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
				getMessage("activityLogs.UserInvited").replace("{companyName}", userCredentials.companyName)
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