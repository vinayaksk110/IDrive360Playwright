package idrive360.utilities;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class OurRetryAnalyzer implements IRetryAnalyzer {

	private int retryCount = 0;
	private static final int maxRetryCount = 3;

	public boolean retry(ITestResult result) {
		if (retryCount < maxRetryCount) {
			retryCount++;
			System.out.println("\n================");
			System.out.println("Retrying attempt: " + retryCount);
			System.out.println("================");
			return true;
		}
		return false;
	}

}
