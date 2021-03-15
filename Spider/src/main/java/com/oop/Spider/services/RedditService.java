package com.oop.Spider.services;

import com.oop.Spider.services.GetReddit;
import com.oop.Spider.objects.Reddit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class RedditService {
	private static final String username = "JamesTsui";
	private static final String password = "3Ss9816142b";
	private static final String clientId = "mULNoJ8YJuJYGQ";
	private static final String clientSecret = "KHJZ9NQbxNILzE4bsa5425W9tUxL9Q";
	
	@Autowired
	private static GetReddit getReddit = new GetReddit();
	
	public void searchSubreddit(Reddit reddit, Model model) {
		model.addAttribute("reddit", reddit);
		initialize(reddit.getSearch());
		
		
		System.out.println("Crawling Done");
	}
	
	private void initialize(String search) {
		getReddit = new GetReddit(username, password, clientId, clientSecret);
		Query(search);
	}
	
	private void Query(String search)
	{
		try
		{
			getReddit.SetSubreddit(search);
			
			getReddit.Crawl();
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
	}
}
