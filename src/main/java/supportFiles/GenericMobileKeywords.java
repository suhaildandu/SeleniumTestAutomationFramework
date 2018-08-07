package supportFiles;


import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Function;

import io.appium.java_client.AppiumDriver;
import io.selendroid.client.SelendroidDriver;

public class GenericMobileKeywords {
	
	/*public static   WebElement getElement(final By locator)
	{
		TestConfigurations testConfig= GlobalVariables.getConfigMap().get(Thread.currentThread().getName());
		WebElement  webElement = null;
					
		try
		{
			
			if(GlobalConstants.mobileAutomationMode.equalsIgnoreCase("Selendroid")){
				Wait<SelendroidDriver> wait = new FluentWait<SelendroidDriver>(testConfig.getSelendroidDriver())
						.withTimeout(GlobalConstants.explicitWaitTime, TimeUnit.SECONDS)
						.pollingEvery(GlobalConstants.pollingWaitTime, TimeUnit.SECONDS)
						.ignoring(NoSuchElementException.class);			
		
					webElement = wait.until(new Function<SelendroidDriver, WebElement>() {
						public WebElement apply(SelendroidDriver driver) {
							WebElement element =null;							
							element = driver.findElement(locator);
							return element;
						}
					});
			}else{
				
				
				Wait<AppiumDriver> wait = new FluentWait<AppiumDriver>(testConfig.getAppiumDriver())
						.withTimeout(GlobalConstants.explicitWaitTime, TimeUnit.SECONDS)
						.pollingEvery(GlobalConstants.pollingWaitTime, TimeUnit.SECONDS)
						.ignoring(NoSuchElementException.class);			
		
					webElement = wait.until(new Function<AppiumDriver, WebElement>() {
						public WebElement apply(AppiumDriver driver) {
							WebElement element =null;							
							element = driver.findElement(locator);
							return element;
						}
					});
				
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			LOG.report_ERROR(e);
		}
		

		return webElement;

	}*/
	public static   WebElement getElement(final By locator){
		TestConfigurations testConfig= GlobalVariables.getConfigMap().get(Thread.currentThread().getName());
		WebElement element=null;
		
		if(GlobalConstants.mobileAutomationMode.equalsIgnoreCase("Appium"))	
			element= new WebDriverWait(testConfig.getAppiumDriver(),GlobalConstants.explicitWaitTime).until(ExpectedConditions.visibilityOfElementLocated(locator));
		else
			element= new WebDriverWait(testConfig.getSelendroidDriver(),GlobalConstants.explicitWaitTime).until(ExpectedConditions.visibilityOfElementLocated(locator));
		
		return element;
		
	}
	
