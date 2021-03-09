package Spider;

import java.util.Scanner;

public class Scrapper
{
	private static Scanner scanner;
	private static Reddit reddit = new Reddit();
	
	public static void main(String[] args)
	{
		String username = "";
		String password = "";
		String clientId = "083CeQvkPFTQkA";
		String clientSecret = "NhX6tJACk3By8oo-llV8-rL0dVhpfA";
		
		reddit = new Reddit(username, password, clientId, clientSecret);
		
		scanner = new Scanner(System.in);
		
		Query();
	}
	
	private static void Query()
	{
		try
		{
			System.out.println("Enter subreddit name: ");
			String subredditName = scanner.nextLine();
			reddit.SetSubreddit(subredditName);
			
			reddit.Crawl();
		}
		catch (Exception e)
		{
			System.out.println(e);
			Query();
		}
	}
}
