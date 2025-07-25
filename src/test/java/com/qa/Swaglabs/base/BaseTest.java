package com.qa.Swaglabs.base;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;

import org.testng.annotations.Parameters; 
import com.qa.Swaglabs.factory.DriverFactory;
import com.qa.Swaglabs.pages.HomePage;
import com.qa.Swaglabs.pages.LoginPage;
import com.qa.Swaglabs.pages.ProductDetail;
import com.qa.Swaglabs.utils.ReadProperty;

	
// Base test class for setting up and tearing down WebDriver and test environment

public class BaseTest {

		DriverFactory df;
		public static ReadProperty readProp;										// Utility for reading properties from config file
		public static com.aventstack.extentreports.ExtentTest test;						// ExtentTest instance for reporting
		private static final Logger logger = LogManager.getLogger(BaseTest.class); 		// Logger for logging test events
		public LoginPage loginPage;
		public HomePage homePage;
		public ProductDetail productDetail;

		// Static block to initialize ReadProperty utility
		static {
		    try {
		        readProp = new ReadProperty();
		    } catch (IOException e) {
		        e.printStackTrace();
		    }
		}
		
		// Setup method to initialize WebDriver and open base URL before tests
		@Parameters("browser")
		@BeforeClass
		public void setup(@Optional("") String browser ) throws IOException {
		    logger.info("Test setup started.");
		    DriverFactory.initDriver(browser);
		    
		    String baseUrl = readProp.getProperty("testurl");
		    logger.info("Navigating to base URL: {}", baseUrl);
		    DriverFactory.getDriver().get(baseUrl);
		    loginPage = new LoginPage(DriverFactory.getDriver());
		    homePage = new HomePage(DriverFactory.getDriver());
		    productDetail = new ProductDetail(DriverFactory.getDriver());
		    logger.info("Test setup completed.");
		}

		// Tear down method to quit WebDriver after tests
		@AfterClass(alwaysRun = true)
		public void tearDown() {
		    logger.info("Test teardown started.");
		    DriverFactory.quitDriver();
		    logger.info("Test teardown completed.");
		}

}





