package idrive360.PlaywrightFactory;

import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;

import com.microsoft.playwright.*;

import idrive360.testbase.Constants;
import idrive360.utilities.CheckIPAddress;
import idrive360.utilities.ExcelUtilityNew;
import idrive360.utilities.LocalExcelUtility;

public class PlaywrightFactory {

	// Logger
	public static Logger log = LogManager.getLogger(PlaywrightFactory.class.getName());

	// ExcelUtility
	protected ExcelUtilityNew excelUtility = null;
	protected LocalExcelUtility localExcelUtility = null;
	String accountsWorkBookName = Constants.FILENAME_ACCOUNTS_FOR_TESTING;

	protected CheckIPAddress checkIPAddress = new CheckIPAddress();

	private static final ThreadLocal<Playwright> playwright = new ThreadLocal<>();
	private static final ThreadLocal<Browser> browserInstance = new ThreadLocal<>();
	private static final ThreadLocal<BrowserContext> context = new ThreadLocal<>();
	private static final ThreadLocal<Page> page = new ThreadLocal<>();

	/**
	 * Initializes the browser with optional overrides.
	 *
	 * @param excelFileStatus      Excel status (not used directly here, but can be
	 *                             used for logging or logic)
	 * @param browser              Browser type: "chromium", "firefox", "webkit"
	 * @param headless             Headless mode: true/false
	 * @param executionEnvironment "local", "ci", "remote", etc.
	 */
	public void initBrowser(String excelFileStatus, String browser, boolean headless, String executionEnvironment) {
		String os = System.getProperty("os.name");
		log.info("Environment Operating System: " + os);
		
		localExcelUtility = new LocalExcelUtility();

		String browserName = browser.toLowerCase();
		boolean isHeadless = headless;
		String environment = executionEnvironment.toLowerCase();

		playwright.set(Playwright.create());

		// ✅ Add --start-maximized only in headful mode
		BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions().setHeadless(isHeadless);

		if (!isHeadless && (browserName.equals("chrome") || browserName.equals("edge"))) {
			launchOptions.setArgs(List.of("--start-maximized"));
		}

		Browser browserLaunched;

		switch (browserName) {
		case "firefox":
			browserLaunched = playwright.get().firefox().launch(launchOptions);
			break;

		case "webkit":
			browserLaunched = playwright.get().webkit().launch(launchOptions);
			break;

		case "chrome":
			launchOptions.setChannel("chrome"); // ✅ Use Chrome channel
			browserLaunched = playwright.get().chromium().launch(launchOptions);
			break;

		case "edge":
			launchOptions.setChannel("msedge"); // ✅ Use Edge channel
			browserLaunched = playwright.get().chromium().launch(launchOptions);
			break;

		case "chromium":
		default:
			browserLaunched = playwright.get().chromium().launch(launchOptions);
			break;
		}

		// ✅ Create context in full screen (no viewport = use full available screen)
		Browser.NewContextOptions contextOptions = new Browser.NewContextOptions().setViewportSize(null); // disables
																											// viewport
																											// and uses
																											// screen
																											// size
		browserInstance.set(browserLaunched);
		context.set(browserLaunched.newContext(contextOptions));
		page.set(context.get().newPage());

		log.info("==========Initializing Test Environment==============");
		log.info(excelFileStatus + " : " + browser + " : " + headless + " : " + executionEnvironment);

		// ✅ Check if required Excel files are present
		try {
			if (verifyIfTheRequiredFileIsPresent(getWorkbookPath_AccountsForTesting())) {
				log.info("The required AccountsForTesting sheet is present");
				System.out.println("local excel from playwrightfactory"+localExcelUtility);
			} else {
				log.info("The required 'AccountsForTesting' sheet is not present");
				Assert.assertTrue(false,
						"The required 'AccountsForTesting' sheet is not present");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected String getResourceHome() {
		return Constants.RESOURCE_HOME;
	}

	protected String getProjectResourceHome() {
		return Constants.PROJECT_RESOURCE_HOME;
	}

	protected String getScreenshotsPath() {
		return Constants.SCREENSHOTS;
	}

	protected String getWorkbookPath_AccountsForTesting() {
		return getProjectResourceHome().concat(Constants.FILENAME_ACCOUNTS_FOR_TESTING);
	}

	// Check if the required file to upload the present in the required location
	public boolean verifyIfTheRequiredFileIsPresent(String fileName) {
		try {
			String filePath = fileName;
			log.info(filePath);
			Path path = Paths.get(filePath);
			return Files.exists(path) && Files.isRegularFile(path);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Exception occured while verifying the presence of file in the directory");
			return false;
		}

	}

	public String getMessage(String key) {
		Properties properties = new Properties();
		try (FileInputStream fis = new FileInputStream(Constants.MESSAGES_FILE_PATH)) {
			properties.load(fis);
			String message = properties.getProperty(key);
			return message;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Page getPage() {
		return page.get();
	}

	public static void closeBrowser() {
		if (page.get() != null)
			page.get().close();
		log.info("Page closed");
		if (context.get() != null)
			context.get().close();
		log.info("Context closed");
		if (browserInstance.get() != null)
			browserInstance.get().close();
		if (playwright.get() != null)
			playwright.get().close();
		log.info("Playwright closed");
	}
}
