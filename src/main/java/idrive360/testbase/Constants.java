package idrive360.testbase;

import idrive360.utilities.DateNTime;

public class Constants {
	static public final String CHROME = "chrome";
	static public final String FIREFOX = "firefox";
	static public final String EDGE = "edge";
	static public final String SAFARI = "safari";
	static DateNTime date = new DateNTime();

	// Browsers
	static public enum BROWSER {
		CHROME, FIREFOX, EDGE, SAFARI
	}

	// URLs
	static public final String IDrive360URL = "www.idrive360.com";
	static public final String loginURL = "https://www.idrive360.com/enterprise/login";
	public static final String IDrive360URL_SIGNUP = "https://www.idrive360.com/enterprise/signup";
	public static final String IDrive360_Logout = "https://www.idrive360.com/enterprise/logout";

	static public int projectTimeout = 60;

	// Path of the messages file
	public static final String MESSAGES_FILE_PATH = "src/main/resources/messages.properties";

	// Identifier for the automation machine
	public static final String AUTOMATION_SERVER = "SamanthDAVDesktop";
	public static final String TEST_SERVER_25 = "testserver25";

	public static final String TEST_SERVER_IP = "66.51.26.25";

	// Path of the local excel files
	public static final String FILENAME_ACCOUNTS_FOR_TESTING = "IDrive360_Accounts_for_testing.xlsx";
	public static final String FILENAME_RESULTS_OF_TESTING = "IDrive360_Results_Of_Testing.xlsx";

	// This is to be passed
	public static final int PROJECT_THRESHOLD = 5;

	public static final String WINDOWS_EMAILABLE_REPORT = null;
	public static final String MAC_EMAILABLE_REPORT = null;

	// ExtentReport
	public static final String EXTENTREPORTFILENAME = "ExtentReport_"
			+ date.printCurrentDateTime().replace("/", "_").replace(":", "_").replace(" ", "_") + ".html";
	public static final String EXTENTREPORTFILEPATH = System.getProperty("user.dir")
			.concat(System.getProperty("file.separator")).concat("test-output")
			.concat(System.getProperty("file.separator")).concat("ExtentReport")
			.concat(System.getProperty("file.separator"));

	// PageTitles
	public static final String LOGIN_PAGE_TITLE = "IDrive® 360 - Sign in to your account";
	public static final String SIGNUP_PAGE_TITLE = "Register with IDrive® 360 | Sign up for an account";
	public static final String BACKUP_CONSOLE_PAGE_TITLE = "IDrive® 360 Backup Console for centralized management";
	public static final String MANAGEMENT_CONSOLE_PAGE_TITLE = "IDrive® 360 Management Console";
	public static final String MY_ACCOUNT_PAGE_TITLE = "IDrive 360 account details";
	
//	public static final String FAILED_TEST_SS = System.getProperty("user.home")
//			.concat(System.getProperty("file.separator")).concat("WebAutomation")
//			.concat(System.getProperty("file.separator")).concat("IDrive360")
//			.concat(System.getProperty("file.separator")).concat("Resources")
//			.concat(System.getProperty("file.separator")).concat("Screenshots")
//			.concat(System.getProperty("file.separator")).concat("FailedTests")
//			.concat(System.getProperty("file.separator")).toLowerCase();
	
//	public static final String PASSED_TEST_SS = System.getProperty("user.home")
//			.concat(System.getProperty("file.separator")).concat("WebAutomation")
//			.concat(System.getProperty("file.separator")).concat("IDrive360")
//			.concat(System.getProperty("file.separator")).concat("Resources")
//			.concat(System.getProperty("file.separator")).concat("Screenshots")
//			.concat(System.getProperty("file.separator")).concat("PassedTests")
//			.concat(System.getProperty("file.separator")).toLowerCase();
	
	public static final String SCREENSHOTS = System.getProperty("user.home")
			.concat(System.getProperty("file.separator")).concat("WebAutomation")
			.concat(System.getProperty("file.separator")).concat("IDrive360")
			.concat(System.getProperty("file.separator")).concat("Resources")
			.concat(System.getProperty("file.separator")).concat("Screenshots")
			.concat(System.getProperty("file.separator")).toLowerCase();
	
	public static final String RESOURCE_HOME = System.getProperty("user.home")
			.concat(System.getProperty("file.separator")).concat("WebAutomation")
			.concat(System.getProperty("file.separator")).concat("IDrive360")
			.concat(System.getProperty("file.separator")).concat("Resources")
			.concat(System.getProperty("file.separator")).toLowerCase();
	
	public static final String PROJECT_RESOURCE_HOME = System.getProperty("user.dir")
			.concat(System.getProperty("file.separator")).concat("src")
			.concat(System.getProperty("file.separator")).concat("main")
			.concat(System.getProperty("file.separator")).concat("Resources")
			.concat(System.getProperty("file.separator")).toLowerCase();
}
