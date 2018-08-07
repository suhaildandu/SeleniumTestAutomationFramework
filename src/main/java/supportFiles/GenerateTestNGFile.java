package supportFiles;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.testng.TestNG;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlInclude;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;



public class GenerateTestNGFile {
	private static String modulerunmode;
	private static String moduleid;
	private static String moduleDesc;
	private static String classname;
	private static String tcname;
	private static String tcdesc;
	
	private static int threadCount=1;
	private static XmlSuite suite;
	private static XmlTest test;
	private static XmlClass testClass;
	private static ArrayList<XmlClass> classes;
	private static ArrayList<XmlInclude> methodsToRun;	
	//private static XmlGroups testgroups;
	
	public static void createTestNG(String groupName) throws Throwable{
		
		String groupNames[]=groupName.split(":");
		List<String> testClasses=getTestClasses();
		
		suite = new XmlSuite ();
		test=new XmlTest(suite);
		suite.setName("CEDRIC_Automation_Test");
		suite.setParallel("methods");
		suite.setThreadCount(threadCount);
		suite.setListeners(addListeners());
		classes = new ArrayList<XmlClass>();        
        testClass = new XmlClass();
        
        test.setPreserveOrder("true");
        test.setName("Automation");
        
        for(String className:testClasses){    
        	
        	classes.add(new XmlClass("testFiles."+className));
        }
        test.setXmlClasses(classes);
        //test.addIncludedGroup(groupNames[0]);
        
        for(String group:groupNames )
        	test.addIncludedGroup(group);	
        
        
	}
	
	
	@SuppressWarnings("deprecation")
	protected static void createSuite(){
		suite = new XmlSuite ();
		suite.setName("Functional_Automation_Test");
		suite.setParallel("methods");
		suite.setThreadCount(threadCount);		
		Map<String, String> suiteMetaDataMap = new HashMap<String, String>();
		//suiteMetaDataMap.put("SiteAddress", "${SiteAddress}");
		suiteMetaDataMap.put("SiteAddress", GlobalConstants.URL);
		//suiteMetaDataMap.put("SiteAddress", "http://172.27.104.44:8080/traksmart4/unprotected///login.do");
		suite.setParameters(suiteMetaDataMap);		
		suite.setListeners(addListeners());
	}
	
	private static void createTest(){
		test = new XmlTest(suite);
        test.setPreserveOrder("true"); 
        test.setName("Automation"); 
	}
	
