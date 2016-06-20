package generatorSystem;

import java.util.ArrayList;
import java.util.Hashtable;
//import java.util.Hashtable;
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
//			String partOne;
//			String partTwo;
//			String partThree;
			// convert the sentences into ArrayLists
			String[] temp = sentence.split(" ");
			
			for(int i = 0; i < temp.length; i++)
			{
				
				if(i == 0)
				{
					Vector<String> start = new Vector<String>();
					if(markovChain.get("__STRT") != null)
					{
						start = markovChain.get("__STRT");

					}
						start.add(temp[i]);
					
					
					
					Vector<String> suff = markovChain.get(temp[i]);
					if(suff == null)
					{
						suff = new Vector<String>();
						suff.add(temp[i+1]);
						markovChain.put("__STRT", start);
						markovChain.put(temp[i], suff);
					}
				}
				else if(i == temp.length-1)
				{
					Vector<String> endOfSentence = new Vector<String>();
					String end = "__END";
					
					if(markovChain.get("__END") != null)
					{
						endOfSentence = markovChain.get(end);
						
					}
					
					endOfSentence.add(temp[i]);
					
					markovChain.put(end, endOfSentence);
					
					
				}
				else
				{
					Vector<String> suff = markovChain.get(temp[i]);
					if(suff == null)
					{
						suff = new Vector<String>();
						suff.add(temp[i+1]);
						markovChain.put(temp[i], suff);
					}
				}
			}
		}

	}
	
	
	/**
	 * Allows the user to save the patterns found as a .TwitterPattern file
	 */
	void savePattern()
	{
		//TODO
	}
	
	public Hashtable<String, Vector<String>> getMarkovChain(){
		
		return markovChain;
	}

	
	
}
