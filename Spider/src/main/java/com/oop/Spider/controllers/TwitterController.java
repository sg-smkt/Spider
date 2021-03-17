package com.oop.Spider.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.oop.Spider.objects.Searchable;
import com.oop.Spider.services.TwitterService;

@Controller
public class TwitterController {
	
	@Autowired
	private TwitterService twitterService;
	
	@GetMapping("/twittersearch")
	public String getTwitterPage(Model model) {
		model.addAttribute("search", new Searchable());
		return "twitter";
	}
	
	@PostMapping("/twittersearch")
	public String searchHashtag(Searchable search, Model model) {
		twitterService.searchHashTag(search, model);
		return "result1";
	}
}
