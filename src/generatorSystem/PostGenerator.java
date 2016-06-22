package generatorSystem;



import java.util.Hashtable;
import java.util.Random;
import java.util.Vector;

public class PostGenerator extends MarkovThreeState {
	private int sentenceLength = 140;

	/**
	 * Constructor to create a post generator from a saved PatternAnalyzer.
	 * @param savedPattern
	 */
	PostGenerator(String savedPattern) {
	}

	/**
	 * Constructor to construct a post generator from new data.
	 */
	PostGenerator(Hashtable<String, Vector<String>> inMarkovChain) {
		this.markovChain = inMarkovChain;
	}
	
	/**
	 * Used to verify if a sentence is already known. if it isn't it adds it to known sentences, and returns false.
	 * If it is, it returns true.
	 * @param inSentence
	 * @return
	 */
	private Boolean sentenceAlreadyKnown(String inSentence)
	{
		if(knownSentences.contains(inSentence)) return true;
		
		//TODO - Improve later. Possibly create a basic parser?
		
		knownSentences.add(inSentence);
		return false;
	}

	/**
	 * Generates a sentence based on the markov chain held inside MarkovThreeState.
	 * Only returns a sentence of acceptable length for twitter.
	 * @return
	 */
	public String generate() {
		StringBuilder returnSentence = new StringBuilder();
		do {
			returnSentence = new StringBuilder();
			Random rand = new Random();
			
			Vector<String> strt = markovChain.get("__STRT");
			int sizeOfVector = strt.size();
			String nextWord = strt.get(rand.nextInt(sizeOfVector));
			returnSentence.append(nextWord);
			
			do {
				returnSentence.append(" ");
				Vector<String> tempSelect = markovChain.get(nextWord);
				int tempLength = tempSelect.size();
				nextWord = tempSelect.get(rand.nextInt(tempLength));
				returnSentence.append(nextWord);
			} while (!nextWord.equals("__END") && returnSentence.length() <= sentenceLength );
			
		} while ((returnSentence.length() <= sentenceLength) && !sentenceAlreadyKnown(returnSentence.toString()));
		
		String returnItem = returnSentence.toString();
		
		if(returnItem.endsWith("__END")) returnItem = returnItem.substring(0, returnItem.length()-5);
		
		return returnItem;

	}
	
	public Hashtable<String, Vector<String>> getMarkovChain()
	{
		return markovChain;
	}

}
