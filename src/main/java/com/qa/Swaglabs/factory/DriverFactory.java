package com.qa.Swaglabs.factory;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import com.qa.Swaglabs.utils.ReadProperty;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DriverFactory {

	    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
	    private static final Logger logger = LogManager.getLogger(DriverFactory.class);
	    private static ReadProperty readProperty;

	    static {
	        try {
	            readProperty = new ReadProperty();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }

	    public static WebDriver initDriver() {
	        if (driver.get() != null) {
	            return driver.get();
	        }
	        String browser = readProperty.getProperty("browser");
	        logger.info("Initializing WebDriver for browser: {}", browser);
	        switch (browser.toLowerCase()) {
	            case "chrome":
	                ChromeOptions options = new ChromeOptions();
	                options.addArguments("--disable-notifications");
	                driver.set(new ChromeDriver(options));
	                break;
	                
	            case "firefox":
	            	System.setProperty("webdriver.gecko.driver", "driver/firefoxdriver/geckodriver.exe");
	            	File f = new File("driver/firefoxdriver/geckodriver.exe");
		        	   System.out.println("GeckoDriver exists: " + f.exists() + " at " + f.getAbsolutePath());
		        	   System.out.println("Firefox version: " + System.getProperty("webdriver.firefox.bin"));
	                driver.set(new FirefoxDriver());
	                break;
	                
	            default:
	                logger.warn("Unsupported browser: {}. Defaulting to Chrome.", browser);
	                driver.set(new ChromeDriver());
	                break;
	        }
	        driver.get().manage().window().maximize();
	        driver.get().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	        logger.info("WebDriver initialized and configured.");
	        return driver.get();
	    }

	    public static WebDriver getDriver() {
	        return driver.get();
	    }

	    public static void quitDriver() {
	        if (driver.get() != null) {
	            logger.info("Quitting WebDriver instance.");
	            driver.get().quit();
	            driver.remove();
	            logger.info("WebDriver instance quit and removed from ThreadLocal.");
	        } else {
	            logger.warn("No WebDriver instance found to quit for this thread.");
	        }
	    }
}

