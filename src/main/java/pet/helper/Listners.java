
package pet.helper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

/**
 * This is class is meant for implementation of testNg Listeners, helpful in
 * creating test report. Methods here listens to tests run and performs actions
 * based on test status.
 * 
 * @author Anand Chandran
 */
public class Listners implements ITestListener {
	private ExtentReports extent = ExtentReport.getReport();
	private static ExtentTest test;
	private static Logger log = LogManager.getLogger(Listners.class.getName());

	/**
	 * To run on start of testcase. On start of test start extent reporting
	 */
	@Override
	public void onTestStart(ITestResult result) {
		String testCaseName = getTestName(result);
		test = extent.createTest(testCaseName);
//		thread.set(test);
		log.info("starting Test Case: " + testCaseName);
		getReporter().log(Status.INFO, "starting Test Case: " + result.getMethod().getMethodName());
		ITestListener.super.onTestStart(result);
	}

	/**
	 * To run when a test has passed. marks the test as passed on extent report
	 */
	@Override
	public void onTestSuccess(ITestResult result) {

		String testCaseName = getTestName(result);
		getReporter().log(Status.PASS, "Test Passed :" + testCaseName);
		log.debug("Test Passed");
		ITestListener.super.onTestSuccess(result);
	}

	/**
	 * To run when a test has failed. marks the test as failed on extent report
	 */
	@Override
	public void onTestFailure(ITestResult result) {

		log.fatal(result.getThrowable());
		result.getThrowable().printStackTrace();
		getReporter().log(Status.FAIL, "Test Failed Abruptly");
		String methodName = result.getMethod().getMethodName();
		log.fatal("Test Failed Abruptly. Test name: " + methodName);
		ITestListener.super.onTestFailure(result);
	}

	/**
	 * To run when a test has skipped. marks the test as skipped on extent report
	 */
	@Override
	public void onTestSkipped(ITestResult result) {

		String testCaseName = result.getMethod().getMethodName();
		getReporter().log(Status.SKIP, "Test was Skipped");
		getReporter().skip(result.getThrowable());
		log.fatal("Test was skipped!. Test name: " + testCaseName);
		ITestListener.super.onTestSkipped(result);
	}

	/**
	 * To run when test has finished. flush extent report.
	 */
	@Override
	public void onFinish(ITestContext context) {
		extent.flush();
		ITestListener.super.onFinish(context);
	}

	/**
	 * Method to be accessed from other classes to add logs to test report
	 * 
	 * @return Instance of ExtentReports
	 */
	public static ExtentTest getReporter() {
		return test;
	}

	/**
	 * Method to get arguments received by each test method and update the test case
	 * name with the argument
	 * 
	 * @param result Results of a test
	 * @return Test case name
	 */
	private String getTestName(ITestResult result) {
		Object param = "";
		String testCaseName;

		for (Object parameter : result.getParameters()) {
			param = parameter;
		}
		testCaseName = result.getMethod().getMethodName() + "-" + param;
		return testCaseName;
	}

}
