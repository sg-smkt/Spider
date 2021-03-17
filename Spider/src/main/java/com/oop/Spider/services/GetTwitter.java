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
     
     private static final String TweeterJson = "./data2.json";
	
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
	   		  System.out.println("Count: " + result.getTweets().size());
	   		  for (Status tweet: result.getTweets()) {
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
		  
		  JsonService newjson = new JsonService();
		  newjson.writeToFile(TweeterJson, json);
	  }
}
