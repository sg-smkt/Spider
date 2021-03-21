package com.oop.Spider.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

import org.ejml.simple.SimpleMatrix;
import org.json.simple.JSONObject;

import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.trees.Tree;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oop.Spider.errorhanding.CustomError;

/** Contains method to parse data into CoreNLP sentimental analysis
 * @author Tsui Sau Chi
 * @version 1.0
 */
@Service
public class SentimentalService {
	private StanfordCoreNLP pipeline;
	
	@Autowired
	JsonService json = new JsonService();
	 
	/**
	 * <p> Initialization of CoreNLP Sentiment Library. Core NLP architecture is based on pipelines 
	 * 	To use Sentimental Analysis for CoreNLP, the specific pipeline needs to be specified. 
	 *  The initialization follows CoreNLP requirement pipeline tag for sentimental analysis 
	 *  The tags are: tokenize, ssplit, pos, parse</p>
	 * @see <a href="https://stanfordnlp.github.io/CoreNLP">CoreNLP Library</a>
	 * @see <a href="https://stanfordnlp.github.io/CoreNLP/annotators.html">CoreNLP Annotations</a>
	 * @since 1.0
	 */
	public void initialize() {
		Properties properties = new Properties();
		properties.setProperty("annotators", "tokenize, ssplit, parse, sentiment");
		pipeline = new StanfordCoreNLP(properties);
	}
	
	/**
	 * <p> This method takes in a list of sentences and output the sentimental classification type onto a file. 
	 * There are 5 types of sentimental categorization which includes: "Very Negative", "Negative", "Neutral", "Positive" and "Very Positive"</p>
	 * @param sentences - ArrayList containing sentences to be parse into CoreNLP sentimental analysis
	 * @param filename - the specified filename to write the sentimental analysis data
	 * @throws UserDataException - If sentences size is less than 1, throw "Sentence size is less then 1" message to console 
	 * @throws NullPointerException - ArrayList is not initialized 
	 * @throws IOException If there's error during input or output
	 * @since 1.0
	 */
	public void writeClassificationToFile(ArrayList<String> sentences, String filename) throws CustomError, NullPointerException, IOException {
		ArrayList<String> classificationList = new ArrayList<String>();
		if (sentences.size() > 0) {
			// Parse each sentence into the sentimental analysis library for classification
			// Each sentence is encaspulated within a document required before sentimental analsysis is conducted
			for (int i = 0; i < sentences.size(); i++) {
				Annotation doc = new Annotation(sentences.get(i));
				pipeline.annotate(doc);
				// Get classification
				for (int j = 0; j < doc.get(SentencesAnnotation.class).size(); j++) {
					String type = doc.get(SentencesAnnotation.class).get(j).get(SentimentCoreAnnotations.SentimentClass.class);
					classificationList.add(type);
				}
			}
			// Stores result into a HashMap 
			HashMap<String, ArrayList<String>> hashData = new HashMap<String, ArrayList<String>>();
			hashData.put("Sentimental Type Data", classificationList);
			JSONObject obj = new JSONObject(hashData);
			// Write result to file
			json.writeToFile(filename, obj);	
		} else {
			throw new CustomError("Sentence size is less then 1. No sentence to parse into sentimental analysis");
		}
	}
	
	/**
	 * <p> This methods takes in a list of sentences, parse the sentences into the sentimental analysis 
	 *  and output the sentimental score into an array </p>
	 * @param sentences - ArrayList containing sentences to be parse into CoreNLP sentimental analysis
	 * @throws UserDataException - If sentences size is less than 1, throw "Sentence size is less then 1" message to console 
	 * @throws NullPointerException - ArrayList is not initialized 
	 * @return a double array containing the sentimental Score
	 * @since 1.0
	 */
	public double[] SentimentalCalculation(ArrayList<String> sentences) throws CustomError, NullPointerException{
		double[] sentimentalScore = new double[5];
		int length = 0;
		
		if (sentences.size() > 0) {
			// Parse each sentence into the sentimental analysis library for classification
		    // Each sentence is encapsulated within a document required before sentimental analysis is conducted
			for (int i = 0; i < sentences.size(); i++) {
				Annotation doc = new Annotation(sentences.get(i));
				pipeline.annotate(doc);
				
				length += doc.get(SentencesAnnotation.class).size();
				for (int j = 0; j < doc.get(SentencesAnnotation.class).size(); j++) {
					// Code to get Sentiment Distribution 
					Tree tree = doc.get(SentencesAnnotation.class).get(j).get(SentimentCoreAnnotations.SentimentAnnotatedTree.class);
					SimpleMatrix simpleMatrix = RNNCoreAnnotations.getPredictions(tree);
					
					// Get Probability 
					sentimentalScore[0] += simpleMatrix.get(0); // Very Negative
					sentimentalScore[1] += simpleMatrix.get(1); // Negative
					sentimentalScore[2] += simpleMatrix.get(2); // Neutral
					sentimentalScore[3] += simpleMatrix.get(3); // Positive
					sentimentalScore[4] += simpleMatrix.get(4); // Very Positive 
				}
			}
			
			for (int i = 0; i < sentimentalScore.length; i++) {
				sentimentalScore[i] /= length;
			}
			
			return sentimentalScore;	
		} else {
			throw new CustomError("Sentence size is less then 1. No sentence to parse into sentimental analysis");
		}
		
	} 
}
