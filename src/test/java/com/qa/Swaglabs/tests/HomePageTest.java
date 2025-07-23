package com.qa.Swaglabs.tests;

import java.io.IOException;

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
		loginPage.insertCredential(readProp.getProperty("username"),readProp.getProperty("password"));
	}

	@Test(description = "To Verify Home Page URL" , priority = 0 )
	public void verifyHomeUrl() {
		String url = homePage.verifyHomePageUrl();
		Assert.assertEquals(url, "");
	}
	
	@Test(priority = 1)
	public void verifyHomePageLoaded() {
		logger.info("verification of homePage");
		Assert.assertEquals(homePage.isProductsDisplayed(),6);
	}
	
	@Test(priority =2)
	public void verifyAddtoCart() {
		logger.info("verification add to cart");
		Assert.assertEquals(homePage.verifyAddToCart(), "1");
	}
	
	@Test(priority = 3)
	public void verifyDefaultSort() {
		
		Assert.assertEquals(homePage.defaultSorting(), true);
	}
	
}
