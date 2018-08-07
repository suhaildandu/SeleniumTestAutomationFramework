package supportFiles;




import java.io.File;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentTest;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.selendroid.standalone.SelendroidConfiguration;
import io.selendroid.standalone.SelendroidLauncher;



public class TestListener implements ITestListener,IInvokedMethodListener {
	AppiumDriverLocalService appiumService;
	Set<String> set= new HashSet<String>();
	Logger log;
	//**** Change Below Paths for Starting Appium Internally*****//
	String Appium_Node_Path="C:\\Program Files (x86)\\Appium\\node.exe";
	String Appium_JS_Path="C:/Program Files (x86)/Appium/node_modules/appium/bin/appium.js";
	
	@Override
	public synchronized void beforeInvocation(IInvokedMethod method, ITestResult listner) {
		
		TestConfigurations testConfig= GlobalVariables.getConfigMap().get(Thread.currentThread().getName());
		//if(!method.isTestMethod())
		//	testConfig.getLogger().info("Non Test Method: "+method.toString());
		
	}


	@Override
	public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
		
		
		
	}
	
	//****** Add Keyword ["synchronized"] to method onTestStart if there are issue is reports for parallel execution****//
	@Override
	public void onTestStart(ITestResult listner) {
		
		String testName=listner.getName();
		String currentClassName= listner.getTestClass().toString().split("testFiles.")[1].split("]")[0];
		
		
		boolean flag=set.contains(currentClassName);
		//System.out.println("#Current Method Name: " + testName +"\t Class Name: " + currentClassName+" \t Add Flag: " + flag);		
		
		if(set.add(currentClassName)){
			
			
			if(!GlobalConstants.moduleToReportMap.containsKey(currentClassName)){
				ExtentTest parent=ExtentTestManager.startModule(currentClassName,GlobalConstants.moduleToDescMap.get(currentClassName));
				GlobalConstants.moduleToReportMap.put(currentClassName, parent);
			}
				
		}
		
		
		GlobalVariables.setTestConfiguration(testName,currentClassName);	
		/********** Initializing Log4j Here *************/
		
		
		
		log=Logger.getLogger(TestListener.class);
		
		log.info("Test ["+testName+"] has started");
		
		/**************************************************/
	
		
		
		if(GlobalConstants.automationType.equals("Web Application Automation"))
			GenericKeywords.startApplication();	
		/*else if (GlobalConstants.mobile_automation_type.equalsIgnoreCase("Mobile Browser"))
			GenericMobileKeywords.startApplication();	*/
		
		
		
		
	}


	@Override
	public void onFinish(ITestContext listner) {
		
		//TestConfigurations.stopTestReport();
		ExtentManager.getReporter().flush();
		//ExtentManager.getReporter().close();
		
		if(GlobalConstants.mobileAutomationMode.equalsIgnoreCase("Selendroid"))
			GlobalConstants.selendroidServer.stopSelendroid();
		if(GlobalConstants.mobileAutomationMode.equalsIgnoreCase("Appium") && GlobalConstants.mobile_appium_server_type.equalsIgnoreCase("Internal")){			
			
			this.appiumService.stop();
		}
	}

	@Override
	public void onStart(ITestContext testContext) {
		

		if(GlobalConstants.mobileAutomationMode.equalsIgnoreCase("Selendroid")){
			SelendroidConfiguration config = new SelendroidConfiguration();	
			//config.setPort(4723);
			File app = new File(GlobalConstants.mobileAPKFilePath, GlobalConstants.mobileAppAPKName);		
			config.addSupportedApp(app.getAbsolutePath());
			GlobalConstants.selendroidServer = new SelendroidLauncher(config);
			GlobalConstants.selendroidServer.launchSelendroid();
		}
		if(GlobalConstants.mobileAutomationMode.equalsIgnoreCase("Appium") && GlobalConstants.mobile_appium_server_type.equalsIgnoreCase("Internal")){	
			
		
			

			this.appiumService = AppiumDriverLocalService.buildService(new AppiumServiceBuilder().
				                usingAnyFreePort().usingDriverExecutable(new File(Appium_Node_Path)).
				                withAppiumJS(new File(Appium_JS_Path)));
			
			this.appiumService.start();
			GlobalConstants.appiumServiceUrl = appiumService.getUrl().toString();
		}
		
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult listner) {
		
		log.fatal("Test ["+listner.getName()+"] has failed",listner.getThrowable());
		
	}

	@Override
	public void onTestFailure(ITestResult listner) {
		
		
		
		if(listner.getThrowable()!=null && !(listner.getThrowable().toString().contains("java.lang.AssertionError")))
			LOG.report_ERROR(listner.getThrowable());
		else if (listner.getThrowable().toString().contains("java.lang.AssertionError"))
			listner.getThrowable().printStackTrace();
		
		if(GlobalConstants.automationType.equals("Web Application Automation"))
			GenericKeywords.stopApplication();
		else
			GenericMobileKeywords.stopApplication();
		TestConfigurations.stopTestReport();
		
		log.fatal("Test ["+listner.getName()+"] has failed",listner.getThrowable());

	}

	@Override
	public void onTestSkipped(ITestResult listner) {
	
		
		TestConfigurations.stopTestReport();
		log.info("Test ["+listner.getName()+"] has skipped");
	}


	@Override
	public void onTestSuccess(ITestResult listner) {
		
		if(GlobalConstants.automationType.equals("Web Application Automation"))
			GenericKeywords.stopApplication();
		else
			GenericMobileKeywords.stopApplication();
		TestConfigurations.stopTestReport();	
		
		log.info("Test ["+listner.getName()+"] has PASSED");
		
	}	

	

}
