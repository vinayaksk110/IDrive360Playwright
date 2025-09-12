package idrive360.pagelibrary;

import org.openqa.selenium.ElementClickInterceptedException;
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
import idrive360.testbase.Constants;
import idrive360.testbase.TestBase;
import idrive360.utilities.TestResultsStatus;

public class AccountPage extends TestBase {

	private WebDriver driver = null;
	private Wait<WebDriver> wait = null;
	private PageObjectManager pageObjectManager = null;

	public AccountPage(WebDriver driver, Wait<WebDriver> wait) {
		this.driver = driver;
		this.wait = wait;
		PageFactory.initElements(this.driver, this);
	}

	@FindBy(xpath = "//li[@id='5']//span[contains(text(), 'My Account')]")
	public WebElement myAccountLhsbtn;

	public @FindBy(id = "profileAccount") WebElement btnProfileTab;

	public @FindBy(id = "upgradeAccount") WebElement btnUpgradeAccountTab;

	public @FindBy(xpath = "//div[@class='id-cncl-acc']//a[@class='id-tkn-btn']") WebElement cancelMyAccount;

	public @FindBy(id = "upgradeCC") WebElement btnUpdateCCTab;

	public @FindBy(id = "billinfo") WebElement btnBillingInfoTab;

	public @FindBy(id = "security") WebElement btnSecurityTab;

	public @FindBy(xpath = "//div[@class='id-loader-section id-show']") WebElement loader;

	public @FindBy(id = "fname") WebElement inputFirstName;

	public @FindBy(id = "lname") WebElement inputLastName;

	public @FindBy(id = "cname") WebElement inputCompanyName;

	public @FindBy(id = "email") WebElement inputEmail;

	public @FindBy(id = "tel_code") WebElement inputCountryCode;

	public @FindBy(id = "phone") WebElement inputPhoneNumber;

	public @FindBy(linkText = "(Change email address)") WebElement linkChangeEmailAddress;

	public @FindBy(id = "new-email") WebElement inputNewEmailAddress;

	public @FindBy(id = "email-update-password") WebElement inputCurrentPasswordChangeEmailAddress;

	public @FindBy(linkText = "Cancel") WebElement linkCancel;

	public @FindBy(xpath = "//div[@id='id-change-email-verify1']/ancestor::div[@id='id-change-email-verify' and @style='display: block;']") WebElement popupChangeEmailAddress;

	public @FindBy(xpath = "//div[@id='id-change-email-verify1']//a[@class='id-btn id-info-btn']") WebElement btnChange;

	public @FindBy(xpath = "//div[@id='id-change-email-verify1']//a[@class='close id-close']") WebElement btnCloseChangeEmailPopup;

	public @FindBy(id = "currentPassword") WebElement inputCurrentPassword;

	public @FindBy(id = "newPassword") WebElement inputNewPassword;

	public @FindBy(id = "repeatNewPassword") WebElement inputConfirmNewPassword;

	public @FindBy(id = "savechanges") WebElement btnSaveProfileChanges;

	public @FindBy(id = "cancel-auto-renewal") WebElement btnCancelAutoRenewal;

	public @FindBy(id = "send-update-chxbx") WebElement chxbxSendUpdates;

	public @FindBy(id = "show-new-password") WebElement btnShowNewPassword;

	public @FindBy(id = "show-current-password") WebElement btnShowCurrentPassword;

	public @FindBy(id = "cancel-renewal-cross") WebElement btnCancelRenewalCross;

	public @FindBy(id = "cancel-renewal-confirm") WebElement btnCancelRenewalConfirm;

	public @FindBy(id = "cancel-renewal-cancel") WebElement btnCancelRenewalCancel;

	public @FindBy(id = "accountInfo-toggle") WebElement btnAccountInfoToggle;

	public @FindBy(id = "recalculate-storage") WebElement btnRecalculateStorage;

	public @FindBy(id = "addcard1") WebElement btnUpgradeAddNewcard;

	public @FindBy(name = "cardnumber") WebElement inputUpgradeCardNumber;

	public @FindBy(name = "exp-date") WebElement inputUpgradeExpDate;

	public @FindBy(name = "cvc") WebElement inputUpgradeCVC;

	public @FindBy(id = "countryname") WebElement inputUpgradeConutryName;

	public @FindBy(id = "state") WebElement inputUpgradeState;

	public @FindBy(id = "billAddr") WebElement inputUpgradeBillingAddress;

