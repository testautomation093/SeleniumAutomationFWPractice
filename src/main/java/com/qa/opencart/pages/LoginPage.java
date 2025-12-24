package com.qa.opencart.pages;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

public class LoginPage {
	
	private By emailId=By.id("input-email");
	private By password =By.id("input-password");
	private By loginBtn= By.xpath("//input[@value='Login']");
	private final By forgotPwdLink=By.xpath("(//a[text()='Forgotten Password'])[1]");
	private final By header=By.tagName("h2");
	
	private final By register=By.xpath("(//a[text()='Register'])[2]");
	
	private final By warningErrorMessage=By.xpath("//div[@class='alert alert-danger alert-dismissible']");
	
	private static final Logger log = LogManager.getLogger(LoginPage.class);
	
	private WebDriver driver;
	private ElementUtil elUtil;
	
	
	public LoginPage(WebDriver driver)
	{
		this.driver=driver;
		elUtil=new ElementUtil(driver);
	}
	
	
	public String getLoginPageTitle()
	{
		
		String title=elUtil.waitforexactTitle(AppConstants.LOGIN_PAGE_TITLE,AppConstants.DEFAULT_SHORT_WAIT);
		//System.out.println("Title of the Login Page is : "+title);
		log.info("Title of the Login Page is : "+title);
		return title;
	}
	
	public String getLoginPageUrl()
	{
		String currentUrl=elUtil.waitforUrlContains(AppConstants.LOGIN_PAGE_FRACTION_URL,AppConstants.DEFAULT_SHORT_WAIT);
		//System.out.println("Url of the Login Page is : "+currentUrl);
		log.info("Url of the Login Page is : "+currentUrl);
		return currentUrl;
		
	}
	
	public boolean isForgotPwdLinkExist()
	{
		boolean flag=elUtil.isElementDisplayed(forgotPwdLink);
		return flag;
		
	}
	
	public boolean isHeaderExist() 
	{
		boolean flag=elUtil.isElementDisplayed(header);
		return flag;
	}

	public AccountsPage doLogin(String uname, String pass)
	{
		//System.out.println("App Credentials are : "+uname+ ": " +pass);
		log.info("App Credentials are : "+uname+ ": " +pass);
		elUtil.waitForElementVisibility(emailId, 6).sendKeys(uname);
		elUtil.doSendKeys(password, pass);
		elUtil.doClick(loginBtn);
	    
		elUtil.waitforexactTitle(AppConstants.ACC_PAGE_TITLE, AppConstants.DEFAULT_SHORT_WAIT);
	   
	    return new AccountsPage(driver);
	    
	}
	
	public boolean doLoginwithInvalidCredes(String inUname, String inPass)
	{
		log.info("Invalid App Credentials are : "+inUname+ ": " +inPass);
		WebElement el=elUtil.waitForElementVisibility(emailId, 6);
		        el.clear();
				el.sendKeys(inUname);
				elUtil.waitForElementPresence(password, AppConstants.DEFAULT_SHORT_WAIT).clear();
		elUtil.doSendKeys(password, inPass);
		elUtil.doClick(loginBtn);
		
		String error=elUtil.waitForElementVisibility(warningErrorMessage, AppConstants.DEFAULT_SHORT_WAIT).getText();
		log.info("Login Info Error Message is : "+error);
		
		if(error.contains(AppConstants.LOGIN_MAX_ATTEMPT_MESSG))
		{
			return true;
		}
		else if(error.contains(AppConstants.LOGIN_INVALID_ERROR_MESSG))
		{
			return true;
		}
		return false;
		
	}
	
	public RegisterPage navigateToRegisterPage()
	{
		elUtil.waitForElementPresence(register, AppConstants.DEFAULT_SHORT_WAIT).click();
		return new RegisterPage(driver);

	}
	
	
}
