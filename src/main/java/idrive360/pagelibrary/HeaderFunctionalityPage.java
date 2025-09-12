package idrive360.pagelibrary;

import java.util.NoSuchElementException;
import java.util.concurrent.TimeoutException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.testng.Assert;

import idrive360.managers.PageObjectManager;
import idrive360.testbase.Constants;
import idrive360.testbase.TestBase;
import idrive360.utilities.TestResultsStatus;

public class HeaderFunctionalityPage extends TestBase {

	private WebDriver driver = null;
	private Wait<WebDriver> wait = null;
	private PageObjectManager pageObjectManager = null;

	public HeaderFunctionalityPage(WebDriver driver, Wait<WebDriver> wait) {
		this.driver = driver;
		this.wait = wait;
		PageFactory.initElements(this.driver, this);
	}

//	@FindBy(xpath = "//following-sibling::div[@class='id-prof-dw id-prof-dpdw']/a")
//	public WebElement txtAccountName;

	@FindBy(xpath = "//div[@class='id-hdr-rgt']//div[@class='id-prof-dw id-prof-dpdw']/a")
	public WebElement txtAccountName;

	@FindBy(xpath = "//div[@class='id-profblk']/a[@title='My Account']")
	public WebElement btnMyAccount;

	@FindBy(xpath = "//div[@id='Profiledrp-dwn']//a[@title='Logout']")
	public WebElement btnLogout;

	@FindBy(xpath = "//button[@class='id-btn id-warning-btn-drk id-tkn-btn']")
	public WebElement btnHeaderUpgradeNow;

	@FindBy(xpath = "//a[@class='id-btn id-warning-btn-drk id-tkn-btn']")
	public WebElement btnHeaderUpgradeNowLink;

	@FindBy(xpath = "//a[@class='id-btn id-warning-btn-drk id-tkn-btn']")
	public WebElement btnPaidAccountHeaderUpgrade;

	@FindBy(linkText = "Add")
	public WebElement headerAdd;

	@FindBy(id = "add-device-header-btn")
	public WebElement headerAddDeviceButton;

	// Add user in the Header add option
	@FindBy(xpath = "//a[contains(text(),'Add User')]")
	public WebElement headerAddUser;

	// Add user in the Header add option
	@FindBy(xpath = "//a[contains(text(),'Add Company')]")
	public WebElement headerAddCompany;

	@FindBy(xpath = "//div[contains(@class,'id-alert failure id-show') or contains(@class,'id-alert success id-show')]/p")
	public WebElement commonmessage;
	
	@FindBy(xpath = "//button[@class='id-btn id-console-btn id-primary-btn-bd id-management-console-btn']")
	public WebElement managementConsoleButton;
	
	@FindBy(xpath = "//button[@class='id-btn id-primary-btn-bd id-console-btn id-backup-console-btn']")
	public WebElement backupConsoleButton;

	public void clickAccountSection() {
		txtAccountName.click();
	}

	public void clickMyAccountFromHeader() {
		btnMyAccount.click();
	}

	public void clickLogout() {
		btnLogout.click();
		log.info("Logout button clicked");
	}

	public void clickBtnHeaderUpgradeNow() {
		btnHeaderUpgradeNow.click();
	}

	public void clickPaidAccountBtnHeaderUpgrade() {
		btnPaidAccountHeaderUpgrade.click();
	}

	public void clickHeaderAdd() {
		headerAdd.click();
	}

	public void clickHeaderAddUser() {
		headerAddUser.click();
	}

	public void clickHeaderAddCompany() {
		headerAddCompany.click();
	}

	public TestResultsStatus waitAndClickAddButtonFromHeader(TestResultsStatus testResultsStatus) {
		try {
			pageObjectManager = new PageObjectManager(driver, wait);
			log.info("Waiting for Overview tab to be visible");
			wait.until(ExpectedConditions.visibilityOf(pageObjectManager.getLhsOptionsPage().OverviewTab));
			log.info("Clcking on Add button from header");
			clickHeaderAdd();
			log.info("Header add button clicked");
		} catch (Exception e) {
			String errorMessage = "Error while clicking on Add button from header in management console\n";
			System.out.println(errorMessage);
			e.printStackTrace();
			testResultsStatus.setTestResult("Fail");
			testResultsStatus.setTestResultComment(errorMessage + e.getMessage());
			Assert.assertTrue(false);
		}
		return testResultsStatus;
	}

