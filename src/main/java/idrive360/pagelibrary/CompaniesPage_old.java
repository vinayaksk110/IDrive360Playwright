package idrive360.pagelibrary;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Wait;

public class CompaniesPage_old {
	private WebDriver driver = null;
	private Wait<WebDriver> wait = null;

	public CompaniesPage_old(WebDriver driver) {
		this.driver = driver;
		this.wait = wait;
		PageFactory.initElements(this.driver, this);
	}

	public @FindBy(xpath = "//li[@class='id-ico-overview active']") WebElement Overview;

	public @FindBy(id = "2") WebElement Units;

	public @FindBy(linkText = "Add Unit") WebElement AddUnit;

	public @FindBy(id = "unit_name") WebElement UnitNameField;

	public @FindBy(id = "createbutton") WebElement btnCreate;

	public @FindBy(xpath = "//div[@class='id-alert success id-show']/p") WebElement msgSuccess;

	public WebElement unitnameinlisting(String unittobedeleted) {
		return this.driver.findElement(By
				.xpath("//div[@class='id-td id-ut-name id-ur-icn']/span[contains(@title,'" + unittobedeleted + "')]"));

	}

	public WebElement Delete(String unittobedeleted) {
		return this.driver.findElement(By.xpath("//span[contains(@title,'" + unittobedeleted
				+ "')]/parent::div[@class='id-td id-ut-name id-ur-icn']//following-sibling::div[@class='id-td id-ut-action id-ut-mble-action']/ul[@class='id-ut-sctn']/li[@class='id-ut-act id-ut-dlt id-tkn-btn']/a"));
	}

	// check box in the Delete Unit pop-up from unit actions menu
	public @FindBy(xpath = "//div[@class='id-check-container id-smcheck-container']//span") WebElement chxbxDelete;

	// Button Delete in the Delete Unit pop-up
	public @FindBy(linkText = "Delete") WebElement btnDelete;

}
