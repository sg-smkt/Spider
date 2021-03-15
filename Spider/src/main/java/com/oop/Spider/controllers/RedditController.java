package com.oop.Spider.controllers;

import com.oop.Spider.objects.Reddit;
import com.oop.Spider.objects.TwitterObject;
import com.oop.Spider.services.RedditService;
import com.oop.Spider.services.TwitterService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller 
public class RedditController {
	
	@Autowired
	private RedditService redditService;
	
	@GetMapping("/redditsearch")
	public String getRedditPage(Model model) {
		model.addAttribute("reddit", new Reddit());
		return "reddit";
	}
	
	@PostMapping("/redditsearch")
	public String searchSubreddit(Reddit reddit, Model model) {
		redditService.searchSubreddit(reddit, model);
		return "result";
	}
}
