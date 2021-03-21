package com.oop.Spider.unittesting;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import java.lang.IllegalStateException;
import java.util.ArrayList;

import com.oop.Spider.services.CrawlTwitter;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;
import twitter4j.TwitterException;

/** Crawl Twitter Unit Testing
 * @author Sim Poh Kang Sebastian
 * @version 1.0
 */
class CrawlTwitterTest {
	private static final String consumerkey = "2bGiyfbtRzEoirN2eCBPrTa3x";
	private static final String consumerSecret = "PfKt8S5yXFKChcOBRfoo858YDSvWnpTxE4KDoNivtiYrtuDD6e";
	private static final String accessToken = "810785369557962752-XRBRcHpcC2484WdJ2s7fCd5OL2457aX";
	private static final String accessTokenSecret = "cMxMh0k0oYrIG6odxFAID0V54niGe2bzKU1HMIXaUFsXC";
	
    ArrayList<String> TweetsList = new ArrayList<String>();
    QueryResult result;
    ConfigurationBuilder cb = new ConfigurationBuilder()
    		.setDebugEnabled(true)
 		   .setOAuthConsumerKey(consumerkey)
 		   .setOAuthConsumerSecret(consumerSecret)
 		   .setOAuthAccessToken(accessToken)
 		   .setOAuthAccessTokenSecret(accessTokenSecret);
	TwitterFactory tf = new TwitterFactory(cb.build());
	Twitter twitterr = tf.getInstance();
	CrawlTwitter tester = new CrawlTwitter();


	@Test
	//If API keys are all null, error should be thrown
	void testNullAuthenticate() throws IllegalStateException{
		String consumerkey, consumerSecret, accessToken, accessTokenSecret;
		consumerkey = consumerSecret = accessToken = accessTokenSecret = null;
		tester.authenticate(consumerkey, consumerSecret, accessToken, accessTokenSecret);
		assertThrows(IllegalStateException.class,() ->{tester.Crawl("word");
		});
	}
	
	@Test
	//Determine accuracy of search query by checking whether text from input can be found in text from results
	void testQueryResult() throws TwitterException {
		String word = "hello";
		Query query = new Query(word);
		assertTrue(twitterr.search(query).getTweets().get(0).getText().toLowerCase().contains("hello"));
	}
	
	@Test
	//If search term is null, throws null pointer exception error
	void testConvertToJsonNull() {
		assertThrows(NullPointerException.class,() ->{tester.ConvertToJson(null);
		});
	}
}