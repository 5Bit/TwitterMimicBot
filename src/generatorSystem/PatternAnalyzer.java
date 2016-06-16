package generatorSystem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Vector;

class PatternAnalyzer extends MarkovThreeState {
	// will hold a list of all the known words
	ArrayList<String> knownWords = new ArrayList<String>();

	// will hold a markov chain of the data.

	//may take a while
	PatternAnalyzer(ArrayList<String> lines)
	{
		for(String s: lines)
			knownSentences.add(s);
		
		// call AnalyzerHelper
		AnalyzerHelper();
		
	}
	
	//TODO - TEST
	private void AnalyzerHelper()
	{
		
		// for every sentence provided, parse them and turn them into an ArrayList of ArrayList of words, and then go 
		// For clarity: ArrayList(Sentences) contains ArrayList(words)
		for(String sentence: knownSentences)
		{
			String partOne;
			String partTwo;
			String partThree;
			// convert the sentences into ArrayLists
			String[] temp = sentence.split(" ");
			
			// go through each word and count up combinations.
			for(int i = 0; i < temp.length - 2; i++)
			{
				partOne = temp[i];
				partTwo = temp[i+1];
				partThree = temp[i+2];
				numberOfWordsTotal += 3;
				String[] tempThree = {partOne, partTwo, partThree};
				
				processInputData(tempThree);
			}
		}
		
		

		
	}
	
	//TODO - TEST
	private void processInputData(String combo[])
	{
		
		//comment out - debugging only!
		//System.out.println("Processing combination: " + combo[0] + ", " + combo[1] + ", " + combo[2]);
		
		
		
		if(markovChain.containsKey(combo))
		{
			int tmp = markovChain.get(combo);
			tmp += 1;
			markovChain.replace(combo, tmp);
			
		}
		else
			markovChain.put(combo, 1);
		
	}
	
	PostGenerator toPostGenerator()
	{
		//TODO
		return null;
	}
	
	
	/**
	 * Allows the user to save the patterns found as a .TwitterPattern file
	 */
	void savePattern()
	{
		//TODO
	}
	
	
}
