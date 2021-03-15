package com.oop.Spider.services;

import java.util.ArrayList;
import java.util.Properties;

import org.ejml.simple.SimpleMatrix;

import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.trees.Tree;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SentimentalService {
	StanfordCoreNLP pipeline;
	
	@Autowired
	JsonService json = new JsonService();
	 
	// Initialize CoreNLP Sentiment Library
	public void initialize() {
		Properties properties = new Properties();
		properties.setProperty("annotators", "tokenize, ssplit, parse, sentiment");
		pipeline = new StanfordCoreNLP(properties);
	}
	
	public void writeClassificationToFile(ArrayList<String> sentences, String filename) {
		
		ArrayList<String> classificationList = new ArrayList<String>();
		for (int i = 0; i < sentences.size(); i++) {
			Annotation doc = new Annotation(sentences.get(i));
			pipeline.annotate(doc);
			for (int j = 0; j < doc.get(SentencesAnnotation.class).size(); j++) {
				String type = doc.get(SentencesAnnotation.class).get(j).get(SentimentCoreAnnotations.SentimentClass.class);
				classificationList.add(type);
			}
		}
		json.write(classificationList, filename);
	}
	
	public double[] SentimentalCalculation(ArrayList<String> sentences) {
		
		double[] sentimentalScore = new double[5];
		int length = 0;
		
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
		
	} 
}
