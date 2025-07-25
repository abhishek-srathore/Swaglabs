package com.qa.Swaglabs.pages;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.Swaglabs.utils.ElementUtil;
import com.qa.Swaglabs.utils.ReadProperty;

/**
 * LoginPage class represents the login page of SwagLabs.
 * Provides methods to interact with and verify elements on the login page.
 */
public class LoginPage {
	
	private WebDriver driver;
	ElementUtil eutil;
	ReadProperty readProp;
	
	// Logger for logging actions in LoginPage
    private static final Logger logger = LogManager.getLogger(LoginPage.class);
	
	/**
     * Constructor to initialize LoginPage with WebDriver, ElementUtil, and ReadProperty.
     * @param driver The WebDriver instance
     * @throws IOException if the property file cannot be read
     */
	public LoginPage(WebDriver driver) throws IOException {
		this.driver = driver;
		eutil = new ElementUtil(driver);
		readProp = new ReadProperty();
		logger.info("LoginPage initialized");
	}
	
	// Locators for login page elements
	public By username = By.xpath( "//input[@id='user-name']");
	public By password =  By.xpath("//input[@id='password']");
	public By loginButton =  By.xpath("//input[@id='login-button']");
	public By rightMenu=  By.xpath("//button[text()= 'Open Menu']");
	public By logout =  By.xpath("//a[@id = 'logout_sidebar_link']");
	public By productPage =  By.xpath("//div[@id='inventory_filter_container']/child::div[text()='Products']");
	public By errorMsg = By.xpath("//h3[@data-test='error']");
	

	/**
     * Gets the current login page URL.
     * @return The current URL as a String
     */
	public String getLoginUrl() {
		logger.info("Getting current page URL");
		String url = driver.getCurrentUrl();
		logger.info("Current page URL: {}", url);
		return url;	
	}
	
	/**
     * Attempts to log in with invalid credentials and returns the error message.
     * @param email The username/email to use
     * @param passw The password to use
     * @return The error message displayed on the login page
     */
	public String insertInvalidCredentials(String email, String passw) {
		logger.info("Validating wrong credentials: email={}, password=****", email);
		eutil.WaitForElementVisiblity(username, 10);
		eutil.WaitForElementVisiblity(password, 10);
		eutil.doSendKeys(username, email);
		eutil.doSendKeys(password, passw);
		eutil.doClick(loginButton);
		eutil.WaitForElementVisiblity(errorMsg, 10);
		String msg = eutil.getText(errorMsg);
		logger.info("Error message displayed: {}", msg);
		return msg;
		
	}
		
	/**
     * Attempts to log in with valid credentials and returns the HomePage instance.
     * @param email The username/email to use
     * @param passw The password to use
     * @return HomePage instance if login is successful
     */
	public HomePage insertValidCredential(String email, String passw) {
		logger.info("Sending correct credentials: email={}, password=****", email);
		eutil.WaitForElementVisiblity(username, 10);
		eutil.WaitForElementVisiblity(password, 10);
		eutil.doSendKeys(username, email);
		eutil.doSendKeys(password, passw);
		eutil.doClick(loginButton);
		logger.info("Login successful, returning HomePage instance");
		return new HomePage(driver);
	}
		


}
