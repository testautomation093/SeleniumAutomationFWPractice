package com.qa.opencart.tests;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;

public class AccountsPageTest extends BaseTest {

	@BeforeClass
	public void accSetUp() {
		accPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
		// accPage=new AccountsPage(driver);

	}

	@Test
	public void isLogOutLinkExistTest() {
		boolean flag = accPage.isLogOutLinkExist();
		Assert.assertTrue(flag);
	}

	@Test
	public void accPageHeadersTest() {
		List<String> listHeaders = accPage.getAccPageHeaders();
		Assert.assertEquals(listHeaders.size(), 4);

		Assert.assertEquals(listHeaders, AppConstants.expHeaders);

	}

	@Test
	public void searchProductTest() {
		accPage.doSearch("imac");
	}

}
