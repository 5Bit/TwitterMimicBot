package readSystem;

import java.util.ArrayList;

public interface Reader {
	
	/**
	 * Will return all items read from the text file as an arraylist of type string.
	 * Must be accessed as unicode.
	 * @return
	 */
	ArrayList<String> ToArrayList();
	
	/**
	 * Reads from the source location provided by accessing the system's type,
	 * and reading from the text file with the source name in the source location.
	 * @param source
	 * @param SourceFileName
	 */
	void ReadFrom(String source[]);
	

}
