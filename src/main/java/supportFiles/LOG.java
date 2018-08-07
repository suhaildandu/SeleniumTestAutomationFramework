package supportFiles;

import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.MediaEntityBuilder;

public class LOG {
	
	
	public static void report_PASS(String logMessage){		
		TestConfigurations testConfig= GlobalVariables.getConfigMap().get(Thread.currentThread().getName());
		
		try{
			testConfig.getLogger().info(logMessage);
			if(GlobalConstants.flagTakeScreenShotForPASS){
				String screenShot=null;
				if(GlobalConstants.automationType.equals("Web Application Automation"))
					screenShot=GenericKeywords.captureCurrentScreenShot();
				else
					screenShot=GenericMobileKeywords.captureCurrentScreenShot();
					
				String path=System.getProperty("user.dir")+"/ReportLogs/"+GlobalConstants.currentReportFolderName+"/"+screenShot;			
				
				testConfig.getReporter().pass(logMessage,MediaEntityBuilder.createScreenCaptureFromPath(path).build());
				
			}else
				testConfig.getReporter().pass(logMessage);
			}catch(Exception e){
				
				e.printStackTrace();
			}
	}
	
	public static void report_FAIL(String errorMessage){
		TestConfigurations testConfig= GlobalVariables.getConfigMap().get(Thread.currentThread().getName());
		
		try{
			testConfig.getLogger().fatal(errorMessage);
		
		if(GlobalConstants.flagTakeScreenShotForFAIL){
			
			String screenShot=null;
			if(GlobalConstants.automationType.equals("Web Application Automation"))
				screenShot=GenericKeywords.captureCurrentScreenShot();
			else
				screenShot=GenericMobileKeywords.captureCurrentScreenShot();
			String path=System.getProperty("user.dir")+"/ReportLogs/"+GlobalConstants.currentReportFolderName+"/"+screenShot;
			
			testConfig.getReporter().fail(errorMessage,MediaEntityBuilder.createScreenCaptureFromPath(path).build());			
		}else
			testConfig.getReporter().fail(errorMessage);
		
		testConfig.getDriver().quit();
		Assert.fail(errorMessage);		
		
		}catch(Exception e){
			Assert.fail(errorMessage);
			e.printStackTrace();
		}
		
	}
	
	public static void reportFailAndContinue(String errorMessage){
		TestConfigurations testConfig= GlobalVariables.getConfigMap().get(Thread.currentThread().getName());
		testConfig.getLogger().fatal(errorMessage);
		try{
			testConfig.getLogger().fatal(errorMessage);
			if(GlobalConstants.flagTakeScreenShotForFAIL){
			
				String screenShot=null;
				if(GlobalConstants.automationType.equals("Web Application Automation"))
					screenShot=GenericKeywords.captureCurrentScreenShot();
				else
					screenShot=GenericMobileKeywords.captureCurrentScreenShot();
				String path=System.getProperty("user.dir")+"/ReportLogs/"+GlobalConstants.currentReportFolderName+"/"+screenShot;
			
				testConfig.getReporter().fail(errorMessage,MediaEntityBuilder.createScreenCaptureFromPath(path).build());			
			}else
				testConfig.getReporter().fail(errorMessage);
			
			new SoftAssert().fail(errorMessage);
		}catch(Exception e){
			new SoftAssert().fail(errorMessage);
			e.printStackTrace();
		}
		
		
		
	}
	public static void report_FAIL(Throwable e){
		TestConfigurations testConfig= GlobalVariables.getConfigMap().get(Thread.currentThread().getName());
		testConfig.getLogger().fatal(e);
		try{
		
		if(GlobalConstants.flagTakeScreenShotForFAIL){
			String screenShot=null;
			if(GlobalConstants.automationType.equals("Web Application Automation"))
				screenShot=GenericKeywords.captureCurrentScreenShot();
			else
				screenShot=GenericMobileKeywords.captureCurrentScreenShot();
			String path=System.getProperty("user.dir")+"/ReportLogs/"+GlobalConstants.currentReportFolderName+"/"+screenShot;			
			testConfig.getReporter().fail(e,MediaEntityBuilder.createScreenCaptureFromPath(path).build());				
		}
		else
			testConfig.getReporter().fail(e);
		testConfig.getDriver().quit();
		Assert.fail(e.getMessage());
		}catch(Exception ex){
			Assert.fail(e.getMessage());
			ex.printStackTrace();
		}
	}
	
