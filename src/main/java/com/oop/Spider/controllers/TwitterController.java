package com.oop.Spider.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.oop.Spider.Interface.SearchInterface;
import com.oop.Spider.errorhanding.CustomError;
import com.oop.Spider.objects.Searchable;
import com.oop.Spider.services.TwitterService;

import twitter4j.TwitterException;

/** Controller for Twitter
* @version 1.0
*/
@Controller
public class TwitterController extends SearchInterface{
	
	//dependency injection
	@Autowired
	private TwitterService twitterService;
	
	/**
	 * <p>This method uses the Spring Boot @GetMapping annotation for HTTP get method to map to 
	 * the path: '/twittersearch' </p>
	 * @param model - encapsulates search result to be passed between view page
	 * @return /twitter view template
	 */
	@GetMapping("/twittersearch")
	public String getTwitterPage(Model model) {
		model.addAttribute("search", new Searchable());
		return "twitter";
	}
	
	/**
	 * <p>This method also uses the Spring Boot @PostMapping annotation for HTTP post method and map to request 
	 * to path: '/twittersearch' </p>\
	 * <p>In addition, it catches IOException, CustomError and TwitterException exceptions from the Service class
	 * @param model - encapsulates search result to be passed between view page
	 * @return /result1 view template
	 */
	@PostMapping("/twittersearch")
	public String search(Searchable search, Model model) {
		try {
			twitterService.searchHashTag(search, model);	
		} catch (CustomError e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} catch (TwitterException e) {
			System.out.println(e.getMessage());
		}
		return "result1";
	}
}
