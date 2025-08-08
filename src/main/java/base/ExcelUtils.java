package base;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
public class ExcelUtils {

	 public static Object[][] readExcelData(String filePath, String sheetName) throws IOException {
	        FileInputStream fileInputStream = new FileInputStream(filePath);
	        Workbook workbook = new XSSFWorkbook(fileInputStream);
	        Sheet sheet = workbook.getSheet(sheetName);
	        int rowCount = sheet.getLastRowNum();
	        int columnCount = sheet.getRow(0).getLastCellNum();
	        Object[][] data = new Object[rowCount][columnCount];

	        for (int i = 0; i < rowCount; i++) {
	            Row row = sheet.getRow(i + 1);
	            for (int j = 0; j < columnCount; j++) {
	                Cell cell = row.getCell(j);
	                data[i][j] = cell.toString();
	            }
	        }

	        workbook.close();
	        fileInputStream.close();
	        return data;
	    }
}
