package idrive360.pagelibrary;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.testng.Assert;

import idrive360.data.UserCredentials;
import idrive360.managers.PageObjectManager;
import idrive360.testbase.TestBase;
import idrive360.utilities.TestResultsStatus;

public class SignupPage extends TestBase {

	private WebDriver driver = null;
	private Wait<WebDriver> wait = null;
	PageObjectManager pageObjectManager = null;
//	UserCredentials userCredentials = null;

	@FindBy(id = "fname")
	public WebElement txtbxFirstName;

	@FindBy(id = "lname")
	public WebElement txtbxLastName;

	@FindBy(id = "email")
	public WebElement txtbxEmailID;

	@FindBy(id = "password")
	public WebElement txtbxPassword;

	@FindBy(id = "cname")
	public WebElement txbxCompanyName;

	@FindBy(id = "telnumSignup")
	public WebElement txbxPhoneNumber;

	@FindBy(id = "frm-btn")
	public WebElement btnCreateMyAccount;

	@FindBy(xpath = "//div[@class='id-maincnt-hed']")
	public WebElement headerCreateAccountField;

	@FindBy(xpath = "//h3[@class='id-wlcme-bnrhd']")
	public WebElement welcomeIDrive360header;

	public SignupPage(WebDriver driver, Wait<WebDriver> wait) {
		this.driver = driver;
		this.wait = wait;
		PageFactory.initElements(this.driver, this);
	}

	public void enterFirstName(String firstName) {
		txtbxFirstName.sendKeys(firstName);
		log.info("Entered the first name: " + firstName);
	}

	public void enterLastName(String lastName) {
		txtbxLastName.sendKeys(lastName);
		log.info("Entered the last name: " + lastName);
	}

	public void enterEmail(String emailAddress) {
		txtbxEmailID.sendKeys(emailAddress);
		log.info("Entered the email id: " + emailAddress);
	}

	public void enterCompanyName(String companyName) {
		txbxCompanyName.sendKeys(companyName);
		log.info("Entered the company name: " + companyName);
	}

	public void enterPhoneNumber(String phoneNumber) {
		txbxPhoneNumber.click();
		txbxPhoneNumber.sendKeys(phoneNumber);
		log.info("Entered the phone number: " + phoneNumber);
	}

	public void enterPassword(String password) {
		txtbxPassword.sendKeys(password);
		log.info("Entered the password");
	}

	public void clickCreateMyAccount() {
		btnCreateMyAccount.click();
		log.info("Clicked Create My Account button");
	}

	public WebElement waitForElement(WebElement elementToWaitFor) {
		return wait.until(ExpectedConditions.visibilityOf(elementToWaitFor));
	}

	public TestResultsStatus enterSignupData(UserCredentials userCredentials, TestResultsStatus testResultsStatus) {
		try {
			// Fill the signup data to the form.
			enterFirstName(userCredentials.firstName);
			enterLastName(userCredentials.lastName);
			enterEmail(userCredentials.emailID);
			enterPassword(userCredentials.password);
			enterCompanyName(userCredentials.companyName);
			enterPhoneNumber(userCredentials.phoneNo);
		} catch (Exception e) {
			e.printStackTrace();
			testResultsStatus.setIsLoginSuccess(false);
			testResultsStatus.setTestResult("Fail");
			testResultsStatus.setTestResultComment("Exception occured While filling out form\n" + e.getMessage());
			testResultsStatus.setTestEndTime(System.currentTimeMillis());
			testResultsStatus.setTestExecutionTime(testResultsStatus.getTestEndTime(),
					testResultsStatus.getTestStartTime());
			Assert.assertTrue(false, "Exception occured");
		}
		return testResultsStatus;
	}

	public TestResultsStatus waitAndClickSignupButton(TestResultsStatus testResultsStatus) {
		try {
			testResultsStatus.setTestStartTime(System.currentTimeMillis());
			clickCreateMyAccount();
		} catch (Exception e) {
			e.printStackTrace();
			testResultsStatus.setIsLoginSuccess(false);
			testResultsStatus.setTestResult("Fail");
			testResultsStatus.setTestResultComment("Exception occured while clicking Signup button\n" + e.getMessage());
			testResultsStatus.setTestEndTime(System.currentTimeMillis());
			testResultsStatus.setTestExecutionTime(testResultsStatus.getTestEndTime(),
					testResultsStatus.getTestStartTime());
			Assert.assertTrue(false, "Exception occured");
		}
		return testResultsStatus;
	}
}
