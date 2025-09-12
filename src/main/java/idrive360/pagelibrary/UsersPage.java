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
import idrive360.managers.PageObjectManager;
import idrive360.testbase.TestBase;
import idrive360.utilities.TestResultsStatus;

public class UsersPage extends TestBase {
	private WebDriver driver = null;
	private Wait<WebDriver> wait = null;
	private PageObjectManager pageObjectManager = null;
	private Actions action = null;

	public UsersPage(WebDriver driver, Wait<WebDriver> wait) {
		this.driver = driver;
		this.wait = wait;
		PageFactory.initElements(this.driver, this);
	}

	// LHS USer open
	public @FindBy(xpath = "//span[normalize-space()='Users']") WebElement lhsUSerOption;

	// Add user center button- Add user for the first time
	public @FindBy(xpath = "//a[@class='id-btn id-info-btn-frm id-tkn-btn']") WebElement centreAddUser;

	// Existing User-Appearance
	public @FindBy(xpath = "//div[@class='id-td id-ut-name id-ur-icn id-admin-icn']") WebElement userListappear;

	// Add user button below company Administrator
	public @FindBy(xpath = "//button[@class='id-btn-opt id-btn-adduser']") WebElement bottomAddUserButton;

	// Email id field for the User- Add user
	public @FindBy(id = "email_ids") WebElement emailFieldAddUser;

	// By Default selected company name in the Add user(s) to company dropdwon
	public @FindBy(id = "adduserlist") WebElement byDefaultUserInDropdown;

	// By Default company admin is selected in the checkbox for Roles
	public @FindBy(xpath = "//label[contains(text(),'Company administrator')]//span") WebElement byDefaultCompanyAdmInRolesChecked;

	// Admin checkbox in add user page
	public @FindBy(id = "admin-check") WebElement companyAdminRole;

	// Admin checkbox in edit/view slider
	public @FindBy(id = "adminusr") WebElement companyAdministratorRole;

	// Create User button
	public @FindBy(id = "createuser") WebElement createUserButton;

	// User has been added- Toast success message
	public @FindBy(xpath = "//div[@class='id-alert success id-show']") WebElement userCreatedSuccessToastmsg;

	// Add user button below Company Admin in the Default company
	public @FindBy(xpath = "//div[@class='id-user-add ']/a") WebElement addUserButtonInDefaultCompany;

	// Company Admin user reflected
	public @FindBy(xpath = "//div[@class='id-td id-ut-name id-ur-icn id-me-icn id-admin-icn']") WebElement companyAdminRoleVisible;

	// sub company in the Add user to company Box from Dropdown
	public WebElement companyNameinlisting(String companyName) {
		return this.driver.findElement(
				By.xpath("//div[@class='id-deviceLists-grp']//div[@class='id-deviceLists closed']//span[@title='"
						+ companyName + "']"));
	}

	// Disable button in the Disable User pop-up
	public @FindBy(id = "blockModalYes") WebElement btnDisableYes;

	// Enable button in the Enable User pop-up
	public @FindBy(id = "unblockModalYes") WebElement btnEnableYes;

	// Reset button in the Reset Password pop-up
	public @FindBy(xpath = "//div[@id='resetpwdModal']//a[@class='id-btn id-info-btn']") WebElement btnReset;

	// user row
	public WebElement userRow(String userName) {
		return this.driver.findElement(By.xpath("//div[@class='id-td id-ut-login']/span[@title='" + userName + "']"));
	}

	// user status
	public WebElement userStatus(String userName) {
		return this.driver.findElement(By.xpath("//span[@title='" + userName
				+ "']//parent::div[@class='id-td id-ut-login']//preceding-sibling::div[@class='id-td id-ut-status']/span"));
	}

	// resend email button
	public WebElement invitedUserButtonResendMail(String userName) {
		return this.driver.findElement(By.xpath("//span[@title='" + userName
				+ "']//parent::div[@class='id-td id-ut-login']//following-sibling::div[@class='id-td id-ut-action id-ut-mble-action']//li//button[contains(text(),' Resend mail ')]"));
	}

