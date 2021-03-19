package com.oop.Spider.controllers;

import com.oop.Spider.Interface.SearchInterface;
import com.oop.Spider.objects.Searchable;
import com.oop.Spider.services.RedditService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller 
public class RedditController extends SearchInterface{
	
	@Autowired
	private RedditService redditService;
	
	@GetMapping("/redditsearch")
	public String getRedditPage(Model model) {
		model.addAttribute("search", new Searchable());
		return "reddit";
	}
	
	@PostMapping("/redditsearch")
	public String search(Searchable search, Model model) {
		redditService.searchSubreddit(search, model);
		return "result";
	}
}
