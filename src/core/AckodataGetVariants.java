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

public class AckodataGetVariants {

	WebDriver driver;
	WebDriverWait wait;

	String baseurl = "	";
	String carurl = "https://www.acko.com/mmv/?quote=3rhi6Ja3d2y_0GcbacXNog";
	String excelpath = "C:\\Excelfiles\\Acko\\MakeList.xlsx";
	String excelpath_update = "C:\\Excelfiles\\Acko\\Data\\dataentry.xls";
	String sheetname = "Base Template";

	int rowCount;
	int excelrow = 1;
	int dexcelrow =1;
	XSSFSheet sheet;
	XSSFSheet modelsheet;
	XSSFRow row = null;
	XSSFWorkbook workbook;
	String resultdata[] = new String[13];
	
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
			excelpath_update = "C:\\Excelfiles\\Acko\\Data\\dataentry_" + localDate11 + ".xls";
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
	public void getPetrol() throws Exception {

		// driver.get(baseurl);
		driver.manage().window().maximize();
		// wait.until(ExpectedConditions.elementToBeClickable(pom.Ackoelements.dontknow(driver))).click();

		
		// Making the object of excel row
		

		for (int i = 1; i <= rowCount; i++) {
			try {

				driver.get(carurl);
				row = modelsheet.getRow(i);

				String make = modelsheet.getRow(i).getCell(1).getStringCellValue();
				System.out.println("the make is " + make);
				String model = "";
				Ackoelements.selectmake(driver).sendKeys(make);
				Thread.sleep(2000);
				List <WebElement> models = driver.findElements(By.xpath("//*[@id='acko_app']/div/div[2]/div[1]/div[2]/div[2]/div[3]/div/div/ul/li"));
				int msize = models.size();
				System.out.println("the size of models is " + msize);
				for(int m =0;m<msize;m++){
					model = models.get(m).getText();
					System.out.println("the model is " + model);
					String mm = make + " " + model;
					System.out.println(mm);
					
					Thread.sleep(4000);
					Ackoelements.selectmake(driver).clear();
					Thread.sleep(1000);
					Ackoelements.selectmake(driver).sendKeys(model);
					Thread.sleep(1500);
					//List <WebElement> models = driver.findElements(By.xpath("//*[@id='acko_app']/div/div[2]/div[1]/div[2]/div[2]/div[3]/div/div/ul/li"));
					Ackoelements.selectmodel(driver).click();

					try {
						try {
							Ackoelements.petrol(driver).click();
						} catch (Exception e) {

						}

						List<WebElement> varianttypes = Ackoelements.variantsCount(driver);
						int vsize = varianttypes.size();
						System.out.println("The number of petrol variants are " + vsize);

						for (int j = 1; j <= vsize; j++) {

							String variant = "";
							try {
								Thread.sleep(1000);

								//System.out.println("the current url is " + driver.getCurrentUrl());

								System.out.println(
										"The model variant selected is  " + Ackoelements.variants(driver, j).getText());
								variant = Ackoelements.variants(driver, j).getText();
								
								
								
								resultdata[0] = make;
								resultdata[1] = model;
								resultdata[2] = variant;
								resultdata[3] = "petrol";
								

								SetCellData1(excelpath_update, sheetname, resultdata, excelrow);
								excelrow++;


							} catch (Exception e) {
								resultdata[0] = make;
								resultdata[1] = model;
								resultdata[2] = "No variant available";
								resultdata[3] = "petrol";
								
								SetCellData1(excelpath_update, sheetname, resultdata, dexcelrow);
								dexcelrow++;
								//e.printStackTrace();
							}
							
						}
						
					}catch (Exception e) {
						e.printStackTrace();
					}

				}
				
				

				
				
			}catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		dexcelrow = excelrow;	
	}					

	
	//@Test(priority = 2)
	public void getDiesel() throws Exception {

		// driver.get(baseurl);
		driver.manage().window().maximize();
		System.out.println("dexcelrow : "+dexcelrow);
		// wait.until(ExpectedConditions.elementToBeClickable(pom.Ackoelements.dontknow(driver))).click();

		
		// Making the object of excel row
		

		for (int i = 1; i <= rowCount; i++) {
			try {

				driver.get(carurl);
				row = modelsheet.getRow(i);

				String make = modelsheet.getRow(i).getCell(0).getStringCellValue();
				System.out.println("the make is " + make);

				String model = modelsheet.getRow(i).getCell(1).getStringCellValue();
				System.out.println("the model is " + model);

				String mm = make + " " + model;
				System.out.println(mm);

				Thread.sleep(4000);

				Ackoelements.selectmake(driver).sendKeys(mm);
				Thread.sleep(1500);
				Ackoelements.selectmodel(driver).click();

				try {
					try {
						Ackoelements.diesel(driver).click();
					} catch (Exception e) {

					}

					List<WebElement> varianttypes = Ackoelements.variantsCount(driver);
					int vsize = varianttypes.size();
					System.out.println("The number of petrol variants are " + vsize);

					for (int j = 1; j <= vsize; j++) {

						String variant = "";
						try {
							Thread.sleep(1000);

							//System.out.println("the current url is " + driver.getCurrentUrl());

							System.out.println(
									"The model variant selected is  " + Ackoelements.variants(driver, j).getText());
							variant = Ackoelements.variants(driver, j).getText();
							
							
							
							resultdata[0] = make;
							resultdata[1] = model;
							resultdata[2] = variant;
							resultdata[3] = "diesel";
							
							SetCellData1(excelpath_update, sheetname, resultdata, dexcelrow);
							dexcelrow++;


						} catch (Exception e) {
							resultdata[0] = make;
							resultdata[1] = model;
							resultdata[2] = "'No variant available";
							resultdata[3] = "diesel";
							
							SetCellData1(excelpath_update, sheetname, resultdata, dexcelrow);
							dexcelrow++;
							//e.printStackTrace();
						}
						
					}
					
				}catch (Exception e) {
					e.printStackTrace();
				}
				
			}catch (Exception e) {
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
			row0.createCell(11).setCellValue("Date");
			row0.createCell(12).setCellValue("Time");

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
