package base;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelUtilsTwo {

    /**
     * Finds the column index for a given date in the header row.
     * @param headerRow The header row of the Excel sheet.
     * @param desiredDate The date to find in the header row.
     * @return The column index of the desired date, or -1 if not found.
     */
    public static int findDateColumnIndex(Row headerRow, String desiredDate) {
        for (Cell cell : headerRow) {
            if (cell.getCellType() == CellType.STRING && cell.getStringCellValue().contains(desiredDate)) {
                return cell.getColumnIndex();
            }
        }
        return -1; // Date not found
    }

    /**
     * Extracts costs data for a specified date from an Excel sheet.
     * @param filePath The path to the Excel file.
     * @param desiredDate The date for which to extract costs data.
     * @return A map with category names and their corresponding costs for the specified date.
     * @throws IOException If there is an error reading the file.
     */
    
    
    public static Map<String, String> getJobTypeAndTotalTips(String filePath) throws IOException {
        Map<String, String> jobTypeTipsMap = new HashMap<>();

        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0); 
            Row headerRow = sheet.getRow(0);       

           
            int jobTypeColumnIndex = -1;
            int totalTipsColumnIndex = -1;

            for (int cellIndex = 0; cellIndex < headerRow.getLastCellNum(); cellIndex++) {
                Cell cell = headerRow.getCell(cellIndex);
                if (cell != null) {
                    String cellValue = cell.getStringCellValue().trim();
                    if (cellValue.equalsIgnoreCase("Job Type")) {
                        jobTypeColumnIndex = cellIndex;
                    } else if (cellValue.equalsIgnoreCase("Total Tips")) {
                        totalTipsColumnIndex = cellIndex;
                    }
                }
            }

            if (jobTypeColumnIndex == -1) {
                throw new IllegalArgumentException("Job Type column not found.");
            }

            if (totalTipsColumnIndex == -1) {
                throw new IllegalArgumentException("Total Tips column not found.");
            }

            // Iterate through the rows to get the Job Type and Total Tips values
            for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                Row row = sheet.getRow(rowIndex);

                if (row != null) {
                    // Get Job Type cell value
                    Cell jobTypeCell = row.getCell(jobTypeColumnIndex);
                    String jobTypeValue = jobTypeCell != null ? jobTypeCell.toString().trim() : "(empty)";

                    // Get Total Tips cell value
                    Cell totalTipsCell = row.getCell(totalTipsColumnIndex);
                    String totalTipsValue = totalTipsCell != null ? totalTipsCell.toString().trim() : "(empty)";

                    // Add the values to the map
                    jobTypeTipsMap.put(jobTypeValue, totalTipsValue);
                }
            }

        }

        return jobTypeTipsMap;
    }

    public  Workbook getWorkbook(String filePath) throws IOException {
        File file = new File(filePath);
        FileInputStream fis = new FileInputStream(file);
        
        if (filePath.toLowerCase().endsWith(".xlsx")) {
            return new XSSFWorkbook(fis);  // For .xlsx files
        } else if (filePath.toLowerCase().endsWith(".xls")) {
            return new HSSFWorkbook(fis);  // For .xls files
        } else {
            throw new IllegalArgumentException("The specified file is not Excel format.");
        }
    }
   
        public static List<String> extractDataWithAsterisk(String filePath) {
            List<String> results = new ArrayList<>();

            try (FileInputStream fis = new FileInputStream(filePath);
                 Workbook workbook = new HSSFWorkbook(fis)) { // Use HSSFWorkbook for .xls files

                Sheet sheet = workbook.getSheetAt(0); // Get the first sheet
                for (Row row : sheet) {
                    for (Cell cell : row) {
                        if (cell.getCellType() == CellType.STRING) {
                            String cellValue = cell.getStringCellValue();
                            if (cellValue.contains("*")) {
                                results.add(cellValue);
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return results;
        }
    
    public static String findAnyFileExceptCSV(String folderPath) {
        File folder = new File(folderPath);
        
        // List all files in the directory
        File[] files = folder.listFiles();
        
        if (files != null && files.length > 0) {
            for (File file : files) {
                // Check if it's a file and not a directory, and also skip CSV files
                if (file.isFile() && !file.getName().toLowerCase().endsWith(".csv")) {
                    return file.getName();  // Return the first non-CSV file found
                }
            }
        }
        
        return null;  // No non-CSV files found
    }
    
    public static List<String> getSummaryData(String filePath, int amountColumnIndex) {
        List<String> summaryData = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new HSSFWorkbook(fis)) {  // Use HSSFWorkbook for .xls files

            Sheet sheet = workbook.getSheetAt(0); // Get the first sheet
            System.out.println("Sheet Name: " + sheet.getSheetName());

            // Iterate over rows to find all "Summary" rows
            for (Row row : sheet) {
                Cell summaryCell = row.getCell(1); // Column B (index 1)
                if (summaryCell != null && summaryCell.getCellType() == CellType.STRING) {
                    String cellValue = summaryCell.getStringCellValue();
                    System.out.println("Row Cell Value: " + cellValue); // Debug print

                    if (cellValue.equalsIgnoreCase("Summary")) {
                        // Extract data from the specified amount column
                        Cell amountCell = row.getCell(amountColumnIndex); // Column for amount data
                        if (amountCell != null) {
                            String cellData = "";
                            if (amountCell.getCellType() == CellType.STRING) {
                                cellData = amountCell.getStringCellValue();
                            } else if (amountCell.getCellType() == CellType.NUMERIC) {
                                cellData = String.valueOf(amountCell.getNumericCellValue());
                            }
                            summaryData.add(cellData);
                            System.out.println("Found 'Summary' row, Amount: " + cellData); // Debug
                        }
                    }
                }
            }

            if (summaryData.isEmpty()) {
                System.out.println("No 'Summary' rows found in the file.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return summaryData;
    }
    
    public static List<String> extractDataFromColumnA(String filePath) {
        List<String> results = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new HSSFWorkbook(fis)) { // Use HSSFWorkbook for .xls files

            Sheet sheet = workbook.getSheetAt(0); // Get the first sheet
            for (Row row : sheet) {
                Cell cell = row.getCell(0); // Get the cell in Column A (index 0)
                if (cell != null && cell.getCellType() == CellType.STRING) {
                    String cellValue = cell.getStringCellValue();
                    // Optionally, you can trim the value if needed:
                    cellValue = cellValue.trim();
                    results.add(cellValue);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return results;
    }
    
    public static String getAmountNextToLaborCost(String filePath) {
        String amountValue = null;  // To store the value from the adjacent column

        try (FileInputStream fis = new FileInputStream(new File(filePath));
             Workbook workbook = getWorkbook(fis, filePath)) {

            Sheet sheet = workbook.getSheetAt(0); // Get the first sheet
            boolean found = false;

            // Iterate through each row
            for (Row row : sheet) {
                Cell laborCostCell = row.getCell(10); // Column O (index 14)
                
                if (laborCostCell != null && laborCostCell.getCellType() == CellType.STRING) {
                    String cellValue = laborCostCell.getStringCellValue().trim();
                    
                    // Check if cell contains "Labor Cost:" or similar
                    if (cellValue.toLowerCase().contains("labor cost:")) {
                        Cell amountCell = row.getCell(11); // Column P (index 15)
                        
                        if (amountCell != null) {
                            amountValue = getCellValue(amountCell);
                            System.out.println("Found amount value: " + amountValue); // Debug statement
                        } else {
                            System.out.println("Amount cell is null.");
                        }
                        
                        found = true;
                        break; // Break loop once "Labor Cost:" is found
                    }
                }
            }

            if (!found) {
                System.out.println("Text containing 'Labor Cost:' not found in the sheet.");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return amountValue;  // Return the extracted amount value
    }

    // Determine which Workbook (HSSF for .xls or XSSF for .xlsx) to use based on file extension
    private static Workbook getWorkbook(FileInputStream fis, String filePath) throws IOException {
        if (filePath.toLowerCase().endsWith(".xlsx")) {
            return new XSSFWorkbook(fis); // For .xlsx files
        } else if (filePath.toLowerCase().endsWith(".xls")) {
            return new HSSFWorkbook(fis); // For .xls files
        } else {
            throw new IllegalArgumentException("The specified file is not an Excel file.");
        }
    }

    // Helper method to extract cell value regardless of type
    private static String getCellValue(Cell cell) {
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue().trim(); // Trim spaces
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                } else {
                    return String.valueOf(cell.getNumericCellValue());
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            default:
                return "";
        }
    }
    
    
    public static List<String> extractDataFromColumn0(String filePath) {
        List<String> results = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new HSSFWorkbook(fis)) { // Use HSSFWorkbook for .xls files

            Sheet sheet = workbook.getSheetAt(0); // Get the first sheet
            for (Row row : sheet) {
                Cell cell = row.getCell(10); // Get the cell in Column A (index 0)
                if (cell != null && cell.getCellType() == CellType.STRING) {
                    String cellValue = cell.getStringCellValue();
                    // Optionally, you can trim the value if needed:
                    cellValue = cellValue.trim();
                    results.add(cellValue);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return results;
    }
    
    
    public static List<String> extractDataFromColumnP(String filePath) {
        List<String> results = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new HSSFWorkbook(fis)) { // Use HSSFWorkbook for .xls files

            Sheet sheet = workbook.getSheetAt(0); // Get the first sheet
            for (Row row : sheet) {
                Cell cell = row.getCell(15); // Get the cell in Column A (index 0)
                if (cell != null && cell.getCellType() == CellType.STRING) {
                    String cellValue = cell.getStringCellValue();
                    // Optionally, you can trim the value if needed:
                    cellValue = cellValue.trim();
                    results.add(cellValue);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return results;
    }
    
}