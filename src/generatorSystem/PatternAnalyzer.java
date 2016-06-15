package generatorSystem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

class PatternAnalyzer extends MarkovThreeState {
	ArrayList<String> knownWords = new ArrayList<String>();
	
	

	//may take a while
	PatternAnalyzer(ArrayList<String> lines)
	{
		for(String s: lines)
			knownSentences.add(s);
		
		// find all unique words within each sentences, and sort after done.
		
		// for each sentence
		for(String s: knownSentences)
		{
			// chops up the sentence into separate words
			String[] temp = s.split(" ");
			
			// for each word in the sentence
			for(String wrd: temp)
			{
				// if it is not contained, add it to the list of known words!
				if(!knownWords.contains(wrd))
					knownWords.add(wrd);
			}
		}
		
		// Will sort the list of known Words according to the first letter
		Collections.sort(knownWords.subList(1, knownWords.size()));
	
		//debugging- for seeing the sort list.
		
		for(String s: knownWords)
			System.out.println(s);
		
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
