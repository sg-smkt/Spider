package Spider;

import java.util.List;
import java.util.Iterator;

import net.dean.jraw.RedditClient;
import net.dean.jraw.oauth.Credentials;
import net.dean.jraw.oauth.OAuthHelper;
import net.dean.jraw.http.UserAgent;
import net.dean.jraw.http.OkHttpNetworkAdapter;
import net.dean.jraw.pagination.*;
import net.dean.jraw.models.*;
import net.dean.jraw.tree.*;

import org.json.JSONObject;
import java.io.FileWriter;
import java.io.IOException;
import org.json.JSONArray;

public class Reddit
{	
	private Credentials oauthCreds;
	private UserAgent userAgent;
	private RedditClient client;
	
	private String subredditName;
	private List<Submission> submissions;
	
	public Reddit()
	{
	}
	
	public Reddit(String username, String password, String clientId, String clientSecret)
	{
		oauthCreds = Credentials.script(username, password, clientId, clientSecret);
		userAgent = new UserAgent("redditScrapper-inator", "github.com/sg-smkt/Spider", "1.1.0", "/u/" + "username");
		
		client = OAuthHelper.automatic(new OkHttpNetworkAdapter(userAgent), oauthCreds);
	}
	
	public void SetSubreddit(String name) throws IOException
	{
		// Replace special characters with space.
		name = name.replaceAll("[^a-zA-Z0-9_.]", " ");
		// Subreddit name has a minimum of 3 characters and a maximum of 20 characters.
		if (name.length() < 3 || name.length() > 20)
		{
			throw new IOException("Subreddit name must be 3 to 20 characters long.");
		}
		// Spaces are not allowed.
		else if(name.contains(" "))
		{
			throw new IOException("Subreddit name cannot contain spaces and special symbols except _ are not allowed");
		}
		
		subredditName = name;
	}
	
	public void Crawl()
	{
		/* Additional options:
		 * limit - how many subreddit posts to search through.
		 * sorting - sort subreddit posts by type using SubredditSort enum (HOT, NEW, TOP, BEST, CONSTROVERSIAL and RISING).
		 * timePeriod - sort subreddit posts by time period using TimePeriod enum (ALL, DAY, HOUR, MONTH, WEEK, YEAR).*/
		// Default values are used when options are not specified.
		DefaultPaginator<Submission> paginator = client.subreddit(subredditName)
				.posts()
				.sorting(SubredditSort.HOT)
				.limit(1)
				.build();
		
		// Gets the next page, influenced by limit.
		submissions = paginator.next();
		
		ConvertToJson();
	}
	
	private void ConvertToJson()
	{
		// Create a Json object that holds name and array of submissions.
		JSONObject json = new JSONObject();
		json.put("Subreddit Name", subredditName);
		JSONArray jSubmissions = new JSONArray();
		
		for (Submission post : submissions)
		{
		    String id = post.getId();
		    RootCommentNode root = client.submission(id).comments();
		    //root.loadFully(client); // << This is too powerful, dont try it in large numbers.
		    Iterator<CommentNode<PublicContribution<?>>> comments = root.walkTree().iterator();
		    
			// Create a Json Object for every submission that holds id, title, score and an array of comments
		    JSONObject obj = new JSONObject();
		    obj.put("Id", id);
		    obj.put("Submission Name", post.getTitle());
			obj.put("Score", post.getScore());
			JSONArray jComments = new JSONArray();
			
		    while (comments.hasNext())
		    {
		    	PublicContribution<?> comment = comments.next().getSubject();
		    	
		    	// Exclude comments deleted by mods or have violated the subreddit rules.
		    	if(comment.getBody() != null && comment.getBody().contains("[removed]"))
		    	{
		    		break;
		    	}
		    	// Exclude comments deleted by the user.
		    	else if(comment.getBody() != null && comment.getBody().contains("[deleted]"))
		    	{
		    		break;
		    	}
		    	
	    		jComments.put(comment.getBody());
		    }
		    
		    obj.put("Comments", jComments);
		    jSubmissions.put(obj);
		}
		
		json.put("Submissions", jSubmissions);
		WriteToFile(json);
	}
	
	private void WriteToFile(JSONObject json)
	{
		try(FileWriter file = new FileWriter("data.json"))
		{
			file.write(json.toString());
			file.flush();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
}