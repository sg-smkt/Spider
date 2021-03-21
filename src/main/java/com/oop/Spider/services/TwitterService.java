package com.oop.Spider.services;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.oop.Spider.errorhanding.CustomError;
import com.oop.Spider.objects.Searchable;

import twitter4j.TwitterException;

/** Driver class for Twitter API 
 * @author Sim Poh Kang Sebastian 
 * @version 1.0
 */
@Service
public class TwitterService{
	
	private static final String consumerkey = "2bGiyfbtRzEoirN2eCBPrTa3x";
	private static final String consumerSecret = "PfKt8S5yXFKChcOBRfoo858YDSvWnpTxE4KDoNivtiYrtuDD6e";
	private static final String accessToken = "810785369557962752-XRBRcHpcC2484WdJ2s7fCd5OL2457aX";
	private static final String accessTokenSecret = "cMxMh0k0oYrIG6odxFAID0V54niGe2bzKU1HMIXaUFsXC";
	
	/**
	 * <p>This method encapsulates the search item to pass passed into model and calls the initialization method</p>
	 * @param search - item to be searched 
	 * @param model - encapsulates search result to be passed between view page
	 * @see <a href="https://www.tutorialspoint.com/spring/spring_web_mvc_framework.htm">Spring Boot MVC concept</a>
	 * @since 1.0
	 */
	public void searchHashTag(Searchable search, Model model) throws IOException, CustomError, TwitterException {
		model.addAttribute("twitter", search);
		initialize(search.getSearch());
	}
	
	/**
	 * <p>This method initialize twitter API and crawl for the specified result</p>
	 * @param search - item to be searched
	 * @throws IOException - Thrown if an error occurs during I/O operation
	 * @throws TwitterException - An exception class that will be thrown when TwitterAPI calls are failed.
	 * @since 1.0
	 */
	public static void initialize(String search) throws IOException, CustomError, TwitterException {
		CrawlTwitter bigBird = new CrawlTwitter();
		bigBird.authenticate(consumerkey, consumerSecret, accessToken, accessTokenSecret);
		bigBird.Crawl(search);	
	}
}
