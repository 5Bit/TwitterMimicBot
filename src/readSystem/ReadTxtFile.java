package readSystem;

import java.util.ArrayList;

class ReadTxtFile implements Reader{
	//TODO
	String FileLocation;
	/**
	 * Constructor - sets up an empty ReadTxtFile object.
	 * Requires ReadFrom to be used before safe to use.
	 */
	ReadTxtFile(){
		//TODO - Finish
		FileLocation = new String();
		
	}
	
	/**
	 * Reads from the source location provided by accessing the system's type,
	 * and reading from the text file with the source name (source [0] in the
	 * source location[1].
	 * @param source
	 */
	public void ReadFrom(String source[])
	{
		//TODO
	}
	
	/**
	 * Will return all items read from the text file as an arraylist of type string.
	 * Must be accessed as unicode.
	 * @return
	 */
	public ArrayList<String> ToArrayList()
	{
		//TODO - FINISH
		return new ArrayList<String>();
	}
}
