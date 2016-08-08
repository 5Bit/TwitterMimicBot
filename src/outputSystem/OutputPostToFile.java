package outputSystem;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class OutputPostToFile implements OutputPoster  {
	File outputLog = null;
	
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
