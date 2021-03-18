package unittesting;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.oop.Spider.services.SentimentalService;

import errorhandling.CustomError;

class PlottingTest {
	static SentimentalService plottingTest;
	static ArrayList<String> sentences;
	static ArrayList<String> sentences2;
	private final static String filename = "./test.txt";
	
	@BeforeAll
	public static void setup() {
		plottingTest = new SentimentalService();
		sentences = new ArrayList<String>();
	}
	
	@Test
	public void testwriteClassificationToFile() {
		assertThrows(CustomError.class, () -> {plottingTest.writeClassificationToFile(sentences, filename);});
		assertThrows(NullPointerException.class, () -> {plottingTest.writeClassificationToFile(sentences2, filename);});
	}
	
	@Test
	public void testSentimentalCalculation() {
		assertThrows(CustomError.class,() -> {plottingTest.SentimentalCalculation(sentences);});
		assertThrows(NullPointerException.class, () -> {plottingTest.SentimentalCalculation(sentences2);});
	}

}
