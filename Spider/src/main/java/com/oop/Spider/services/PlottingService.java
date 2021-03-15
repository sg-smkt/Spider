package com.oop.Spider.services;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import org.springframework.stereotype.Service;

import tech.tablesaw.api.IntColumn;
import tech.tablesaw.api.StringColumn;
import tech.tablesaw.api.Table;
import tech.tablesaw.plotly.Plot;
import tech.tablesaw.plotly.api.HorizontalBarPlot;
import tech.tablesaw.plotly.api.PiePlot;

@Service
public class PlottingService {
	public Table createTableByCount(ArrayList<String> data) {
		String[] sentimentalType = {"Very Negative", "Negative", "Neurtal", "Positive", "Very Positive"};
		int[] sentimentalCounter = new int[5];
		
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
			}
		}
		
		// Create Table 
		Table sentimentalTable = Table.create("Sentimental Table")
				.addColumns(
						StringColumn.create("Sentimental Type", sentimentalType),
						IntColumn.create("Count", sentimentalCounter));
		
		return sentimentalTable;
	}
	
	public void printTable(Table table) {
		System.out.println(table);
	}
	
	public void displayBarChart(Table table) {
		String javascript = HorizontalBarPlot.create("Sentimental By Count", table, "Sentimental Type", "Count").asJavascript("newBarPlot");
        try(
        FileWriter fw = new FileWriter("./src/main/resources/templates/plotting.html", true);
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter out = new PrintWriter(bw)){
            out.println(javascript);
        } catch(IOException e){
            e.getMessage();
        }
		
	}
	
	public void displayPieChart(Table table) {
		String javascript = PiePlot.create("Sentimental By Percentage", table, "Sentimental Type", "Count").asJavascript("newPieChart");
		try(
		FileWriter fw = new FileWriter("./src/main/resources/templates/plotting.html", true);
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter out = new PrintWriter(bw)){
            out.println(javascript);
            out.println("</body>");
            out.println("</html>");
        } catch(IOException e){
            e.getMessage();
        }
	}
}
