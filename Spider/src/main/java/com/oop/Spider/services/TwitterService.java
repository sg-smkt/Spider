package com.oop.Spider.services;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.oop.Spider.objects.Searchable;

import errorhandling.CustomError;
import twitter4j.TwitterException;

@Service
public class TwitterService{
	
	private static final String consumerkey = "2bGiyfbtRzEoirN2eCBPrTa3x";
	private static final String consumerSecret = "PfKt8S5yXFKChcOBRfoo858YDSvWnpTxE4KDoNivtiYrtuDD6e";
	private static final String accessToken = "810785369557962752-XRBRcHpcC2484WdJ2s7fCd5OL2457aX";
	private static final String accessTokenSecret = "cMxMh0k0oYrIG6odxFAID0V54niGe2bzKU1HMIXaUFsXC";
	
	
	public void searchHashTag(Searchable search, Model model) throws IOException, CustomError, TwitterException {
		model.addAttribute("twitter", search);
		initialize(search.getSearch());
	}
	
	public static void initialize(String search) throws IOException, CustomError, TwitterException {
		CrawlTwitter bigBird = new CrawlTwitter();
		bigBird.authenticate(consumerkey, consumerSecret, accessToken, accessTokenSecret);
		bigBird.Crawl(search);	
	}
}
