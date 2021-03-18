package unittesting;

import org.json.simple.JSONObject;

import com.oop.Spider.services.JsonService;
import com.oop.Spider.services.StatisticsService;

import errorhandling.CustomError;

public class Testing {
	private static final String RedditJson = "./data.json";
	
	public static void main(String[] args) throws CustomError {
		JSONObject RedditObject = JsonService.read(RedditJson);
		System.out.println(RedditObject);
	}
}
