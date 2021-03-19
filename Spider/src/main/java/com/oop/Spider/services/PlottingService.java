package com.oop.Spider.services;

import java.awt.FontFormatException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import org.springframework.stereotype.Service;

import errorhandling.CustomError;
import tech.tablesaw.api.IntColumn;
import tech.tablesaw.api.StringColumn;
import tech.tablesaw.api.Table;
import tech.tablesaw.plotly.Plot;
import tech.tablesaw.plotly.api.HorizontalBarPlot;
import tech.tablesaw.plotly.api.PiePlot;

@Service
public class PlottingService {
	
	/**
	 * <p>This Method takes in a list of sentimental categorization and outputs a Table Object </br>
	 * There are 5 types of sentimental categorization which includes: "Very Negative", "Negative", "Neutral", "Positive" and "Very Positive"</p>
	 * The method also counts the number of invalid string in the list and returns an error messsage with the information</p>
	 * @param data ArrayList containing Sentimental categorization
	 * @return Table object consisting two column; Column 1 list the sentimental categorization; Column 2 list of number 
	 * of counts associated with the sentimental categorization
	 * @see <a href="">https://javadoc.io/doc/tech.tablesaw/tablesaw-core/0.25.2/tech/tablesaw/api/Table.html</a>
	 * @since 1.0
	 */
	public Table createTableByCount(ArrayList<String> data) throws CustomError, NullPointerException{
		String[] sentimentalType = {"Very Negative", "Negative", "Neurtal", "Positive", "Very Positive"};
		int[] sentimentalCounter = new int[5];
		int invalidString = 0;
		
		for (int i = 0; i < data.size(); i++) {
			String setimentType = data.get(i);
			
			// Get Counter 
			switch(setimentType) {
				case "Very negative":
					sentimentalCounter[0] += 1;
					break;
				case "Negative":
					sentimentalCounter[1] += 1;
					break;
				case "Neutral":
					sentimentalCounter[2] += 1;
					break;
				case "Positive":
					sentimentalCounter[3] += 1;
					break;
				case "Very positive":
					sentimentalCounter[4] += 1;
					break;
				default:
					invalidString += 1;
					break;
			}
		}
		
		// Create Table 
		Table sentimentalTable = Table.create("Sentimental Table")
				.addColumns(
						StringColumn.create("Sentimental Type", sentimentalType),
						IntColumn.create("Count", sentimentalCounter));
		
		if (invalidString > 0) {
			throw new CustomError("Number of Invalid String is " + invalidString);
		}
		
		return sentimentalTable;
	}
	
	/**
	 * <p> This method print the table information</p>
	 * @param table Table object
	 * @see <a href="">https://javadoc.io/doc/tech.tablesaw/tablesaw-core/0.25.2/tech/tablesaw/api/Table.html</a>
	 * @since 1.0
	 */
	public void printTable(Table table) {
		System.out.println(table);
	}
	
	/**
	 * <p> This method creates a Bar Chart based based on the Table input data. Then the Bar Chart is insert into a html file specified </b>
	 * by the user in the form of javascript </p>
	 * @param table Table object
	 * @param filename html file to insert Bar Chart javascript
	 * @param javascriptVar specify the javascript getElementById variable
	 * @throws IOException Error druing write operation 
	 * @throws CustomError Either Table is Empty or the file type is not html
	 * @see <a href="">https://javadoc.io/doc/tech.tablesaw/tablesaw-core/0.25.2/tech/tablesaw/api/Table.html</a>
	 */
	public void displayBarChart(Table table, String filename, String javascriptVar) throws CustomError, IOException{
		if (table != null) {
			String javascript = HorizontalBarPlot.create("Sentimental By Count", table, "Sentimental Type", "Count").asJavascript(javascriptVar);
			if (getFileExtension(filename).equals("html")) {
				
				FileWriter fw = new FileWriter(filename, true);
		        BufferedWriter bw = new BufferedWriter(fw);
		        PrintWriter out = new PrintWriter(bw);
				out.println(javascript);		
		        out.flush();
		      
			} else {
				throw new CustomError("Invalid File Type");
			}	
		} else {
			throw new CustomError("Table is Empty");
		}
	}
	
	/**
	 * <p> This method creates a Bar Chart based based on the Table input data. Then the Bar Chart is insert into a html file specified </b>
	 * by the user in the form of javascript </p>
	 * @param table Table object
	 * @param filename html file to insert Bar Chart javascript
	 * @param javascriptVar specify the javascript getElementById variable
	 * @throws IOException Error druing write operation 
	 * @throws CustomError Either Table is Empty or the file type is not html
	 * @see <a href="">https://javadoc.io/doc/tech.tablesaw/tablesaw-core/0.25.2/tech/tablesaw/api/Table.html</a>
	 */
	public void displayPieChart(Table table, String filename, String javascriptVar) throws CustomError, IOException{
		if (table != null) {
			String javascript = PiePlot.create("Sentimental By Percentage", table, "Sentimental Type", "Count").asJavascript(javascriptVar);
			if (getFileExtension(filename).equals("html")) {
				
				FileWriter fw = new FileWriter(filename, true);
		        BufferedWriter bw = new BufferedWriter(fw);
		        PrintWriter out = new PrintWriter(bw);
		        out.println(javascript);
		        out.flush();	
		        
			} else {
				throw new CustomError("Invalid File Type");
			}
		} else {
			throw new CustomError("Table is Empty");
		}
	}
	
	// Reference: https://stackoverflow.com/questions/25298691/how-to-check-the-file-type-in-java/25298748
	// Method to read File Extension
	private static String getFileExtension(String fullName) {
	    String fileName = new File(fullName).getName();
	    int dotIndex = fileName.lastIndexOf('.');
	    return (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1);
	}
}
