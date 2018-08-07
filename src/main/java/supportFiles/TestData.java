package supportFiles;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class TestData {
	private static XSSFRow row;	
	private static ArrayList<TestData> arrayList;	
	public static XSSFWorkbook workbook;
	public static XSSFSheet sheet;
	public static int testcaseIndex=0;
	

	public static String getData(String FilePath, String SheetName,String TestCase,String Field) throws Exception {
		
		String retrieved_value = null;
		arrayList = new ArrayList<TestData>();
		Map<String, Integer> map = new HashMap<String,Integer>(); 
		sheet = ExcelUtils.getsheet(FilePath, SheetName);
		XSSFRow row = sheet.getRow(0); 
		short minColIx = row.getFirstCellNum(); 
		short maxColIx = row.getLastCellNum(); 
		for(short colIx=minColIx; colIx<maxColIx; colIx++) { 
			XSSFCell cell = row.getCell(colIx); 
			
			map.put(cell.getStringCellValue(),cell.getColumnIndex()); 
		
		}
		
		Iterator<Row> rowIterator = sheet.iterator();
		rowIterator.next();
		while (rowIterator.hasNext()){
			row = (XSSFRow) rowIterator.next();
			
			int idxForTestCase = map.get("TestCaseID"); 
			
			
		
			
				
				XSSFCell requestedField_cell = row.getCell(idxForTestCase);
				String Cell_Content = requestedField_cell.getStringCellValue().trim();
				
				if (Cell_Content.equals(TestCase)){
					
				
				int idxForrequestedField = map.get(Field);
				XSSFCell requestedField_cell1 = row.getCell(idxForrequestedField);
				
				 retrieved_value =  requestedField_cell1.toString().trim();
				
				break;
				
			}else{
				
				//System.out.println("Did not yet locate the Test Case ID from the Data sheet");
			}
		}	
		
			return retrieved_value;		
	}	
	
	
	
	public static String getData(String module,String TestCase,String Field) throws Exception {
		String FilePath= GlobalConstants.dataSheetsPath+module+".xlsx";
		String sheetName=module;
		String retrieved_value = null;
		arrayList = new ArrayList<TestData>();
		Map<String, Integer> map = new HashMap<String,Integer>(); 
		sheet = ExcelUtils.getsheet(FilePath, sheetName);
		XSSFRow row = sheet.getRow(0); 
		short minColIx = row.getFirstCellNum(); 
		short maxColIx = row.getLastCellNum(); 
		for(short colIx=minColIx; colIx<maxColIx; colIx++) { 
			XSSFCell cell = row.getCell(colIx); 
			
			map.put(cell.getStringCellValue(),cell.getColumnIndex()); 
			}		
		

		
		Iterator<Row> rowIterator = sheet.iterator();
		rowIterator.next();
		while (rowIterator.hasNext()){
			row = (XSSFRow) rowIterator.next();
			
			int idxForTestCase = map.get("TestCaseID"); 
			
			
			
				
				XSSFCell requestedField_cell = row.getCell(idxForTestCase);
				String Cell_Content = requestedField_cell.getStringCellValue().trim();
				
				if (Cell_Content.equals(TestCase)){
					
				
				int idxForrequestedField = map.get(Field);
				XSSFCell requestedField_cell1 = row.getCell(idxForrequestedField);
				
				 retrieved_value =  requestedField_cell1.toString().trim();
				
				break;
				
			}else{
				
				//System.out.println("Did not yet locate the Test Case ID from in the Data sheet");
			}
				
		}
			return retrieved_value;			
	}	
	
	
	public static String getData(String module,String Field)  {
		String retrieved_value = null;
		TestConfigurations testConfig= GlobalVariables.getConfigMap().get(Thread.currentThread().getName());
		try{
		
		
		String FilePath= GlobalConstants.dataSheetsPath+module+".xlsx";
		String SheetName=module;		//workbook.getSheetName(0);
		String threadName=Thread.currentThread().getName();			
		int index=threadName.indexOf('_');
		String TestCase=threadName.substring(index+1);
		
		//
		arrayList = new ArrayList<TestData>();
		Map<String, Integer> map = new HashMap<String,Integer>(); 
		sheet = ExcelUtils.getsheet(FilePath, SheetName);
		XSSFRow row = sheet.getRow(0); 
		short minColIx = row.getFirstCellNum(); 
		short maxColIx = row.getLastCellNum(); 
		for(short colIx=minColIx; colIx<maxColIx; colIx++) { 
			XSSFCell cell = row.getCell(colIx); 
		
			map.put(cell.getStringCellValue(),cell.getColumnIndex()); 
			}		
		
		
		
		Iterator<Row> rowIterator = sheet.iterator();
		rowIterator.next();
		while (rowIterator.hasNext()){
			row = (XSSFRow) rowIterator.next();
			
			int idxForTestCase = map.get("TestCaseID"); 
			
			int number_of_rows = sheet.getLastRowNum()+1;
			
			
			
				
				XSSFCell requestedField_cell = row.getCell(idxForTestCase);
				String Cell_Content = requestedField_cell.getStringCellValue().trim();
				
				if (Cell_Content.equals(TestCase)){
					
				
				int idxForrequestedField = map.get(Field);
				XSSFCell requestedField_cell1 = row.getCell(idxForrequestedField);
				
				 retrieved_value =  requestedField_cell1.toString().trim();
				
				break;
				
			}else{
				
				//System.out.println("Did not yet locate the Test Case ID in the Data sheet");
			}
				
		}
			
		testConfig.getLogger().info("Data ["+retrieved_value +"] Fetched from Excel ["+module+".xlsx]");
		}catch(Throwable e){
			//testConfig.getLogger().error("Error In Get Data From Excel ["+module+".xlsx]",e);
			LOG.report_ERROR("Error in Getting Test Data For Module:["+ module+ "]\n***" + e.toString());
		}
		return retrieved_value;	
	}
	
	public static Map<String, String> getData(String moduleName){
		//TestConfigurations testConfig= GlobalVariables.getConfigMap().get(Thread.currentThread().getName());
			Map<String, String> dataMap= new HashMap<String, String>();
			
			//String retrieved_value = null;
			int indexCol=0;
		try{
			
			
			String FilePath= GlobalConstants.dataSheetsPath+moduleName+".xlsx";
			//String SheetName=moduleName;
			String threadName=Thread.currentThread().getName();			
			int index=threadName.indexOf('_');
			String TestCase=threadName.substring(index+1);
			
			
			
		
		
			sheet = ExcelUtils.getsheetAt(FilePath, 0);
			
			
			
			
			Map<Integer,String> mapOne= getRowMap(sheet, 0);
			
		
			Iterator<Row> rowIterator = sheet.iterator();
			rowIterator.next();
			boolean flag=false;
			while (rowIterator.hasNext()){
					row = (XSSFRow) rowIterator.next();	
					XSSFCell requestedField_cell = row.getCell(testcaseIndex);
					String Cell_Content = requestedField_cell.getStringCellValue().trim();
					
					if (Cell_Content.equals(TestCase)){
						
						
						
						flag=true;
					
				}
					
					indexCol++;
					if(flag)
						break;
			}
			
			if(flag){			
				Map<Integer,String> mapTwo= getRowMap(sheet, indexCol);
			
		
			
			
				for (int i=0; i<mapOne.size(); i++) {
			    
					dataMap.put(mapOne.get(i), mapTwo.get(i));
				}
			}else
				//throw new Exception("[***Exception in TestData] Test Case entry ["+TestCase+"] not found in data spreadhseet ["+moduleName+".xlsx]. Make correct test case entry in said excel or You can ignore this exception if you don't require data from datasheet for this test");
				System.out.println("[***Exception in TestData] Test Case entry ["+TestCase+"] not found in data spreadhseet ["+moduleName+".xlsx]. Make correct test case entry in said excel or You can ignore this exception if you don't require data from datasheet for this test");
			//printMap(dataMap);		
			
			//testConfig.getLogger().info("Data Fetched from Excel ["+moduleName+"].xlsx");
			
			
		}catch(FileNotFoundException e){
			
			
			//testConfig.getLogger().info("Datasheet not available for Module ["+ moduleName+"]. Create a Data File in dataSheets folder or You can ignore this exception if you don't require datasheet for this module");
			System.out.println("[***Exception in TestData] Datasheet not available for Module ["+ moduleName+"]. Create a Data File in dataSheets folder or You can ignore this exception if you don't require datasheet for this module");
			
			
		}catch(Throwable e){
			
			//System.out.println(e.getMessage());
			
			//LOG.report_ERROR(e);
			//testConfig.getLogger().error("Error in Getting Test Data For Module:["+ moduleName+ "]",e);
		}
		
		
		
		
		return dataMap;
	}
	
	public static Map<Integer,String> getRowMap(XSSFSheet sheet, int index){
		
		Map<Integer,String> map=new HashMap<Integer,String>();
		XSSFRow row = sheet.getRow(index); 
		short minColIx = row.getFirstCellNum(); 
		short maxColIx = row.getLastCellNum(); 
		for(short colIx=minColIx; colIx<maxColIx; colIx++) { 
			XSSFCell cell = row.getCell(colIx);		
			map.put(cell.getColumnIndex(),cell.getStringCellValue()); 
			
			
		}
		
			
		return map;		
	}
	
	public static void printMap(Map<String,String> map){
		
		for (Map.Entry<String,String> entry : map.entrySet()) {
		    String key = entry.getKey().toString();
		    String value = entry.getValue();
		    System.out.println("***key, " + key + " value " + value);
		}

		
		
	}


}
