package scripts.profileActions;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
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

public class UpdateEmailAddress extends TestBase {
	UserCredentials userCredentials = null;
	PageObjectManager pageObjectManager = null;
	WebDriver driver = null;
	Wait<WebDriver> wait = null;

	public UpdateEmailAddress() {

	}

	public UpdateEmailAddress(PageObjectManager pageObjectManager, WebDriver driver, Wait<WebDriver> wait,

			TestResultsStatus testResultsStatus, UserCredentials userCredentials) {
		this.pageObjectManager = pageObjectManager;
		this.driver = driver;
		this.wait = wait;
		this.testResultsStatus = testResultsStatus;
		this.userCredentials = userCredentials;

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

		driver = threadLocalDriver.get();
		wait = threadLocalWait.get();
		pageObjectManager = new PageObjectManager(driver, wait);
		testResultsStatus.setTestName(UpdateEmailAddress.class.getSimpleName());
	}

	@Test(priority = 1)
	public void runUpdateEmailAddress() {
		driver.manage().window().maximize();
		driver.get(Constants.loginURL);

		// Get the required user credentials
		try {
			userCredentials = localExcelUtility.getCompleteSheetData(getWorkbookPath_AccountsForTesting(),
					"ProfileDetails", "UpdateProfileDetails");
			log.error(userCredentials.emailID + "  " + userCredentials.password);
			pageObjectManager.setUserCredentials(userCredentials);

		} catch (Exception e1) {
			e1.printStackTrace();
		}

		pageObjectManager.getLoginPage().loginToAccount(testResultsStatus, userCredentials);
		logicForUpdateEmailAddress(userCredentials.emailID, userCredentials.password);

	}

	@Test(priority = 2, dependsOnMethods = "runUpdateEmailAddress")
	public void cancelEmailUpdate() {
		testResultsStatus.setTestName("CancelEmailUpdate");
		logicForCancelEmailUpdate();
	}

	public TestResultsStatus logicForUpdateEmailAddress(String email, String password) {

		pageObjectManager.getAccountPage().navigateToMyAccount(testResultsStatus);
		try {
			wait.until(ExpectedConditions.elementToBeClickable(pageObjectManager.getAccountPage().btnProfileTab));
			wait.until(
					ExpectedConditions.elementToBeClickable(pageObjectManager.getAccountPage().linkChangeEmailAddress));
			wait.until(ExpectedConditions.visibilityOf(pageObjectManager.getAccountPage().linkChangeEmailAddress));
			pageObjectManager.getAccountPage().linkChangeEmailAddress.click();
			log.info("Clicked on change email address link");
			wait.until(ExpectedConditions.visibilityOf(pageObjectManager.getAccountPage().popupChangeEmailAddress));
			wait.until(ExpectedConditions.visibilityOf(pageObjectManager.getAccountPage().btnChange));
		} catch (Exception e) {
			e.printStackTrace();
			testResultsStatus.setTestResult("Fail");
			testResultsStatus.setTestResultComment(e.getMessage());
			log.error(e.getMessage());
			Assert.assertTrue(false);
		}
		try {
			int index = email.indexOf('@');
			userCredentials.emailAddress = email.substring(0, index) + "_emailUpdate" + email.substring(index);
			log.info(userCredentials.emailAddress);
			pageObjectManager.setUserCredentials(userCredentials);
			log.info("Email to be updated: " + userCredentials.emailAddress);
			pageObjectManager.getAccountPage().inputNewEmailAddress.click();
			pageObjectManager.getAccountPage()
					.inputNewEmailAddress(pageObjectManager.getUserCredentials().emailAddress);
			pageObjectManager.getAccountPage().inputCurrentPasswordChangeEmailAddress.click();
			pageObjectManager.getAccountPage()
					.inputCurrentPasswordChangeEmailAddress(pageObjectManager.getUserCredentials().password);
			pageObjectManager.getAccountPage().btnChange.click();
		} catch (Exception e) {
			pageObjectManager.getAccountPage().btnCloseChangeEmailPopup.click();
			log.error("Closed the change email address pop-up");
			e.printStackTrace();
			testResultsStatus.setTestResult("Fail");
			testResultsStatus.setTestResultComment(e.getMessage());
			log.error(e.getMessage());
			Assert.assertTrue(false);
		}
		String message = null;
		try {
			WebElement success = wait
					.until(ExpectedConditions.visibilityOf(pageObjectManager.getAccountPage().commonSuccessMessage));
			message = success.getText();
			log.info("Success message received from the update email id is: \n " + message);
		} catch (Exception e) {
			e.printStackTrace();
			testResultsStatus.setTestResult("Fail");
			testResultsStatus.setTestResultComment(e.getMessage());
			log.error("Test failed while waiting for the success notification\n" + e.getMessage());
			Assert.assertTrue(false);
		}
		String ExpMessage = "An email has been sent to " + email + ". Complete verification to update email address.\n"
				+ "You may need to check your spam folder or add support@idrive.com and support@send.idrive.com to your contact list to make sure that emails get delivered to your inbox.";
		try {
			Assert.assertEquals(message, ExpMessage);
		} catch (AssertionError e) {
			e.printStackTrace();
			testResultsStatus.setTestResult("Fail");
			testResultsStatus.setTestResultComment("Error while asserting the success message\n" + e.getMessage());
			log.error(testResultsStatus.getTestResultComment());
			Assert.assertTrue(false);
		}
		testResultsStatus.setTestResult("Pass");
		testResultsStatus.setTestResultComment(message
				+ "\n\n For email to be changed, the verification has to be manually completed by logging in to the recipient email id and verification to be completed.");
		return testResultsStatus;

	}

