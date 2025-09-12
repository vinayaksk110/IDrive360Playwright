package idrive360.pagelibrary;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.testng.Assert;

import idrive360.managers.PageObjectManager;
import idrive360.testbase.TestBase;
import idrive360.utilities.TestResultsStatus;

public class ActivityLogsPage extends TestBase {

	private WebDriver driver = null;
	private Wait<WebDriver> wait = null;
	private PageObjectManager pageObjectManager = null;

	public ActivityLogsPage(WebDriver driver, Wait<WebDriver> wait) {
		this.driver = driver;
		this.wait = wait;
		PageFactory.initElements(this.driver, this);
	}

	// =========================================================Locators==========================================
	public @FindBy(id = "datepicker-range-start") WebElement inputStartDate;

	public @FindBy(id = "datepicker-range-end") WebElement inputEndDate;

	public @FindBy(id = "view-report") WebElement btnViewReport;

	public @FindBy(id = "reset") WebElement btnReset;

	public @FindBy(id = "download-pdf") WebElement btnDownloadPdf;

	public @FindBy(id = "filter") WebElement btnFilter;

	public @FindBy(id = "event-dropdown") WebElement btnEventDropdown;

	public @FindBy(id = "filter-apply") WebElement btnFilterApply;

	public @FindBy(id = "filter-clear") WebElement btnFilterClear;

	public @FindBy(id = "AuditLogtime") WebElement btnDateSort;

	public @FindBy(id = "scrollAudit") WebElement btnBackToTop;

	public @FindBy(xpath = "//div[@id='scrollAudit0']//div[@class='id-td id-ut-event']//span") WebElement firstRow;

	public @FindBy(xpath = "//div[@id='scrollAudit1']//div[@class='id-td id-ut-event']//span") WebElement secondRow;

	// ===================================================Methods==========================================

	public void inputStartDate(String date) {
		inputStartDate.sendKeys(date);
	}

	public void inputEndDate(String date) {
		inputEndDate.sendKeys(date);
	}

	public void clickViewReport() {
		btnViewReport.click();
	}

	public void clickReset() {
		btnReset.click();
	}

	public void clickDownloadPdf() {
		btnDownloadPdf.click();
	}

	public void clickFilter() {
		btnFilter.click();
	}

	public void clickEventDropdown() {
		btnEventDropdown.click();
	}

	public void clickFilterApply() {
		btnFilterApply.click();
	}

	public void clickFilterClear() {
		btnFilterClear.click();
	}

	public void toggleDateSort() {
		btnDateSort.click();
	}

	public void clickBackToTop() {
		btnBackToTop.click();
	}

	public TestResultsStatus getActivityLogContent(TestResultsStatus testResultsStatus) {
		try {
			pageObjectManager = new PageObjectManager(driver, wait);
			pageObjectManager.getLhsOptionsPage().waitForActivityLogsVisibilityAndClick(testResultsStatus);
			wait.until(ExpectedConditions.visibilityOf(btnViewReport));
			if (testResultsStatus.getTestName().equalsIgnoreCase("SignUpToIDrive360")) {
				wait.until(ExpectedConditions.visibilityOf(secondRow));
				testResultsStatus.setTestActivityLogContent(secondRow.getDomAttribute("title"));
			} else {
				wait.until(ExpectedConditions.visibilityOf(firstRow));
				testResultsStatus.setTestActivityLogContent(firstRow.getDomAttribute("title"));
			}

			log.info("Activity log received is: " + testResultsStatus.getTestActivityLogContent());
		} catch (Exception e) {
			String errorMessage = "Error while fetching activity log\n";
			log.error(errorMessage);
			e.printStackTrace();
			testResultsStatus.setTestResult("Fail");
			testResultsStatus.setTestResultComment(errorMessage + e.getMessage());
			Assert.assertTrue(false);
		}
		return testResultsStatus;
	}

	public TestResultsStatus AssertActivityLogs(String expectedLog, TestResultsStatus testResultsStatus) {

		try {
			getActivityLogContent(testResultsStatus);
			Assert.assertEquals(testResultsStatus.getTestActivityLogContent(), expectedLog);
			testResultsStatus.setTestResult("Pass");
			testResultsStatus.setTestResultComment(testResultsStatus.getTestActivityLogContent());
			log.info("Test passed");
		} catch (AssertionError ae) {
			String errorMessage = "Error asserting activity Logs, Expected log is : " + expectedLog
					+ ", but actual log is: " + testResultsStatus.getTestActivityLogContent();
			log.error(errorMessage);
			ae.printStackTrace();
			testResultsStatus.setTestResult("Fail");
			testResultsStatus.setTestResultComment(errorMessage);
			Assert.assertTrue(false);
		}
		return testResultsStatus;

	}

}
