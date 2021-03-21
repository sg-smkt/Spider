package com.oop.Spider.services;

import java.util.List;
import java.util.Iterator;

import net.dean.jraw.RedditClient;
import net.dean.jraw.oauth.Credentials;
import net.dean.jraw.oauth.OAuthHelper;
import net.dean.jraw.http.UserAgent;
import net.dean.jraw.http.OkHttpNetworkAdapter;
import net.dean.jraw.pagination.*;
import net.dean.jraw.models.PublicContribution;
import net.dean.jraw.models.SearchSort;
import net.dean.jraw.models.Submission;
import net.dean.jraw.models.Subreddit;
import net.dean.jraw.models.SubredditSearchSort;
import net.dean.jraw.models.SubredditSort;
import net.dean.jraw.models.TimePeriod;
import net.dean.jraw.tree.CommentNode;
import net.dean.jraw.tree.RootCommentNode;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import com.oop.Spider.Interface.CrawlerInterface;

import errorhandling.CustomError;

import org.json.simple.JSONArray;
import java.io.IOException;

/** Creates a reddit "client" to crawl data from Reddit using different forms of paginator to yield different results.
 * @author Seng Sam Kiat
 * user sg-smkt at github.com/sg-smkt/Spider
 * @version 1.0
 */
@Service
public class CrawlReddit extends CrawlerInterface {
	// Fields
	private Credentials oauthCreds;
	private UserAgent userAgent;
	private RedditClient client;
	
	private String subredditName;
	private List<Submission> submissions;
	
	private static final String RedditJson = "./data.json";
	private static final String RedditSubRedditJson = "./rsubreddit.json";
	private static final String RedditTitleJson = "./rtitle.json";
	
	// Properties
	protected RedditClient GetClient()
	{
		return this.client;
	}
	
	private void SetClient(RedditClient client)
	{
		this.client = client;
	}
	
	protected List<Submission> GetSubmissions()
	{
		return this.submissions;
	}
	
	private void SetSubmissions(List<Submission> submissions)
	{
		this.submissions = submissions;
	}
	
	protected String GetSubreddit()
	{
		return this.subredditName;
	}
	
	private void SetSubreddit(String subredditName)
	{
		this.subredditName = subredditName;
	}
	
	// Default Constructor.
	public CrawlReddit()
	{
	}
	
	/**
	 * <p>Class constructor for the CrawlReddit Object, it uses the required parameters to create a “client” 
	 * after OAuth has been verified. This “client” grants user access to Reddit through the use of a script. </p>
	 * @param username - reddit account username.
	 * @param password - reddit account password.
	 * @param clientId - client id taken from reddit app.
	 * @param clientSecret - client secret key taken from reddit app.
	 * @since 1.0
	 */
	public CrawlReddit(String username, String password, String clientId, String clientSecret)
	{
		oauthCreds = Credentials.script(username, password, clientId, clientSecret);
		userAgent = new UserAgent("redditScrapper-inator", "github.com/sg-smkt/Spider", "1.1.0", "/u/" + "username");
		
		client = OAuthHelper.automatic(new OkHttpNetworkAdapter(userAgent), oauthCreds);
	}
	
	/**
	 * <p>Run a check for characters, any special character will be detected and on Exception will be thrown</p>
	 * @param word String value to be check for special characters
	 * @return returns the parameter value
	 * @throws CustomError Thrown if a special character is detected
	 * @since 1.0
	 */
	private String CheckSpecials(String word) throws CustomError
	{
		// Replaces non alphabetical, numeral and spaces with an arbitrary character: @.
		word = word.replaceAll("[^a-zA-Z\\d\\s]", "@");
		// Reddit search does not yield search results containing special characters.
		if (word.contains("@"))
		{
			throw new CustomError("Search term cannot contain special characters.");
		}
		return word;
	}
	
