/**
 * Author : Vinayak
 * This is a template class to identify how to use the page factory methods along with the scripts.
 */
package scripts.devices;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.openqa.selenium.WebDriver;
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
import scripts.cancellation.CancellationOfIDrive360Account;
import scripts.login.LoginToFreeTrialAccount;


public class BlockComputerFromSettings extends TestBase {
	PageObjectManager pageObjectManager = null;
	UserCredentials userCredentials = null;
	WebDriver driver = null;
	Wait<WebDriver> wait = null;
	
	public BlockComputerFromSettings() {
		
	}
	
	public BlockComputerFromSettings(PageObjectManager pageObjectManager, WebDriver driver,
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
			testResultsStatus.setTestName(LoginToFreeTrialAccount.class.getSimpleName());
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
		System.out.println(wait);
		testResultsStatus.setTestName(BlockComputerFromSettings.class.getSimpleName());
	}

	@Test
	public void BlockAComputerFromSettings() {
		
		driver.get(Constants.loginURL);

		pageObjectManager.getIndexPage().clickSignIn();
		// Get the required user credentials
		try {
			userCredentials = localExcelUtility.getCompleteSheetData(getWorkbookPath_AccountsForTesting(), "Login",
					"accountwithapplypendingcharges");
			
			System.out.println(userCredentials.emailID + "  " + userCredentials.password);
			pageObjectManager.setUserCredentials(userCredentials);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		pageObjectManager.getLoginPage().enterEmail(pageObjectManager.getUserCredentials().emailID);
		pageObjectManager.getLoginPage().enterPasword(pageObjectManager.getUserCredentials().password);
		testResultsStatus.setTestStartTime(System.currentTimeMillis());
		pageObjectManager.getLoginPage().clickSignIn();
		System.out.println("Clicked Login button");
//
//		try {
//			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='devDtlRow-All Computers-0']")));
//			WebElement device = driver.findElement(By.xpath("//*[@id='devDtlRow-All Computers-0']"));
//			System.out.println("computer is visible");
//			// using actions class to perform mouse hover over the computer in order to click the settings.
//			Actions action = new Actions(driver);
//			action.moveToElement(device).perform();
//			System.out.println("mouse hovered on device");
//			wait.until(ExpectedConditions.visibilityOf(pageObjectManager.getComputerDevicePage().btnSettingsComputer));
//			action.moveToElement(pageObjectManager.getComputerDevicePage().btnSettingsComputer).perform();
//			System.out.println("moved to settings");
//			action.click(pageObjectManager.getComputerDevicePage().btnSettingsComputer).build().perform();
//			wait.until(ExpectedConditions.visibilityOf(pageObjectManager.getComputerDevicePage().btnBlockComputer));
//			pageObjectManager.getComputerDevicePage().btnBlockComputer.click();
//			wait.until(ExpectedConditions.visibilityOf(pageObjectManager.getComputerDevicePage().btnblock));
//			pageObjectManager.getComputerDevicePage().btnblock.click();
//			System.out.println("first block button clicked");
//			pageObjectManager.getComputerDevicePage().btnblock.click();
//			System.out.println("final block button clicked");
//			wait.until(ExpectedConditions.visibilityOf(pageObjectManager.getComputerDevicePage().msgSuccess));
//			String message = pageObjectManager.getComputerDevicePage().msgSuccess.getText();
//			System.out.println("message received from toast message :" + message);
//			Assert.assertEquals(message, "Device DILEEPKUMARDAV has been blocked.");
//			testResultComment = "Device has been blocked";
//			testResult = "Pass";
//		} catch (Exception e) {
//			testResult = "Fail";
//			testResultComment = e.getMessage();
//			System.out.println(e.getMessage());
//			// Once test is completed, end the session.
//			headerFunctionalityPage.clickAccountSection();
//			headerFunctionalityPage.logout();
//			wait.until(ExpectedConditions.visibilityOf(loginPage.txtbxEmail));
//		}
	}

	@AfterTest
	public void endTest() {
		// Write the test results to the test results workbook and close the browser.
		try {

			localExcelUtility.writeTestResultsStatus(testResultsStatus.getTestName(), testResultsStatus.getTestResult(),
					testResultsStatus.getTestResultComment(), testResultsStatus.getTestExecutionTime().intValue());
		} catch (Exception e) {
			System.out.println("Reached exception while writing testresults.");
			e.printStackTrace();
			Assert.assertTrue(false, "Reached exception while waiting, post login");
		}
		driver.quit();
	}
}
