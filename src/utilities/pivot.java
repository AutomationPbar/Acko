package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Optional;

import org.apache.poi.ss.SpreadsheetVersion;
import org.apache.poi.ss.usermodel.DataConsolidateFunction;
import org.apache.poi.ss.util.AreaReference;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFPivotTable;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.docx4j.model.fields.merge.DataFieldName;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTDataFields;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.STAxis;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.STItemType;
import org.xlsx4j.sml.CTDataField;
import org.xlsx4j.sml.STShowDataAs;

public class pivot {
	
	 public static void main(String[] args) throws Exception{
		 
		 //System.out.println("comparing the data");
			//utilities.Ackocomparepivot.datacomparison("C:\\Excelfiles\\Acko\\17Jan2019\\test.xlsx");

         /* Read the input file that contains the data to pivot */
         FileInputStream input_document = new FileInputStream(new File("C:\\Excelfiles\\Acko\\17Jan2019\\test.xlsx"));    
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
        
         
         
        /* CTDataFields dataFields;
         dataFields = pivotTable.getCTPivotTableDefinition().getDataFields();  
         System.out.println(dataFields.getDataFieldList().get(0));*/
         
             
        //STShowDataAs abc = STShowDataAs.PERCENT_OF_ROW;
       // System.out.println(abc);
        //setFormatDataField(pivotTable,0,9,abc);
               
        /*  for (STShowDataAs c : STShowDataAs.values()){
        	    System.out.println(c);
         }
          Write Pivot Table to File */
         FileOutputStream output_file = new FileOutputStream(new File("C:\\Excelfiles\\Acko\\17Jan2019\\newtest.xlsx")); 
         my_xlsx_workbook.write(output_file);
         input_document.close(); 
 }

	 private static void setFormatDataField(XSSFPivotTable pivotTable,
             long fieldIndex,
             long numFmtId,STShowDataAs a) {
Optional.ofNullable(pivotTable
.getCTPivotTableDefinition()
.getDataFields())
.map(CTDataFields::getDataFieldList)
.map(List::stream)
.ifPresent(stream -> stream
.filter(dataField -> dataField.getFld() == fieldIndex)
.findFirst()
.ifPresent(dataField -> dataField.setNumFmtId(numFmtId)));
 System.out.println(pivotTable.getCTPivotTableDefinition().getDataFields().getDataFieldList());
 System.out.println(pivotTable.getCTPivotTableDefinition().getDataFields().getDataFieldArray(0));
 
 
}    
	
}

