package supportFiles;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;



public class ExcelUtils {
	
	private static XSSFSheet ExcelWSheet;
    private static XSSFWorkbook ExcelWBook;
    private static org.apache.poi.ss.usermodel.Cell Cell;
    private static XSSFRow Row;
    public Row row;    
    public String tcrunmode;
    private static FileInputStream ExcelFile;
    public static String[][] tabArray = null;
    
    

	public static void setExcelFile(String Path) throws Exception {
			
		try{
			ExcelFile = new FileInputStream(Path);
	        ExcelWBook = new XSSFWorkbook(ExcelFile);
		}
		catch (Exception e){
				e.printStackTrace();
			}

	       }
	
	public static XSSFSheet getsheet(String FilePath, String sheetname) throws Exception{
			ExcelFile = new FileInputStream(FilePath);
	        ExcelWBook = new XSSFWorkbook(ExcelFile);
	        ExcelWSheet = ExcelWBook.getSheet(sheetname);
		
		return ExcelWSheet;		
	}
	public static XSSFSheet getsheetAt(String FilePath, int index) throws Exception{
		ExcelFile = new FileInputStream(FilePath);
        ExcelWBook = new XSSFWorkbook(ExcelFile);
        ExcelWSheet = ExcelWBook.getSheetAt(index);
	
        return ExcelWSheet;		
	}
	


	public static String getCellData(int RowNum, int ColNum, String SheetName) throws Exception{		  
	    try{
		    ExcelWSheet = ExcelWBook.getSheet(SheetName);	  
			Cell = ExcelWSheet.getRow(RowNum).getCell(ColNum);			
			String CellData = Cell.getStringCellValue();
			return CellData;
		  }
		  catch(Exception e){
			  
			  //e.printStackTrace();
			  return"";
		  }
		}
	

	public static int getRowCount(String SheetName){
		  int iNumber=0;
		  try{
			  ExcelWSheet = ExcelWBook.getSheet(SheetName);
			  iNumber=ExcelWSheet.getLastRowNum()+1;
		  }
		  catch (Exception e){
						  
			  	System.out.println("Exception in ExcelUtils: getRowCount, SheetName: " + SheetName);
			  	e.printStackTrace();
				}
		  		
			return iNumber;
		}
	
	public static int getColumnCount(String SheetName, int row){
		int iNumber=0;
		try{
			ExcelWSheet = ExcelWBook.getSheet(SheetName);
			iNumber=ExcelWSheet.getRow(row).getLastCellNum();
		}
		catch (Exception e){
			e.printStackTrace();
			}
	  		
		return iNumber;
	}
	
	public static int agetRowCount(String SheetName){
		  int iNumber=0;
		  try{
			  ExcelWSheet = ExcelWBook.getSheet(SheetName);
			  iNumber=ExcelWSheet.getLastRowNum()+1;
		  }
		  catch (Exception e){
			e.printStackTrace();
		  }
		  		
			return iNumber;
		}
	
		
	
	//This method is use to write value in the excel sheet
	//This method accepts four arguments (Result, Row Number, Column Number & Sheet Name)
	public static void setCellData(String Result,  int RowNum, int ColNum, String SheetName) throws Exception    {
		   try{
	
			   ExcelWSheet = ExcelWBook.getSheet(SheetName);
			   Row  = ExcelWSheet.getRow(RowNum);
			   Cell = Row.getCell(ColNum, org.apache.poi.ss.usermodel.Row.RETURN_BLANK_AS_NULL);
			   if (Cell == null) {
				   Cell = Row.createCell(ColNum);
				   Cell.setCellValue(Result);
			   } else {
					Cell.setCellValue(Result);
					}
				// Constant variables Test Data path and Test Data file name
				FileOutputStream fileOut = new FileOutputStream(GlobalConstants.excelpath);
				ExcelWBook.write(fileOut);
				//fileOut.flush();
				fileOut.close();
				ExcelWBook = new XSSFWorkbook(new FileInputStream(GlobalConstants.excelpath));
			 }catch(Exception e){
				 e.printStackTrace();
				}
	}
	

	public static int excelFileName(){
		int excelfile = ExcelWBook.getNumberOfSheets();
		return excelfile;
		
	}	


}
