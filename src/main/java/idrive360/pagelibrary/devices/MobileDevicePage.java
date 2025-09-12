package idrive360.pagelibrary.devices;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Wait;

public class MobileDevicePage {
	
	private WebDriver driver = null;
	private Wait<WebDriver> wait = null;

	public MobileDevicePage(WebDriver driver, Wait<WebDriver> wait) {
		this.driver = driver;
		this.wait = wait;
		PageFactory.initElements(this.driver, this);
	}

	public @FindBy(id = "mobile-search-input") WebElement inputMobileSearch;

	public @FindBy(id = "mobile-search-cross") WebElement btnMobileSearchCross;

	public @FindBy(id = "mobile-search") WebElement btnMobileSearch;

	public @FindBy(id = "name") WebElement btnSortName;

	public @FindBy(id = "emailId") WebElement btnSortEmail;

	public @FindBy(id = "backupStatus") WebElement btnSortBackupStatus;

	public @FindBy(id = "lastBackup") WebElement btnSortLastBackup;

	public @FindBy(id = "restore-mob-ind") WebElement btnMobileRestore;

	public @FindBy(id = "del-mob-ind") WebElement btnMobileDelete;

	public @FindBy(id = "selectDevice") WebElement chxbxSelectDevice;

	public @FindBy(id = "select-mobile-delete") WebElement btnSelectedDeviceDelete;

	public @FindBy(id = "mobile-delete-cross") WebElement btnMobileDeleteCross;

	public @FindBy(id = "select-mobile-cancel") WebElement btnMobileDeleteCancel;

	public @FindBy(id = "select-mobile-confirm") WebElement btnMobileDeleteConfirm;

	public @FindBy(id = "mobile-restore-cross") WebElement btnMobileRestoreCross;

	public @FindBy(id = "mobile-restore-app-store") WebElement btnMobileRestoreAppStore;

	public @FindBy(id = "mobile-restore-play-store") WebElement btnMobileRestorePlayStore;

	public @FindBy(id = "mobile-restore-copy-token") WebElement btnMobileRestoreCopyToken;

	public @FindBy(id = "mobileRestoreEmailInput") WebElement inputMobileRestoreEmail;

	public @FindBy(id = "mobile-restore-send-email") WebElement btnMobileRestoreSendEmail;

	public void inputMobileSearch(String key) {
		inputMobileSearch.sendKeys(key);
	}

	public void crossMobileSearch() {
		btnMobileSearchCross.click();
	}

	public void mobileSearch() {
		btnMobileSearch.click();
	}

	public void toggleSortName() {
		btnSortName.click();
	}

	public void toggleSortEmail() {
		btnSortEmail.click();
	}

	public void toggleSortBackupStatus() {
		btnSortBackupStatus.click();
	}

	public void toggleSortLastBackup() {
		btnSortLastBackup.click();
	}

	public void openMobileRestore() {
		btnMobileRestore.click();
	}

	public void openMobileDelete() {
		btnMobileDelete.click();
	}

	public void selectDevice() {
		chxbxSelectDevice.click();
	}

	public void clickSelectedDeviceDelete() {
		btnSelectedDeviceDelete.click();
	}

	public void clickMobileDeleteCross() {
		btnMobileDeleteCross.click();
	}

	public void clickMobileDeleteCancel() {
		btnMobileDeleteCancel.click();
	}

	public void clickMobileDeleteConfirm() {
		btnMobileDeleteConfirm.click();
	}

	public void closeMobileRestore() {
		btnMobileRestoreCross.click();
	}

	public void openMobileRestoreAppStore() {
		btnMobileRestoreAppStore.click();
	}

	public void openMobileRestorePlayStore() {
		btnMobileRestorePlayStore.click();
	}

	public void clickMobileRestoreCopyToken() {
		btnMobileRestoreCopyToken.click();
	}

	public void inputMobileRestoreEmail(String emails) {
		inputMobileRestoreEmail.sendKeys(emails);
	}

	public void sendMobileRestoreEmail() {
		btnMobileRestoreSendEmail.click();
	}
}
