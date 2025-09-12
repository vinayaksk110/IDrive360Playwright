package scripts.companies.editCompany;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
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
import scripts.companies.deleteCompany.DeleteCompany;

public class DisableCompany extends TestBase {

	UserCredentials userCredentials = null;
	PageObjectManager pageObjectManager = null;
	WebDriver driver = null;
	Wait<WebDriver> wait = null;

	public DisableCompany() {

	}

	public DisableCompany(PageObjectManager pageObjectManager, WebDriver driver, Wait<WebDriver> wait,
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
		testResultsStatus.setTestName(DeleteCompany.class.getSimpleName());
	}

	@Test
	public void startTest() {
		driver.get(Constants.loginURL);

		// Get the required user credentials
		try {
			userCredentials = localExcelUtility.getCompleteSheetData(getWorkbookPath_AccountsForTesting(), "Companies",
					"DeleteCompany");
			System.out.println(userCredentials.emailID + "  " + userCredentials.password);
			pageObjectManager.setUserCredentials(userCredentials);

		} catch (Exception e1) {
			e1.printStackTrace();
		}

		pageObjectManager.getLoginPage().loginToAccount(testResultsStatus, userCredentials);
		// Click management section from LHS
		pageObjectManager.getLhsOptionsPage().waitForManagementSectionAndClick(testResultsStatus);
		logicForDisableCompany();
	}

	public TestResultsStatus logicForDisableCompany() {
		try {
			// Click on Companies from LHS
			pageObjectManager.getLhsOptionsPage().waitForCompaniesSectionVisibilityAndClick(testResultsStatus);
			// navigate on Company
			try {
				pageObjectManager.getCompaniesPage()
						.navigateToCompany(pageObjectManager.getUserCredentials().companyName);
			} catch (Exception e) {
				String errorMessage = "Error while moving to Company\n";
				System.out.println(errorMessage);
				e.printStackTrace();
				testResultsStatus.setTestResult("Fail");
				testResultsStatus.setTestResultComment(errorMessage + e.getMessage());
				Assert.assertTrue(false);
			}

			// navigate to delete icon
			try {
				pageObjectManager.getCompaniesPage()
						.navigateToDeleteIconForCompany(pageObjectManager.getUserCredentials().companyName);
			} catch (Exception e) {
				String errorMessage = "Error while moving to Delete button\n";
				System.out.println(errorMessage);
				e.printStackTrace();
				testResultsStatus.setTestResult("Fail");
				testResultsStatus.setTestResultComment(errorMessage + e.getMessage());
				Assert.assertTrue(false);
			}

			// delete company
			try {
				pageObjectManager.getCompaniesPage().deleteCompany(pageObjectManager.getUserCredentials().companyName);
			} catch (Exception e) {
				String errorMessage = "Error while deleting company\n";
				System.out.println(errorMessage);
				e.printStackTrace();
				testResultsStatus.setTestResult("Fail");
				testResultsStatus.setTestResultComment(errorMessage + e.getMessage());
				Assert.assertTrue(false);
			}

			// success message
			String message = pageObjectManager.getHeaderFunctionalityPage().waitForMessage();
			System.out.println("Message from toast message is : " + message);
			try {
				Assert.assertEquals(message, "Company " + userCredentials.companyName + " has been added");
				testResultsStatus.setTestResultComment("Toast message received is : " + message);
				testResultsStatus.setTestResult("Pass");
			} catch (AssertionError ae) {
				String errorMessage = "Error while asserting toast message for add user\n";
				System.out.println(errorMessage);
				ae.printStackTrace();
				testResultsStatus.setTestResult("Fail");
				testResultsStatus.setTestResultComment(errorMessage + ae.getMessage());
				Assert.assertTrue(false);
			}
			wait.until(ExpectedConditions.visibilityOf(pageObjectManager.getCompaniesPage().subCompany));
			Assert.assertEquals(message, "Unit " + userCredentials.companyName + " has been added");
			testResultsStatus.setTestResultComment(message);
			testResultsStatus.setTestResult("Pass");
		} catch (Exception e) {
			testResultsStatus.setTestResult("Fail");
			testResultsStatus.setTestResultComment(e.getMessage());
			System.out.println(e.getMessage());
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
