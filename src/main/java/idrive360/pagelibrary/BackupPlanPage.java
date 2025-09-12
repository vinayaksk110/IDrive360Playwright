package idrive360.pagelibrary;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.testng.Assert;

import idrive360.data.UserCredentials;
import idrive360.managers.PageObjectManager;
import idrive360.testbase.TestBase;
import idrive360.utilities.TestResultsStatus;

public class BackupPlanPage extends TestBase {

	private WebDriver driver = null;
	private Wait<WebDriver> wait = null;
	private PageObjectManager pageObjectManager = null;
	private Select select = null;
	private Actions action = null;

	public BackupPlanPage(WebDriver driver, Wait<WebDriver> wait) {
		this.driver = driver;
		this.wait = wait;
		PageFactory.initElements(this.driver, this);
	}

	// =========================================================Locators==========================================
	@FindBy(id = "id-btn-bkp-crtpln")
	public WebElement createBackupPlan;

	@FindBy(id = "disMultibckupBtn")
	public WebElement disableBackupPlan;

	@FindBy(id = "enMultibckupBtn")
	public WebElement enableBackupPlan;

	@FindBy(id = "dltMultibckupBtn")
	public WebElement deleteBackupPlan;

	@FindBy(id = "idEdit")
	public WebElement editBackupPlanNameInSlider;

	@FindBy(id = "addvcebtn")
	public WebElement propogateButtonInSlider;

	@FindBy(xpath = "//a[@class='gotoadddvce id-tooltip']")
	public WebElement addLinkComputersInSlider;

	@FindBy(xpath = "//button[@class='id-btn id-info-btn id-tkn-btn']")
	public WebElement CreatebuttonInSlider;

	@FindBy(xpath = "//a[@class='gotosrc id-tooltip']")
	public WebElement specifyLinkItemsToBackupInSlider;
	
	@FindBy(xpath = "//a[@class='gotosrc id-tooltip']/span/span")
	public WebElement editSpecifyLinkItemsToBackupInSlider;

	@FindBy(xpath = "//button[@class='id-btn id-info-btn id-tkn-btn']")
	public WebElement createBackupPlanInSlider;

	@FindBy(xpath = "//button[@class='id-btn']")
	public WebElement cancelBackupPlanInSlider;

	@FindBy(xpath = "//a[@class='id-close-sidebar id-tooltip-bottom']")
	public WebElement closeBackupPlanSlider;

	@FindBy(xpath = "//div[(@id='backupPlanNameModel') and contains(@style,'display: block;')]")
	public WebElement renameBackupPlanPopup;

	@FindBy(id = "bkpNameChngeConfBtnId")
	public WebElement saveRenameBackupPlanPopup;

	@FindBy(xpath = "//div[@id='deleteCheckBox']//span[@class='id-checkmark']")
	public WebElement deleteConfirmCheckboxBackupPlanPopup;

	@FindBy(xpath = "//div[@id='disableMultibckupModal']//a[@class='id-btn id-info-btn']")
	public WebElement disableBackupPlanPopupDisableButton;

	@FindBy(xpath = "//div[@id='enableMultibckupModal']//a[@class='id-btn id-info-btn']")
	public WebElement enableBackupPlanPopupEnableButton;

	@FindBy(xpath = "//div[@id='enableMultibckupModal']//a[@class='id-btn id-info-btn']")
	public WebElement updateplanButtonInEditslider;

	@FindBy(id = "bckupPlanNameToEdit")
	public WebElement textBoxRenameBackupPlanPopup;

	@FindBy(id = "addrule")
	public WebElement specifyWhatToBackupSelect;

	@FindBy(id = "dltMultiBkpPlanBtn")
	public WebElement deleteButtonInconfirmPopup;

	@FindBy(xpath = "//div[@id='deletebckupModal']//div[@class='id-pop-header']//a[@class='close id-close']")
	public WebElement closeDeletePopup;

