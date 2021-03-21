package com.oop.Spider.unittesting;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;

import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;

import com.oop.Spider.errorhanding.CustomError;
import com.oop.Spider.services.CrawlReddit;

/** Crawl Reddit Unit Testing
 * @author Seng Sam Kiat
 * user sg-smkt at github.com/sg-smkt/Spider
 * @version 1.0
 */
public class CrawlRedditTest
{
	private static CrawlReddit redditAuthTest = new CrawlReddit();
	private String term1 = "spacex launch";
	private String term2 = "spacex_launch!$@";
	private String subreddit = "choosing*beggars";
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception
	{
		redditAuthTest = new CrawlReddit();
	}

	@Test
	public void SearchTitleTest()
	{
		//Tests whether an invalid search term has been inputed by the user
		assertThrows(CustomError.class,() -> {redditAuthTest.SearchTitle(term2);});
	}

	@Test
	public void SearchSubredditTest() throws IOException, CustomError
	{	
		//Tests whether a valid search term has been inputed by the user
		assertThrows(NullPointerException.class,() -> {redditAuthTest.SearchSubreddits(term1, 1);});
	}
	
	@Test
	public void CrawlTest()
	{
		//Tests for a valid subreddit name that has to be 3-20 letters long, no special characters except underscore.
		assertThrows(CustomError.class,() -> {redditAuthTest.Crawl(subreddit);});
	}
}
