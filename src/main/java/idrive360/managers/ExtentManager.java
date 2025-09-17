package idrive360.managers;

import java.io.File;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentManager {

	private static ExtentReports extent;

	public static ExtentReports getInstance() {
		if (extent == null) {
			try {
				String reportDir = System.getProperty("user.dir") + "/test-output/reports";
				File dir = new File(reportDir);
				if (!dir.exists())
					dir.mkdirs();

				String reportPath = reportDir + "/ExtentReport.html";

				ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);
				spark.config().setReportName("Automation Test Report");
				spark.config().setDocumentTitle("Test Results");

				extent = new ExtentReports();
				extent.attachReporter(spark);
				extent.setSystemInfo("Framework", "Playwright");
				extent.setSystemInfo("Tester", "QA Engineer");

			} catch (Exception e) {
				System.err.println("❌ [ExtentManager] Failed to initialize ExtentReports: " + e.getMessage());
				e.printStackTrace();
			}
		}
		return extent;
	}
}
