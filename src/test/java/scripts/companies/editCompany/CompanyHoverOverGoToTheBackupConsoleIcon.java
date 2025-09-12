/**
 * Author : Vinayak
 * This is a template class to identify how to use the page factory methods along with the scripts.
 */
package scripts.companies.editCompany;

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

public class CompanyHoverOverGoToTheBackupConsoleIcon extends TestBase {
	UserCredentials userCredentials = null;
	PageObjectManager pageObjectManager = null;
	WebDriver driver = null;
	Wait<WebDriver> wait = null;

	public CompanyHoverOverGoToTheBackupConsoleIcon() {

	}

	public CompanyHoverOverGoToTheBackupConsoleIcon(PageObjectManager pageObjectManager, WebDriver driver,
			Wait<WebDriver> wait, TestResultsStatus testResultsStatus) {
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
			System.out.println("Properties file could not be found");
			fnfe.printStackTrace();
		} catch (IOException ioe) {
			System.out.println("Path for file is not found or incorrect.");
			ioe.printStackTrace();
		} catch (TimeoutException toe) {
			System.out.println("Wait creation failed");
		} catch (Exception e) {
			e.printStackTrace();
		}

		driver = threadLocalDriver.get();
		wait = threadLocalWait.get();
		pageObjectManager = new PageObjectManager(driver, wait);
		testResultsStatus.setTestName(CompanyHoverOverGoToTheBackupConsoleIcon.class.getSimpleName());
	}

	@Test
	public void hoveroverUnitOption() {
		driver.get(Constants.loginURL);

		// Get the required user credentials
		try {
			userCredentials = localExcelUtility.getCompleteSheetData(getWorkbookPath_AccountsForTesting(), "Companies",
					"ViewCompany");
			System.out.println(userCredentials.emailID + "  " + userCredentials.password);
			pageObjectManager.setUserCredentials(userCredentials);

		} catch (Exception e1) {
			e1.printStackTrace();
		}
		pageObjectManager.getLoginPage().loginToAccount(testResultsStatus, userCredentials);
		pageObjectManager.getLhsOptionsPage().waitForManagementSectionAndClick(testResultsStatus);
		logicForUnitHoverOverGoToTheBackupConsoleIcon();

	}

	public TestResultsStatus logicForUnitHoverOverGoToTheBackupConsoleIcon() {
		try {
			// Click on Companies from LHS
			pageObjectManager.getLhsOptionsPage().waitForCompaniesSectionVisibilityAndClick(testResultsStatus);
			// Navigate to backup console
			try {
				pageObjectManager.getCompaniesPage()
						.navigateToBackupConsole(pageObjectManager.getUserCredentials().companyName);
			} catch (Exception e) {
				String errorMessage = "Error while navigating to Backup console\n";
				System.out.println(errorMessage);
				e.printStackTrace();
				testResultsStatus.setTestResult("Fail");
				testResultsStatus.setTestResultComment(errorMessage + e.getMessage());
				Assert.assertTrue(false);
			}
			// Navigate to backup console
			try {
				pageObjectManager.getCompaniesPage()
						.clickBackupConsole(pageObjectManager.getUserCredentials().companyName);
			} catch (Exception e) {
				String errorMessage = "Error while clicing on Backup console\n";
				System.out.println(errorMessage);
				e.printStackTrace();
				testResultsStatus.setTestResult("Fail");
				testResultsStatus.setTestResultComment(errorMessage + e.getMessage());
				Assert.assertTrue(false);
			}

			wait.until(ExpectedConditions
					.visibilityOf(pageObjectManager.getHeaderFunctionalityPage().headerAddDeviceButton));
			System.out.println("Add device button displayed in backup console");

		} catch (Exception e) {
			testResultsStatus.setTestResult("Fail");
			testResultsStatus.setTestResultComment(e.getMessage());
			System.out.println(e.getMessage());
		} catch (AssertionError ae) {
			testResultsStatus.setTestResult("Fail");
			testResultsStatus
					.setTestResultComment("Looks like the toast message is not matching with expected message");
			System.out.println(ae.getMessage());
		}
		return testResultsStatus;
	}

	@AfterTest
	public void endTest() {
		// Write the test results to the test results workbook and close the browser.
		System.out.println("updating test results from after test");
		System.out.println(testResultsStatus.getTestResultComment());
		try {
			localExcelUtility.writeTestResultsStatus(testResultsStatus.getTestName(), testResultsStatus.getTestResult(),
					testResultsStatus.getTestResultComment(), testResultsStatus.getTestExecutionTime().intValue());
		} catch (Exception e) {
			System.out.println("Reached exception while writing testresults.");
			e.printStackTrace();
			Assert.assertTrue(false, "Reached exception while waiting, post login");
		}

		// logout of the application
		System.out.println("Logging out of the application");
		pageObjectManager.getHeaderFunctionalityPage().logoutOfApplication();
		driver.quit();
	}

}