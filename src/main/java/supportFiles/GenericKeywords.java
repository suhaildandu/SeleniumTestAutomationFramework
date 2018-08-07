package supportFiles;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.ExtentTest;
import com.google.common.base.Function;




public class GenericKeywords extends SeleniumDriverSetup
{

	public static String parentWindowHandle;
	
	
	public static   WebElement getElement(final By locator)
	{
		TestConfigurations testConfig= GlobalVariables.getConfigMap().get(Thread.currentThread().getName());
		WebElement webElement = null;
		
			
		try
		{
			Wait<WebDriver> wait = new FluentWait<WebDriver>(testConfig.getDriver())
					.withTimeout(GlobalConstants.explicitWaitTime, TimeUnit.SECONDS)
					.pollingEvery(GlobalConstants.pollingWaitTime, TimeUnit.SECONDS)
					.ignoring(NoSuchElementException.class);			
		
				webElement = wait.until(new Function<WebDriver, WebElement>() {
					public WebElement apply(WebDriver driver) {
						WebElement element =null;							
						element = driver.findElement(locator);
						return element;
					}
				});			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			LOG.report_ERROR(e);
		}
		

		return webElement;

	}
	
	/*public static   WebElement getElement(By locator){
		TestConfigurations testConfig= GlobalVariables.getConfigMap().get(Thread.currentThread().getName());
		WebElement element=null;
		try{
			element=new WebDriverWait(testConfig.getDriver(),60).until(ExpectedConditions.visibilityOfElementLocated(locator));
		return element;
		}catch(Exception e){
			
			e.printStackTrace();
		}
		return element;
	}*/
	
	public static   List<WebElement> getElementList(final By locator)
	{
		TestConfigurations testConfig= GlobalVariables.getConfigMap().get(Thread.currentThread().getName());
		List<WebElement> webElements = null;
		
		try
		{
			Wait<WebDriver> wait = new FluentWait<WebDriver>(testConfig.getDriver())
					.withTimeout(GlobalConstants.explicitWaitTime, TimeUnit.SECONDS)
					.pollingEvery(GlobalConstants.pollingWaitTime, TimeUnit.SECONDS)
					.ignoring(NoSuchElementException.class);			
		
			webElements = wait.until(new Function<WebDriver, List<WebElement>>() {
					public List<WebElement> apply(WebDriver driver) {
						List<WebElement> element =null;							
						element = driver.findElements(locator);
						return element;
					}
				});			
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
				
										
				testConfig.getDriver().get(GlobalConstants.URL);			
					
				LOG.report_INFO("Application "+ GlobalConstants.URL +" is launched");
				
				
				
			}catch(Exception e)
			{
				e.printStackTrace();
				LOG.report_ERROR(e);
				
			}


	}
	
	public  static  String getWebPageTitle()
	{
			String title=null;
		 	
			TestConfigurations testConfig= GlobalVariables.getConfigMap().get(Thread.currentThread().getName());
			try
			{
				
										
				title=testConfig.getDriver().getTitle();						
				//LOG.report_INFO("Get WebPage Title", "Tile is "+ title);
				
				
				
			}catch(Exception e)
			{
				//e.printStackTrace();
				LOG.report_ERROR(e);
				
			}
			
			return title;


	}
		
	public static  void enterText(By locator, String input)
	{
		
		try
		{
			GenericKeywords.getElement(locator).sendKeys(input);
			
			
			//LOG.report_INFO("Enter Text", "Entered Text \""+ input+ "\"");
		}
		catch(Exception e)
		{
			e.printStackTrace();
			LOG.report_ERROR(e);
			
		}

	}

