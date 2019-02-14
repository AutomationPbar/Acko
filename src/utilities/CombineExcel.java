package utilities;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class CombineExcel {
 // Excel file path
 public static final String firstfile = "C:\\Excelfiles\\Acko\\03Jan2019\\file1.xlsx";
 public static final String secondfile = "C:\\Excelfiles\\Acko\\03Jan2019\\file2.xlsx";

public static void main(String[] args) throws IOException, InvalidFormatException {
 FileInputStream file1 = new FileInputStream(firstfile);
 XSSFWorkbook workbook1 = new XSSFWorkbook(file1);
 FileInputStream file2 = new FileInputStream(secondfile);
 XSSFWorkbook workbook2 = new XSSFWorkbook(file2);
 // Getting sheet1
 Sheet sheet1 = workbook1.getSheetAt(0);
 Sheet sheet2 = workbook2.getSheetAt(0);
 // Getting row at index 0 in sheet1
 Row row1 = sheet1.getRow(0);
 Row row2 = sheet2.getRow(0);
 
int rowCount = sheet1.getLastRowNum() + 1;
	System.out.println("Row Count sheet1  :- " + rowCount);
	
	int rowCount2 = sheet2.getLastRowNum() + 1;
	System.out.println("Row Count sheet 2:- " + rowCount2);

 int rowLength = row1.getPhysicalNumberOfCells();
 int rowLength2 = row2.getPhysicalNumberOfCells();
 // Creating sheet2
 
 // Setting value in row of sheet2 from sheet1
 for(int j = 1;j<rowCount2;j++){
	 
	 row2 = sheet2.getRow(j);
	 Cell trial = row2.getCell(0);
	 System.out.println(trial.getStringCellValue());
	 Row rownew = sheet1.createRow(rowCount+j-1);
 for (int i = 0; i < rowLength2; i++) {
 Cell cell = rownew.createCell(i);
 Cell firstSheetCell = row2.getCell(i);
 cell.setCellValue(firstSheetCell.getStringCellValue());
 }
 }
 // Writing changes in Excel file
 file1.close();
 FileOutputStream outFile = new FileOutputStream(new File(firstfile));
 workbook1.write(outFile);
 outFile.close();
 }
 }