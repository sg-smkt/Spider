package com.oop.Spider.controllers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.parser.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.oop.Spider.objects.Statistics;
import com.oop.Spider.services.JsonService;
import com.oop.Spider.services.PlottingService;
import com.oop.Spider.services.SentimentalService;
import com.oop.Spider.services.StatisticsService;

import errorhandling.CustomError;
import tech.tablesaw.api.Table;

@Controller 
public class PlottingController {
	
	private static final String RedditJson = "./data.json";
	private static final String TweeterJson = "./data2.json";
	private static final String SentimentalJson = "./sentimentaldata.json";
	private static final String PlottingFile = "./src/main/resources/templates/plotting.html";
	
	private static final String jsBarChartVarReddit = "redditBarPlot";
	private static final String jsPieChartVarReddit = "redditPieChart";
	private static final String jsBarChartVarTwitter = "twitterBarPlot";
	private static final String jsPieChartVarTwitter = "twitterPieChart";

	
	@Autowired
	SentimentalService classification = new SentimentalService();
	
	@Autowired
	PlottingService newPlot = new PlottingService();
	
	@Autowired
	StatisticsService newStats = new StatisticsService();
	
	@Autowired
	JsonService json= new JsonService();

	@GetMapping("/plotting")
	public String getPlot() {
		return "plotting";
	}
	
	@GetMapping("/plottingreddit")
	public String getPlotReddit() {
		return "plotting";
	}
	
	@GetMapping("/plottingtwitter")
	public String getPlotTwitter() {
		return "plotting";
	}
	
	@GetMapping("/statsreddit")
	public String getStatsReddit() {
		return "plotting";
	}
	
	@GetMapping("/statstwitter")
	public String getStatsTwitter() {
		return "plotting";
	}
	
	@PostMapping("/plottingreddit")
	public String postPlotReddit() {
		classification.initialize();
		
		try {
			ArrayList<String> sentences = json.getRedditComments(RedditJson);
			classification.writeClassificationToFile(sentences, SentimentalJson);
			ArrayList<String> SentimentalTypeArray = json.getSentimentalData(SentimentalJson);
			Table table = newPlot.createTableByCount(SentimentalTypeArray);
			newPlot.printTable(table);
			newPlot.displayBarChart(table, PlottingFile, jsBarChartVarReddit);
			newPlot.displayPieChart(table, PlottingFile, jsPieChartVarReddit);	
		} catch(CustomError e) {
			System.out.println(e.getMessage());
		} catch(NullPointerException e) {
			System.out.println(e.getMessage());
		}  catch(ParseException e) {
			System.out.println(e.getMessage());
		} catch(FileNotFoundException e) {
			System.out.println(e.getMessage());
		} catch(IOException e) {
			System.out.println(e.getMessage());
		}

		return "plotting";
	}
	
	@PostMapping("/plottingtwitter")
	public String postPlotTwitter() {
		classification.initialize();
		
		try {
			ArrayList<String> sentences = json.getTwitterComments(TweeterJson);
			classification.writeClassificationToFile(sentences, SentimentalJson);		
			ArrayList<String> SentimentalTypeArray = json.getSentimentalData(SentimentalJson);
			Table table = newPlot.createTableByCount(SentimentalTypeArray);
			newPlot.printTable(table);
			newPlot.displayBarChart(table, PlottingFile, jsBarChartVarTwitter);
			newPlot.displayPieChart(table, PlottingFile, jsPieChartVarTwitter);	
		} catch(CustomError e) {
			System.out.println(e.getMessage());
		} catch(NullPointerException e) {
			System.out.println(e.getMessage());
		} catch(ParseException e) {
			System.out.println(e.getMessage());
		} catch(FileNotFoundException e) {
			System.out.println(e.getMessage());
		} catch(IOException e) {
			System.out.println(e.getMessage());
		} 

		return "plotting";
	}
	
	@PostMapping("statsreddit")
	public String postStatsReddit(Model model) {
		// Instance for Sentimental Analysis
		classification.initialize();
		
		try {
			ArrayList<String> sentences = json.getRedditComments(RedditJson);
			double[] sentimentalScore = new double[5];
			sentimentalScore = classification.SentimentalCalculation(sentences);
			Statistics statistics = newStats.printStats(sentimentalScore);
			model.addAttribute("statistics", statistics);
		} catch(CustomError e) {
			System.out.println(e.getMessage());
		} catch(NullPointerException e) {
			System.out.println(e.getMessage());
		} catch(IOException e) {
			System.out.println(e.getMessage());
		} catch(ParseException e) {
			System.out.println(e.getMessage());
		}
		
		return "plotting";
	}
	
	@PostMapping("statstwitter")
	public String postStatsTwitter(Model model) {
		// Instance for Sentimental Analysis
		classification.initialize();
		
		try {
			ArrayList<String> sentences = json.getTwitterComments(TweeterJson);
			double[] sentimentalScore = new double[5];
			sentimentalScore = classification.SentimentalCalculation(sentences);	
			Statistics statistics = newStats.printStats(sentimentalScore);
			model.addAttribute("statistics", statistics);
		} catch(CustomError e) {
			System.out.println(e.getMessage());
		} catch(NullPointerException e) {
			System.out.println(e.getMessage());
		} catch(IOException e) {
			System.out.println(e.getMessage());
		} catch(ParseException e) {
			System.out.println(e.getMessage());
		}
		
		return "plotting";
	}
}