	public static  void clickElement(By locator)
	{
		

		try
		{
			GenericKeywords.getElement(locator).click();
			
			LOG.report_INFO("Clicked Element Successfully");
			
		}catch(Exception e)
		{
			e.printStackTrace();
			LOG.report_ERROR(e);
			
		}

	}
	
	
	public  static   void stopApplication()
	{
		
		
			TestConfigurations testConfig= GlobalVariables.getConfigMap().get(Thread.currentThread().getName());
			try
			{
				
									
				testConfig.getDriver().quit();	
				LOG.report_INFO( "Application Closed");	

				
				
			}catch(Exception e)
			{
				e.printStackTrace();
				LOG.report_ERROR(e);			
			
			}finally{
				
				TestConfigurations.stopTestReport();
			}


	}
	public  static   void stopApplicationAfterErrorOrFailure(String massages)
	{
		
		
			TestConfigurations testConfig= GlobalVariables.getConfigMap().get(Thread.currentThread().getName());
			try
			{
			
				LOG.report_INFO( massages);						
				testConfig.getDriver().quit();			
				//LOG.report_INFO("Close Application", "Application Closed");
				
				
			}catch(Exception e)
			{
				e.printStackTrace();
				LOG.report_ERROR(e);				
			
			}finally{				
				
				TestConfigurations.stopTestReport();
				/*ExtentManager.getReporter().flush();
				ExtentManager.getReporter().close();*/
				
				System.out.println("**The test has been stopped");
				Assert.fail("Test Stopped");
			}
	}
	
	public static String captureCurrentScreenShot(){
		
		TestConfigurations testConfig= GlobalVariables.getConfigMap().get(Thread.currentThread().getName());
		WebDriver driver= testConfig.getDriver();
		
		String screenShotName= "Snapshot" + "_" + GlobalVariables.getTimestamp()+".png";		
		String screenShotPath=GlobalConstants.currentReportFolderPath+"/"+ screenShotName;
		try{
			
			File scr = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(scr, new File(screenShotPath));
			
			
		}catch(Exception e){
			
			e.printStackTrace();
			//LOG.report_ERROR("Error is Taking Screenshot", e);
			
			
		}
		return screenShotName;
	}

	
	 
	  public static void selectFrame(By locator){
		  TestConfigurations testConfig= GlobalVariables.getConfigMap().get(Thread.currentThread().getName());
		  
		  try{
			  
			  	WebElement frame=GenericKeywords.getElement(locator);
			  	testConfig.getDriver().switchTo().frame(frame);
			  
		  }catch(Exception e){
			  
			  LOG.report_ERROR( e);
			  
		  }
		  
		  
	  }
	  
	  public static void generateTestSuiteReport(String module){
		  
				
			//ExtentTest parent = ExtentTestManager.startTest(module,moduleDesc);
			Map<String, ExtentTest> reportMap= GlobalConstants.testReportMap;
			Map<String, Vector<String>> moduleToTest= GlobalConstants.moduleToTestsMap;
			
			//System.out.println("###Executing Tear Down for Module:" + module);
			
			for (Map.Entry<String, ExtentTest> entry : reportMap.entrySet()){
				
				Vector<String> vec= moduleToTest.get(module);		
				
				
				//System.out.println("\n###Module:" +entry.getKey()+"\t ExtentTest:" + entry.getValue());
				
				
				if (vec.contains(entry.getKey()))
				{
					
					//System.out.println("********************InSide Module:" +entry.getKey());
					//ExtentTest test=entry.getValue();
					//parent2.appendChild(test);	
					
				}
			}
			
			//ExtentTestManager.getExtentReports().endTest(parent2);
		
		  
		  
	  }
	 
	public static void selectByVisibleText(By locator, String text){
		
		try{
			WebElement elem=GenericKeywords.getElement(locator);
			Select selectbox = new Select(elem);
			selectbox.selectByVisibleText(text);
		}catch(Exception e){
			
			LOG.report_ERROR( e);
		}
	}
	
	public static void navigateToURL(String url){
		try{
			TestConfigurations testConfig= GlobalVariables.getConfigMap().get(Thread.currentThread().getName());
		
			WebDriver driver = testConfig.getDriver();
		
			driver.navigate().to(url);
			
		}catch(Exception e){
			
			LOG.report_ERROR( e);
		}
		
	}
	
