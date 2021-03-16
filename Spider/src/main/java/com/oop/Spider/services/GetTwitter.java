package com.oop.Spider.services;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;
import twitter4j.TwitterException;

@Service
public class GetTwitter {
	
	 private Twitter twitterr;
     ArrayList<String> TweetsList = new ArrayList<String>();
     QueryResult result;
	
	  public void authenticate(String consumerkey, String consumerSecret, String accessToken, String accessTokenSecret){
	    // Makes an instance of Twitter - this is re-useable and thread safe.
	    // Connects to Twitter and performs authorizations.
		 ConfigurationBuilder cb = new ConfigurationBuilder();
		 cb.setDebugEnabled(true)
		   .setOAuthConsumerKey(consumerkey)
		   .setOAuthConsumerSecret(consumerSecret)
		   .setOAuthAccessToken(accessToken)
		   .setOAuthAccessTokenSecret(accessTokenSecret);
		 TwitterFactory tf = new TwitterFactory(cb.build());
		 twitterr = tf.getInstance(); 
		}
	  
	  public void saQuery (String searchTerm){
	   	  Query query = new Query(searchTerm);
	   	  query.setCount(5);
	   	  
	   	  try {
	   		  result = twitterr.search(query);
	   		  int counter = 0;
	   		  System.out.println("Count: " + result.getTweets().size());
	   		  for (Status tweet: result.getTweets()) {
	   			  counter ++;
//	   			  System.out.println("Tweet #" + counter +": @"+tweet.getUser().getName() + " tweeted \"" + tweet.getText() + "\" Retweets:" + tweet.getRetweetCount());
//	   			  System.out.println("=============");
	   			  TweetsList.add(tweet.getText());
	   		  }
	   	  }
	   	  catch(TwitterException e) {
	   		  e.printStackTrace();
	   	  }
	   	  System.out.println(TweetsList);
	   	  
	   	  ConvertToJson(searchTerm);
   	  }  
	  
	  
	  private void ConvertToJson(String searchTerm)
	  {
		  JSONObject json = new JSONObject();
		  json.put("Hashtag Name", searchTerm);
		  JSONArray jarray = new JSONArray();
		  for (int i = 0; i < result.getTweets().size(); i++) {
			  for (Status tweet: result.getTweets())
			  {
				  jarray.add(tweet.getText());
			  }
		  }
		  json.put("Tweets", jarray);
		  WriteToFile(json);
	  }
	  
	  private void WriteToFile(JSONObject json) {
		  try(FileWriter file = new FileWriter("data2.json")){
			  file.write(json.toString());
			  file.flush();
		  } catch(IOException e) {
			  e.printStackTrace();
		  }
	  }
}
