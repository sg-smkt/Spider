package com.oop.Spider.controllers;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.oop.Spider.services.JsonService;
import com.oop.Spider.services.PlottingService;
import com.oop.Spider.services.SentimentalService;

import tech.tablesaw.api.Table;

@Controller 
public class PlottingController {
	
	private static final String RedditJson = "./data.json";
	private static final String TweeterJson = "./data2.json";
	private static final String SentimentalJson = "./sentimentaldata.json";

	
	@Autowired
	SentimentalService classification = new SentimentalService();
	
	@Autowired
	PlottingService newPlot = new PlottingService();
	
	@Autowired
	JsonService json= new JsonService();

	@GetMapping("/plotting")
	public String getPlot() {
		return "plotting";
	}
	
	@PostMapping("/plotting")
	public void postPlot() {
		classification.initialize();
		
		//ArrayList<String> sentences = json.getRedditComments(RedditJson);
		ArrayList<String> sentences = json.getTwitterComments(TweeterJson);
		classification.writeClassificationToFile(sentences, SentimentalJson);
		
		ArrayList<String> SentimentalTypeArray = json.getSentimentalData(SentimentalJson);
		Table table = newPlot.createTableByCount(SentimentalTypeArray);
		newPlot.printTable(table);
		newPlot.displayBarChart(table);
		newPlot.displayPieChart(table);	
	}
}
