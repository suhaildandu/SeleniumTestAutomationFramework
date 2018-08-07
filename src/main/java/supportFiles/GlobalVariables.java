package supportFiles;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import io.appium.java_client.AppiumDriver;
import io.selendroid.client.SelendroidDriver;

public class GlobalVariables
{

	public static Map<String ,TestConfigurations> configMap=new HashMap<String,TestConfigurations>() ;
	
	
	
	public static Map<String, TestConfigurations> getConfigMap() {
		return configMap;
	}
	
	public static void setConfigMap(String thread, TestConfigurations tc){
		
		configMap.put(thread, tc);
		
	}

	public static synchronized  void setTestConfiguration(String testName, String moduleName){
		
		try{
			String threadName=moduleName+"_"+testName;
			Thread.currentThread().setName(threadName);
			TestConfigurations testConfig=null;
			Logger log=Logger.getLogger(moduleName);
			
			if(GlobalConstants.automationType.equalsIgnoreCase("Web Application Automation")){
				WebDriver driver= SeleniumDriverSetup.getDriver(GlobalConstants.browser);	
				testConfig=new TestConfigurations(Thread.currentThread().getName(), driver);
			}else if(GlobalConstants.mobileAutomationMode.equalsIgnoreCase("Appium")){
					
				AppiumDriver driver=SeleniumDriverSetup.getAppiumMobileDriver();
				testConfig=new TestConfigurations(Thread.currentThread().getName(), driver);
				}else{
				SelendroidDriver driver= SeleniumDriverSetup.getSelendroidMobileDriver();
				testConfig=new TestConfigurations(Thread.currentThread().getName(), driver);
				
			}
				
			GlobalVariables.setConfigMap(Thread.currentThread().getName(), testConfig);
			String description=GlobalConstants.testToDescMap.get(testName);
			testConfig.setModuleName(moduleName);
			//System.out.println("#ThreadName: "+testName+"\tDescription: "+ description);
			testConfig.startTestReport(GlobalConstants.moduleToReportMap.get(moduleName),description);
			testConfig.setTestData(TestData.getData(moduleName));
			testConfig.setLogger(log);
			
		}catch(Exception e){
			
			e.printStackTrace();
		}
	}
	
	public static String getTimestamp()
	{
		
		String timeStamp = new SimpleDateFormat("MM.dd.yyyy__hh.mm.ssa").format(new Date());
		
		return timeStamp;
	}


}
