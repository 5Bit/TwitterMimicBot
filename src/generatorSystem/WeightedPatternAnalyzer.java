package generatorSystem;

import java.util.ArrayList;

// This class will handle the weighted version of the pattern analyzer and markov chain.
// Decided to split into a seperate class as extending PA would make it messier and take
// more room.
// Better to keep separate and deal with it separately, as they functionally are diff.
public class WeightedPatternAnalyzer {
	WeightedThreeStateMarkovChain markovChain = new WeightedThreeStateMarkovChain();
	
	ArrayList<String> knownWords = new ArrayList<String>();
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
			int currentPos = 0;
			StringBuilder sentence = new StringBuilder();

			while(!temp[currentPos].contains("RETWEET:"))
			{
//				System.out.print(temp[currentPos]);
				sentence.append(temp[currentPos]);
				sentence.append(" ");
				currentPos++;
			}
			
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
			
			
			// now that sentences are parsed, need to create das markov chain and markov weights!
			
		}
		

		
		
		
		
	}
	
	
	private void AnalyzerHelper(ArrayList<Integer> retweetCounts, ArrayList<Integer> favoriteCounts)
	{
		for(String sentence:markovChain.knownSentences)
		{
			//TODO - create the markov chains in here!
		}
		
		
	}
	
	
	
	

}