	@FindBy(xpath = "//div[@id='disableMultibckupModal']//div[@class='id-pop-header']//a[@class='close id-close']")
	public WebElement closeDisablePopup;

	@FindBy(xpath = "//div[@id='enableMultibckupModal']//div[@class='id-pop-header']//a[@class='close id-close']")
	public WebElement closeEnablePopup;

	@FindBy(xpath = "//div[@class='id-addgrp-ft']//button[@class='id-btn id-info-btn']")
	public WebElement doneButtonSpecifyRule;

	@FindBy(xpath = "//div[contains(@class, 'id-alert success id-show')]/p")
	public WebElement successMessage;

	@FindBy(xpath = "//div[@class='id-loader-section id-show']")
	public WebElement loader;

	public WebElement backupPlanRow(String planName) {
		return this.driver.findElement(By.xpath("//div[@title='" + planName + "']"));
	}

	public WebElement propogateBackupPlanOnHover(String planName) {
		return this.driver.findElement(By.xpath("//div[@title='" + planName
				+ "']//following::div[@class='id-td id-ut-action']//a[@class='id-probkp-plan id-tooltip']"));
	}

	public WebElement checkboxBackupPlanList(String planName) {
		return this.driver.findElement(By.xpath("//div[@title='" + planName
				+ "']//preceding-sibling::div[@class='id-td id-td-rw-select']//span[@class='id-checkmark']"));
	}

	public WebElement editBackupPlanOnHover(String planName) {
		return this.driver.findElement(By.xpath("//div[@title='" + planName
				+ "']//following::div[@class='id-td id-ut-action']//a[@class='id-edit-opt id-tooltip id-tkn-btn']"));
	}

	public WebElement disableBackupPlanOnHover(String planName) {
		return this.driver.findElement(By.xpath("//div[@title='" + planName
				+ "']//following::div[@class='id-td id-ut-action']//a[@class='id-block-opt id-tooltip id-tkn-btn']"));
	}

	public WebElement enableBackupPlanOnHover(String planName) {
		return this.driver.findElement(By.xpath("//div[@title='" + planName
				+ "']//following::div[@class='id-td id-ut-action']//a[@class='id-unblock-opt id-tooltip id-tkn-btn']"));
	}

	public WebElement deleteBackupPlanOnHover(String planName) {
		return this.driver.findElement(By.xpath("//div[@title='" + planName
				+ "']//following::div[@class='id-td id-ut-action']//a[@class='id-delete-opt id-tooltip id-tkn-btn']"));
	}

	// ===================================================Methods==========================================

	public TestResultsStatus clickCreatePlan(TestResultsStatus testResultsStatus) {
		try {
			wait.until(ExpectedConditions.visibilityOf(createBackupPlan));
			createBackupPlan.click();
			log.info("Create plan button clicked");
		} catch (Exception e) {
			String errorMessage = "Error clicking Create backupplan button\n";
			log.error(errorMessage);
			e.printStackTrace();
			testResultsStatus.setTestResult("Fail");
			testResultsStatus.setTestResultComment(errorMessage + e.getMessage());
			Assert.assertTrue(false);
		}
		return testResultsStatus;
	}

	public TestResultsStatus clickdisablePlanFromTopMenu(TestResultsStatus testResultsStatus) {
		try {
			wait.until(ExpectedConditions.visibilityOf(disableBackupPlan));
			disableBackupPlan.click();
			log.info("Disable plan button clicked");
		} catch (Exception e) {
			if (enableBackupPlan.isDisplayed()) {
				String errorMessage = "Disable button not diaplayed instead Enable button is displayed\n";
				log.error(errorMessage);
				e.printStackTrace();
				testResultsStatus.setTestResult("Fail");
				testResultsStatus.setTestResultComment(errorMessage + e.getMessage());
				Assert.assertTrue(false);
			}
		}
		return testResultsStatus;
	}

