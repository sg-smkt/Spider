package spiderProject;

import java.util.ArrayList;
import java.util.List;

import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.util.CoreMap;
import tech.tablesaw.api.ColumnType;
import tech.tablesaw.api.DoubleColumn;
import tech.tablesaw.api.IntColumn;
import tech.tablesaw.api.StringColumn;
import tech.tablesaw.api.Table;
import tech.tablesaw.columns.numbers.NumberColumnFormatter;
import tech.tablesaw.plotly.Plot;
import tech.tablesaw.plotly.api.HorizontalBarPlot;
import tech.tablesaw.plotly.api.PiePlot;

public class SpiderPlotting {
	
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
		Plot.show(HorizontalBarPlot.create("Sentimental By Count", table, "Sentimental Type", "Count"));
	}
	
	public void displayPieChart(Table table) {
		Plot.show(PiePlot.create("Sentimental By Percentage", table, "Sentimental Type", "Count"));
	}
	
}
