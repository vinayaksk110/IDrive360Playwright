package idrive360.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import idrive360.PlaywrightFactory.PlaywrightFactory;
import idrive360.data.UserCredentials;
import idrive360.testbase.Constants;

public class LocalExcelUtility extends PlaywrightFactory {

	UserCredentials userData = new UserCredentials();
	Workbook workbook;

	public enum ColumnHeadingValues {
		testcase, firstname, lastname, email, password, passwordtoupdate, companyname, companynametoupdate, phonenumber,
		showpassword, hidepassword, firstnametoupdate, lastnametoupdate, phonenotoupdate, cc, testcc, mm, yy, cvv,
		billingaddress, country, state, zipcode, reasonforcancellation, subuseremail, role, usertobeedited,
		usertobedeleted, usertobedisabled, usertobeenabled, usertoberesetpassword, usertoberesendmail, adminemail,
		editedcompanyname, parentcompanyname, emailaddress, companybottom, companyheader, ruletobeadded, backupplanname;

	}

	public LocalExcelUtility() {
		excelUtility = new ExcelUtilityNew();
		ZipSecureFile.setMinInflateRatio(0);
	}

//	public void writeTestHeading() {
//		try {
//			log.info("Updated the test heading, in the local results file.");
//			excelUtility.writeHeadingToSheet(getWorkbookPath_ResultsOfTesting(), getSheetName());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	/**
//	 * This method updates the test result of the test.
//	 * 
//	 * @param testCase          : This is the current test case being executed.
//	 * @param testResult        : This is the result of the current test case (Pass
//	 *                          / Fail).
//	 * @param testResultComment : This is the comment which indicates why a
//	 *                          particular test result has been updated.
//	 * @param executionTime     : This is the time taken for the script from the
//	 *                          start to end.
//	 */
//	public void writeTestResultsStatus(String testCase, String testResult, String testResultComment,
//			int executionTime) {
//		try {
//			excelUtility.writeResultsToSheet(getWorkbookPath_ResultsOfTesting(), getSheetName(), testCase, testResult,
//					testResultComment, executionTime, Constants.PROJECT_THRESHOLD);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	/**
//	 * This method get the last written row and makes it bold
//	 */
//	public void makeRowBold() {
//		try (FileInputStream fis = new FileInputStream(getWorkbookPath_ResultsOfTesting());
//				Workbook workbook = new XSSFWorkbook(fis)) {
//
//			Sheet sheet = workbook.getSheet(getSheetName());
//
//			if (sheet != null) {
//				int lastRowIndex = sheet.getLastRowNum();
//				Row lastRow = sheet.getRow(lastRowIndex);
//
//				if (lastRow != null) {
//					CellStyle boldStyle = workbook.createCellStyle();
//					Font boldFont = workbook.createFont();
//					boldFont.setBold(true);
//					boldStyle.setFont(boldFont);
//
//					for (Cell cell : lastRow) {
//						cell.setCellStyle(boldStyle);
//					}
//
//					try (FileOutputStream fos = new FileOutputStream(getWorkbookPath_ResultsOfTesting())) {
//						workbook.write(fos);
//					}
//				} else {
//					log.info("Last row not found in sheet: " + getSheetName());
//				}
//			} else {
//				log.info("Sheet not found: " + getSheetName());
//			}
//
//		} catch (Exception ex) {
//			ex.printStackTrace();
//		}
//	}
//
//	/**
//	 * This method add background colour to the columns for the test results
//	 */
//
//	public void addBackgroundColor() {
//		try (FileInputStream fis = new FileInputStream(getWorkbookPath_ResultsOfTesting());
//				Workbook workbook = new XSSFWorkbook(fis)) {
//
//			Sheet sheet = workbook.getSheet(getSheetName());
//
//			if (sheet != null) {
//				int lastRowIndex = sheet.getLastRowNum();
//				Row lastRow = sheet.getRow(lastRowIndex);
//
//				if (lastRow != null) {
//					CellStyle existingStyle = lastRow.getCell(0).getCellStyle();
//					CellStyle cellStyle = workbook.createCellStyle();
//					cellStyle.cloneStyleFrom(existingStyle);
//					cellStyle.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
//					cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
//
//					for (Cell cell : lastRow) {
//						cell.setCellStyle(cellStyle);
//					}
//
//					try (FileOutputStream fos = new FileOutputStream(getWorkbookPath_ResultsOfTesting())) {
//						workbook.write(fos);
//					}
//				} else {
//					log.info("Last row not found in sheet: " + getSheetName());
//				}
//			} else {
//				log.info("Sheet not found: " + getSheetName());
//			}
//
//		} catch (Exception ex) {
//			ex.printStackTrace();
//		}
//	}
//
//	/**
//	 * This method writes the value to the first column cell in the Results Sheet
//	 * 
//	 * @param workbookPath : workbookPath_ResultsOfTesting /
//	 *                     workbookPath_AccountsForTesting to be provided where the
//	 *                     path is already declared in Test Base
//	 * @param sheetName    : This is the sheet name of the workbook in which the
//	 *                     value is to be written
//	 * @param columnName   : The columnNo will be considered based on the provided
//	 *                     columnName. First column will be considered if value null
//	 *                     is passed
//	 * @param cellValue    : The string value to be written
//	 */
//	public void writeToSingleCell(String workbookPath, String sheetName, String columnName, String cellValue) {
//		try {
//			int rowNo = getLastRowNumber(workbookPath, sheetName);
//			rowNo++;
//			int columnNo = getColumnNumber(workbookPath, sheetName, columnName);
////			log.info(rowNo + " : " + columnNo);
//			excelUtility.writeCellValue(workbookPath, sheetName, rowNo, columnNo, cellValue);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	/**
//	 * This method writes the value to the required cell in the Accounts Sheet
//	 * 
//	 * @param sheetName
//	 * @param columnName
//	 * @param rowName
//	 * @param cellValue
//	 */
//	public void writeToCellInAccountsSheet(String sheetName, String columnName, String rowName, String cellValue) {
//		try {
//			String workbookPath = getWorkbookPath_AccountsForTesting();
//			int rowNo = getRowNumber(workbookPath, sheetName, rowName);
//			int columnNo = getColumnNumber(workbookPath, sheetName, columnName);
//			log.info(rowNo + " : " + columnNo);
//			writeToSingleCell(workbookPath, sheetName, rowNo, columnNo, cellValue);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	/**
//	 * This method provides the complete sheet data with added rows & columns with
//	 * sheetname,workbookpath and identifier
//	 * 
//	 * @param workbookPath   this is the workbook inclusive of sheetname withtest
//	 *                       cases
//	 * @param sheetName      this is the Module used for executing the test cases
//	 *                       related to that
//	 * @param dataIdentifier
//	 * @return Userdata
//	 * @throws IndexOutOfBoundsException
//	 * @throws FileNotFoundException
//	 */

