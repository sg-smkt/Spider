package spiderProject;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;
import twitter4j.TwitterException;

import java.util.ArrayList;

public class Twitterer {
	
	 private Twitter twitter;
     ArrayList<String> TweetsList = new ArrayList<String>();

    
     public Twitter initialize()
     {
        // Makes an instance of Twitter - this is re-useable and thread safe.
        // Connects to Twitter and performs authorizations.
    	 ConfigurationBuilder cb = new ConfigurationBuilder();
    	 cb.setDebugEnabled(true)
    	   .setOAuthConsumerKey("2bGiyfbtRzEoirN2eCBPrTa3x")
    	   .setOAuthConsumerSecret("PfKt8S5yXFKChcOBRfoo858YDSvWnpTxE4KDoNivtiYrtuDD6e")
    	   .setOAuthAccessToken("810785369557962752-XRBRcHpcC2484WdJ2s7fCd5OL2457aX")
    	   .setOAuthAccessTokenSecret("cMxMh0k0oYrIG6odxFAID0V54niGe2bzKU1HMIXaUFsXC");
    	 TwitterFactory tf = new TwitterFactory(cb.build());
    	 twitter = tf.getInstance(); 
    	 return twitter;
     }
     
     public void saQuery (String searchTerm)
     {
   	  Query query = new Query (searchTerm);
   	  query.setCount(100);
   	  
   	  try {
   		  QueryResult result = twitter.search(query);
   		  int counter = 0;
   		  System.out.println("Count: " + result.getTweets().size());
   		  for (Status tweet: result.getTweets()) {
   			  counter ++;
   			  System.out.println("Tweet #" + counter +": @"+tweet.getUser().getName() + " tweeted \"" + tweet.getText() + "\" Retweets:" + tweet.getRetweetCount());
   			  System.out.println("=============");
   			  TweetsList.add(tweet.getText());
   		  }
   	  }
   	  catch(TwitterException e) {
   		  e.printStackTrace();
   	  }
   	  System.out.println(TweetsList);
      }
  
}
