package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.SpreadsheetVersion;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataConsolidateFunction;
import org.apache.poi.ss.util.AreaReference;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFPivotTable;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.STAxis;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.STItemType;

public class Ackocomparepivot {
	
	
	XSSFSheet sheet;
	static XSSFSheet modelsheet;
	static XSSFRow row = null;
	static XSSFWorkbook workbook;
	static int rowCount;
	static String datacompare;
	
	
	public static void create_pivot(String filepath) throws IOException{
		FileInputStream input_document = new FileInputStream(new File(filepath));    
        /* Create a POI XSSFWorkbook Object from the input file */
        XSSFWorkbook my_xlsx_workbook = new XSSFWorkbook(input_document); 
        /* Read Data to be Pivoted - we have only one worksheet */
        XSSFSheet sheet = my_xlsx_workbook.getSheetAt(0); 
        /* Get the reference for Pivot Data */
        
        int firstRow = sheet.getFirstRowNum();
        int lastRow = sheet.getLastRowNum();
        int firstCol = sheet.getRow(0).getFirstCellNum();
        int lastCol = sheet.getRow(0).getLastCellNum();
        
        CellReference topLeft = new CellReference(firstRow, firstCol);
        CellReference botRight = new CellReference(lastRow, lastCol - 1);

       // AreaReference a=new AreaReference("topLeft:botRight",SpreadsheetVersion.EXCEL2007);
        /* Find out where the Pivot Table needs to be placed */
      //  CellReference b=new CellReference("A1");
        /* Create Pivot Table */
        XSSFSheet pivot_sheet=my_xlsx_workbook.createSheet("pivot");
       // XSSFPivotTable pivotTable = pivot_sheet.createPivotTable(a,b,sheet);
        AreaReference a = new AreaReference("A1:AH" + (sheet.getLastRowNum() + 1), SpreadsheetVersion.EXCEL2007);
        CellReference b = new CellReference("A1");

        XSSFPivotTable pivotTable = pivot_sheet.createPivotTable(a, b, sheet);

        /* Add filters */
        pivotTable.addRowLabel(10);
        pivotTable.addRowLabel(11);  
        pivotTable.getCTPivotTableDefinition().getPivotFields().getPivotFieldArray(33).setAxis(STAxis.AXIS_COL);
        pivotTable.getCTPivotTableDefinition().getPivotFields().getPivotFieldArray(33).addNewItems();
        pivotTable.getCTPivotTableDefinition().getPivotFields().getPivotFieldArray(33).getItems().addNewItem()
                .setT(STItemType.DEFAULT);

        pivotTable.getCTPivotTableDefinition().addNewColFields().addNewField().setX(33);
     
        pivotTable.addColumnLabel(DataConsolidateFunction.COUNT,0,"Count of LeadID");
                
        
        /* Write Pivot Table to File */
        FileOutputStream output_file = new FileOutputStream(new File(filepath)); 
        my_xlsx_workbook.write(output_file);
        input_document.close(); 
	}
	
	
	public static void datacomparison(String filepath) throws IOException{
		try{
		FileInputStream fis = new FileInputStream(filepath);
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
		    	  SetInputData(filepath,i,datacompare);
		    	  
		      }else  if(pbdatafinal>=ackodatafinal){
		    	  
		    	  datacompare = "Acko";
		    	  SetInputData(filepath,i,datacompare);
		      } else {
		    	  
		    	  datacompare = "PB";
		    	  SetInputData(filepath,i,datacompare);
		      }
		      
		      
		      
			}catch(Exception e){
				e.printStackTrace();
			}
		  }
		}catch(Exception e){
			e.printStackTrace();
		}
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
		
		cell = row0.getCell(33);
		
			cell = row0.createCell(33);
		
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
