package com.oop.Spider.unittesting;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.oop.Spider.errorhanding.CustomError;
import com.oop.Spider.services.CrawlReddit;

class CrawlRedditTest
{
	private static CrawlReddit redditAuthTest;
	private String term1 = "spacex launch";
	private String term2 = "spacex_launch!$@";
	private String subreddit = "choosing*beggars";
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception
	{
		redditAuthTest = new CrawlReddit("JamesTsui", "3Ss9816142b", "mULNoJ8YJuJYGQ", "KHJZ9NQbxNILzE4bsa5425W9tUxL9Q");
	}

	@Test
	private void SearchTitleTest()
	{
		assertThrows(CustomError.class,() -> {redditAuthTest.SearchTitle(term2);});
	}

	@Test
	private void SearchSubredditTest() throws IOException
	{
		try
		{
			redditAuthTest.SearchSubreddits(term1, 1);
			fail("Correct Input");
		}
		catch(CustomError e)
		{
		}
	}
	
	@Test
	private void CrawlTest()
	{
		assertThrows(CustomError.class,() -> {redditAuthTest.Crawl(subreddit);});
	}
}