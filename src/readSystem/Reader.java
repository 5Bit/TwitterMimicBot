package readSystem;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public interface Reader {
	
	/**
	 * Will return all items read from the text file as an arraylist of type string.
	 * Must be accessed as unicode.
	 * @return
	 */
	ArrayList<String> toArrayList();
	
	/**
	 * Reads from the source location provided by accessing the system's type,
	 * and reading from the text file with the source name in the source location.
	 * @param source
	 * @param SourceFileName
	 * @throws FileNotFoundException 
	 * @throws UnsupportedEncodingException 
	 * @throws IOException 
	 */
	void setTarget(String source[]) throws FileNotFoundException, UnsupportedEncodingException, IOException;
	

}
