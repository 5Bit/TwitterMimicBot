package twitterMimicBotMain;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
//import java.lang.instrument.*;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
//import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.Vector;
//import java.util.function.BiConsumer;
import readSystem.*;
import twitter4j.Status;
import twitter4j.conf.ConfigurationBuilder;
import generatorSystem.*;
import outputSystem.OutputPostCMD;
import outputSystem.OutputPoster;
import java.util.Scanner;




public class Main {
	final static double versionNum = 0.1;
	final static String name = "VTMB";
	
	//Where the magic happens!
	public static void main(String[] args)
	{
		vtmb(args);
	}
	
	/**
	 * Will be the core Viral Twitter Mimic Bot system.
	 */
	public static void vtmb(String[] args)
	{
		Scanner textInput = new Scanner(System.in);
		
		System.out.println("Viral Twitter Mimic Bot, ver. " + versionNum);
		System.out.println("Developer: Thomas S. Field | Twitter: @FieldOfDesign"
				+ " | Github: https://github.com/5Bit/TwitterMimicBot");
		System.out.println("Utilizing Twitter4j - http://twitter4j.org/en/index.html");

		// main menu sys call
		mainMenu(textInput);
		


	}
	

	
	public static void mainMenu(Scanner scan){
		int choice = 42;
		boolean quit = false;
		String[] targetAccounts;
		
		while(quit == false)
		{
		
	
			do{ 
			System.out.println("\n-MENU-----------------------------------------------------------------------/n");
			System.out.println("1. Download tweet data from n Twitter accounts (to local file)");
			System.out.println("2. Download data from n Twitter accounts and generate tweets (to local file)");
			System.out.println("3. Download data from n Twitter accounts and generate tweets (to twitter)");
			System.out.println("4. Use saved twitter data and generate tweets (to local file)");
			System.out.println("5. Use saved twitter data and generate tweets (to twitter)");
			System.out.println("6. Quit");
			System.out.println("\nNOTE: For 1, 2, 3 or 5 the user needs Twitter Keys and Access Tokens");
			choice = scan.nextInt();
			
			}while(choice < 0 || choice > 6);
			
			switch(choice)
			{
				// TEST!
				case 0:
					
					// get account info
					try {
						targetAccounts = inputAccounts();
					} catch (Exception e) {
						System.out.println("Twitter accounts provided are incorrectly formatted.");
						System.out.println("An error has occured.");
						System.out.println("Returning to main menu");
					}
					
					// set up link to twitter
					ConfigurationBuilder cb = enterAccessData();
					
					// Create and call a Reader, download from twitter
					
					
					//TODO
					break;
				// Download tweets to local file
				case 1: 
					//TODO
					break;
				// Download tweets from n twitter accounts and generate (to local file)
				case 2:
	
					//TODO
					break;
				// Download tweets from n twitter accounts and generate (to twitter)
				case 3:
					
					//TODO
					break;
				case 4:
					//TODO
					break;
				case 5:
					//TODO
					break;
				case 6:
					System.out.print("Quitting " + name);
					quit = true;
					break;
			}
		
		}
		
	}
	
	public static Boolean yesNoConfirmation(Scanner scan)
	{
		String temp = "What!?";
		do
		{
			temp = scan.nextLine();
			temp.toUpperCase(Locale.ROOT);// Using Locale to avoid cross-sys support issues.
		}while(!temp.contentEquals("Y") || !temp.contentEquals("N"));
		
		if(temp.contentEquals("Y")) return true;
		else return false;
	}
	
