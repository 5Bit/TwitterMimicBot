package generatorSystem;

import java.util.Random;
import java.util.Vector;

public class WeightedPostGenerator {
	
	private final int sentenceLength = 140;
	private WeightedThreeStateMarkovChain markovChain = null;
	private final String startIdentifier = "__STRT";
	private final String endIdentifier = "__END";
	
	/**
	 * Constructor of this post generator. uses the inMarkov as the markov
	 * chain it uses to generate sentences.
	 * @param inMarkov
	 */
	WeightedPostGenerator(WeightedThreeStateMarkovChain inMarkov)
	{
		this.markovChain = inMarkov;
		
	}
	
	private Boolean sentenceAlreadyKnown(String inSentence)
	{
		if(markovChain.knownSentences.contains(inSentence)) return true;
		
		markovChain.knownSentences.add(inSentence);
		return false;
	}
	
	public String generate()
	{
		StringBuilder returnSentence = new StringBuilder();
		
		do{
//			Random rand = new Random();
			
			
			Vector<String[]> possibleChoices = markovChain.markovChain.get(startIdentifier);
			Vector<Integer> possibleChoiceWeights = getWeights(startIdentifier, possibleChoices);
			int totalWeight = 0;
			
			for(Integer i: possibleChoiceWeights)
				totalWeight+= i;
			
			
			
			// now that have weights, choose!
			String chosenWord = getNextWord(totalWeight, possibleChoices, possibleChoiceWeights);
			
			returnSentence.append(chosenWord);
//			System.out.println(returnSentence.toString());
			
			
			// loop for constructing rest of sentence.
			do{
				System.out.println(returnSentence.toString());
				totalWeight = 0;
				returnSentence.append(" ");
				
				possibleChoices = markovChain.markovChain.get(chosenWord);
				if(possibleChoices == null || chosenWord == null) break; // TODO - MUST...ELIMINATE THIS HERESY - after I get it running.
				
				possibleChoiceWeights = getWeights(chosenWord, possibleChoices);
				
				for(Integer i: possibleChoiceWeights)
					totalWeight+= i;
				
				//TODO BUG HERE
				chosenWord = getNextWord(totalWeight, possibleChoices, possibleChoiceWeights);
				
				
				returnSentence.append(chosenWord);
				
				
				
			}while(!sentenceAlreadyKnown(returnSentence.toString()) && !chosenWord.equals(endIdentifier) && returnSentence.length() <= sentenceLength);
			
			
		}while(!sentenceAlreadyKnown(returnSentence.toString()));
		
		String returnItem = returnSentence.toString();
		
		if(returnItem.endsWith("__END")) returnItem = returnItem.substring(0, returnItem.length()-5);
//		
		if(returnItem.endsWith("__END ")) returnItem = returnItem.substring(0, returnItem.length()-7);
		
		return returnItem;
	}
	
	
	
	private String getNextWord( int totalWeight, Vector<String[]> possibleChoices, Vector<Integer> possibleChoiceWeights)
	{
		Random rand = new Random();
		
		int choice = rand.nextInt(totalWeight);
		
		String chosenWord = possibleChoices.get(0)[0];

		// issue here - array out of range (2)
		for(int index = 0, count = totalWeight; (count >= choice) && (index < possibleChoiceWeights.size()-1); count-= possibleChoiceWeights.get(index))
		{
			chosenWord = possibleChoices.get(index)[0];
			index++;
		}
//		System.out.println("Got value " + choice + ", with value " + chosenWord);
		
		return chosenWord;
	}
	
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
