package readSystem;



import java.util.ArrayList;
import java.util.List;

import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;



public class ReadTwitter implements Reader{
	String twitterAccount, password;
	ConfigurationBuilder cb = null;
	
	ArrayList<Status> statusList = new ArrayList<Status>();
	ArrayList<String> statusListWithoutData = new ArrayList<String>();
	
	//TODO - uncomment when twitterCopier is done
	//private TwitterCopier Copier = new TwitterCopier();
	
	public ReadTwitter(){
		
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
	
	private void readAccount() throws TwitterException
	{

		Twitter twitter = new TwitterFactory(cb.build()).getInstance();


	    int pageno = 1;

	    ArrayList<Status> statuses = new ArrayList();
	    
	    

	    while(true){
	    	
	    	try{
	    		int size = statuses.size(); 
	            Paging page = new Paging(pageno++, 200);
	            statuses.addAll(twitter.getUserTimeline(twitterAccount, page));
//	            System.out.println("Twitter contacted");

	            
	            if (statuses.size() == size)
	              break;
	    	}
	    	catch(TwitterException e)
	    	{
	    		e.printStackTrace();
	    		break;
	    	}
	    }
	    
	    //System.out.println("Earliest post is " + statuses.get(statuses.size()-1).getCreatedAt());
	    
	    for(Status s: statuses)
	    {
	    	statusListWithoutData.add(s.getText());
	    }
	    
	    statusList = statuses;
		
		//System.out.println("Total: " + statuses.size());
	}
	
	/**
	 * Will return all items read from the text file as an arraylist of type string.
	 * Returns the list without the status data
	 * Must be accessed as unicode.
	 * @return
	 */
	public ArrayList<String> toArrayList()
	{
		
		return statusListWithoutData;
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
