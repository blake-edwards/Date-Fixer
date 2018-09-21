import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

public class dateFixer {
	 // main 
	 public static void main(String[] args) throws IOException, InvalidFormatException {
	        try {
	        	
	        	/*
	            ======================================
	            Retrieval of dates from the excel file
	            ======================================
	           	 */
	        	
	        	System.out.println("[+] Date fixer started");
	        	String path = "C:\\yourpath\\Your_Workbook.xlsx";
	        	ExcelReader readExcel = new ExcelReader();
	        	ArrayList<String> dates = new ArrayList<String>();
	        	dates = readExcel.getDates(path);
	           	//System.out.println("Test item: "+dates.get(500));
	           	
	           	/*
	            ===================================================
	            Editing the MySQL command file to add correct dates
	            ===================================================
	           	 */
	           	
	            File f = new File("C:\\yourpath\\Your_Command_File.txt"); // sql command text file
	            BufferedReader b = new BufferedReader(new FileReader(f));
	            OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream("C:\\yourpath\\Your_Command_Script.sql"), "UTF-8");
	            BufferedWriter bufWriter = new BufferedWriter(writer);
	            String readLine = "";
	            System.out.println("Reading and writing files . . .");
	            int counter = 0;
	            // inserting the correct date into every line
	            while ((readLine = b.readLine()) != null) {
	            	counter++;
	            	if (readLine.indexOf("RA_year")!=-1) {
	            		String newString2 = readLine.substring(0,(readLine.indexOf("RA_year")-1))+"`id`,"+ readLine.substring((readLine.indexOf("RA_year")-1));
	            		readLine = newString2;
	            	}
	            	if (counter%4==0) { // every fourth line is what we want
	            		if (readLine.indexOf("null")!=-1) { // perform insert if null string exists
	            			int nullPosition = readLine.indexOf("null");
	            			if (dates.get((counter/4)-1).equals("NULL")) {
	            				String newString = readLine.substring(0, nullPosition) +"NULL"+ readLine.substring(nullPosition+4); // constructs new string with date inserted
			            		//System.out.println("entry "+counter/4+ " : " +newString);
			            		// write the new line over the old line
			            		bufWriter.write(newString+"\r\n");
	            			} else {
	            				String newString = readLine.substring(0, nullPosition) +"'"+ dates.get((counter/4)-1) +" 00:00:00'"+ readLine.substring(nullPosition+4); // constructs new string with date inserted
			            		//System.out.println("entry "+counter/4+ " : " +newString);
			            		// write the new line over the old line
			            		bufWriter.write(newString+"\r\n");
	            			}
		            	}
	            	} else bufWriter.write(readLine+"\r\n");
	            }
	            
	            b.close();
	            bufWriter.close();
	            System.out.println("[+] complete!");
	        } catch (IOException e) {
	            e.printStackTrace();
	        }

	    } 
}
