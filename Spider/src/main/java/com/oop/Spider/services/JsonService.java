package com.oop.Spider.services;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

@Service
public class JsonService {
	
private static FileWriter file;
	
	public static JSONObject read(String filename) {
		JSONParser jsonParser = new JSONParser();
		JSONObject jsonObj = null;
		try {
			FileReader reader = new FileReader(filename);
			Object obj = jsonParser.parse(reader);
			jsonObj = (JSONObject) obj;
		} catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
		return jsonObj;
	}
	
	public void write(ArrayList<String> data, String filename) {
		// Using HashMap to implement Type Safety to JSONObject
		HashMap<String, ArrayList<String>> hashData = new HashMap<String, ArrayList<String>>();
		hashData.put("Sentimental Type Data", data);
		JSONObject obj = new JSONObject(hashData);
		
		try {
			file = new FileWriter(filename);
			file.write(obj.toJSONString());
			System.out.println("Added data to File");
		}catch(IOException e) {
			e.printStackTrace();
		}finally {
			try {
				file.close();	
			}catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void writeToFile(String filename, JSONObject json) {
		  try(FileWriter file = new FileWriter(filename)){
			  file.write(json.toString());
			  file.flush();
		  } catch(IOException e) {
			  e.printStackTrace();
		  } 
	  }
	
	// Implement Getter and Setter here 
	public ArrayList<String> getRedditComments(String filename) {
		// Initialize ArrayList to Store Reddit Comments into a string
		ArrayList<String> textCollection = new ArrayList<String>();
		String text = "";
		JSONObject RedditObject = JsonService.read(filename);
		// ABLE TO DO ERROR CHECKING HERE
		JSONArray RedditSubmission = (JSONArray) RedditObject.get("Submissions");
		Iterator<JSONObject> iterator = RedditSubmission.iterator();
		while (iterator.hasNext()) {
			ArrayList<String> RedditComments = (ArrayList<String>) iterator.next().get("Comments");
			for (int i = 1; i < RedditComments.size(); i++) {
				text += RedditComments.get(i) + ".\n";
			}
			textCollection.add(text);
		}
		return textCollection;
	}
	
	public ArrayList<String> getTwitterComments(String filename) {
		ArrayList<String> textCollection = new ArrayList<String>();
		String text = "";
		JSONObject TwitterObject = JsonService.read(filename);
		JSONArray TwitterTweets = (JSONArray) TwitterObject.get("Tweets");
		textCollection = TwitterTweets;
		return textCollection;
	}
	
	public Object getRedditObject(String filename) {
		// filename = "./data.json"
		JSONObject RedditObject = JsonService.read(filename);
		return RedditObject;
	}
	
	public Object getTwitterObject(String filename) {
		// filename = "./data1.json"
		JSONObject TwitterObject = JsonService.read(filename);
		return TwitterObject;
	}
	
	// Implement Getter and Setter here 
	public ArrayList<String> getSentimentalData(String filename) {
		JSONObject jsonObject = JsonService.read(filename);
		JSONArray jsonArray = (JSONArray) jsonObject.get("Sentimental Type Data");
		ArrayList<String> list = new ArrayList<String>();
		// Custom Error message here?
		return jsonArray;
	}
}
