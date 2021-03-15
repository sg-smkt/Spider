package spiderProject;

import java.util.ArrayList;


import tech.tablesaw.api.DoubleColumn;

public class StatsSentimental {
	private static final String RedditJson = "./src/main/java/spiderProject/data.json";
	
	public static void main(String[] args) {
		// Instance for Sentimental Analysis
		Sentimental classification = new Sentimental();
		classification.initialize();
		
		// Instance for GettingJson
		Json json= new Json();
		ArrayList<String> sentences = json.getRedditComments(RedditJson);
		
		double[] sentimentalScore = new double[5];
		sentimentalScore = classification.SentimentalCalculation(sentences);
		
		printStats(sentimentalScore);
	}
	
	public static void printStats(double[] sentimentalScore) {
		SpiderStatistics newStats = new SpiderStatistics();
		double[] normalizedScore = newStats.normalize(sentimentalScore);
		
		DoubleColumn ss = DoubleColumn.create("Sentimental Data", normalizedScore);
		
		System.out.println();
		System.out.println("--- Statistics ---");
		System.out.println("Mean (Expected Value: " + newStats.getMean(ss));
		System.out.println("Standard Deviation: " + newStats.getSD(ss));
		System.out.println("Variance :" + newStats.getVar(ss));	
		System.out.println("Sentimental Type (Mode): " + newStats.getModeSentimenet(newStats.getMean(ss)));
	}
}
