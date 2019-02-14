package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.ss.SpreadsheetVersion;
import org.apache.poi.ss.usermodel.DataConsolidateFunction;
import org.apache.poi.ss.util.AreaReference;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFPivotTable;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.STAxis;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.STItemType;

public class Excelfunctions {
	
	 public static void main(String[] args) throws Exception{
         /* Read the input file that contains the data to pivot */
         FileInputStream input_document = new FileInputStream(new File("C:\\Excelfiles\\Acko\\New folder\\testfile.xlsx"));    
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
         AreaReference a = new AreaReference("A1:AJ" + (sheet.getLastRowNum() + 1), SpreadsheetVersion.EXCEL2007);
         CellReference b = new CellReference("A1");

         XSSFPivotTable pivotTable = pivot_sheet.createPivotTable(a, b, sheet);

         /* Add filters */
         pivotTable.addRowLabel(10);
         pivotTable.addRowLabel(11);  
         
         pivotTable.getCTPivotTableDefinition().getPivotFields().getPivotFieldArray(35).setAxis(STAxis.AXIS_COL);
         pivotTable.getCTPivotTableDefinition().getPivotFields().getPivotFieldArray(35).addNewItems();
         pivotTable.getCTPivotTableDefinition().getPivotFields().getPivotFieldArray(35).getItems().addNewItem()
                 .setT(STItemType.DEFAULT);

         pivotTable.getCTPivotTableDefinition().addNewColFields().addNewField().setX(35);
         
         pivotTable.addColumnLabel(DataConsolidateFunction.COUNT,0,"Count of LeadID");
                
         
         /* Write Pivot Table to File */
         FileOutputStream output_file = new FileOutputStream(new File("C:\\Excelfiles\\Acko\\Data\\testfinal.xlsx")); 
         my_xlsx_workbook.write(output_file);
         input_document.close(); 
 }

}

