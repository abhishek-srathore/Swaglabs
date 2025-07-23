package com.qa.Swaglabs.testUtils;


import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;

import java.io.File;
import java.io.IOException;


//Utility class for capturing and saving screenshots during tests
// Now thread-safe: always use the WebDriver instance passed from DriverFactory.getDriver()
public class ScreenshotUtil {
		

// Captures a screenshot for the given test result and returns the file path
public static String captureScreenshot(WebDriver driver, ITestResult result) {
        String methodName = result.getMethod().getMethodName().trim();
        String screenshotDir = System.getProperty("user.dir") + File.separator + "screenshots" + File.separator;
        
        // Ensure the screenshot directory exists
        File dir = new File(screenshotDir);
        if (!dir.exists()) {
            dir.mkdirs(); // Create the directory if it does not exist
        }
        
        // Capture screenshot
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String screenshotPath = screenshotDir + methodName + "_" + System.currentTimeMillis() + ".png";
        
        try {
            // Copy the screenshot to the defined path
            FileUtils.copyFile(screenshot, new File(screenshotPath));
            System.out.println("Screenshot saved to: " + screenshotPath);
        } catch (IOException e) {
            System.err.println("Failed to save screenshot: " + e.getMessage());
            return null; // Return null if screenshot could not be saved
        }
        
        return screenshotPath;
    }
}



