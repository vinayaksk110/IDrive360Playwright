package idrive360.pagelibrary;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Wait;

import idrive360.managers.PageObjectManager;

public class ExpressPage {

	private WebDriver driver = null;
	private Wait<WebDriver> wait = null;
	private PageObjectManager pageObjectManager = null;

	public ExpressPage(WebDriver driver, Wait<WebDriver> wait) {
		this.driver = driver;
		this.wait = wait;
		PageFactory.initElements(this.driver, this);
	}

}
