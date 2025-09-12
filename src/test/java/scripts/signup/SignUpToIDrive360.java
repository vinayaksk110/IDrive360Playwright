package scripts.signup;

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

public class SignUpToIDrive360 extends TestBase {

	UserCredentials userCredentials = null;
	PageObjectManager pageObjectManager = null;
	WebDriver driver = null;
	Wait<WebDriver> wait = null;

	public SignUpToIDrive360() {

	}

	public SignUpToIDrive360(WebDriver driver, Wait<WebDriver> wait, PageObjectManager pageObjectManager,
			idrive360.utilities.TestResultsStatus testResultsStatus) {
		this.driver = driver;
		this.wait = wait;
		this.pageObjectManager = pageObjectManager;
		this.testResultsStatus = testResultsStatus;
	}

	@BeforeClass
	@Parameters({ "excelFileStatus", "browser", "headlessMode", "executionEnvironment" })
	public void initializeEnvironment(@Optional("yes") String excelFileStatus, @Optional("chrome") String browser,
			@Optional("no") String headlessMode, @Optional("testserver25") String executionEnvironment) {
		try {
			initializeTestingEnvironment(excelFileStatus, browser, headlessMode, executionEnvironment);
		} catch (Exception e) {
			e.printStackTrace();
		}
		driver = threadLocalDriver.get();
		wait = threadLocalWait.get();
		pageObjectManager = new PageObjectManager(driver, wait);

		testResultsStatus.setTestName(SignUpToIDrive360.class.getSimpleName());
	}

	@Test
	@Parameters({ "executionEnvironment" })
	public void startSignup() {
		driver.manage().window().maximize();
		driver.get(Constants.IDrive360URL_SIGNUP);

		try {

			userCredentials = localExcelUtility.getCompleteSheetData(getWorkbookPath_AccountsForTesting(), "Signup",
					"SignupForFreeTrialAccount");
			// Form the email id with todays date and time to make it unique
			userCredentials.emailID = dateNTime.emailCreationWithDateNTime(userCredentials.emailID);
			pageObjectManager.setUserCredentials(userCredentials);
		} catch (Exception e) {
			testResultsStatus.setTestResult("Fail");
			testResultsStatus.setTestResultComment("Signup Failed");
			e.printStackTrace();

			Assert.assertTrue(false, "Reached exception while getting user details");
		}

		logicForSignUpToIDrive360(userCredentials);

	}

	public TestResultsStatus logicForSignUpToIDrive360(UserCredentials userCredentials) {
		wait.until(ExpectedConditions.visibilityOf(pageObjectManager.getSignupPage().btnCreateMyAccount));
		log.info("Signup Page loaded");

		pageObjectManager.getSignupPage().enterSignupData(userCredentials, testResultsStatus);

		pageObjectManager.getSignupPage().waitAndClickSignupButton(testResultsStatus);

		try {
			// To confirm if signup is successful,
			// Wait for the Welcome to Idrive 360 header message at the center post signup

			wait.until(ExpectedConditions.visibilityOf(pageObjectManager.getSignupPage().welcomeIDrive360header));
			String msg = pageObjectManager.getSignupPage().welcomeIDrive360header.getText();
			log.info(msg);
			if (!msg.contains(getMessage("signup.signuptoidrive360account"))) {
				Assert.assertEquals(getMessage("signup.signuptoidrive360account"), msg);
			}
			testResultsStatus.setIsLoginSuccess(true);
			testResultsStatus.setTestResult("Pass");
			testResultsStatus.setTestResultComment("Welcome to IDrive ®360! header apperared post signup");
			testResultsStatus.setTestEndTime(System.currentTimeMillis());
			testResultsStatus.setTestExecutionTime(testResultsStatus.getTestEndTime(),
					testResultsStatus.getTestStartTime());
		} catch (Exception e) {
			log.error("Signup Failed: Waited for element and as element did not appear, timed out");
			// Set the test results to fail with appropriate comments
			testResultsStatus.setTestResult("Fail");
			testResultsStatus
					.setTestResultComment("Welcome to IDrive ®360! header did not appear post signup" + e.getMessage());
			log.error(testResultsStatus.getTestResultComment());
			testResultsStatus.setTestEndTime(System.currentTimeMillis());
			testResultsStatus.setTestExecutionTime(testResultsStatus.getTestEndTime(),
					testResultsStatus.getTestStartTime());
			Assert.assertTrue(false, "Exception occured");
		} catch (AssertionError ae) {
			log.error("Assertion error appeared");
			testResultsStatus.setTestResult("Fail");
			testResultsStatus.setTestResultComment("Reached assertion exception: " + ae.getMessage());
			log.error(testResultsStatus.getTestResultComment());
			testResultsStatus.setTestEndTime(System.currentTimeMillis());
			testResultsStatus.setTestExecutionTime(testResultsStatus.getTestEndTime(),
					testResultsStatus.getTestStartTime());
			// Once test is completed, end the session.
			pageObjectManager.getHeaderFunctionalityPage().clickAccountSection();
			pageObjectManager.getHeaderFunctionalityPage().clickLogout();
			wait.until(ExpectedConditions.visibilityOf(pageObjectManager.getLoginPage().txtbxEmail));
			Assert.assertTrue(false, "Reached Assertion exception");
		}

		pageObjectManager.getActivityLogsPage().AssertActivityLogs(
				getMessage("activitylogs.AccountCreation").replace("[userEmail]", userCredentials.emailID),
				testResultsStatus);
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

		// Once test is completed, end the session.
		pageObjectManager.getHeaderFunctionalityPage().logoutOfApplication();
		driver.quit();
	}
}
