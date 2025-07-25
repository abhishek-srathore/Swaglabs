package com.qa.Swaglabs.factory;

import java.io.IOException;
import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import com.qa.Swaglabs.utils.ReadProperty;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * DriverFactory is responsible for initializing, providing, and quitting WebDriver instances.
 * It supports thread-safe driver management for parallel test execution and supports multiple browsers.
 */
public class DriverFactory {

    
     //ThreadLocal to ensure each thread gets its own WebDriver instance.
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private static final Logger logger = LogManager.getLogger(DriverFactory.class);
    private static ReadProperty readProperty;

    // Static block to initialize ReadProperty utility
    static {
        try {
            readProperty = new ReadProperty();
            logger.info("ReadProperty utility initialized successfully.");
        } catch (IOException e) {
            logger.error("Failed to initialize ReadProperty utility.", e);
            e.printStackTrace();
        }
    }

    /**
     * Initializes the WebDriver instance for the given browser.
     * If already initialized for the thread, returns the existing instance.
     * @param browserParam The browser to initialize (chrome, firefox, edge)
     * @return The initialized WebDriver instance
     */
    public static WebDriver initDriver(String browserParam) {
        if (driver.get() != null) {
            logger.debug("WebDriver already initialized for this thread. Returning existing instance.");
            return driver.get();
        }
        
        String browser;
        if (browserParam != null && !browserParam.trim().isEmpty()) {
            browser = browserParam;
            logger.info("Browser picked from TestNG parameter: {}", browser);
        } else {
            browser = readProperty.getProperty("browser");
            logger.info("Browser picked from config file: {}", browser);
        }
        
        logger.info("Initializing WebDriver for browser: {}", browser);
        switch (browser.toLowerCase()) {
            case "chrome":
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--disable-notifications");
                driver.set(new ChromeDriver(options));
                logger.info("ChromeDriver initialized.");
                break;
            
            case "firefox":
                System.setProperty("webdriver.gecko.driver", "driver/firefoxdriver/geckodriver.exe");
                driver.set(new FirefoxDriver());
                logger.info("FirefoxDriver initialized.");
                break;
            
            case "edge":
                System.setProperty("webdriver.edge.driver", "driver/edgedriver/msedgedriver.exe");
                driver.set(new EdgeDriver());
                logger.info("EdgeDriver initialized.");
                break;
            
            default:
                logger.warn("Unsupported browser: {}. Defaulting to Chrome.", browser);
                driver.set(new ChromeDriver());
                logger.info("ChromeDriver initialized as default.");
                break;
        }
        // Maximize window and set implicit wait
        driver.get().manage().window().maximize();
        driver.get().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        logger.info("WebDriver initialized and configured (window maximized, implicit wait set).");
        return driver.get();
    }

    /**
     * Returns the WebDriver instance for the current thread.
     * @return The WebDriver instance
     */
    public static WebDriver getDriver() {
        return driver.get();
    }

    /**
     * Quits the WebDriver instance for the current thread and removes it from ThreadLocal.
     */
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

