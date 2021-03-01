package Spider;

import org.json.JSONObject;
import java.io.FileWriter;
import java.io.IOException;
import org.json.JSONArray;

public class JsonTest {
	public void JsonRun() {
		JSONObject json = new JSONObject();
		json.put("Subreddit name", "all");
		json.put("Submission id", "erg34gg");
		json.put("Submission name", "meow");
		
		JSONArray list = new JSONArray();
		list.put("comment 1");
		list.put("comment 2");
		list.put("comment 3");
		list.put("comment 4");
		
		json.put("Comments", list);
		
		try(FileWriter file = new FileWriter("myjson.json"))
		{
			file.write(json.toString());
			file.flush();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		System.out.println(json);
		
	}
}
