package com.qa.Swaglabs.testUtils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.qa.Swaglabs.utils.ReadProperty;


// TestNG listener for ExtentReports integration and screenshot capture
public class ExtentListener  implements ITestListener{


		
		private ExtentReports extentReports;		// ThreadLocal for ExtentReports instance (one per thread)
		private static ThreadLocal<ExtentTest> currentTest = new ThreadLocal<>();
		private String reportName;					// ThreadLocal for report file name
		
		// Flag to ensure cleanup is done only once
		private static boolean isCleaned = false; // Ensure cleanup is done only once.
		public static ReadProperty readProperty;										// ReadProperty utility for config values

		// Called before any test starts, sets up ExtentReports and cleans old reports/screenshots
		@Override
		public void onStart(ITestContext context) {
		    try {
		        readProperty = new ReadProperty();
		    } catch (IOException e) {
		        e.printStackTrace();
		    }

		    // Cleanup old reports and screenshots if not already done
		    if (!isCleaned) {
		        deleteOldReportsAndScreenshots();
		        isCleaned = true;
		    }

		    // Generate unique report name
		    String timeStamp = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());
		    String threadName = Thread.currentThread().getName();
		    String uniqueReportName = "Test-Report-" + timeStamp + "-" + threadName + ".html";
		    reportName = uniqueReportName;

		    // Create an instance of ExtentSparkReporter
		    String reportPath = ".\\reports\\" + uniqueReportName;
		    ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);
		    sparkReporter.config().setDocumentTitle("Automation Report");
		    sparkReporter.config().setReportName("Functional Testing - " + threadName);
		    sparkReporter.config().setTheme(Theme.DARK);

		    // Set custom logo for the report (commented out)
		    String logoPath = System.getProperty("user.dir") + "\\src\\test\\resources\\logo\\logo.jpeg" ;
		    String logoHTML = "<img src='" + logoPath + "' alt='Custom Logo' style='width: 80px; height: auto;' />";
		    sparkReporter.config().setReportName("<html><body>" + logoHTML + "<br/>Functional Testing - " + threadName + "</body></html>");

		    ExtentReports extent = new ExtentReports();
		    extent.attachReporter(sparkReporter);

		    // Set system info from properties file
		    setSystemInfo(extent, context);

		    extentReports = extent;
		}

		// Called when a test passes, logs result and adds screenshot
		@Override
		public void onTestSuccess(ITestResult result) {
		    createTestEntry(result);
		    currentTest.get().log(Status.PASS, "Test method " + result.getMethod().getMethodName() + " passed successfully.");

		    // Capture and add screenshot after success
		    addScreenshot(result);
		}

		// Called when a test fails, logs result and adds screenshot
		@Override
		public void onTestFailure(ITestResult result) {
		    createTestEntry(result);
		    currentTest.get().log(Status.FAIL, "Test method " + result.getMethod().getMethodName() + " failed");
		    currentTest.get().log(Status.INFO, "Failure reason: " + result.getThrowable().getMessage());

		    // Capture and add screenshot after failure
		    addScreenshot(result);
		}

		// Called when a test is skipped, logs result and adds screenshot
		@Override
		public void onTestSkipped(ITestResult result) {
		    createTestEntry(result);
		    currentTest.get().log(Status.SKIP, "Test method " + result.getMethod().getMethodName() + " was skipped");
		    currentTest.get().log(Status.INFO, "Skipped due to: " + result.getThrowable().getMessage());

		    // Capture and add screenshot after skip
		    addScreenshot(result);
		}

		// Creates a new test entry in the report for the current test method
		private void createTestEntry(ITestResult result) {
		    ExtentReports extent = extentReports;
		    currentTest.set(extent.createTest(result.getMethod().getMethodName(), result.getMethod().getDescription()));
		    currentTest.get().assignCategory(result.getMethod().getGroups());
		}

		// Called after all tests finish, flushes the report
		@Override
		public void onFinish(ITestContext context) {
		    if (extentReports != null) {
		        extentReports.flush();
		    }
		}

		//Delete old reports and ScreenShots
		private void deleteOldReportsAndScreenshots() {
		        String screenshotsDir = System.getProperty("user.dir") + File.separator + "screenshots" + File.separator;
		        String reportsDir = System.getProperty("user.dir") + File.separator + "reports" + File.separator;

		        // Delete old screenshots directory
		        deleteDirectory(new File(screenshotsDir));

		        // Delete old reports directory
		        deleteDirectory(new File(reportsDir));
		    }

		    private void deleteDirectory(File directory) {
		        if (directory.exists()) {
		            try {
		                FileUtils.deleteDirectory(directory);
		            } catch (IOException e) {
		                // Handle exception (if necessary)
		            }
		        }
		    }

		    private void setSystemInfo(ExtentReports extent, ITestContext context) {
		        extent.setSystemInfo("Name", readProperty.getProperty("testName"));
		        extent.setSystemInfo("Computer Name", readProperty.getProperty("computerName"));
		        extent.setSystemInfo("Environment", readProperty.getProperty("environment"));
		        extent.setSystemInfo("Tester Name", readProperty.getProperty("testerName"));

		        List<String> includedGroups = context.getCurrentXmlTest().getIncludedGroups();
		        if (!includedGroups.isEmpty()) {
		            extent.setSystemInfo("Groups", includedGroups.toString());
		        }
		    }

		    private void addScreenshot(ITestResult result) {
		        String screenshotPath = ScreenshotUtil.captureScreenshot(com.qa.Swaglabs.factory.DriverFactory.getDriver(), result);
		        if (screenshotPath != null) {
		            currentTest.get().log(Status.INFO, "Screenshot: " + currentTest.get().addScreenCaptureFromPath(screenshotPath));
		        }
		    }
		}