	public static   List<WebElement> getElementList(final By locator)
	{
		TestConfigurations testConfig= GlobalVariables.getConfigMap().get(Thread.currentThread().getName());
		List<WebElement> webElements = null;
		
		try
		{	if(GlobalConstants.mobileAutomationMode.equalsIgnoreCase("Selendroid")){
				Wait<SelendroidDriver> wait = new FluentWait<SelendroidDriver>(testConfig.getSelendroidDriver())
						.withTimeout(GlobalConstants.explicitWaitTime, TimeUnit.SECONDS)
						.pollingEvery(GlobalConstants.pollingWaitTime, TimeUnit.SECONDS)
						.ignoring(NoSuchElementException.class);			
		
				webElements = wait.until(new Function<SelendroidDriver, List<WebElement>>() {
					public List<WebElement> apply(SelendroidDriver driver) {
						List<WebElement> element =null;							
						element = driver.findElements(locator);
						return element;
					}
				});	
			}else{
				
				Wait<AppiumDriver> wait = new FluentWait<AppiumDriver>(testConfig.getAppiumDriver())
						.withTimeout(GlobalConstants.explicitWaitTime, TimeUnit.SECONDS)
						.pollingEvery(GlobalConstants.pollingWaitTime, TimeUnit.SECONDS)
						.ignoring(NoSuchElementException.class);			
		
				webElements = wait.until(new Function<AppiumDriver, List<WebElement>>() {
					public List<WebElement> apply(AppiumDriver driver) {
						List<WebElement> element =null;							
						element = driver.findElements(locator);
						return element;
					}
				});	
				
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			LOG.report_ERROR(e);
		}
		

		return webElements;

	}
	
	public  static   void startApplication()
	{
		
		 	
			TestConfigurations testConfig= GlobalVariables.getConfigMap().get(Thread.currentThread().getName());
			try
			{
				
				if(GlobalConstants.mobileAutomationMode.equalsIgnoreCase("Appium"))						
					testConfig.getAppiumDriver().get(GlobalConstants.URL);
				else
					testConfig.getSelendroidDriver().get(GlobalConstants.URL);
					
				LOG.report_INFO("Application "+ GlobalConstants.URL +" is launched in Mobile Browser");
				
				
				
			}catch(Exception e)
			{
				e.printStackTrace();
				LOG.report_ERROR(e);
				
			}


	}

		
	public static  void enterText(By locator, String input)
	{
		TestConfigurations testConfig= GlobalVariables.getConfigMap().get(Thread.currentThread().getName());
		try
		{
			
			GenericMobileKeywords.getElement(locator).sendKeys(input);
			testConfig.getLogger().info("Entered Text ["+input+"] in Element ["+locator+"]");
			
			//LOG.report_INFO("Enter Text", "Entered Text \""+ input+ "\"");
		}
		catch(Throwable e)
		{
			testConfig.getLogger().error("Entered Text Failed for Element ["+locator+"]",e);
			LOG.report_ERROR(e);
			
		}

	}
	
	public static  String getElementText(By locator)
	{	
		TestConfigurations testConfig= GlobalVariables.getConfigMap().get(Thread.currentThread().getName());
		String text=null;
		try
		{
			text=GenericMobileKeywords.getElement(locator).getText();
			
			testConfig.getLogger().info("Get Text Successfull for Element ["+locator+"], Text fetched ["+text+"]");
			//LOG.report_INFO("Enter Text", "Entered Text \""+ input+ "\"");
		}
		catch(Throwable e)
		{
			testConfig.getLogger().error("Get Text Failed for Element ["+locator+"]",e);
			LOG.report_ERROR(e);
			
		}
		
		return text;

	}

	public static  void clickElement(By locator)
	{
		TestConfigurations testConfig= GlobalVariables.getConfigMap().get(Thread.currentThread().getName());

		try
		{
			GenericMobileKeywords.getElement(locator).click();
			
			//LOG.report_INFO("Clicked Element Successfully");
			testConfig.getLogger().info("Clicked Element ["+locator+"]");
			
		}catch(Throwable e)
		{
			testConfig.getLogger().error("Click Element Failed for Element ["+locator+"]",e);
			LOG.report_ERROR(e);
			
		}

	}
	
	
	 
	  
	 
	public static void selectByVisibleText(By locator, String text){
		TestConfigurations testConfig= GlobalVariables.getConfigMap().get(Thread.currentThread().getName());
		try{
			WebElement elem=GenericMobileKeywords.getElement(locator);
			Select selectbox = new Select(elem);
			selectbox.selectByVisibleText(text);
			testConfig.getLogger().info("Selected Text ["+text+"] in Selectbox ["+locator+"]");
		}catch(Throwable e){
			testConfig.getLogger().error("Select text Failed for Element ["+locator+"]",e);
			LOG.report_ERROR( e);
		}
	}
	
public static String captureCurrentScreenShot(){
		
		TestConfigurations testConfig= GlobalVariables.getConfigMap().get(Thread.currentThread().getName());
		WebDriver driver;
		if(GlobalConstants.mobileAutomationMode.equalsIgnoreCase("Appium"))	
			driver= testConfig.getAppiumDriver();
		else
			driver= testConfig.getSelendroidDriver();
		
		String screenShotName= "Snapshot" + "_" + GlobalVariables.getTimestamp()+".png";		
		String screenShotPath=GlobalConstants.currentReportFolderPath+"/"+ screenShotName;
		try{
			
			File scr = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(scr, new File(screenShotPath));
			
			testConfig.getLogger().info("Screen Captured Succussfully");
		}catch(Throwable e){
			
			testConfig.getLogger().error("Screen Capture Failed",e);
			LOG.report_ERROR("Error is Taking Screenshot: "+ e);
			
			
		}
		return screenShotName;
	}

public  static   void stopApplication()
{
	
	
		TestConfigurations testConfig= GlobalVariables.getConfigMap().get(Thread.currentThread().getName());
		try
		{
			
			WebDriver driver;
			if(GlobalConstants.mobileAutomationMode.equalsIgnoreCase("Appium"))	
				driver= testConfig.getAppiumDriver();
			else
				driver= testConfig.getSelendroidDriver();
								
			driver.quit();	
			LOG.report_INFO( "Application Closed");	
			testConfig.getLogger().info("Application Closed");
			
			
		}catch(Throwable e)
		{
			LOG.report_ERROR("Close Application Failed"+ e);
			LOG.report_ERROR(e);			
		
		}finally{
			
			TestConfigurations.stopTestReport();
		}


}

public static boolean verifyElementExists(By locator) {
	TestConfigurations testConfig= GlobalVariables.getConfigMap().get(Thread.currentThread().getName());
	try{
		
		WebDriver driver=testConfig.getMobileDriver();
				
			driver.findElement(locator);
			testConfig.getLogger().info("Element Exists ["+locator+"]");
		
		return true;
		
	}catch(NoSuchElementException e){
		testConfig.getLogger().fatal("Element Not Found ["+locator+"]",e);
		return false;
		
	}catch(TimeoutException t){
		testConfig.getLogger().fatal("Element Not Found ["+locator+"]",t);
		return false;
	}
	
}
	
public static void scrollDown()
{
	
	
}


public static boolean isElementDisplayed(By locator){
	TestConfigurations testConfig= GlobalVariables.getConfigMap().get(Thread.currentThread().getName());
	WebDriver driver=testConfig.getMobileDriver();
	
	try{
		
		GenericMobileKeywords.getElement(locator);
		testConfig.getLogger().info("Element Found ["+locator+"]");
		return true;
	}catch(Exception e){
		testConfig.getLogger().info("Element Not Found ["+locator+"]");
		return false;
		
	}
	
	
}
	

}
