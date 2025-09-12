package idrive360.pagelibrary;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.testng.Assert;

import idrive360.data.UserCredentials;
import idrive360.managers.PageObjectManager;
import idrive360.testbase.TestBase;
import idrive360.utilities.TestResultsStatus;

public class CompaniesPage extends TestBase {
	private WebDriver driver = null;
	private Wait<WebDriver> wait = null;
	UserCredentials userCredentials = null;
	private Actions action = null;
	private PageObjectManager pageObjectManager = null;

	public CompaniesPage(WebDriver driver, Wait<WebDriver> wait) {
		this.driver = driver;
		this.wait = wait;
		PageFactory.initElements(this.driver, this);
	}

	// Overview in LHS
	public @FindBy(xpath = "//li[@class='id-ico-overview active']") WebElement overView;

	// Units in LHS
	public @FindBy(xpath = "//li[@class='id-ico-Company none']") WebElement Companies;

	// Add Company center button- Add Company for the first time
	public @FindBy(xpath = "//button[@class='id-btn id-info-btn-frm id-tkn-btn']") WebElement addCompanyCentreButton;

	// Add Company in the Overview page
	public @FindBy(linkText = "Add Unit") WebElement overviewAddCompanyButton;

	// Add Company in the Header add option
	public @FindBy(xpath = "//a[contains(text(),'Add Unit')]") WebElement headerAddCompany;

	// Add Company button when few units are added in the Main Company/other Company
	public @FindBy(xpath = "//button[@class='id-btn id-add-icon id-tkn-btn']") WebElement addCompanyButtonAtBottom;

	// Existing unit under main unit/ other unit
	public @FindBy(xpath = "//div[@class='id-td id-ut-name id-ur-icn']") WebElement subCompany;

	// UnitName field while adding Unit
	public @FindBy(id = "unit_name") WebElement companyNameField;

	public @FindBy(name = "email") WebElement createAdm_EmailField;

	public @FindBy(id = "fname") WebElement firstNameField;

	public @FindBy(id = "sname") WebElement lastNameField;

	public @FindBy(id = "createbutton") WebElement createButton;

	public @FindBy(xpath = "//button[@class='id-btn id-btn-frm']") WebElement cancelCreateCompany;

	public @FindBy(xpath = "id-addunit-wrpr-right") WebElement outsideCreateCompanyFrame;

	// Toast msg after adding unit
	public @FindBy(xpath = "//div[@class='id-alert success id-show']/p") WebElement toastSuccessmsg;

	// Existing unit Edit icon
	public @FindBy(id = "editUnitname") WebElement companyEditIcon;

	// Existing unit Name field
	public @FindBy(id = "idUnitname") WebElement editCompanyNameField;

	// Save button after updating unit name
	public @FindBy(id = "updateUnitname") WebElement saveButton;

	// Toast msg after updating existing unit name
	public @FindBy(linkText = "Unit updated successfully") WebElement companyNameUpdatedToastmsg;

	// Slider unit-Class icon
	public @FindBy(xpath = "//div[@class='id-sidebar-clsblk']/a[@class='id-close-sidebar id-tooltip-left']") WebElement closeIcon;

	public @FindBy(name = "pattern") WebElement searchFieldUnitPage;

	public @FindBy(xpath = "//div[@class='id-pg-srch id-pg-srch-new']/button[@class='id-srch-ico']") WebElement searchIconCompanypage;

	// Hover over - unit name
	public WebElement existingCompanyName(String companyName) {
		return this.driver.findElement(By.xpath("//span[@title='" + companyName + "']"));
	}

	// Hover over- View icon
	public WebElement viewIcon(String companyName) {
		return this.driver.findElement(By.xpath("//span[@title='" + companyName
				+ "']//parent::div[@class='id-td id-ut-name id-ur-icn']//following-sibling::div[@class='id-td id-ut-action id-ut-mble-action']//ul//li[@class='id-ut-act id-ut-edt']/a"));
	}

	// Hover hover to Delete icon
	public WebElement deleteIcon(String companyName) {
		return this.driver.findElement(By.xpath("//span[@title='" + companyName
				+ "']//parent::div[@class='id-td id-ut-name id-ur-icn']//following-sibling::div[@class='id-td id-ut-action id-ut-mble-action']//ul//li[@class='id-ut-act id-ut-dlt id-tkn-btn']"));
	}

	// Hover hover to Disable icon
	public WebElement disableIcon(String companyName) {
		return this.driver.findElement(By.xpath("//span[@title='" + companyName
				+ "']//parent::div[@class='id-td id-ut-name id-ur-icn']//following-sibling::div[@class='id-td id-ut-action id-ut-mble-action']//ul//li[@class='id-ut-act id-ut-adduser id-tkn-btn']"));
	}

	// Hover hover to User List icon
	public WebElement userListIcon(String companyName) {
		return this.driver.findElement(By.xpath("//span[@title='" + companyName
				+ "']//parent::div[@class='id-td id-ut-name id-ur-icn']//following-sibling::div[@class='id-td id-ut-action id-ut-mble-action']//ul//li[@class='id-ut-act id-ut-vew id-tkn-btn']/a"));
	}

