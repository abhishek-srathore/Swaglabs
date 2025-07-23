package com.qa.Swaglabs.tests;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;


import com.qa.Swaglabs.base.BaseTest;

@Listeners
public class LoginPageTest extends BaseTest {
	
private static final Logger logger = LogManager.getLogger(LoginPageTest.class);

@BeforeMethod
public void goToLoginPage() {
    com.qa.Swaglabs.factory.DriverFactory.getDriver().get(readProp.getProperty("testurl"));
}


@Test(priority = 1)
public void verifyUrl() {
	logger.info("Running test: verifyUrl");
	String urlName = loginPage.getUrl();
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
	
@Test(dataProvider= "invalidCredentials", priority = 1)
public void verifyWrongCreds(String username, String password, String message) {
	System.out.println("Trying with: [" + username + "] and [" + password + "]");
	String errorMsg = loginPage.validateWrongCredentials(username, password);
	Assert.assertEquals(errorMsg, message);
}


@Test(priority = 3)
public void verifyLogin() {
	logger.info("Verifying correct logins");
	loginPage.insertCredential(readProp.getProperty("username"),readProp.getProperty("password"));
}



}
