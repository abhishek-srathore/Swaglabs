package com.qa.Swaglabs.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import com.qa.Swaglabs.utils.ElementUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ProductDetail {
	
	private WebDriver driver;
	ElementUtil eUtil;
	private static final Logger logger = LogManager.getLogger(ProductDetail.class);
	
	public ProductDetail(WebDriver driver) {
		this.driver = driver;
		eUtil = new ElementUtil(driver);
		logger.info("ProductDetail initialized");
	}
	
	
	// XPath
	public By  selectBag = By.xpath("//div[text() = 'Sauce Labs Backpack']");
	public By bagProductName = By.xpath("//div[text() = 'Sauce Labs Backpack']");
	public By bagproductPrice= By.xpath("//div[@class='inventory_details_price']");
	public By addtoCart = By.xpath("//button[text() = 'ADD TO CART']");
	
	
	public String  getPageUrl() {
		logger.info("Getting product detail page URL");
		String url = driver.getCurrentUrl();
		logger.info("Product detail page URL: {}", url);
		return url;
	}
	
	
	public String isNameDisplayed() {
		logger.info("Checking if product name is displayed");
		if (eUtil.useIsDisplayed(bagProductName)) {
			String name = eUtil.getText(bagProductName);
			logger.info("Product name displayed: {}", name);
			return name;
		}else {
			logger.warn("No Product Name Available");
			return "No Product Name Available";
		}
	}
}

	
//	public String isPriceDisplayed() {
//		if (eUtil.useIsDisplayed(bagProductName)) {
//			
//		}
//	}
//	
	
	
	
	
	
	
