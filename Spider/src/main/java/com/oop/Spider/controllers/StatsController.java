package com.oop.Spider.controllers;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.oop.Spider.services.JsonService;
import com.oop.Spider.services.SentimentalService;
import com.oop.Spider.services.StatisticsService;

@Controller
public class StatsController {
	
	@Autowired
	SentimentalService classification = new SentimentalService();
	@Autowired
	JsonService json= new JsonService();
	@Autowired
	StatisticsService newStats = new StatisticsService();
	
	private static final String RedditJson = "./data.json";
	
	@GetMapping("/stats")
	public String getStats() {
		return "stats";
	}
	
	@PostMapping("/stats")
	public void postStats() {
		// Instance for Sentimental Analysis
		classification.initialize();
		
		ArrayList<String> sentences = json.getRedditComments(RedditJson);
		
		double[] sentimentalScore = new double[5];
		sentimentalScore = classification.SentimentalCalculation(sentences);
		
		newStats.printStats(sentimentalScore);
	}
}
