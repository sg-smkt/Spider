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

public class SentimenetTut {
	
	public static String text = "The real tragedy is that the higher the IQ and the longer the period of higher education, the less in touch the person will be with their animal instincts and intuition.\n" + 
			"\n" + 
			"The psyche becomes undecided after so long a period of structured rote learning. The expression becomes overly cautious, afraid to offend.\n" + 
			"\n" + 
			"The mind withdraws itself from the tempest of the natural world and seeks comfort in the heavily quantifiable. Every belief needs to be calculated in excess and checked against a long list of others before the self would even make the smallest step forward.\n" + 
			"\n" + 
			"And if you are to observe carefully enough, you will see that this phenomenon disproportionely affects young men. I am not saying that one nature is more important than the other. You need to be a complete human being.";
	
	
	public static void main(String[] args) {
		/* Requirement Steps to Use Library */
		// 1. Create Properties 
		Properties properties = new Properties();
		// 2. Set Properties 
		// Sentiment Dependency: Tokenize, ssplit, parse
		properties.setProperty("annotators", "tokenize, ssplit, parse, sentiment");
		// 3. Initialize Pipeline 
		StanfordCoreNLP pipeline = new StanfordCoreNLP(properties);
		// 4. Create Annotation
		Annotation doc = new Annotation(text);
		// 5. Run Annotation on Text
		pipeline.annotate(doc);
		
		
		List<CoreMap> sentences = doc.get(SentencesAnnotation.class);	
		
		// Mapping sentimentalScore to sentimentalType
		double[] sentimentalScore = new double[5];
		String[] sentimentalType = {"Very Negative", "Negative", "Neurtal", "Positive", "Very Positive"};
		
		int length = sentences.size();
		System.out.println("No of sentences: " + length);
		
		for (int i = 0; i <sentences.size(); i++) {
			
			// Code to Get Sentiment Type 
			/*String setimentType = sentences.get(i).get(SentimentCoreAnnotations.SentimentClass.class);
			  System.out.println("Sentiment Analysis Type: " + setimentType); */
			
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
		
		int modeIndex = 0;
		double totalSum = 0;
		for (int i = 0; i < sentimentalScore.length; i++) {
			System.out.print(sentimentalType[i] + ": ");
			System.out.println(sentimentalScore[i]);
			
			// Calculate Mean
			totalSum += (i+1) * sentimentalScore[i];
			
			// Calculate Mode 
			if (sentimentalScore[modeIndex] < sentimentalScore[i]) {
				modeIndex = i;
			}
		}
		
		System.out.println();
		System.out.println("--- Statistics ---");
		System.out.println("Mean: " + totalSum / 5);
		System.out.println("Sentimental Type (Mode): " + sentimentalType[modeIndex]);
	}

}
