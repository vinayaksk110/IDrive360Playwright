package idrive360.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtilityNew {
	Workbook workbook;
	int totalSheetCount = 0;
	int sheetIndex = -1;
	public int rowCount = 0;
	boolean sheetStatus = false;
	public static Logger log = LogManager.getLogger(ExcelUtilityNew.class);

	/**
	 * Verifies if the sheet is present is a particular workbook
	 * 
	 * @param workbook
	 * @param workbookPath     : Path of the workbook
	 * @param sheetNameToCheck : Name of the sheet to be verified if it exists.
	 * @return returns a true or false value based on if the sheet exists.
	 */
	private boolean verifyIfSheetIsPresent(Workbook workbook, String workbookPath, String sheetNameToCheck) {
		try {
			// Creating file object of existing excel file
			File xlsxFile = new File(workbookPath);
			// Creating input stream to the existing file.
			FileInputStream inputStream = new FileInputStream(xlsxFile);

			// Creating workbook from input stream
			workbook = WorkbookFactory.create(inputStream);
			// Iterate through all the sheets in the workbook
			for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
				String sheetName = workbook.getSheetName(i);
				if (sheetName.equalsIgnoreCase(sheetNameToCheck)) {
//					log.info("Sheet exists: " + sheetNameToCheck);
					sheetStatus = true;
					inputStream.close();
					break;
				} else {
					// If the loop completes without finding the sheet, it means the sheet doesn't
					// exist
					sheetStatus = false;
//					log.info("Sheet does not exist: " + sheetNameToCheck);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return sheetStatus;
	}

	/**
	 * Returns the row count of the sheet from a workbook
	 * 
	 * @param workbookPath
	 * @param sheetIndex
	 * @return: returns the row count in the integer format
	 */
	private int getRowCount(String workbookPath, int sheetIndex) {

		try {
			workbook = new XSSFWorkbook(workbookPath);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Sheet sheet = workbook.getSheetAt(sheetIndex);
		return (rowCount = sheet.getPhysicalNumberOfRows());

	}

	/**
	 * Method returns the sheet index of the sheet from the workbook
	 * 
	 * @param workbookPath : Identifies the path of the workbook
	 * @param sheetName    : name of the sheet for which index has to be retrieved.
	 * @return : index of the sheet in the integer format.
	 */
	public int getSheetIndex(String workbookPath, String sheetName) {
		// Creating file object of existing excel file
		File xlsxFile = new File(workbookPath);
		try {
			// Creating input stream
			FileInputStream inputStream = new FileInputStream(xlsxFile);

			// Creating workbook from input stream
			Workbook workbook = WorkbookFactory.create(inputStream);
			sheetIndex = workbook.getSheetIndex(sheetName);
			inputStream.close();
		} catch (Exception e) {
			sheetIndex = -1;
			e.printStackTrace();
		}
		return sheetIndex;
	}

	/**
	 * This method provides the count of the sheets present in a workbook.
	 * 
	 * @param pathOfWorkbook : This is the path of the workbook from which the sheet
	 *                       count has to be calculated.
	 * @return : is the total number of sheets in a workbook as a integer.
	 */
	private int getTotalSheetCount(String pathOfWorkbook) {
		try {
			FileInputStream file = new FileInputStream(new File(pathOfWorkbook));
			workbook = new XSSFWorkbook(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return (totalSheetCount = workbook.getNumberOfSheets());
	}

	/**
	 * This method adds a sheet into the workbook
	 * 
	 * @param workbookPath : This is the path of the workbook where sheet is to be
	 *                     added.
	 * @param sheetName    : This is the name of the sheet which has to be added.
	 */
	private void addSheetIntoWorkbook(Workbook workbook, String workbookPath, String sheetName) {
		File f = new File(workbookPath);
		try {
			if (f.exists()) {
				try {
					if (!(verifyIfSheetIsPresent(workbook, workbookPath, sheetName))) {

						// Create a new sheet
						Sheet newSheet = workbook.createSheet(sheetName);
						log.info("Created the sheet as it did not exists." + sheetName);
						workbook.setSheetOrder(newSheet.getSheetName(), 0);
						FileOutputStream outFile = new FileOutputStream(workbookPath);
						workbook.write(outFile);
						log.info("Sheet has been created");
						workbook.close();
						outFile.close();
					}
				} catch (IllegalArgumentException iae) {
					log.error("Sheet already exists, not adding. Proceeding with update");
				} catch (Exception e) {
					log.error("Failed to write to the sheet");
					e.printStackTrace();
				}
			} else {
				log.info("The file provided to add sheet doesnot exists.");
			}

		} catch (IllegalArgumentException iae) {
			log.error("The sheet being created already exists");
			iae.printStackTrace();
		} catch (Exception e) {
			log.error("General exception");
			e.printStackTrace();
		}
	}

	/**
	 * The method accepts the file path and the sheetname and writes the data to
	 * this sheet.
	 * 
	 * @param workbookPath          : This is the physical path of where the file is
	 *                              located.
	 * @param sheetName             : This is the sheet name in which the data
	 *                              should be written.
	 * @param testCase              : This is the name of the test case being
	 *                              executed.
	 * @param testResult            : This is the result (pass / fail) of the test
	 *                              case being executed.
	 * @param testResultComment     : This indicates why a test result is classified
	 *                              as passed or failed.
	 * @param executionTime         : Total time taken by the script to identify
	 *                              passed or failed scenario.
	 * @param projectThreshHoldTime : Time that identifies how long should the
	 *                              script execution takes.
	 * @throws Exception
	 */
	public void writeResultsToSheet(String workbookPath, String sheetName, String testCase, String testResult,
			String testResultComment, int executionTime, int projectThreshHoldTime) throws Exception {
		log.info("\n\nUpdating Results");
		// Creating file object of existing excel file
		File xlsxFile = new File(workbookPath);
		String sheetname = sheetName;

		// New row data
		Object[][] rowData = { { testCase, testResult, testResultComment, executionTime } };

		try {
			// Creating input stream to the existing file.
			FileInputStream inputStream = new FileInputStream(xlsxFile);

			// Creating workbook from input stream
			workbook = WorkbookFactory.create(inputStream);

			// Check if the sheet is present. If not add the sheet.
			if (!(verifyIfSheetIsPresent(workbook, workbookPath, sheetname))) {

				// Add the sheet
				addSheetIntoWorkbook(workbook, workbookPath, sheetname);

				// Reload the file with new sheet again, so that references are updated.
				inputStream = new FileInputStream(xlsxFile);

				// Creating workbook from input stream
				workbook = WorkbookFactory.create(inputStream);
			}

			// Get the index of the sheet where the data has to be written.
			int sheetIndex = getSheetIndex(workbookPath, sheetname);
			if (sheetIndex >= 0) {
				Sheet sheet = workbook.getSheetAt(sheetIndex);

				// Getting the count of existing records
				int rowCount = sheet.getPhysicalNumberOfRows();
				
				// Iterating new students to update
				for (Object[] cellData : rowData) {

					// Creating new row from the next row count
					Row row = sheet.createRow(rowCount);

					int columnCount = 0;

					// Iterating student informations
					for (Object info : cellData) {
						// Creating new cell and setting the value
						Cell cell = row.createCell(columnCount++);
						if (info instanceof String) {
							cell.setCellValue((String) info);
						} else if (info instanceof Integer) {
							cell.setCellValue((Integer) info);
							if (cell.getColumnIndex() == 3) {
								if ((executionTime > projectThreshHoldTime)) {
									changeCellBackgroundColor(cell, "red");
								} else {
									changeCellBackgroundColor(cell, "green");
								}
							}
						}
					}
				}
				// Close input stream
				inputStream.close();

				// Crating output stream and writing the updated workbook
				FileOutputStream os = new FileOutputStream(xlsxFile);
				workbook.write(os);

				// Close the workbook and output stream
				workbook.close();
				os.close();

				log.info("Excel file has been updated successfully.");
			} else {
				System.out
						.println("Excel file could not be updated as the sheet you are trying to use is not present.");
			}

		} catch (Exception e) {
			throw e;
		}

	}

	/**
	 * The method adds the standard heading to the sheet provided in the workbook
	 * path.
	 * 
	 * @param workbookPath : Identifies the path of the workbook on the local disk.
	 * @param sheetName    : Identifies the sheet in which the heading has to be
	 *                     added.
	 * @throws Exception
	 */
	public void writeHeadingToSheet(String workbookPath, String sheetName) throws Exception {
		log.info("Updating the heading");
		// Creating file object of existing excel file
		File xlsxFile = new File(workbookPath);

		// New row data
		Object[][] rowData = { { "Test Case", "Test Result", "Test Result Comment", "Execution Time" } };

		try {
			// Creating input stream to the existing file.
			FileInputStream inputStream = new FileInputStream(xlsxFile);

			// Creating workbook from input stream
			workbook = WorkbookFactory.create(inputStream);

			// Check if the sheet is present. If not add the sheet.
			if (!(verifyIfSheetIsPresent(workbook, workbookPath, sheetName))) {

				// Add the sheet
				addSheetIntoWorkbook(workbook, workbookPath, sheetName);

				// Reload the file with new sheet again, so that references are updated.
				inputStream = new FileInputStream(xlsxFile);

				// Creating workbook from input stream
				workbook = WorkbookFactory.create(inputStream);
			}

			// Get the index of the sheet where the data has to be written.
			int sheetIndex = getSheetIndex(workbookPath, sheetName);
			if (sheetIndex >= 0) {
				Sheet sheet = workbook.getSheetAt(sheetIndex);

				// Getting the count of existing records
				int rowCount = sheet.getPhysicalNumberOfRows();

				// Iterating new students to update
				for (Object[] cellData : rowData) {

					// Creating new row from the next row count
					Row row = sheet.createRow(rowCount);

					int columnCount = 0;

					// Iterating student informations
					for (Object info : cellData) {

						// Creating new cell and setting the value
						Cell cell = row.createCell(columnCount++);
						if (info instanceof String) {
							cell.setCellValue((String) info);
						} else if (info instanceof Integer) {
							cell.setCellValue((Integer) info);
						}
						makeRowBold(workbook, row);
						changeRowBackgroundColor(cell);
					}
				}
				// Close input stream
				inputStream.close();

				// Crating output stream and writing the updated workbook
				FileOutputStream os = new FileOutputStream(xlsxFile);
				workbook.write(os);

				// Close the workbook and output stream
				workbook.close();
				os.close();

				log.info("Excel file has been updated successfully.");
			} else {
				System.out
						.println("Excel file could not be updated as the sheet you are trying to use is not present.");
			}

		} catch (Exception e) {
			throw e;
		}

	}

	/**
	 * This method make a row completely bold
	 * 
	 * @param workbook : This is the workbook where the row has to be made bold
	 * @param row      : This is the row which has to be made bold.
	 */
	public void makeRowBold(Workbook workbook, Row row) {
		CellStyle style = workbook.createCellStyle();// Create style
		Font font = workbook.createFont();// Create font
		font.setBold(true);// Make font bold
		style.setFont(font);// set it to bold

		for (int i = 0; i < row.getLastCellNum(); i++) {
			// For each cell in the row
			row.getCell(i).setCellStyle(style);// Set the style
		}
	}

	/**
	 * This method changes the background color of the cell to either red or green.
	 * 
	 * @param cell  : This is the cell for which background color needs to be
	 *              changed.
	 * @param color : This take the color red or green, as values.
	 */
	private void changeCellBackgroundColor(Cell cell, String color) {
		CellStyle cellStyle = cell.getSheet().getWorkbook().createCellStyle();
		if (color.equalsIgnoreCase("green")) {
			cellStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
		} else if (color.equalsIgnoreCase("red")) {
			cellStyle.setFillForegroundColor(IndexedColors.LIGHT_ORANGE.getIndex());
		} else {
			cellStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
		}
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cell.setCellStyle(cellStyle);

	}

	/**
	 * This method makes the entire row color to blue.
	 * 
	 * @param cell
	 */
	private void changeRowBackgroundColor(Cell cell) {
		CellStyle cellStyle = cell.getCellStyle();
		if (cellStyle == null) {
			cellStyle = cell.getSheet().getWorkbook().createCellStyle();
		}
		cellStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cell.setCellStyle(cellStyle);

	}

	/**
	 * This method writes the value in a cell of a particular sheet in the workbook
	 * using row and column numbers
	 * 
	 * @param workbookPath : This is the path of the file on the local machine
	 * @param sheetName    : This is the sheetname of the workbook in which the
	 *                     value is to be updated.
	 * @param rowNo        : This is the rowNo where we can find the cell to be
	 *                     updated
	 * @param columNo      : This is the columNo where we can find the cell to be
	 *                     updated.
	 * @param cellValue    : String value that needs to be updated in the cell.
	 * @throws Exception
	 */
	public void writeCellValue(String workbookPath, String sheetName, int rowNo, int columNo, String cellValue)
			throws Exception {
		log.info("\n\nUpdating the cell value in the workbook : " + workbookPath + " in the sheet:"
				+ sheetName + " the value: " + cellValue);
		// Creating file object of existing excel file
		File xlsxFile = new File(workbookPath);

		try {
			// Creating input stream to the existing file.
			FileInputStream inputStream = new FileInputStream(xlsxFile);

			// Creating workbook from input stream
			workbook = WorkbookFactory.create(inputStream);

			// Check if the sheet is present. If not add the sheet.
			if (verifyIfSheetIsPresent(workbook, workbookPath, sheetName)) {
				log.info("The sheet is present..");
				// Get the index of the sheet where the data has to be written.
				int sheetIndex = getSheetIndex(workbookPath, sheetName);
				if (sheetIndex >= 0) {
					Sheet sheet = workbook.getSheetAt(sheetIndex);
					sheet.createRow(rowNo);
					sheet.getRow(rowNo).createCell(columNo).setCellValue(cellValue);
					log.info("Updated the cell value");
					// Close input stream
					inputStream.close();
				}

				// Crating output stream and writing the updated workbook
				FileOutputStream os = new FileOutputStream(xlsxFile);
				workbook.write(os);

				// Close the workbook and output stream
				workbook.close();
				os.close();

//				log.info("Excel file has been updated successfully.");
			} else {
				System.out
						.println("Excel file could not be updated as the sheet you are trying to use is not present.");
			}

		} catch (Exception e) {
			throw e;
		}

	}

	/**
	 * This method reads the data from the workbook based on the decided
	 * rowIdentifier.
	 * 
	 * @param workbookPath  : This is the path of the file on the local machine
	 * @param sheetName     : This is the sheetname of the workbook from which the
	 *                      value is to be read.
	 * @param rowIdentifierValue : This is the column value of the row, which identifies
	 *                      the row from which the data has to be read.
	 */
	public void getRowData(String workbookPath, String sheetName, String rowIdentifierValue) {
		int sheetIndex = 0;
		Sheet sheet = null;
		int totalRowCount = 0;
		int totalColumnCount = 0;
		Row row = null;
		Cell cell = null;
		int identifierRowLocation = 0;

		log.info("Connecting to the workbook at: " + workbookPath);

		File file = new File(workbookPath);

		// Access the workbook to be used.
		try {
			this.workbook = WorkbookFactory.create(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		sheetIndex = getSheetIndex(workbookPath, sheetName);

		// Access the sheet in the workbook to be useed.
		sheet = this.workbook.getSheetAt(sheetIndex);

		totalRowCount = getRowCount(workbookPath, sheetIndex);

		totalColumnCount = 0;

		// Iterate thru the total number of rows to find the row position of the row
		// identifier.
		for (int rowNo = 0; rowNo < totalRowCount; rowNo++) {
			String excelCellValue = sheet.getRow(rowNo).getCell(0).toString().toLowerCase();
			// check if the cellvalue passed is equal to the excel Cell Value.
			if (excelCellValue.equalsIgnoreCase(rowIdentifierValue.toLowerCase())) {
				identifierRowLocation = rowNo;
				
				// Get the total number of columns in the data identifier row.
				totalColumnCount = sheet.getRow(identifierRowLocation).getPhysicalNumberOfCells();

				// Get the values in each cell of the data identifier row.
				for (int column = 0; column < totalColumnCount; column++) {
					System.out.print(getCellData(sheet, identifierRowLocation, column));
//					System.out.print(getCellData(workbookPath, sheetName, identifierRowLocation, column));
				}
				
				break;
			} else {
				log.info("The value is not found in the sheet.");
			}
		}

		
	}
	
	/**
	 * This method is a private method used only in the getRowData
	 * @param sheetName : Identifies the sheetname current from where the data has to come
	 * @param row : The Row where the data is present.
	 * @param column : The column where the data is present.
	 * @return : is a string that is found in the cell which matches the row and column provided.
	 */
	private String getCellData(Sheet sheetName, int row, int column) {
		return(sheetName.getRow(row).getCell(column).toString());
	}

	/**
	 * This method returns a string value from the cell which matches the workbook path, sheetname, row and column provided.
	 * @param workbookPath : The local system path, where the workbook is present. 
	 * @param sheetName : This the sheetname from which the data has to be read.
	 * @param row : The row where the data is present.
	 * @param column : The column where the data is present. 
	 * @return is a string that is found in the cell which matches the row and column provided.
	 */
	public String getCellData(String workbookPath, String sheetName, int row, int column) {
		File file = new File(workbookPath);
		// Access the workbook to be used.
		try {
			this.workbook = WorkbookFactory.create(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		sheetIndex = getSheetIndex(workbookPath, sheetName);

		// Access the sheet in the workbook to be useed.
		Sheet sheet = this.workbook.getSheetAt(sheetIndex);

		return (sheet.getRow(row).getCell(column).toString());
	}
	
	/**
	 * This method returns the cell reference of the string which is to be found.
	 * @param workbookPath : The local system path where the data is present.
	 * @param sheetName : The sheetname from which the data is to be read.
	 * @param toFind : This is the string value that is to be found in the sheet.
	 * @return : Returns the cell reference of the string found.
	 */
	public String getCellReference(String workbookPath, String sheetName, String toFind) {

		Workbook wb = null;
		String cellLocation = null;
		try {
			wb = WorkbookFactory.create(new File(workbookPath));
		} catch (EncryptedDocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		int sheetIndex = getSheetIndex(workbookPath, sheetName);
		DataFormatter formatter = new DataFormatter();
		Sheet sheet1 = wb.getSheetAt(0);
		for (Row row : sheet1) {
		    for (Cell cell : row) {
		        CellReference cellRef = new CellReference(row.getRowNum(), cell.getColumnIndex());

		        // get the text that appears in the cell by getting the cell value and applying any data formats (Date, 0.00, 1.23e9, $1.23, etc)
		        String text = formatter.formatCellValue(cell);

		        // is it an exact match?
		        if (toFind.equals(text)) {
		           log.info("Text matched at " + cellRef.formatAsString());
		           cellLocation = cellRef.formatAsString();
		        }
		        // is it a partial match?
		        else if (text.contains(toFind)) {
		           log.info("Text found as part of " + cellRef.formatAsString());
		          // cellLocation = cellRef.formatAsString();
		        }
		    }
		}
		return cellLocation;
	}

}
