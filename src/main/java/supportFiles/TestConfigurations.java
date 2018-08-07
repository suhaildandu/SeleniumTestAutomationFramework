package supportFiles;



import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.ExtentTest;

import io.appium.java_client.AppiumDriver;
import io.selendroid.client.SelendroidDriver;





public class TestConfigurations extends SeleniumDriverSetup {


	
	public String testConfigs = null;	
	public String thread= null;
	public ExtentTest report;	
	public WebDriver driver = null;
	public SelendroidDriver selendroidDriver=null;
	public AppiumDriver appiumDriver=null;
	public String moduleName=null;
	public Map<String, String> testData= new HashMap<String, String>();
	public Logger log=null;
	
	
	
	public Logger getLogger() {
		return this.log;
	}

	public void setLogger(Logger log) {
		this.log = log;
	}

	public void setTestData(Map<String,String> map){
		
		this.testData=map;
	}
	
	public Map<String,String> getTestData(){
		
		return this.testData;
	}
	
	
	public String getThread() {
		return Thread.currentThread().getName();
	}

	public void setThread(String thread) {
		this.thread = thread;
	}

	
	
	public TestConfigurations (String thread, WebDriver driver){
		
		this.thread=thread;
		this.driver=driver;	
		
	}
	
public TestConfigurations (String thread, SelendroidDriver driver){
		
		this.thread=thread;
		this.selendroidDriver=driver;	
		
	}
public TestConfigurations (String thread, AppiumDriver driver){
	
	this.thread=thread;
	this.appiumDriver=driver;	
	
}
	
	
	public void startTestReport(ExtentTest test,String testDescription){
		String threadName=Thread.currentThread().getName();		
		String testName=null;
		
		int index=threadName.indexOf('_');
		testName=threadName.substring(index+1);
		
		this.report= ExtentTestManager.startNode(test,testName,testDescription);
		GlobalConstants.testReportMap.put(testName, this.report);
		
	}
	
	/*public Map<String, ExtentTest> getTestReportMap(){
		
		return testReportMap;
	}*/
	

	public static void stopTestReport(){
		
		ExtentTestManager.endTest();
	}
	
	public ExtentTest getReporter(){
		String threadName=Thread.currentThread().getName();		
		String testName=null;
		
		int index=threadName.indexOf('_');
		testName=threadName.substring(index+1);
			
		return GlobalConstants.testReportMap.get(testName);
	}
	
	public void setModuleName(String moduleName){
		
		this.moduleName=moduleName;
	}
	
	public String getModuleName(){
		
		return moduleName;
	}
	


	public  WebDriver getDriver() {
		return driver;
	}
	
	public  AppiumDriver getAppiumDriver() {
		return this.appiumDriver;
	}
	
	public  SelendroidDriver getSelendroidDriver() {
		return this.selendroidDriver;
	}
	
	
	public <Any> Any getMobileDriver(){
		Any any=null;
		if(GlobalConstants.mobileAutomationMode.equalsIgnoreCase("Appium"))	
			any=((Any)((SelendroidDriver)(this.selendroidDriver)));
		else
			any=((Any)((AppiumDriver)(this.appiumDriver)));
		
		return any;
	}
}
