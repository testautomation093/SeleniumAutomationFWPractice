package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

public class SearchResultsPage {

	private WebDriver driver;
	private ElementUtil elUtil;

	private final By searchResults = By.cssSelector("div.product-thumb");
	private final By resulltsHeader = By.tagName("h1");

	public SearchResultsPage(WebDriver driver) {
		this.driver = driver;
		elUtil = new ElementUtil(driver);
	}
	
	// Public methods related to SearchResults Page only :
	
	public int getSearchResultsCount()
	{
		int count=elUtil
				   .waitforElementsPresence(searchResults,AppConstants.DEFAULT_SHORT_WAIT).size();
	
		System.out.println("Total Search Results are : "+count);
		
		return count;
	
	}
	
	public String getResultsHeaderValue()
	{
		String headerValue=elUtil.doElementGetText(resulltsHeader);
		System.out.println("Header Value is : "+headerValue);
	 
		return headerValue;
	
	}
	
	public ProductInfoPage selectProduct(String productName)
	{
	    System.out.println("Clicked Product is : "+productName);	
		elUtil.doClick(By.linkText(productName));
		
		return new ProductInfoPage(driver);
	}
	

}
