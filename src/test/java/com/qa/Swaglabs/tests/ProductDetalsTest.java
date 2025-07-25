package com.qa.Swaglabs.tests;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import com.qa.Swaglabs.base.BaseTest;

@Listeners	
public class ProductDetalsTest extends BaseTest {

	private static final Logger logger = LogManager.getLogger(ProductDetalsTest.class);
	
	@BeforeClass
	public void selectBagPack() {
		logger.info("Performing Login");
		loginPage.insertValidCredential(readProp.getProperty("username"),readProp.getProperty("password"));
		logger.info("Selecting BagPack");
		homePage.clickBagPack();
	}

	
	@Test(description = "To verify the BagPack product name is displayed correctly")
	public void verifyBagPackName() {
		Assert.assertEquals(productDetail, "Sauce Labs Backpack" );
	}
	
	
}