	public @FindBy(id = "upgradezipcode1") WebElement inputUpgradeZipCode;

	public @FindBy(id = "frm-btn2") WebElement btnUpgrade;

	public @FindBy(id = "frm-btn1") WebElement myAccountBtnUpgrade;

	public @FindBy(id = "infoupgrade") WebElement btnUpgradeInfoHover;

	public @FindBy(id = "frm-cancelbtn2") WebElement btnUpgradeCancel;

	public @FindBy(id = "addcard2") WebElement btnUpdateCCAddNewcard;

	public @FindBy(id = "info1") WebElement btnUpdateCCInfoHover;

	public @FindBy(id = "updatecreditaddress") WebElement inputUpdateCCBillingAddress;

	public @FindBy(id = "updatecreditzipcode") WebElement inputUpdateCCZipCode;

	public @FindBy(id = "frm-cancel3") WebElement btnUpdateCCCancel;

	public @FindBy(id = "frm-3") WebElement btnUpdateCCConfirm;

	public @FindBy(id = "billing-info-print") WebElement btnBillingInfoPrint;

	public @FindBy(id = "billing-info-updateCC") WebElement btnBillingInfoUpdateCC;

	public @FindBy(id = "billing-info-view") WebElement btnBillingInfoView;

	public @FindBy(id = "security-know-more") WebElement btnSecurityKnowMore;

	public @FindBy(id = "security-confirm") WebElement btnSecurityConfirm;

	public @FindBy(id = "security-radio-email") WebElement radioSecurityEmail;

	public @FindBy(id = "security-radio-phone") WebElement radioSecurityPhone;

	public @FindBy(id = "security-radio-totp") WebElement radioSecurityTotp;

	public @FindBy(id = "totp-supported-apps") WebElement btnTotpSupportedApps;

	public @FindBy(id = "totp-cros") WebElement btnTotpCross;

	public @FindBy(id = "idManual") WebElement btnTotpEnterManually;

	public @FindBy(id = "idManuals") WebElement btnTotpScanQR;

	public @FindBy(id = "totp-next") WebElement btnTotpNext;

	public @FindBy(id = "totp-cancel") WebElement btnTotpCancel;

	public @FindBy(id = "totp-copy-code") WebElement btnTotpCopyCode;

	public @FindBy(id = "totp-download") WebElement btnTotpDownload;

	public @FindBy(id = "totp-print") WebElement btnTotpPrint;

	public @FindBy(id = "totp-continue") WebElement btnTotpContinue;

	public @FindBy(id = "totp-back") WebElement btnTotpBack;

	public @FindBy(id = "totpValue") WebElement inputTotpOneTimeCode;

	public @FindBy(id = "totp-activate") WebElement btnTotpActivate;

	public @FindBy(id = "totp-cancel2") WebElement btnTotpCancel2;

	public @FindBy(id = "telnum") WebElement inputSMSNumber;

	public @FindBy(id = "sms-send-code") WebElement btnSMSSendCode;

	public @FindBy(id = "sms-back") WebElement btnSMSBack;

	public @FindBy(id = "sms-verify-code") WebElement inputSMSVerifyCode;

	public @FindBy(id = "sms-verify") WebElement btnSMSVerify;

	public @FindBy(id = "sms-back2") WebElement btnSMSBack2;

	public @FindBy(id = "sms-resend") WebElement btnSMSResend;

	public @FindBy(id = "emailOtp") WebElement inputEmailOtp;

	public @FindBy(id = "email-verify") WebElement btnEmailVerify;

	public @FindBy(id = "email-back") WebElement btnEmailBack;

	public @FindBy(id = "email-resend") WebElement btnEmailResend;

	public @FindBy(id = "email-know-more") WebElement btnEmailKnowMore;

	public @FindBy(id = "disable") WebElement btnEmailDisable;

	public @FindBy(id = "switch-email") WebElement btnSwitchToEmail;

	public @FindBy(id = "switch-totp") WebElement btnSwitchToTotp;

	public @FindBy(id = "switch-sms") WebElement btnSwitchToSMS;

	public @FindBy(id = "switch-cross") WebElement btnSwitchCross;

	public @FindBy(id = "switch-cancel") WebElement btnSwitchCancel;

	public @FindBy(id = "switch-confirm") WebElement btnSwitchConfirm;

	public @FindBy(xpath = "//*contains(text(),'Cloud to Cloud Backup')") WebElement cloudToCloudTab;

