package com.qa.Swaglabs.tests;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;


import com.qa.Swaglabs.base.BaseTest;


@Listeners
public class HomePageTest extends BaseTest{
	
	private static final Logger logger = LogManager.getLogger(LoginPageTest.class);

	@BeforeClass
	public void homePageSetup() throws IOException {
		com.qa.Swaglabs.factory.DriverFactory.getDriver().navigate().refresh();
		loginPage.insertValidCredential(readProp.getProperty("username"),readProp.getProperty("password"));
	}


	@Test(description = "To Verify Home Page URL" , priority = 3 )
	public void verifyHomePageUrl() {
		String url = homePage.getHomePageUrl();
		Assert.assertEquals(url, readProp.getProperty("homePageUrl"));
		}
	
	@Test(description ="To verify Page Title of Homepage", priority =4)
	public void verifyHomePageTitle() {
		String title = homePage.getHomePageTitle();
		Assert.assertEquals(title, "Swag Labs");
	}
	
	@Test(description = "To verify that the Home Page loads and displays 6 products", priority = 5)
	public void verifyHomePageLoaded() {
		logger.info("verification of homePage");
		Assert.assertEquals(homePage.loadHomePage(),6);
	}
	
	@Test(description = "To verify that Add to Cart functionality works", 
			dependsOnMethods = "verifyHomePageLoaded", 
			priority =6)
	public void verifyAddtoCart() {
		logger.info("verification add to cart");
		Assert.assertEquals(homePage.addProductToCart(), "1");
	}
	
	@Test(description = "To verify that Remove from Cart functionality works", 
			dependsOnMethods = "verifyAddtoCart",
			priority = 7)
	public void verifyRemoveFromBasket() {
		logger.info("verification removing from cart");
		Assert.assertEquals(homePage.removeProductfromCart(), "0", "Last Product was removed from cart successfully");
	}
	
	@Test(description = "To verify that default sorting is applied on Home Page", priority = 7)
	public void verifyDefaultSort() {
		logger.info("verifying sorting ");
		Assert.assertEquals(homePage.defaultSortCheck(), true);
	}
	
	
	@Test(description = "To verify that reverse sorting is applied on Home Page", 
			dependsOnMethods = "verifyHomePageLoaded", 
			priority = 8)
	public void verifyReverseSort() {
		Assert.assertEquals(homePage.reverseSortCheck(), true);
		
	}
}
