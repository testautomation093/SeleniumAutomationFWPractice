package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.chaintest.plugins.ChainTestListener;
import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

@Epic("EP-100: Design the Open Cart App Login Page")
@Feature("F-101: design open cart login feature")
@Story("US-50: develop login core features: title, url, user is able to login")
public class LoginPageTest extends BaseTest {
	
	@Description("login page title test.....")
	@Link("")
	@Owner("Nishant Goel")
	@Severity(SeverityLevel.MINOR)
	@Test
	public void loginPageTitleTest()
	{
		String actTitle=loginPage.getLoginPageTitle();
		
		ChainTestListener.log("Login Page Title is : "+actTitle);
		Assert.assertEquals(actTitle,AppConstants.LOGIN_PAGE_TITLE);
		
	}
	
	@Test
	public void loginPageUrlTest()
	{
		String actUrl=loginPage.getLoginPageUrl();
		Assert.assertTrue(actUrl.contains(AppConstants.LOGIN_PAGE_FRACTION_URL));		
	}
	
	@Test
	public void isForgotPwdLinkExistTest()
	{
		boolean flag=loginPage.isForgotPwdLinkExist();
		Assert.assertTrue(flag);

	}
	
	@Test
	public void isLoginPageHeaderExistTest()
	{
		boolean flag=loginPage.isHeaderExist();
		Assert.assertTrue(flag);
	}
	
	@Test
	public void loginTest()
	{
		accPage=loginPage.doLogin(prop.getProperty("username"),prop.getProperty("password"));
		
		Assert.assertTrue(accPage.isLogOutLinkExist());	
	}
	
	
}