	public static void switchToNextWindow(){
		
		try{
		
			TestConfigurations testConfig= GlobalVariables.getConfigMap().get(Thread.currentThread().getName());
		
			Set<String> handles=testConfig.getDriver().getWindowHandles();
			String next=null;
			for(String handle:handles)
				next=handle;
		
			testConfig.getDriver().switchTo().window(next);		
		}catch(Exception e){
			
			LOG.report_ERROR(e);
		}
		
	}
	
	public static Map<String,String> getTestData(){
		TestConfigurations testConfig= GlobalVariables.getConfigMap().get(Thread.currentThread().getName());
		return testConfig.getTestData();
		
	}
	public static Map<String,String> getTestData(String excelFileName){
		
		return TestData.getData(excelFileName);
		
	}
	
	public static String generateRandomString(int length){
		
		String CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		StringBuilder strng = new StringBuilder();
        Random rnd = new Random();
        while (strng.length() < length) { // length of the random string.
            int index = (int) (rnd.nextFloat() * CHARS.length());
            strng.append(CHARS.charAt(index));
        }
         
        return strng.toString();		
	}
	
	public static int generateRandomNumber(int length){
		
		String NUMBERS = "1234567890";
		StringBuilder numbs = new StringBuilder();
        Random rnd = new Random();
        while (numbs.length() < length) { // length of the random string.
            int index = (int) (rnd.nextFloat() * NUMBERS.length());
            numbs.append(NUMBERS.charAt(index));
        }
         
        return Integer.parseInt(numbs.toString());		
	}

	public static boolean verifyElementExists(By menuBar) {
		try{
			TestConfigurations testConfig= GlobalVariables.getConfigMap().get(Thread.currentThread().getName());
			
			testConfig.getDriver().findElement(menuBar);
			
			
			return true;
			
		}catch(NoSuchElementException e){
			
			return false;
			
		}catch(TimeoutException t){
			
			return false;
		}
		
	}
	

	public static String toddMMyy(Date day) throws ParseException
	{ 
		SimpleDateFormat sdf= new SimpleDateFormat("dd/MM/yyyy");
	    String SDay = sdf.format(day);
	  	return SDay; 
	}
	
	public static void tryFluentWait(){
		
		TestConfigurations testConfig= GlobalVariables.getConfigMap().get(Thread.currentThread().getName());
		
		Wait<WebDriver> wait=new FluentWait<WebDriver>(testConfig.getDriver()).withTimeout(60, TimeUnit.SECONDS).pollingEvery(5, TimeUnit.SECONDS).ignoring(TimeoutException.class);
		
		
		wait.until(new Function<WebDriver, WebElement>(){			
			public WebElement apply (WebDriver driver){
				return driver.findElement(By.className(" "));
			}			
			
		});
		
		
		
		
	}
	
	public static void clickOnCoordinates(By locator,int x, int y) throws Throwable{
		TestConfigurations testConfig= GlobalVariables.getConfigMap().get(Thread.currentThread().getName());
		Actions action=new Actions(testConfig.getDriver());
		
		Thread.sleep(3000);
		action.moveToElement(getElement(locator), x, y).click().build().perform();
		Thread.sleep(3000);
	}

	public static void enterText(By locator, Keys key) {

		try
		{
			GenericKeywords.getElement(locator).sendKeys(key);
			
			
			//LOG.report_INFO("Enter Text", "Entered Text \""+ input+ "\"");
		}
		catch(Exception e)
		{
			e.printStackTrace();
			LOG.report_ERROR(e);
			
		}
		
	}
	public static String getElementText(By locator) {
			String text=null;
		try
		{
			text=GenericKeywords.getElement(locator).getText();
						

		}
		catch(Exception e)
		{
			e.printStackTrace();
			LOG.report_ERROR(e);
			
		}
		return text;
	}
	
	public static boolean isElementPresent(By locator){
		try{
			TestConfigurations testConfig= GlobalVariables.getConfigMap().get(Thread.currentThread().getName());
			new WebDriverWait(testConfig.getDriver(),5).until(ExpectedConditions.visibilityOfElementLocated(locator));
			return true;
		}catch (Throwable e){
			
			return false;
		}
		
	}

	
		
}