	public void logoutOfApplication() {
		pageObjectManager = new PageObjectManager(driver, wait);
		try {
			log.info("Looking for profile icon");
			clickAccountSection();
			log.info("Looking for Log out button");
			clickLogout();
			log.info("Waiting for Email text field in login page");
			wait.until(ExpectedConditions.visibilityOf(pageObjectManager.getLoginPage().txtbxEmail));
			log.info("Logged out of the application successfully");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Error while logging out of the account with following reason==>.\n" + e.getMessage());
			driver.get(Constants.IDrive360_Logout);
			wait.until(ExpectedConditions.visibilityOf(pageObjectManager.getLoginPage().txtbxEmail));
			log.error("Logged out of the application successfully");
		}
	}

	public void waitAndClickOnAddUserFromHeaderUnderAddButton() {
		log.info("Waiting for Add user button under header Add button");
		wait.until(ExpectedConditions.visibilityOf(pageObjectManager.getHeaderFunctionalityPage().headerAddUser));
		log.info("Clicking on Add user button");
		pageObjectManager.getHeaderFunctionalityPage().clickHeaderAddUser();
		log.info("Header Add user button clicked");
	}

	public void waitAndClickOnAddCompanyFromHeaderUnderAddButton() {
		log.info("Waiting for Add company button under header Add button");
		wait.until(ExpectedConditions.visibilityOf(pageObjectManager.getHeaderFunctionalityPage().headerAddCompany));
		log.info("Clicking on Add company button");
		pageObjectManager.getHeaderFunctionalityPage().clickHeaderAddCompany();
		log.info("Header Add company button clicked");
	}

	public String waitForMessage() {
		log.info("Waiting for common message");
		wait.until(ExpectedConditions.visibilityOf(commonmessage));
		String message = commonmessage.getText();
		return message;
	}

	public TestResultsStatus navigateToMyAccount(TestResultsStatus testResultsStatus) {
		try {
			log.info("Looking for profile icon");
			clickAccountSection();
			log.info("Profile from header is clicked");
			clickMyAccountFromHeader();
			log.info("My account button clicked from header");
		} catch (Exception e) {
			String errorMessage = "Error while navigating to My account\n";
			log.error(errorMessage);
			e.printStackTrace();
			testResultsStatus.setTestResult("Fail");
			testResultsStatus.setTestResultComment(errorMessage + e.getMessage());
			Assert.assertTrue(false);
		}
		return testResultsStatus;
	}

	public TestResultsStatus waitAndClickOnAddUpgradeFromHeader(TestResultsStatus testResultsStatus) {
		try {
			wait.until(ExpectedConditions.visibilityOf(btnHeaderUpgradeNow));
			log.info("Upgrade Now button appeared in the header");
			// click upgrade Now from header
			clickBtnHeaderUpgradeNow();
			log.info("Upgrade now button clicked");
		} catch (Exception e) {
			log.info(btnHeaderUpgradeNowLink.isDisplayed());
			if (btnHeaderUpgradeNowLink.isDisplayed()) {
				pageObjectManager = new PageObjectManager(driver, wait);
				String message = "Account is already upgraded: "
						+ pageObjectManager.getAccountPage().txtNoOfLicenses.getText();
				log.error(message);
				testResultsStatus.setTestResult("Fail");
				testResultsStatus.setTestResultComment(message);
				Assert.assertTrue(false);
			}
			e.printStackTrace();
			log.error("Exception occured with upgrade now button: " + e.getMessage());
			testResultsStatus.setTestResult("Fail");
			testResultsStatus.setTestResultComment("Exception occured with upgrade now button: " + e.getMessage());
			Assert.assertTrue(false, "Exception occured with upgrade now button");
		}
		return testResultsStatus;
	}

}
