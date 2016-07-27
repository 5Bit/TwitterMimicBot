package readSystem;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileManager {
	ArrayList<String> files = null;
	String fileManagerName;
	Path filePath = null;
	
	
	
	public FileManager(String fileMangrName)
	{
		fileManagerName = fileMangrName; 
		filePath = Paths.get(fileManagerName);
		
		// Get any previous files, and readies the files array list.
		files = readFileManager();
		
		// if there was no manager file...
		
		
		if(files == null)
		{
			List<String> temp = new ArrayList<String>();
			files = new ArrayList<String>();
			try {
				Files.write(filePath, temp, Charset.forName("UTF-8"));
			} catch (IOException e) {
				System.out.println("An error occured within creation of the file manager object.");
				e.printStackTrace();
			}
		}
		
	}
	
	
	/**
	 * Reads the local file for the fileManager, and if successful, will return an ArrayList of
	 * the file managers's content.
	 * If error IOException, an error occured while reading from the fileManager or with accessing
	 * the file.
	 * @return
	 */
	public ArrayList<String> readFileManager()
	{
		ArrayList<String> lines = new ArrayList<String>();
		
		File file = new File(filePath.toUri());
		
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(file));

			String line = null;
			try {
				while((line = reader.readLine()) != null)
				{
					lines.add(line);
				}
			}
			catch (IOException e) {
				System.out.println("An error occured while reading a line from file " + fileManagerName);
				System.out.println("Printing stacktrace.");
				e.printStackTrace();
			}
			reader.close();
			return lines;
			
		} catch (IOException e1) {
			System.out.println("An error occured with the Buffered Reader to " + fileManagerName);
			System.out.println("Printing stacktrace.");
			e1.printStackTrace();
			return null;
		}
		
	}
	
	public ArrayList<String> getFileNames(){
		
		return files;
	}
	
	public void addFile(String addedFileName)
	{
		files.add(addedFileName);
		updateFileManager();
	}
	
	
	private void updateFileManager()
	{
		List<String> updatedManagerList = files;
		
		try {
			Files.write(filePath, updatedManagerList, Charset.forName("UTF-8"));
		} catch (IOException e) {
			System.out.println("An error occured within the file manager updater.");
			e.printStackTrace();
		}
	}

}
