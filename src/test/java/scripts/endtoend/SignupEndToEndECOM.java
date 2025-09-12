package scripts.endtoend;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import idrive360.data.UserCredentials;
import idrive360.managers.PageObjectManager;
import idrive360.testbase.Constants;
import idrive360.testbase.TestBase;
import scripts.signup.SignUpToIDrive360;
import scripts.upgrade.UpgradeToYearlyPaidAccount;

@Listeners(idrive360.listeners.TestResultsListeners.class)

public class SignupEndToEndECOM extends TestBase {

	// Page related objects.
	UserCredentials userCredentials = null;
	PageObjectManager pageObjectManager = null;
	WebDriver driver = null;
	Wait<WebDriver> wait = null;
	String executionEnvironment = null;

	// Objects of granular objects
	SignUpToIDrive360 signUpToIDrive360 = null;
	UpgradeToYearlyPaidAccount upgradeToYearlyPaidAccount = null;
	scripts.cancellation.CancellationOfIDrive360Account cancellationOfIDrive360Account = null;

	@Parameters({ "excelFileStatus", "browser", "headlessMode", "executionEnvironment" })
	@BeforeClass
	public void initializeEnvironment(@Optional("yes") String excelFileStatus, @Optional("chrome") String browser,
			@Optional("no") String headlessMode, @Optional("testserver25") String executionEnvironment) {

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
		this.executionEnvironment = executionEnvironment;
	}

	@AfterClass
	public void closeEnvironment() {
		driver.quit();
	}

	@BeforeMethod
	public void getTestName(Method method) {
		testResultsStatus.setTestName(method.getName());
	}

	@AfterMethod
	public void writeTestStatus() {
		if (!testResultsStatus.getTestName().equalsIgnoreCase("launchBrowser")) {
			testResultsStatus.setTestName(testResultsStatus.getTestName());
		}

		try {
			localExcelUtility.writeTestResultsStatus(testResultsStatus.getTestName(), testResultsStatus.getTestResult(),
					testResultsStatus.getTestResultComment(), testResultsStatus.getTestExecutionTime().intValue());
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		if (testResultsStatus.getIsLoginSuccess()) {
			try {
				pageObjectManager.getHeaderFunctionalityPage().logoutOfApplication();
			} catch (Exception e) {
				e.printStackTrace();
				testResultsStatus.setTestName("Logout");
				testResultsStatus.setTestResult("Fail");
				testResultsStatus.setTestResultComment("Logout failed: \n" + e.getMessage());
				log.error("Passing logout URL manually since logout failed");
				driver.get(Constants.IDrive360_Logout);
				wait.until(ExpectedConditions.visibilityOf(pageObjectManager.getLoginPage().btnSignIn));
				log.error("Logged out successfully");
			}
		}

		log.info("====End of " + testResultsStatus.getTestName() + " test====");
	}

	public UserCredentials userCreds(String sheet, String TestName) {
		try {
			userCredentials = localExcelUtility.getCompleteSheetData(getWorkbookPath_AccountsForTesting(), sheet,
					TestName);
			log.info("Got user credentials from the sheet successfully");
			userCredentials.emailID = dateNTime.emailCreationWithDateNTime(userCredentials.emailID);
			pageObjectManager.setUserCredentials(userCredentials);
		} catch (Exception e) {
			testResultsStatus.setTestResult("Fail");
			testResultsStatus.setTestResultComment("\n" + e.getMessage());
			e.printStackTrace();
			Assert.assertTrue(false, "Reached exception while getting user details");
		}
		return userCredentials;
	}

	@Test(priority = 1)
	public void launchBrowser() {
		testResultsStatus.setIsLoginSuccess(false);
		try {
			driver.manage().window().maximize();
			driver.get(Constants.IDrive360URL_SIGNUP);
			wait.until(ExpectedConditions.visibilityOf(pageObjectManager.getSignupPage().btnCreateMyAccount));
			log.info("Signup Page loaded");
			testResultsStatus.setTestResult("Pass");
			testResultsStatus.setTestResultComment("Browser launched and Signup page is loaded successfully");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Error occured while launching browser and loading the Signup URL: " + e.getMessage());
			testResultsStatus.setTestResult("Fail");
			testResultsStatus.setTestResultComment(
					"Exception occured while launching browser and loading the Signup URL: " + e.getMessage());
			Assert.assertTrue(false, "Reached exception while loading loginPage");
		}
	}

	@Test(priority = 2, dependsOnMethods = "launchBrowser")
	public void signupToFreeTrailAccount() {
		// Get the user data for signup
		userCreds("EndToEndECOM", "SignupEndToEndECOM");

		try {
			localExcelUtility.writeToSingleCell(getWorkbookPath_ResultsOfTesting(), getSheetName(), null,
					"Free Trail Signup and Upgrade to the default paid plan \nEmail: " + userCredentials.emailID);
			localExcelUtility.makeRowBold();
		} catch (Exception e) {
			e.printStackTrace();
		}

		// call the signup logic
		try {
			driver.get(Constants.IDrive360URL_SIGNUP);
			wait.until(ExpectedConditions.visibilityOf(pageObjectManager.getSignupPage().btnCreateMyAccount));
			log.info("Signup Page loaded");
			signUpToIDrive360 = new SignUpToIDrive360(driver, wait, pageObjectManager, testResultsStatus);
			signUpToIDrive360.logicForSignUpToIDrive360(userCredentials);
			testResultsStatus.setIsLoginSuccess(false);
		} catch (Exception e) {
			e.printStackTrace();
			testResultsStatus.setTestResult("Fail");
			testResultsStatus.setTestResultComment("Signup Failed: " + e.getMessage());
			Assert.assertTrue(false, "Reached exception while signup for free trail account");
		}
	}

	@Test(priority = 3, dependsOnMethods = "signupToFreeTrailAccount")
	public void upgradeTheAccount() {
		// call the upgrade logic
		try {
			upgradeToYearlyPaidAccount = new UpgradeToYearlyPaidAccount(pageObjectManager, driver, wait,
					testResultsStatus);
			upgradeToYearlyPaidAccount.logicForUpgradeToYearlyPaidAccount(executionEnvironment);
			testResultsStatus.setIsLoginSuccess(false);
		} catch (Exception e) {
			e.printStackTrace();
			testResultsStatus.setTestResult("Fail");
			testResultsStatus.setTestResultComment("Upgrade Failed: \n" + e.getMessage());
			Assert.assertTrue(false, "Reached exception while upgrading the account");
		}
	}

	@Test(priority = 4)
	public void CancellationOfIDrive360Account() {
		// call the cancellation logic
		try {
			cancellationOfIDrive360Account = new scripts.cancellation.CancellationOfIDrive360Account(pageObjectManager,
					driver, wait, testResultsStatus);
			cancellationOfIDrive360Account.logicForCancellationOfIDrive360Account(executionEnvironment);
			testResultsStatus.setIsLoginSuccess(false);
		} catch (Exception e) {
			e.printStackTrace();
			testResultsStatus.setTestResult("Fail");
			testResultsStatus.setTestResultComment("Account cancellation failed \n" + e.getMessage());
			Assert.assertTrue(false);
		}
	}

}
