package idrive360.pagelibrary.devices;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Wait;

public class ComputerDevicePage {

	private WebDriver driver = null;
	private Wait<?> wait = null;

	public @FindBy(xpath = "//span[contains(text(),\"Plan: \")]") WebElement elePlan;

	public @FindBy(id = "selectDeviceSpan0") WebElement chkbxAllDevices;

	public @FindBy(id = "crt-grp-btn") WebElement btnCreateNewGroup;

	public @FindBy(id = "grp-name-input") WebElement inputNewGroupName;

	public @FindBy(id = "crt-popup-cnl") WebElement btnNewGroupCancel;

	public @FindBy(id = "crt-pupup-crs") WebElement btnNewGroupCross;

	public @FindBy(id = "crt-new-grp") WebElement btnNewGroupCreate;

	public @FindBy(id = "add-to-grp-btn") WebElement btnAddToGroup;

	public @FindBy(id = "dlt-device") WebElement btnDelete;

	public @FindBy(id = "stp-all-crrnt-bckup") WebElement btnStopAllCurrentBackups;

	public @FindBy(id = "rmv-bckup-agent") WebElement btnRemoveBackupAgent;

	public @FindBy(id = "view-fltr-btn") WebElement btnFilterDropDown;

	public @FindBy(id = "id-dvce-userblk") WebElement chkbxEmailAddress;

	public @FindBy(id = "id-dvce-status") WebElement chkbxBackupStatus;

	public @FindBy(id = "id-used-space") WebElement chkbxSpaceUsed;

	public @FindBy(id = "id-dvce-lstblk") WebElement chkbxLastBackup;

	public @FindBy(id = "id-dvce-nxtblk") WebElement chkbxNextBackup;

	public @FindBy(id = "id-dvce-grpblk") WebElement chkbxGroupName;

	public @FindBy(id = "sv-view-btn") WebElement btnSaveCurrentView;

	public @FindBy(id = "add-device-no-computer") WebElement btnAddDeviceNoComputer;

	public @FindBy(id = "group-delete") WebElement btnGroupDelete;

	public @FindBy(id = "group-delete-cross") WebElement btnGroupDeleteCross;

	public @FindBy(id = "group-delete-cancel") WebElement btnGroupDeleteCancel;

	public @FindBy(id = "group-delete-confirm") WebElement btnGroupDeleteConfirm;

	public @FindBy(id = "group-rename") WebElement btnGroupRename;

	public @FindBy(id = "txt-box") WebElement inputGroupRename;

	public @FindBy(id = "group-rename-cross") WebElement btnGroupRenameCross;

	public @FindBy(id = "group-rename-cancel") WebElement btnGroupRenameCancel;

	public @FindBy(id = "group-rename-confirm") WebElement btnGroupRenameConfirm;

	public @FindBy(id = "group-collapse") WebElement btnGroupCollapse;

	public @FindBy(id = "computer-search-input") WebElement inputComputerSearch;

	public @FindBy(id = "computer-search-cross") WebElement btnComputerSearchCross;

	public @FindBy(id = "computer-search") WebElement btnComputerSearch;

	public @FindBy(id = "name") WebElement btnSortName;

	public @FindBy(id = "emailId") WebElement btnSortEmail;

	public @FindBy(id = "backupStatus") WebElement btnSortBackupStatus;

	public @FindBy(id = "usedSpace") WebElement btnSortSpaceUsed;

	public @FindBy(id = "lastBackup") WebElement btnSortLastBackup;

	public @FindBy(id = "nextBackup") WebElement btnSortNextBackup;

	public @FindBy(id = "groupName") WebElement btnSortGroupName;

	public @FindBy(id = "comp-settings") WebElement btnComputerSettingsDropdown;

	public @FindBy(id = "comp-remote-manage") WebElement btnComputerRemoteManage;

	public @FindBy(id = "comp-rmv-bckup-agent") WebElement btnComputerRemoveBackupAgent;
	
	
	public @FindBy(xpath = "//div[@id='removeckupagentModal']//a[@class='id-btn id-warning-btn-drk'][normalize-space()='Remove']") WebElement btnremove;
	
	public @FindBy(id = "comp-rename") WebElement btnComputerRename;

	public @FindBy(id = "comp-block") WebElement btnComputerBlock;

	public @FindBy(xpath ="//a[@onclick=\"closeModal(event, 'blockSingleComputerModal'); openModal(event, 'blockSingleComputerCnfrmModal')\"]") WebElement btnblock;
	
	public @FindBy(id = "comp-unblock") WebElement btnComputerUnblock;