	public @FindBy(id = "expiredmsg") WebElement freeTrialExpiredBanner;

	// cancellation pop up
	public @FindBy(id = "password-cancel") WebElement txtbxPassword;

	public @FindBy(xpath = "//*[.='Phone number']//following-sibling::input[@formcontrolname='phone']") WebElement txtbxPhoneno;

	public @FindBy(name = "email") WebElement txtbxEmail;

	// dropdown cancellationReason
	public @FindBy(xpath = "//select[@name='cnxType']") WebElement reasonForCancellationDropDown;

	public @FindBy(xpath = "//div[@class='id-frm-btnblk']//button[contains(text(),'Cancel My Account')]") WebElement btnPopupCancel;

	public @FindBy(xpath = "//div[@class='id-pop-footer']//button[contains(text(),'Cancel My account')]") WebElement btnCancelMyAccount;

	public @FindBy(xpath = "//div[@id='id-upgrade-card-info']//h2[contains(text(),'Reactivate account')]") WebElement reactivatePopupHeading;

	public @FindBy(xpath = "//div[@id='id-upgrade-card-info']//a[@title='close']") WebElement btnCloseReactivatePopupHeading;

	public @FindBy(xpath = "//div[@id='id-upgrade-non-payment']//h2[contains(text(),'Your account has been suspended due to non-payment')]") WebElement applyPendingChargespopupHeading;

	public @FindBy(xpath = "//div[@id='id-upgrade-non-payment']/div/div//a[@title='close']") WebElement btnCloseApplyPendingChargespopup;

	public @FindBy(xpath = "//div[@class='id-form-row id-form-row-0']") WebElement contentUpgradePlan;

	@FindBy(xpath = "//*[@id='payment1']//iframe[@title='Secure card payment input frame']")
	public WebElement ccIFrame;

	@FindBy(xpath = "//input[@name='cardnumber']")
	public WebElement ccNumber;

	@FindBy(id = "upgradeaddress")
	public WebElement txtbxBillingAddress;

	@FindBy(xpath = "//input[@name='exp-date']")
	public WebElement txtbxCardExpiry;

	@FindBy(xpath = "//input[@name='cvc']")
	public WebElement txtbxCVV;

	@FindBy(xpath = "//div[@id='id-tabCont2']//input[@id='state']")
	public WebElement txtbxState;

	@FindBy(xpath = "//div[@id='id-tabCont2']//input[@id='upgradezipcode']")
	public WebElement txtbxZipCode;

	@FindBy(id = "id-btn-continuefree")
	public WebElement btnContinueUpgradeConfirmationPopup;

//	@FindBy(xpath = "//*[@class='id-acc-info-wrpr']//div[@class='id-alert success id-show']//p")
//	public WebElement msgSuccessUpgrade;

	@FindBy(xpath = "//div[@class='id-alert success id-show']//p")
	public WebElement commonSuccessMessage;

	@FindBy(xpath = "//div[@class='id-form-grp id-frm-50']//div[@class='id-inp-error']")
	public WebElement commonErrorMessage;

	@FindBy(xpath = "//div[@class='id-accinfo-sec-cont']//*[contains(text(), 'Devices')]")
	public WebElement txtPlanName;

	@FindBy(xpath = "//span[@class='id-plan-name']")
	public WebElement txtNoOfLicenses;

	@FindBy(xpath = "//div[@id='us-state-upgradeAccountForm1']//select[@formcontrolname='state']")
	public WebElement paidstatedropdownUS;

	@FindBy(xpath = "//div[@id='us-state-upgradeAccountForm']//select[@formcontrolname='state']")
	public WebElement freestatedropdownUS;

	@FindBy(id = "countryname")
	public WebElement freeAccountCountryDropDown;

	public void selctStateDropDownUSAforFreeAccount(String stateName) {
		Select dropdownUnitedState = new Select(freestatedropdownUS);
		dropdownUnitedState.selectByVisibleText(stateName);
	}

	public void clickMyAccountLhsTab() {
		myAccountLhsbtn.click();
	}

	public void clickProfileTab() {
		btnProfileTab.click();
	}

	public void clickUpgradeAccountTab() {
		btnUpgradeAccountTab.click();
	}

	public void clickUpdateCCTab() {
		btnUpdateCCTab.click();
	}

	public void clickBillingInfoTab() {
		btnBillingInfoTab.click();
	}

