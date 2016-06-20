package twitterMimicBotMain;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Vector;

import readSystem.*;
import generatorSystem.*;
import outputSystem.OutputPostCMD;
import outputSystem.OutputPoster;


public class Main {
	
	
	//Where the magic happens!
	public static void main(String[] args)
	{
		testingTxt();
		
		
		//TODO
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
		
		Hashtable<String, Vector<String>> markovChain = gen.getMarkovChain();
		
		if(markovChain.isEmpty()) System.out.println("The markov chain is empty - this aint good, yo.");
		
//		for(Vector<String> s: gen.)
		
//		OutputPoster outputPost = new OutputPostCMD();
		
		
//		outputPost.submit(gen.run());
		

		
		
	}
	
	
}
