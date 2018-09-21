import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class ExcelReader {

    public ArrayList<String> getDates(String path) throws IOException, InvalidFormatException {
        // Creating a Workbook from an Excel file - allows reading of both .xls and .xlsx
        Workbook workbook = WorkbookFactory.create(new File(path));
        // Retrieving the number of sheets in the Workbook
        System.out.println("Workbook has " + workbook.getNumberOfSheets() + " Sheets : ");

        /*
           =============================================
           Iterating over all the sheets in the workbook
           =============================================
        */

        System.out.println("Retrieving Sheets :");
        for(Sheet sheet: workbook) {
            System.out.println("=> " + sheet.getSheetName());
        }

        /*
           ==================================================
           Iterating over all the rows and columns in a Sheet
           ==================================================
        */

        // Sheet at index zero
        Sheet sheet = workbook.getSheetAt(0);
        // DataFormatter to format and get each cell's value as String
        DataFormatter dataFormatter = new DataFormatter();
        // obtain a rowIterator and columnIterator and iterate over them
        System.out.println("Iterating through "+sheet.getSheetName()+" . . .");
        ArrayList<String> dates = new ArrayList<String>(); 
        for (int i=1; i<sheet.getPhysicalNumberOfRows(); i++) {
        	Row currentRow = sheet.getRow(i);
        	Cell currentCell = currentRow.getCell(17); // row 17 = date data
        	String currentCellValue = dataFormatter.formatCellValue(currentCell);
        	//System.out.println("Adding: "+currentCellValue);
        	dates.add(currentCellValue); // popping value into array list of dates
        }
        workbook.close(); // closing the workbook to prevent memory leak (otherwise might not be able to open again!)
        return dates;
    }
}