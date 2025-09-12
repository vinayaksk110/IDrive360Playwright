package idrive360.pagelibrary.devices;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;

import idrive360.testbase.Constants;

public class DevicePage {

	private WebDriver driver = null;
	private Wait<WebDriver> wait = null;

	public DevicePage(WebDriver driver, Wait<WebDriver> wait) {
		this.driver = driver;
		this.wait = wait;
		PageFactory.initElements(this.driver, this);
	}

	public @FindBy(id = "selectDeviceSpan0") WebElement chkbxAllDevices;

	public @FindBy(id = "add-device-header-btn") WebElement btnAddDevicesHeader;

	public @FindBy(id = "add-device-close-btn") WebElement btnAddDevicesClose;

	public @FindBy(id = "add-device-info-hover") WebElement btnAddDevicesInfoHover;

	public @FindBy(id = "add-device-encryptn-chkbx") WebElement chxbxAddDevicesEncryption;

	public @FindBy(id = "add-device-windows-tab") WebElement btnAddDevicesWindowsTab;

	public @FindBy(id = "add-device-windows-download") WebElement btnAddDevicesWindowsDownload;

	public @FindBy(id = "add-device-windows-link-copy") WebElement btnAddDevicesWindowsLinkCopy;

	public @FindBy(id = "add-device-mac-tab") WebElement btnAddDevicesMacTab;

	public @FindBy(id = "add-device-mac-download") WebElement btnAdDevicesMacDownload;

	public @FindBy(id = "add-device-mac-link-copy") WebElement btnAddDevicesMacLinkCopy;

	public @FindBy(id = "add-device-linux-tab") WebElement btnAddDevicesLinuxTab;

	public @FindBy(id = "add-device-rpm-copy") WebElement btnAddDevicesRpmCopy;

	public @FindBy(id = "add-device-rpm-download") WebElement btnAddDevicesRpmDownload;

	public @FindBy(id = "add-device-deb-copy") WebElement btnAddDevicesDebCopy;

	public @FindBy(id = "add-device-deb-download") WebElement btnAddDevicesDebDownload;

	public @FindBy(id = "id-card-title-rpm") WebElement btnAddDevicesFedoraCollapse;

	public @FindBy(id = "id-card-title-deb") WebElement btnAddDevicesUbuntuCollapse;

	public @FindBy(id = "id-card-title-mob") WebElement btnAddDevicesMobileCollapse;

	public @FindBy(id = "add-device-app-store-link") WebElement btnAddDevicesMobileAppStoreLink;

	public @FindBy(id = "add-device-play-store-link") WebElement btnAddDevicesMobilePlayStoreLink;

	public @FindBy(id = "add-device-mobile-copy") WebElement btnAddDevicesMobileCopy;

	public @FindBy(id = "add-device-mobile-email-input") WebElement btnAddDevicesMobileEmailInput;

	public @FindBy(id = "add-device-mobile-send-email") WebElement btnAddDevicesMobileSendEmail;

	public @FindBy(id = "id-card-title-win") WebElement btnGroupDeploymentCollapse;

	public @FindBy(id = "grp-deployment-know-more") WebElement btnGroupDeploymentKnowMore;

	public @FindBy(id = "grp-deployment-configID-copy") WebElement btnGroupDeploymentConfigCopy;

	public @FindBy(id = "grp-deployment-download") WebElement btnGroupDeploymentDownload;

	public @FindBy(id = "grp-deployment-copy") WebElement btnGroupDeploymentCopy;

	public @FindBy(xpath = "//div[@class='id-unit-ctnr']/div/div/h3") WebElement subUserNoComputerAddedContent;

	public @FindBy(id = "add-device-no-computer") WebElement btnAddDevices;

	public void openAddDevice() {
		btnAddDevicesHeader.click();
	}

	public void closeAddDecive() {
		btnAddDevicesClose.click();
	}

	public void toggleOwnEncryyption() {
		chxbxAddDevicesEncryption.click();
	}

	public void openWindowsTab() {
		btnAddDevicesWindowsTab.click();
	}

	public void downloadWindows() {
		btnAddDevicesWindowsDownload.click();
	}

	public void copyWindowsLink() {
		btnAddDevicesWindowsLinkCopy.click();
	}

	public void openMacTab() {
		btnAddDevicesMacTab.click();
	}

	public void downloadMac() {
		btnAdDevicesMacDownload.click();
	}

	public void copyMacLink() {
		btnAddDevicesMacLinkCopy.click();
	}

	public void openLinuxTab() {
		btnAddDevicesLinuxTab.click();
	}

	public void copyRpmLink() {
		btnAddDevicesRpmCopy.click();
	}

	public void downloadRpm() {
		btnAddDevicesRpmDownload.click();
	}

	public void copyDebLink() {
		btnAddDevicesDebCopy.click();
	}

	public void downloadDeb() {
		btnAddDevicesDebDownload.click();
	}

	public void toggleFedoraCollapse() {
		btnAddDevicesFedoraCollapse.click();
	}

	public void toggleUbuntuCollapse() {
		btnAddDevicesUbuntuCollapse.click();
	}

	public void toggleMobileCollapse() {
		btnAddDevicesMobileCollapse.click();
	}

	public void openAppStore() {
		btnAddDevicesMobileAppStoreLink.click();
	}

	public void openPlayStore() {
		btnAddDevicesMobilePlayStoreLink.click();
	}

	public void copyMobileToken() {
		btnAddDevicesMobileCopy.click();
	}

	public void inputEmail(String email) {
		btnAddDevicesMobileEmailInput.sendKeys(email);
	}

	public void sendTokenViaEmail() {
		btnAddDevicesMobileSendEmail.click();
	}

	public void toggleGroupDeploymentCollapse() {
		btnGroupDeploymentCollapse.click();
	}

	public void knowMoreGroupDeployment() {
		btnGroupDeploymentKnowMore.click();
	}

	public void copyGroupDeploymentConfigID() {
		btnGroupDeploymentConfigCopy.click();
	}

	public void downloadGroupDeployment() {
		btnGroupDeploymentDownload.click();
	}

	public void copyGroupDeploymentLink() {
		btnGroupDeploymentCopy.click();
	}

	public void clickTopCheckBox() {
		chkbxAllDevices.click();
	}
}
