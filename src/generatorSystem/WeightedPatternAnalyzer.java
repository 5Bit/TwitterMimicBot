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
	int retweetModifier = 2;
	public WeightedThreeStateMarkovChain markovChain = new WeightedThreeStateMarkovChain();
	
	public ArrayList<String> knownWords = new ArrayList<String>();
	// holds each sentence's retweet and favorite counts.

	
	public WeightedPatternAnalyzer(ArrayList<String> lines)
	{
//		ArrayList<String> parsedLines = new ArrayList<String>();
		ArrayList<Integer> retweetCounts = new ArrayList<Integer>();
		ArrayList<Integer> favoriteCounts = new ArrayList<Integer>();
		// structure "Sentence RETWEET: 134 FAVORITE: 146
		
		// parse sentence from retweet and favorite data.
		
		for(String s: lines)
		{
			if(s.contains("RETWEET"))
			{
			String temp[] = s.split(" ");
			int currentPos = 1;
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
			
			markovChain.knownSentences.add(sentence.toString().trim());
//			parsedLines.add(sentence.toString().trim());
			currentPos++;
			int retweetCount = Integer.parseInt(temp[currentPos]);
			currentPos+= 2;
			int favoriteCount = Integer.parseInt(temp[currentPos]);
			
			retweetCounts.add(retweetCount);
			favoriteCounts.add(favoriteCount);
			
			// for testing!
//			System.out.println(sentence.toString().trim());
//			System.out.println("Retweet: " + retweetCount);
//			System.out.println("favorite: " + favoriteCount);
			
			
			}
			else 
			{
				// skip any items without proper formatting.
				//TODO - FIX READ ISSUE WITH NEW LINES! will be within ReadTwitter - have to handle newlines!
				//Doesn't at the moment.
			}
			

			
		}
		

		
		
		AnalyzerHelper(retweetCounts, favoriteCounts);
		
	}
	
	private void createWeights(String[] sentence, int retweetCount, int favoriteCount)
	{
		for(int i = 1; i<= sentence.length-2; i++)
		{
			int hashCode = sentence[i].hashCode();
			int modifier = (retweetCount * retweetModifier) + favoriteCount + 1;
			// if it does not contain the word...
			if(markovChain.weightHashTable.get(hashCode) == null)
			{
				markovChain.weightHashTable.put(hashCode, modifier);
			}
			else // if it does contain the word, add and replace val.
			{
				int currentVal = markovChain.weightHashTable.get(hashCode) + modifier;
				markovChain.weightHashTable.replace(hashCode, currentVal);
			}
		}
	}
	
	private void AnalyzerHelper(ArrayList<Integer> retweetCounts, ArrayList<Integer> favoriteCounts)
	{
		int currentSentenceNum = 0;
		for(String sentence: markovChain.knownSentences)
		{
			//TODO - create the markov chains in here!
			
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
					
					if(markovChain.markovChain.get(strtIdentifier) != null)
					{
						start = markovChain.markovChain.get(strtIdentifier);
					}
					
					//TODO - exception for if the sentence has only one word.
					// __STRT WordHere
					String[] tempArray = {temp[i], temp[i+1]};
					start.add(tempArray);
					
					Vector<String[]> suff = markovChain.markovChain.get(temp[i]);
					if(suff == null)
					{
						suff = new Vector<String[]>();
						String[] suffTemp = {temp[i], temp[i+1]};
						suff.add(suffTemp);
						markovChain.markovChain.put(strtIdentifier, start);
						markovChain.markovChain.put(temp[i], suff);
					}
				}
				else if(i == temp.length-2)
				{

					Vector<String[]> endOfSentence = new Vector<String[]>();
//					String end = "__END";
//					System.out.println(temp[i] +" will hold" + temp[i+1] + "and " + endIdentifier);
					String[] tempEnd = {temp[i+1], endIdentifier};
					
					if(markovChain.markovChain.get(tempEnd) != null)
					{
						endOfSentence = markovChain.markovChain.get(tempEnd);
					}
					
					endOfSentence.add(tempEnd);
					
					markovChain.markovChain.put(temp[i], endOfSentence);
					
					String[] endEnd = {endIdentifier, endIdentifier};
					Vector<String[]> endOfTheLine = new Vector<String[]>();
					endOfTheLine.addElement(endEnd);
					
					markovChain.markovChain.put(temp[i+1], endOfTheLine);
				}
				else
				{

					Vector<String[]> suff = markovChain.markovChain.get(temp[i]);
					
					if(suff == null)
					{
							suff = new Vector<String[]>();
							String[] tempSuff = {temp[i+1], temp[i+2]};
							suff.add(tempSuff);
							markovChain.markovChain.put(temp[i], suff);
					}
					
					
				}
					
				
				
				
			}
		currentSentenceNum++;
		}
		
		
	}
	

	
	
	
	

}
