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

import com.oop.Spider.errorhanding.CustomError;
import com.oop.Spider.objects.Statistics;
import com.oop.Spider.services.JsonService;
import com.oop.Spider.services.PlottingService;
import com.oop.Spider.services.SentimentalService;
import com.oop.Spider.services.StatisticsService;

import tech.tablesaw.api.Table;

@Controller 
public class PlottingController {
	
	//file name that map to json file
	private static final String RedditJson = "./data.json";
	private static final String TweeterJson = "./data2.json";
	private static final String SentimentalJson = "./sentimentaldata.json";
	private static final String PlottingFile = "./src/main/resources/templates/plotting.html";
	
	//name for javascript variables that will be used for the Bar Chart and Pie Chart
	private static final String jsBarChartVarReddit = "redditBarPlot";
	private static final String jsPieChartVarReddit = "redditPieChart";
	private static final String jsBarChartVarTwitter = "twitterBarPlot";
	private static final String jsPieChartVarTwitter = "twitterPieChart";

	//dependency injection
	@Autowired
	SentimentalService classification = new SentimentalService();

	@Autowired
	PlottingService newPlot = new PlottingService();
	
	@Autowired
	StatisticsService newStats = new StatisticsService();
	
	@Autowired
	JsonService json= new JsonService();

	/**
	 * <p>This method uses the Spring Boot @GetMapping annotation for HTTP get method to map to 
	 * the path: '/plotting' </p>
	 * @return /plotting view template
	 */
	@GetMapping("/plotting")
	public String getPlot() {
		return "plotting";
	}
	
	/**
	 * <p>This method uses the Spring Boot @GetMapping annotation for HTTP get method to map to 
	 * the path: '/plottingreddit' </p>
	 * @return /plotting view template
	 */
	@GetMapping("/plottingreddit")
	public String getPlotReddit() {
		return "plotting";
	}
	
	/**
	 * <p>This method uses the Spring Boot @GetMapping annotation for HTTP get method to map to 
	 * the path: '/plottingtwitter' </p>
	 * @return /plotting view template
	 */
	@GetMapping("/plottingtwitter")
	public String getPlotTwitter() {
		return "plotting";
	}
	
	/**
	 * <p>This method uses the Spring Boot @GetMapping annotation for HTTP get method to map to 
	 * the path: '/statsreddit' </p>
	 * @return /plotting view template
	 */
	@GetMapping("/statsreddit")
	public String getStatsReddit() {
		return "plotting";
	}
	
	/**
	 * <p>This method uses the Spring Boot @GetMapping annotation for HTTP get method to map to 
	 * the path: '/statstwitter' </p>
	 * @return /plotting view template
	 */
	@GetMapping("/statstwitter")
	public String getStatsTwitter() {
		return "plotting";
	}
	
	
	/**
	 * <p>This method uses get the reddit comments data from the a specified json file and parse it into the CoreNLP 
	 * library for sentimental analysis. Then, a bar chart and pie chart will be generated from sentimental result</p>
	 * <p>This method also uses the Spring Boot @PostMapping annotation for HTTP post method and map to request 
	 * to path: '/plottingreddit' </p>
	 * @return /plotting view template
	 */
	@PostMapping("/plottingreddit")
	public String postPlotReddit() {
		classification.initialize();
		
		try {
			// Get data from json and stores it in an Array List
			ArrayList<String> sentences = json.getRedditComments(RedditJson);
			// Parse sentences to sentimental library for classification and write it to a file
			classification.writeClassificationToFile(sentences, SentimentalJson);
			// Retrieve sentimental data from file
			ArrayList<String> SentimentalTypeArray = json.getSentimentalData(SentimentalJson);
			// Refromat sentimental data into a Table
			Table table = newPlot.createTableByCount(SentimentalTypeArray);
			newPlot.printTable(table);
			// insert Bar Chart into html template at /src/main/resources/templates/plotting.html
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
	
	/**
	 * <p>This method uses get the twitter's tweets data from the a specified json file and parse it into the CoreNLP 
	 * library for sentimental analysis. Then, a bar chart and pie chart will be generated from sentimental result</p>
	 * <p>This method also uses the Spring Boot @PostMapping annotation for HTTP post method and map to request 
	 * to path: '/plottingtwitter' </p>
	 * @return /plotting view template
	 */
	@PostMapping("/plottingtwitter")
	public String postPlotTwitter() {
		classification.initialize();
		
		try {
			// Get data from json and stores it in an Array List
			ArrayList<String> sentences = json.getTwitterComments(TweeterJson);
			// Parse sentences to sentimental library for classification and write it to a file
			classification.writeClassificationToFile(sentences, SentimentalJson);		
			// Retrieve sentimental data from file
			ArrayList<String> SentimentalTypeArray = json.getSentimentalData(SentimentalJson);
			// Refromat sentimental data into a Table
			Table table = newPlot.createTableByCount(SentimentalTypeArray);
			newPlot.printTable(table);
			// insert Bar Chart into html template at /src/main/resources/templates/plotting.html
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
	
	
	/**
	 * <p>This method uses get the reddit's comment data from the a specified json file and parse it into the CoreNLP 
	 * library for sentimental analysis. Then, a statistical summary will be generated from those result</p>
	 * <p>This method also uses the Spring Boot @PostMapping annotation for HTTP post method and map to request 
	 * to path: '/statsreddit' </p>
	 * @param model - encapsulates data to be passed between view page
	 * @return /plotting view template
	 */
	@PostMapping("statsreddit")
	public String postStatsReddit(Model model) {
		// Instance for Sentimental Analysis
		classification.initialize();
		
		try {
			// Get data from json and stores it in an Array List
			ArrayList<String> sentences = json.getRedditComments(RedditJson);
			double[] sentimentalScore = new double[5];
			// Parse sentences to sentimental library for classification and stores it in an array
			sentimentalScore = classification.SentimentalCalculation(sentences);
			Statistics statistics = newStats.printStats(sentimentalScore);
			// encapsulates statistics result and send the response to the view page
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
	
	
	/**
	 * <p>This method uses get the twitter's tweets data from the a specified json file and parse it into the CoreNLP 
	 * library for sentimental analysis. Then, a statistical summary will be generated from those result</p>
	 * <p>This method also uses the Spring Boot @PostMapping annotation for HTTP post method and map to request 
	 * to path: '/statstwitter' </p>
	 * @param model - encapsulates data to be passed between view page
	 * @return /plotting view template
	 */
	@PostMapping("statstwitter")
	public String postStatsTwitter(Model model) {
		// Instance for Sentimental Analysis
		classification.initialize();
		
		try {
			// Get data from json and stores it in an Array List
			ArrayList<String> sentences = json.getTwitterComments(TweeterJson);
			double[] sentimentalScore = new double[5];
			// Parse sentences to sentimental library for classification and stores it in an array
			sentimentalScore = classification.SentimentalCalculation(sentences);	
			Statistics statistics = newStats.printStats(sentimentalScore);
			// encapsulates statistics result and send the response to the view page
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
