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

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import java.io.FileWriter;
import java.io.IOException;

public class Reddit extends Platform
{	
	private Credentials oauthCreds;
	private UserAgent userAgent;
	private RedditClient client;
	
	private String subredditName;
	//private List<Submission> submissions;
	
	public Reddit()
	{
	}
	
	public Reddit(String username, String password, String clientId, String clientSecret)
	{
		oauthCreds = Credentials.script(username, password, clientId, clientSecret);
		userAgent = new UserAgent("redditScrapper-inator", "github.com/sg-smkt/Spider", "1.1.0", "/u/" + "username");
		
		client = OAuthHelper.automatic(new OkHttpNetworkAdapter(userAgent), oauthCreds);
	}
	
	//Changes Search Settings
	
	// Searches for and lists Subreddits that contain the term specified.
	public void SearchSubreddits(String term, int size) throws UserInputException
	{
		// Runs a check for special characters.
		term = CheckSpecials(term);
		
		SubredditSearchPaginator paginator = client.searchSubreddits()
				.query(term)
				.limit(size)
				.sorting(SubredditSearchSort.RELEVANCE)
				.build();
		
		List<Subreddit> subreddits = paginator.next();
		
		for (Subreddit sr : subreddits)
		{
            System.out.println(sr.getName());
		}
	}
	
	// Searches for posts that contain the specified term in the title in all of Reddit.
	public void SearchTitle(String term) throws UserInputException
	{
		term = CheckSpecials(term);
		
		SearchPaginator paginator = client.search()
				.sorting(SearchSort.HOT)
				.query(term)
				.timePeriod(TimePeriod.ALL)
				.limit(5)
				.build();
		List<Submission> submissions = paginator.next();
		
		for (Submission post : submissions)
		{
            System.out.println(post.getTitle());
		}
	}

	// Crawls the specified subreddit to get post information.
	public void Crawl(String subredditName) throws UserInputException
	{
		CheckSubredditName(subredditName);
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
		List<Submission> submissions = paginator.next();
		
		if(submissions.size() != 0)
		{
			for (Submission s : submissions)
			{
	            System.out.println(s.getTitle());
			}
			ConvertToJson(submissions);
		}
	}
	
	// Runs a check for special characters.
	private String CheckSpecials(String word) throws UserInputException
	{
		// Replaces non alphabetical, numeral and spaces with an arbitrary character: @.
		word = word.replaceAll("[^a-zA-Z\\d\s]", "@");
		// Reddit search does not yield search results containing special characters.
		if (word.contains("@"))
		{
			throw new UserInputException("Search term cannot contain special characters.");
		}
		return word;
	}
	
	// Checks for invalid subreddit names.
	private void CheckSubredditName(String name) throws UserInputException
	{
		// Replace all non alphabetical, numbers and underscore with space.
		name = name.replaceAll("[^a-zA-Z0-9_]", " ");
		// Subreddit name has a minimum of 3 characters and a maximum of 20 characters.
		if (name.length() < 3 || name.length() > 20)
		{
			throw new UserInputException("Subreddit name must be 3 to 20 characters long.");
		}
		// Spaces are not allowed.
		else if(name.contains(" "))
		{
			throw new UserInputException("Subreddit name cannot contain spaces and special characters except _ are not allowed");
		}
		
		subredditName = name;
	}
	
	// Formats the submission information and its comments into a JSON object.
	private void ConvertToJson(List<Submission> submissions)
	{
		// Create a Json object that holds name and array of submissions.
		JSONObject json = new JSONObject();
		json.put("Subreddit Name", subredditName);
		JSONArray jSubmissions = new JSONArray();
		
		for (Submission post : submissions)
		{
		    String id = post.getId();
		    RootCommentNode root;
		    
		    if (client.submission(id).comments() != null)
		    {
		    	root = client.submission(id).comments();
		    	//root.loadFully(client); Loads comments without limit, can take a long time if there is a large number of comments.
		    }
		    else
		    {
		    	break;
		    }
		    
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
		    	
	    		jComments.add(comment.getBody());
		    }	
		    
		    obj.put("Comments", jComments);
		    jSubmissions.add(obj);
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