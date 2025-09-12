package idrive360.managers;

import com.microsoft.playwright.Page;

import idrive360.data.UserCredentials;
import idrive360.pagelibrary.AccountPage;
import idrive360.pagelibrary.ActivityLogsPage;
import idrive360.pagelibrary.BackupPlanPage;
import idrive360.pagelibrary.CompaniesPage;
import idrive360.pagelibrary.ExpressPage;
import idrive360.pagelibrary.HeaderFunctionalityPage;
import idrive360.pagelibrary.IndexPage;
import idrive360.pagelibrary.LHSOptionsPage;
import idrive360.pagelibrary.LoginPage;
import idrive360.pagelibrary.OverviewPage;
import idrive360.pagelibrary.SignupPage;
import idrive360.pagelibrary.UsersPage;
import idrive360.pagelibrary.devices.ComputerDevicePage;
import idrive360.pagelibrary.devices.DevicePage;
import idrive360.testbase.BaseTest;

public class PageObjectManager extends BaseTest {
	
	private final Page page;

	IndexPage indexPage = null;
	private LoginPage loginPage;
	ComputerDevicePage computerDevicePage = null;
	DevicePage devicePage = null;
	HeaderFunctionalityPage headerFunctionalityPage = null;
	UserCredentials userCredentials = null;
	PageObjectManager pageObjectManager = null;
	AccountPage accountPage = null;
	SignupPage signupPage = null;
	LHSOptionsPage lhsOptionsPage = null;
	CompaniesPage companiesPage = null;
	UsersPage usersPage = null;
	OverviewPage overviewPage = null;
	ExpressPage expressPage = null;
	BackupPlanPage backupPlanPage = null;
	ActivityLogsPage activityLogsPage = null;
	

	public PageObjectManager(Page page) {
		this.page = page;
	}

//	public SignupPage getSignupPage() {
//		return (signupPage == null) ? signupPage = new SignupPage(page) : signupPage;
//	}
//
//	public IndexPage getIndexPage() {
//		return (indexPage == null) ? indexPage = new IndexPage(page) : indexPage;
//	}

//	public LoginPage getLoginPage() {
//		return (loginPage == null) ? loginPage = new LoginPage(page) : loginPage;
//	}
	
	public LoginPage getLoginPage() {
        if (loginPage == null) {
            loginPage = new LoginPage(page);
        }
        return loginPage;
    }

//	public LHSOptionsPage getLhsOptionsPage() {
//		return (lhsOptionsPage == null) ? lhsOptionsPage = new LHSOptionsPage(page) : lhsOptionsPage;
//	}
//
//	public ComputerDevicePage getComputerDevicePage() {
//		return (computerDevicePage == null) ? computerDevicePage = new ComputerDevicePage(page)
//				: computerDevicePage;
//	}
//
//	public DevicePage getDevicePage() {
//		return (devicePage == null) ? devicePage = new DevicePage(page) : devicePage;
//	}
//
//	public HeaderFunctionalityPage getHeaderFunctionalityPage() {
//		return (headerFunctionalityPage == null) ? headerFunctionalityPage = new HeaderFunctionalityPage(page)
//				: headerFunctionalityPage;
//	}
//
//	public AccountPage getAccountPage() {
//		return (accountPage == null) ? accountPage = new AccountPage(page) : accountPage;
//	}
//
//	public CompaniesPage getCompaniesPage() {
//		return (companiesPage == null) ? companiesPage = new CompaniesPage(page) : companiesPage;
//	}
//
//	public UsersPage getUsersPage() {
//		return (usersPage == null) ? usersPage = new UsersPage(page) : usersPage;
//	}
//
//	public OverviewPage getOverviewPage() {
//		return (overviewPage == null) ? overviewPage = new OverviewPage(page) : overviewPage;
//	}
//
//	public ExpressPage getExpressPage() {
//		return (expressPage == null) ? expressPage = new ExpressPage(page) : expressPage;
//	}
//
//	public BackupPlanPage getBackupPlanPage() {
//		return (backupPlanPage == null) ? backupPlanPage = new BackupPlanPage(page) : backupPlanPage;
//	}
//	
//	public ActivityLogsPage getActivityLogsPage() {
//		return (activityLogsPage == null) ? activityLogsPage = new ActivityLogsPage(page) : activityLogsPage;
//	}

	public void setUserCredentials(UserCredentials userCredentials) {
		this.userCredentials = userCredentials;
	}

	public UserCredentials getUserCredentials() {
		return userCredentials;
	}
}