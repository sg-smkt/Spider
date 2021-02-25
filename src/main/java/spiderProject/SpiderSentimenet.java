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

public class SpiderSentimenet {
	
	public static String text = "The real tragedy is that the higher the IQ and the longer the period of higher education, the less in touch the person will be with their animal instincts and intuition.\n" + 
			"\n" + 
			"The psyche becomes undecided after so long a period of structured rote learning. The expression becomes overly cautious, afraid to offend.\n" + 
			"\n" + 
			"The mind withdraws itself from the tempest of the natural world and seeks comfort in the heavily quantifiable. Every belief needs to be calculated in excess and checked against a long list of others before the self would even make the smallest step forward.\n" + 
			"\n" + 
			"And if you are to observe carefully enough, you will see that this phenomenon disproportionely affects young men. I am not saying that one nature is more important than the other. You need to be a complete human being.";
	
	public Annotation Initialize() {
		Properties properties = new Properties();
		properties.setProperty("annotators", "tokenize, ssplit, parse, sentiment");
		StanfordCoreNLP pipeline = new StanfordCoreNLP(properties);
		Annotation doc = new Annotation(text);
		pipeline.annotate(doc);
		return doc;
	}
	
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
		SpiderSentimenet newSentiment = new SpiderSentimenet();
		//Initialize CoreNLP Sentiment Library
		Annotation doc = newSentiment.Initialize();
		//Pass document into Sentiment Library for calculation  
		double[] result = newSentiment.SentimentalCalculation(doc.get(SentencesAnnotation.class));
		//Print Result
		newSentiment.printStats(result);
	}

}
