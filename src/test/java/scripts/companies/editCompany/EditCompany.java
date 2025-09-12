/**
 * Author : Vinayak
 * This is a template class to identify how to use the page factory methods along with the scripts.
 */

package scripts.companies.editCompany;

import java.io.FileNotFoundException;
import java.io.IOException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
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

public class EditCompany extends TestBase {
	UserCredentials userCredentials = null;
	PageObjectManager pageObjectManager = null;
	WebDriver driver = null;
	Wait<WebDriver> wait = null;

	public EditCompany() {

	}

	public EditCompany(PageObjectManager pageObjectManager, WebDriver driver, Wait<WebDriver> wait,
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

		testResultsStatus.setTestName(EditCompany.class.getSimpleName());
	}

	@Test
	public void clickEditcompany() {
		driver.manage().window().maximize();
		driver.get(Constants.loginURL);

		// Get the required user credentials
		try {
			userCredentials = localExcelUtility.getCompleteSheetData(getWorkbookPath_AccountsForTesting(), "Companies",
					"EditCompany");
			System.out.println(userCredentials.emailID + "  " + userCredentials.password);
			pageObjectManager.setUserCredentials(userCredentials);

		} catch (Exception e1) {
			e1.printStackTrace();
		}
		pageObjectManager.getLoginPage().loginToAccount(testResultsStatus, userCredentials);
		pageObjectManager.getLhsOptionsPage().waitForManagementSectionAndClick(testResultsStatus);
		logicForEditCompany();

	}

	public TestResultsStatus logicForEditCompany() {
		try {
			// Hover over existing company- View icon
			wait.until(ExpectedConditions.visibilityOf(pageObjectManager.getLhsOptionsPage().goToManagementSectionTab));
			pageObjectManager.getLhsOptionsPage().goToManagementSectionTab.click();
			System.out.println("Go to Management Console");
			wait.until(ExpectedConditions.visibilityOf(pageObjectManager.getCompaniesPage().overView));
			wait.until(ExpectedConditions.visibilityOf(pageObjectManager.getCompaniesPage().subCompany));
			pageObjectManager.getCompaniesPage().clickLHSMenuCompanies();
			System.out.println("Navigated to companys Tab");
			// Visibility of existing company
			wait.until(ExpectedConditions.visibilityOf(pageObjectManager.getCompaniesPage().subCompany));
			// hover over option of existing company-View icon
			Actions a1 = new Actions(driver);
			a1.moveToElement(pageObjectManager.getCompaniesPage().existingCompanyName(userCredentials.companyName))
					.perform();
			pageObjectManager.getCompaniesPage().viewIconClick(userCredentials.companyName);
			System.out.println("Clicked View icon, Slider opened up");
			// Edit icon under View
			// wait.until(ExpectedConditions.visibilityOf(pageObjectManager.getCompaniesPage().viewIcon(userCredentials.companyName)));
			pageObjectManager.getCompaniesPage().viewUnitEditIcon();
			System.out.println("Clicked Edit icon");
			// pageObjectManager.getCompaniesPage().EditcompanyNameField.sendKeys(userCredentials.editedcompanyname);
			pageObjectManager.getCompaniesPage().editCompanyNameField.clear();
			pageObjectManager.getCompaniesPage().unitNameEditField(userCredentials.editedCompanyName);
			System.out.println("Entered updated company name");
			pageObjectManager.getCompaniesPage().saveButton();
			System.out.println("company name has been updated");
			wait.until(
					ExpectedConditions.visibilityOf(pageObjectManager.getCompaniesPage().companyNameUpdatedToastmsg));
			String message = pageObjectManager.getCompaniesPage().companyNameUpdatedToastmsg.getText();
			System.out.println("message received from toast message :" + message);
			wait.until(ExpectedConditions.visibilityOf(pageObjectManager.getCompaniesPage().subCompany));
			Assert.assertEquals(message, "company updated successfully");
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