	/**
	 * SearchSubreddits displays a list of subreddits that have posts that contain the search term. This is 
	 * used to suggest to the user which subreddits that would contain information that is relevant to them
	 * @param term The term to be searched 
	 * @param size Set the limit for the search result
	 * @throws IOException If there's error during input or output
	 * @since 1.0
	 */
	public void SearchSubreddits(String term, int size) throws CustomError, IOException
	{
		// Runs a check for special characters.
		term = CheckSpecials(term);
		
		SubredditSearchPaginator paginator = client.searchSubreddits()
				.query(term)
				.limit(size)
				.sorting(SubredditSearchSort.RELEVANCE)
				.build();
		
		List<Subreddit> subreddits = paginator.next();
		
		JSONObject json = new JSONObject();
		json.put("Subreddit Term", term);
		JSONArray jarray = new JSONArray();
		for (Subreddit sr : subreddits)
		{
            jarray.add(sr.getName());
		}
		json.put("Subreddits", jarray);
		
		JsonService newjson = new JsonService();
		System.out.println(newjson);
		newjson.writeToFile(RedditSubRedditJson, json);
	}
	
	
	/**
	 * <p>Searches for relevent subreddit name. The subreddit name specified by Reddit has to be 3 - 20 characters long 
	 * and must not contain special characters and spaces. Exception handling has been set up to make sure the user 
	 * follows the above criteria.</p>
	 * @param name The name to be searched
	 * @throws CustomError Thrown if the name in not within 3 - 20 characters or conatins special characters
	 */
	public void CheckSubredditName(String name) throws CustomError
	{
		// Replace all non alphabetical, numbers and underscore with space.
		name = name.replaceAll("[^a-zA-Z0-9_]", " ");
		// Subreddit name has a minimum of 3 characters and a maximum of 20 characters.
		if (name.length() < 3 || name.length() > 20)
		{
			throw new CustomError("Subreddit name must be 3 to 20 characters long.");
		}
		// Spaces are not allowed.
		else if(name.contains(" "))
		{
			throw new CustomError("Subreddit name cannot contain spaces and special characters except _ are not allowed");
		}
		
		subredditName = name;
	}
	
	/**
	 * SearchTitle displays a list of submission posts throughout the whole of Reddit that contain the search term in their title.
	 * @param term The term to be searched
	 * @throws IOException If there's error during input or output
	 * @since 1.0
	 */
	public void SearchTitle(String term) throws CustomError, IOException
	{
		term = CheckSpecials(term);
		
		SearchPaginator paginator = client.search()
				.sorting(SearchSort.HOT)
				.query(term)
				.timePeriod(TimePeriod.ALL)
				.limit(5)
				.build();
		submissions = paginator.next();
		
		JSONObject json = new JSONObject();
		json.put("Subreddit Term", term);
		JSONArray jarray = new JSONArray();
		for (Submission post : submissions)
		{
            jarray.add(post.getTitle());
		}
		json.put("Subreddits", jarray);
		
		JsonService newjson = new JsonService();
		newjson.writeToFile(RedditTitleJson, json);
	}
	
	/**
	 * <p>The Crawl function takes in a subreddit name and scans the specified subreddit’s posts and stores the posts in a 
	 * submission array. Do note that the subreddit name entered here must be the same as the actual subreddit name and is not 
	 * case sensitive. For example, entering “redditdev” will crawl through the posts in r/redditdev but if a typographical 
	 * error is made such as “reddiydev”, it will bring the user to a different or a non-existing subreddit. </p>
	 * @param subredditName The term to be searched
	 * @throws IOException If there's error during input or output
	 * @since 1.0
	 */
	public void Crawl(String subredditName) throws CustomError, IOException
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
	
		/**
		 * ConvertToJson function takes in a submission array, and formats it into a JSON Object. The JSON 
		 * object holds the name of the subreddit, an array of submission posts, their id, title and comments. 
		 * There is additional filtering such as Comments that are deleted by the user or removed by the moderators 
		 * of the subreddit will be removed before being stored to the comment array. </p>
		 * @param submissions The Array List to be formatted into a JSON Object
		 * @throws IOException If there's error during input or output
		 */
		private void ConvertToJson(List<Submission> submissions) throws CustomError, IOException
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
			JsonService newJson = new JsonService();
			newJson.writeToFile(RedditJson, json);
		}
}
