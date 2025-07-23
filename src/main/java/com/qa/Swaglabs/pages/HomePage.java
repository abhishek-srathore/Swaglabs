package com.qa.Swaglabs.pages;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.Swaglabs.utils.ElementUtil;


public class HomePage {

	ElementUtil eUtil;
	WebDriver driver;
	private static final Logger logger = LogManager.getLogger(HomePage.class);

	public HomePage(WebDriver driver) {
		this.driver = driver;
		eUtil = new ElementUtil(driver);
		logger.info("HomePage initialized");
	}

	public By productsNames = By.xpath("//div[@class=\"inventory_item_name \"]");
	public By bagPack = By.xpath("//div[text()='Sauce Labs Backpack']/ancestor::div[@class='inventory_item']//button");
	public By BikeLight =By.xpath("//div[text()='Sauce Labs Bike Light']/ancestor::div[@class='inventory_item']//button");
	public By cart = By.xpath("//a[@class='shopping_cart_link fa-layers fa-fw']");
	public By removeBikeLight = By.xpath("//div[text()='Sauce Labs Bike Light']/parent::a/following-sibling::div[@class='item_pricebar']/button");
	public By checkout = By.xpath("//a[@class='btn_action checkout_button']");
	public By cartItemCount = By.xpath("//span[@class='shopping_cart_badge']");
	public By cartName = By.xpath("//div[text() = 'Your Cart']");
	public By checkoutText = By.xpath("//div[text() = 'Checkout: Your Information']");
	public By removeButton = By.xpath("//button[text() =  'REMOVE']");

	
	public String verifyHomePageUrl() {
		logger.info("Getting current page URL");
		String url = driver.getCurrentUrl();
		logger.info("Current page URL: {}", url);
		return url;
	}
	
	public int isProductsDisplayed() {
		logger.info("Checking if products are displayed");
		eUtil.waitForMultiElementsVisiblity(productsNames, 10);
		List<WebElement> displayedElements = eUtil.getElements(productsNames);
		logger.info("Products displayed: {}", displayedElements);
		return displayedElements.size();
	}
	
	public boolean defaultSorting() {
		logger.info("Sorting product names in ascending order");
		List<WebElement> productNames = eUtil.getElements(productsNames); 
		List<String> actualNames = new ArrayList<>();
		for (WebElement names : productNames) {
		    actualNames.add(names.getText().trim());
		}
		List<String> expectedNames = new ArrayList<>(actualNames);
		Collections.sort(expectedNames);
		logger.info("Sorted product names: {}", expectedNames);
		if(actualNames.equals(expectedNames)  ) {
			return true;
		}else {
			return false;
		}
	}
	
	public String verifyAddToCart() {
		eUtil.WaitForElementVisiblity(BikeLight, 10 );
		eUtil.doClick(BikeLight);
		eUtil.WaitForElementVisiblity(cartItemCount, 10);
		return eUtil.getText(cartItemCount);
	}
	
	public ProductDetail clickBagPack() {
		logger.info("Clicking on BagPack product");
		eUtil.doClick(bagPack);
		return new ProductDetail(driver);
	}
	
}
