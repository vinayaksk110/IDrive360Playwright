package scripts.profileActions;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
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

public class ChangePassword extends TestBase {
	UserCredentials userCredentials = null;
	PageObjectManager pageObjectManager = null;
	WebDriver driver = null;
	Wait<WebDriver> wait = null;

	public ChangePassword() {

	}

	public ChangePassword(PageObjectManager pageObjectManager, WebDriver driver, Wait<WebDriver> wait,
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
		testResultsStatus.setTestName(ChangePassword.class.getSimpleName());
	}

	@Test(priority = 1)
	public void runChangePassword() {
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
		logicForChangePassword();

	}

	@Test(priority = 2)
	public void loginAfterChangePassword() {
		logicForVerifyingLoginAfterPasswordChange(userCredentials.emailID, userCredentials.passwordToUpdate);
	}

	public TestResultsStatus logicForChangePassword() {

		pageObjectManager.getAccountPage().navigateToMyAccount(testResultsStatus);
		try {
			wait.until(ExpectedConditions.elementToBeClickable(pageObjectManager.getAccountPage().btnProfileTab));
			wait.until(ExpectedConditions.visibilityOf(pageObjectManager.getAccountPage().inputFirstName));
			Actions action = new Actions(driver);
			wait.until(ExpectedConditions.visibilityOf(pageObjectManager.getAccountPage().inputCurrentPassword));
			action.moveToElement(pageObjectManager.getAccountPage().inputCurrentPassword);
			pageObjectManager.getAccountPage().inputCurrentPassword.click();
			pageObjectManager.getAccountPage().inputCurrentPassword(pageObjectManager.getUserCredentials().password);
			log.info("Current password entered");
			pageObjectManager.getAccountPage().inputNewPassword.click();
			pageObjectManager.getAccountPage()
					.inputNewPassword(pageObjectManager.getUserCredentials().passwordToUpdate);
			log.info("New password entered");
			pageObjectManager.getAccountPage().inputConfirmNewPassword.click();
			pageObjectManager.getAccountPage()
					.inputConfirmNewPassword(pageObjectManager.getUserCredentials().passwordToUpdate);
			log.info("Confirm new password entered");

			pageObjectManager.getAccountPage().clickSaveButton(testResultsStatus);
		} catch (Exception e) {
			e.printStackTrace();
			testResultsStatus.setTestResult("Fail");
			testResultsStatus.setTestResultComment(e.getMessage());
			log.error(e.getMessage());
			Assert.assertTrue(false);
		}

		pageObjectManager.getAccountPage().commonMessage(getMessage("profile.updatedMessage"), testResultsStatus);
		return testResultsStatus;

	}

	public TestResultsStatus logicForVerifyingLoginAfterPasswordChange(String userName, String Password) {

		try {
			pageObjectManager.getHeaderFunctionalityPage().logoutOfApplication();
			Thread.sleep(3000);
			log.info("waited 3 sec");
			wait.until(ExpectedConditions.visibilityOf(pageObjectManager.getLoginPage().btnSignIn));
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			pageObjectManager.getLoginPage().enterEmail(pageObjectManager.getUserCredentials().emailID);
			pageObjectManager.getLoginPage().enterPasword(pageObjectManager.getUserCredentials().passwordToUpdate);
			pageObjectManager.getLoginPage().clickSignIn();
			log.info("Clicked Login button");
		} catch (Exception e) {
			e.printStackTrace();
			testResultsStatus.setIsLoginSuccess(false);
			testResultsStatus.setTestResult("Fail");
			testResultsStatus.setTestResultComment("\n" + e.getMessage());
			Assert.assertTrue(false, "Exception occured");
		}
		try {
			// To confirm if login is successful,
			wait.until(ExpectedConditions.visibilityOf(pageObjectManager.getDevicePage().btnAddDevices));
			testResultsStatus.setIsLoginSuccess(true);
			testResultsStatus.setTestResult("Pass");
			testResultsStatus.setTestResultComment("Logged in successfully with changed password");
		} catch (Exception e) {
			log.error("Login Failed: Waited for element and as element did not appear, timed out");
			// Set the test results to fail with appropriate comments
			testResultsStatus.setIsLoginSuccess(false);
			testResultsStatus.setTestResult("Fail");
			testResultsStatus.setTestResultComment("Add devices button did not appear post login" + e.getMessage());
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
