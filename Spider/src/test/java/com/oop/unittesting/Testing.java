package com.oop.unittesting;

import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import com.oop.Spider.services.JsonService;

import errorhandling.CustomError;

public class Testing {
	private static final String filename = "./data.json";
	
	public static void main(String[] args) throws CustomError, ParseException, IOException {
		getSentimentalData(filename);
	}
	
	public static ArrayList<String> getSentimentalData(String filename) throws ParseException, IOException {
		JSONObject jsonObject = JsonService.read(filename);
		JSONArray jsonArray = (JSONArray) jsonObject.get("Sentimental Type Data");
		return jsonArray;
	}
}
