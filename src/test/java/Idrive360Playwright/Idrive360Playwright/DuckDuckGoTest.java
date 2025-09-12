package Idrive360Playwright.Idrive360Playwright;


import org.testng.Assert;
import org.testng.annotations.Test;

import idrive360.testbase.BaseTest;
import idrive360.utilities.ExtentReportManager;


public class DuckDuckGoTest extends BaseTest {

    @Test
    public void testSearch() {
        DuckDuckGoPage duckPage = new DuckDuckGoPage(page());

        duckPage.navigate();
        ExtentReportManager.logInfo("Navigated to DuckDuckGo");

        duckPage.search("Playwright Java");
        ExtentReportManager.logInfo("Performed search for 'Playwright Java'");

        String result = duckPage.getFirstResultText();
        ExtentReportManager.logInfo("First result: " + result);

        Assert.assertNotNull(result);
    }
}