	public void clickSecurityTab() {
		btnSecurityTab.click();
	}

	public void inputFirstName(String firstName) {
		inputFirstName.sendKeys(firstName);
	}

	public void inputLastName(String lastName) {
		inputLastName.sendKeys(lastName);
	}

	public void inputCompanyName(String companyName) {
		inputCompanyName.sendKeys(companyName);
	}

	public void inputEmail(String email) {
		inputEmail.sendKeys(email);
	}

	public void inputNewEmailAddress(String email) {
		inputNewEmailAddress.sendKeys(email);
	}

	public void inputCurrentPasswordChangeEmailAddress(String password) {
		inputCurrentPasswordChangeEmailAddress.sendKeys(password);
	}

	public void inputCountryCode(String countryCode) {
		inputCountryCode.sendKeys(countryCode);
	}

	public void clickCancelMyAccount() {
		cancelMyAccount.click();
	}

	public void inputPhoneNumber(String phoneNumber) {
		inputPhoneNumber.sendKeys(phoneNumber);
	}

	public void inputCurrentPassword(String currentPassword) {
		inputCurrentPassword.sendKeys(currentPassword);
	}

	public void inputNewPassword(String newPassword) {
		inputNewPassword.sendKeys(newPassword);
	}

	public void inputConfirmNewPassword(String confirmNewPassword) {
		inputConfirmNewPassword.sendKeys(confirmNewPassword);
	}

	public void clickSaveProfileChanges() {
		btnSaveProfileChanges.click();
	}

	public void clickCancelAutoRenewal() {
		btnCancelAutoRenewal.click();
	}

	public void selectSendUpdates() {
		chxbxSendUpdates.click();
	}

	public void clickShowNewPassword() {
		btnShowNewPassword.click();
	}

	public void clickShowCurrentPassword() {
		btnShowCurrentPassword.click();
	}

	public void clickCancelRenewalCross() {
		btnCancelRenewalCross.click();
	}

	public void clickCancelRenewalConfirm() {
		btnCancelRenewalConfirm.click();
	}

	public void clickCancelRenewalCancel() {
		btnCancelRenewalCancel.click();
	}

	public void toggleAccountInfo() {
		btnAccountInfoToggle.click();
	}

	public void clickRecalculateStorage() {
		btnRecalculateStorage.click();
	}

	public void clickUpgradeAddNewcard() {
		btnUpgradeAddNewcard.click();
	}

	public void inputUpgradeBillingAddress(String address) {
		inputUpgradeBillingAddress.sendKeys(address);
	}

	public void inputUpgradeZipCode(String code) {
		inputUpgradeZipCode.sendKeys(code);
	}

	public void clickUpgrade() {
		btnUpgrade.click();
	}

	public void hoverUpgradeInfo() {
		btnUpgradeInfoHover.click();
	}

	public void clickUpgradeCancel() {
		btnUpgradeCancel.click();
	}

	public void clickUpdateCCAddNewcard() {
		btnUpdateCCAddNewcard.click();
	}

	public void hoverUpdateCCInfo() {
		btnUpdateCCInfoHover.click();
	}

	public void inputUpdateCCBillingAddress(String address) {
		inputUpdateCCBillingAddress.sendKeys(address);
	}

	public void inputUpdateCCZipCode(String code) {
		inputUpdateCCZipCode.sendKeys(code);
	}

	public void clickUpdateCCCancel() {
		btnUpdateCCCancel.click();
	}

	public void clickUpdateCCConfirm() {
		btnUpdateCCConfirm.click();
	}

	public void clickBillingInfoPrint() {
		btnBillingInfoPrint.click();
	}

	public void clickBillingInfoUpdateCC() {
		btnBillingInfoUpdateCC.click();
	}

	public void clickBillingInfoView() {
		btnBillingInfoView.click();
	}

	public void clickSecurityKnowMore() {
		btnSecurityKnowMore.click();
	}

	public void clickSecurityConfirm() {
		btnSecurityConfirm.click();
	}

	public void selectSecurityEmail() {
		radioSecurityEmail.click();
	}

	public void selectSecurityPhone() {
		radioSecurityPhone.click();
	}

	public void selectSecurityTotp() {
		radioSecurityTotp.click();
	}

	public void clickTotpSupportedApps() {
		btnTotpSupportedApps.click();
	}

	public void clickTotpCross() {
		btnTotpCross.click();
	}

