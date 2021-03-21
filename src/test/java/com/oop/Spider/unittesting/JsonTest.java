package com.oop.Spider.unittesting;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.oop.Spider.errorhanding.CustomError;
import com.oop.Spider.services.JsonService;

/** Json Service Unit Testing
 * @author Tsui Sau Chi
 * @version 1.0
 */
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
		//Test whether the filename is found
		assertThrows(FileNotFoundException.class, () -> {JsonService.read(filename);});
	}

	@Test
	void testwriteToFile() {
		//Writes the file, if the filename is an empty string, then a custom error will be thrown 
		assertThrows(CustomError.class, () -> {jsonTest.writeToFile(filename, data);});
	}
	
	@Test
	void testgetRedditComments() {
		//Read the file name, if the file is not found, a FileNotFoundException will be thrown
		assertThrows(FileNotFoundException.class, () -> {jsonTest.getRedditComments(filename2);});
		//If the getRedditComments return a Null value due to an invalid format or empty file, a NullPointerException will be thrown
		assertThrows(NullPointerException.class, () -> {jsonTest.getRedditComments(filename3);});
	}
	
	@Test
	void testgetTwitterComments() {
		//Read the file name, if the file is not found, a FileNotFoundException will be thrown
		assertThrows(FileNotFoundException.class, () -> {jsonTest.getTwitterComments(filename2);});
		//If the Twitter Comments return a Null value due to an invalid format or empty file, a NullPointerException will be thrown
		assertThrows(NullPointerException.class, () -> {jsonTest.getTwitterComments(filename4);});
	}
	
	@Test
	void testgetRedditObject() {
		//Read the file name, if the file is not found, a FileNotFoundException will be thrown
		assertThrows(FileNotFoundException.class, () -> {jsonTest.getRedditObject(filename2);});
	}
	
	@Test
	void testgetTwitterObject() {
		//Read the file name, if the file is not found, aaFileNotFoundException will be thrown
		assertThrows(FileNotFoundException.class, () -> {jsonTest.getTwitterObject(filename2);});
	}
	
	@Test
	void testgetSentimentalData() {
		//Read the file name, if the file is not found, a FileNotFoundException will be thrown
		assertThrows(FileNotFoundException.class, () -> {jsonTest.getSentimentalData(filename2);});
		//Read the file name, if the file is not found, a FileNotFoundException will be thrown
		assertThrows(NullPointerException.class, () -> {jsonTest.getSentimentalData(filename4);});
	}

}
