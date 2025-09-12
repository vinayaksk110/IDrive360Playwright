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
import scripts.profileActions.ChangePassword;
import scripts.profileActions.UpdateCompanyName;
import scripts.profileActions.UpdateEmailAddress;
import scripts.profileActions.UpdateFirstName;
import scripts.profileActions.UpdateLastName;
import scripts.profileActions.UpdatePhoneNumber;
import scripts.signup.SignUpToIDrive360;

@Listeners(idrive360.listeners.TestResultsListeners.class)

public class AllProfileTests extends TestBase {

	// Page related objects.;

	UserCredentials userCredentials = null;
	PageObjectManager pageObjectManager = null;
	WebDriver driver = null;
	Wait<WebDriver> wait = null;

	// Objects of granular objects
	SignUpToIDrive360 signUpToIDrive360 = null;
	UpdateFirstName updateFirstName = null;
	UpdateLastName updateLastName = null;
	UpdatePhoneNumber updatePhoneNumber = null;
	UpdateCompanyName updateCompanyName = null;
	UpdateEmailAddress updateEmailAddress = null;
	ChangePassword changePassword = null;

	String emailToLogin = null;
	String singupPassword = null;
	String updatedPassword = null;

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
		if (!testResultsStatus.getTestName().equalsIgnoreCase("launchBrowser")
				&& !testResultsStatus.getTestName().equalsIgnoreCase("signupToFreeTrailAccount")
				&& !testResultsStatus.getTestName().equalsIgnoreCase("updateFirstName")
				&& !testResultsStatus.getTestName().equalsIgnoreCase("cancelUpdateEmailAddress")
				&& !testResultsStatus.getTestName().equalsIgnoreCase("loginAfterPasswordChange")) {
			testResultsStatus.setTestName(testResultsStatus.getTestName());
			try {
				wait.until(ExpectedConditions.visibilityOf(pageObjectManager.getLhsOptionsPage().devicesTab));
				pageObjectManager.getLhsOptionsPage().devicesTab.click();
				log.info("clicked on devices tab");
				wait.until(ExpectedConditions.visibilityOf(pageObjectManager.getDevicePage().btnAddDevices));
				log.info("Add Devices button appeared");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// clicking on devices tab in order to refresh the account page.

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
			pageObjectManager.getHeaderFunctionalityPage().logoutOfApplication();
		}

		log.info("====End of " + testResultsStatus.getTestName() + " test====");
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

	@Test(priority = 2)
	public void signupToFreeTrailAccount() {
		// Get the user data for signup
		try {
			userCredentials = localExcelUtility.getCompleteSheetData(getWorkbookPath_AccountsForTesting(),
					"EndToEndECOM", "SignupEndToEndECOM");
			// Form the email id with todays date and time to make it unique
			userCredentials.emailID = dateNTime.emailCreationWithDateNTime(userCredentials.emailID);
			emailToLogin = userCredentials.emailID;
			singupPassword = userCredentials.password;
			updatedPassword = userCredentials.passwordToUpdate;
			pageObjectManager.setUserCredentials(userCredentials);
		} catch (Exception e) {
			testResultsStatus.setTestResult("Fail");
			testResultsStatus.setTestResultComment("Signup Failed");
			e.printStackTrace();
			Assert.assertTrue(false, "Reached exception while getting user details");
		}

		// call the signup logic
		try {
			signUpToIDrive360 = new SignUpToIDrive360(driver, wait, pageObjectManager, testResultsStatus);
			signUpToIDrive360.logicForSignUpToIDrive360(userCredentials);
			testResultsStatus.setIsLoginSuccess(false);
		} catch (Exception e) {
			e.printStackTrace();
			testResultsStatus.setTestResult("Fail");
			testResultsStatus.setTestResultComment("Signup Failed: " + e.getMessage());
			Assert.assertTrue(false, "Reached exception while signup for free trail account");
		}

		try {
			localExcelUtility.writeToCellInAccountsSheet("Login", "Email", "AccountWithFreeTrial",
					userCredentials.emailID);
			log.info("Updated the email ID in Accounts Sheet");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test(priority = 3)
	public void updateFirstName() {
		try {
			userCredentials = localExcelUtility.getCompleteSheetData(getWorkbookPath_AccountsForTesting(),
					"ProfileDetails", "UpdateProfileDetails");
			pageObjectManager.setUserCredentials(userCredentials);
		} catch (Exception e) {
			testResultsStatus.setTestResult("Fail");
			testResultsStatus.setTestResultComment("Signup Failed");
			e.printStackTrace();
			Assert.assertTrue(false, "Reached exception while getting user details");
		}
		try {
			updateFirstName = new UpdateFirstName(pageObjectManager, driver, wait, testResultsStatus);
			updateFirstName.logicForUpdateFirstName();
			testResultsStatus.setIsLoginSuccess(false);

		} catch (Exception e) {
			e.printStackTrace();
			testResultsStatus.setTestResult("Fail");
			testResultsStatus.setTestResultComment("update first name Failed: " + e.getMessage());
			Assert.assertTrue(false, "Reached exception while updating the first name");
		}

	}

	@Test(priority = 4)
	public void updateLastName() {
		try {
			updateLastName = new UpdateLastName(pageObjectManager, driver, wait, testResultsStatus);
			updateLastName.logicForUpdateLastName();
			testResultsStatus.setIsLoginSuccess(false);

		} catch (Exception e) {
			e.printStackTrace();
			testResultsStatus.setTestResult("Fail");
			testResultsStatus.setTestResultComment("update last name Failed: " + e.getMessage());
			Assert.assertTrue(false, "Reached exception while updating the last name");
		}

	}

	@Test(priority = 5)
	public void updateCompanyName() {
		try {
			updateCompanyName = new UpdateCompanyName(pageObjectManager, driver, wait, testResultsStatus);
			updateCompanyName.logicForUpdateCompanyName();
			testResultsStatus.setIsLoginSuccess(false);
		} catch (Exception e) {
			testResultsStatus.setTestResult("Fail");
			testResultsStatus.setTestResultComment("update company name Failed: " + e.getMessage());
			Assert.assertTrue(false, "Reached exception while updating the company name");
		}
	}

	@Test(priority = 6)
	public void updatePhoneNumber() {
		try {
			updatePhoneNumber = new UpdatePhoneNumber(pageObjectManager, driver, wait, testResultsStatus);
			updatePhoneNumber.logicForUpdatePhoneNumber();
			testResultsStatus.setIsLoginSuccess(false);
		} catch (Exception e) {
			testResultsStatus.setTestResult("Fail");
			testResultsStatus.setTestResultComment("update phone number Failed: " + e.getMessage());
			Assert.assertTrue(false, "Reached exception while updating the phone number");
		}

	}

	@Test(priority = 7)
	public void updateEmailAddress() {
		try {
			updateEmailAddress = new UpdateEmailAddress(pageObjectManager, driver, wait, testResultsStatus,
					userCredentials);
			updateEmailAddress.logicForUpdateEmailAddress(emailToLogin, singupPassword);
			testResultsStatus.setIsLoginSuccess(false);
		} catch (Exception e) {
			testResultsStatus.setTestResult("Fail");
			testResultsStatus.setTestResultComment("update email address Failed: " + e.getMessage());
			Assert.assertTrue(false, "Reached exception while updating the email address");

		}
	}

	@Test(priority = 8)
	public void cancelUpdateEmailAddress() {
		try {
			updateEmailAddress = new UpdateEmailAddress(pageObjectManager, driver, wait, testResultsStatus,
					userCredentials);
			updateEmailAddress.logicForCancelEmailUpdate();
			testResultsStatus.setIsLoginSuccess(false);
		} catch (Exception e) {
			testResultsStatus.setTestResult("fail");
			testResultsStatus.setTestResultComment("cancel update email address Failed: " + e.getMessage());
			Assert.assertTrue(false, "Reached exception while canceling update email address");
		}
	}

	@Test(priority = 9)
	public void changePassword() {
		try {
			changePassword = new ChangePassword(pageObjectManager, driver, wait, testResultsStatus);
			changePassword.logicForChangePassword();
			testResultsStatus.setIsLoginSuccess(false);
		} catch (Exception e) {
			testResultsStatus.setTestResult("fail");
			testResultsStatus.setTestResultComment("change password Failed: " + e.getMessage());
			Assert.assertTrue(false, "Reached exception while changing the password");
		}
	}

	@Test(priority = 10)
	public void loginAfterPasswordChange() {
		try {
			changePassword = new ChangePassword(pageObjectManager, driver, wait, testResultsStatus);
			changePassword.logicForVerifyingLoginAfterPasswordChange(emailToLogin, updatedPassword);
			testResultsStatus.setIsLoginSuccess(true);
		} catch (Exception e) {
			testResultsStatus.setTestResult("fail");
			testResultsStatus.setTestResultComment("change password Failed: " + e.getMessage());
			Assert.assertTrue(false, "Reached exception while changing the password");
		}

	}
}
