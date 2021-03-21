package com.oop.Spider.services;

import java.io.IOException;
import java.util.Scanner;

import com.oop.Spider.errorhanding.CustomError;
import com.oop.Spider.objects.Searchable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

/** Driver class for Reddit API 
 * @author Seng Sam Kiat
 * @version 1.0
 */
@Service
public class RedditService {
	private static final String username = "JamesTsui";
	private static final String password = "3Ss9816142b";
	private static final String clientId = "mULNoJ8YJuJYGQ";
	private static final String clientSecret = "KHJZ9NQbxNILzE4bsa5425W9tUxL9Q";
	
	@Autowired
	private static CrawlReddit getReddit = new CrawlReddit();
	
	/**
	 * <p>This method encapsulates the search item to pass passed into model and calls the initalization method</p>
	 * <p>Also, it calls the method to query the search object to search for comments within subreddit</p>
	 * @param search - item to be searched 
	 * @param model - encapsulates search result to be passed between view page
	 * @throws IOException - Thrown if an error occurs during I/O operation
	 * @see <a href="https://www.tutorialspoint.com/spring/spring_web_mvc_framework.htm">Spring Boot MVC concept</a>
	 * @since 1.0
	 */
	public void searchReddit(Searchable search, Model model) throws CustomError, IOException {
		model.addAttribute("reddit", search);
		initialize();
		getReddit.Crawl(search.getSearch());
	}
	
	/**
	 * <p>This method encapsulates the search item to pass passed into model and calls the initalization method</p>
	 * <p>Also, it calls the method to query the search object to search for relevent sub reddit</p>
	 * @param search - item to be searched 
	 * @param model - encapsulates search result to be passed between view page
	 * @throws IOException - Thrown if an error occurs during I/O operation
	 * @see <a href="https://www.tutorialspoint.com/spring/spring_web_mvc_framework.htm">Spring Boot MVC concept</a>
	 * @since 1.0
	 */
	public void searchSubReddit(Searchable search, Model model) throws CustomError, IOException {
		model.addAttribute("reddit", search);
		initialize();
		getReddit.SearchSubreddits(search.getSearch(), 100);
	}
	
	/**
	 * <p>This method encapsulates the search item to pass passed into model and calls the initalization method</p>
	 * <p>Also, it calls the method to query the search object to search for post containing the title</p>
	 * @param search - item to be searched 
	 * @param model - encapsulates search result to be passed between view page
	 * @throws IOException - Thrown if an error occurs during I/O operation
	 * @see <a href="https://www.tutorialspoint.com/spring/spring_web_mvc_framework.htm">Spring Boot MVC concept</a>
	 * @since 1.0
	 */
	public void searchTitle(Searchable search, Model model) throws CustomError, IOException {
		model.addAttribute("reddit", search);
		initialize();
		getReddit.SearchTitle(search.getSearch());
	}
	
	/**
	 * <p>This method initializes reddit api using the required information</p>
	 * @since 1.0 
	 */
	private void initialize() {
		getReddit = new CrawlReddit(username, password, clientId, clientSecret);
	}
}
