package spiderProject;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.util.CoreMap;

public class ReadJson {
	
	private static FileWriter file;
	
	public static JSONObject ReadData(String filename) {
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

	public Object getComments() {	
		//Initialize ArrayList to store all comment per submission
		ArrayList<String> textCollection = new ArrayList<String>();
		String text = "";
		//Get Subreddit Data
		JSONObject RedditObject = ReadJson.ReadData("./src/main/java/spiderProject/data.json");
		//Get Submission Data
		JSONArray RedditSubmission = (JSONArray) RedditObject.get("Submissions");
		Iterator<JSONObject> iterator = RedditSubmission.iterator();
		//Loop through all submission in subreddit
		while (iterator.hasNext()) {
			ArrayList<String> RedditComments = (ArrayList<String>) iterator.next().get("Comments");
			text = "\n\n\n";
			for (int i = 1; i < RedditComments.size(); i++) {
				text += RedditComments.get(i) + ".\n";
			}				
			text += "\n\n\n";
			textCollection.add(text);
		}
		
		return textCollection;
	}
	
	public ArrayList<Long> getUpvotes() {
		JSONObject jsonObject = ReadJson.ReadData("./src/main/java/spiderProject/data.json");
		JSONArray RedditSubmission = (JSONArray) jsonObject.get("Submissions");
		Iterator<JSONObject> iterator = RedditSubmission.iterator();
		ArrayList<Long> upvoteList = new ArrayList<Long>();
		while (iterator.hasNext()) {
			Long upvote =  (Long) iterator.next().get("Score");
			upvoteList.add(upvote);
		}
		return upvoteList;
	}
	
	public Object getSentimentalData() {
		JSONObject jsonObject = ReadJson.ReadData("./src/main/java/spiderProject/sentimentaldata.json");
		JSONArray arr = (JSONArray) jsonObject.get("Sentimental Type Data");
		return arr;
	}
	
	public ArrayList<String> writeData(List<CoreMap> sentences, ArrayList<String> newList) {
		for (int i = 0; i <sentences.size(); i++) {
			String setimentType = sentences.get(i).get(SentimentCoreAnnotations.SentimentClass.class);
			newList.add(setimentType);
		}
		return newList;
	}
	
	public void writeJson(ArrayList<String> data) {
		JSONObject obj = new JSONObject();
		JSONArray arr = new JSONArray();
	
		obj.put("Sentimental Type Data", data);
		
		try {
			file = new FileWriter("./src/main/java/spiderProject/sentimentaldata.json");
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

}