	public static ConfigurationBuilder enterAccessData()
	{
		System.out.print("\nVerify you have entered the following data into the config.txt file that came with this program.");
		System.out.println("Consumer key");
		System.out.println("Consumer Secret");
		System.out.println("OAuthTokenKey");
		System.out.println("OAuthTokenSecret");
		
		pressAnyKeyToContinue();
		
		String[] configData = null;
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
	
	public static String[] readConfigFile() throws IOException
	{
		String[] returnString = new String[4];
		
		URL path = Main.class.getResource("Config.txt");
		File file = new File(path.getFile());
		BufferedReader reader = new BufferedReader(new FileReader(file));
		
		// assuming 0 based
		returnString[0] = reader.readLine().substring(13);
		returnString[1] = reader.readLine().substring(15);
		returnString[2] = reader.readLine().substring(14);
		returnString[3] = reader.readLine().substring(17);
		reader.close();
		return returnString;
	}
	
	
	public static String[] inputAccounts() throws Exception
	{
		System.out.println("Enter twitter accounts you would like to analyze.");
		System.out.println("Enter the account names in the following format:");
		System.out.println(" @Twitter @FieldOfDesign");

		
		String in;
		Scanner scan = new Scanner(System.in);
		
		in = scan.nextLine();

		String[] targetAccounts = in.split(" ");
		for(String s : targetAccounts)
		{
			if(!s.startsWith("@")) throw new Exception();
		}
		scan.close();
		return targetAccounts;
	}
	
	public static void pressAnyKeyToContinue()
	{
		System.out.println("Press any key and press enter to continue...");
		try
		{
			System.in.read();
		}
		catch(Exception e)
		{}
	}
	
	
	
	
	
	public static void testingTwitter()
	{

		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		String target = "google";
		System.out.println("Testing target.\nTarget: " + target);
		
		ReadTwitter reader = new ReadTwitter();
		
		//Reference! 
		// http://stackoverflow.com/questions/13545936/twitter4j-search-for-public-tweets
	
		
		ConfigurationBuilder cb = new ConfigurationBuilder();
	    cb.setDebugEnabled(true)
	          .setOAuthConsumerKey("EMPTY")
	          .setOAuthConsumerSecret("EMPTY")
	          .setOAuthAccessToken("EMPTY")
	          .setOAuthAccessTokenSecret("EMPTY");
	    
	    
		reader.setTarget(target, cb);
		
		ArrayList<String> temp = reader.toArrayList();
		ArrayList<Status> statusTemp = reader.toArrayListStatus();
		
		List<String> statusStrgPrep = new ArrayList<String>();
		
		Path file = Paths.get(target + "StatusUpdates");
		for(Status s: statusTemp)
		{
			StringBuilder newStr = new StringBuilder();
			newStr.append(s.getText());
			newStr.append("\n");
			newStr.append("RETWEET: " + s.getRetweetCount() +" FAVORITE: " + s.getFavoriteCount());
			statusStrgPrep.add(newStr.toString());
		}
		
		
		for(String x: statusStrgPrep)
			try {
				
				Files.write(file, statusStrgPrep, Charset.forName("UTF-8"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
//		Files.write(file, statusStrgPrep, Charset.forName("UTF-8"));
		
		
		
		System.out.println("Done.");
	}
	
	public static void testingTxt()
	{
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		ReadTxtFile reader = new ReadTxtFile();
		
		String[] target = {"\\Users\\Cuin\\Documents\\GitHub\\TwitterMimicBot\\TestFiles", "googleStatusUpdates.txt"};
		System.out.println("Testing target.\n target: " + target[0] + "\\" + target[1]);
		
		try {
			reader.setTarget(target);
		} catch (FileNotFoundException e) {

			System.out.println("The file was not found.");
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {

			System.out.println("The encoding provided was not supported");
			e.printStackTrace();
		} catch (IOException e) {

			System.out.println("There was an error reading from the file.");
			e.printStackTrace();
		}
		
		ArrayList<String> temp = reader.toArrayList();
		int lineCount = 0;
		for(String str: temp)
		{
			System.out.println("Line " + lineCount + " : " + str);
			out.println(str);
			++lineCount;
		}
		
		
		System.out.println("Number of lines:" + lineCount);
		
		// End of reading
		
		System.out.println("\n\nGenerator Pre-processing step:");
		Generator gen = new Generator(reader.toArrayList());
		
		Hashtable<String, Vector<String[]>> markovChain = gen.getMarkovChain();
		
		
		if(markovChain.isEmpty()) System.out.println("The markov chain is empty - this aint good, yo.");
		
		Set<String> keys = markovChain.keySet();
		
//		for(String key: keys)
//		{
//			System.out.print(key + " : ");
//			Vector<String[]> tempStringVec = markovChain.get(key);
//			for(String[] s : tempStringVec)
//			{
//				for(int i = 0; i < s.length; i++)
//					System.out.print(s[i] + " ");
//				
//				System.out.print(" :: ");
//			}
//			
//			System.out.println("");
//		}


		

		
		OutputPoster outputPost = new OutputPostCMD();
		
		
		System.out.println("Printing 42 outputs.");
		for(int i = 0; i < 42; i++)
		{
			System.out.println("");
			outputPost.submit(gen.run());
		}
		
		
//		outputPost.submit(gen.run());
		

		
		
	}
	
	
}
