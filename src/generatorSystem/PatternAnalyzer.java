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
			// convert the sentences into ArrayLists
			String[] temp = sentence.split(" ");
			
			for(int i = 0; i <= temp.length-2; i++)
			{
				
				//For debugging
//				System.out.println("At position " + i + "trying to access position" + (i+1) + " and " + (i+2) + " when length is" + temp.length);
				
				if(i == 0)
				{
					Vector<String[]> start = new Vector<String[]>();
					if(markovChain.get("__STRT") != null)
					{
						start = markovChain.get("__STRT");

					}
						String[] tempArray = {temp[i], temp[i+1]};
						start.add(tempArray);
					
					
					
					Vector<String[]> suff = markovChain.get(temp[i]);
					if(suff == null)
					{
						suff = new Vector<String[]>();
						String[] suffTemp = {temp[i+1], temp[i+2]};
						suff.add(suffTemp);
						markovChain.put("__STRT", start);
						markovChain.put(temp[i], suff);
					}
				}
				else if(i == temp.length-2)
				{

					Vector<String[]> endOfSentence = new Vector<String[]>();
					String end = "__END";
					System.out.println(temp[i] +" will hold" + temp[i+1] + "and __END");
					String[] tempEnd = {temp[i+1], end};
					
					if(markovChain.get(tempEnd) != null)
					{
						endOfSentence = markovChain.get(tempEnd);
					}
					
					endOfSentence.add(tempEnd);
					
					markovChain.put(temp[i], endOfSentence);
					
					String[] endEnd = {end, end};
					Vector<String[]> endOfTheLine = new Vector<String[]>();
					endOfTheLine.addElement(endEnd);
					
					markovChain.put(temp[i+1], endOfTheLine);
				}
				else
				{

					Vector<String[]> suff = markovChain.get(temp[i]);
					
					if(suff == null)
					{
							suff = new Vector<String[]>();
							String[] tempSuff = {temp[i+1], temp[i+2]};
							suff.add(tempSuff);
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
	
	public Hashtable<String, Vector<String[]>> getMarkovChain(){
		
		return markovChain;
	}

	
	
}
