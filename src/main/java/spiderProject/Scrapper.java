package Spider;
import java.util.Scanner;

public class Scrapper
{
	public static void main(String[] args)
	{
		String username = "";
		String password = "";
		String clientId = "083CeQvkPFTQkA";
		String clientSecret = "NhX6tJACk3By8oo-llV8-rL0dVhpfA";
		
		Reddit reddit = new Reddit(username, password, clientId, clientSecret);
		
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter subreddit name: ");
		String subredditName = scanner.nextLine();
		reddit.Crawl(subredditName);
	}
}
