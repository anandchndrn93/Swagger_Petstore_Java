package pet.helper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

public class Listners implements ITestListener {
	ExtentReports extent = ExtentReport.getReport();
	ExtentTest test;
	public static ThreadLocal<ExtentTest> thread = new ThreadLocal<ExtentTest>();
	private static Logger log = LogManager.getLogger(Listners.class.getName());

	@Override
	public void onTestStart(ITestResult result) {
//To run on start of testcase
// on start of test start extent reporting
		String testCaseName = getTestName(result);
		test = extent.createTest(testCaseName);
		thread.set(test);
		log.info("starting Test Case: " + result.getMethod().getMethodName());
		getReporter().log(Status.INFO, "starting Test Case: " + result.getMethod().getMethodName());
		ITestListener.super.onTestStart(result);
	}

	@Override
	public void onTestSuccess(ITestResult result) {
// To run when a test has passed. marks the test as passed on extent report
		getReporter().log(Status.PASS, "Test Passed");
		log.debug("Test Passed");
		ITestListener.super.onTestSuccess(result);
	}

	@Override
	public void onTestFailure(ITestResult result) {
//To run when a test has failed. marks the test as failed on extent report
		log.fatal(result.getThrowable());
		result.getThrowable().printStackTrace();
		getReporter().log(Status.FAIL, "Test Failed Abruptly");
		String methodName = result.getMethod().getMethodName();
		log.fatal("Test Failed Abruptly. Test name: " + methodName);
		ITestListener.super.onTestFailure(result);
	}

	@Override
	public void onTestSkipped(ITestResult result) {
//To run when a test has skipped. marks the test as skipped on extent report
		String testCaseName = result.getMethod().getMethodName();
		getReporter().log(Status.SKIP, "Test was Skipped");
		getReporter().skip(result.getThrowable());
		log.fatal("Test was skipped!. Test name: " + testCaseName);
		ITestListener.super.onTestSkipped(result);
	}

	@Override
	public void onFinish(ITestContext context) {
//To run when test has finished. flush extent report.

		extent.flush();
		ITestListener.super.onFinish(context);
	}

	public static ExtentTest getReporter() {
// ensure thread safety
		return thread.get();
	}

	private String getTestName(ITestResult result) {
//method to get arguments received by each test method and update the test case name with the argument
		Object param = "";
		String testCaseName;

//		if (result.getParameters().length != 0) {
		for (Object parameter : result.getParameters()) {
			param = parameter;
		}
		testCaseName = result.getMethod().getMethodName() + "-" + param;
//		} else
//			testCaseName = result.getMethod().getMethodName();
		return testCaseName;
	}

}
