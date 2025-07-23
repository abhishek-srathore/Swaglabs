package com.qa.Swaglabs.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class JavaScriptUtil {
	// Utility class for common WebElement actions and waits
	private WebDriver driver;
	Actions actions;
	WebDriverWait wait;
	JavascriptExecutor js;
	
    private static final Logger logger = LogManager.getLogger(JavaScriptUtil.class);
	
	
	public JavaScriptUtil(WebDriver driver) {
		this.driver = driver;
		actions = new Actions(this.driver);
	}
	
	public void doClick(By locator) {
		logger.info("Clicking element by JS: {}", locator);
		WebElement WebEle = driver.findElement(locator);
		js.executeScript("arguments[0].click();", WebEle);
		logger.info("Clicked element by JS: {}", locator);
	}
	
	//Click double
	public void doClick(WebElement WebEle) {
		logger.info("Clicking WebElement by JS: {}", WebEle);
		js.executeScript("arguments[0].click();", WebEle);
		logger.info("Clicked WebElement by JS: {}", WebEle);
	}
	
	//Scroll to element by locator, WebElements 
	public void scrollToElement(By locator) {
		logger.info("Scrolling to element: {}", locator);
		WebElement webEle = driver.findElement(locator);
		js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth',block: 'center'});", webEle);
		logger.info("Scrolled to element: {}", locator);
	}
	
	public void scrollToElement(WebElement WebEle) {
		logger.info("Scrolling to WebElement: {}", WebEle);
		js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth',block: 'center'});",WebEle);
		logger.info("Scrolled to WebElement: {}", WebEle);
	}
			
	public boolean isPageLoaded(WebDriver driver) {
	    JavascriptExecutor js = (JavascriptExecutor) driver;
	    return js.executeScript("return document.readyState").equals("complete");
	}
	
	
	
	

}
