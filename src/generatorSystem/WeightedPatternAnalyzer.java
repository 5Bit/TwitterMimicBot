package generatorSystem;

import java.util.ArrayList;
import java.util.Vector;

// This class will handle the weighted version of the pattern analyzer and markov chain.
// Decided to split into a seperate class as extending PA would make it messier and take
// more room.
// Better to keep separate and deal with it separately, as they functionally are diff.
public class WeightedPatternAnalyzer {
	String strtIdentifier = "__STRT";
	String endIdentifier = "__END";
	int retweetModifier = 1;
	public WeightedMarkovChain markChain = new WeightedMarkovChain();
	
	public ArrayList<String> knownWords = new ArrayList<String>();
	// holds each sentence's retweet and favorite counts.

	
	/**
	 * Constructor for a weighted pattern analyzer. Takes in the lines, as formatted in the following way:
	 *  structure "Sentence RETWEET: 134 FAVORITE: 146
	 * @param lines
	 */
	public WeightedPatternAnalyzer(ArrayList<String> lines)
	{
		ArrayList<Integer> retweetCounts = new ArrayList<Integer>();
		ArrayList<Integer> favoriteCounts = new ArrayList<Integer>();
		
		// parse sentence from retweet and favorite data.
		for(String s: lines)
		{
			if(s.contains("RETWEET"))
			{
			String temp[] = s.split(" ");
			int currentPos = 0;
			StringBuilder sentence = new StringBuilder();
			// add start identifier
			sentence.append(strtIdentifier + " ");
			while(!temp[currentPos].contains("RETWEET:"))
			{
//				System.out.print(temp[currentPos]);
				sentence.append(temp[currentPos]);
				sentence.append(" ");
				currentPos++;
			}
			
			// add end identifier
			sentence.append(endIdentifier);
			
			markChain.knownSentences.add(sentence.toString().trim());
			currentPos++;
			int retweetCount = Integer.parseInt(temp[currentPos]);
			currentPos+= 2;
			int favoriteCount = Integer.parseInt(temp[currentPos]);
			
			retweetCounts.add(retweetCount);
			favoriteCounts.add(favoriteCount);
			// for debugging
//			System.out.println(sentence.toString().trim());
//			System.out.println("Retweet: " + retweetCount);
//			System.out.println("favorite: " + favoriteCount);
			}
			else 
			{
				// skip any items without proper formatting. '\n' is apparently hidden within .txt files, so it's best to be safe
				// than sorry.
			}
	
		}
		
		AnalyzerHelper(retweetCounts, favoriteCounts);
		
	}
	
	/**
	 * Used to construct weights. Easily done before constructing the markov chain.
	 * Takes in a sentence as an array of strings, a retweet count and a favorite count.
	 * @param sentence
	 * @param retweetCount
	 * @param favoriteCount
	 */
	private void createWeights(String[] sentence, int retweetCount, int favoriteCount)
	{
		int modifier = (retweetCount * retweetModifier) + favoriteCount + 1;
		for(int i = 1; i<= sentence.length-2; i++)
		{
			int hashCode = sentence[i].hashCode();

			// if it does not contain the word...
			if(markChain.weightHashTable.get(hashCode) == null)
			{
				markChain.weightHashTable.put(hashCode, modifier);
			}
			else // if it does contain the word, add and replace val.
			{
				int currentVal = markChain.weightHashTable.get(hashCode) + modifier;
				markChain.weightHashTable.replace(hashCode, currentVal);
			}
			
			
		}
		
		int endHash = endIdentifier.hashCode();
		if(markChain.weightHashTable.get(endIdentifier) == null)
		{
			markChain.weightHashTable.put(endHash, modifier);
		}
		else
		{
			int temp = markChain.weightHashTable.get(endHash) + modifier;
			markChain.weightHashTable.replace(endHash, temp);
		}
		
		
	}
	
