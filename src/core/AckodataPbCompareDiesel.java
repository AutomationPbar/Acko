package core;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import pom.Ackoelements;

public class AckodataPbCompareDiesel {

	WebDriver driver;
	WebDriverWait wait;

	String baseurl = "	";
	String carurl = "https://www.acko.com/mmv/?quote=3rhi6Ja3d2y_0GcbacXNog";
	String excelpath = "C:\\Excelfiles\\Acko\\InputDataDiesel.xlsx";
	String excelpath_update = "C:\\Excelfiles\\Acko\\Data\\dataentry.xls";
	String sheetname = "Base Template";

	int rowCount;
	int excelrow = 1;
	int dexcelrow =1;
	XSSFSheet sheet;
	XSSFSheet modelsheet;
	XSSFRow row = null;
	XSSFWorkbook workbook;
	String resultdata[] = new String[14];
	
	String nodata ="No Data Found";

	@BeforeSuite

	public void setup() {

		try {
			System.setProperty("webdriver.chrome.driver", "C:\\eclipse\\chromedriver.exe");

			driver = new ChromeDriver();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

			wait = new WebDriverWait(driver, 10);
			
			FileInputStream fis = new FileInputStream(excelpath);
			workbook = new XSSFWorkbook(fis);
			modelsheet = workbook.getSheetAt(0);

			
			SimpleDateFormat formatter = new SimpleDateFormat("dd_MM_yyyy_HH_mm");
			Date datedd = new Date();
			System.out.println(formatter.format(datedd));
			String localDate11 = formatter.format(datedd).toString();
			excelpath_update = "C:\\Excelfiles\\Acko\\Data\\dataentry_cmt" + localDate11 + ".xls";
			utilities.ExcelUtils.SetExcelFile(excelpath_update, sheetname);

			row = modelsheet.getRow(0);

			int colCount = row.getLastCellNum();
			System.out.println("Column Count :- " + colCount);

			rowCount = modelsheet.getLastRowNum() + 1;
			System.out.println("Row Count :- " + rowCount);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test(priority = 1)
	public void getDiesel() throws Exception {

		// driver.get(baseurl);
		driver.manage().window().maximize();
		// wait.until(ExpectedConditions.elementToBeClickable(pom.Ackoelements.dontknow(driver))).click();

		
		// Making the object of excel row
		

		for (int i =1000; i <=1500; i++) {
			try {

				driver.get(carurl);
				row = modelsheet.getRow(i);

				String make = modelsheet.getRow(i).getCell(0).getStringCellValue();
				System.out.println("the make is " + make);
				
				CellType modelcell = modelsheet.getRow(i).getCell(1).getCellTypeEnum();
				System.out.println("Cell type of model is " + modelcell);
				
				String model ="";
				try{
				if(modelcell == CellType.STRING){

				model = modelsheet.getRow(i).getCell(1).getRichStringCellValue().toString();
				System.out.println("the model is " + model);
				}else if(modelcell == CellType.NUMERIC){
					model = modelsheet.getRow(i).getCell(1).getRawValue().toString();
					System.out.println("the model is " + model);
				}
				}catch(Exception e){
					e.printStackTrace();
				}
				String mm = make + " " + model;
				System.out.println(mm);
				
				CellType modelcell2 = modelsheet.getRow(i).getCell(2).getCellTypeEnum();
				System.out.println("Cell type of model is " + modelcell2);
				
				String Variant ="";
				
				try{
					if(modelcell2 == CellType.STRING){

					Variant = modelsheet.getRow(i).getCell(2).getRichStringCellValue().toString();
					System.out.println("the variant is " + Variant);
					}else if(modelcell2 == CellType.NUMERIC){
						Variant = modelsheet.getRow(i).getCell(2).getRawValue().toString();
						System.out.println("the variant is " + Variant);
					}
					}catch(Exception e){
						e.printStackTrace();
					}
				

				double Year = modelsheet.getRow(i).getCell(3).getNumericCellValue();
				System.out.println("the Year is " + Year);
				
				double NCB = modelsheet.getRow(i).getCell(4).getNumericCellValue();
				System.out.println("the NCB Year is " + NCB);
				
				String Fuel = modelsheet.getRow(i).getCell(5).getStringCellValue();
				System.out.println("the Fuel is " + Fuel);
				
				String Pincode = modelsheet.getRow(i).getCell(6).getRawValue().toString();
				System.out.println("the Pincode is " + Pincode);
				
				String leadid= modelsheet.getRow(i).getCell(7).getRawValue().toString();
				System.out.println("the lead id is " + leadid);

				Thread.sleep(4000);

				Ackoelements.selectmake(driver).sendKeys(model);;
				Thread.sleep(2500);
				
				//*[@id="acko_app"]/div/div[2]/div[1]/div[2]/div[2]/div[3]/div/div/ul/li
				List <WebElement> models = driver.findElements(By.xpath("//*[@id='acko_app']/div/div[2]/div[1]/div[2]/div[2]/div[3]/div/div/ul/li"));
				int msize = models.size();
				System.out.println("the size of models is " + msize);
				for(int m =0;m<msize;m++){
					Ackoelements.selectmodell(driver,m).click();
					
					System.out.println("Make model selected ");
				
				System.out.println("Make model selected ");
				
				try {
					Ackoelements.diesel(driver).click();
					System.out.println("Fuel selected");
				} catch (Exception e) {
					
				}
				
				
				List<WebElement> varianttypes = Ackoelements.variantsCount(driver);
				int vsize = varianttypes.size();
				
				System.out.println("The number of Diesel variants are " + vsize);
				int variantindex = 0;
				for (int j = 1; j <= vsize; j++){
					
					String variant1 = Ackoelements.variants(driver, j).getText();
					if(variant1.equalsIgnoreCase(Variant)){
						Ackoelements.variants(driver, j).click();
						System.out.println("Variant selected ");
						variantindex =1;
						
						break;
					}
					
				}
				
				Thread.sleep(1500);
				if (variantindex==0){
					
					/*resultdata[0] = make;
					resultdata[1] = model;
					resultdata[2] = Variant;
					resultdata[3] = "diesel";
					resultdata[4] = Pincode;
					resultdata[5] ="";
					resultdata[6] ="";
					resultdata[7] = "Variant not found";
					resultdata[8] = "Variant not found";
					resultdata[9] = "Variant not found";
					resultdata[10] = "";
					resultdata[12] = "";
					resultdata[11] = leadid;

					SetCellData1(excelpath_update, sheetname, resultdata, excelrow);
					excelrow++;*/
					continue;
				}
				try{
				JavascriptExecutor executor = (JavascriptExecutor) driver;
				executor.executeScript("arguments[0].click();", driver.findElement(By.xpath("//*[@id='campaingnMMVContinueId']")));
				System.out.println("Clicked continue after variant");
				}catch(Exception e){
					e.printStackTrace();
				}
				Thread.sleep(1500);
				
				driver.findElement(By.xpath("//*[@id='previousPolicyStausId_false']")).click();
				Thread.sleep(1000);
				try{
				Ackoelements.pincode(driver).clear();
				Thread.sleep(1500);
				}catch(Exception e){
					Ackoelements.pincode(driver).clear();
					Thread.sleep(1500);
				}
				Ackoelements.pincode(driver).sendKeys(Pincode);
				Thread.sleep(1500);
				
				System.out.println("Entered pincode");
				
				System.out.println(
						"entered new pincode :- " + Ackoelements.pincode(driver).getAttribute("value"));
				Ackoelements.pincontinue(driver).click();
				Thread.sleep(1500);

				try {

					String errMsg = driver.findElement(By.xpath(".//*[@class='Value FailPincode']"))
							.getText();

					if (errMsg.contains("almost there")) {
						driver.navigate().back();
						Thread.sleep(1500);
						System.out.println("Received Error - Wrong Pin Code");
						
						resultdata[0] = make;
						resultdata[1] = model;
						resultdata[2] = Variant;
						resultdata[3] = "Diesel";
						resultdata[4] = Pincode;
						resultdata[5] ="";
						resultdata[6] ="Error :-"+errMsg;
						resultdata[7] = nodata;
						resultdata[8] = nodata;
						resultdata[9] = nodata;
						resultdata[10] = "";
						resultdata[12] = "";
						resultdata[11] = leadid;

						SetCellData1(excelpath_update, sheetname, resultdata, excelrow);
						excelrow++;

						continue;
					}

				} catch (Exception e) {

				}
				
				Ackoelements.policyexpiry(driver).click();

				Thread.sleep(1000);
				Ackoelements.previouspolicyexpiry(driver).click();

				Thread.sleep(2000);
				
				System.out.println("Year to be selected is " + Year);
				if(Year>NCB){
					NCB=0;
					System.out.println("Reg year is less than NCB");
				}
				
				
				if(Year < 2008){
					Year = 0;
				}

				JavascriptExecutor executor1 = (JavascriptExecutor) driver;
				executor1.executeScript("arguments[0].click();",
						Ackoelements.regyear(driver, (int) Year));
				
				if(NCB==2013){
					NCB=0;
				}
				JavascriptExecutor executor2 = (JavascriptExecutor) driver;
				executor2.executeScript("arguments[0].click();",
						Ackoelements.claimregyear(driver, (int) NCB));
				
				Thread.sleep(2000);
				
				String insuredval = Ackoelements.insuredvalue(driver).getText();

				String killerprice = Ackoelements.carprice(driver).getText();

				String basepric = Ackoelements.basevalue(driver).getAttribute("value");
				String addontext ="";
				try{
				addontext = Ackoelements.addon(driver).getText();
				}catch(Exception e){
					addontext = "Zero dep not available";
				}

				System.out.println("The insured value is  : " + insuredval);
				System.out.println("The killer price is  : " + killerprice);
				System.out.println("The base price is  : " + basepric);
				
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

				LocalDate localDate = LocalDate.now();

				//System.out.println(dtf.format(localDate));
				String localDate1 = dtf.format(localDate).toString();

				DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss a");
				Date datet = new Date();
				String localtime = dateFormat.format(datet);
				
				String year_string = String.valueOf(Year);
				String NCB_string = String.valueOf(NCB);
				
				resultdata[0] = make;
				resultdata[1] = model;
				resultdata[2] = Variant;
				resultdata[3] = "Diesel";
				resultdata[4] = Pincode;
				resultdata[5] = year_string;
				resultdata[6] = NCB_string;
				resultdata[7] = killerprice;
				resultdata[8] = insuredval;
				resultdata[9] = basepric;
				resultdata[12] = localDate1;
				resultdata[10] = addontext;
				resultdata[11] = leadid;

				SetCellData1(excelpath_update, sheetname, resultdata, excelrow);
				excelrow++;


				}
				} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		

	}

	
	@AfterTest
	public void teardown() {

		try {

			Thread.sleep(2000);
			driver.close();
			driver.quit();

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	public static void SetInputData(String filePath, String sheetName, int row, int col, String data) throws Exception {

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
		cell = row0.getCell(col);
		if (cell == null) {
			cell = row0.createCell(col);
		}
		cell.setCellValue(data);

		try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
			workbook.write(fileOut);
			fileOut.close();
		} catch (Exception e) {
			System.out.println(e);
		}

		workbook.close();
	}

	public static void SetCellData1(String filePath, String sheetName, String[] result, int row) throws Exception {

		FileInputStream ExcelFile = new FileInputStream(filePath);

		HSSFWorkbook wb = new HSSFWorkbook(ExcelFile);

		Sheet resultSheet = wb.getSheet(sheetName);

		System.out.println("Row Passed : " + row);

		if (row == 1) {
			Row row0 = resultSheet.createRow(0);

			row0.createCell(0).setCellValue("S.No.");
			row0.createCell(1).setCellValue("Make");
			row0.createCell(2).setCellValue("Model");
			row0.createCell(3).setCellValue("Sub Model");
			row0.createCell(4).setCellValue("Fuel");
			row0.createCell(5).setCellValue("Pin Code");
			row0.createCell(6).setCellValue("Age");
			row0.createCell(7).setCellValue("Claim");
			row0.createCell(8).setCellValue("Premium");
			row0.createCell(9).setCellValue("IDV");
			row0.createCell(10).setCellValue("Base Value");
			row0.createCell(11).setCellValue("Zero Dep");
			row0.createCell(12).setCellValue("Lead ID");
			row0.createCell(13).setCellValue("Date");

		}
		Row row2 = resultSheet.createRow(row);
		row2.createCell(0).setCellValue(row);
		System.out.println("Row Created :" + (row));
		// TODO give max i length as result.length
		for (int i = 0; i < result.length; i++) {

			row2.createCell(i + 1).setCellValue(result[i]);

		}

		try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
			wb.write(fileOut);
			fileOut.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		wb.close();

	}

}