	// Hover over - Add user icon
	public WebElement addUserIcon(String companyName) {
		return this.driver.findElement(By.xpath("//span[@title='" + companyName
				+ "']//parent::div[@class='id-td id-ut-name id-ur-icn']//following-sibling::div[@class='id-td id-ut-action id-ut-mble-action']//ul//li[@class='id-ut-act id-ut-adduser id-tkn-btn']/button"));
	}

	// Hover over - Go to the Backup console icon
	public WebElement goToTheBackupConsoleIcon(String companyName) {
		return this.driver.findElement(By.xpath("//span[@title='" + companyName
				+ "']//parent::div[@class='id-td id-ut-name id-ur-icn']//following-sibling::div[@class='id-td id-ut-action id-ut-mble-action']//ul//button[@class='id-devcieview-opt id-tooltip']"));
	}

	public @FindBy(id = "deleteModal") WebElement deletePopup;

	public @FindBy(id = "trustedDeviceModal") WebElement trustPopup;

	public @FindBy(xpath = "//div[@id='delModal']//label[@class='id-check']") WebElement deletePopupCheckbox;

	public @FindBy(id = "disablemodal") WebElement disablePopup;

	public @FindBy(xpath = "//span[@class='id-checkmark']") WebElement checkBox;

	public @FindBy(xpath = "//div[@class='id-pop-footer']/a[@class='id-btn id-warning-btn-drk']") WebElement deleteButton;

	public @FindBy(id = "unitdisbtn") WebElement popupDisableButton;

	public @FindBy(xpath = "//div[@class='id-alert success id-hide']") WebElement companyNameDeleteToastmsg;

	public @FindBy(xpath = "//div[@class='id-inp-error']") WebElement errorMsgBlankCompanyName;

	// Methods
	// --------------------------------------------------------------------------

	public void scrollIntoview(WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView(true);", element);
	}

	public void clickLHSMenuCompanies() {
		Companies.click();
	}

	public void clickcenterAddCompany() {
		addCompanyCentreButton.click();
	}

	public void enterCompanyName(String companyName) {
		companyNameField.sendKeys(companyName);
	}

	public void clickCompanyNameField() {
		companyNameField.click();
	}

	public void clickAdminEmailField() {
		createAdm_EmailField.click();
	}

	public void enterAdminEmailField(String emailidField) {
		createAdm_EmailField.sendKeys(emailidField);
	}

	public void clickOverviewAddCompanyButton() {
		overviewAddCompanyButton.click();
	}

	public void enterSearchFieldUnitPage(String companyName) {
		searchFieldUnitPage.sendKeys(companyName);
	}

	public void clickSearchIconoftheSearchFiledUnit() {
		searchIconCompanypage.click();
	}

	public void enterFirstNameField(String firstname) {
		firstNameField.sendKeys(firstname);
	}

	public void enterLastNameField(String lastname) {
		lastNameField.sendKeys(lastname);
	}

	public void clickCreateUnitButton() {
		createButton.click();
	}

	public void clickCancelbuttonforCreateUnit() {
		cancelCreateCompany.click();
	}

	public void clickOutsideCreateUnitFrame() {
		outsideCreateCompanyFrame.click();
	}

	public void headerAddUnit() {
		headerAddCompany.click();
	}

	public void subUnitUnderMainUnit() {
		subCompany.click();
	}

	public void clickAddUnitButtonAtBottom() {
		addCompanyButtonAtBottom.click();
	}

	public void viewIconClick(String companyName) {
		viewIcon(companyName).click();
	}

	public void addUserIconClick(String companyName) {
		addUserIcon(companyName).click();
	}

	public void viewUnitEditIcon() {
		companyEditIcon.click();
	}

	public void goToTheBackupConsoleIconClick(String companyName) {
		goToTheBackupConsoleIcon(companyName).click();
	}

	public void unitNameEditField(String EditedUnitName) {
		editCompanyNameField.sendKeys(EditedUnitName);
	}

	public void saveButton() {
		saveButton.click();
	}

	public void sliderCloseIcon() {
		closeIcon.click();
	}

	public void deleteIconClick(String companyName) {
		deleteIcon(companyName).click();
	}

	public void deletePopupCheckBox() {
		deletePopupCheckbox.click();
	}

	public void deleteButtonClick() {
		deleteButton.click();
	}

	public void disableButtonClick() {
		popupDisableButton.click();
	}

	public void userListIconClick(String companyName) {
		userListIcon(companyName).click();
	}

	public TestResultsStatus waitForCentreAddCompanyButtonAndClik(TestResultsStatus testResultsStatus) {
		try {
			wait.until(ExpectedConditions.visibilityOf(addCompanyCentreButton));
			clickcenterAddCompany();
			log.info("Centre Add Company button clicked");
		} catch (Exception e) {
			String errorMessage = "Error clicking company button from centre\n";
			System.out.println(errorMessage);
			e.printStackTrace();
			testResultsStatus.setTestResult("Fail");
			testResultsStatus.setTestResultComment(errorMessage + e.getMessage());
			Assert.assertTrue(false);
		}
		return testResultsStatus;
	}

