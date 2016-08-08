package readSystem;



import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;



public class ReadTwitter implements Reader{
	String twitterAccount, password;
	ConfigurationBuilder cb = null;
	
	ArrayList<Status> statusList = new ArrayList<Status>();
	ArrayList<String> statusListWithoutData = new ArrayList<String>();
	String hashtag, atUser;
	
	/**
	 * Creates a blank Read Twitter  - needs to have it's target set before running.
	 */
	public ReadTwitter(){
		hashtag = null;
		atUser = null;
	}
	
	/**
	 * Reads from the source location provided by accessing the account name
	 * from location 0 in the given source array.
	 * Throws a Twitter Exception if Tweet not found. 
	 * @param source
	 * @param SourceFileName
	 */
	public void setTarget(String source[]) throws TwitterException
	{
		twitterAccount = source[0];
		password = source[1];
		try{
		readAccount();
		}
		catch(TwitterException e)
		{
			System.out.println("There was an error reading from the twitter account.");
		}
		
	}
	
	public void setHashtag(String hash)
	{
		hashtag = hash;
	}
	
	public void setAt(String atTarget)
	{
		atUser = atTarget;
	}
	
	/**
	 * Use for twitter accounts that one does not have password access to.
	 * @param source
	 */
	public void setTarget(String source, ConfigurationBuilder inCb){
		twitterAccount = source;
		cb = inCb;
		
		try{
		readAccount();
		}
		catch(TwitterException e)
		{
		System.out.println("There was an error reading from the twitter account.");
			
		}
	}
	
	/**
	 * Reads as many tweets from the account as possible, storing them locally.
	 * @throws TwitterException
	 */
	private void readAccount() throws TwitterException
	{
		Twitter twitter = new TwitterFactory(cb.build()).getInstance();
	    int pageno = 1;
	    twitter.getScreenName();
	    
	    //Ignore the status arraylist warning!
	    ArrayList<Status> statuses = new ArrayList();
	    System.out.print("\n");
	    while(true){
	    	
	    	try{
	    		int size = statuses.size(); 
	            Paging page = new Paging(pageno++, 200);
	            statuses.addAll(twitter.getUserTimeline(twitterAccount, page));
	            System.out.println("Twitter contacted");
	            if (statuses.size() == size)
	              break;
	    	}
	    	catch(TwitterException e)
	    	{
	    		e.printStackTrace();
	    		break;
	    	}
	    }
	    
	    for(Status s: statuses)
	    	statusListWithoutData.add(s.getText());
	    
	    statusList = statuses;
	}
	
	/**
	 * Will return all items read from the text file as an arraylist of type string.
	 * Returns the filename.
	 * Must be accessed as unicode.
	 * @return
	 */
	public ArrayList<String> toArrayList()
	{
		
		return statusListWithoutData;
	}
	
	/**
	 * Used to pre-process the tweets locally.
	 * @param status
	 * @param hashReplacement
	 * @param atReplacement
	 * @return
	 */
	private String sentenceCleaning(Status status, String hashReplacement, String atReplacement)
	{
		StringBuilder cleanedData = new StringBuilder();
		
		String[] temp = status.getText().split(" ");
		
		for(String s: temp)
		{
			if(s.contains("http"))
			{
				// skip posts with websites...
			}
			else
			{
				cleanedData.append(s);
			}
			cleanedData.append(" ");
		}
		
		
		return cleanedData.toString().trim();
	}
	
	
	/**
	 * Saves the tweets to a local file.
	 * @return
	 */
	public String saveToFile()
	{
		String fileName = twitterAccount + "StatusUpdates.txt";
		
	ArrayList<Status> statusTemp = toArrayListStatus();
	List<String> statusStringPrep = new ArrayList<String>();
	
	Path file = Paths.get(fileName);
	
	for(Status s: statusTemp)
	{
		StringBuilder newStr = new StringBuilder();
		String text = s.getText();
		
		if(!text.contains("RT ") && !text.contains("\n") && !text.contains("#") && !text.contains("@"))
		{
		newStr.append(sentenceCleaning(s, hashtag, atUser));
		
		
		newStr.append(" ");
		newStr.append("RETWEET: " + s.getRetweetCount() +" FAVORITE: " + s.getFavoriteCount());
		statusStringPrep.add(newStr.toString());
		}
	}
	
	
	try {
		Files.write(file,  statusStringPrep, Charset.forName("UTF-8"));
	} catch(IOException e) {
		System.out.println("An error occured in saving records from " + twitterAccount);
		System.out.println("Printing stacktrace.");
		e.printStackTrace();
	}
	
	System.out.println("Done saving " + twitterAccount + " data.");
		
		return fileName;
	}
	
	/**
	 * Returns the list of status updates as status types.
	 * @return
	 */
	public ArrayList<Status> toArrayListStatus()
	{
		return statusList;
	}
	

}
