package supportFiles;


import java.util.HashMap;
import java.util.Map;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentManager {
    private static ExtentReports extent=null;
    private static ExtentHtmlReporter htmlReporter;
	public static Map<Long, String> threadToExtentTestMap = new HashMap<Long, String>();
	public static Map<String, ExtentTest> nameToTestMap = new HashMap<String, ExtentTest>();
    
    public synchronized static ExtentReports getReporter(String filePath) {
        if (extent == null) {
        	
        	       	
        	htmlReporter= new ExtentHtmlReporter(filePath);
        	
        	
        	 htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
             htmlReporter.config().setChartVisibilityOnOpen(true);
             htmlReporter.config().setTheme(Theme.DARK);
             htmlReporter.config().setDocumentTitle(GlobalConstants.projectName);
             htmlReporter.config().setEncoding("utf-8");
             htmlReporter.config().setReportName(GlobalConstants.projectName);
        	
        	
            extent = new ExtentReports();
            extent.attachReporter(htmlReporter);
            //htmlReporter.loadXMLConfig(new File(GlobalConstants.extentConfigFilePath));
            //System.out.println("***Extent Report Created: " +filePath );
        }
        
        return extent;
    }
    
    public synchronized static ExtentReports getReporter() {
        return extent;
    }
}