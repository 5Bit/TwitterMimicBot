package twitterMimicBotMain;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import readSystem.*;
import twitter4j.conf.ConfigurationBuilder;
import generatorSystem.*;
import outputSystem.OutputPoster;
import java.util.Scanner;
import outputSystem.*;


public class Main {
	final static double versionNum = 0.1;
	final static String name = "VTMB";
	final static String devs = "Developer: Thomas S. Field | Twitter: @FieldOfDesign";
	final static String projectGitHub = "Github: https://github.com/5Bit/TwitterMimicBot";
	final static String tools = "Utilizing Twitter4j - http://twitter4j.org/en/index.html";
	final static String fileManagerName = "dataManager.txt";
	final static FileManager fileManager = new FileManager(fileManagerName);
	static String[] configData = null;
	
	
	//Where the magic happens!
	public static void main(String[] args)
	{
		vtmb(args);
	}
	
	/**
	 * Viral Twitter Mimic Bot run!
	 * @param args
	 */
	public static void vtmb(String[] args)
	{
		System.out.println("Viral Twitter Mimic Bot, ver. " + versionNum);
		System.out.println(devs);
		System.out.println(projectGitHub);
		System.out.println(tools);
		// main menu run
		mainMenu();
	}
	

	
	/**
	 * The main menu system. It does a lot.
	 */
	public static void mainMenu(){
		int choice = 42;
		boolean quit = false;
		ArrayList<String> collectedData = null;

		// Ignore the following complaint - it's closed after the while loop.
		Scanner scan = new Scanner(System.in);

		while(quit == false)
		{
			WeightedGenerator gen = null;
			String post = null;
			OutputPoster poster = null;
			OutputPoster localSave = null;
			
			choice = 42;
			do{ 
			System.out.println("\n\n-MENU-----------------------------------------------------------------------/n");
//			System.out.println("0. Testing the system - download from twitter, store to local file, read from local file, generate to local file, UI and to twitter.");
			System.out.println("1. Download tweet data from n Twitter accounts (to local file)");
			System.out.println("2. Download data from n Twitter accounts and generate tweets (to local file)");
			System.out.println("3. Download data from n Twitter accounts and generate tweets (to twitter)");
			System.out.println("4. Use saved twitter data and generate tweets (to local file)");
			System.out.println("5. Use saved twitter data and generate tweets (to twitter)");
			System.out.println("6. Quit");
			System.out.println("\nNOTE: For 1, 2, 3 or 5 the user needs Twitter Keys and Access Tokens");

			choice = scan.nextInt();
			
			}while(choice < 0 || choice > 6);
			
			scan = scan.reset();
			
			switch(choice)
			{
				// TEST MODE!
				case 0:
					collectedData = downloadSys();
					gen = new WeightedGenerator(collectedData);
//					gen.checkMarkovChain();
					
					post = gen.run();
					System.out.println(post);
					poster = new OutputPostTwitter(configData);
					localSave = new OutputPostToFile();
					poster.submit(post);
					localSave.submit(post);
					break;

				// Download tweets to local file
				case 1:
					collectedData = downloadSys();
					break;
					
				// Download tweets from n twitter accounts and generate (to local file)
				case 2:
					collectedData = downloadSys();

					gen = new WeightedGenerator(collectedData);
					post = gen.run();
					localSave = new OutputPostToFile();
					localSave.submit(post);

					break;
				// Download tweets from n twitter accounts and generate (to twitter)
				case 3:
					
					collectedData = downloadSys();
					gen = new WeightedGenerator(collectedData);
					post = gen.run();
					poster = new OutputPostTwitter(configData);
					poster.submit(post);
					
					break;
				// Use saved twitter data and generate tweets (to local file)
				case 4:
					
					collectedData = getAllFileContent();
					gen = new WeightedGenerator(collectedData);
					post = gen.run();
					localSave = new OutputPostToFile();
					localSave.submit(post);
				
					break;
				// Use saved twitter data and generate tweets (to twitter)
				case 5:
					
					collectedData = getAllFileContent();
					gen = new WeightedGenerator(collectedData);
					post = gen.run();
					poster = new OutputPostTwitter(configData);
					poster.submit(post);
					break;
				case 6:
				//Quits!
					System.out.print("Quitting " + name);
					quit = true;
					break;
			}
		}
		scan.close();
	}
	
	/**
	 * Gets all saved file content saved via the dataManager.txt.
	 * @return
	 */
	public static ArrayList<String> getAllFileContent()
	{
		ArrayList<String> collectedData = null;
		try {
			collectedData = fileManager.getAllFilesContent();
		} catch (IOException e) {
			System.out.println("There was an error reading all files via the fileManager.");
			e.printStackTrace();
		}
		return collectedData;
	}
	
