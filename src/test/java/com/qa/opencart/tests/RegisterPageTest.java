package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.utils.CSVUtil;
import com.qa.opencart.utils.ExcelUtil;
import com.qa.opencart.utils.StringUtil;

public class RegisterPageTest extends BaseTest {
	
	@BeforeClass
	public void goToRegisterPage()
	{
		registerPage=loginPage.navigateToRegisterPage();
		
	}
	
	@DataProvider
	public Object getUserDataFromSheet()
	{
		Object obj[][]=ExcelUtil.getTestData("user_registration");
		return obj;
	}
	
	@DataProvider
	public Object getCSVData()
	{
		Object obj[][]=CSVUtil.csvData("registration");
		
		return obj;
		
	}
	
	
	@Test(dataProvider = "getCSVData")
	public void userRegistrationTest(String firstName,String lastName, String telephone, String password, String subscribe)
	{
		
		boolean flag=registerPage.userRegistration(firstName,lastName, StringUtil.generateRandomEmail(),telephone,password,subscribe);
		
		Assert.assertTrue(flag);
		
	}

}
