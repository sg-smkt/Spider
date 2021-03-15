package spiderProject;

import java.util.ArrayList;

import tech.tablesaw.api.Table;

public class PlotSentimental {
	private static final String RedditJson = "./src/main/java/spiderProject/data.json";
	private static final String SentimentalJson = "./src/main/java/spiderProject/sentimentaldata.json";

	public static void main(String[] args) {
		// Instance for Sentimental Analysis
		Sentimental classification = new Sentimental();
		classification.initialize();
		
		// Instance for GettingJson
		Json json= new Json();
		ArrayList<String> sentences = json.getRedditComments(RedditJson);
		classification.writeClassificationToFile(sentences, SentimentalJson);
		
		SpiderPlotting newPlot = new SpiderPlotting();
		ArrayList<String> SentimentalTypeArray = json.getSentimentalData(SentimentalJson);
		Table table = newPlot.createTableByCount(SentimentalTypeArray);
		newPlot.printTable(table);
		newPlot.displayBarChart(table);
		newPlot.displayPieChart(table);	
	}

}
