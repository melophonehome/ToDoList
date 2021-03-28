package utils;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ExcelUtils {

	WebDriver driver;
	Logger log = Logger.getLogger("ExcelUtils");

	public ExcelUtils(WebDriver driver) {
		this.driver = driver;
	}

	public String getValuesfromDataFiles(String dataFile, String sheetName, int row, int cell)
			throws IOException, Exception {
		String projectPath = System.getProperty("user.dir");
		// ============ Linked to the data file =================== //
		File file = new File(projectPath + "\\src\\test\\java\\com\\datas\\" + dataFile);
		// ============ Create an object to read the data file =========== //
		FileInputStream testData = new FileInputStream(file);
		// ============ Create a workbook object related to the .xlsx file ========== //
		XSSFWorkbook workbook = new XSSFWorkbook(testData);
		// ============ Locate the sheet name ============= //
		XSSFSheet sheet = workbook.getSheet(sheetName);
		// ============ Locate the row to verify in that sheet =========== //
		XSSFRow Row = sheet.getRow(row);
		// ============ Locate the specific cell in the row ============= //
		XSSFCell Cell = Row.getCell(cell);
		log.info("Value is in: " + dataFile + "  | Sheet: " + sheetName + "  | Row: " + row + " | Cell: " + cell);
		// ============ Get value of specific cell ========== //
		String Value = Cell.getStringCellValue();
		Thread.sleep(2000);
		log.info("Data value is: " + Value);
		return Value;

	}

}