	public static void readClassesMethods() throws Exception{
		int tccount; 
		
		createSuite();
		createTest();              
        classes = new ArrayList<XmlClass>();        
        testClass = new XmlClass();
        ExcelUtils.setExcelFile(GlobalConstants.excelpath);		
		int totalmodules = ExcelUtils.getRowCount(GlobalConstants.sheet_name_modules);		
		////log.info("****************Total Module is:" +totalmodules);
		for(int module = 1; module<totalmodules; module++){
			modulerunmode = ExcelUtils.getCellData(module, GlobalConstants.col_modulerunmode, GlobalConstants.sheet_name_modules);
			moduleid = ExcelUtils.getCellData(module, GlobalConstants.col_moduleid, GlobalConstants.sheet_name_modules);
			moduleDesc=ExcelUtils.getCellData(module, GlobalConstants.col_moduleDesc, GlobalConstants.sheet_name_modules);
			////log.info("********** Module Id's are" + moduleid);
			if(modulerunmode.equalsIgnoreCase("Yes")){
				
				//System.out.println("###***ModuleName: " + moduleid );
				//classname = ExcelUtils.getCellData(module, GlobalConstants.col_classnames, GlobalConstants.sheet_name_modules);
				classname="testFiles."+moduleid;
				//url= ExcelUtils.getCellData(module, FrameworkLevelData.col_classnames, FrameworkLevelData.sheet_modules);
				//log.info("Class Names are" + classname);				
				testClass.setName(classname);				
				methodsToRun = new ArrayList<XmlInclude>();				
				//log.info("***********Class Name is:" + classname);	
				//int totaltcs = ExcelUtils.getRowCount(FrameworkLevelData.wrksheet_testcasesnames);
				GlobalConstants.wrksheet_name = moduleid;
				int totaltcs = ExcelUtils.getRowCount(GlobalConstants.wrksheet_name);
				//log.info("Total Methods are: " + totaltcs);
				int rowcountoftc = 0;
				Vector<String> testNames= new Vector<String>();
				for(tccount = 1; tccount<= totaltcs; tccount++){
					//moduleid_inwrksheettcn = ExcelUtils.getCellData(tccount, FrameworkLevelData.col_moduleid, FrameworkLevelData.wrksheet_testcasesnames);
					//String tcrunmode = ExcelUtils.getCellData(tccount, FrameworkLevelData.col_tcrunmode, FrameworkLevelData.wrksheet_testcasesnames);
					String tcrunmode = ExcelUtils.getCellData(tccount, GlobalConstants.col_tcrunmode, GlobalConstants.wrksheet_name);
					////log.info("Module id is:" +moduleid_inwrksheettcn);
					//if(moduleid_inwrksheettcn.equals(moduleid)&& tcrunmode.equals("Yes")){
					if(tcrunmode.equals("Yes")){
						/*//log.info("*******" +ExcelUtils.getCellData(tccount, FrameworkLevelData.col_moduleid, FrameworkLevelData.wrksheet_testcasesnames));
						tcname = ExcelUtils.getCellData(tccount, FrameworkLevelData.wrksheettcn_col_tcnames, FrameworkLevelData.wrksheet_testcasesnames);*/
						//log.info("*******" +ExcelUtils.getCellData(tccount, GlobalConstants.col_moduleid, GlobalConstants.wrksheet_name));
						tcname = ExcelUtils.getCellData(tccount, GlobalConstants.wrksheettcn_col_tcnames, GlobalConstants.wrksheet_name);
						tcdesc = ExcelUtils.getCellData(tccount, GlobalConstants.wrksheettcn_col_tcdesc, GlobalConstants.wrksheet_name);
						
						testNames.add(tcname);		
						
						GlobalConstants.testToDescMap.put(tcname,tcdesc);
						//log.info("Test Case name to be included is:" + tcname);						
						rowcountoftc++;						
						methodsToRun.add(new XmlInclude(tcname));
						testClass.setIncludedMethods(methodsToRun);											
					}					
				}				
				//log.info("Row count of " +moduleid+ " is " +rowcountoftc);
				if(rowcountoftc > 0){
					classes.add(testClass);
				}							
				test.setXmlClasses(classes);
				
				
				GlobalConstants.moduleToTestsMap.put(moduleid,testNames);
				GlobalConstants.moduleToDescMap.put(moduleid, moduleDesc);
				
			}			
			testClass = new XmlClass();		
		}		
	}
	
	protected static ArrayList<String>addListeners(){
		ArrayList<String> listeners = new ArrayList<String>();
		listeners.add("supportFiles.TestListener");
		return listeners;
	}
	
	public static void createTestNGFile(String filepath) throws IOException{
		File file = new File(filepath);
		//File file = new File("D:\\testit.xml");
		FileWriter writer = new FileWriter(file);
		writer.write(suite.toXml());
		writer.close();	
	}
	
	public static void runTestNG(String filename){
		TestNG runner=new TestNG();		  
		// Create a list of String 
		List<String> suitefiles=new ArrayList<String>();			  
		// Add xml file which you have to execute
		suitefiles.add(GlobalConstants.testng_xml_filename);		
		// now set xml file for execution
		runner.setTestSuites(suitefiles);		
		// finally execute the runner using run method
		runner.run();
	}
	
	/*public static void readGroups() throws Exception{
		createSuite();
		createTest();		       
		testgroups = new XmlGroups();
		XmlRun run = new XmlRun();		
		testgroups.setRun(run);
		ExcelUtils.setExcelFile(GlobalConstants.excelpath);
		int rowcount = ExcelUtils.getRowCount(GlobalConstants.wrksheet_groupname);
		//log.info("****************Total rowcount is:" +rowcount);
		for(int row = 1; row<rowcount; row++){
			String groupname = ExcelUtils.getCellData(row, GlobalConstants.col_groupname, GlobalConstants.wrksheet_groupname);
			String runmode = ExcelUtils.getCellData(row, GlobalConstants.col_groupname_runmode, GlobalConstants.wrksheet_groupname);
			if(runmode.equals("Yes")){
				test.addIncludedGroup(groupname);
			}
		}
		classes = new ArrayList<XmlClass>();        
	    testClass = new XmlClass();
		int totalmodules = ExcelUtils.getRowCount(GlobalConstants.sheet_name_modules);		
		//log.info("****************Total Module is:" +totalmodules);
		for(int module = 1; module<totalmodules; module++){
			modulerunmode = ExcelUtils.getCellData(module, GlobalConstants.col_modulerunmode, GlobalConstants.sheet_name_modules);
			moduleid = ExcelUtils.getCellData(module, GlobalConstants.col_moduleid, GlobalConstants.sheet_name_modules);
			//log.info("********** Module Id's are" + moduleid);
			if(modulerunmode.equalsIgnoreCase("Yes")){
				//classname = ExcelUtils.getCellData(module, GlobalConstants.col_classnames, GlobalConstants.sheet_name_modules);
				//log.info("Class Names are" + classname);				
				testClass.setName(classname);	
				classes.add(testClass);
				test.setXmlClasses(classes);		
			}		
		testClass = new XmlClass();			
	}
	}*/
	
