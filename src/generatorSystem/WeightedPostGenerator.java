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
		Random rand = new Random();
		
		
		Vector<String[]> possibleStrts = markovChain.markovChain.get(startIdentifier);
		int sizeOfVector = possibleStrts.size();
		Vector<Integer> possibleStrtsWeights = new Vector<Integer>();
		int totalWeight = 0;
		
		// Get the weights...
		for(String[] s: possibleStrts)
		{
			int temp = markovChain.weightHashTable.get(s[0].hashCode());
			System.out.println("s: " + temp);
			totalWeight += temp;
			possibleStrtsWeights.add(temp);
		}
		
		// now that have weights, choose!
		int choice = rand.nextInt(totalWeight);
		
		String chosenWord = null;
		
		for(int index = 0, count = totalWeight; count >= choice ; count-= possibleStrtsWeights.get(index))
		{
			chosenWord = possibleStrts.get(index)[0];
			index++;
		}
		
		
		System.out.println("Got value " + choice + ", with value " + chosenWord);
		
		//generate words after the start! Same general idea.
		
		//TODO
		
		
		
		
		
		String returnItem = returnSentence.toString();
		
//		if(returnItem.endsWith("__END")) returnItem = returnItem.substring(0, returnItem.length()-5);
//		
//		if(returnItem.endsWith("__END ")) returnItem = returnItem.substring(0)
		
		return returnItem;
	}
	
}
