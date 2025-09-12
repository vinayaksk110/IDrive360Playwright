/**
 * Author : Vinayak
 * This is a template class to identify how to use the pagefactory methods along with the scripts.
 */
package scripts.login;

import org.testng.Assert;
import org.testng.annotations.Test;

import idrive360.data.UserCredentials;
import idrive360.pagelibrary.LoginPage;
import idrive360.testbase.BaseTest;

public class LoginToAccountWithDevices extends BaseTest {

	UserCredentials userCredentials;

//	public LoginToAccountWithDevices() {
//
//	}
//
//	public LoginToAccountWithDevices(PageObjectManager pageObjectManager) {
//		this.pageObjectManager = pageObjectManager;
//	}

	@Test
	public void testLoginIDrive360() {
		page.navigate("https://www.idrive360.com/enterprise/login");

		try {
			userCredentials = localExcelUtility.getCompleteSheetData(getWorkbookPath_AccountsForTesting(), "Login",
					"accountwithoutdevices");
			log.info(userCredentials.emailID + "  " + userCredentials.password);
			pageObjectManager.setUserCredentials(userCredentials);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.assertTrue(false, "Reached exception while getting user details");
		}
		System.out.println("local excel from login test"+localExcelUtility);
		LoginPage loginPage = pageObjectManager.getLoginPage();
		loginPage.loginToAccount(userCredentials);
		Assert.assertTrue(page.url().contains("/devices"), "Expected to land on dashboard post-login");
	}
}