	public void clickTotpEnterManually() {
		btnTotpEnterManually.click();
	}

	public void clickTotpScanQR() {
		btnTotpScanQR.click();
	}

	public void clickTotpNext() {
		btnTotpNext.click();
	}

	public void clickTotpCancel() {
		btnTotpCancel.click();
	}

	public void clickTotpCopyCode() {
		btnTotpCopyCode.click();
	}

	public void clickTotpDownload() {
		btnTotpDownload.click();
	}

	public void clickTotpPrint() {
		btnTotpPrint.click();
	}

	public void clickTotpContinue() {
		btnTotpContinue.click();
	}

	public void clickTotpBack() {
		btnTotpBack.click();
	}

	public void inputTotpOneTimeCode(String otp) {
		inputTotpOneTimeCode.sendKeys(otp);
	}

	public void clickTotpActivate() {
		btnTotpActivate.click();
	}

	public void clickTotpCancel2() {
		btnTotpCancel2.click();
	}

	public void inputSMSNumber(String phone) {
		inputSMSNumber.sendKeys(phone);
	}

	public void clickSMSSendCode() {
		btnSMSSendCode.click();
	}

	public void clickSMSBack() {
		btnSMSBack.click();
	}

	public void inputSMSVerifyCode(String code) {
		inputSMSVerifyCode.sendKeys(code);
	}

	public void clickSMSVerify() {
		btnSMSVerify.click();
	}

	public void clickSMSBack2() {
		btnSMSBack2.click();
	}

	public void clickSMSResend() {
		btnSMSResend.click();
	}

	public void inputEmailOtp(String otp) {
		inputEmailOtp.sendKeys(otp);
	}

	public void clickEmailVerify() {
		btnEmailVerify.click();
	}

	public void clickEmailBack() {
		btnEmailBack.click();
	}

	public void clickEmailResend() {
		btnEmailResend.click();
	}

	public void clickEmailKnowMore() {
		btnEmailKnowMore.click();
	}

	public void clickEmailDisable() {
		btnEmailDisable.click();
	}

	/**
	 * This method clicks the option provided for 2FA to switch to Email
	 */
	public void clickSwitchToEmail() {
		btnSwitchToEmail.click();
	}

	public void clickSwitchToTotp() {
		btnSwitchToTotp.click();
	}

	public void clickSwitchToSMS() {
		btnSwitchToSMS.click();
	}

	public void clickSwitchCross() {
		btnSwitchCross.click();
	}

	public void clickSwitchCancel() {
		btnSwitchCancel.click();
	}

	public void clickSwitchConfirm() {
		btnSwitchConfirm.click();
	}

	public void clickCloudToCloudTab() {
		cloudToCloudTab.click();
	}

	public void clickBtnCloseReactivatePopupHeading() {
		btnCloseReactivatePopupHeading.click();

	}

	public void clickBtnCloseApplyPendingChargespopup() {
		btnCloseApplyPendingChargespopup.click();
	}

	/**
	 * This method enters the billing information
	 * 
	 * @param userCredentials      : This is the credentials that has to be entered
	 *                             during signup.
	 * @param executionEnvironment : This will the environment on which the
	 *                             automation scripts will be run.
	 */
	public void enterTheBillingInformation(UserCredentials userCredentials, String executionEnvironment) {
		log.info("Execution Environment: " + executionEnvironment);
		driver.switchTo().frame(ccIFrame);
		log.info("Switched to CC iFrame");
		wait.until(ExpectedConditions.visibilityOf(ccNumber));
		if (executionEnvironment.equalsIgnoreCase(Constants.TEST_SERVER_25)) {
			ccNumber.sendKeys(userCredentials.testCC);
		} else {
			ccNumber.sendKeys(userCredentials.ccNo);
		}
		if (userCredentials.mm.endsWith(".0")) {
			userCredentials.mm = userCredentials.mm.replace(".0", "");
			userCredentials.yy = userCredentials.yy.replace(".0", "");
			txtbxCardExpiry.sendKeys(userCredentials.mm + userCredentials.yy);
		} else {
			txtbxCardExpiry.sendKeys(userCredentials.mm + userCredentials.yy);
		}
		txtbxCVV.sendKeys(userCredentials.cvv);
		log.info("Entered the CC details");
		driver.switchTo().parentFrame();
		txtbxBillingAddress.clear();
		txtbxBillingAddress.sendKeys(userCredentials.billingAddress);
		log.info("Entered the Billing Address: " + userCredentials.billingAddress);
		log.info("dropdown status>>>" + freestatedropdownUS.isDisplayed());
		selctStateDropDownUSAforFreeAccount(userCredentials.state);
		txtbxZipCode.sendKeys(userCredentials.zipcode);
		log.info("Entered the zip code: " + userCredentials.zipcode);
	}

