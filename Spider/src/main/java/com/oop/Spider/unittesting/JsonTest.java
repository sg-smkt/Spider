package com.oop.Spider.unittesting;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.oop.Spider.services.JsonService;

import errorhandling.CustomError;

class JsonTest {
	
	static JsonService jsonTest;
	private static final String filename = "";
	private static final String filename2 = "text.json";
	private static final String filename3 = "data2.json";
	private static final String filename4 = "data.json";
	private static final Object data = "TestData";
	
	@BeforeAll
	public static void setup() {
		jsonTest = new JsonService();
	}
	
	
	@Test
	void testread() {
		assertThrows(FileNotFoundException.class, () -> {JsonService.read(filename);});
	}

	@Test
	void testwriteToFile() {
		assertThrows(CustomError.class, () -> {jsonTest.writeToFile(filename, data);});
	}
	
	@Test
	void testgetRedditComments() {
		assertThrows(FileNotFoundException.class, () -> {jsonTest.getRedditComments(filename2);});
		assertThrows(NullPointerException.class, () -> {jsonTest.getRedditComments(filename3);});
	}
	
	@Test
	void testgetTwitterComments() {
		assertThrows(FileNotFoundException.class, () -> {jsonTest.getTwitterComments(filename2);});
		assertThrows(NullPointerException.class, () -> {jsonTest.getTwitterComments(filename4);});
	}
	
	@Test
	void testgetRedditObject() {
		assertThrows(FileNotFoundException.class, () -> {jsonTest.getRedditObject(filename2);});
	}
	
	@Test
	void testgetTwitterObject() {
		assertThrows(FileNotFoundException.class, () -> {jsonTest.getTwitterObject(filename2);});
	}
	
	@Test
	void testgetSentimentalData() {
		assertThrows(FileNotFoundException.class, () -> {jsonTest.getSentimentalData(filename2);});
		assertThrows(NullPointerException.class, () -> {jsonTest.getSentimentalData(filename4);});
	}

}
