package com.oop.Spider.Interface;

import java.io.IOException;

import com.oop.Spider.errorhanding.CustomError;

import twitter4j.TwitterException;

public abstract class CrawlerInterface {
	public abstract void Crawl(String term) throws CustomError, IOException, TwitterException;
}