package com.qa.opencart.pages;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

public class ProductInfoPage {
	
	
	private WebDriver driver;
	private ElementUtil elUtil;

	public ProductInfoPage(WebDriver driver) {
		this.driver = driver;
		elUtil = new ElementUtil(driver);
	}
	
	private final By header=By.tagName("h1");
	private final By images=By.xpath("//a[@class='thumbnail']");
	
	private final By productMedadata=By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[1]//li");
	private final By productPrice=By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[2]//li");
	
	private Map<String,String> productInfo;
	
	public String getProductHeader()
	{
		String headerValue=
				  elUtil
				     .waitForElementVisibility(header, AppConstants.DEFAULT_SHORT_WAIT).getText();
		System.out.println("Product Header Value is : "+headerValue);
		
		return headerValue;
		
	}
	
	public int getProductImagesCount()
	{
		int imagesCount=
				elUtil
				  .waitforElementsVisibility(images, AppConstants.DEFAULT_SHORT_WAIT).size();

		System.out.println("Total Images are : "+imagesCount);
	
		return imagesCount;
	}
	
	public Map<String, String> getProductCompleteInfo()
	{
		productInfo=new HashMap<String, String>();
		getProductMetadata();
		getProductPriceData();
		System.out.println("Product Complete Info is : \n"+productInfo);
		
		return productInfo;
	}
	
	
	private void getProductMetadata()
	{
		List<WebElement> metaList = elUtil.waitforElementsVisibility(productMedadata, AppConstants.DEFAULT_SHORT_WAIT);
		System.out.println("Total Meta size is : "+metaList.size());
		
		for(WebElement e : metaList)
		{
			String text=e.getText();
			String meta[]=text.split(":");
			
			String key=meta[0];
			String value=meta[1].trim();
			
			productInfo.put(key, value);
			
		}
		
	}
	
	private void getProductPriceData()
	{
		
		List<WebElement> priceList = elUtil.waitforElementsVisibility(productPrice, AppConstants.DEFAULT_SHORT_WAIT);

		System.out.println("Total Price size is : "+priceList.size());
		String productPrice=priceList.get(0).getText();
		String exTaxPrice=priceList.get(1).getText().split(":")[1].trim();
		
		productInfo.put("productPrice",productPrice);
		productInfo.put("exTaxPrice", exTaxPrice);
		
	}

	
}