	public TestResultsStatus clickEnablePlanFromTopMenu(TestResultsStatus testResultsStatus) {
		try {
			wait.until(ExpectedConditions.visibilityOf(enableBackupPlan));
			enableBackupPlan.click();
			log.info("Enable plan button clicked");
		} catch (Exception e) {
			if (enableBackupPlan.isDisplayed()) {
				String errorMessage = "Enable button not diaplayed instead Disable button is displayed\n";
				log.error(errorMessage);
				e.printStackTrace();
				testResultsStatus.setTestResult("Fail");
				testResultsStatus.setTestResultComment(errorMessage + e.getMessage());
				Assert.assertTrue(false);
			}
		}
		return testResultsStatus;
	}

	public void clickEnablePlan() {
		wait.until(ExpectedConditions.visibilityOf(enableBackupPlan));
		enableBackupPlan.click();
		log.info("Enable plan button clicked");
	}

	public TestResultsStatus DeleteBackupPlan(TestResultsStatus testResultsStatus) {
		try {
			clickDeletePlanFromTopMenu();
			confirmDeletePopup();
		} catch (Exception e) {
			String errorMessage = "Error while deleting backup plan \n";
			log.error(errorMessage);
			e.printStackTrace();
			testResultsStatus.setTestResult("Fail");
			testResultsStatus.setTestResultComment(errorMessage + e.getMessage());
			Assert.assertTrue(false);
		}
		return testResultsStatus;
	}

	public TestResultsStatus DisableBackupPlan(TestResultsStatus testResultsStatus) {
		try {
			clickdisablePlanFromTopMenu(testResultsStatus);
			confirmDisablePopup();
		} catch (Exception e) {
			String errorMessage = "Error while disabling backup plan \n";
			log.error(errorMessage);
			e.printStackTrace();
			testResultsStatus.setTestResult("Fail");
			testResultsStatus.setTestResultComment(errorMessage + e.getMessage());
			Assert.assertTrue(false);
		}
		return testResultsStatus;
	}

	public TestResultsStatus EnableBackupPlan(TestResultsStatus testResultsStatus) {
		try {
			clickEnablePlanFromTopMenu(testResultsStatus);
			confirmEnablePopup();
		} catch (Exception e) {
			String errorMessage = "Error while enabling backup plan \n";
			log.error(errorMessage);
			e.printStackTrace();
			testResultsStatus.setTestResult("Fail");
			testResultsStatus.setTestResultComment(errorMessage + e.getMessage());
			Assert.assertTrue(false);
		}
		return testResultsStatus;
	}

	public void clickDeletePlanFromTopMenu() {
		wait.until(ExpectedConditions.visibilityOf(deleteBackupPlan));
		deleteBackupPlan.click();
		log.info("Delete plan button clicked");
	}

	public void confirmDeletePopup() {
		try {
			wait.until(ExpectedConditions.visibilityOf(deleteConfirmCheckboxBackupPlanPopup));
			deleteConfirmCheckboxBackupPlanPopup.click();
			log.info("confirm checkbox clicked in Delete popup");
			if (deleteButtonInconfirmPopup.isEnabled()) {
				deleteButtonInconfirmPopup.click();
			}
		} catch (Exception e) {
			e.printStackTrace();
			closeDeletePopup.click();
			log.info("Closed the delete popup due to error while deleting the popup");
		}
	}

	public void confirmDisablePopup() {
		try {
			wait.until(ExpectedConditions.visibilityOf(disableBackupPlanPopupDisableButton));
			disableBackupPlanPopupDisableButton.click();
			log.info("Disable button clicked from popup");
		} catch (Exception e) {
			e.printStackTrace();
			closeDisablePopup.click();
			log.info("Closed the disable popup due to error while deleting the popup");
		}
	}