	public static void createTestNGFileGroupWise(String executionTag,String executionMode,String browser, String threadC) throws Throwable{
		ExcelUtils.setExcelFile(GlobalConstants.excelpath);
		GlobalConstants.automationType= ExcelUtils.getCellData(1, 0, GlobalConstants.wrksheet_main);
		GlobalConstants.projectName=ExcelUtils.getCellData(1, 2, GlobalConstants.wrksheet_main);
		GlobalConstants.executionMode=executionMode;
		
		if(GlobalConstants.executionMode.equalsIgnoreCase("Grid")){
			GlobalConstants.gridIPAddressPort=ExcelUtils.getCellData(1, 3, GlobalConstants.wrksheet_main);
			GlobalConstants.nodeOS=ExcelUtils.getCellData(1, 4, GlobalConstants.wrksheet_main);
		}
		
		
		if(GlobalConstants.automationType.equalsIgnoreCase("Web Application Automation")){
			threadCount=Integer.parseInt(threadC);
			int rowcount = ExcelUtils.getRowCount(GlobalConstants.wrksheet_suitetype);			
			boolean flag=false;
			String select;
			GlobalConstants.browser=browser;
			for(int row = 1; row<rowcount; row++){

				select= ExcelUtils.getCellData(row, GlobalConstants.wrksheet_suitetype_select_mode, GlobalConstants.wrksheet_suitetype);			
				if (select.equalsIgnoreCase("Yes")){			
				
					GlobalConstants.URL= ExcelUtils.getCellData(row, GlobalConstants.wrksheet_suitetype_col_url, GlobalConstants.wrksheet_suitetype);
					GlobalConstants.platform=ExcelUtils.getCellData(row, GlobalConstants.wrksheet_suitetype_col_platform, GlobalConstants.wrksheet_suitetype);
					//threadCount=Integer.parseInt(ExcelUtils.getCellData(row, GlobalConstants.wrksheet_suitetype_col_paralellThreads, GlobalConstants.wrksheet_suitetype));			
					flag=true;
				}		
				
			}
			if (!flag)
				System.out.println("###############****PLEASE SELECT ATLEAST ONE ROW IN WEB AUTOMATION MAIN BY CHOOSING SELECT MODE AS 'Yes'*****###############");
		}else{
			
			selectMobileSettings();		
			
		}
		
		GenerateTestNGFile.createTestNG(executionTag);
		
		
		
	}
	
	
	public static void selectSuiteType() throws Throwable{
		ExcelUtils.setExcelFile(GlobalConstants.excelpath);
		GlobalConstants.automationType= ExcelUtils.getCellData(1, 0, GlobalConstants.wrksheet_main);
		GlobalConstants.executionMode= ExcelUtils.getCellData(1, 1, GlobalConstants.wrksheet_main);
		GlobalConstants.projectName=ExcelUtils.getCellData(1, 2, GlobalConstants.wrksheet_main);
		
		if(GlobalConstants.executionMode.equalsIgnoreCase("Grid")){
			GlobalConstants.gridIPAddressPort=ExcelUtils.getCellData(1, 3, GlobalConstants.wrksheet_main);
			GlobalConstants.nodeOS=ExcelUtils.getCellData(1, 4, GlobalConstants.wrksheet_main);
		}
		
		
		
		if(GlobalConstants.automationType.equalsIgnoreCase("Web Application Automation"))
			selectWebAutomationSettings();
		else
			selectMobileSettings();
		
			readClassesMethods();			
			
}
	