	public @FindBy(xpath = "//a[@onclick=\"closeModal(event, 'unblockSingleComputerCnfrmModal')\"][normalize-space()='Unblock']") WebElement btnunblock;
	
	public @FindBy(id = "comp-delete") WebElement btnComputerDelete;

	public @FindBy(xpath = "//*[@id='devDtlRow-All Computers-0']//ancestor::div[@class='id-rw-col id-dvce-uname']//following-sibling::div[@id='devDtlRowAction-All Computers-0']/ul//li[@class='id-ut-act']/button") WebElement btnSettingsComputer;

	public @FindBy(id = "id-grp-drp-icon0") WebElement btnExpandAllComputer;

	
	public @FindBy(xpath ="//div[@class='id-alert success id-show']" ) WebElement msgSuccess;
	
	public WebElement btnGroupExpand(String groupName) {
		return this.driver.findElement(By.xpath("//h2[contains(text(),'" + groupName
				+ "')]//parent::div[@class='id-device-grphed']//div[@class='id-grp-drp-ico']"));
	}

	public @FindBy(id = "id-username") WebElement InputRenameField;

	public @FindBy(xpath = "//a[@class='id-btn id-info-btn'][normalize-space()='Save']") WebElement btnSaveRenameComputer;
	
	
	
	public ComputerDevicePage(WebDriver driver, Wait<WebDriver> wait) {
		this.driver = driver;
		this.wait = wait;
		PageFactory.initElements(this.driver, this);
	}

	
	
	public void clickTopCheckBox() {
		chkbxAllDevices.click();
	}

	public void clickFilterDropDown() {
		btnFilterDropDown.click();
	}

	public void clickSaveCurrentView() {
		btnSaveCurrentView.click();
	}

	public void clickCreateNewGroup() {
		btnCreateNewGroup.click();
	}

	public void enterGroupName(String groupName) {
		inputNewGroupName.sendKeys(groupName);
	}

	public void clickCreatePopupCreateBtn() {
		btnNewGroupCreate.click();
	}

	public void clickCreatePopupCancelBtn() {
		btnNewGroupCancel.click();
	}

	public void clickCreatePopupCrossBtn() {
		btnNewGroupCross.click();
	}

	public void clickAddToGroup() {
		btnAddToGroup.click();
	}

	public void clickDelete() {
		btnDelete.click();
	}

	public void clickStopAllCurrentBackups() {
		btnStopAllCurrentBackups.click();
	}

	public void clickRemoveBackupAgent() {
		btnRemoveBackupAgent.click();
	}

	public void openAddDeviceNoComputer() {
		btnAddDeviceNoComputer.click();
	}

	public void crossGroupDelete() {
		btnGroupDeleteCross.click();
	}

	public void cancelGroupDelete() {
		btnGroupDeleteCancel.click();
	}

	public void confirmGroupDelete() {
		btnGroupDeleteConfirm.click();
	}

	public void openGroupRename() {
		btnGroupRename.click();
	}

	public void inputGroupRename(String name) {
		inputGroupRename.sendKeys(name);
	}

	public void crossGroupRename() {
		btnGroupRenameCross.click();
	}

	public void cancelGroupRename() {
		btnGroupRenameCancel.click();
	}

	public void confirmGroupRename() {
		btnGroupRenameConfirm.click();
	}

	public void toggleGroupCollapse() {
		btnGroupCollapse.click();
	}

	public void inputComputerSearch(String key) {
		inputComputerSearch.sendKeys(key);
	}

	public void crossComputerSearch() {
		btnComputerSearchCross.click();
	}

	public void computerSearch() {
		btnComputerSearch.click();
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

	public void toggleSortSpaceUsed() {
		btnSortSpaceUsed.click();
	}

	public void toggleSortLastBackup() {
		btnSortLastBackup.click();
	}

	public void toggleSortNextBackup() {
		btnSortNextBackup.click();
	}

	public void toggleSortGroupName() {
		btnSortGroupName.click();
	}

	public void openComputerSettingsDropdown() {
		btnComputerSettingsDropdown.click();
	}

	public void openComputerRemoteManage() {
		btnComputerRemoteManage.click();
	}

	public void openComputerRemoveBackupAgent() {
		btnComputerRemoveBackupAgent.click();
	}

	public void openComputerRename() {
		btnComputerRename.click();
	}

	public void openComputerBlock() {
		btnComputerBlock.click();
	}

	public void openComputerUnblock() {
		btnComputerUnblock.click();
	}

	public void openComputerDelete() {
		btnComputerDelete.click();
	}

}
