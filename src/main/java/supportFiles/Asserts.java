package supportFiles;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;



public class Asserts {

	public static  void verifyText(By locator,String input)
	{
		
	
		try
		{
			
			String text=GenericMobileKeywords.getElement(locator).getText();
			
			if (text.equals(input)){
				
				LOG.report_PASS( "Element Text \""+input +"\" has matched");
			}
			else{
				
				LOG.report_FAIL("Element Text Should have been \""+input +"\" but instead text \""+text+"\" has found");
			}
			
			
			
		}catch(Exception e)
		{
			e.printStackTrace();
			LOG.report_ERROR(e);
			

		}
		
	}
	
	public static void verifyElementExists(By locator){
		 
		 try{
			 
			 
			 	 
			 if (GenericMobileKeywords.getElement(locator).isDisplayed())
				 LOG.report_PASS("Element is found");
		     else
		    	 LOG.report_FAIL("Element is Not found, Please check locator: " + locator);
		        
			 
		 }catch(Exception e){
			 
			 LOG.report_ERROR( e);
		 
		}
	    }
	
	  public static void verifyElementCount(By locator, int expectedCount){
		  
		  
		  try{
			  
			  
	        List<WebElement> elements = GenericMobileKeywords.getElementList(locator);
	        		
	        if (elements.size()==expectedCount)
	        	LOG.report_PASS("The Element Count has Matched Correctly");
	        else
	        	LOG.report_FAIL( "The Elements Count is Not Matched, Expected Count was "+expectedCount+" but instead Count "+elements.size()+" was Found");
	        
	        
		  }catch(Exception e){
			  
			  LOG.report_ERROR(e);
			  
		  }
	    }
	  
	
	
	
}
