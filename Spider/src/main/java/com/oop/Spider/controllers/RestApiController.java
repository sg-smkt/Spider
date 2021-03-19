package com.oop.Spider.controllers;

import java.io.IOException;

import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oop.Spider.objects.Searchable;
import com.oop.Spider.services.JsonService;
import com.oop.Spider.services.RedditService;
import com.oop.Spider.services.TwitterService;

import errorhandling.CustomError;
import twitter4j.TwitterException;

@RestController
public class RestApiController {
	
	private static final String RedditJson = "./data.json";
	private static final String RedditSubRedditJson = "./rsubreddit.json";
	private static final String RedditTitleJson = "./rtitle.json";

	private static final String TwitterJson = "./data2.json";
	
	@Autowired
	private JsonService jsonService;

	@GetMapping("/redditapi")
	public Object searchSubreddit() throws ParseException, IOException {
		return jsonService.getRedditObject(RedditJson);
		
	}
	
	@GetMapping("/rsubredditapi")
	public Object searchSubredditRelevance() throws ParseException, IOException {
		return jsonService.getRedditObject(RedditSubRedditJson);
		
	}
	
	@GetMapping("/rtitleapi")
	public Object searchSubredditTitle() throws ParseException, IOException {
		return jsonService.getRedditObject(RedditTitleJson);
		
	}

	@GetMapping("/twitterapi")
	public Object searchHashtag() throws ParseException, IOException, CustomError, TwitterException {
		return jsonService.getTwitterObject(TwitterJson);
	}
	
}