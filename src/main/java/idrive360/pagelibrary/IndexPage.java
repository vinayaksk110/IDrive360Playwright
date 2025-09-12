package idrive360.pagelibrary;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.*;
import org.openqa.selenium.support.ui.Wait;

public class IndexPage {

	private WebDriver driver = null;
	private Wait<WebDriver> wait = null;

	@FindBy(xpath = "//div[@class='login_wrap']//a[text()='Sign In']")
	private WebElement lnktxtSignIn;

	public IndexPage(WebDriver driver, Wait<WebDriver> wait) {
		this.driver = driver;
		this.wait = wait;
		PageFactory.initElements(this.driver, this);
	}

	public void clickSignIn() {
		lnktxtSignIn.click();
	}

}
