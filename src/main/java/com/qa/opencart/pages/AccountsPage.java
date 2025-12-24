package com.qa.opencart.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.utils.ElementUtil;

public class AccountsPage {

	private By headers = By.tagName("h2"); // mutiple headers
	private By logoutLink = By.linkText("Logout"); // Logout Link
	private By searchIcon = By.xpath("//div[@id='search']//button");
	private By searchBar = By.name("search");

	private WebDriver driver;
	private ElementUtil elUtil;

	public AccountsPage(WebDriver driver) {
		this.driver = driver;
		elUtil=new ElementUtil(driver);
	}

	public List<String> getAccPageHeaders() {
		
		return elUtil.getElementsTextList(headers);
		
	}

	public boolean isLogOutLinkExist()
	{
		boolean flag=elUtil.isElementDisplayed(logoutLink);
		return flag;
		
	}
	
	public SearchResultsPage doSearch(String searchValue)
	{
		System.out.println("Search Product is : "+searchValue);
		WebElement el=elUtil.waitForElementVisibility(searchBar, 3);
		el.clear();
		el.sendKeys(searchValue);
		elUtil.doClick(searchIcon);
		
		return new SearchResultsPage(driver);
	}
	
	
}
