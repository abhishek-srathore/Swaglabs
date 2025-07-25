package com.qa.Swaglabs.testUtils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

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

		private static Map<String, ExtentReports> reportsMap = new ConcurrentHashMap<>();
		private static Map<String, ExtentTest> currentTestMap = new ConcurrentHashMap<>();
		private static Map<String, String> reportNameMap = new ConcurrentHashMap<>();
		private String reportName;					// ThreadLocal for report file name
		
		// Flag to ensure cleanup is done only once
		private static boolean isCleaned = false; // Ensure cleanup is done only once.
		public static ReadProperty readProperty;	// ReadProperty utility for config values
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

		    String browser = context.getCurrentXmlTest().getParameter("browser");
		    if (browser == null) {
		        browser = readProperty.getProperty("browser");
		    }
		    
		    // Generate unique report name
		    String timeStamp = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());
		    String suiteName = context.getSuite().getName();
		    String uniqueReportName = "Test-Report-" + browser + timeStamp + ".html";
		    String reportPath = ".\\reports\\" + uniqueReportName;
		    reportName = uniqueReportName;

		    // Create an instance of ExtentSparkReporter
		    ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);
		    sparkReporter.config().setDocumentTitle("Automation Report");
		    sparkReporter.config().setReportName("Functional Testing - " + suiteName + "-" + browser);
		    sparkReporter.config().setTheme(Theme.DARK);

		    // Set custom logo for the report 
		    String logoPath = System.getProperty("user.dir") + "\\src\\test\\resources\\logo\\logoNew.png" ;
		    String logoHTML = "<img src='" + logoPath + "' alt='Custom Logo' style='width: 30px; height: auto;' />";
		    sparkReporter.config().setReportName("<html><body>" + logoHTML + "<br/>Functional Testing - " + suiteName +"<br/>" +browser.toUpperCase() + "</body></html>");

		    ExtentReports extent = new ExtentReports();
		    extent.attachReporter(sparkReporter);

		    // Set system info from properties file
		    setSystemInfo(extent, context);
		    reportsMap.put(browser, extent);
		    reportNameMap.put(browser, reportPath);
		}
		
		public static ExtentTest getTest() {
	        return currentTestMap.get(readProperty.getProperty("browser")); // This method is no longer needed as per new logic
	    }

		// Called when a test passes, logs result and adds screenshot
		@Override
		public void onTestSuccess(ITestResult result) {
		    createTestEntry(result);
		    getCurrentTest(result).log(Status.PASS, "Test method " + result.getMethod().getMethodName() + " passed successfully.");

		    // Capture and add screenshot after success
		    addScreenshot(result);
		}

		// Called when a test fails, logs result and adds screenshot
		@Override
		public void onTestFailure(ITestResult result) {
		    createTestEntry(result);
		    getCurrentTest(result).log(Status.FAIL, "Test method " + result.getMethod().getMethodName() + " failed");
		    getCurrentTest(result).log(Status.INFO, "Failure reason: " + result.getThrowable().getMessage());

		    // Capture and add screenshot after failure
		    addScreenshot(result);
		}

		// Called when a test is skipped, logs result and adds screenshot
		@Override
		public void onTestSkipped(ITestResult result) {
		    createTestEntry(result);
		    getCurrentTest(result).log(Status.SKIP, "Test method " + result.getMethod().getMethodName() + " was skipped");
		    getCurrentTest(result).log(Status.INFO, "Skipped due to: " + result.getThrowable().getMessage());

		    // Capture and add screenshot after skip
		    addScreenshot(result);
		}

		// Creates a new test entry in the report for the current test method
		private void createTestEntry(ITestResult result) {
			
				ExtentReports extent = getExtentReports(result);
			    // Extract test description (use as Title)
			    String testTitle = result.getMethod().getDescription();
			    if (testTitle == null || testTitle.trim().isEmpty()) {
			        testTitle = result.getMethod().getMethodName(); // fallback
			    }

			    // Use method name as the description (visible under the title)
			    String methodDescription = "Method: " + result.getMethod().getMethodName();

			    // Create the Extent test with title and method description
			    ExtentTest test = extent.createTest(testTitle, methodDescription);
			    setCurrentTest(result, test);

			    // Assign groups (categories)
			    getCurrentTest(result).assignCategory(result.getMethod().getGroups());
			
		}
		
		
		

		// Called after all tests finish, flushes the report
		@Override
		public void onFinish(ITestContext context) {
		    String browser = context.getCurrentXmlTest().getParameter("browser");
		    if (browser == null) {
		        browser = readProperty.getProperty("browser");
		    }
		    ExtentReports extent = reportsMap.get(browser);
		    if (extent != null) {
		        extent.flush();
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

		    private ExtentReports getExtentReports(ITestResult result) {
		        String browser = result.getTestContext().getCurrentXmlTest().getParameter("browser");
		        if (browser == null) {
		            browser = readProperty.getProperty("browser");
		        }
		        return reportsMap.get(browser);
		    }

		    private void setCurrentTest(ITestResult result, ExtentTest test) {
		        String browser = result.getTestContext().getCurrentXmlTest().getParameter("browser");
		        if (browser == null) {
		            browser = readProperty.getProperty("browser");
		        }
		        currentTestMap.put(browser, test);
		    }

		    private ExtentTest getCurrentTest(ITestResult result) {
		        String browser = result.getTestContext().getCurrentXmlTest().getParameter("browser");
		        if (browser == null) {
		            browser = readProperty.getProperty("browser");
		        }
		        return currentTestMap.get(browser);
		    }

		    private void addScreenshot(ITestResult result) {
		        String screenshotPath = ScreenshotUtil.captureScreenshot(com.qa.Swaglabs.factory.DriverFactory.getDriver(), result);
		        if (screenshotPath != null) {
		            getCurrentTest(result).log(Status.INFO, "Screenshot: " + getCurrentTest(result).addScreenCaptureFromPath(screenshotPath));
		        }
		    }
		}


