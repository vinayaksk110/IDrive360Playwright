package idrive360.utilities;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.ScreenshotType;

public class TakeScreenShot {

	private static final String SCREENSHOT_DIR = "C:\\Users\\Vinayak\\eclipse-workspace\\Idrive360Playwright\\test-output\\screenshots";

	public static String capture(Page page, String screenshotName) {
		try {
			// Create screenshot directory if it doesn't exist
			Path dir = Paths.get(SCREENSHOT_DIR);
			if (!Files.exists(dir)) {
				Files.createDirectories(dir);
			}
			System.out.println(dir);

			// Create a unique file name with timestamp
			String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
			String fileName = screenshotName + "_" + timestamp + ".png";
			Path filePath = dir.resolve(fileName);

			// Take the screenshot
			page.screenshot(
					new Page.ScreenshotOptions().setPath(filePath).setFullPage(true).setType(ScreenshotType.PNG));

			System.out.println("[Screenshot] Saved: " + filePath.toAbsolutePath());

		} catch (IOException e) {
			System.err.println("Failed to capture screenshot: " + e.getMessage());
			e.printStackTrace();
		}
		return screenshotName;
	}
}
