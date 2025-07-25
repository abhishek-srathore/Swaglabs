package com.qa.Swaglabs.utils;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;
import org.openqa.selenium.support.ui.Select;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Utility class for common WebElement actions and waits.
 */
public class ElementUtil {

		// Utility class for common WebElement actions and waits
		private WebDriver driver;
		Actions actions;
		WebDriverWait wait;
		JavascriptExecutor js;
	    private static final Logger logger = LogManager.getLogger(ElementUtil.class);

		// Constructor initializes utilities for element actions
		public ElementUtil(WebDriver driver) {
			this.driver = driver;
			actions = new Actions(this.driver);
		}

		// Checks if the provided CharSequence is null
		private void nullCheck(CharSequence... str) {
			if (str == null) {
				throw new RuntimeException("===String cannot be null===");
			}
		}

		// Returns a single WebElement for the given locator
		public WebElement getElement(By locator) {
			logger.info("Getting element for locator: {}", locator);
			return driver.findElement(locator);
		}

		// Returns a list of WebElements for the given locator
		public List<WebElement> getElements(By locator) {
			logger.info("Getting elements for locator: {}", locator);
			return driver.findElements(locator);
		}
		
		// Waits for visibility of a single element by locator
		public WebElement WaitForElementVisiblity(By locator, int timeOut ) {
			logger.info("Waiting for visibility of element: {} for {} seconds", locator, timeOut);
			wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
			WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
			logger.info("Element visible: {}", locator);
			return element;
		}
		
		// Waits for visibility of multiple elements by locator
		public List<WebElement> waitForMultiElementsVisiblity(By locator, int timeOut) {
			logger.info("Waiting for visibility of multiple elements: {} for {} seconds", locator, timeOut);
			wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
			List<WebElement> elements = wait.until(ExpectedConditions.visibilityOfAllElements(getElements(locator)));
			logger.info("Multiple elements visible: {} (count: {})", locator, elements.size());
			return elements;
		}
		
		// Waits for visibility of a specific WebElement
		public WebElement WaitForElementVisiblity(WebElement element, int timeOut) {
			logger.info("Waiting for visibility of element: {} for {} seconds", element, timeOut);
			wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
			WebElement visibleElement = wait.until(ExpectedConditions.visibilityOf(element));
			logger.info("Element is visible: {}", element);
			return visibleElement;
		}
		
		// Clicks on the element found by locator
		public void doClick(By locator) {
			logger.info("Clicking element: {}", locator);
			getElement(locator).click();
			logger.info("Element clicked: {}", locator);
		}
		// Sends keys to the element found by locator
		public void doSendKeys(By locator, CharSequence... str) {
			logger.info("Sending keys to element: {}. Value: {}", locator, str);
			nullCheck(str);
			getElement(locator).sendKeys(str);
			logger.info("Keys sent to element: {}", locator);
		}
		
		public void clearField(By locator) {
			getElement(locator).clear();
		}

		// Checks if element is displayed, handles NoSuchElementException
		public boolean useIsDisplayed(By locator) {
			logger.info("Checking if element is displayed: {}", locator);
			boolean displayed;
			try {
				displayed = getElement(locator).isDisplayed();
			} catch (NoSuchElementException e) {
				logger.warn("Element not available on page: {}", locator);
				return false;
			}
			logger.info("Element displayed: {} = {}", locator, displayed);
			return displayed;
		}
		
		// Checks if element is enabled (not disabled)
		public boolean ElementDisabilty(By locator) {
			logger.info("Checking if element is enabled: {}", locator);
			boolean enabled = getElement(locator).isEnabled();
			logger.info("Element enabled: {} = {}", locator, enabled);
			return enabled;
			}
		
		
		// Performs hover action on the element found by locator
		public void hoverAction(By locator) {
			logger.info("Hovering over element: {}", locator);
			WebElement WebEle = getElement(locator);
			actions.moveToElement(WebEle).perform();
			logger.info("Hovered over element: {}", locator);
		}
		
		// Performs hover action on a specific WebElement
		public void hoverAction(WebElement element) {
			logger.info("Hovering over WebElement: {}", element);
			actions.moveToElement(element).perform();
			logger.info("Hovered over WebElement: {}", element);
		}

		// Performs hover action at a specific offset (not used in this project)
		public void hoverAction() {
			logger.info("Hovering at offset (5000, 10000)");
			actions.moveByOffset(5000, 10000);
			logger.info("Hovered at offset (5000, 10000)");
		}
		
		// Performs context click (right-click) on the element found by locator
		public void contextClick(By locator) {
			logger.info("Performing context click on element: {}", locator);
			actions.moveToElement(getElement(locator)).contextClick();
			logger.info("Context click performed on element: {}", locator);
		}
		// Gets the CSS value for a given property from the element found by locator
		public String getCssValue(By locator, String cssKey) {
			logger.info("Getting CSS value '{}' for element: {}", cssKey, locator);
			String cssValue = getElement(locator).getCssValue(cssKey);
			logger.info("CSS value for '{}' on element {}: {}", cssKey, locator, cssValue);
			return cssValue;
		}
		
		// Returns the count of elements found by locator
		public int checkElementCount(By locator) {
			logger.info("Checking element count for locator: {}", locator);
			List<WebElement> count =  getElements(locator);
			logger.info("Element count for locator {}: {}", locator, count.size());
			return count.size();
		}
		
		// Selects an option from a dropdown by visible text
		public void selectElement(By locator, String option) {
			logger.info("Selecting option '{}' from dropdown: {}", option, locator);
			WebElement dropdown = getElement(locator);
			Select select = new Select(dropdown);
			select.selectByVisibleText(option);
			logger.info("Option '{}' selected from dropdown: {}", option, locator);
		}
		
		// Gets the value attribute of the element found by locator
		public String getAttribute(By locator) {
			logger.info("Getting attribute 'value' for element: {}", locator);
			String value = getElement(locator).getAttribute("value");
			logger.info("Attribute 'value' for element {}: {}", locator, value);
			return value;
		}
		
		public String getText(By locator) {
			logger.info("Getting text for element: {}", locator);
			String value = getElement(locator).getText();
			logger.info("Text for element {}: {}", locator, value );
			return value;
		}
		
		// Switches to an iframe by its id
		public void switchtoIframe(String id) {
			logger.info("Switching to iframe with id: {}", id);
			driver.switchTo().frame(id);
			logger.info("Switched to iframe with id: {}", id);
		}
		
		// Thread.Sleep for force implicit Wait
		public void ImpliciteSleep(int milliseconds) {
			logger.info("Sleeping for {} milliseconds", milliseconds);
			try {
				Thread.sleep(milliseconds); 
	            }catch (InterruptedException e) {
	            	logger.warn("Interrupted during sleep");
	        }
			logger.info("Sleep completed for {} milliseconds", milliseconds);
		}
		
		
		public void refreshPage() {
			driver.navigate().refresh();
		}
		
}

	

