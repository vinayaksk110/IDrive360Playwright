package idrive360.pagelibrary;

import org.testng.Assert;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;

import idrive360.PlaywrightFactory.PlaywrightFactory;
import idrive360.data.UserCredentials;

public class LoginPage extends PlaywrightFactory {

	private Page page;
	private UserCredentials userCredentials;

	private String txtbxEmail = "#username";
	private String txtbxPassword = "#password";
	private String btnSignIn = "#frm-btn";
	private String loginLoader = "//button[@id='frm-btn' and contains(@class,'id-btn id-info-btn-frm id-sm-loader')]";
	private String cancelledAccountToastMsg = "//*[@class='id-card-blk id-card-success']//p";
	private String freeTrialUpgradebanner = "#upgrade";
	private String msgInvalidLogin = "div[@class='id-card-blk id-show id-card-error' and contains(@style , 'display: block;')]//div//p";

	/**
	 * This constructor is used to set the required driver for the page
	 * 
	 * @param driver : Pass the testbase webdriver for maintaining the browser
	 *               status through out the pages.
	 */
	public LoginPage(Page page) {
		this.page = page;
	}

	public void enterEmail(String email) {
		page.fill(txtbxEmail, "");
		page.fill(txtbxEmail, email);
		log.info("Entered the email: " + email);
	}

	public void enterPasword(String password) {
		page.fill(txtbxPassword, "");
		page.fill(txtbxPassword, password);
		log.info("Entered the password");
	}

	public void clickSignIn() {
		page.click(btnSignIn);
		log.info("Clicked on SignIn button");
	}

	public String getLoginPageURL() {
		String url = page.url();
		System.out.println("Login page url is : " + url);
		return url;
	}

	public String getPageTitle() {
		String title = page.title();
		System.out.println("page title is: " + title);
		return title;
	}

	public void loginToAccount(UserCredentials userCredentials) {
//		userCredentials = new UserCredentials();
		System.out.println("user creds in login page"+userCredentials);
		try {
			log.info(userCredentials.emailID +" , "+userCredentials.password);
			enterEmail(userCredentials.emailID);
			enterPasword(userCredentials.password);
//			enterEmail("vinayak.kumbar+whitelabel@idrive.com");
//			enterPasword("test12");
			clickSignIn();
		} catch (Exception e) {
			e.printStackTrace();
			Assert.assertTrue(false, "Reached exception while entering usercredentials in login page");
		}
		try {
			log.info("Waiting for login loader");
			page.waitForSelector(loginLoader, new Page.WaitForSelectorOptions().setState(WaitForSelectorState.VISIBLE));
			log.info("signin Loader appeared");
			page.waitForSelector(loginLoader, new Page.WaitForSelectorOptions().setState(WaitForSelectorState.HIDDEN));
			log.info("signin Loader disappeared");
		} catch (Exception e) {
			e.printStackTrace();
		}
		page.waitForURL("https://www.idrive360.com/enterprise/devices", new Page.WaitForURLOptions().setTimeout(30000));
		String pagetitleReceived = getPageTitle();
		log.info(pagetitleReceived);

//		if (pagetitleReceived.contains(Constants.LOGIN_PAGE_TITLE)) {
//			log.info("Login page detected");
//			// Verify if the invalid login credentials message appears
//			WebElement errorMsg = wait.until(ExpectedConditions.visibilityOf(msgInvalidLogin));
//			log.info(errorMsg.getText());
//			if (!errorMsg.getText().equals(getMessage("cancel.cancellationofidrive360account"))) {
//				testResultsStatus.setIsLoginSuccess(false);
//				testResultsStatus.setTestResult("Fail");
//				testResultsStatus.setTestResultComment(
//						"Account was not logged in, received following error\n" + errorMsg.getText());
//				testResultsStatus.setTestEndTime(System.currentTimeMillis());
//				testResultsStatus.setTestExecutionTime(testResultsStatus.getTestEndTime(),
//						testResultsStatus.getTestStartTime());
//				Assert.assertTrue(false, "Account was not logged in");
//			}
//		}
	}

}
