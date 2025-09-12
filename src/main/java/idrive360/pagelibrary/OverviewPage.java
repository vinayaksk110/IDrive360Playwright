package idrive360.pagelibrary;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;

import idrive360.data.UserCredentials;
import idrive360.testbase.TestBase;

public class OverviewPage extends TestBase{
	private WebDriver driver = null;
	private Wait<WebDriver> wait = null;
	UserCredentials userCredentials = null;
	
	public OverviewPage(WebDriver driver, Wait<WebDriver> wait){
		this.driver = driver;
		this.wait = wait;
		PageFactory.initElements(this.driver, this);
	}
	
	public @FindBy(linkText = "Add Company") WebElement addCompany;
	
	public @FindBy(linkText = "Add User") WebElement addUser;
	
	public void WaitAndClickAddCompanyButton() {
		wait.until(ExpectedConditions.visibilityOf(addCompany));
		log.info("Add company button is displayed");
		addCompany.click();
		log.info("Clicked on Add company button");
	}

}
