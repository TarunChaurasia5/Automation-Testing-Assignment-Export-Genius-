package generic_utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class FileUtility {
	public String getDataFromPropertiesFile(String key) throws IOException {
		FileInputStream fis = new FileInputStream("./src/test/resources/commondata.properties");
		Properties pObj = new Properties();
		pObj.load(fis);

		String value = pObj.getProperty(key);
		return value;
	}

	public String getDataFromExcelFile(String sheetName, int rowNum, int cellNum)
			throws EncryptedDocumentException, IOException {

		FileInputStream fis2 = new FileInputStream("./src/test/resources/TEST_DATA.xlsx");
		Workbook wb = WorkbookFactory.create(fis2);
		Sheet sh = wb.getSheet(sheetName);
		String value = sh.getRow(rowNum).getCell(cellNum).getStringCellValue();
		return value;
	}
	
	public String getDataFromExcelFile(int rowNum, int cellNum, String sheetName  )
			throws EncryptedDocumentException, IOException {

		FileInputStream fis2 = new FileInputStream("./src/test/resources/TEST_DATA.xlsx");
		Workbook wb = WorkbookFactory.create(fis2);
		Sheet sh = wb.getSheet(sheetName);
		DataFormatter df = new DataFormatter();
		 String date = df.formatCellValue(sh.getRow(rowNum).getCell(cellNum));
		//Number value = sh.getRow(rowNum).getCell(cellNum).getNumericCellValue();
		return date;
	}
}