	public static void selectWebAutomationSettings() throws Throwable{
		
		int rowcount = ExcelUtils.getRowCount(GlobalConstants.wrksheet_suitetype);			
		boolean flag=false;
		String select;
		
		for(int row = 1; row<rowcount; row++){

			select= ExcelUtils.getCellData(row, GlobalConstants.wrksheet_suitetype_select_mode, GlobalConstants.wrksheet_suitetype);			
			if (select.equalsIgnoreCase("Yes")){			
			
				GlobalConstants.URL= ExcelUtils.getCellData(row, GlobalConstants.wrksheet_suitetype_col_url, GlobalConstants.wrksheet_suitetype);
				GlobalConstants.browser=ExcelUtils.getCellData(row, GlobalConstants.wrksheet_suitetype_col_selectBrowser, GlobalConstants.wrksheet_suitetype);
				GlobalConstants.platform=ExcelUtils.getCellData(row, GlobalConstants.wrksheet_suitetype_col_platform, GlobalConstants.wrksheet_suitetype);
				threadCount=Integer.parseInt(ExcelUtils.getCellData(row, GlobalConstants.wrksheet_suitetype_col_paralellThreads, GlobalConstants.wrksheet_suitetype));			
				
				flag=true;
			}	
				
				
						
							
	}
		if (!flag){
			int row=1;
			GlobalConstants.URL= ExcelUtils.getCellData(row, GlobalConstants.wrksheet_suitetype_col_url, GlobalConstants.wrksheet_suitetype);
			GlobalConstants.browser=ExcelUtils.getCellData(row, GlobalConstants.wrksheet_suitetype_col_selectBrowser, GlobalConstants.wrksheet_suitetype);
			GlobalConstants.platform=ExcelUtils.getCellData(row, GlobalConstants.wrksheet_suitetype_col_platform, GlobalConstants.wrksheet_suitetype);
			threadCount=Integer.parseInt(ExcelUtils.getCellData(row, GlobalConstants.wrksheet_suitetype_col_paralellThreads, GlobalConstants.wrksheet_suitetype));			
			
		}
	
	}
	