	/**
	 * Reads from the the target accounts stored within the file manager, and 
	 * returns (collectively) the data within all of the files.
	 * Has an exception if the twitter accounts are not properly formatted.
	 * Has an IOException if the file manager has an issue getting all file(s) content.
	 * @return
	 */
	public static ArrayList<String> downloadSys(){
		String[] targetAccounts = new String[10];
		ArrayList<String> collectedData = null;
		// get account info
		try {
			targetAccounts = inputAccounts();
		} catch (Exception e) {
			System.out.println("Twitter accounts provided are incorrectly formatted.");
			System.out.println("An error has occured.");
			System.out.println("Returning to main menu");
		}
		
		// loops through all the targets, assigning the reader to look at it, record all statuses to a local file,
		// repeats.
		// Will need to modify to work with FileManager within Reader!
		if(targetAccounts.length <= 10)
		{
			ArrayList<String> fileNames = dlTwitAccUpdatesToFile(targetAccounts, name, name);
			
			for(String s: fileNames)
				fileManager.addFile(s);
			

				try {
					collectedData = fileManager.getAllFilesContent();
				} catch (IOException e) {
					System.out.println("An error occured while reading the collective data from the HDD.");
					e.printStackTrace();
				}
				
				//   use the following block only for testing.
				//////////////////////////////////////////////
//				System.out.println("Printing collective data for verification.");
//				for(String s: collectedData)
//				{
//					System.out.println(s);
//				}
				//////////////////////////////////////////////
				
		}
		else
		{
			System.out.println("The number of target accounts exceeded the amount allowed.");
			System.out.println("The maximum number of accounts to pull from is 10.");
		}
		
		return collectedData;
	}
	
	/**
	 * Takes twitter account information, contacts Twitter, downloads as many
	 * tweets as it can from each account given. Returns a list of the
	 * filenames for all Twitter Account data. Files created are stored locally.
	 * @param targetAccounts
	 * @return targetFileNames
	 */
	public static ArrayList<String> dlTwitAccUpdatesToFile(String[] targetAccounts, String newHash, String newUserTarget)
	{
		ArrayList<String> returnStringArray = new ArrayList<String>();
		
		for(String target: targetAccounts)
		{
			System.out.println("Downloading " + target + " information.");
			ReadTwitter reader = new ReadTwitter();
			reader.setHashtag(newHash);
			reader.setAt(newUserTarget);
			ConfigurationBuilder cb = enterAccessData();
			System.out.print("Keys and Secret Keys entered.");
			reader.setTarget(target, cb);
			returnStringArray.add(reader.saveToFile());
		}
		
		// update the file manager

		System.out.println("Done saving Twitter posts of all targets to local system.");
		return returnStringArray;
	}
	
	/**
	 * Creates a ConfigurationBuilder based on the data within the
	 * config.txt file.
	 * @return
	 */
	public static ConfigurationBuilder enterAccessData()
	{


		try {
			configData = readConfigFile();
		} catch (IOException e) {
			System.out.println("Configuration data read incorrectly.");
			System.out.println("Printing stack trace.");
			e.printStackTrace();
		}
		
		
		ConfigurationBuilder cb = new ConfigurationBuilder();

	    cb.setDebugEnabled(true)
	          .setOAuthConsumerKey(configData[0])
	          .setOAuthConsumerSecret(configData[1])
	          .setOAuthAccessToken(configData[2])
	          .setOAuthAccessTokenSecret(configData[3]);

		return cb;
	}
	
	/**
	 * Reads the configuration file of the program. If it is not found, an
	 * IOException will occur. If a line is empty or null, an IOException
	 * will occur.
	 * If this is having multiple issues, be sure you have the configuration
	 * file set up.
	 * @return
	 * @throws IOException
	 */
	public static String[] readConfigFile() throws IOException
	{
		String[] returnString = new String[4];
		
		URL path = Main.class.getResource("config.txt");
		File file = new File(path.getFile());
		
		BufferedReader reader = new BufferedReader(new FileReader(file));
		
		// Hardcoded - probably should optimize.
		returnString[0] = reader.readLine().substring(13);
		returnString[1] = reader.readLine().substring(16);
		returnString[2] = reader.readLine().substring(15);
		returnString[3] = reader.readLine().substring(18);
		
		for(String s: returnString)
			if(s.isEmpty() || s == null) throw new IOException(); // had an issue reading it.
		
		reader.close();
		return returnString;
	}
	
	/**
	 * Asks the user to input the target accounts, which it will parse. Will throw an error
	 * If it is given an account not started with the '@' symbol.
	 * @return
	 * @throws Exception
	 */
	public static String[] inputAccounts() throws Exception
	{
		System.out.println("Enter twitter accounts you would like to analyze.");
		System.out.println("Enter the account names in the following format:");
		System.out.println(" @Twitter @FieldOfDesign");

		String in;
		
		//Ignore this complaint. if closed, it closes the in stream. No bueno.
		Scanner scan = new Scanner(System.in);
		
		in = scan.nextLine();

		String[] targetAccounts = in.split(" ");
		for(String s : targetAccounts)
			if(!s.startsWith("@")) throw new Exception();

		return targetAccounts;
	}
	
	/**
	 * A function that requires the user to press enter to continue.
	 */
	public static void pressEnterToContinue()
	{
		System.out.println("Press enter to continue...");
		try
		{
			System.in.read();
		}
		catch(Exception e)
		{
			System.out.println("Error in pressEnterToContinue. If you are seeing this, something went seriously wrong. PM @FieldOfDesign the stacktrace.");
			e.printStackTrace();
		}
	}
	
	
}
