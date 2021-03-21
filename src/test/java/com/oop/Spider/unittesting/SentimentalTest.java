package com.oop.Spider.unittesting;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.oop.Spider.errorhanding.CustomError;
import com.oop.Spider.services.SentimentalService;

/** Sentimental Service Unit Testing
 * @author Tsui Sau Chi
 * @version 1.0
 */
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
		//If the Array List is an empty string, it will be detected and a custom error exception is thrown 
		assertThrows(CustomError.class, () -> {sentimentalTest.writeClassificationToFile(sentences, filename);});
		//If the Array List is not initialized, a null exception is thrown
		assertThrows(NullPointerException.class, () -> {sentimentalTest.writeClassificationToFile(sentences2, filename);});
	}
	
	@Test
	public void testSentimentalCalculation() {
		//If the Array List is an empty string, it will be detected and a custom error exception is thrown 
		assertThrows(CustomError.class,() -> {sentimentalTest.SentimentalCalculation(sentences);});
		//If the Array List is not initialized, a null exception is thrown
		assertThrows(NullPointerException.class, () -> {sentimentalTest.SentimentalCalculation(sentences2);});
	}

}
