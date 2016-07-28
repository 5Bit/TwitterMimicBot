package readSystem;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import twitterMimicBotMain.Main;

public class FileManager {
	ArrayList<String> files = new ArrayList<String>();
	String fileManagerName;
	Path filePath = null;
	
	
	/**
	 * Constructor for the File Manager. needs the name of the file manager's local file.
	 * @param fileMangrName
	 */
	public FileManager(String fileMangrName)
	{
		fileManagerName = fileMangrName; 
		filePath = Paths.get(fileManagerName);
		// Get any previous files, and readies the files array list.
		try {
			files = readFileManager();
		} catch (IOException e) {
			System.out.println("An error occured with readFileManager.");
			e.printStackTrace();
		}	
	}
	
	
	/**
	 * Reads the local file for the fileManager, and if successful, will return an ArrayList of
	 * the file managers's content.
	 * If error IOException, an error occured while reading from the fileManager or with accessing
	 * the file.
	 * @return
	 * @throws IOException 
	 */
	public ArrayList<String> readFileManager() throws IOException
	{
		if(filePath == null)
		{
			List<String> temp = new ArrayList<String>();
			files = new ArrayList<String>();
			try {
				Files.write(filePath, temp, Charset.forName("UTF-8"));
			} catch (IOException e) {
				System.out.println("No " + fileManagerName + " found.");
				System.out.println("Solution - create an empty " + fileManagerName + " in the root location of this software package.");
				e.printStackTrace();
			}
			return files;
		}
		List<String> fileContent = Files.readAllLines(filePath);
		
		ArrayList<String> returnArray = new ArrayList<String>();
		for(String s: fileContent)
		{
			returnArray.add(s);
			System.out.println("The file manager contains: " + s);
		}

		

		
		return returnArray;
	}
	
	/**
	 * Creates a deep copy of file names, and returns the new copy.
	 * @return
	 */
	public ArrayList<String> getFileNames(){
		ArrayList<String> temp = new ArrayList<String>();
		for(String s: files)
				temp.add(s);
		
		return temp;
	}
	
	/**
	 * Goes through each file and returns a copy of the collective content over all source files collected
	 * And referenced within the dataManager.
	 * @return
	 * @throws IOException
	 */
	public ArrayList<String> getAllFilesContent() throws IOException
	{
		ArrayList<String> allFilesContent = new ArrayList<String>();
		
		for(String file: files)
		{
			
			System.out.println("Getting content of " + file);
			ArrayList<String> fileContent = getFileContent(file);
			
			for(String s: fileContent)
				allFilesContent.add(s);
		}
		
		return allFilesContent;
	}
	
	

	private ArrayList<String> getFileContent(String fileName) throws IOException
	{
		ArrayList<String> fileContent = new ArrayList<String>();
		
		String absolutePath = new File(fileName).getAbsolutePath();
		

		File file = new File(absolutePath);
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			
			// while it has lines to read, read them all and store them into fileContent.
			String line = reader.readLine();
			while(line != null)
			{
				fileContent.add(line);
				line = reader.readLine();
			}

		} catch (FileNotFoundException e) {
			System.out.println(fileName + " was not found.");
			System.out.println("Be sure to include the file type, which should be .txt");
			System.out.println("Printing stack trace.");
			e.printStackTrace();
		}
		return fileContent;
	}
	
	
	
	/**
	 * Adds the file to the dataManager.txt, and is managed by the FileManager.
	 * @param addedFileName
	 */
	public void addFile(String addedFileName)
	{
		//TODO FIND BUG IN HERE
		files.add(addedFileName);
		updateFileManager();
	}
	
	/**
	 * Updates the file manager's dataManager.txt file, retaining previous file information.
	 */
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
