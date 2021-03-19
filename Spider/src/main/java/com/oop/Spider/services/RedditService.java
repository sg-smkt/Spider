package com.oop.Spider.services;

import java.util.Scanner;

import com.oop.Spider.services.GetReddit;
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
	private static GetReddit getReddit = new GetReddit();
	
	public void searchSubreddit(Searchable search, Model model) {
		initialize(search.getSearch());
	}
	
	private void initialize(String search) {
		getReddit = new GetReddit(username, password, clientId, clientSecret);
		SearchTitle(search);
		SearchTitle(search);
		Query(search);
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
	
	/*
	 * Search Title 
	 * Search SubReddit Names
	 */
}
