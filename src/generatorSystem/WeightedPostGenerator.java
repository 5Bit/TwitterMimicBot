package generatorSystem;

import java.util.Random;
import java.util.Vector;

public class WeightedPostGenerator {
	
	private final int sentenceLength = 140;
	private WeightedMarkovChain markovChain = null;
	private final String startIdentifier = "__STRT";
	private final String endIdentifier = "__END";
	
	/**
	 * Constructor of this post generator. uses the inMarkov as the markov
	 * chain it uses to generate sentences.
	 * @param inMarkov
	 */
	WeightedPostGenerator(WeightedMarkovChain inMarkov)
	{
		this.markovChain = inMarkov;
		
	}
	
	/**
	 * Checks if the sentence provided with inSentence is new/novel. If it is, if returns false and adds it to the list.
	 * Else, it returns true.
	 * @param inSentence
	 * @return
	 */
	private Boolean sentenceAlreadyKnown(String inSentence)
	{
		if(markovChain.knownSentences.contains(inSentence)) return true;
		
		markovChain.knownSentences.add(inSentence);
		return false;
	}
	
	/**
	 * Generates a new line based on the markov chain.
	 * @return
	 */
	public String generate()
	{
		StringBuilder returnSentence = new StringBuilder();
		
		do{
			
			Vector<String[]> possibleChoices = markovChain.markovChain.get(startIdentifier);
			Vector<Integer> possibleChoiceWeights = getWeights(startIdentifier, possibleChoices);
			int totalWeight = 0;
			
			for(Integer i: possibleChoiceWeights)
				totalWeight+= i;
			
			
			
			// now that have weights, choose!
			String chosenWord = getNextWord(totalWeight, possibleChoices, possibleChoiceWeights);
			
			returnSentence.append(chosenWord);
			
			
			// loop for constructing rest of sentence.
			do{
				totalWeight = 0;
				returnSentence.append(" ");
				
				possibleChoices = markovChain.markovChain.get(chosenWord);
				if(possibleChoices == null || chosenWord == null) break; // needed in case the source data doesn't follow proper formatting.
				
				possibleChoiceWeights = getWeights(chosenWord, possibleChoices);
				
				for(Integer i: possibleChoiceWeights)
					totalWeight+= i;
				
				chosenWord = getNextWord(totalWeight, possibleChoices, possibleChoiceWeights);
				
				
				returnSentence.append(chosenWord);
				
				
				
			}while(!sentenceAlreadyKnown(returnSentence.toString()) && !chosenWord.equals(endIdentifier) && returnSentence.length() <= sentenceLength);
			
			
		}while(!sentenceAlreadyKnown(returnSentence.toString()));
		
		String returnItem = returnSentence.toString();
		
		if(returnItem.endsWith("__END")) returnItem = returnItem.substring(0, returnItem.length()-5);
		
		if(returnItem.endsWith("__END ")) returnItem = returnItem.substring(0, returnItem.length()-7);
		
		return returnItem;
	}
	
	
	/**
	 * Used to get the next word in the markov chain. takes in the total weight, a vector for all possible choices, and possibleChoiceWeights.
	 * @param totalWeight
	 * @param possibleChoices
	 * @param possibleChoiceWeights
	 * @return
	 */
	private String getNextWord( int totalWeight, Vector<String[]> possibleChoices, Vector<Integer> possibleChoiceWeights)
	{
		Random rand = new Random();
		
		int choice = rand.nextInt(totalWeight);
		
		String chosenWord = possibleChoices.get(0)[0];

		for(int index = 0, count = totalWeight; (count >= choice) && (index < possibleChoiceWeights.size()-1); count-= possibleChoiceWeights.get(index))
		{
			chosenWord = possibleChoices.get(index)[0];
			index++;
		}
//		System.out.println("Got value " + choice + ", with value " + chosenWord);
		
		return chosenWord;
	}
	
	/**
	 * Given the word and it's possible choices, returns a vector of ints that are weights.
	 * @param word
	 * @param possibleChoices
	 * @return
	 */
	private Vector<Integer> getWeights(String word, Vector<String[]> possibleChoices)
	{
		Vector<Integer> possibleChoiceWeights = new Vector<Integer>();
		if(possibleChoices.size() == 1)
		{
			possibleChoiceWeights.add(1);
			return possibleChoiceWeights;
		}

		for(String[] s: possibleChoices)
		{
			int temp = markovChain.weightHashTable.get(s[0].hashCode());
//			System.out.println(s[0] + ": " + temp);
			possibleChoiceWeights.add(temp);
		}
		
		
		return possibleChoiceWeights;
	}
	
}
