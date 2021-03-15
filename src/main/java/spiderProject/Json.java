package spiderProject;

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

public class Json {
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
	
	// Implement Getter and Setter here 
	public ArrayList<String> getRedditComments(String filename) {
		// Initialize ArrayList to Store Reddit Comments into a string
		ArrayList<String> textCollection = new ArrayList<String>();
		String text = "";
		JSONObject RedditObject = Json.read(filename);
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
	
	// Implement Getter and Setter here 
	public ArrayList<String> getSentimentalData(String filename) {
		JSONObject jsonObject = Json.read(filename);
		JSONArray jsonArray = (JSONArray) jsonObject.get("Sentimental Type Data");
		ArrayList<String> list = new ArrayList<String>();
		// Custom Error message here?
		return jsonArray;
	}
}