	public UserCredentials getCompleteSheetData(String workbookPath, String sheetName, String dataIdentifier)
			throws IndexOutOfBoundsException, FileNotFoundException {

		List<String> heading = new ArrayList<String>();
		List<String> getRowDataList = new ArrayList<String>();

		log.info("Connecting to the workbook at: " + workbookPath);
		File file = new File(workbookPath);
		String excelCellValue = null;
		int sheetIndex = 0;
		int totalRowCount = 0;
		int totalColumnCount = 0;
		int identifierRowLocation = 0;
		Sheet sheet = null;

		// Access the workbook to be used.
		try {
			this.workbook = WorkbookFactory.create(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		sheetIndex = workbook.getSheetIndex(sheetName);
//		log.info("Sheet index = " + sheetIndex);

		// Access the sheet in the workbook to be used.
		sheet = this.workbook.getSheetAt(sheetIndex);
//		log.info("sheet :>>>"+sheet);

		totalRowCount = sheet.getLastRowNum() + 1;
//		log.info("totalRowCount :>>>"+totalRowCount);
//		totalColumnCount = sheet.getRow(identifierRowLocation).getPhysicalNumberOfCells();
		totalColumnCount = sheet.getRow(identifierRowLocation).getLastCellNum();
//		log.info("totalColumnCount :>>>>"+totalColumnCount);

		// Iterate over the cells
		int actualColumnCount = 0;
		for (int currentColumn = 0; currentColumn < totalColumnCount; currentColumn++) {
			Cell cell = sheet.getRow(identifierRowLocation).getCell(currentColumn);

			// Check for null and empty cell before using the cell value
			if (cell != null && !cell.getStringCellValue().isEmpty()) {
				// Access the cell value
//		    	log.info(currentColumn + " : " + cell);
				actualColumnCount++;
			}
		}
		totalColumnCount = actualColumnCount;

		// Clearing the list of it is already having data
//		if(heading.size()!=0 || getRowDataList.size()!=0) {
//			heading.clear();
//			getRowDataList.clear();
//		}

		// Iterate to find the heading values.
		excelCellValue = sheet.getRow(0).getCell(0).toString().toLowerCase();
		// check if the cellvalue passed is equal to the excel Cell Value.
		if (excelCellValue.equalsIgnoreCase(sheet.getRow(0).getCell(0).toString().toLowerCase())) {
			// Get the values in each cell of the data identifier row.
			for (int column = 0; column < totalColumnCount; column++) {
//				System.out.print(getCellData(sheet, identifierRowLocation, column));
//				heading.add(excelUtility.getCellData(sheet, identifierRowLocation, column).trim());
//				heading.add(excelUtility.getCellData(sheet, identifierRowLocation, column).trim());
				heading.add(excelUtility
						.getCellData(getWorkbookPath_AccountsForTesting(), sheetName, identifierRowLocation, column)
						.trim());
			}
		}

		// Iterate through the total number of rows to find the row data values
		for (int rowNo = 0; rowNo < totalRowCount; rowNo++) {
			excelCellValue = sheet.getRow(rowNo).getCell(0).toString().toLowerCase();
			// check if the cellvalue passed is equal to the excel Cell Value.
			if (excelCellValue.equalsIgnoreCase(dataIdentifier.toLowerCase())) {
				identifierRowLocation = rowNo;
				log.info("Identifier row num from sheet is: "+identifierRowLocation);
				// Get the values in each cell of the data identifier row.
				for (int column = 0; column < totalColumnCount; column++) {
//					System.out.print(excelUtility.getCellData(sheet, identifierRowLocation, column));
//					getRowDataList.add(excelUtility.getCellData(sheet, identifierRowLocation, column));
//					getRowDataList.add(excelUtility.getCellData(sheet, identifierRowLocation, column));
					getRowDataList.add(excelUtility.getCellData(getWorkbookPath_AccountsForTesting(), sheetName,
							identifierRowLocation, column));
				}
				break;
			}
		}

		for (int i = 0; i < totalColumnCount; i++) {
			String columnHeading = heading.get(i).toLowerCase();
			ColumnHeadingValues columnHeadingValues = ColumnHeadingValues.valueOf(columnHeading);
			switch (columnHeadingValues) {
			case testcase:
				userData.testCase = getRowDataList.get(i).toString();
				break;
			case firstname:
				userData.firstName = getRowDataList.get(i).toString();
				break;
			case lastname:
				userData.lastName = getRowDataList.get(i).toString();
				break;
			case email:
				userData.emailID = getRowDataList.get(i).toString();
				break;
			case password:
				userData.password = getRowDataList.get(i).toString();
				break;
			case passwordtoupdate:
				userData.passwordToUpdate = getRowDataList.get(i).toString();
			case phonenumber:
				userData.phoneNo = getRowDataList.get(i).toString();
				break;
			case firstnametoupdate:
				userData.firstNameToUpdate = getRowDataList.get(i).toString();
				break;
			case lastnametoupdate:
				userData.lastNameToUpdate = getRowDataList.get(i).toString();
				break;
			case phonenotoupdate:
				userData.phoneNoToUpdate = getRowDataList.get(i).toString();
				break;
			case companyname:
				userData.companyName = getRowDataList.get(i).toString();
				break;
			case companynametoupdate:
				userData.companyNameToUpdate = getRowDataList.get(i).toString();
				break;
			case emailaddress:
				userData.emailAddress = getRowDataList.get(i).toString();
				break;
			case showpassword:
				userData.showPassword = getRowDataList.get(i).toString();
				break;
			case hidepassword:
				userData.hidePassword = getRowDataList.get(i).toString();
				break;
			case cc:
				userData.ccNo = getRowDataList.get(i).toString();
				break;
			case testcc:
				userData.testCC = getRowDataList.get(i).toString();
				break;
			case mm:
				userData.mm = getRowDataList.get(i).toString();
				break;
			case cvv:
				userData.cvv = getRowDataList.get(i).toString();
				break;
			case yy:
				userData.yy = getRowDataList.get(i).toString();
				break;
			case billingaddress:
				userData.billingAddress = getRowDataList.get(i).toString();
				break;
			case zipcode:
				userData.zipcode = getRowDataList.get(i).toString();
				break;
			case state:
				userData.state = getRowDataList.get(i).toString();
				break;
			case country:
				userData.country = getRowDataList.get(i).toString();
				break;
			case reasonforcancellation:
				userData.reasonForCancellation = getRowDataList.get(i).toString();
				break;
			case subuseremail:
				userData.subUserEmail = getRowDataList.get(i).toString();
				break;
			case usertobeedited:
				userData.userToBeEdited = getRowDataList.get(i).toString();
				break;
			case usertobedisabled:
				userData.userToBeDisabled = getRowDataList.get(i).toString();
				break;
			case usertobeenabled:
				userData.userToBeEnabled = getRowDataList.get(i).toString();
				break;
			case usertoberesetpassword:
				userData.userToBeResetPassword = getRowDataList.get(i).toString();
				break;
			case usertoberesendmail:
				userData.userToBeResendMail = getRowDataList.get(i).toString();
				break;
			case role:
				userData.role = getRowDataList.get(i).toString();
				break;
			case usertobedeleted:
				userData.userToBeDeleted = getRowDataList.get(i).toString();
				break;
			case adminemail:
				userData.adminEmail = getRowDataList.get(i).toString();
				break;
			case editedcompanyname:
				userData.editedCompanyName = getRowDataList.get(i).toString();
				break;
			case parentcompanyname:
				userData.parentCompanyName = getRowDataList.get(i).toString();
				break;
			case companybottom:
				userData.companyBottom = getRowDataList.get(i).toString();
				break;
			case companyheader:
				userData.companyHeader = getRowDataList.get(i).toString();
				break;
			case ruletobeadded:
				userData.ruleToBeAdded = getRowDataList.get(i).toString();
				break;
			case backupplanname:
				userData.backupPlanName = getRowDataList.get(i).toString();
				break;
			}
		}
		try {
			workbook.close();
			log.info("Workbook closed");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return userData;

	}

	/**
	 * This takes the last rownumber of all the sheets added for the sheet range to
	 * the execution of the test cases
	 * 
	 * @param workbookPath
	 * @param sheetName
	 * @return lastrownumber
	 */

	public int getLastRowNumber(String workbookPath, String sheetName) {

		int lastRowNumber = -1;

		// try with resources, so that these resources are closed after execution of try
		// block
		try (FileInputStream fis = new FileInputStream(workbookPath); Workbook workbook = WorkbookFactory.create(fis)) {

			Sheet sheet = workbook.getSheet(sheetName);

			if (sheet != null) {
				lastRowNumber = sheet.getLastRowNum();
			} else {
				log.info("Sheet not found: " + sheetName);
			}
		} catch (IOException | EncryptedDocumentException ex) {
			ex.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lastRowNumber;
	}

	/**
	 * This takes the columnnumber of all the sheets added for the sheet range to
	 * the execution of the test cases
	 * 
	 * @param workbookPath
	 * @param sheetName
	 * @return columnnumber
	 */
	public int getColumnNumber(String workbookPath, String sheetName, String columnName) {
		int columnNumber = -1;

		if (columnName != null) {
			try (FileInputStream fis = new FileInputStream(workbookPath);
					Workbook workbook = WorkbookFactory.create(fis)) {

				Sheet sheet = workbook.getSheet(sheetName);

				if (sheet != null) {
					Row headerRow = sheet.getRow(0); // Assuming the header is in the first row

					if (headerRow != null) {
						for (int i = 0; i < headerRow.getLastCellNum(); i++) {
							Cell cell = headerRow.getCell(i);
							if (cell != null && cell.getStringCellValue().equalsIgnoreCase(columnName)) {
								columnNumber = i;
								break;
							}
						}
					} else {
						log.info("Header row not found.");
					}
				} else {
					log.info("Sheet not found: " + sheetName);
				}

			} catch (IOException | EncryptedDocumentException ex) {
				ex.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}

			return columnNumber;
		} else {
			return 0;
		}
	}

	/**
	 * This takes the rownumber of all the sheets added for the sheet range to the
	 * execution of the test cases
	 * 
	 * @param workbookPath
	 * @param sheetName
	 * @return rownumber
	 */

	public int getRowNumber(String workbookPath, String sheetName, String rowName) {
		try (FileInputStream fis = new FileInputStream(workbookPath); Workbook workbook = new XSSFWorkbook(fis)) {
			Sheet sheet = workbook.getSheet(sheetName);
			if (sheet == null) {
				throw new IllegalArgumentException("Sheet " + sheetName + " does not exist in the workbook");
			}

			for (Row row : sheet) {
				for (Cell cell : row) {
					if (cell.getCellType() == CellType.STRING && cell.getStringCellValue().equals(rowName)) {
						return row.getRowNum();
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		log.info("Row name " + rowName + " not found");
		return -1; // If rowName is not found
	}

	/**
	 * This method writes the result /exception passed in the scripts in the
	 * particular test case cell
	 * 
	 * @param workbookPath
	 * @param sheetName
	 * @param rowNo
	 * @param columnNo
	 * @param cellValue
	 * @throws Exception
	 */

	public void writeToSingleCell(String workbookPath, String sheetName, int rowNo, int columnNo, String cellValue)
			throws Exception {
		log.info("Updating the cell value in the workbook : " + workbookPath + " in the sheet: " + sheetName
				+ " the value: " + cellValue);

		// Creating file object of existing excel file
		File xlsxFile = new File(workbookPath);

		try (FileInputStream inputStream = new FileInputStream(xlsxFile);
				Workbook workbook = WorkbookFactory.create(inputStream)) {

			// Get the sheet, create it if it doesn't exist
			Sheet sheet = workbook.getSheet(sheetName);
			if (sheet == null) {
				sheet = workbook.createSheet(sheetName);
			}

			// Get the row, create it if it doesn't exist
			Row row = sheet.getRow(rowNo);
			if (row == null) {
				row = sheet.createRow(rowNo);
			}

			// Get the cell, create it if it doesn't exist
			Cell cell = row.getCell(columnNo);
			if (cell == null) {
				cell = row.createCell(columnNo);
			}

			// Set the cell value
			cell.setCellValue(cellValue);

			// Close input stream
			inputStream.close();

			// Creating output stream and writing the updated workbook
			try (FileOutputStream os = new FileOutputStream(xlsxFile)) {
				workbook.write(os);
			}

			log.info("Updated the cell value successfully.");
		} catch (Exception e) {
			throw e;
		}
	}

}
