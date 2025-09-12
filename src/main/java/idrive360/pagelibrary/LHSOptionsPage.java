package idrive360.pagelibrary;

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

public class LHSOptionsPage extends TestBase {

	private WebDriver driver = null;
	private Wait<WebDriver> wait = null;
	private PageObjectManager pageObjectManager = null;

	public LHSOptionsPage(WebDriver driver, Wait<WebDriver> wait) {
		this.driver = driver;
		this.wait = wait;
		PageFactory.initElements(this.driver, this);
	}

	@FindBy(xpath = "//li[@id='1']//span[contains(text(), 'Devices')]")
	public WebElement devicesTab;

	@FindBy(xpath = "//li[@id='1']//span[contains(text(), 'Overview')]")
	public WebElement OverviewTab;

	@FindBy(xpath = "//li[@id='2']//span[contains(text(), 'Backup Plan')]")
	public WebElement backupPlanTab;

	@FindBy(xpath = "//li[@id='2']//span[contains(text(), 'Companies')]")
	public WebElement companiesTab;

	@FindBy(xpath = "//li[@id='3']//span[contains(text(), 'Settings')]")
	public WebElement settingsTab;

	@FindBy(xpath = "//li[@id='3']//span[contains(text(), 'Users')]")
	public WebElement usersTab;

	@FindBy(xpath = "//li[@id='4']//span[contains(text(), 'Reports')]")
	public WebElement reportsTab;

	@FindBy(xpath = "//li[@id='4']//span[contains(text(), 'Activity Logs')]")
	public WebElement activityLogsTab;

	@FindBy(xpath = "//li[@id='5']//span[contains(text(), 'My Account')]")
	public WebElement BackupSectionmyAccounttab;

	@FindBy(xpath = "//li[@id='5']//span[contains(text(), 'Settings')]")
	public WebElement managementSettingsTab;

	@FindBy(id = "6")
	public WebElement ExpressTab;

	@FindBy(xpath = "//li[@id='7']//span[contains(text(), 'My Account')]")
	public WebElement managementSectionMyAccounttab;

	@FindBy(xpath = "//li[@class='id-goto-bkp']")
	public WebElement goToBackupSectionTab;

	@FindBy(xpath = "//li[@class='id-goto-mc']")
	public WebElement goToManagementSectionTab;

	@FindBy(xpath = "//div[@id='unitDropddown']//a[@class='id-unit-dropdown']")
	public WebElement companyDropDown;

	// -------------------Methods-----------------------
	public void clickDevicesTab() {
		devicesTab.click();
	}

	public void clickBackupPlanTab() {
		backupPlanTab.click();
	}

	public void clickManagementSectionSettingsTab() {
		managementSettingsTab.click();
	}

	public void clickBackupSectionSettingsTab() {
		settingsTab.click();
	}

	public void clickReportsTab() {
		reportsTab.click();
	}

	public void clickCompaniesTab() {
		companiesTab.click();
	}

	public void clickUsersTab() {
		usersTab.click();
	}

	public void clickActivityLogTab() {
		activityLogsTab.click();
	}

	public void clickManagementSettingsTab() {
		managementSettingsTab.click();
	}

	public void click360ExpressTab() {
		ExpressTab.click();
	}

	public void clickgoToBackupSectionTab() {
		goToBackupSectionTab.click();
	}

	public void clickBackupSectionMyAccounttab() {
		BackupSectionmyAccounttab.click();
	}

	public void clickManagementSectionMyAccounttab() {
		managementSectionMyAccounttab.click();
	}

	public TestResultsStatus waitForManagementSectionAndClick(TestResultsStatus testResultsStatus) {
		try {
			wait.until(ExpectedConditions.visibilityOf(goToManagementSectionTab));
			goToManagementSectionTab.click();
			VerifyManagementSectionPageTitle();
			log.info("Go to Management Console clicked from LHS");
		} catch (Exception e) {
			String errorMessage = "error in the wait for management";
			log.error(errorMessage);
			e.printStackTrace();
			testResultsStatus.setTestResult("Fail");
			testResultsStatus.setTestResultComment(errorMessage + e.getMessage());
			Assert.assertTrue(false);
		}
		return testResultsStatus;
	}

