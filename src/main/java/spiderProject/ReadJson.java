package spiderProject;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class ReadJson {

	public ArrayList<String> getData() {	
		// JSON tutorial: https://crunchify.com/how-to-read-json-object-from-file-in-java/
		JSONParser jsonParser = new JSONParser();
		//Initialize ArrayList to store all comment per submission
		ArrayList<String> textCollection = new ArrayList<String>();
		String text = "";
		try (FileReader reader = new FileReader("./src/main/java/spiderProject/data.json")){
			Object obj = jsonParser.parse(reader);
			//Get Subreddit
			JSONObject RedditObject = (JSONObject) obj;
			//Get Submission from Subreddit
			JSONArray RedditSubmission = (JSONArray) RedditObject.get("Submissions");
			Iterator<JSONObject> iterator = RedditSubmission.iterator();
			//Loop through all submission in subreddit
			while (iterator.hasNext()) {
				ArrayList<String> RedditComments = (ArrayList<String>) iterator.next().get("Comments");
				for (int i = 1; i < RedditComments.size(); i++) {
					text += RedditComments.get(i) + "\n";
				}
				textCollection.add(text);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return textCollection;
	}

}
