/***
	//====================================================================================================
	// Framework Name  	: selenium_automation_framework
	// Description     	: This is generic Selenium Automation Framework
	// Components		: Java, Maven, TestNG, Extent Reports		
	// Created By	 	: Suhail Dandu  (Employee ID: 12701)
	// Project	    	: DT-CRM
	// Date Created		: December 2016
	//
	// Note				: For Queries/Technical issues kindly drop mail to: suhaild@cybage.com
	//====================================================================================================

***/

package main;

import java.io.File;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.PropertyConfigurator;

import supportFiles.GenericKeywords;
import supportFiles.ExtentManager;
import supportFiles.GlobalConstants;
import supportFiles.GlobalVariables;
import supportFiles.GenerateTestNGFile;



public class Initiator {	
	
	public static void main(String[] args) {
		
		try{	
			BasicConfigurator.configure();				
			PropertyConfigurator.configure("log4j.properties");
			//GlobalConstants.currentReportFolderName="Report_"+GlobalVariables.getTimestamp();
			GlobalConstants.currentReportFolderName="Report";
			GlobalConstants.currentReportFolderPath= GlobalConstants.exec_report_path+GlobalConstants.currentReportFolderName;		
			new File(GlobalConstants.currentReportFolderPath).mkdir();		
			GlobalConstants.executionReportName=GlobalConstants.currentReportFolderPath + "/REPORT.html";
			GlobalConstants.extent=ExtentManager.getReporter(GlobalConstants.executionReportName);
			
			String mavenExecutionTag = System.getProperty("executionTag");
			String mavenExecutionMode = System.getProperty("executionMode");
			String mavenBrowser = System.getProperty("browser");
			String mavenParallelThreads=System.getProperty("paralellThreads");
			if (mavenParallelThreads==null) mavenParallelThreads="1";
			if(mavenExecutionTag!=null){
				GenerateTestNGFile.createTestNGFileGroupWise(mavenExecutionTag,mavenExecutionMode,mavenBrowser,mavenParallelThreads);
				
			}
			else{
				GenerateTestNGFile.selectSuiteType();
				
			}
			GenerateTestNGFile.createTestNGFile(GlobalConstants.testng_xml_path);
			System.out.println("***PROJECT NAME: "+GlobalConstants.projectName);	
			if(GlobalConstants.automationType.equalsIgnoreCase("Mobile Automation")){
				System.out.println("\n***Automation Mode: "+GlobalConstants.mobileAutomationMode);
				System.out.println("***Mobile App: "+GlobalConstants.mobileAppAPKName);
				System.out.println("***Mobile Device: "+GlobalConstants.mobileDeviceName);
				System.out.println("***Mobile Platform Version: "+GlobalConstants.mobilePlatformVersion);
				System.out.println("***Mobile Platform Name: "+GlobalConstants.mobilePlatformName);
				System.out.println("***Mobile Browser: "+GlobalConstants.mobileBrowser);
				System.out.println("***Mobile App Packg: "+GlobalConstants.mobileAppPackage);
				System.out.println("***Mobile App Activity: "+GlobalConstants.mobileAppActivity);
				System.out.println("***Mobile App ID: " + GlobalConstants.mobileAppID);
				System.out.println("***Mobile URL: " + GlobalConstants.URL);
			}else{
			
				System.out.println("***APPLICATION URL: " + GlobalConstants.URL);
				System.out.println("***BROWSER: " + GlobalConstants.browser);
			}
		//Execute TestNG File
			GenerateTestNGFile.runTestNG(GlobalConstants.testng_xml_filename);		
		}catch(Throwable e){			
			
			System.out.println("###***Exception in Initiator***###: " + e.toString());
			e.printStackTrace();		
			
		}
	}	
		
}