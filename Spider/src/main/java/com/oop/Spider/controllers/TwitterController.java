package com.oop.Spider.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.oop.Spider.Interface.SearchInterface;
import com.oop.Spider.objects.Searchable;
import com.oop.Spider.services.TwitterService;

import errorhandling.CustomError;
import twitter4j.TwitterException;

@Controller
public class TwitterController extends SearchInterface{
	
	@Autowired
	private TwitterService twitterService;
	
	@GetMapping("/twittersearch")
	public String getTwitterPage(Model model) {
		model.addAttribute("search", new Searchable());
		return "twitter";
	}
	
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
