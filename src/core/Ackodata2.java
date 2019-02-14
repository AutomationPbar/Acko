package core;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
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

import org.apache.commons.codec.binary.Base64;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONObject;
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

public class Ackodata2 {

	WebDriver driver;
	WebDriverWait wait;

	String baseurl = "	";
	String carurl = "https://www.acko.com/mmv/?quote=3rhi6Ja3d2y_0GcbacXNog";
	String excelpath = "C:\\Excelfiles\\sampledata1.xlsx";
	String excelpath_update = "C:\\Excelfiles\\dataentry.xls";
	String sheetname = "Base Template";
	String emailFrom = "prdetext@gmail.com";
	String emailTo1 = "prdetext@gmail.com";
	String emailSubject ="Acko data";
	String emailBody1 = "PFA Acko datasheet";
	String emailTo2 = "Avani@policybazaar.com";
	String emailTo3 = "prdetext@gmail.com";

	int rowCount;
	int excelrow = 1;
	int dexcelrow =1;
	XSSFSheet sheet;
	XSSFSheet modelsheet;
	XSSFRow row = null;
	XSSFWorkbook workbook;
	String resultdata[] = new String[13];
	
	private final boolean chunks = true;
	String fileToEncode = "";
	String encodedFilePath = "C:\\Excelfiles\\dataentry.txt";
	String apiURL = "http://matrixliveapi.policybazaar.com/Communication/Communication.svc/send";

	@BeforeSuite

