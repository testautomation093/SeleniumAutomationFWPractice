package com.qa.opencart.tests;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.qa.opencart.base.BaseTest;

public class ProductInfoTest extends BaseTest {
	
	@BeforeClass
	public void productInfoSetUp()
	{
		accPage=loginPage.doLogin(prop.getProperty("username"),prop.getProperty("password"));
		
	}

	@DataProvider
	public Object getProducts()
	{
		Object obj[][]=new Object[3][2];
		obj[0][0]="macbook";
		obj[0][1]="MacBook Pro";

		obj[1][0]="samsung";
		obj[1][1]="Samsung SyncMaster 941BW";
		
		obj[2][0]="canon";
		obj[2][1]="Canon EOS 5D";
		
		return obj;
		
	}
	
	
	@Test(dataProvider = "getProducts")
	public void productHeaderTest(String searchKey, String productName)
	{
		searchResultsPage=accPage.doSearch(searchKey);
		productInfoPage=searchResultsPage.selectProduct(productName);
		String actualHeaderVal=productInfoPage.getProductHeader();
		
		Assert.assertEquals(actualHeaderVal,productName);
		
	}
	
	@DataProvider
	public Object getProductImages()
	{
		return new Object[][]
				{
			    
			    {"macbook","MacBook Pro", 4},
				{"samsung","Samsung SyncMaster 941BW",1 },
				{"canon","Canon EOS 5D",3}
				
				};
				
		
	}

	@Test(dataProvider = "getProductImages")
	public void productImagesCountTest(String searchKey, String productName, int count)
	{
		searchResultsPage=accPage.doSearch(searchKey);
		productInfoPage=searchResultsPage.selectProduct(productName);
		int expCount=productInfoPage.getProductImagesCount();
		
		Assert.assertEquals(expCount, count);
	}

	@Test
	public void productInfoTest()
	{
		
		SoftAssert sf=new SoftAssert();
		searchResultsPage=accPage.doSearch("macbook");
		productInfoPage=searchResultsPage.selectProduct("MacBook Pro");
		
		Map<String, String> productCompleteInfo = productInfoPage.getProductCompleteInfo();
		
		sf.assertEquals(productCompleteInfo.get("Brand"),"Apple");
		sf.assertEquals(productCompleteInfo.get("Availability"),"Out Of Stock");
		sf.assertEquals(productCompleteInfo.get("Product Code"),"Product 181");
		sf.assertEquals(productCompleteInfo.get("Reward Points"),"800");
		sf.assertEquals(productCompleteInfo.get("productPrice"),"$2,000.00");
		
		sf.assertAll();
	
	}
	
	
}
