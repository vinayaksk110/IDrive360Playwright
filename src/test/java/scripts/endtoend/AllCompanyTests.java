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
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import idrive360.data.UserCredentials;
import idrive360.managers.PageObjectManager;
import idrive360.testbase.Constants;
import idrive360.testbase.TestBase;
import scripts.companies.addCompany.AddCompanyForFirstTimeUsingCenterButton;
import scripts.companies.addCompany.AddSubCompanyBelowExistingCompany;
import scripts.companies.addCompany.HeaderAddCompany;
import scripts.companies.addCompany.OverviewAddCompany;
import scripts.companies.addCompany.SubCompanyInsideExistingCompany;
import scripts.companies.deleteCompany.DeleteCompany;
import scripts.signup.SignUpToIDrive360;

public class AllCompanyTests extends TestBase {

	// Page related objects.;
	UserCredentials userCredentials = null;
	PageObjectManager pageObjectManager = null;
	WebDriver driver = null;
	Wait<WebDriver> wait = null;

	SignUpToIDrive360 signUpToIDrive360 = null;
	AddCompanyForFirstTimeUsingCenterButton addCompanyForFirstTimeUsingCenterButton = null;
	AddSubCompanyBelowExistingCompany addSubCompanyBelowExistingCompany = null;
	HeaderAddCompany headerAddCompany = null;
	OverviewAddCompany overviewAddCompany = null;
	SubCompanyInsideExistingCompany subCompanyInsideExistingCompany = null;
	DeleteCompany deleteCompany = null;

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
//			if (!testResultsStatus.getTestName().equalsIgnoreCase("launchBrowser")) {
//				testResultsStatus.setTestName(testResultsStatus.getTestName());
//				try {
//					wait.until(ExpectedConditions.visibilityOf(pageObjectManager.getLhsOptionsPage().devicesTab));
//					pageObjectManager.getLhsOptionsPage().devicesTab.click();
//					log.info("clicked on devices tab");
//					wait.until(ExpectedConditions.visibilityOf(pageObjectManager.getDevicePage().btnAddDevices));
//					log.info("Add Devices button appeared");
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
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

//		if (testResultsStatus.getIsLoginSuccess()) {
//			pageObjectManager.getHeaderFunctionalityPage().logoutOfApplication();
//		}

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
			userCredentials = localExcelUtility.getCompleteSheetData(getWorkbookPath_AccountsForTesting(), "Signup",
					"CreateFreeTrialAccount");
			// Form the email id with todays date and time to make it unique
			userCredentials.emailID = dateNTime.emailCreationWithDateNTime(userCredentials.emailID);
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

	public void AddCompanyForFirstTimeUsingCenterButton() {

		try {
			addCompanyForFirstTimeUsingCenterButton = new AddCompanyForFirstTimeUsingCenterButton(driver, wait,
					pageObjectManager, testResultsStatus);
			addCompanyForFirstTimeUsingCenterButton.logicForAddCompanyForFirstTimeUsingCenterButton();
		} catch (Exception e) {
			testResultsStatus.setTestResult("fail");
			testResultsStatus.setTestResultComment("Error while creating a company " + e.getMessage());
			Assert.assertTrue(false);
		}
	}

}
