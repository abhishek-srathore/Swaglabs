package com.qa.Swaglabs.pages;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeoutException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.Swaglabs.utils.ElementUtil;

/**
 * HomePage class represents the Inventory page of SwagLabs.
 * Provides methods to interact with and verify elements on the home page.
 */
public class HomePage {

	ElementUtil eUtil;
	WebDriver driver;
	private static final Logger logger = LogManager.getLogger(HomePage.class);

	/**
	 * Constructor to initialize HomePage with WebDriver and ElementUtil.
	 * @param driver The WebDriver instance
	 */
	public HomePage(WebDriver driver) {
		this.driver = driver;
		eUtil = new ElementUtil(driver);
		logger.info("HomePage initialized");
	}

	// Locators for various elements on the home page
	public By productsNames = By.xpath("//div[@class=\"inventory_item_name \" ]");
	public By productImages = By.xpath("//img[@class =\"inventory_item_img\"]");
	public By bagPackAddToCartBtn = By.xpath("//div[text()='Sauce Labs Backpack']/ancestor::div[@class='inventory_item']//button");
	public By BikeLightAddtoCartBtn =By.xpath("//div[text()='Sauce Labs Bike Light']/ancestor::div[@class='inventory_item']//button");
	public By cart = By.xpath("//a[@class='shopping_cart_link fa-layers fa-fw']");
	public By removeBikeLight = By.xpath("//div[text()='Sauce Labs Bike Light']/parent::a/following-sibling::div[@class='item_pricebar']/button");
	public By checkout = By.xpath("//a[@class='btn_action checkout_button']");
	public By cartItemCount = By.xpath("//span[@class='shopping_cart_badge']");
	public By cartName = By.xpath("//div[text() = 'Your Cart']");
	public By checkoutText = By.xpath("//div[text() = 'Checkout: Your Information']");
	public By removeButton = By.xpath("//button[text() =  'REMOVE']");
	public By sortFilter = By.xpath("//select[@class=\"product_sort_container\"]");

	/**
	 * Gets the current page URL.
	 * @return The current URL as a String
	 */
	public String getHomePageUrl() {
		logger.info("Getting current page URL");
		String url = driver.getCurrentUrl();
		logger.info("Current page URL: {}", url);
		return url;
	}

	/**
	 * Gets the current page title.
	 * @return The current page title as a String
	 */
	public String getHomePageTitle() {
		logger.info("Getting current page Title");
		String title = driver.getTitle();
		logger.info("Current page Title: {}", title);
		return title;
	}

	/**
	 * Checks if products are displayed on the home page.
	 * @return The number of products displayed
	 */
	public int loadHomePage() {
		logger.info("Checking if products are displayed");
		eUtil.waitForMultiElementsVisiblity(productsNames, 10);
		eUtil.waitForMultiElementsVisiblity(productImages, 20);
		List<WebElement> displayedElements = eUtil.getElements(productImages);
		logger.info("Products displayed: {}", displayedElements);
		return displayedElements.size();
	}

	/**
	 * Verifies that the default sorting (ascending order) is applied to product names.
	 * @return true if sorted in ascending order, false otherwise
	 */
	public boolean defaultSortCheck() {
		logger.info("Sorting product names in ascending order");
		List<WebElement> productNames = eUtil.getElements(productsNames); 
		List<String> actualNames = new ArrayList<>();
		for (WebElement names : productNames) {
		    actualNames.add(names.getText().trim());
		}
		List<String> expectedNames = new ArrayList<>(actualNames);
		Collections.sort(expectedNames);
		logger.info("Sorted product names: {}", expectedNames);
		// Compare actual and expected sorted lists
		return actualNames.equals(expectedNames);
	}

	/**
	 * Verifies that reverse sorting (descending order) is applied to product names.
	 * @return true if sorted in descending order, false otherwise
	 */
	public boolean reverseSortCheck() {
		logger.info("Sorting product names in descending order");
		eUtil.selectElement(sortFilter, "Name (Z to A)");
		List<WebElement> productNames = eUtil.getElements(productsNames); 
		
		List<String> actualNames = new ArrayList<>();
		for (WebElement names : productNames) {
		    actualNames.add(names.getText().trim());
		}
		
		List<String> expectedNames = new ArrayList<>(actualNames);
		expectedNames.sort(Collections.reverseOrder());
		logger.info("Sorted product names: {}", expectedNames);
		// Compare actual and expected sorted lists
		return actualNames.equals(expectedNames);
	}

	/**
	 * Adds the 'Sauce Labs Bike Light' product to the cart and returns the cart item count.
	 * @return The cart item count as a String
	 */
	public String addProductToCart() {
		logger.info("Adding 'Sauce Labs Bike Light' to cart");
		eUtil.WaitForElementVisiblity(BikeLightAddtoCartBtn, 10 );
		eUtil.doClick(BikeLightAddtoCartBtn);
		eUtil.WaitForElementVisiblity(cartItemCount, 10);
		String count = eUtil.getText(cartItemCount);
		logger.info("Cart item count after adding: {}", count);
		return count;
	}
	
	/**
	 * Removes the 'Sauce Labs Bike Light' product to the cart and returns the cart item count.
	 * @return The cart item count as a String
	 */
	public String removeProductfromCart() {
		logger.info("removing 'Sauce Labs Bike Light' from cart");
		eUtil.WaitForElementVisiblity(BikeLightAddtoCartBtn, 10 );
		eUtil.doClick(BikeLightAddtoCartBtn);
		
		try {
			eUtil.getElement(cartItemCount);
		}catch(NoSuchElementException e) {
			logger.info("Cart item count not found, so no item added to cart");
			return "0";
		}
			
		String count = eUtil.getText(cartItemCount);
		logger.info("Cart item count after removing last: {}", count);
		return count;
	}
	
	/**
	 * Clicks on the BagPack product and navigates to its detail page.
	 * @return ProductDetail page object
	 */
	public ProductDetail getProductDetailsPagr() {
		logger.info("Clicking on BagPack product");
		eUtil.doClick(bagPackAddToCartBtn);
		return new ProductDetail(driver);
	}

}
