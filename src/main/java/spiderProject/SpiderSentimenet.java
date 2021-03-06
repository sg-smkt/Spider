package spiderProject;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.ejml.simple.SimpleMatrix;

import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;
import tech.tablesaw.api.DoubleColumn;
import tech.tablesaw.api.StringColumn;
import tech.tablesaw.api.Table;
import tech.tablesaw.plotly.Plot;
import tech.tablesaw.plotly.api.HorizontalBarPlot;
import tech.tablesaw.plotly.api.PiePlot;

public class SpiderSentimenet {
	
	// This is not used
	public double[] SentimentalCalculation(List<CoreMap> sentences) {
		double[] sentimentalScore = new double[5];
		int length = sentences.size();
		System.out.println("No of sentences: " + length);
		
		for (int i = 0; i <sentences.size(); i++) {
			
			// Code to get Sentiment Distribution 
			Tree tree = sentences.get(i).get(SentimentCoreAnnotations.SentimentAnnotatedTree.class);
			SimpleMatrix simpleMatrix = RNNCoreAnnotations.getPredictions(tree);
			
			// Get Probability 
			sentimentalScore[0] += (simpleMatrix.get(0) / length); // Very Negative
			sentimentalScore[1] += (simpleMatrix.get(1) / length); // Negative
			sentimentalScore[2] += (simpleMatrix.get(2) / length); // Neutral
			sentimentalScore[3] += (simpleMatrix.get(3) / length); // Positive
			sentimentalScore[4] += (simpleMatrix.get(4) / length); // Very Positive 
		}
		return sentimentalScore;
	}
	
	public void printStats(double[] sentimentalScore) {
		DoubleColumn ss = DoubleColumn.create("Sentimental Data", sentimentalScore);
		SpiderStatistics newStats = new SpiderStatistics();
		
		System.out.println();
		System.out.println("--- Statistics ---");
		System.out.println("Mean: " + newStats.getMean(ss));
		System.out.println("Median: " +newStats.getMedian(ss));
		System.out.println("Standard Deviation: " + newStats.getSD(ss));
		System.out.println("Variance :" + newStats.getVar(ss));	
		System.out.println("Sentimental Type (Mode): " + newStats.getModeSentimenet(sentimentalScore));
	}
	
	public static void main(String[] args) {
		ReadJson jsonData = new ReadJson();
		ArrayList<String> data = jsonData.getData();
		System.out.println(data.get(0));
		
		SpiderSentimenet newSentiment = new SpiderSentimenet();
		//Initialize CoreNLP Sentiment Library
		Properties properties = new Properties();
		properties.setProperty("annotators", "tokenize, ssplit, parse, sentiment");
		StanfordCoreNLP pipeline = new StanfordCoreNLP(properties);
		
		double[][] result = new double[data.size()][5];
		double[] summarizesResult = new double[5];
		for(int i = 0; i < 1; i++) {
			Annotation doc = new Annotation(data.get(i));
			pipeline.annotate(doc);
			//Pass document into Sentiment Library for calculation  
			result[i] = newSentiment.SentimentalCalculation(doc.get(SentencesAnnotation.class));
		}
		
		for(int i = 0; i < data.size(); i++) {
			for(int j = 0; j < 5; j++) {
				System.out.print(result[i][j] + " ");
			}
			System.out.println();
		}

		
		for(int j = 0; j < 5; j++) {
			for(int i = 0; i < data.size(); i++) {
				summarizesResult[j] += result[i][j];
			}
		}

		//print result
		newSentiment.printStats(summarizesResult);
		
		
		//need to figure out how to handle plotting in terms of double array
		
//		SpiderPlotting newPlot = new SpiderPlotting();
//		Table table = newPlot.createTableByCount(doc.get(SentencesAnnotation.class));
//		newPlot.printTable(table);
//		newPlot.displayBarChart(table);
//		newPlot.displayPieChart(table);
	}

}
