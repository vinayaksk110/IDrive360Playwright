package scripts.devices;

import static org.testng.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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

public class RenameAComputerFromSettings extends TestBase {
	UserCredentials userCredentials = null;
	PageObjectManager pageObjectManager = null;
	WebDriver driver = null;
	Wait<WebDriver> wait = null;
	
	public RenameAComputerFromSettings() {
		
	}
	
	public RenameAComputerFromSettings(PageObjectManager pageObjectManager, WebDriver driver, Wait<WebDriver> wait,
			TestResultsStatus testResultsStatus) {
		this.pageObjectManager = pageObjectManager;
		this.driver = driver;
		this.wait = wait;
		this.testResultsStatus = testResultsStatus;
	}

	@Parameters({ "excelFileStatus", "browser", "headlessMode" })
	@BeforeClass
	public void initializeTest(@Optional("yes") String excelFileStatus, @Optional("chrome") String browser,
			@Optional("no") String headlessMode, @Optional("testserver25") String executionEnvironment)
					throws FileNotFoundException, IOException {
				try {
					initializeTestingEnvironment(excelFileStatus, browser, headlessMode, executionEnvironment);
					testResultsStatus.setTestName(DeleteComputerFromSettings.class.getSimpleName());
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
				testResultsStatus.setTestName(RenameAComputerFromSettings.class.getSimpleName());
			}

	@Test
	public void startRenameAComputerFromSettings() {
		driver.manage().window().maximize();
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
		
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@title='DILEEPKUMARDAV']")));
			WebElement device = driver.findElement(By.xpath("//*[@title='DILEEPKUMARDAV']"));
			System.out.println("computer is visible");
			//using actions class to perform mouse hover over the computer in order to click the settings.
			Actions action = new Actions(driver);
			action.moveToElement(device).perform();
			System.out.println("mouse hovered on device");
			//WebElement settings = driver.findElement(By.xpath("//b[@title='DILEEPKUMARDAV']//ancestor::div[@class='id-rw-col id-dvce-uname']//following-sibling::div[@id='devDtlRowAction-All Computers-0']/ul//li[@class='id-ut-act']/button"));
			wait.until(ExpectedConditions.visibilityOf(pageObjectManager.getComputerDevicePage().btnSettingsComputer));
			action.moveToElement(pageObjectManager.getComputerDevicePage().btnSettingsComputer).perform();
			System.out.println("moved to settings");
			action.click(pageObjectManager.getComputerDevicePage().btnSettingsComputer).build().perform();
			wait.until(ExpectedConditions.visibilityOf(pageObjectManager.getComputerDevicePage().btnComputerRename));
			action.moveToElement(pageObjectManager.getComputerDevicePage().btnComputerRename).perform();
			action.click(pageObjectManager.getComputerDevicePage().btnComputerRename).build().perform();
			driver.getWindowHandle();
			System.out.println("Rename computer clicked");
			wait.until(ExpectedConditions.visibilityOf(pageObjectManager.getComputerDevicePage().InputRenameField));
			pageObjectManager.getComputerDevicePage().InputRenameField.clear();
			pageObjectManager.getComputerDevicePage().InputRenameField.sendKeys("DileepOffice");
			pageObjectManager.getComputerDevicePage().btnSaveRenameComputer.click();
			wait.until(ExpectedConditions.visibilityOf(pageObjectManager.getComputerDevicePage().msgSuccess));
			String message = pageObjectManager.getComputerDevicePage().msgSuccess.getText();
			System.out.println("message received from toast message :" + message);
			Assert.assertEquals(message, "Device name has been changed from DILEEPKUMARDAV to DileepOffice.");
			testResultsStatus.setTestResultComment(message);
			testResultsStatus.setTestResult("Pass");
		} catch (Exception e) {
			testResultsStatus.setTestResult("Fail");
			testResultsStatus.setTestResultComment(e.getMessage());

			System.out.println(e.getMessage());
			// Once test is completed, end the session.
						pageObjectManager.getHeaderFunctionalityPage().clickAccountSection();
						pageObjectManager.getHeaderFunctionalityPage().clickLogout();
				
						wait.until(ExpectedConditions.visibilityOf(pageObjectManager.getLoginPage().txtbxEmail));
					}

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
