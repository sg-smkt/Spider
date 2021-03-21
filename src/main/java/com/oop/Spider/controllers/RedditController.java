package com.oop.Spider.controllers;

import com.oop.Spider.Interface.SearchInterface;
import com.oop.Spider.errorhanding.CustomError;
import com.oop.Spider.objects.Searchable;
import com.oop.Spider.services.RedditService;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/** Controller for Reddit
* @version 1.0
*/
@Controller 
public class RedditController extends SearchInterface{
	
	//dependency injection
	@Autowired
	private RedditService redditService;
	
	/**
	 * <p>This method uses the Spring Boot @GetMapping annotation for HTTP get method to map to 
	 * the path: '/redditsearch' </p>
	 * @param model - encapsulates search result to be passed between view page
	 * @return /reddit view template
	 */
	@GetMapping("/redditsearch")
	public String getRedditPage(Model model) {
		model.addAttribute("search", new Searchable());
		return "reddit";
	}
	
	/**
	 * <p>This method also uses the Spring Boot @PostMapping annotation for HTTP post method and map to request 
	 * to path: '/redditsearch' </p>\
	 * <p>In addition, it catches IOException and CustomError exceptions from the Service class
	 * @param model - encapsulates search result to be passed between view page
	 * @return /result view template
	 */
	@PostMapping("/redditsearch")
	public String search(Searchable search, Model model) {
		try {
		redditService.searchReddit(search, model);
		} catch(IOException e) {
			System.out.println(e.getMessage());
		} catch(CustomError e) {
			System.out.println(e.getMessage());
		}
		return "result";
	}
	
	/**
	 * <p>This method also uses the Spring Boot @PostMapping annotation for HTTP post method and map to request 
	 * to path: '/subredditsearch' </p>\
	 * <p>In addition, it catches IOException and CustomError exceptions from the Service class
	 * @param model - encapsulates search result to be passed between view page
	 * @return /result view template
	 */
	@PostMapping("/subredditsearch")
	public String searchsubreddit(Searchable search, Model model) {
		try {
		redditService.searchSubReddit(search, model);
		} catch(IOException e) {
			System.out.println(e.getMessage());
		} catch(CustomError e) {
			System.out.println(e.getMessage());
		}
		return "result";
	}
	
	/**
	 * <p>This method also uses the Spring Boot @PostMapping annotation for HTTP post method and map to request 
	 * to path: '/titlesearch' </p>\
	 * <p>In addition, it catches IOException and CustomError exceptions from the Service class
	 * @param model - encapsulates search result to be passed between view page
	 * @return /result view template
	 */
	@PostMapping("/titlesearch")
	public String searchtitle(Searchable search, Model model) {
		try {
		redditService.searchTitle(search, model);
		} catch(IOException e) {
			System.out.println(e.getMessage());
		} catch(CustomError e) {
			System.out.println(e.getMessage());
		}
		return "result";
	}
}