	public void waitForBottomAddCompanyButtonAndClick() {
		wait.until(ExpectedConditions.visibilityOf(addCompanyButtonAtBottom));
		clickAddUnitButtonAtBottom();
		log.info("Add Company button clicked at bottom");
	}

	public void clickOnExistingCompany(String companyName) {
		wait.until(ExpectedConditions.visibilityOf(addCompanyButtonAtBottom));
		wait.until(ExpectedConditions.visibilityOf(existingCompanyName(companyName)));
		log.info("Company is visible");
		existingCompanyName(companyName).click();
		log.info("Clicked on company " + companyName);
	}

	public TestResultsStatus enterCompanyDetailsAndClickCreate(UserCredentials userCredentials, TestResultsStatus testResultsStatus) {
		try {
			wait.until(ExpectedConditions.visibilityOf(companyNameField));
			enterCompanyName(userCredentials.companyName);
			log.info("Company name entered is :" + userCredentials.companyName);
			enterAdminEmailField(userCredentials.adminEmail);
			log.info("admin email entered is :" + userCredentials.adminEmail);
			enterFirstNameField(userCredentials.firstName);
			log.info("admin email entered is :" + userCredentials.firstName);
			enterLastNameField(userCredentials.lastName);
			log.info("admin email entered is :" + userCredentials.lastName);
			wait.until(ExpectedConditions.visibilityOf(createButton));
			clickCreateUnitButton();
			log.info("Create button clicked");
		}catch (Exception e) {
			String errorMessage = "Error entering company details\n";
			System.out.println(errorMessage);
			e.printStackTrace();
			testResultsStatus.setTestResult("Fail");
			testResultsStatus.setTestResultComment(errorMessage + e.getMessage());
			Assert.assertTrue(false);
		}
		return testResultsStatus;
	}

	public void navigateToCompany(String companyName) {
		wait.until(ExpectedConditions.visibilityOf(addCompanyButtonAtBottom));
		scrollIntoview(existingCompanyName(companyName));
		action = new Actions(driver);
		wait.until(ExpectedConditions.visibilityOf(existingCompanyName(companyName)));
		action.moveToElement(existingCompanyName(companyName)).perform();
		log.info("Mouse moved to company");
	}

	public void navigateToDeleteIconForCompany(String companyName) {
		action = new Actions(driver);
		wait.until(ExpectedConditions.visibilityOf(deleteIcon(companyName)));
		log.info("Delete button is displayed");
		action.moveToElement(deleteIcon(companyName)).perform();
		log.info("mouse moved to delete button");
	}

	public void navigateToDisableIconForCompany(String companyName) {
		action = new Actions(driver);
		wait.until(ExpectedConditions.visibilityOf(disableIcon(companyName)));
		log.info("Disable button is displayed");
		action.moveToElement(disableIcon(companyName)).perform();
		log.info("mouse moved to disable button");
	}

	public void navigateToAddUserIconForCompany(String companyName) {
		action = new Actions(driver);
		wait.until(ExpectedConditions.visibilityOf(addUserIcon(companyName)));
		log.info("Add user button is displayed");
		action.moveToElement(addUserIcon(companyName)).perform();
		log.info("mouse moved to Add user button");
	}

	public void navigateToBackupConsole(String companyName) {
		pageObjectManager = new PageObjectManager(driver, wait);
		wait.until(ExpectedConditions.visibilityOf(pageObjectManager.getCompaniesPage().subCompany));
		log.info("Sub companies are displayed");
		wait.until(ExpectedConditions.visibilityOf(existingCompanyName(companyName)));
		log.info("sub company " + companyName + " displayed in companies list.");
		Actions a1 = new Actions(driver);
		a1.moveToElement(existingCompanyName(companyName)).perform();
		a1.moveToElement(goToTheBackupConsoleIcon(companyName));
		log.info("mouse moved to element backup console");
	}

	public void clickBackupConsole(String companyName) {
		goToTheBackupConsoleIconClick(companyName);
		log.info("Clicked Go to backup console icon");
	}

	public void clickAddUser(String companyName) {
		addUserIcon(companyName).click();
		log.info("Add user button clicked for company");
	}

	public void deleteCompany(String companyName) {
		deleteIcon(companyName).click();
		log.info("Delete button clicked for company");
		wait.until(ExpectedConditions.visibilityOf(deletePopup));
		deletePopupCheckBox();
		deleteButtonClick();
		log.info("Clicked Delete button");
	}

	public void disableCompany(String companyName) {
		disableIcon(companyName).click();
		log.info("Disable button clicked for company");
		wait.until(ExpectedConditions.visibilityOf(disablePopup));
		disableButtonClick();
		log.info("Clicked Disable button");
	}

}
