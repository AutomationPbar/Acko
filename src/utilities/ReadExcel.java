package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.WorkbookUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
 
//How to read excel files using Apache POI
public class ReadExcel {
	
	static String excelpath = "C:\\Excelfiles\\Acko\\Data\\testfinal.xlsx";
	static String sheetname = "Base Template";
	
	XSSFSheet sheet;
	static XSSFSheet modelsheet;
	static XSSFRow row = null;
	static XSSFWorkbook workbook;
	static int rowCount;
	static String datacompare;
	
 public static void main (String [] args) throws IOException{
                       
	/* FileInputStream fis = new FileInputStream(excelpath);
		workbook = new XSSFWorkbook(fis);
		modelsheet = workbook.getSheetAt(0);
		
		row = modelsheet.getRow(0);
		
		rowCount = modelsheet.getLastRowNum() + 1;
		System.out.println("Row Count :- " + rowCount);

		for (int i =1; i <rowCount; i++) { // For each Row.
			try{
			row = modelsheet.getRow(i);
		      Cell cell = row.getCell(22);
		      
		      String pbdata = cell.getStringCellValue();
		      float pbdatafinal = Float.parseFloat(pbdata);
		      
		      System.out.println("Cell PB value  " + cell.getStringCellValue());
		      System.out.println("Cell int PB value  " + pbdatafinal);
		      // Get the Cell at the Index / Column you want.
		      
		      Cell cella = row.getCell(29);
		      String ackodata = cella.getStringCellValue();
		      float ackodatafinal = 0;
		      if(ackodata.contains("No Data")){
		    	  datacompare = "Not available";
		      }else{
		      ackodatafinal = Float.parseFloat(ackodata);
		      }
		      
		      System.out.println("Cell Acko value  " + cella.getStringCellValue());
		      System.out.println("Cell int Acko value  " + ackodatafinal);
		      
		      if(ackodatafinal == 0.0){
		    	  
		    	  datacompare = "Not Available";
		    	  SetInputData(excelpath,i,datacompare);
		    	  
		      }else  if(pbdatafinal>=ackodatafinal){
		    	  
		    	  datacompare = "Acko";
		    	  SetInputData(excelpath,i,datacompare);
		      } else {
		    	  
		    	  datacompare = "PB";
		    	  SetInputData(excelpath,i,datacompare);
		      }
		      
		      
		      
			}catch(Exception e){
				e.printStackTrace();
			}
			
			
			
		  }*/

		int currentmonth = Calendar.getInstance().get(Calendar.MONTH)+1;
		System.out.println("current month is " +currentmonth);
		
	/* System.out.println("comparing the data");
		utilities.Ackocomparepivot.datacomparison(excelpath);
		
 
		try {
			Thread.sleep(10000);
			System.out.println("creating pivot table ");
			utilities.Ackocomparepivot.create_pivot(excelpath);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
 
 
	}
 public static void SetInputData(String filePath, int row, String data) throws Exception {

		FileInputStream fis = new FileInputStream(filePath);
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		XSSFSheet inputSheet = workbook.getSheetAt(0);

		// Retrieve the row and check for null
		XSSFRow row0 = (XSSFRow) inputSheet.getRow(row);
		Cell cell = null;
		if (row0 == null) {
			row0 = (XSSFRow) inputSheet.createRow(row);
		}
		// Update the value of cell
		
		cell = row0.getCell(34);
		
			cell = row0.createCell(34);
		
		cell.setCellValue(data);
		
		try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
			workbook.write(fileOut);
			fileOut.close();
		} catch (Exception e) {
			System.out.println(e);
		}

		workbook.close();
	}

}