	// Invited user view button
	public WebElement invitedUserButtonView(String userName) {
		return this.driver.findElement(By.xpath("//span[@title='" + userName
				+ "']//parent::div[@class='id-td id-ut-login']//following-sibling::div[@class='id-td id-ut-action id-ut-mble-action']//li//button[contains(text(),' View ')]"));
	}

	// User Delete button
	public WebElement userButtonDelete(String userName) {
		return this.driver.findElement(By.xpath("//span[@title='" + userName
				+ "']//parent::div[@class='id-td id-ut-login']//following-sibling::div[@class='id-td id-ut-action id-ut-mble-action']//li//button[contains(text(),' Delete ')]"));
	}

	// Reset password button
	public WebElement activeUserButtonResetPassword(String userName) {
		return this.driver.findElement(By.xpath("//span[@title='" + userName
				+ "']//parent::div[@class='id-td id-ut-login']//following-sibling::div[@class='id-td id-ut-action id-ut-mble-action']//li//button[contains(text(),' Reset Password ')]"));
	}

	// User view button
	public WebElement userButtonView(String userName) {
		return this.driver.findElement(By.xpath("//span[@title='" + userName
				+ "']//parent::div[@class='id-td id-ut-login']//following-sibling::div[@class='id-td id-ut-action id-ut-mble-action']//li//button[contains(text(),' View ')]"));
	}

	// Disable button in Disable Pop up
	public WebElement activeUserButtonDisable(String userName) {
		return this.driver.findElement(By.xpath("//span[@title='" + userName
				+ "']//parent::div[@class='id-td id-ut-login']//following-sibling::div[@class='id-td id-ut-action id-ut-mble-action']//li//button[contains(text(),' Disable ')]"));
	}

	// Enable button in Enable Pop up
	public WebElement activeUserButtonEnable(String userName) {
		return this.driver.findElement(By.xpath("//span[@title='" + userName
				+ "']//parent::div[@class='id-td id-ut-login']//following-sibling::div[@class='id-td id-ut-action id-ut-mble-action']//li//button[contains(text(),' Enable ')]"));
	}

	// Delete User popup
	public @FindBy(id = "deleteuser") WebElement deleteUserpopup;

	// Delete User popup checkbox
	public @FindBy(id = "deleteCheckBox") WebElement checkboxDeleteUser;

	// Delete User popup close button
	public @FindBy(xpath = "//div[@class='id-pop-header']//h2[contains(text(),'Delete User')]//following-sibling::a[@class='close id-close']") WebElement closeDeleteUserpopup;

	// Delete User button in popup
	public @FindBy(xpath = "//a[@class='id-btn id-warning-btn-drk']") WebElement buttonDeleteUserInPopup;

	// Disable User popup
	public @FindBy(id = "disableuser") WebElement popupDisableUser;

	// Enable User popup
	public @FindBy(id = "enableuser") WebElement popupEnableUser;

	// Reset Password popup
	public @FindBy(id = "resetpwdModal") WebElement popupResetPassword;

	// User Name in the view slider
	public @FindBy(id = "id-usr-name") WebElement userNameInViewSlider;

	// Edit Icon for updating first and last name in the view slider
	public @FindBy(id = "editProfile") WebElement editFirstAndLastNameIcon;

	// First Name field in the view slider once clicked on edit
	public @FindBy(id = "idUsrname") WebElement inputFirstName;

	// Last Name field in the view slider once clicked on edit
	public @FindBy(id = "iduserlastName") WebElement inputLastName;

	// Save Button for updating the first and last name in the view slider
	public @FindBy(id = "updateUser") WebElement btnSaveFirstAndLastName;

	// Roles and Permissions in the view slider
	public @FindBy(xpath = "//div[@class='id-form-row']//h2[contains(text(),  'Roles and Permissions')]") WebElement RolesAndPermissions;

	// Edit icon for changing the roles and permissions
	public @FindBy(id = "editRole") WebElement editRoleIcon;

	// Button save for updating the role in the view slider
	public @FindBy(id = "updateRole") WebElement btnSaveRolesAndPermissions;

