
package pet.helper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

/**
 * Implementation for extend reports. Report document specifics such as name of
 * doc, format, report name are set here.
 * 
 * @author Anand Chandran
 */
public class ExtentReport {
	static ExtentReports extent;

	/**
	 * Creates a report document
	 * 
	 * @return Instance of ExtentReports class
	 */
	public static ExtentReports getReport() {
		DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
		LocalDateTime now = LocalDateTime.now();
		String path = System.getProperty("user.dir") + "\\test-output\\reports\\WoogaTestReport-"
				+ timeFormat.format(now) + ".html";

		ExtentSparkReporter reporter = new ExtentSparkReporter(path);
		reporter.config().setReportName("Swagger Petstore Results");
		reporter.config().setDocumentTitle("Wooga Take Home Test");

		extent = new ExtentReports();
		extent.attachReporter(reporter);
		return extent;
	}
}
