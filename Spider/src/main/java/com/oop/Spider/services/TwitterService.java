package com.oop.Spider.services;

import java.util.ArrayList;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.oop.Spider.objects.Searchable;

@Service
public class TwitterService {
	
	private static final String consumerkey = "2bGiyfbtRzEoirN2eCBPrTa3x";
	private static final String consumerSecret = "PfKt8S5yXFKChcOBRfoo858YDSvWnpTxE4KDoNivtiYrtuDD6e";
	private static final String accessToken = "810785369557962752-XRBRcHpcC2484WdJ2s7fCd5OL2457aX";
	private static final String accessTokenSecret = "cMxMh0k0oYrIG6odxFAID0V54niGe2bzKU1HMIXaUFsXC";
	
	
    ArrayList<String> TweetsList = new ArrayList<String>();
	
	public void searchHashTag(Searchable search, Model model) {
		model.addAttribute("twitter", search);
		initialize(search.getSearch());
	}
	
	public static void initialize(String search) {
		GetTwitter bigBird = new GetTwitter();
		bigBird.authenticate(consumerkey, consumerSecret, accessToken, accessTokenSecret);
		bigBird.saQuery("covid");	
	}
}
