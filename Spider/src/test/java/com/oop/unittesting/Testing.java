package com.oop.unittesting;

import java.io.File;
import java.util.ArrayList;

import org.json.simple.JSONObject;

import com.oop.Spider.services.JsonService;
import com.oop.Spider.services.PlottingService;
import com.oop.Spider.services.StatisticsService;

import errorhandling.CustomError;
import tech.tablesaw.api.Table;

public class Testing {
	private static final String filename = "test.html";
	private static final String var = "testingvariable";
	
	public static void main(String[] args) throws CustomError {
		System.out.println(getFileExtension(filename));
	}
	
	public static String getFileExtension(String fullName) {
	    String fileName = new File(fullName).getName();
	    int dotIndex = fileName.lastIndexOf('.');
	    return (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1);
	}
}
