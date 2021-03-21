package com.oop.Spider.controllers;

import java.io.IOException;

import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oop.Spider.errorhanding.CustomError;
import com.oop.Spider.objects.Searchable;
import com.oop.Spider.services.JsonService;
import com.oop.Spider.services.RedditService;
import com.oop.Spider.services.TwitterService;

import twitter4j.TwitterException;

//Using Rest Controller to automatically converts data into json format in the view page
@RestController
public class RestApiController {
	
	//file name that map to json file
	private static final String RedditJson = "./data.json";
	private static final String RedditSubRedditJson = "./rsubreddit.json";
	private static final String RedditTitleJson = "./rtitle.json";
	private static final String TwitterJson = "./data2.json";
	
	//dependency injection
	@Autowired
	private JsonService jsonService;

	/**
	 * <p>This method uses the Spring Boot @GetMapping annotation for HTTP get method to map to 
	 * the path: '/redditapi' </p>
	 * @return Reddit's comments object to be converted to JSON format
	 */
	@GetMapping("/redditapi")
	public Object searchSubreddit() throws ParseException, IOException {
		return jsonService.getRedditObject(RedditJson);
		
	}
	
	/**
	 * <p>This method uses the Spring Boot @GetMapping annotation for HTTP get method to map to 
	 * the path: '/rsubredditapi' </p>
	 * @return Reddit's comments object to be converted to JSON format
	 */
	@GetMapping("/rsubredditapi")
	public Object searchSubredditRelevance() throws ParseException, IOException {
		return jsonService.getRedditObject(RedditSubRedditJson);
		
	}
	
	/**
	 * <p>This method uses the Spring Boot @GetMapping annotation for HTTP get method to map to 
	 * the path: '/rtitleapi' </p>
	 * @return Reddit's comments object to be converted to JSON format
	 */
	@GetMapping("/rtitleapi")
	public Object searchSubredditTitle() throws ParseException, IOException {
		return jsonService.getRedditObject(RedditTitleJson);
		
	}

	/**
	 * <p>This method uses the Spring Boot @GetMapping annotation for HTTP get method to map to 
	 * the path: '/twitterapi' </p>
	 * @return Reddit's comments object to be converted to JSON format
	 */
	@GetMapping("/twitterapi")
	public Object searchHashtag() throws ParseException, IOException, CustomError, TwitterException {
		return jsonService.getTwitterObject(TwitterJson);
	}
	
}