package outputSystem;

import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

public class OutputPostTwitter implements OutputPoster {


	ConfigurationBuilder cb = null;
	
	/**
	 * Constructs the OutputPostTwitter, and creates a configurationBuilder so it can properly access the twitter account in question.
	 * @param configData
	 */
	public OutputPostTwitter(String[] configData)
	{
		// build the cb
		cb = new ConfigurationBuilder();
	    cb.setDebugEnabled(true)
        .setOAuthConsumerKey(configData[0])
        .setOAuthConsumerSecret(configData[1])
        .setOAuthAccessToken(configData[2])
        .setOAuthAccessTokenSecret(configData[3]);
		
	}
	
	/**
	 * Submits the provided post to the twitter account.
	 */
	public void submit(String post) {
		
		Twitter twitter = new TwitterFactory(cb.build()).getInstance();
		try {
			twitter.updateStatus(post);
		} catch (TwitterException e) {
			System.out.println("There was an error posting to twitter. check CB data.");
			e.printStackTrace();
		}
		
	}

	
}