	public static void reportFailAndContinue(Throwable e){
		TestConfigurations testConfig= GlobalVariables.getConfigMap().get(Thread.currentThread().getName());
		testConfig.getLogger().fatal(e);
		try{
		
		if(GlobalConstants.flagTakeScreenShotForFAIL){
			String screenShot=null;
			if(GlobalConstants.automationType.equals("Web Application Automation"))
				screenShot=GenericKeywords.captureCurrentScreenShot();
			else
				screenShot=GenericMobileKeywords.captureCurrentScreenShot();
			String path=System.getProperty("user.dir")+"/ReportLogs/"+GlobalConstants.currentReportFolderName+"/"+screenShot;			
			testConfig.getReporter().fail(e,MediaEntityBuilder.createScreenCaptureFromPath(path).build());				
		}
		else
			testConfig.getReporter().fail(e);
			new SoftAssert().fail(e.getMessage());
		}catch(Exception ex){
			
			new SoftAssert().fail(e.getMessage());
			ex.printStackTrace();
		}
		//GenericKeywords.stopApplicationAfterErrorOrFailure("Application Stopped Forcefully Due to Test Failure");
		
	}
	
	public static void report_INFO(String logMessage){
		TestConfigurations testConfig= GlobalVariables.getConfigMap().get(Thread.currentThread().getName());
		try{
			testConfig.getLogger().info(logMessage);

		if(GlobalConstants.flagTakeScreenShotForINFO){
			String screenShot=null;
			if(GlobalConstants.automationType.equals("Web Application Automation"))
				screenShot=GenericKeywords.captureCurrentScreenShot();
			else
				screenShot=GenericMobileKeywords.captureCurrentScreenShot();
			String path=System.getProperty("user.dir")+"/ReportLogs/"+GlobalConstants.currentReportFolderName+"/"+screenShot;			
			testConfig.getReporter().info(logMessage,MediaEntityBuilder.createScreenCaptureFromPath(path).build());			
		}else
			testConfig.getReporter().info(logMessage);
		}catch(Exception ex){
			
			ex.printStackTrace();
		}
		
	}
	
	public static void report_WARNING(String warningMessage){
		TestConfigurations testConfig= GlobalVariables.getConfigMap().get(Thread.currentThread().getName());
		
		try{
		
		if(GlobalConstants.flagTakeScreenShotForWARNING){
			String screenShot=null;
			if(GlobalConstants.automationType.equals("Web Application Automation"))
				screenShot=GenericKeywords.captureCurrentScreenShot();
			else
				screenShot=GenericMobileKeywords.captureCurrentScreenShot();
			String path=System.getProperty("user.dir")+"/ReportLogs/"+GlobalConstants.currentReportFolderName+"/"+screenShot;			
			testConfig.getReporter().warning(warningMessage,MediaEntityBuilder.createScreenCaptureFromPath(path).build());			
		}else
			testConfig.getReporter().warning(warningMessage);
		}catch(Exception ex){
			
			ex.printStackTrace();
		}
		
	}
	
	public static void report_ERROR(Throwable e) {
		TestConfigurations testConfig= GlobalVariables.getConfigMap().get(Thread.currentThread().getName());
		testConfig.getLogger().fatal(e);
		try{
		
		if(GlobalConstants.flagTakeScreenShotForERROR){
			String screenShot=null;
			if(GlobalConstants.automationType.equals("Web Application Automation"))
				screenShot=GenericKeywords.captureCurrentScreenShot();
			else
				screenShot=GenericMobileKeywords.captureCurrentScreenShot();
			String path=System.getProperty("user.dir")+"/ReportLogs/"+GlobalConstants.currentReportFolderName+"/"+screenShot;			
			testConfig.getReporter().error(e,MediaEntityBuilder.createScreenCaptureFromPath(path).build());		
			//GenericKeywords.stopApplicationAfterErrorOrFailure("Application Stopped Forcefully Due to Error");
		}
		else
			testConfig.getReporter().error(e);
			
				
		testConfig.getDriver().quit();
		Assert.fail(e.getMessage());
		
		}catch(Exception exception){
			Assert.fail(e.getMessage());
			exception.printStackTrace();
			
		}
		
	}
	public static void report_ERROR(String errorMessage){
		TestConfigurations testConfig= GlobalVariables.getConfigMap().get(Thread.currentThread().getName());
		testConfig.getLogger().fatal(errorMessage);
		try{
		if(GlobalConstants.flagTakeScreenShotForERROR){
			String screenShot=null;
			if(GlobalConstants.automationType.equals("Web Application Automation"))
				screenShot=GenericKeywords.captureCurrentScreenShot();
			else
				screenShot=GenericMobileKeywords.captureCurrentScreenShot();
			String path=System.getProperty("user.dir")+"/ReportLogs/"+GlobalConstants.currentReportFolderName+"/"+screenShot;		
			testConfig.getReporter().error(errorMessage,MediaEntityBuilder.createScreenCaptureFromPath(path).build());			
			GenericKeywords.stopApplicationAfterErrorOrFailure("Application Stopped Forcefully Due to Error");
		}
		else
			testConfig.getReporter().error(errorMessage);
		
		testConfig.getDriver().quit();
		Assert.fail(errorMessage);
		
		}catch(Exception exception){
			Assert.fail(errorMessage);
			exception.printStackTrace();
			
		}
			
	}
		

}