	public void confirmEnablePopup() {
		try {
			wait.until(ExpectedConditions.visibilityOf(enableBackupPlanPopupEnableButton));
			enableBackupPlanPopupEnableButton.click();
			log.info("Enable button clicked from popup");
		} catch (Exception e) {
			e.printStackTrace();
			closeEnablePopup.click();
			log.info("Closed the disable popup due to error while deleting the popup");
		}
	}

	public void clickPropogatePlanOnHover(String planName) {
		wait.until(ExpectedConditions.visibilityOf(propogateBackupPlanOnHover(planName)));
		propogateBackupPlanOnHover(planName).click();
		log.info("Propogate plan button clicked on hover");
	}

	public void clickEditPlanOnHover(String planName) {
		wait.until(ExpectedConditions.visibilityOf(editBackupPlanOnHover(planName)));
		editBackupPlanOnHover(planName).click();
		log.info("Edit plan button clicked on hover");
	}

	public void clickDisablePlanOnHover(String planName) {
		wait.until(ExpectedConditions.visibilityOf(disableBackupPlanOnHover(planName)));
		disableBackupPlanOnHover(planName).click();
		log.info("Disable plan button clicked on hover");
	}

	public void clickUpdateButtonInEditSlider(String planName) {
		wait.until(ExpectedConditions.visibilityOf(disableBackupPlanOnHover(planName)));
		disableBackupPlanOnHover(planName).click();
		log.info("Disable plan button clicked on hover");
	}

	public void clickEnablePlanOnHover(String planName) {
		wait.until(ExpectedConditions.visibilityOf(enableBackupPlanOnHover(planName)));
		enableBackupPlanOnHover(planName).click();
		log.info("Enable plan button clicked on hover");
	}
	
	public TestResultsStatus clickEditSpecifyLinkInSlider(TestResultsStatus testResultsStatus) {
		try {
			wait.until(ExpectedConditions.visibilityOf(editSpecifyLinkItemsToBackupInSlider));
			editSpecifyLinkItemsToBackupInSlider.click();
			log.info("Items to backup link clicked on hover");
		} catch (Exception e) {
			String errorMessage = "Error while clicking Items to backup \n";
			log.error(errorMessage);
			e.printStackTrace();
			testResultsStatus.setTestResult("Fail");
			testResultsStatus.setTestResultComment(errorMessage + e.getMessage());
			Assert.assertTrue(false);
		}
		return testResultsStatus;
	}

	public void clickSpecifyLinkInSlider() {
			wait.until(ExpectedConditions.visibilityOf(specifyLinkItemsToBackupInSlider));
			specifyLinkItemsToBackupInSlider.click();
			log.info("Specify link clicked on hover");
	}

	public void addRuleWhatToBackup(String rule) {
		select = new Select(specifyWhatToBackupSelect);
		select.selectByVisibleText(rule);
		log.info("Select dropdown selected rule : " + rule);
	}

	public void selectBackupPlan(String planName) {
		try {
			wait.until(ExpectedConditions.visibilityOf(createBackupPlan));
			wait.until(ExpectedConditions.visibilityOf(checkboxBackupPlanList(planName)));
			checkboxBackupPlanList(planName).click();
			log.info("Backup plan checkbox clicked");
		} catch (Exception e) {
			String errorMessage = "Error while clicking backup plan checkbox \n";
			log.error(errorMessage);
			e.printStackTrace();
			testResultsStatus.setTestResult("Fail");
			testResultsStatus.setTestResultComment(errorMessage + e.getMessage());
			Assert.assertTrue(false);
		}
	}

