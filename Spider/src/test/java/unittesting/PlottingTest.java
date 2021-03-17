package unittesting;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.oop.Spider.services.SentimentalService;

class PlottingTest {
	SentimentalService plottingTest;
	private final static String filename = "./test.txt";
	ArrayList<String> sentences;
	
	@BeforeAll
	public static void setup() {
		new SentimentalService();
		new ArrayList<String>();
	}
	
	@Test
	public void testwriteClassificationToFile() {
		assertThrows(NullPointerException.class, () -> {plottingTest.writeClassificationToFile(sentences, filename);});
	}
	
	@Test
	public void testSentimentalCalculation() {
		assertThrows(NullPointerException.class,() -> {plottingTest.SentimentalCalculation(sentences);});
	}

}
