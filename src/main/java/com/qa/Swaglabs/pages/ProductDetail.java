package com.qa.Swaglabs.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import com.qa.Swaglabs.utils.ElementUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * ProductDetail class represents the product detail page for a specific product (e.g., Sauce Labs Backpack).
 * Provides methods to interact with and verify elements on the product detail page.
 */
public class ProductDetail {
	
	private WebDriver driver;
	ElementUtil eUtil;
	private static final Logger logger = LogManager.getLogger(ProductDetail.class);
	
	/**
	 * Constructor to initialize ProductDetail with WebDriver and ElementUtil.
	 * @param driver The WebDriver instance
	 */
	public ProductDetail(WebDriver driver) {
		this.driver = driver;
		eUtil = new ElementUtil(driver);
		logger.info("ProductDetail initialized");
	}
	
	// Locators for product detail elements
	public By selectBag = By.xpath("//div[text() = 'Sauce Labs Backpack']");
	public By bagProductName = By.xpath("//div[text() = 'Sauce Labs Backpack']");
	public By bagproductPrice = By.xpath("//div[@class='inventory_details_price']");
	public By addtoCart = By.xpath("//button[text() = 'ADD TO CART']");
	
	/**
	 * Gets the current product detail page URL.
	 * @return The current URL as a String
	 */
	public String getPageUrl() {
		logger.info("Getting product detail page URL");
		String url = driver.getCurrentUrl();
		logger.info("Product detail page URL: {}", url);
		return url;
	}
	
	/**
	 * Checks if the product name is displayed on the product detail page.
	 * @return The product name if displayed, otherwise a warning message
	 */
	public String isNameDisplayed() {
		logger.info("Checking if product name is displayed");
		if (eUtil.useIsDisplayed(bagProductName)) {
			String name = eUtil.getText(bagProductName);
			logger.info("Product name displayed: {}", name);
			return name;
		} else {
			logger.warn("No Product Name Available");
			return "No Product Name Available";
		}
	}
}

	
	
	
	
