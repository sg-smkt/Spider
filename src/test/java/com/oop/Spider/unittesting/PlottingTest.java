package com.oop.Spider.unittesting;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.oop.Spider.errorhanding.CustomError;
import com.oop.Spider.services.PlottingService;

import tech.tablesaw.api.Table;

/** Plotting Service Unit Testing
 * @author Tsui Sau Chi
 * @version 1.0
 */
class PlottingTest {
	static PlottingService plottingtest;
	static ArrayList<String> data;
	static ArrayList<String> data2;
	
	static Table table;
	private static final String filename = "test.html";
	private static final String filename2 = "test.txt";
	private static final String var = "testingvariable";
	
	@BeforeAll
	public static void setup() {
		plottingtest = new PlottingService();
		data = new ArrayList<String>();
		data.add("Positive");
		data.add("Test");
	}

	@Test
	public void testcreateTableByCount() {
		//If the Array List does not contain any of the classification types of “Very Positive, Positive, Neutral, 
		//Negative, Very Negative”. The total count of valid data will be thrown together with a custom error exception 
		assertThrows(CustomError.class, () -> {plottingtest.createTableByCount(data);});
		//If the Array List is not initialized, a null exception is thrown 
		assertThrows(NullPointerException.class, () -> {plottingtest.createTableByCount(data2);});
	}
	
	@Test
	public void testChart() {
		//If the filename does not end with a “.html”, a custom exception message is thrown 
		assertThrows(CustomError.class, () -> {plottingtest.displayPieChart(table, filename, var);});
		//If the Table value is null, a custom exception message is thrown
		assertThrows(CustomError.class, () -> {plottingtest.displayBarChart(table, filename, var);});
	}
	
	@Test
	public void testBar() {
		////If the filename does not end with a “.html”, a custom exception message is thrown 
		assertThrows(CustomError.class, () -> {plottingtest.displayPieChart(table, filename2, var);});
		////If the Table value is null, a custom exception message is thrown
		assertThrows(CustomError.class, () -> {plottingtest.displayBarChart(table, filename2, var);});
	}

}