	public static void selectMobileSettings() throws Throwable{
			
			int rowcount = ExcelUtils.getRowCount(GlobalConstants.sheet_name_mobileAutomation);
			boolean flag=false;
			String select;
			
			for(int row = 1; row<rowcount; row++){
			
				select= ExcelUtils.getCellData(row, GlobalConstants.mobile_col_selectMode, GlobalConstants.sheet_name_mobileAutomation);			
				if (select.equalsIgnoreCase("Yes")){
					GlobalConstants.mobileAutomationMode=ExcelUtils.getCellData(row, GlobalConstants.mobile_col_automationMode, GlobalConstants.sheet_name_mobileAutomation);
					GlobalConstants.mobileExecutionMode=ExcelUtils.getCellData(row, GlobalConstants.mobile_col_executionMode, GlobalConstants.sheet_name_mobileAutomation);
					GlobalConstants.mobile_appium_server_type=ExcelUtils.getCellData(row, GlobalConstants.mobile_col_appium_server_type, GlobalConstants.sheet_name_mobileAutomation);
					GlobalConstants.mobileAppAPKName=ExcelUtils.getCellData(row, GlobalConstants.mobile_col_appFileName, GlobalConstants.sheet_name_mobileAutomation);
					GlobalConstants.mobileDeviceName=ExcelUtils.getCellData(row, GlobalConstants.mobile_col_deviceName, GlobalConstants.sheet_name_mobileAutomation);
					GlobalConstants.mobilePlatformVersion=ExcelUtils.getCellData(row, GlobalConstants.mobile_col_platFormVersion, GlobalConstants.sheet_name_mobileAutomation);
					GlobalConstants.mobilePlatformName=ExcelUtils.getCellData(row, GlobalConstants.mobile_col_platFormName, GlobalConstants.sheet_name_mobileAutomation);
					GlobalConstants.mobileBrowser=ExcelUtils.getCellData(row, GlobalConstants.mobile_col_mobileBrowser, GlobalConstants.sheet_name_mobileAutomation);
					GlobalConstants.mobileAppPackage=ExcelUtils.getCellData(row, GlobalConstants.mobile_col_appPackage, GlobalConstants.sheet_name_mobileAutomation);
					GlobalConstants.mobileAppActivity=ExcelUtils.getCellData(row, GlobalConstants.mobile_col_appActivity, GlobalConstants.sheet_name_mobileAutomation);
					GlobalConstants.mobileAppID=ExcelUtils.getCellData(row, GlobalConstants.mobile_col_appID, GlobalConstants.sheet_name_mobileAutomation);
					GlobalConstants.URL=ExcelUtils.getCellData(row, GlobalConstants.mobile_col_URL, GlobalConstants.sheet_name_mobileAutomation);
					GlobalConstants.browser=GlobalConstants.mobileBrowser;
					if(GlobalConstants.mobileBrowser.equalsIgnoreCase("NA"))
						GlobalConstants.mobileBrowser="";
					if(GlobalConstants.mobileAppID.equalsIgnoreCase("NA"))
						GlobalConstants.mobileAppID="";
					flag=true;
					threadCount=1;
			
			}
		}
			if (!flag){
				int row=1;
				GlobalConstants.mobileAutomationMode=ExcelUtils.getCellData(row, GlobalConstants.mobile_col_automationMode, GlobalConstants.sheet_name_mobileAutomation);
				GlobalConstants.mobileExecutionMode=ExcelUtils.getCellData(row, GlobalConstants.mobile_col_executionMode, GlobalConstants.sheet_name_mobileAutomation);
				GlobalConstants.mobile_appium_server_type=ExcelUtils.getCellData(row, GlobalConstants.mobile_col_appium_server_type, GlobalConstants.sheet_name_mobileAutomation);
				GlobalConstants.mobileAppAPKName=ExcelUtils.getCellData(row, GlobalConstants.mobile_col_appFileName, GlobalConstants.sheet_name_mobileAutomation);
				GlobalConstants.mobileDeviceName=ExcelUtils.getCellData(row, GlobalConstants.mobile_col_deviceName, GlobalConstants.sheet_name_mobileAutomation);
				GlobalConstants.mobilePlatformVersion=ExcelUtils.getCellData(row, GlobalConstants.mobile_col_platFormVersion, GlobalConstants.sheet_name_mobileAutomation);
				GlobalConstants.mobilePlatformName=ExcelUtils.getCellData(row, GlobalConstants.mobile_col_platFormName, GlobalConstants.sheet_name_mobileAutomation);
				GlobalConstants.mobileBrowser=ExcelUtils.getCellData(row, GlobalConstants.mobile_col_mobileBrowser, GlobalConstants.sheet_name_mobileAutomation);
				GlobalConstants.mobileAppPackage=ExcelUtils.getCellData(row, GlobalConstants.mobile_col_appPackage, GlobalConstants.sheet_name_mobileAutomation);
				GlobalConstants.mobileAppActivity=ExcelUtils.getCellData(row, GlobalConstants.mobile_col_appActivity, GlobalConstants.sheet_name_mobileAutomation);
				GlobalConstants.mobileAppID=ExcelUtils.getCellData(row, GlobalConstants.mobile_col_appID, GlobalConstants.sheet_name_mobileAutomation);
				GlobalConstants.URL=ExcelUtils.getCellData(row, GlobalConstants.mobile_col_URL, GlobalConstants.sheet_name_mobileAutomation);
				GlobalConstants.browser=GlobalConstants.mobileBrowser;
				if(GlobalConstants.mobileBrowser.equalsIgnoreCase("NA"))
					GlobalConstants.mobileBrowser="";
				if(GlobalConstants.mobileAppID.equalsIgnoreCase("NA"))
					GlobalConstants.mobileAppID="";				
				threadCount=1;
				
			}
	}
	public static List<String> getTestClasses(){
		List<String> testFiles=new ArrayList<String>();
		try{
		File folder = new File(GlobalConstants.projectDirectoryPath+"/src/main/java/testFiles");
		File[] listOfFiles = folder.listFiles();
		

		    for (int i = 0; i < listOfFiles.length; i++) {
		      if (listOfFiles[i].isFile()) {
		        System.out.println("File " + listOfFiles[i].getName());
		        
		        testFiles.add(listOfFiles[i].getName().split("\\.")[0]);
		      } 
		    }
		System.out.println("Total Classes: "+testFiles.size());
		
		}catch (Throwable e){
			e.printStackTrace();
		}
		return testFiles;
	}
	
}