	/**
	 * Used to analyze known sentences, and break down tweets into a markov chain.
	 * @param retweetCounts
	 * @param favoriteCounts
	 */
	private void AnalyzerHelper(ArrayList<Integer> retweetCounts, ArrayList<Integer> favoriteCounts)
	{
		int currentSentenceNum = 0;
		for(String sentence: markChain.knownSentences)
		{
			String[]temp = sentence.split(" ");
			
			//create weights first!
			createWeights(temp, retweetCounts.get(currentSentenceNum), favoriteCounts.get(currentSentenceNum));
			
			for(int i = 0; i<= temp.length-2; i++)
			{
				// For debugging...
//				System.out.println("At position " + i + "trying to access position" + (i+1) + " and " + (i+2) + " when length is" + temp.length);
				if(i == 0)
				{
					// getting the first part of the sentence
					// start doesn't need a weight... it always needs to occur.
					Vector<String[]> start = new Vector<String[]>();
					
					if(markChain.markovChain.get(strtIdentifier) != null)
					{
						start = markChain.markovChain.get(strtIdentifier);
					}
					
					// exception for sentences with one word only.
					if(temp.length == 3)
					{
						String[] tempArray = {temp[i+1], endIdentifier};
						String[] suffArray = {endIdentifier, endIdentifier};
						
						// if the item being added is not within the vector, add it!
						if(!doesMarkovContains(temp[i], tempArray))
						{
							start.add(tempArray);
							Vector<String[]> end = new Vector<String[]>();
							// if there is an end item already
							if(markChain.markovChain.get(endIdentifier) != null)
							{
								end = markChain.markovChain.get(endIdentifier);
							}
							end.add(suffArray);
							markChain.markovChain.put(endIdentifier, end);
							markChain.markovChain.put(strtIdentifier, start);
							break;
						}
					}

					String[] tempArray = {temp[i+1], temp[i+2]};
					
					// if the array being added is not within the vector, add it!
					if(!doesMarkovContains(temp[i], tempArray))
					{
						start.add(tempArray);
						
						Vector<String[]> suff = markChain.markovChain.get(temp[i+1]);
						if(suff == null)
						{
							suff = new Vector<String[]>();
							String[] suffTemp = {temp[i+1], temp[i+2]};
							suff.add(suffTemp);
							markChain.markovChain.put(strtIdentifier, start);
							markChain.markovChain.put(temp[i+1], suff);
						}
					}	
					
				}
				else if(i == temp.length-2) // if the end...
				{

					Vector<String[]> endOfSentence = new Vector<String[]>();

//					System.out.println(temp[i] +" will hold" + temp[i+1] + "and " + endIdentifier);
					String[] tempEnd = {temp[i+1], endIdentifier};
					
					// The ending being added is not within the vector, add it!
					if(!doesMarkovContains(temp[i], tempEnd))
					{
						
						if(markChain.markovChain.get(tempEnd) != null)
						{
							endOfSentence = markChain.markovChain.get(tempEnd);
						}
						
						endOfSentence.add(tempEnd);
						
						markChain.markovChain.put(temp[i], endOfSentence);
						
						String[] endEnd = {endIdentifier, endIdentifier};
						Vector<String[]> endOfTheLine = new Vector<String[]>();
						endOfTheLine.addElement(endEnd);
						
						markChain.markovChain.put(temp[i+1], endOfTheLine);
					}
					
				}
				else // if in the middle...
				{
					
					Vector<String[]> suff = markChain.markovChain.get(temp[i]);
					String[] tempSuff = {temp[i+1], temp[i+2]};
					
					if(!doesMarkovContains(temp[i], tempSuff))
					{
						if(suff == null)
						{
								suff = new Vector<String[]>();
								suff.add(tempSuff);
								markChain.markovChain.put(temp[i], suff);
						}
						else
						{
							suff.add(tempSuff);
						}
					}
				}
			}
		currentSentenceNum++;
		}	
	}
	
	/**
	 * Checks if the particular values given are contained within the key's
	 * location in the markov chain.
	 * @param key
	 * @param values
	 * @return
	 */
	private boolean doesMarkovContains(String key, String[] values)
	{
		Vector<String[]> target = markChain.markovChain.get(key);
		if(target == null) return false; // Nothing here but us chickens
		
		for(String[] s: target)
		{
			if(s[0].equals(values[0]) && s[1].equals(values[1])) return true;
		}
		return false;
	}

}
