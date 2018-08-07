package supportFiles;

import java.util.HashMap;
import java.util.Map;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;


public class ExtentTestManager {  // new
    static Map<Integer, ExtentTest> extentTestMap = new HashMap<Integer, ExtentTest>();
    private static ExtentReports extent = ExtentManager.getReporter();
    
    
    public static ExtentReports getExtentReports(){
    	
    	
    	//System.out.println("###ProjectName:" + extent.getProjectName());
    	return extent;
    }

    public static synchronized ExtentTest getTest() {
        return (ExtentTest) extentTestMap.get((int) (long) (Thread.currentThread().getId()));
    }

    public static synchronized void endTest() {
        //extent.endTest((ExtentTest) extentTestMap.get((int) (long) (Thread.currentThread().getId())));
    }

    public static synchronized ExtentTest startModule(String testName) {
        return startModule(testName, "");
    }

    public static synchronized ExtentTest startModule(String testName, String desc) {
        ExtentTest test = extent.createTest(testName, desc);       

        return test;
    }
    
    public static synchronized ExtentTest startNode(ExtentTest parentTest, String testName, String desc){
    	ExtentTest test=parentTest.createNode(testName,desc);
    	 extentTestMap.put((int) (long) (Thread.currentThread().getId()), test);
    	 return test;
    }
}