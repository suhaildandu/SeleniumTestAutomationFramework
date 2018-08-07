package supportFiles;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import io.selendroid.standalone.SelendroidLauncher;





public class GlobalConstants {
	
	
	//*** SET FLAG FOR SCREENSHOT 	
	public static final boolean flagTakeScreenShotForPASS= true;
	public static final boolean flagTakeScreenShotForFAIL= true;
	public static final boolean flagTakeScreenShotForINFO= false;
	public static final boolean flagTakeScreenShotForERROR= false;
	public static final boolean flagTakeScreenShotForWARNING= false;
	
	
	//*** SELENIUM WAIT RELATED PAREMATERS
	public static final int implicitWaitTime=10;
	public static final int explicitWaitTime=60;
	public static final int pollingWaitTime=5;
	
	
	//*** FRAMEWORK PARAMETERS
	public static final String projectDirectoryPath=System.getProperty("user.dir"); 
	public static String URL=null;
	public static String browser=null;
	public static ExtentReports extent;
	public static String specURL=null;
	public static String platform=null;	
	
	//*** ALL THE RESOURCES PATHS ****	
	
	public static final String excelpath = projectDirectoryPath+"/RunEngine.xlsx";
	public static final String testng_xml_path = projectDirectoryPath+"/testit.xml";
	public static final String exec_report_path=projectDirectoryPath+"/reportLogs/";
	public static final String librariesPath=projectDirectoryPath+"/libraries/";
	public static final String dataSheetsPath=projectDirectoryPath+"/dataSheets/";
	public static final String testng_xml_filename = "testit.xml";	
	
	
	//*** EXTENT REPORT RELATED PARAMETERS
	public static String executionReportName;
	public static String currentReportFolderPath;
	public static String currentReportFolderName;
	public static final String extentConfigFilePath=projectDirectoryPath+"/extent-config.xml";
	public static Map<String,String> testToDescMap= new HashMap<String,String>();
	public static Map<String,String> moduleToDescMap= new HashMap<String,String>();
	public static Map<String, Vector<String>> moduleToTestsMap= new HashMap<String, Vector<String>>();
	public static Map<String,ExtentTest> testReportMap= new HashMap<String, ExtentTest>();
	public static Map<String,ExtentTest> moduleToReportMap= new HashMap<String, ExtentTest>();
	
	
	
	//*** CONTANSTS FOR RUN ENGINE ***	
	
		
	
	//***Main Sheet
	public static final String wrksheet_main="Main";	
	public static String automationType=null;
	public static String executionMode=null;
	public static String gridIPAddressPort="127.0.0.1:4723";
	public static String nodeOS=null;
	public static String projectName=null;
	
	
	
	//***WebAutomationMain Sheet
	public static final String sheet_name_modules = "Modules";	
	public static String wrksheet_name = "";
	public static final String wrksheet_suitetype = "WebAutomationMain";
	public static final int wrksheet_suitetype_col_suitetype = 0;
	public static final int wrksheet_suitetype_col_selectBrowser=1;
	public static final int wrksheet_suitetype_col_url=0;
	public static final int wrksheet_suitetype_col_paralellThreads=2;
	public static final int wrksheet_suitetype_col_platform=3;
	public static final int wrksheet_suitetype_select_mode=4;
	
	//*** Module and Test Related columns	
	public static final int wrksheettcn_col_tcnames = 0;
	public static final int wrksheettcn_col_tcdesc = 1;	
	public static final int col_tcrunmode = 2;

	public static final int col_moduleid = 0;
	public static final int col_moduleDesc=1;
	public static final int col_modulerunmode = 2;

	
	



	
	//****** All Variables for Appium Mobile Automation ********
	
	public static String selendroid_server_jar="selendroid-standalone-0.17.0-with-dependencies.jar";
	

	public static final String mobileAPKFilePath=projectDirectoryPath+"/mobileApps/";
	public static final String sheet_name_mobileAutomation = "MobileAutomationMain";
	public static String mobileAutomationMode="null";
	public static String mobileExecutionMode=null; 
	public static String mobile_appium_server_type=null;
	public static String mobileDeviceName=null;
	public static String mobileAppAPKName=null;
	public static String mobilePlatformVersion=null;
	public static String mobilePlatformName=null;
	public static String mobileAppPackage=null;
	public static String mobileAppActivity=null;
	public static String mobileBrowser=null;
	public static String mobileAppID=null;
	
	
	
	public static final int mobile_col_automationMode=0;
	public static final int mobile_col_executionMode=1;
	public static final int mobile_col_appium_server_type=2;
	public static final int mobile_col_appFileName=3;
	public static final int mobile_col_appID=4;
	public static final int mobile_col_URL=5;
	public static final int mobile_col_deviceName=6;
	public static final int mobile_col_platFormVersion=7;
	public static final int mobile_col_platFormName=8;
	public static final int mobile_col_mobileBrowser=9;
	public static final int mobile_col_appPackage=10;
	public static final int mobile_col_appActivity=11;
	public static final int mobile_col_selectMode=12;
	
	public static SelendroidLauncher selendroidServer=null;
	public static String appiumServiceUrl=null;
	
	
	
	
}
