package readSystem;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;


class ReadTxtFile implements Reader{
	ArrayList<String> fileLines = new ArrayList<String>();
	
	/**
	 * Constructor - sets up an empty ReadTxtFile object.
	 * Requires setTarget to be used before safe to use.
	 */
	ReadTxtFile(){
		
	}
	
	/**
	 * Reads from the source location provided by accessing the system's type,
	 * and reading from the text file in the source location (source [0]) with
	 * the source name (source[1]).
	 * if the source file is not found, it will throw a File Not Found
	 * Exception. if the source file is found to not have supported encoding,
	 * an UnsupportedEncodingException will be thrown. If there is an issue
	 * with reading from the file, a IOException will be thrown.
	 * @param source
	 * @throws FileNotFoundException 
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	public void setTarget(String source[]) throws FileNotFoundException, UnsupportedEncodingException, IOException
	{
		String target = source[0] + "\\" + source[1];
		InputStream inputStream = new FileInputStream(target);
		BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
			
		for(String line; (line = in.readLine()) != null; )
			fileLines.add(line);

		in.close();
		
		cleanFileLines();
	}
	
	/**
	 * Removes any empty lines from the fileLines String ArrayList.
	 */
	private void cleanFileLines()
	{
		ArrayList<String> newList = new ArrayList<String>();
		
		for(String str: fileLines)
		{
			if(!str.isEmpty())
				newList.add(str);
		}
		
		fileLines = newList;
	}
	
	
	
	/**
	 * Creates a deep copy of the fileLines read, and returns them.
	 * @return
	 */
	public ArrayList<String> toArrayList()
	{
		ArrayList<String> temp = new ArrayList<String>();
		for(String line: fileLines)
		{
			temp.add(line);
		}
	
		return fileLines;
	}
}
