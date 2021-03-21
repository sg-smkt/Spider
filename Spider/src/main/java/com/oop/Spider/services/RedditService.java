package com.oop.Spider.services;

import java.util.Scanner;

import com.oop.Spider.objects.Searchable;

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
	private static CrawlReddit getReddit = new CrawlReddit();
	
	public void searchReddit(Searchable search, Model model) {
		model.addAttribute("reddit", search);
		initialize();
		Query(search.getSearch());
	}
	
	public void searchSubReddit(Searchable search, Model model) {
		model.addAttribute("reddit", search);
		initialize();
		Search(search.getSearch());
	}
	
	public void searchTitle(Searchable search, Model model) {
		model.addAttribute("reddit", search);
		initialize();
		SearchTitle(search.getSearch());
	}
	
	private void initialize() {
		getReddit = new CrawlReddit(username, password, clientId, clientSecret);
	}
	
	private static void Search(String search)
	{
		try
		{
			getReddit.SearchSubreddits(search, 100);
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
	}

	private static void SearchTitle(String search)
	{
		try
		{
			getReddit.SearchTitle(search);
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
	}
	
	private static void Query(String search)
	{
		try
		{			
			getReddit.Crawl(search);
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
	}
}
