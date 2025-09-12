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
import scripts.login.LoginToAccountWithDevices;
import scripts.login.LoginToAccountWithoutDevices;
import scripts.login.LoginToBackpSubUser;
import scripts.login.LoginToCompanyAdminSubUser;
import scripts.login.LoginToFreeTrialAccount;

@Listeners(idrive360.listeners.TestResultsListeners.class)

public class AllLoginTests extends TestBase {

	// Page related objects.;
	UserCredentials userCredentials = null;
	PageObjectManager pageObjectManager = null;
	WebDriver driver = null;
	Wait<WebDriver> wait = null;

	// Objects of granular objects
	LoginToAccountWithDevices loginToAccountWithDevices = null;
	LoginToAccountWithoutDevices loginToAccountWithoutDevices = null;
	LoginToFreeTrialAccount loginToFreeTrialAccount = null;
	LoginToCompanyAdminSubUser loginToCompanyAdminSubUser = null;
	LoginToBackpSubUser loginToBackpSubUser = null;

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
	}

	@AfterClass
	public void closeEnvironment() {
		driver.quit();
	}

	@BeforeMethod
	public void getTestName(Method method) {
		testResultsStatus.setTestName(method.getName());
		if (!testResultsStatus.getTestName().equals("launchBrowser")) {
			log.info("Waiting for email address text field in Login page");
			wait.until(ExpectedConditions.visibilityOf(pageObjectManager.getLoginPage().txtbxEmail));
		}
	}

	@AfterMethod
	public void writeTestStatus() {
		if (testResultsStatus.getIsLoginSuccess()) {
			pageObjectManager.getHeaderFunctionalityPage().logoutOfApplication();
		}

		log.info("====End of " + testResultsStatus.getTestName() + " test====");
		log.info(System.lineSeparator());
		log.info("===========================================================");
		log.info(testResultsStatus.getTestName());
		log.info(testResultsStatus.getTestResult());
		log.info(testResultsStatus.getTestResultComment());
		log.info("===========================================================");
		
		if (!testResultsStatus.getTestName().equalsIgnoreCase("launchBrowser")) {
			testResultsStatus.setTestName(
					testResultsStatus.getTestName() + "\nEmail ID: " + pageObjectManager.getUserCredentials().emailID);
			try {
				localExcelUtility.writeTestResultsStatus(testResultsStatus.getTestName(),
						testResultsStatus.getTestResult(), testResultsStatus.getTestResultComment(),
						testResultsStatus.getTestExecutionTime().intValue());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	public UserCredentials userCreds(String sheet, String TestName) {
		try {
			userCredentials = localExcelUtility.getCompleteSheetData(getWorkbookPath_AccountsForTesting(), sheet,
					TestName);
			log.info("Got user credentials from the sheet successfully");
			pageObjectManager.setUserCredentials(userCredentials);
		} catch (Exception e) {
			testResultsStatus.setTestResult("Fail");
			testResultsStatus.setTestResultComment("\n" + e.getMessage());
			e.printStackTrace();
			Assert.assertTrue(false, "Reached exception while getting user details");
		}
		return userCredentials;
	}

	@Test(priority = 0)
	public void launchBrowser() {
		testResultsStatus.setIsLoginSuccess(false);
		try {
			driver.get(Constants.loginURL);

			wait.until(ExpectedConditions.visibilityOf(pageObjectManager.getLoginPage().btnSignIn));
			log.info("Login Page loaded");
			log.info("Launched browser with URL: " + Constants.loginURL);
		} catch (Exception e) {

			log.error("Error occured while launching browser and loading the URL: " + e.getMessage());
			e.printStackTrace();
			Assert.assertTrue(false, "Reached exception while loading loginPage");
		}
		testResultsStatus.setTestResult("Pass");
		testResultsStatus.setTestResultComment("Browser launched successfully");

	}

	@Test(priority = 1, dependsOnMethods = "launchBrowser")
	public void logintoAccountWithDevices() {

		// Get the required credentials for logging to the account
		userCreds("Login", "accountwithdevices");

		try {
			// Enter the credentials and login to the account
			pageObjectManager.getLoginPage().loginToAccount(testResultsStatus, userCredentials);

			// Create the object and call the logic
			loginToAccountWithDevices = new LoginToAccountWithDevices(pageObjectManager, driver, wait,
					testResultsStatus);
			loginToAccountWithDevices.logicForLoginToAccountWithDevices();

		} catch (Exception e2) {
			log.error("Error while entering login credentials");
			e2.printStackTrace();
			testResultsStatus.setTestResult("Fail");
			testResultsStatus.setTestResultComment("Error while entering login credentials \n" + e2.getMessage());
			Assert.assertTrue(false, "Error while entering login credentials");
		}
	}

	@Test(priority = 2, dependsOnMethods = "launchBrowser")
	public void logintoAccountWithoutDevices() {
		// Get the required credentials for logging to the account
		userCreds("Login", "accountwithoutdevices");

		try {
			// Enter the credentials and login to the account
			pageObjectManager.getLoginPage().loginToAccount(testResultsStatus, userCredentials);

			// Create the object and call the logic
			loginToAccountWithoutDevices = new LoginToAccountWithoutDevices(pageObjectManager, driver, wait,
					testResultsStatus);
			loginToAccountWithoutDevices.logicForLoginToAccountWithoutDevices();

		} catch (Exception e2) {
			log.error("Error while entering login credentials");
			e2.printStackTrace();
			testResultsStatus.setTestResult("Fail");
			testResultsStatus.setTestResultComment("Error while entering login credentials \n" + e2.getMessage());
			Assert.assertTrue(false, "Error while entering login credentials");
		}
	}

	@Test(priority = 3, dependsOnMethods = "launchBrowser")
	public void loginToFreeTrialAccount() {
		// Get the required credentials for logging to the account
		userCreds("Login", "accountwithfreetrial");

		try {
			// Enter the credentials and login to the account
			pageObjectManager.getLoginPage().loginToAccount(testResultsStatus, userCredentials);

			// Create the object and call the logic
			loginToFreeTrialAccount = new LoginToFreeTrialAccount(pageObjectManager, driver, wait, testResultsStatus);
			loginToFreeTrialAccount.logicForLoginToFreeTrialAccount();
		} catch (Exception e2) {
			log.error("Error while entering login credentials");
			e2.printStackTrace();
			testResultsStatus.setTestResult("Fail");
			testResultsStatus.setTestResultComment("Error while entering login credentials \n" + e2.getMessage());
			Assert.assertTrue(false, "Error while entering login credentials");
		}
	}

	@Test(priority = 4, dependsOnMethods = "launchBrowser")
	public void LoginToCompanyAdminSubUser() {
		// Get the required credentials for logging to the account
		userCreds("Login", "accountwithcompanyadmin");

		try {
			// Enter the credentials and login to the account
			pageObjectManager.getLoginPage().loginToAccount(testResultsStatus, userCredentials);

			// Create the object and call the logic
			loginToCompanyAdminSubUser = new LoginToCompanyAdminSubUser(pageObjectManager, driver, wait,
					testResultsStatus);
			loginToCompanyAdminSubUser.logicForLoginToCompanyAdminSubUser();

		} catch (Exception e2) {
			log.error("Error while entering login credentials");
			e2.printStackTrace();
			testResultsStatus.setTestResult("Fail");
			testResultsStatus.setTestResultComment("Error while entering login credentials \n" + e2.getMessage());
			Assert.assertTrue(false, "Error while entering login credentials");
		}
	}

	@Test(priority = 5, dependsOnMethods = "launchBrowser")
	public void LoginToBackpSubUser() {
		// Get the required credentials for logging to the account
		userCreds("Login", "accountwithbackupuser");

		try {
			// Enter the credentials and login to the account
			pageObjectManager.getLoginPage().loginToAccount(testResultsStatus, userCredentials);

			// Create the object and call the logic
			loginToBackpSubUser = new LoginToBackpSubUser(pageObjectManager, driver, wait, testResultsStatus);
			loginToBackpSubUser.logicForLoginToBackpSubUser();
		} catch (Exception e2) {
			log.error("Error while entering login credentials");
			e2.printStackTrace();
			testResultsStatus.setTestResult("Fail");
			testResultsStatus.setTestResultComment("Error while entering login credentials \n" + e2.getMessage());
			Assert.assertTrue(false, "Error while entering login credentials");
		}

	}
}