	// Success Message in the view slider
	public @FindBy(xpath = "//div[@id='id-card-success' and @style = 'display: block;']/p") WebElement userDetailsUpdatedSucessMessage;

	// Common Edit icon in the view slider for updating first,last name & user roles
	// & permissions
	public @FindBy(id = "editUser") WebElement editUserIcon;

	// View Slider
	public @FindBy(id = "edituser-head") WebElement viewSlider;

	// Save Changes button in the view slider
	public @FindBy(xpath = "//button[@class = 'id-btn id-primary-btn id-tkn-btn']") WebElement btnSaveChanges;

	// Company dropdown
	public @FindBy(xpath = "//a[@class = 'id-unit-dropdown']") WebElement companyDropdown;

	// Company dropdown
	public @FindBy(id = "id-unitlists") WebElement companyDropdownlisting;

	// company name
	public WebElement companyListSelectCompany(String companyName) {
		return this.driver.findElement(By.xpath(
				"//li[@class='id-deviceListing-link']//div[@id='unitDropddown']//div[starts-with(@id,('childUnit'))]/span[@title='"
						+ companyName + "']"));
	}

	// Methods
	// --------------------------------------------------------------------------

	public void clickcenterAddUser(TestResultsStatus testResultsStatus) {
		try {
			wait.until(ExpectedConditions.visibilityOf(centreAddUser));
			centreAddUser.click();
			log.info("Add user button clicked from centre in the Default company");
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	public TestResultsStatus clickBottomAddUserButton(TestResultsStatus testResultsStatus) {
		try {
			wait.until(ExpectedConditions.visibilityOf(bottomAddUserButton));
			bottomAddUserButton.click();
			log.info("Add user button clicked below existing user in the Default company");
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

	public TestResultsStatus clickCreateUserButton(TestResultsStatus testResultsStatus) {
		try {
			log.info("Clicking on Create button");
			createUserButton.click();
			log.info("Create button clicked");
		} catch (Exception e) {
			String errorMessage = "Error while clicking create user button\n";
			log.error(errorMessage);
			e.printStackTrace();
			testResultsStatus.setTestResult("Fail");
			testResultsStatus.setTestResultComment(errorMessage + e.getMessage());
			Assert.assertTrue(false);
		}
		return testResultsStatus;
	}

	public void clickAddUserButtonBelowCompanyAdminInDefaultCompany() {
		addUserButtonInDefaultCompany.click();
	}

	public TestResultsStatus uncheckCompanyAdminRoleCheckbox(TestResultsStatus testResultsStatus) {
		try {
			byDefaultCompanyAdmInRolesChecked.click();
		} catch (Exception e) {
			String errorMessage = "Error while clicking default company admin role checkbox\n";
			log.error(errorMessage);
			e.printStackTrace();
			testResultsStatus.setTestResult("Fail");
			testResultsStatus.setTestResultComment(errorMessage + e.getMessage());
			Assert.assertTrue(false);
		}
		return testResultsStatus;
	}

	public WebElement role(String userRole) {
		return this.driver.findElement(By.xpath("//label[contains(text(),'" + userRole + "')]//span"));
	}

	public void checkRolesFetchedFromExcel(String userRole) {
		role(userRole).click();
	}

	// clicking to the Company name box
	public void clickAddUserToCompanybox() {
		byDefaultUserInDropdown.click();
	}

	public TestResultsStatus enterEmailIds(String emailID, TestResultsStatus testResultsStatus) {
		try {
			wait.until(ExpectedConditions.visibilityOf(emailFieldAddUser));
			emailFieldAddUser.sendKeys(emailID);
			log.info("Email id entered for Adding user is : " + emailID);
		} catch (Exception e) {
			String errorMessage = "Error while entering email id in text field\n";
			log.error(errorMessage);
			e.printStackTrace();
			testResultsStatus.setTestResult("Fail");
			testResultsStatus.setTestResultComment(errorMessage + e.getMessage());
			Assert.assertTrue(false);
		}
		return testResultsStatus;
	}

	public void updateFirstName(String firstName) {
		wait.until(ExpectedConditions.visibilityOf(inputFirstName));
		inputFirstName.clear();
		log.info("Cleared the input first name field");
		inputFirstName.sendKeys(firstName);
		log.info("First name to be updated is : " + firstName);
		btnSaveChanges.click();
		log.info("Clicked on save changes button");
	}

	public void updateLastName(String lastName) {
		wait.until(ExpectedConditions.visibilityOf(inputLastName));
		inputLastName.clear();
		log.info("Cleared the input last name field");
		inputLastName.sendKeys(lastName);
		log.info("Last name to be updated is : " + lastName);
		btnSaveChanges.click();
		log.info("Clicked on save changes button");
	}

	public String waitForMessage() {
		pageObjectManager = new PageObjectManager(driver, wait);
		log.info("Waiting for common message");
		wait.until(ExpectedConditions.visibilityOf(pageObjectManager.getHeaderFunctionalityPage().commonmessage));
		String message = pageObjectManager.getHeaderFunctionalityPage().commonmessage.getText();
		log.info("Message from toast message is : " + message);
		return message;
	}

	public String waitForMessageInViewSlider() {
		log.info("Waiting for success message");
		wait.until(ExpectedConditions.visibilityOf(userDetailsUpdatedSucessMessage));
		String message = userDetailsUpdatedSucessMessage.getText();
		log.info("Message from toast Message is: " + message);
		return message;
	}

	public void waitForBottomAddUserButton() {
		wait.until(ExpectedConditions.visibilityOf(bottomAddUserButton));
		log.info("Bottom add user button is visible");
	}

	public void navigateToUser(String emailID) {
		scrollIntoview(userRow(emailID));
		action = new Actions(driver);
		wait.until(ExpectedConditions.visibilityOf(userRow(emailID)));
		action.moveToElement(userRow(emailID)).perform();
		log.info("Mouse moved to user");
		log.info("Found the user to change the Role");
	}

	public void navigateToDeleteIconForUser(String emailID) {
		action = new Actions(driver);
		wait.until(ExpectedConditions.visibilityOf(userButtonDelete(emailID)));
		log.info("Delete button is displayed");
		action.moveToElement(userButtonDelete(emailID)).perform();
		log.info("mouse moved to delete button");
	}

	public void clickDeleteIconForUser(String emailID) {
		userButtonDelete(emailID).click();
		log.info("Delete button clicked for user");
	}

	public void clickDisableIcon(String emailID) {
		activeUserButtonDisable(emailID).click();
		log.info("Disable icon clicked");
	}

	public void clickEnableIcon(String emailID) {
		activeUserButtonEnable(emailID).click();
		log.info("Enable icon clicked");
	}

	public void clickResetPasswordIcon(String emailID) {
		activeUserButtonResetPassword(emailID).click();
		log.info("Reset Password icon clicked");
	}

	public void clickResendMailIconForInvitedUser(String emailID) {
		invitedUserButtonResendMail(emailID).click();
		log.info("Clicked on Resend mail icon");
	}

	public void clickUserViewIcon(String emailID) {
		userButtonView(emailID).click();
		log.info("Clicked on view icon");
	}

	public String VerifyUserStatus(String emailID) {
		wait.until(ExpectedConditions.visibilityOf(bottomAddUserButton));
		String status = wait.until(ExpectedConditions.visibilityOf(userStatus(emailID))).getText();
		log.info("User status is : " + status);
		return status;
	}

	public void deleteUser(String emailID) {
		clickDeleteIconForUser(emailID);
		wait.until(ExpectedConditions.visibilityOf(checkboxDeleteUser));
		checkboxDeleteUser.click();
		wait.until(ExpectedConditions.visibilityOf(buttonDeleteUserInPopup));
		buttonDeleteUserInPopup.click();
	}

	public void navigateToDisableIcon(String emailID) {
		action = new Actions(driver);
		wait.until(ExpectedConditions.visibilityOf(activeUserButtonDisable(emailID)));
		log.info("Disable icon is displayed");
		action.moveToElement(activeUserButtonDisable(emailID)).perform();
		log.info("mouse moved to Disable icon");
	}

	public void disableUser(String emailID) {
		clickDisableIcon(emailID);
		wait.until(ExpectedConditions.visibilityOf(popupDisableUser));
		popupDisableUser.click();
		wait.until(ExpectedConditions.visibilityOf(btnDisableYes));
		btnDisableYes.click();
	}

	public void navigateToEnableIcon(String emailID) {
		action = new Actions(driver);
		wait.until(ExpectedConditions.visibilityOf(activeUserButtonEnable(emailID)));
		log.info("Enable icon is displayed");
		action.moveToElement(activeUserButtonEnable(emailID)).perform();
		log.info("mouse moved to Enable icon");
	}

	public void enableUser(String emailID) {
		clickEnableIcon(emailID);
		wait.until(ExpectedConditions.visibilityOf(popupEnableUser));
		popupEnableUser.click();
		wait.until(ExpectedConditions.visibilityOf(btnEnableYes));
		btnEnableYes.click();
	}

	public void navigateToResetPasswordIcon(String emailID, TestResultsStatus testResultsStatus) {
		action = new Actions(driver);
		wait.until(ExpectedConditions.visibilityOf(activeUserButtonResetPassword(emailID)));
		log.info("Reset Password icon is displayed");
		action.moveToElement(activeUserButtonResetPassword(emailID)).perform();
		log.info("mouse moved to Reset Password icon");
	}

	public void resetPassword(String emailID, TestResultsStatus testResultsStatus) {
		clickResetPasswordIcon(emailID);
		wait.until(ExpectedConditions.visibilityOf(popupResetPassword));
		popupResetPassword.click();
		wait.until(ExpectedConditions.visibilityOf(btnReset));
		btnReset.click();
	}

	public void navigateToResendEmailIcon(String emailID, TestResultsStatus testResultsStatus) {
		action = new Actions(driver);
		wait.until(ExpectedConditions.visibilityOf(invitedUserButtonResendMail(emailID)));
		log.info("Resend mail icon is displayed");
		action.moveToElement(invitedUserButtonResendMail(emailID)).perform();
		log.info("mouse moved to Resend mail icon");
	}

	public void navigateToViewIcon(String emailID, TestResultsStatus testResultsStatus) {
		action = new Actions(driver);
		wait.until(ExpectedConditions.visibilityOf(userButtonView(emailID)));
		log.info("View Icon is displayed");
		action.moveToElement(userButtonView(emailID)).perform();
		log.info("mouse moved to View Icon");
	}

	public void clickOnGlobalEditIcon(String emailID, TestResultsStatus testResultsStatus) {
		clickUserViewIcon(emailID);
		wait.until(ExpectedConditions.visibilityOf(viewSlider));
		wait.until(ExpectedConditions.visibilityOf(editUserIcon));
		editUserIcon.click();
	}

	public void clickOnNameEditIcon(String emailID, TestResultsStatus testResultsStatus) {
		clickUserViewIcon(emailID);
		wait.until(ExpectedConditions.visibilityOf(userNameInViewSlider));
		action = new Actions(driver);
		action.moveToElement(userNameInViewSlider);
		wait.until(ExpectedConditions.visibilityOf(editFirstAndLastNameIcon));
		action.moveToElement(editFirstAndLastNameIcon);
		editFirstAndLastNameIcon.click();
		log.info("Clicked on edit icon next to the username");
	}

	public void resendMail(String emailID, TestResultsStatus testResultsStatus) {
		clickResendMailIconForInvitedUser(emailID);
	}

	public void scrollIntoview(WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView(true);", element);
	}

	public void selectCompany(String company, TestResultsStatus testResultsStatus) {
		action = new Actions(driver);
		wait.until(ExpectedConditions.visibilityOf(companyDropdown));
		companyDropdown.click();
		log.info("Company dropdown clicked");
		wait.until(ExpectedConditions.visibilityOf(companyDropdownlisting));
		log.info("company Dropdown loaded");
		wait.until(ExpectedConditions.visibilityOf(companyListSelectCompany(company)));
		action.moveToElement(companyListSelectCompany(company)).build();
		companyListSelectCompany(company).click();
		log.info("Company selected");
	}

}
