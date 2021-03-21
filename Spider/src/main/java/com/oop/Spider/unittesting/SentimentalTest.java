package com.oop.Spider.unittesting;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.oop.Spider.services.SentimentalService;

import errorhandling.CustomError;

class SentimentalTest {
	static SentimentalService sentimentalTest;
	static ArrayList<String> sentences;
	static ArrayList<String> sentences2;
	private final static String filename = "./test.txt";
	
	@BeforeAll
	public static void setup() {
		sentimentalTest = new SentimentalService();
		sentences = new ArrayList<String>();
	}
	
	@Test
	public void testwriteClassificationToFile() {
		assertThrows(CustomError.class, () -> {sentimentalTest.writeClassificationToFile(sentences, filename);});
		assertThrows(NullPointerException.class, () -> {sentimentalTest.writeClassificationToFile(sentences2, filename);});
	}
	
	@Test
	public void testSentimentalCalculation() {
		assertThrows(CustomError.class,() -> {sentimentalTest.SentimentalCalculation(sentences);});
		assertThrows(NullPointerException.class, () -> {sentimentalTest.SentimentalCalculation(sentences2);});
	}

}
