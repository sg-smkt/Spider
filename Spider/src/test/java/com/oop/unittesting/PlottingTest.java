package com.oop.unittesting;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.oop.Spider.services.PlottingService;

import errorhandling.CustomError;
import tech.tablesaw.api.Table;

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
		assertThrows(CustomError.class, () -> {plottingtest.createTableByCount(data);});
		assertThrows(NullPointerException.class, () -> {plottingtest.createTableByCount(data2);});
	}
	
	@Test
	public void testChart() {
		assertThrows(CustomError.class, () -> {plottingtest.displayPieChart(table, filename, var);});
		assertThrows(CustomError.class, () -> {plottingtest.displayBarChart(table, filename, var);});
	}
	
	@Test
	public void testFileExtension() {
		assertThrows(CustomError.class, () -> {plottingtest.displayPieChart(table, filename2, var);});
		assertThrows(CustomError.class, () -> {plottingtest.displayBarChart(table, filename2, var);});
	}

}