	public TestResultsStatus enterDataAndCreateBackupPlan(UserCredentials userCredentials,
			TestResultsStatus testResultsStatus) {
		action = new Actions(driver);
		pageObjectManager = new PageObjectManager(driver, wait);
		try {
			// rename plan
			wait.until(ExpectedConditions.visibilityOf(editBackupPlanNameInSlider));
			action.moveToElement(editBackupPlanNameInSlider).build().perform();
			wait.until(ExpectedConditions.elementToBeClickable(editBackupPlanNameInSlider));
			editBackupPlanNameInSlider.click();
			log.info("Edit Backup plan name clicked");
			wait.until(ExpectedConditions.visibilityOf(textBoxRenameBackupPlanPopup));
			textBoxRenameBackupPlanPopup.click();
			textBoxRenameBackupPlanPopup.clear();
			textBoxRenameBackupPlanPopup.sendKeys(userCredentials.backupPlanName);
			log.info("Entered backup plan name: " + userCredentials.backupPlanName);
			saveRenameBackupPlanPopup.click();
			log.info("Backup plan Name set");

			// click specify and add rule
			clickSpecifyLinkInSlider();
			addRuleWhatToBackup(userCredentials.ruleToBeAdded);
			doneButtonSpecifyRule.click();
			log.info("Rule set to " + userCredentials.ruleToBeAdded);

			// Click Create in slider.
			CreatebuttonInSlider.click();
			log.info("Create backup plan clicked.");

		} catch (Exception e) {
			String errorMessage = "Error while entering required data and creating a backup plan \n";
			log.error(errorMessage);
			e.printStackTrace();
			testResultsStatus.setTestResult("Fail");
			testResultsStatus.setTestResultComment(errorMessage + e.getMessage());
			Assert.assertTrue(false);
		}
		return testResultsStatus;
	}

	public String waitForSuccessMessage() {
		pageObjectManager = new PageObjectManager(driver, wait);
		log.info("Waiting for common message");
		wait.until(ExpectedConditions.visibilityOf(successMessage));
		String message = successMessage.getText();
		log.info("Message from toast message is : " + message);
		return message;
	}

	public TestResultsStatus loaderAppearAndDisappear(TestResultsStatus testResultsStatus) {
		try {
			wait.until(ExpectedConditions.visibilityOf(loader));
			log.info("Backup page loader is appeared");
			wait.until(ExpectedConditions.invisibilityOf(loader));
			log.info("Backup page loader is disappeared");
		} catch (Exception e) {
			String errorMessage = "Error with display of Loader\n";
			log.error(errorMessage);
			e.printStackTrace();
			testResultsStatus.setTestResult("Fail");
			testResultsStatus.setTestResultComment(errorMessage + e.getMessage());
			Assert.assertTrue(false);
		}
		return testResultsStatus;
	}

	public void hoverBackupPlan(String planName) {
		action = new Actions(driver);
		action.moveToElement(backupPlanRow(planName));
	}

	public TestResultsStatus moveToPropogatePlanandClick(TestResultsStatus testResultsStatus, String planName) {
		try {
			action = new Actions(driver);
			hoverBackupPlan(planName);
			action.moveToElement(propogateBackupPlanOnHover(planName));
			clickPropogatePlanOnHover(planName);
		} catch (Exception e) {
			String errorMessage = "Error while moving to propogate and click\n";
			log.error(errorMessage);
			e.printStackTrace();
			testResultsStatus.setTestResult("Fail");
			testResultsStatus.setTestResultComment(errorMessage + e.getMessage());
			Assert.assertTrue(false);
		}
		return testResultsStatus;
	}

	public TestResultsStatus moveToEditPlanandClick(TestResultsStatus testResultsStatus, String planName) {
		try {
			action = new Actions(driver);
			hoverBackupPlan(planName);
			action.moveToElement(editBackupPlanOnHover(planName));
			clickEditPlanOnHover(planName);
		} catch (Exception e) {
			String errorMessage = "Error while moving to propogate and click\n";
			log.error(errorMessage);
			e.printStackTrace();
			testResultsStatus.setTestResult("Fail");
			testResultsStatus.setTestResultComment(errorMessage + e.getMessage());
			Assert.assertTrue(false);
		}
		return testResultsStatus;
	}

}
