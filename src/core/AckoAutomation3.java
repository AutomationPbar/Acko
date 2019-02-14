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
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.poi.hssf.usermodel.HSSFSheet;
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
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import pom.Ackoelements;

public class AckoAutomation3 {

	WebDriver driver;
	WebDriverWait wait;

	String baseurl = "	";
	String carurl = "https://www.acko.com/mmv/?quote=3rhi6Ja3d2y_0GcbacXNog";
	String excelpath = "C:\\Excelfiles\\Acko\\testdata.xlsx";
	String excelpath_update = "C:\\Excelfiles\\Acko\\Data\\dataentry.xlsx";
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


	@BeforeMethod

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
			excelpath_update = "C:\\Excelfiles\\Acko\\Data\\dataentry_cmt" + localDate11 + ".xlsx";
			SetExcelFile(excelpath_update, sheetname);

			row = modelsheet.getRow(0);

			int colCount = row.getLastCellNum();
			System.out.println("Column Count :- " + colCount);

			rowCount = modelsheet.getLastRowNum() + 1;
			System.out.println("Row Count :- " + rowCount);

		} catch (Exception e) {
			//e.printStackTrace();
		}
	}

	@Test(priority = 1)
	public void getDiesel() throws Exception {

		
		driver.manage().window().maximize();
		// wait.until(ExpectedConditions.elementToBeClickable(pom.Ackoelements.dontknow(driver))).click();

		
		// Making the object of excel row
		

		for (int i =1; i <=10; i++) {
			try {

				driver.get(carurl);
				row = modelsheet.getRow(i);
				
				List<String> arrName = new ArrayList<String>();
		        for(int j=0; j<29; j++){
		        	
		        	
		            // Create an object reference of 'Cell' class
		            Cell cell = row.getCell(j);
		            CellType modelcell = cell.getCellTypeEnum();
		            
		            if(modelcell == CellType.STRING){
		            	arrName.add(modelsheet.getRow(i).getCell(j).getRichStringCellValue().toString());
		            }else if(modelcell == CellType.NUMERIC){
		            
		            
		            arrName.add(modelsheet.getRow(i).getCell(j).getRawValue().toString());
		            }
		        }	
		        System.out.println(arrName);
		        System.out.println("Size of the arrayList: "+arrName.size());
		        // Create an iterator to iterate through the arrayList- 'arrName'
		        Iterator<String> itr = arrName.iterator();
		        while(itr.hasNext()){
		            System.out.println("arrayList values: "+itr.next());
		        }
		        
		        String model = arrName.get(27);
		        System.out.println("The model is " + model);
		        
		        String make = arrName.get(26);
				System.out.println("the make is " + make);
				
				
				String mm = make + " " + model;
				System.out.println(mm);
				

				String Variant = arrName.get(28);
				System.out.println("the variant is " + Variant);
				

				String Year1 = arrName.get(4);
				int Year = Integer.parseInt(Year1);
				System.out.println("the Year is " + Year);
				
				String NCB1 = arrName.get(5);
				int NCB = Integer.parseInt(NCB1);
				System.out.println("the NCB Year is " + NCB);
				
				if(NCB ==0){
					NCB = 2018;
				}else if(NCB == 20){
					NCB = 2017;
				}else if (NCB == 25){
					NCB = 2016;
				}else if (NCB == 35){
					NCB = 2015;
				}else if (NCB == 45){
					NCB = 2014;
				}else if (NCB == 50){
					NCB = 2013;
				}
				
				String Fuel = arrName.get(6);
				System.out.println("the Fuel is " + Fuel);
				
				String Pincode = arrName.get(7);
				System.out.println("the Pincode is " + Pincode);
				
				String leadid= arrName.get(0);
				System.out.println("the lead id is " + leadid);
				
				Thread.sleep(4000);

				Ackoelements.selectmake(driver).sendKeys(model);
				Thread.sleep(2500);
				
				
				
				
				
				//*[@id="acko_app"]/div/div[2]/div[1]/div[2]/div[2]/div[3]/div/div/ul/li
				List <WebElement> models = driver.findElements(By.xpath("//*[@id='acko_app']/div/div[2]/div[1]/div[2]/div[2]/div[3]/div/div/ul/li"));
				int msize = models.size();
				System.out.println("the size of models is " + msize);
				for(int m =0;m<msize;m++){
					//driver.get(carurl);
				//	Ackoelements.selectmake(driver).sendKeys(mm);
					//Thread.sleep(1500);
					
					Ackoelements.selectmodell(driver,m).click();
					
					System.out.println("Make model selected ");
					
					if(Fuel.equalsIgnoreCase("Diesel")){
						
						try {
							Ackoelements.diesel(driver).click();
							System.out.println("Fuel selected " + Fuel);
						} catch (Exception e) {
							//e.printStackTrace();
						}
						}else {
							try {
								Ackoelements.petrol(driver).click();
								System.out.println("Fuel selected " + Fuel);
							} catch (Exception e) {
								//e.printStackTrace();
							}
						}
				
				
			
				
				List<WebElement> varianttypes = Ackoelements.variantsCount(driver);
				int vsize = varianttypes.size();
				
				System.out.println("The number of variants are " + vsize);
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
				
					continue;
				}
				try{
				JavascriptExecutor executor = (JavascriptExecutor) driver;
				executor.executeScript("arguments[0].click();", driver.findElement(By.xpath("//*[@id='campaingnMMVContinueId']")));
				System.out.println("Clicked continue after variant");
				}catch(Exception e){
					//e.printStackTrace();
					JavascriptExecutor executor = (JavascriptExecutor) driver;
					executor.executeScript("arguments[0].click();", driver.findElement(By.xpath("//*[@id='campaingnMMVContinueId']")));
					System.out.println("Clicked continue after variant");
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
						
						arrName.add("No Data");
						arrName.add("Error" +errMsg);
						arrName.add(nodata);
						arrName.add(nodata);
						arrName.add(nodata);
						
						SetInputData(excelpath_update, sheetname, excelrow,arrName);
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
				
				int currentmonth = Calendar.getInstance().get(Calendar.MONTH)+1;
				System.out.println("current month is " +currentmonth);
				
				Thread.sleep(1500);
				pom.Ackoelements.buymonth(driver,currentmonth).click();
				
				if(NCB==2013){
					NCB=0;
				}
				JavascriptExecutor executor2 = (JavascriptExecutor) driver;
				executor2.executeScript("arguments[0].click();",
						Ackoelements.claimregyear(driver, (int) NCB));
				
				Thread.sleep(2000);
				
				String insuredval = Ackoelements.insuredvalue(driver).getText();

				String killerprice = Ackoelements.carprice(driver).getText();
				killerprice = killerprice.replaceAll("[^\\d.]", "");
				
				int kp = Integer.parseInt(killerprice);
				System.out.println("integer value ofkiller price "+ kp);

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
				
				
				arrName.add(killerprice);
				arrName.add(insuredval);
				arrName.add(basepric);
				arrName.add(addontext);
				
		
				SetInputData(excelpath_update, sheetname, excelrow,arrName);
				excelrow++;

				}
				
				
				} catch (Exception e) {
				//e.printStackTrace();
			}
		}
		
		

	}

	
	@AfterTest
	public void teardown() {

		try {
			System.out.println("comparing the data");
			utilities.Ackocomparepivot.datacomparison(excelpath_update);
			
			Thread.sleep(10000);
			System.out.println("creating pivot table ");
			utilities.Ackocomparepivot.create_pivot(excelpath_update);

			Thread.sleep(3000);
			driver.close();
			driver.quit();

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	public static void SetInputData(String filePath, String sheetName, int row, List<String> data) throws Exception {

		FileInputStream fis = new FileInputStream(filePath);
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		XSSFSheet inputSheet = workbook.getSheetAt(0);

		// Retrieve the row and check for null
		XSSFRow row0 = (XSSFRow) inputSheet.getRow(row);
		Cell cell = null;
		if (row0 == null) {
			row0 = (XSSFRow) inputSheet.createRow(row);
		}
		
		Row row1 = inputSheet.createRow(0);

		row1.createCell(0).setCellValue("LeadID");
		row1.createCell(1).setCellValue("MakeName");
		row1.createCell(2).setCellValue("ModelName");
		row1.createCell(3).setCellValue("Variantname");
		row1.createCell(4).setCellValue("RegistrationYear");
		row1.createCell(5).setCellValue("NCB");
		row1.createCell(6).setCellValue("FuleType");
		row1.createCell(7).setCellValue("RegistrationPostCode");
		row1.createCell(8).setCellValue("RegisteredCityName");
		row1.createCell(9).setCellValue("RegisteredStateName");
		row1.createCell(10).setCellValue("Zone");
		row1.createCell(11).setCellValue("City Tier");
		row1.createCell(12).setCellValue("CoverTypedetail");
		row1.createCell(13).setCellValue("PlanAddOns");
		row1.createCell(14).setCellValue("BookedLead_IDV");
		row1.createCell(15).setCellValue("Booking Lead Total Premium");
		row1.createCell(16).setCellValue("TotalOwnDamagePremium");
		row1.createCell(17).setCellValue("FinalTotalLiabilityPremium");
		row1.createCell(18).setCellValue("TotalAddOnPremium");
		row1.createCell(19).setCellValue("OD Discount %");
		row1.createCell(20).setCellValue("1st Rank Insurer (Comp)");
		row1.createCell(21).setCellValue("1st Rank IDV (Comp)");
		row1.createCell(22).setCellValue("1st Rank Total Premium (Comp)");
		row1.createCell(23).setCellValue("1stRank Own Damage Premium (Comp)");
		row1.createCell(24).setCellValue("1stRank Total Liability Premium (Comp)");
		row1.createCell(25).setCellValue("OD Discount %(Comp)");
		row1.createCell(26).setCellValue("Acko Make");
		row1.createCell(27).setCellValue("Acko Model");
		row1.createCell(28).setCellValue("Acko Variant");
		row1.createCell(29).setCellValue("Premium");
		row1.createCell(30).setCellValue("IDV");
		row1.createCell(31).setCellValue("Base Value");
		row1.createCell(32).setCellValue("Zero Dep");
		row1.createCell(33).setCellValue("PB/Acko");
		
		
	
		// Update the value of cell
		for(int i=0;i<data.size();i++){
		cell = row0.getCell(i);
		if (cell == null) {
			cell = row0.createCell(i);
		}
		cell.setCellValue(data.get(i));
		}
		try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
			workbook.write(fileOut);
			fileOut.close();
		} catch (Exception e) {
			System.out.println(e);
		}

		workbook.close();
	}
	
	
	public static void SetExcelFile(String path, String sheetName) throws Exception {

		try {
			// Opening Excel File
			XSSFWorkbook wb = new XSSFWorkbook();
			
			XSSFSheet sh = wb.createSheet(sheetName);
			
			 sh = wb.getSheet(sheetName);

			
			FileOutputStream fileOut = new FileOutputStream(path);
			wb.write(fileOut);
            fileOut.close();
            wb.close();
            System.out.println("Your excel file has been generated!");

		} catch (Exception e) {
			throw (e);
		}

	}



}
