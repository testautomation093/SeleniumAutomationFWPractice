package com.qa.opencart.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

import com.qa.opencart.errors.AppError;
import com.qa.opencart.exceptions.FrameworkException;

public class DriverFactory {

	public WebDriver driver;
	public Properties prop;
	
	public static ThreadLocal<WebDriver> tlDriver=new ThreadLocal<WebDriver>();
	
	private static final Logger log = LogManager.getLogger(DriverFactory.class);
	
	public OptionsManager optionsManager;

	public WebDriver initDriver(Properties prop) {
		
		String browser=prop.getProperty("browser");
		//System.out.println("Browser Name is : " + browser);
		
		optionsManager=new OptionsManager(prop);
		
		boolean remoteExecution=Boolean.parseBoolean(prop.getProperty("remote"));
		       
	    log.info("Logger Browser Name is : " + browser); 	
		switch (browser.toLowerCase().trim()) {
		case "chrome":
			//driver = new ChromeDriver();
			
			if(remoteExecution)
			{
				init_remoteDriver(browser);
			}
			else
			{
			tlDriver.set(new ChromeDriver(optionsManager.getChromeOptions()));
			}
			break;

		case "firefox":
			//driver = new FirefoxDriver();
			if(remoteExecution)
			{
				init_remoteDriver(browser);
			}
			else
			{
			tlDriver.set(new FirefoxDriver(optionsManager.getFirefoxOptions()));
			}
			break;

		case "edge":
			//driver = new EdgeDriver();
     		
			if(remoteExecution)
			{
				init_remoteDriver(browser);
			}
			else
			{
			tlDriver.set(new EdgeDriver(optionsManager.getEdgeOptions()));
			}
			break;

		case "safari":

			//driver = new SafariDriver();
			tlDriver.set(new SafariDriver());
			break;

		default:
			
		//System.out.println(AppError.INVALID_BROWSER_MESG);
		log.error(AppError.INVALID_BROWSER_MESG);	
		throw new FrameworkException("===Invalid Browser=======");
			
		}
		
		getDriver().manage().deleteAllCookies();
		getDriver().manage().window().maximize();
		
		getDriver().get(prop.getProperty("url"));

		return getDriver();
	}
	
	public static WebDriver getDriver()
	{
		return tlDriver.get();
		
	}
	
	
	public Properties initProp()
	{
		prop=new Properties();
		FileInputStream fp=null;
		
		String envName=System.getProperty("env");
		
		try {
			
			if(envName==null)
			{
		    log.info("Test Cases are runnig in the default env as no env has been selected" +envName);
			fp=new FileInputStream("src/test/resources/config/config.properties");

			}
			else
			{
				
			switch (envName.trim().toLowerCase()) {
			case "qa":
			   log.info("Test Cases are running in the " +envName);

			   fp=new FileInputStream("src/test/resources/config/config_qa.properties");

				break;
				
			case "uat":
				   log.info("Test Cases are running in the " +envName);

			fp=new FileInputStream("src/test/resources/config/config_uat.properties");

				break;
				
			case "dev":
				   log.info("Test Cases are running in the " +envName);

				fp=new FileInputStream("src/test/resources/config/config_dev.properties");

				break;	

			default:
				log.error("Wrong Env Name is passed " +envName);
				throw new FrameworkException("=====INVALID ENV========");
				}
				
				
			}
			
			
		prop.load(fp);
	
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return prop;
	}
	
	private void init_remoteDriver(String browser)
	{
		log.info("Running test cases on the remote browser : "+browser);
		try
		{
		switch (browser.trim().toLowerCase()) {
		case "chrome":
			tlDriver.set(new RemoteWebDriver(new URL(prop.getProperty("huburl")),optionsManager.getChromeOptions()));
				
			break;

		case "firefox":
			tlDriver.set(new RemoteWebDriver(new URL(prop.getProperty("huburl")),optionsManager.getFirefoxOptions()));		
			break;
			
		case "edge":
			tlDriver.set(new RemoteWebDriver(new URL(prop.getProperty("huburl")),optionsManager.getEdgeOptions()));		
			break;	
		default:
			log.error("Incorrect BrowserName is passed Please pass the correct browser");
            throw new FrameworkException("===Incorrect Browser===");
			
		}
		}
		catch (MalformedURLException e) {
			e.printStackTrace();
		}	
		
	}

	public static File getScreenshortAsFile()
	{
		File file=((TakesScreenshot)getDriver()).getScreenshotAs(OutputType.FILE);
		return file;
	}
	
	
	
	
}
