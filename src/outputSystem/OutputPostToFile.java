package outputSystem;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class OutputPostToFile implements OutputPoster  {
	File outputLog = null;
	
	/**
	 * Obtains the outputLog.txt file and holds on to it for further submissions.
	 */
	public OutputPostToFile()
	{
		outputLog = new File("OutputLog.txt");
		

			if(!outputLog.exists())
			{
				try {
					outputLog.createNewFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
	}
		
	/**
	 * Submits to the OutputLog.txt file the provided line.
	 */
	public void submit(String out){
		
	try{
		FileWriter fw = new FileWriter(outputLog, true);
		
		BufferedWriter buffWriter = new BufferedWriter(fw);
		buffWriter.write(out + "\n");
		buffWriter.close();
	}
	catch(IOException e)
	{
		System.out.println("Error in saving to local system.");
	}
	
	}

}
