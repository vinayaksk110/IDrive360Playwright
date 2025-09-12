package scripts.endtoend;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
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
import scripts.companies.addCompany.AddSubCompanyBelowExistingCompany;
import scripts.companies.addCompany.HeaderAddCompany;
import scripts.companies.deleteCompany.DeleteCompany;
import scripts.users.addUsers.AddUserFromHeaderInOverview;

public class SanityTestUsers extends TestBase {
	
	// Page related objects.;
		UserCredentials userCredentials = null;
		PageObjectManager pageObjectManager = null;
		WebDriver driver = null;
		Wait<WebDriver> wait = null;

		AddUserFromHeaderInOverview addUserFromHeaderInOverview = null;
		HeaderAddCompany headerAddCompany = null;
		AddSubCompanyBelowExistingCompany addSubCompanyBelowExistingCompany = null;
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
//			log.info("===Starting a new test"+testResultsStatus.getTestName()+" ====");
//				if (!testResultsStatus.getTestName().equalsIgnoreCase("launchBrowser")) {
//					testResultsStatus.setTestName(testResultsStatus.getTestName());
//					try {
//						wait.until(ExpectedConditions.visibilityOf(pageObjectManager.getLhsOptionsPage().devicesTab));
//						pageObjectManager.getLhsOptionsPage().devicesTab.click();
//						log.info("clicked on devices tab");
//						wait.until(ExpectedConditions.visibilityOf(pageObjectManager.getDevicePage().btnAddDevices));
//						log.info("Add Devices button appeared");
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//				}
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

//			if (testResultsStatus.getIsLoginSuccess()) {
//				pageObjectManager.getHeaderFunctionalityPage().logoutOfApplication();
//			}

			log.info("====End of " + testResultsStatus.getTestName() + " test====");

		}

		@Test(priority = 0)
		public void startTest() {
			driver.get(Constants.loginURL);

			// Get the required user credentials
			try {
				userCredentials = localExcelUtility.getCompleteSheetData(getWorkbookPath_AccountsForTesting(),
						"EndToEndUsers", "UsersSanityTest");
				log.info(userCredentials.emailID + "  " + userCredentials.password);
				pageObjectManager.setUserCredentials(userCredentials);

			} catch (Exception e1) {
				e1.printStackTrace();
			}

			pageObjectManager.getLoginPage().loginToAccount(testResultsStatus, userCredentials);
			// Click management section from LHS
			pageObjectManager.getLhsOptionsPage().waitForManagementSectionAndClick(testResultsStatus);
		}

}
