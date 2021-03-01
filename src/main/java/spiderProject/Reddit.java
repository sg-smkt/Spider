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

public class Reddit
{
	private String username = "";
	private String password = "";
	private String clientId = "";
	private String clientSecret = "";
	
	private Credentials oauthCreds;
	private UserAgent userAgent;
	private RedditClient client;
	
	public Reddit()
	{
	}
	
	public Reddit(String username, String password, String clientId, String clientSecret)
	{
		//May not need these...
		this.username = username;
		this.password = password;
		this.clientId = clientId;
		this.clientSecret = clientSecret;
		
		oauthCreds = Credentials.script(username, password, clientId, clientSecret);
		userAgent = new UserAgent("redditScrapper-inator", "github.com/sg-smkt/Spider", "1.0.0", "/u/" + "username");
		
		client = OAuthHelper.automatic(new OkHttpNetworkAdapter(userAgent), oauthCreds);
	}
	
	public void Crawl(String subredditName)
	{
		/*Additional options:
		 *limit - how many subreddit posts to search through, limit(0) = first post
		 *sorting - sort subreddit posts by type using SubredditSort enum (HOT, NEW, TOP, BEST, CONSTROVERSIAL and RISING)
		 *timePeriod - sort subreddit posts by time period using TimePeriod enum (ALL, DAY, HOUR, MONTH, WEEK, YEAR)*/ 
		//Default values are used when options are not specified.
		DefaultPaginator<Submission> paginator = client.subreddit(subredditName)
				.posts()
				.sorting(SubredditSort.HOT)
				.limit(1)
				.build();
		
		//Gets the next page, influenced by limit.
		List<Submission> submissions = paginator.next();
		
		for (Submission post : submissions)
		{
		    System.out.println("Votes: " + post.getScore() + " " + post.getTitle());
		    
		    String id = post.getId();
		    RootCommentNode root = client.submission(id).comments();
		    Iterator<CommentNode<PublicContribution<?>>> comments = root.walkTree().iterator();

		    while (comments.hasNext())
		    {
		    	PublicContribution<?> comment = comments.next().getSubject();
		    	System.out.println(comment.getBody());
		    }
		}
	}
}