package readSystem;

import java.util.ArrayList;

class ReadTwitter implements Reader{
	String TwitterAccount, password;
	
	//TODO - uncomment when twitterCopier is done
	//private TwitterCopier Copier = new TwitterCopier();
	
	public ReadTwitter(){
		TwitterAccount = null;
		password = null;
	}
	
	/**
	 * Reads from the source location provided by accessing the account name
	 * from location 0 in the given source array.
	 * @param source
	 * @param SourceFileName
	 */
	public void ReadFrom(String source[])
	{
		//TODO
		// call TwitterCopier in here!
	}
	
	/**
	 * Will return all items read from the text file as an arraylist of type string.
	 * Must be accessed as unicode.
	 * @return
	 */
	public ArrayList<String> ToArrayList()
	{
		//TODO
		return null;
	}
	

}