	public TestResultsStatus waitForUsersSectionVisibilityAndClick(TestResultsStatus testResultsStatus) {
		try {
			wait.until(ExpectedConditions.visibilityOf(OverviewTab));
			clickUsersTab();
			log.info("Users tab clicked from LHS");
		} catch (Exception e) {
			String errorMessage = "Error in the wait for Users tab\n";
			log.error(errorMessage);
			e.printStackTrace();
			testResultsStatus.setTestResult("Fail");
			testResultsStatus.setTestResultComment(errorMessage + e.getMessage());
			Assert.assertTrue(false);
		}
		return testResultsStatus;
	}

	public TestResultsStatus waitForActivityLogsVisibilityAndClick(TestResultsStatus testResultsStatus) {
		pageObjectManager = new PageObjectManager(driver, wait);
		try {
			if (pageObjectManager.getHeaderFunctionalityPage().managementConsoleButton.isDisplayed()) {
				pageObjectManager.getHeaderFunctionalityPage().managementConsoleButton.click();
				VerifyManagementSectionPageTitle();
				log.info("Go to Management Console clicked from LHS");
			} else if (pageObjectManager.getHeaderFunctionalityPage().backupConsoleButton.isDisplayed()) {
				log.info("Already in management console");
			}
			wait.until(ExpectedConditions.visibilityOf(activityLogsTab));
			clickActivityLogTab();
			log.info("Activty logs tab clicked from LHS");
		} catch (Exception e) {
			String errorMessage = "Error in the wait for Activty logs tab\n";
			log.error(errorMessage);
			e.printStackTrace();
			testResultsStatus.setTestResult("Fail");
			testResultsStatus.setTestResultComment(errorMessage + e.getMessage());
			Assert.assertTrue(false);
		}
		return testResultsStatus;
	}

	public TestResultsStatus clickBackupPlan(TestResultsStatus testResultsStatus) {
		pageObjectManager = new PageObjectManager(driver, wait);
		try {
			wait.until(ExpectedConditions.visibilityOf(backupPlanTab));
			clickBackupPlanTab();
			log.info("Backup plan tab clicked from LHS");
			pageObjectManager.getBackupPlanPage().loaderAppearAndDisappear(testResultsStatus);
		} catch (Exception e) {
			String errorMessage = "Error clicking backup tab\n";
			log.error(errorMessage);
			e.printStackTrace();
			testResultsStatus.setTestResult("Fail");
			testResultsStatus.setTestResultComment(errorMessage + e.getMessage());
			Assert.assertTrue(false);
		}
		return testResultsStatus;
	}

	public TestResultsStatus waitForCompaniesSectionVisibilityAndClick(TestResultsStatus testResultsStatus) {
		try {
			wait.until(ExpectedConditions.visibilityOf(OverviewTab));
			clickCompaniesTab();
			log.info("Companies tab clicked from LHS");
		} catch (Exception e) {
			String errorMessage = "Error in the wait for Companies tab\n";
			System.out.println(errorMessage);
			e.printStackTrace();
			testResultsStatus.setTestResult("Fail");
			testResultsStatus.setTestResultComment(errorMessage + e.getMessage());
			Assert.assertTrue(false);
		}
		return testResultsStatus;
	}

	public void VerifyBackupSectionPageTitle() {
		log.info("Expected page title is : " + Constants.BACKUP_CONSOLE_PAGE_TITLE);
		wait.until(ExpectedConditions.titleContains(Constants.BACKUP_CONSOLE_PAGE_TITLE));
		log.info("Page title received from Backup section module is: " + driver.getTitle());
	}

	public void VerifyManagementSectionPageTitle() {
		log.info("Expected page title is : " + Constants.MANAGEMENT_CONSOLE_PAGE_TITLE);
		wait.until(ExpectedConditions.titleContains(Constants.MANAGEMENT_CONSOLE_PAGE_TITLE));
		log.info("Page title received from Management section module is: " + driver.getTitle());
	}

	public String getCompanyName() {
		String companyName = wait.until(ExpectedConditions.visibilityOf(companyDropDown)).getAttribute("title");
		return companyName;
	}

}