	public void entertxtbxPassword(String password) {
		txtbxPassword.sendKeys(password);
	}

	public void entertxtbxPhoneno(String phoneNo) {
		txtbxPhoneno.sendKeys(phoneNo);
	}

	public void entertxtbxEmail(String emailID) {
		txtbxEmail.sendKeys(emailID);
	}

	/**
	 * This method enters the reason for cancellation.
	 * 
	 * @param reasonForCancellation : This is the reason for cancellation, entered
	 *                              by the user.
	 * @return
	 */
	public TestResultsStatus selectReasonForCancellation(String reasonForCancellation) {
		try {
			Select select = new Select(reasonForCancellationDropDown);
			reasonForCancellationDropDown.click();
			select.selectByVisibleText(reasonForCancellation);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return testResultsStatus;
	}

	public void clickbtnPopupCancel() {
		btnPopupCancel.click();
	}

	public TestResultsStatus clickbtnCancelMyAccountFromPopup(TestResultsStatus testResultsStatus) {
		try {
			btnCancelMyAccount.click();
		} catch (Exception e) {
			String errorMessage = "Error while clicking cancel button inside cancel popup. \n";
			log.error(errorMessage + e.getMessage());
			e.printStackTrace();
			testResultsStatus.setTestResult("Fail");
			testResultsStatus.setTestResultComment(errorMessage + e.getMessage());
			Assert.assertTrue(false);
		}
		return testResultsStatus;
	}

	public TestResultsStatus verifyMyAccountPageLoader(TestResultsStatus testResultsStatus) {
		try {
			wait.until(ExpectedConditions.visibilityOf(loader));
		} catch (Exception e) {
			log.error("Loader didn't appear, hence proceeding to the test case");
		}
		try {
			wait.until(ExpectedConditions.invisibilityOf(loader));
		} catch (Exception e) {
			e.printStackTrace();
			testResultsStatus.setTestResult("Fail");
			testResultsStatus.setTestResultComment(e.getMessage());
			log.error(e.getMessage());
			log.error("Loader appeared but failed while waiting for loader to disappear");
			Assert.assertTrue(false);
		}
		return testResultsStatus;
	}

	public TestResultsStatus enterDataAndClickCancelMyAccount(UserCredentials userCredentials,
			TestResultsStatus testResultsStatus) {
		try {
			entertxtbxPassword(userCredentials.password);
			log.info("Entered password");
			entertxtbxPhoneno(userCredentials.phoneNo);
			log.info("Entered phonenumber");
			entertxtbxEmail(userCredentials.emailID);
			log.info("Entered emailAddress");
			selectReasonForCancellation(userCredentials.reasonForCancellation);
			log.info("Selected Cancellation reason");
			clickbtnPopupCancel();
			log.info("clicked cancel button");
			wait.until(ExpectedConditions.visibilityOf(btnCancelMyAccount));
			log.info("Cancelation confirmation pop up appeared");
		} catch (Exception e) {
			String errorMessage = "Error while entering data in cancel Form \n";
			log.error(errorMessage + e.getMessage());
			e.printStackTrace();
			testResultsStatus.setTestResult("Fail");
			testResultsStatus.setTestResultComment(errorMessage + e.getMessage());
			Assert.assertTrue(false);
		}
		return testResultsStatus;
	}

	public TestResultsStatus waitAndClickCancelMyAccount(TestResultsStatus testResultsStatus) {
		try {
			wait.until(ExpectedConditions.visibilityOf(btnProfileTab));
			log.info("Profiles tab is displayed");
			wait.until(ExpectedConditions.visibilityOf(inputFirstName));
			log.info("Firstname section is reflected");
			clickCancelMyAccount();
			log.info("Cancel button clicked");
		} catch (Exception e) {
			String errorMessage = "Error while clicking on Cancel button. \n";
			log.error(errorMessage + e.getMessage());
			e.printStackTrace();
			testResultsStatus.setTestResult("Fail");
			testResultsStatus.setTestResultComment(errorMessage + e.getMessage());
			Assert.assertTrue(false);
		}
		return testResultsStatus;
	}

	public TestResultsStatus appearanceAndDisappearenceOfLoader(TestResultsStatus testResultsStatus) {
		try {
			wait.until(ExpectedConditions.visibilityOf(loader));
			log.info("Loader disappeared in my account section");
			wait.until(ExpectedConditions.invisibilityOf(loader));
			log.info("Loader disappeared in my account section");
		} catch (Exception e) {
			String errorMessage = "Error while waiting for loader appear/disappear. \n";
			log.error(errorMessage + e.getMessage());
			e.printStackTrace();
			testResultsStatus.setTestResult("Fail");
			testResultsStatus.setTestResultComment(errorMessage + e.getMessage());
			Assert.assertTrue(false);
		}
		return testResultsStatus;
	}

	public TestResultsStatus navigateToMyAccount(TestResultsStatus testResultsStatus) {
		pageObjectManager = new PageObjectManager(driver, wait);
		try {
			wait.until(ExpectedConditions.visibilityOf(pageObjectManager.getHeaderFunctionalityPage().txtAccountName));
			pageObjectManager.getHeaderFunctionalityPage().clickAccountSection();
			pageObjectManager.getHeaderFunctionalityPage().clickMyAccountFromHeader();
			log.info("Clicked on my account");
			verifyMyAccountPageLoader(testResultsStatus);
		} catch (Exception e) {
			e.printStackTrace();
			testResultsStatus.setTestResult("Fail");
			testResultsStatus.setTestResultComment(e.getMessage());
			log.error(e.getMessage());
			log.error("error in the wait for account page");
			Assert.assertTrue(false);
		}
		return testResultsStatus;
	}

	public TestResultsStatus commonMessage(String expectedMessage, TestResultsStatus testResultsStatus) {
		pageObjectManager = new PageObjectManager(driver, wait);
		try {
			wait.until(ExpectedConditions.visibilityOf(commonSuccessMessage));
			String message = commonSuccessMessage.getText();
			log.info("message received from toast message :" + message);
			Assert.assertEquals(message, expectedMessage);
			testResultsStatus.setTestResultComment(message);
			testResultsStatus.setTestResult("Pass");
		} catch (Exception e) {
			testResultsStatus.setTestResult("Fail");
			testResultsStatus.setTestResultComment(e.getMessage());
			log.error(e.getMessage());
			Assert.assertTrue(false);
		} catch (AssertionError ae) {
			testResultsStatus.setTestResult("Fail");
			testResultsStatus
					.setTestResultComment("Looks like the toast message is not matching with expected message");
			log.error(ae.getMessage());
			Assert.assertTrue(false);
		}
		return testResultsStatus;
	}

	public TestResultsStatus clickSaveButton(TestResultsStatus testResultsStatus) {
		try {
			wait.until(ExpectedConditions.visibilityOf(pageObjectManager.getAccountPage().btnSaveProfileChanges));
			Actions action = new Actions(driver);
			action.moveToElement(pageObjectManager.getAccountPage().btnSaveProfileChanges);
			pageObjectManager.getAccountPage().clickSaveProfileChanges();
			log.info("Save Changes button clicked");
		} catch (Exception e) {
			String errorMessage = "Error while clicking on \n";
			log.error(errorMessage + e.getMessage());
			e.printStackTrace();
			testResultsStatus.setTestResult("Fail");
			testResultsStatus.setTestResultComment(errorMessage + e.getMessage());
			Assert.assertTrue(false);
		}
		return testResultsStatus;
	}

	public TestResultsStatus waitAndenterBillingInfo(UserCredentials usercredentials, TestResultsStatus testResultsStatus, String executionEnvironment) {
		pageObjectManager = new PageObjectManager(driver, wait);
		try {
			wait.until(ExpectedConditions.visibilityOf(btnUpgradeAccountTab));
			log.info("Upgrade tab in the my account appears with plans mentioned");
			wait.until(ExpectedConditions.visibilityOf(myAccountBtnUpgrade));
			wait.until(ExpectedConditions.visibilityOf(txtbxBillingAddress));
			testResultsStatus.setTestStartTime(System.currentTimeMillis());
			enterTheBillingInformation(usercredentials, executionEnvironment);
			log.info("Entered the billing info");
		} catch (Exception e) {
			String errorMessage = "Error while entering the billing information";
			log.error(errorMessage + e.getMessage());
			e.printStackTrace();
			testResultsStatus.setTestResult("Fail");
			testResultsStatus.setTestResultComment(errorMessage + e.getMessage());
			Assert.assertTrue(false);
		}
		return testResultsStatus;
	}

	public TestResultsStatus waitAndClickConfirmUpgrade(TestResultsStatus testResultsStatus) {
		try {
			wait.until(ExpectedConditions.visibilityOf(btnContinueUpgradeConfirmationPopup));
			log.info("Upgrade confirmation popup appeared");
			btnContinueUpgradeConfirmationPopup.click();
			log.info("Clicked on upgrade button on the confirmation popup");

		} catch (Exception e) {
			String errorMessage = "Failed on upgrade confirmation popup: ";
			log.error(errorMessage + e.getMessage());
			e.printStackTrace();
			testResultsStatus.setTestResult("Fail");
			testResultsStatus.setTestResultComment(errorMessage + e.getMessage());
			Assert.assertTrue(false);
		}
		return testResultsStatus;
	}

	public TestResultsStatus waitAndClickUpgrade(TestResultsStatus testResultsStatus) {
		try {
			wait.until(ExpectedConditions.visibilityOf(myAccountBtnUpgrade));
			log.info("Upgrade button is displayed");
			wait.until(ExpectedConditions.elementToBeClickable(myAccountBtnUpgrade));
			log.info("Upgrade button is clickable");
			myAccountBtnUpgrade.click();
			log.info("Clicked on the Upgrade button");
		} catch (ElementClickInterceptedException ec) {
			log.error("Element click intercepted exception occured. Trying to click again after waiting for 5 seconds");
			try {
				Thread.sleep(5000);
				wait.until(ExpectedConditions.visibilityOf(myAccountBtnUpgrade));
				wait.until(ExpectedConditions.elementToBeClickable(myAccountBtnUpgrade));
				log.info("Upgrade button is clickable");
				myAccountBtnUpgrade.click();
				log.info("Clicked on the Upgrade button");
			} catch (Exception e) {
				String errorMessage = "Failed to click on upgrade button";
				log.error(errorMessage + e.getMessage());
				e.printStackTrace();
				testResultsStatus.setTestResult("Fail");
				testResultsStatus.setTestResultComment(errorMessage + e.getMessage());
				Assert.assertTrue(false);
			}
		} catch (Exception e) {
			String errorMessage = "Failed to click on upgrade button";
			log.error(errorMessage + e.getMessage());
			e.printStackTrace();
			testResultsStatus.setTestResult("Fail");
			testResultsStatus.setTestResultComment(errorMessage + e.getMessage());
			Assert.assertTrue(false);
		}
		return testResultsStatus;
	}

	public TestResultsStatus waitAndGetSuccessMessage(TestResultsStatus testResultsStatus) {
		try {
			wait.until(ExpectedConditions.visibilityOf(commonSuccessMessage));
			String msg = commonSuccessMessage.getText();
			log.info(msg);
			try {
				Assert.assertEquals(getMessage("upgrade.upgradetoyearlypaidaccount"), msg);
			} catch (AssertionError ae) {
				String errorMessage = "Failed to assert success toast message: ";
				log.error(errorMessage + ae.getMessage());
				ae.printStackTrace();
				testResultsStatus.setTestResult("Fail");
				testResultsStatus.setTestResultComment(errorMessage + ae.getMessage());
				testResultsStatus.setTestEndTime(System.currentTimeMillis());
				testResultsStatus.setTestExecutionTime(testResultsStatus.getTestEndTime(),
						testResultsStatus.getTestStartTime());
				Assert.assertTrue(false);
			}
			wait.until(ExpectedConditions.visibilityOf(loader));
			wait.until(ExpectedConditions.invisibilityOf(loader));
			log.info("Loader disappeared in my account section");
			testResultsStatus.setTestEndTime(System.currentTimeMillis());
			testResultsStatus.setTestExecutionTime(testResultsStatus.getTestEndTime(),
					testResultsStatus.getTestStartTime());
		} catch (Exception e) {
			String errorMessage = "Failed to verify the upgrade success toast message: ";
			log.error(errorMessage + e.getMessage());
			e.printStackTrace();
			testResultsStatus.setTestResult("Fail");
			testResultsStatus.setTestResultComment(errorMessage + e.getMessage());
			Assert.assertTrue(false);
		}
		return testResultsStatus;
	}
	
}
