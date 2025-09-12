package idrive360.listeners;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import idrive360.PlaywrightFactory.PlaywrightFactory;
import idrive360.utilities.DateNTime;

public class TestResultsListeners extends PlaywrightFactory implements ITestListener {

	static DateNTime date = new DateNTime();

	@Override
	public void onTestStart(ITestResult result) {
		log.info(System.lineSeparator());
		log.info("================Test being executed: " + result.getMethod().getMethodName() + "============");
		ITestListener.super.onTestStart(result);
	}

	@Override
	public void onTestFailure(ITestResult result) {
		ITestListener.super.onTestFailure(result);
		if (!result.isSuccess())
			log.info(
					"================Testname: " + result.getMethod().getMethodName() + ", Test Failed==============>");
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		ITestListener.super.onTestSuccess(result);
		if (result.isSuccess())
			log.info(System.lineSeparator());
		log.info(
				"================Testname: " + result.getMethod().getMethodName() + ", Test successful==============>");
	}

	@Override
	public void onTestFailedWithTimeout(ITestResult result) {
		log.info("================Testname: " + result.getMethod().getMethodName()
				+ ", Test Failed With Timeout==============>");
		ITestListener.super.onTestFailedWithTimeout(result);
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		log.info("================Testname: " + result.getMethod().getMethodName() + ", Test skipped==============>");
		ITestListener.super.onTestSkipped(result);
	}

	@Override
	public void onFinish(ITestContext context) {
		ITestListener.super.onFinish(context);
		log.info("================All tests completed ============");
	}

}
