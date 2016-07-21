package twitterMimicBotMain;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import readSystem.*;
import twitter4j.Status;
import twitter4j.conf.ConfigurationBuilder;
import generatorSystem.*;
import outputSystem.OutputPostCMD;
import outputSystem.OutputPoster;


public class Main {
	
	
	//Where the magic happens!
	public static void main(String[] args)
	{
//		testingTxt();
		testingTwitter();
		
		//TODO
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
		
		String[] target = {"\\Users\\Cuin\\Documents\\GitHub\\TwitterMimicBot\\TestFiles", "TantarusTweetSamples.txt"};
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
		
//		for(Vector<String> s: markovChain.values())
//		{
//			
//			System.out.println(s.toString());
//		}
		
		System.out.println(markovChain.toString());
		
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