	public void setup() {

		try {
			System.setProperty("webdriver.chrome.driver", "C:\\eclipse\\chromedriver.exe");

			driver = new ChromeDriver();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

			wait = new WebDriverWait(driver, 10);
			
			FileInputStream fis = new FileInputStream(excelpath);
			workbook = new XSSFWorkbook(fis);
			modelsheet = workbook.getSheetAt(1);

			
			SimpleDateFormat formatter = new SimpleDateFormat("dd_MM_yyyy_HH_mm");
			Date datedd = new Date();
			System.out.println(formatter.format(datedd));
			String localDate11 = formatter.format(datedd).toString();
			excelpath_update = "C:\\Excelfiles\\dataentry" + localDate11 + ".xls";
			//encodedFilePath = "C:\\Excelfiles\\dataentry"+localDate11+".txt";
			System.out.println("encoded file path " +encodedFilePath);
			utilities.ExcelUtils.SetExcelFile(excelpath_update, sheetname);
			
			fileToEncode = excelpath_update;

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

				String make = modelsheet.getRow(i).getCell(0).getStringCellValue();
				System.out.println("the make is " + make);

				String model = modelsheet.getRow(i).getCell(1).getStringCellValue();
				System.out.println("the model is " + model);

				String mm = make + " " + model;
				System.out.println(mm);

				Thread.sleep(2000);

				Ackoelements.selectmake(driver).sendKeys(mm);
				Thread.sleep(1500);
				Ackoelements.selectmodel(driver).click();

				try {
					try {
						Ackoelements.petrol(driver).click();
					} catch (Exception e) {

					}

					List<WebElement> varianttypes = Ackoelements.variantsCount(driver);
					int vsize = varianttypes.size();
					System.out.println("The number of petrol variants are " + vsize);

					for (int j = 1; j <= 1; j++) {

						String variant = "";
						try {
							Thread.sleep(1000);

							System.out.println("the current url is " + driver.getCurrentUrl());

							System.out.println(
									"The model variant selected is  " + Ackoelements.variants(driver, j).getText());
							variant = Ackoelements.variants(driver, j).getText();
							Ackoelements.variants(driver, j).click();

							JavascriptExecutor executor = (JavascriptExecutor) driver;
							executor.executeScript("arguments[0].click();", Ackoelements.carcontinue(driver));

						} catch (Exception e) {
							e.printStackTrace();
						}
						XSSFSheet pincodesheet = workbook.getSheetAt(2);

						XSSFRow row1 = null;

						// Making the object of excel row
						row1 = pincodesheet.getRow(0);

						// int colCount1 = row1.getLastCellNum();
						// System.out.println("Column Count :- " +
						// colCount);

						int pcount = pincodesheet.getLastRowNum() + 1;
						System.out.println("Row Count of pincode :- " + pcount);
						ArrayList<String> pinCodes = new ArrayList<String>();
						for (int n = 1; n < pcount; n++) {
							try {
								int pincodedata = (int) pincodesheet.getRow(n).getCell(0).getNumericCellValue();

								String pdata = Integer.toString(pincodedata);
								pinCodes.add(pdata);
								//System.out.println("the pincode is " + pdata);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}

						System.out.println("Got Pincodes in Array : " + pinCodes.size());
						
						Thread.sleep(1500);
						for (int k = 0; k <pinCodes.size(); k++) {

							try {

								String pdata = pinCodes.get(k);

								Ackoelements.pincode(driver).clear();
								Thread.sleep(1500);
								System.out.println("Pincode data :" +pdata);
								Ackoelements.pincode(driver).sendKeys(pdata);
								Thread.sleep(1500);
								System.out.println(
										"entered new pincode :- " + Ackoelements.pincode(driver).getAttribute("value"));

								System.out.println(
										"Make Model Var Pin  petrol" + make + " - " + model + " - " + variant + " - " + pdata);

								Ackoelements.pincontinue(driver).click();
								Thread.sleep(1500);

								try {

									String errMsg = driver.findElement(By.xpath(".//*[@class='Value FailPincode']"))
											.getText();

									if (errMsg.contains("almost there")) {
										driver.navigate().back();
										Thread.sleep(1500);
										System.out.println("Received Error - Wrong Pin Code");

										continue;
									}

								} catch (Exception e) {

								}

								Ackoelements.policyexpiry(driver).click();

								Thread.sleep(1000);
								Ackoelements.previouspolicyexpiry(driver).click();

								Thread.sleep(2000);

								int year = Calendar.getInstance().get(Calendar.YEAR);

								System.out.println("Current year is " + year);

								int one = 1;

								int five = 5;
								int ten = 9;

								for (int x = 1; x <= 3; x++) {

									int yr = 0;
									if (x == 1) {
										yr = one;
									} else if (x == 2) {
										yr = five;
									} else if (x == 3) {
										yr = ten;
									}

									System.out.println("Final Age : " + yr);

									//String age = Integer.toString(yr);
									//System.out.println("the age of vehicle is " + yr);

									int newyear = year - yr;
									String buyyear =Integer.toString(newyear);
									System.out.println("Year to be selected is " + buyyear);

									JavascriptExecutor executor1 = (JavascriptExecutor) driver;
									executor1.executeScript("arguments[0].click();",
											Ackoelements.regyear(driver, newyear));

									// Ackoelements.regyear(driver,newyear).click();
									Thread.sleep(2000);

									Ackoelements.claimyear(driver).click();
									Thread.sleep(2000);

									String insuredval = Ackoelements.insuredvalue(driver).getText();

									String killerprice = Ackoelements.carprice(driver).getText();

									String basepric = Ackoelements.basevalue(driver).getAttribute("value");

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
									//System.out.println(localtime);

									

									resultdata[0] = make;
									resultdata[1] = model;
									resultdata[2] = variant;
									resultdata[3] = "petrol";
									resultdata[4] = pdata;
									resultdata[5] = buyyear;
									resultdata[6] = "None";
									resultdata[7] = killerprice;
									resultdata[8] = insuredval;
									resultdata[9] = basepric;
									resultdata[10] = localDate1;
									resultdata[11] = localtime;

									SetCellData1(excelpath_update, sheetname, resultdata, excelrow);
									excelrow++;

									// SetInputData(excelpath_update,sheetname,
									// i,12,killerprice);
									// SetInputData(excelpath_update,sheetname,
									// i,13,insuredval);
									driver.navigate().back();
									System.out.println("clicked back first time");
									Thread.sleep(1000);
									JavascriptExecutor executor12 = (JavascriptExecutor) driver;
									executor12.executeScript("arguments[0].click();", Ackoelements.edityear(driver));
									// Ackoelements.edityear(driver).click();
									Thread.sleep(1000);
								}
								JavascriptExecutor executor12 = (JavascriptExecutor) driver;
								executor12.executeScript("arguments[0].click();", Ackoelements.editpincode(driver));
								// Ackoelements.editpincode(driver).click();
								Thread.sleep(1000);

							} catch (Exception e) {
								e.printStackTrace();
							}

						}
						JavascriptExecutor executor12 = (JavascriptExecutor) driver;
						executor12.executeScript("arguments[0].click();", Ackoelements.editlink(driver));
						// Ackoelements.editlink(driver).click();

						Thread.sleep(2000);
						try {

							JavascriptExecutor executor1 = (JavascriptExecutor) driver;
							executor1.executeScript("arguments[0].click();", Ackoelements.crossbutton(driver));
							// Ackoelements.crossbutton(driver).click();
						} catch (Exception e) {
							// e.printStackTrace();
						}
					}
				} catch (Exception e) {
					// e.printStackTrace();
				}

				try {
					JavascriptExecutor executor = (JavascriptExecutor) driver;
					executor.executeScript("arguments[0].click();", Ackoelements.modelcrossbutton(driver));
					// Ackoelements.modelcrossbutton(driver).click();
				} catch (Exception e) {
					// e.printStackTrace();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		dexcelrow = excelrow;

	}

	@Test(priority = 2)
	public void getDiesel() {

		// driver.get(baseurl);
		System.out.println("dexcelrow : "+dexcelrow);
		driver.manage().window().maximize();
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

				Thread.sleep(2000);

				Ackoelements.selectmake(driver).sendKeys(mm);
				Thread.sleep(1500);
				
					Ackoelements.selectmodel(driver).click();
					Thread.sleep(1500);
					try {
						
						Ackoelements.diesel(driver).click();
				
					
					Thread.sleep(2000);
					List<WebElement> varianttypes = Ackoelements.variantsCount(driver);
					int vsize = varianttypes.size();
					System.out.println("The number of diesel variants are " + vsize);

					for (int j = 1; j <= 1; j++) {

						String variant = "";
						try {
							
							
							
							Thread.sleep(1000);

							System.out.println("the current url is " + driver.getCurrentUrl());

							System.out.println(
									"The model variant selected is  " + Ackoelements.variants(driver, j).getText());
							variant = Ackoelements.variants(driver, j).getText();
							Ackoelements.variants(driver, j).click();

							JavascriptExecutor executor = (JavascriptExecutor) driver;
							executor.executeScript("arguments[0].click();", Ackoelements.carcontinue(driver));

						} catch (Exception e) {
							e.printStackTrace();
						}
						XSSFSheet pincodesheet = workbook.getSheetAt(2);

						XSSFRow row1 = null;

						// Making the object of excel row
						row1 = pincodesheet.getRow(0);

						// int colCount1 = row1.getLastCellNum();
						// System.out.println("Column Count :- " +
						// colCount);

						int pcount = pincodesheet.getLastRowNum() + 1;
						System.out.println("Row Count of pincode :- " + pcount);
						ArrayList<String> pinCodes = new ArrayList<String>();
						for (int n = 1; n < pcount; n++) {
							try {
								int pincodedata = (int) pincodesheet.getRow(n).getCell(0).getNumericCellValue();

								String pdata = Integer.toString(pincodedata);
								pinCodes.add(pdata);
								//System.out.println("the pincode is " + pdata);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}

						System.out.println("Got Pincodes in Array : " + pinCodes.size());
						
						Thread.sleep(1500);
						for (int k = 0; k <pinCodes.size(); k++) {

							try {

								String pdata = pinCodes.get(k);

								Ackoelements.pincode(driver).clear();
								Thread.sleep(1500);
								System.out.println("Pincode data :" +pdata);
								Ackoelements.pincode(driver).sendKeys(pdata);
								Thread.sleep(1500);
								System.out.println(
										"entered new pincode :- " + Ackoelements.pincode(driver).getAttribute("value"));

								System.out.println(
										"Make Model Var Pin  diesel " + make + " - " + model + " - " + variant + " - " + pdata);

								Ackoelements.pincontinue(driver).click();
								Thread.sleep(1500);

								try {

									String errMsg = driver.findElement(By.xpath(".//*[@class='Value FailPincode']"))
											.getText();

									if (errMsg.contains("almost there")) {
										driver.navigate().back();
										Thread.sleep(1500);
										System.out.println("Received Error - Wrong Pin Code");

										continue;
									}

								} catch (Exception e) {

								}

								Ackoelements.policyexpiry(driver).click();

								Thread.sleep(1000);
								Ackoelements.previouspolicyexpiry(driver).click();

								Thread.sleep(2000);

								int year = Calendar.getInstance().get(Calendar.YEAR);

								System.out.println("Current year is " + year);

								int one = 1;

								int five = 5;
								int ten = 9;

								for (int x = 1; x <= 3; x++) {

									int yr = 0;
									if (x == 1) {
										yr = one;
									} else if (x == 2) {
										yr = five;
									} else if (x == 3) {
										yr = ten;
									}

									System.out.println("Final Age : " + yr);

									//String age = Integer.toString(yr);
									//System.out.println("the age of vehicle is " + yr);

									int newyear = year - yr;
									String buyyear =Integer.toString(newyear);
									System.out.println("Year to be selected is " + buyyear);

									JavascriptExecutor executor1 = (JavascriptExecutor) driver;
									executor1.executeScript("arguments[0].click();",
											Ackoelements.regyear(driver, newyear));

									// Ackoelements.regyear(driver,newyear).click();
									Thread.sleep(2000);

									Ackoelements.claimyear(driver).click();
									Thread.sleep(2000);

									String insuredval = Ackoelements.insuredvalue(driver).getText();

									String killerprice = Ackoelements.carprice(driver).getText();

									String basepric = Ackoelements.basevalue(driver).getAttribute("value");

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
									//System.out.println(localtime);

									String resultdata[] = new String[13];

									resultdata[0] = make;
									resultdata[1] = model;
									resultdata[2] = variant;
									resultdata[3] = "diesel";
									resultdata[4] = pdata;
									resultdata[5] = buyyear;
									resultdata[6] = "None";
									resultdata[7] = killerprice;
									resultdata[8] = insuredval;
									resultdata[9] = basepric;
									resultdata[10] = localDate1;
									resultdata[11] = localtime;

									SetCellData1(excelpath_update, sheetname, resultdata, dexcelrow);
									dexcelrow++;

									// SetInputData(excelpath_update,sheetname,
									// i,12,killerprice);
									// SetInputData(excelpath_update,sheetname,
									// i,13,insuredval);
									driver.navigate().back();
									System.out.println("clicked back first time");
									Thread.sleep(1000);
									JavascriptExecutor executor12 = (JavascriptExecutor) driver;
									executor12.executeScript("arguments[0].click();", Ackoelements.edityear(driver));
									// Ackoelements.edityear(driver).click();
									Thread.sleep(1000);
								}
								JavascriptExecutor executor12 = (JavascriptExecutor) driver;
								executor12.executeScript("arguments[0].click();", Ackoelements.editpincode(driver));
								// Ackoelements.editpincode(driver).click();
								Thread.sleep(1000);

							} catch (Exception e) {
								e.printStackTrace();
							}

						}
						JavascriptExecutor executor12 = (JavascriptExecutor) driver;
						executor12.executeScript("arguments[0].click();", Ackoelements.editlink(driver));
						// Ackoelements.editlink(driver).click();

						Thread.sleep(2000);
						try {

							JavascriptExecutor executor1 = (JavascriptExecutor) driver;
							executor1.executeScript("arguments[0].click();", Ackoelements.crossbutton(driver));
							// Ackoelements.crossbutton(driver).click();
							
							Thread.sleep(1500);
							Ackoelements.diesel(driver).click();
							Thread.sleep(1500);
						} catch (Exception e) {
							// e.printStackTrace();
						}
					}
				} catch (Exception e) {
					// e.printStackTrace();
				}

				try {
					JavascriptExecutor executor = (JavascriptExecutor) driver;
					executor.executeScript("arguments[0].click();", Ackoelements.modelcrossbutton(driver));
					// Ackoelements.modelcrossbutton(driver).click();
				} catch (Exception e) {
					// e.printStackTrace();
				}

			} catch (Exception e) {
				//e.printStackTrace();
			}
		}

	}

	@AfterTest
	public void teardown() {

		try {
			encodeReport();
			sendEmail();
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
	
	

	public void encodeReport() {

		try {
			encode(fileToEncode, encodedFilePath, chunks);
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	
	public void sendEmail() {
		// String string = "";
		try {

			String Base64String = "";

			InputStream inputStream = new FileInputStream(encodedFilePath);
			InputStreamReader reader = new InputStreamReader(inputStream);
			BufferedReader br = new BufferedReader(reader);
			String line;
			while ((line = br.readLine()) != null) {
				Base64String += line;
			}

			// System.out.println("String Created :" + Base64String);

			Date dt = new Date();

			SimpleDateFormat smdt = new SimpleDateFormat("dd.MM.yyyy");

			String datee = smdt.format(dt);

			System.out.println("Date : " + datee);

			String jSonDataString = "{'CommunicationDetails':{'LeadID':25618644,'Conversations':[{'From':'" + emailFrom
					+ "', 'ToReceipent':['" + emailTo1 + "','" + emailTo2 + "','" + emailTo3 + "'], 'BccEmail':[],'CCEmail':[], 'Body':'" + emailBody1
					+ "<BR>" + "<BR>" + emailBody1 + "', 'Subject':'" + emailSubject + " " + datee
					+ " ', 'MailAttachments': [ {'FileName': 'TeleMedical_Data_Report_" + datee
					+ ".xls', 'AttachemntContent': '" + Base64String
					+ "', 'AttachmentURL': '' }], 'CreatedBy':'Neeraj','UserID':124,'AutoTemplate':true}], 'CommunicationType':1} }";

			JSONObject jsonObject = new JSONObject(jSonDataString);

			System.out.println(jSonDataString);

			System.out.println(jsonObject);

			// Step2: Now pass JSON File Data to REST Service
			try {
				URL url = new URL(apiURL);
				URLConnection connection = url.openConnection();
				connection.setDoOutput(true);
				connection.setRequestProperty("Content-Type", "application/json");
				connection.setConnectTimeout(5000);
				connection.setReadTimeout(5000);
				OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
				out.write(jsonObject.toString());
				out.close();

				BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

				while (in.readLine() != null) {
				}
				System.out.println("\n REST Service Invoked Successfully..");
				in.close();
			} catch (Exception e) {
				System.out.println("\nError while calling REST Service");
				System.out.println(e);
			}

			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void encode(String sourceFile, String targetFile, boolean isChunked) throws Exception {

		byte[] base64EncodedData = Base64.encodeBase64(loadFileAsBytesArray(sourceFile), isChunked);

		writeByteArraysToFile(targetFile, base64EncodedData);
	}

	public void decode(String sourceFile, String targetFile) throws Exception {

		byte[] decodedBytes = Base64.decodeBase64(loadFileAsBytesArray(sourceFile));

		writeByteArraysToFile(targetFile, decodedBytes);
	}

	public byte[] loadFileAsBytesArray(String fileName) throws Exception {

		File file = new File(fileName);
		int length = (int) file.length();
		BufferedInputStream reader = new BufferedInputStream(new FileInputStream(file));
		byte[] bytes = new byte[length];
		reader.read(bytes, 0, length);
		reader.close();
		return bytes;

	}

	public void writeByteArraysToFile(String fileName, byte[] content) throws IOException {

		File file = new File(fileName);
		BufferedOutputStream writer = new BufferedOutputStream(new FileOutputStream(file));
		writer.write(content);
		writer.flush();
		writer.close();

	}

}
