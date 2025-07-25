package com.qa.Swaglabs.tests;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.qa.Swaglabs.base.BaseTest;
import com.qa.Swaglabs.testUtils.ExtentListener;

/**
 * This class contains tests for the Login Page functionality.
 */
@Listeners
public class LoginPageTest extends BaseTest {
	
private static final Logger logger = LogManager.getLogger(LoginPageTest.class);

@BeforeMethod
public void goToLoginPage() {
    com.qa.Swaglabs.factory.DriverFactory.getDriver().navigate().refresh();;
}


@Test(description = "To verify the Login Page URL is correct", priority = 0)
public void verifyLoginUrl() {
	logger.info("Running test: verifyUrl");
	String urlName = loginPage.getLoginUrl();
	logger.info("Asserting URL: ===expected  " + readProp.getProperty("testUrl")+  "     ===actual '{}'   "   +  urlName);
	Assert.assertEquals(urlName, readProp.getProperty("testurl"));
	logger.info("Test verifyUrl passed");
}


@DataProvider
public Object[][] invalidCredentials(){
	return new Object[][] {
	{""		, ""	, "Epic sadface: Username is required" },
	{""		, "Test", "Epic sadface: Username is required"},
	{"Test"		, "", "Epic sadface: Password is required"},
	{"Test"	, "Test", "Epic sadface: Username and password do not match any user in this service"},
	};
}
	
@Test(description = "To verify error messages for invalid login credentials", dataProvider= "invalidCredentials", priority = 1)
public void verifyInvalidCreadentials(String username, String password, String message) {
	// Logging the test data to Extent Report
    ExtentListener.getTest().log(Status.INFO,
        "Test Data: <br>" +
        "<b>Username:</b> " + username + "<br>" +
        "<b>Password:</b> " + password + "<br>" +
        "<b>Expected Message:</b> " + message
    );
	System.out.println("Trying with: [" + username + "] and [" + password + "]");
	String errorMsg = loginPage.insertInvalidCredentials(username, password);
	Assert.assertEquals(errorMsg, message);
}


@Test(description = "To verify successful login with valid credentials", priority = 2)
public void verifyLogin() {
	logger.info("Verifying correct logins");
	loginPage.insertValidCredential(readProp.getProperty("username"),readProp.getProperty("password"));
}



}
