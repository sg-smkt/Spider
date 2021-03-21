package com.oop.Spider.services;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import errorhandling.CustomError;

@Service
public class JsonService {
	
	/**
	 * <p> This method read the data from the specified file and returns the json object</p>
	 * @param filename specify a value file to be read into json object
	 * @return The json Object
	 * @throws FileNotFoundException If the file is not found 
	 * @throws IOException If there's error during input or output
	 * @throws ParseException If there's error during parsing of data
	 * @see <a href="https://code.google.com/archive/p/json-simple/">Json Simple</a>
	 * @since 1.0
	 */
	public static JSONObject read(String filename) throws IOException, ParseException, FileNotFoundException{
		JSONParser jsonParser = new JSONParser();
		JSONObject jsonObj = null;
		FileReader reader = new FileReader(filename);
		Object obj = jsonParser.parse(reader);
		jsonObj = (JSONObject) obj;
		return jsonObj;
	}
	
	/**
	 * <p> This method write data to the file specified </p>
	 * @param filename The filename to write the data to
	 * @param json The data containing infomation to be written to the specified file
	 * @throws CustomError Thrown if filename is empty
	 * @since 1.0
	 */
	public void writeToFile(String filename, Object data) throws IOException, CustomError {
		if ("".equals(filename)) {
			throw new CustomError("Filename is empty");
		} else {
			FileWriter file = new FileWriter(filename);
			file.write(data.toString());
			file.flush();
			file.close();	
		}
	  }
	
	
	/**
	 * <p> This method gets the reddit comments from the file specified. </p>
	 * <p> The file specified has to contain the json with the following format </p>
	 * {
	 * "Subreddit Name:": String 
	 * "Submissions": [{
	 * 		"Score": int 
	 * 		"Comments": [String, String...],
	 * 		"Id": String
	 * 		"Submission Name": String
	 *  }]
	 *  }
	 * @param filename The filename to read the data from
	 * @return The arraylist containing the list of reddit comments within the sub-subreddit
	 * @throws ParseException If there's error during parsing of data
	 * @throws IOException If there's error during input or output
	 * @see GetReddit.ConvertToJson
	 * @since 1.0
	 */
	public ArrayList<String> getRedditComments(String filename) throws ParseException, IOException, NullPointerException, FileNotFoundException {
		// Initialize ArrayList to Store Reddit Comments into a string
		ArrayList<String> textCollection = new ArrayList<String>();
		String text = "";
		JSONObject redditObject = JsonService.read(filename);
		JSONArray redditSubmission = (JSONArray) redditObject.get("Submissions");
		Iterator<JSONObject> iterator = redditSubmission.iterator();
		while (iterator.hasNext()) {
			ArrayList<String> RedditComments = (ArrayList<String>) iterator.next().get("Comments");
			for (int i = 1; i < RedditComments.size(); i++) {
				text += RedditComments.get(i) + ".\n";
			}
			textCollection.add(text);
		}
		return textCollection;
	}
	
	/**
	 * <p> This method get the tweeter comments from the file specified. </p>
	 * <p> The file specified has to contain the json with the following format </p>
	 *  {
	 *  "Tweets" : [String, String],
	 * 	"Hashtag Name" : String 
	 *  }
	 * 
	 * @param filename The filename to read the data from
	 * @return The arraylist containing the list of reddit comments within the sub-subreddit
	 * @throws ParseException If there's error during parsing of data
	 * @throws IOException If there's error during input or output
	 * @see GetTwitter.ConvertToJson
	 * @since 1.0
	 */
	public ArrayList<String> getTwitterComments(String filename) throws ParseException, IOException, NullPointerException, FileNotFoundException{
		ArrayList<String> textCollection = new ArrayList<String>();
		JSONObject TwitterObject = JsonService.read(filename);
		JSONArray TwitterTweets = (JSONArray) TwitterObject.get("Tweets");
		if (TwitterTweets != null) {
			textCollection = TwitterTweets;
			return textCollection;
		} else {
			throw new NullPointerException();
		}
	}
	
	
	/**
	 * <p> This method returns the reddit object from the file specified </p>
	 * @param filename The filename to read the data from
	 * @return The reddit object
	 * @throws ParseException If there's error during parsing of data
	 * @throws IOException If there's error during input or output
	 * @since 1.0
	 */
	public Object getRedditObject(String filename) throws ParseException, IOException {
		JSONObject RedditObject = JsonService.read(filename);
		return RedditObject;
	}
	
	/**
	 * <p> This method returns the twitter object from the file specified </p>
	 * @param filename The filename to read the data from
	 * @return The reddit object
	 * @throws ParseException If there's error during parsing of data
	 * @throws IOException If there's error during input or output
	 * @since 1.0
	 */
	public Object getTwitterObject(String filename) throws ParseException, IOException {
		JSONObject TwitterObject = JsonService.read(filename);
		return TwitterObject;
	}
	
	/**
	 * <p> This method gets the sentimental data from the file specified. </p>
	 * <p> The file specified has to contain the json with the following format </p>
	 *  {
	 *   "Sentimental Type Data" : [String, String ... ]
	 *  }
	 * @param filename The filename to read the data from
	 * @return The array list containing the sentimental data
	 * @throws ParseException If there's error during parsing of data
	 * @throws IOException If there's error during input or output
	 * @since 1.0
	 */
	public ArrayList<String> getSentimentalData(String filename) throws ParseException, IOException {
		JSONObject jsonObject = JsonService.read(filename);
		JSONArray jsonArray = (JSONArray) jsonObject.get("Sentimental Type Data");
		if (jsonArray != null) {
			return jsonArray;	
		} else {
			throw new NullPointerException();
		}
	}
}