	public TestResultsStatus logicForCancelEmailUpdate() {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(pageObjectManager.getAccountPage().btnProfileTab));
			try {
				wait.until(ExpectedConditions.invisibilityOf(pageObjectManager.getAccountPage().commonSuccessMessage));
				log.info("Waiting for the Update email address success message to disappear");
			} catch (Exception e) {
				e.printStackTrace();
				testResultsStatus.setTestResult("Fail");
				testResultsStatus.setTestResultComment(e.getMessage());
				log.error("wait failed for the invisibility of success message");
				Assert.assertTrue(false);
			}
			wait.until(ExpectedConditions.visibilityOf(pageObjectManager.getAccountPage().linkCancel));
			pageObjectManager.getAccountPage().linkCancel.click();
			log.info("Clicked on  cancel email update link");
			String message = null;
			try {
				WebElement success = wait.until(
						ExpectedConditions.visibilityOf(pageObjectManager.getAccountPage().commonSuccessMessage));
				message = success.getText();
				log.info("Success message received from the cancel email update is: \n " + message);

			} catch (Exception e) {
				e.printStackTrace();
				testResultsStatus.setTestResult("Fail");
				testResultsStatus.setTestResultComment(e.getMessage());
				log.error("Test failed while waiting for the success notification\n" + e.getMessage());
				Assert.assertTrue(false);
			}
			try {
				Assert.assertEquals(message, "Email address change request has been canceled");
			} catch (AssertionError e) {
				e.printStackTrace();
				testResultsStatus.setTestResult("Fail");
				testResultsStatus.setTestResultComment("Error while asserting the success message\n" + e.getMessage());
				log.error(testResultsStatus.getTestResultComment());
				Assert.assertTrue(false);
			}
			testResultsStatus.setTestResult("Pass");
			testResultsStatus.setTestResultComment(message);

		} catch (Exception e) {
			e.printStackTrace();
			testResultsStatus.setTestResult("Fail");
			testResultsStatus.setTestResultComment(
					"Error occured while cancelling the update email id verification\n" + e.getMessage());
			log.error(testResultsStatus.getTestResultComment());
			Assert.assertTrue(false);
		}
		return testResultsStatus;

	}

	@AfterMethod
	public void writeResult() {
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
	}

	@AfterClass
	public void endTest() {
		pageObjectManager.getHeaderFunctionalityPage().logoutOfApplication();
		driver.quit();
	}

}
