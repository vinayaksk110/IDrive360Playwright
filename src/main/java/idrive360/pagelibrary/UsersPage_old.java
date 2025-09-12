package idrive360.pagelibrary;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.testng.Assert;

public class UsersPage_old {
	private WebDriver driver = null;
	private Wait<WebDriver> wait = null;

	public UsersPage_old(WebDriver driver) {
		this.driver = driver;
		this.wait = wait;
		PageFactory.initElements(this.driver, this);
	}

	public @FindBy(xpath = "//button[@class='id-btn id-info-btn-frm id-tkn-btn']") WebElement btnCentreAddUser;

	public @FindBy(id = "add-user") WebElement btnBottomAddUser;

	public @FindBy(id = "downloadTemplate") WebElement btnDownloadCSVTemplate;

	public @FindBy(id = "email_ids") WebElement emailIdTxtField;

	public @FindBy(id = "createuser") WebElement btnCreate;
	
	public @FindBy(id = "new-user-cancel") WebElement btnCancelAddUSer;
	
	public @FindBy(id = "new-user-back") WebElement btnBackAddUser;

//  public @FindBy(xpath = "//label[@class='id-check']/span") WebElement chxbxCompanyAdministrator;
//
//	public @FindBy(xpath = "//label[@id='bkUser']/span") WebElement chxbxBackupAdministrator;
//
//	public @FindBy(xpath = "//div[@id='backupUser']//span") WebElement chxbxBackupUser;
//
//	public @FindBy(xpath = "//div[@id='restoreUser']//span") WebElement chxbxRestoreUser;
//
//	public @FindBy(xpath = "//div[@id='backupandrestoreUser']//span") WebElement chxbxBackupandRestoreUser;

	public @FindBy(id = "admin-check") WebElement checkboxCompanyAdministrator;

	public @FindBy(id = "bkUser") WebElement checkboxBackupAdministrator;

	public @FindBy(id = "backupUser") WebElement checkboxBackupUser;

	public @FindBy(id = "restoreUser") WebElement checkboxRestoreUser;

	public @FindBy(id = "backupandrestoreUser") WebElement checkboxBackupandRestoreUser;

	public @FindBy(xpath = "//div[@class='id-alert success id-show']/p") WebElement msgSuccess;

	// Button Delete in Delete user pop-up from User actions menu
	public @FindBy(linkText = "Delete") WebElement btnDelete;

	public WebElement Delete(String usertobedeleted) {
		return this.driver.findElement(By.xpath("//span[contains(@title,'" + usertobedeleted
				+ "')]/parent::div[@class='id-td id-ut-login ']//following-sibling::div[@class='id-td id-ut-action id-ut-mble-action']/ul[@class='id-ut-sctn']/li[@class='id-ut-act id-ut-dlt']/a"));
	}

	// check box in the Delete User pop-up from user actions menu
	public @FindBy(xpath = "//div[@id='deleteCheckBox']//span") WebElement chxbxDelete;

	public WebElement usernameinlisting(String usertobedeleted) {
		return this.driver.findElement(
				By.xpath("//div[@class='id-td id-ut-login ']/span[contains(@title,'" + usertobedeleted + "')]"));

	}

	public WebElement inactiveuserinlisting(String usertoberesendmail) {
		return this.driver.findElement(
				By.xpath("//div[@class='id-td id-ut-login ']/span[contains(@title,'" + usertoberesendmail + "')]"));

	}

	public WebElement ResendMail(String usertoberesendmail) {
		return this.driver.findElement(By.xpath("//span[contains(@title,'" + usertoberesendmail
				+ "')]/parent::div[@class='id-td id-ut-login ']//following-sibling::div[@class='id-td id-ut-action id-ut-mble-action']/ul[@class='id-ut-sctn']/li[@class='id-ut-act id-ut-resendmail id-tkn-btn ']/a"));
	}

	public WebElement ResetPassword(String usertoberesetpassword) {
		return this.driver.findElement(By.xpath("//span[contains(@title,'" + usertoberesetpassword
				+ "')]/parent::div[@class='id-td id-ut-login ']//following-sibling::div[@class='id-td id-ut-action id-ut-mble-action']/ul[@class='id-ut-sctn']/li[@class='id-ut-act id-ut-resetpwd id-tkn-btn']/a"));
	}

	// Button Reset in Reset password pop-up from User actions menu
	public @FindBy(linkText = "Reset") WebElement btnReset;

	public WebElement Disable(String usertobedisabled) {
		return this.driver.findElement(By.xpath("//span[contains(@title,'" + usertobedisabled
				+ "')]/parent::div[@class='id-td id-ut-login ']//following-sibling::div[@class='id-td id-ut-action id-ut-mble-action']/ul[@class='id-ut-sctn']/li[@class='id-ut-act id-tkn-btn id-ut-blk']/a"));
	}

	// button yes in the Disable user pop-up from user action menu
	public @FindBy(id = "blockModalYes") WebElement btnDisableYes;

	public WebElement Enable(String usertobeenabled) {
		return this.driver.findElement(By.xpath("//span[contains(@title,'" + usertobeenabled
				+ "')]/parent::div[@class='id-td id-ut-login ']//following-sibling::div[@class='id-td id-ut-action id-ut-mble-action']/ul[@class='id-ut-sctn']/li[@class='id-ut-act id-tkn-btn id-ut-unblk']/a"));
	}

	// button yes in the Enable user pop-up from user action menu
	public @FindBy(id = "unblockModalYes") WebElement btnEnableYes;
	
	
	public void clickAddUserButtonFromCentre() {
		wait.until(ExpectedConditions.visibilityOf(btnCentreAddUser));
		btnCentreAddUser.click();
	}
	
	public void clickAddUserButtonFromBottom() {
		wait.until(ExpectedConditions.visibilityOf(btnBottomAddUser));
		btnBottomAddUser.click();
	}
	
	public void clickDownloadCSV() {
		wait.until(ExpectedConditions.visibilityOf(btnDownloadCSVTemplate));
		btnDownloadCSVTemplate.click();
	}
	
	public void EnterEmailAddress(String emailAddress) {
		wait.until(ExpectedConditions.visibilityOf(emailIdTxtField));
		emailIdTxtField.click();
		emailIdTxtField.sendKeys(emailAddress);
		System.out.println("Entered the email address: " + emailAddress);
	}
	
	public void clickCreateButton() {
		wait.until(ExpectedConditions.visibilityOf(btnCreate));
		btnCreate.click();
	}
	
	public void clickCancelButtonInAddUserPage() {
		wait.until(ExpectedConditions.visibilityOf(btnCancelAddUSer));
		btnCancelAddUSer.click();
	}
	
	public void clickBackButtonInAddUserPage() {
		wait.until(ExpectedConditions.visibilityOf(btnBackAddUser));
		btnBackAddUser.click();
	}
	
	public void checkCompanyAdminCheckbox() {
		wait.until(ExpectedConditions.visibilityOf(checkboxCompanyAdministrator));
		if(checkboxCompanyAdministrator.isSelected()) {
			System.out.println("Company administrator role is already selected");
		}else {
			checkboxCompanyAdministrator.click();
			//verify if checked
			if(checkboxCompanyAdministrator.isSelected()) {
				System.out.println("Company administrator role is checked");
			}else {
				System.out.println("Company administrator role could not be checked");
				Assert.assertTrue(checkboxCompanyAdministrator.isSelected());
			}
		}
	}
	
	

}