package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;

public class LoginPageNegativeTest extends BaseTest {

	
	@DataProvider
	public Object[][] getNegativeLoginData()
	{
		
		return new Object [][]
				{
			     {"","test"},
			     {"test",""},
			     {"",""}
			     
				};
		
	}
	
	@Test(dataProvider = "getNegativeLoginData")
	public void negativeLoginTest(String uName, String pass)
	{
		
		boolean flag=loginPage.doLoginwithInvalidCredes(uName,pass);
		
		Assert.assertTrue(flag);
		
	}
	
}
