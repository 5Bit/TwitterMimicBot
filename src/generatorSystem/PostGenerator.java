package generatorSystem;


import java.util.Random;
import java.util.Vector;

public class PostGenerator extends MarkovThreeState {
	private int sentenceLength = 140;

	/**
	 * Constructor to create a post generator from a saved PatternAnalyzer.
	 * @param savedPattern
	 */
	PostGenerator(String savedPattern) {
		// TODO

	}

	/**
	 * Constructor to construct a post generator from new data.
	 */
	PostGenerator() {
		// Filler - in case needed.
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
				Vector<String> tempSelect = markovChain.get(nextWord);
				int tempLength = tempSelect.size();
				nextWord = tempSelect.get(rand.nextInt(tempLength));
				returnSentence.append(nextWord);
			} while (!returnSentence.toString().contains("__END"));

		} while (returnSentence.length() <= sentenceLength + 11);
		
		// removes the "__STRT" and "__END"
		String returnItem = returnSentence.toString();
		returnItem.substring(6, 146);
		
		return returnItem;

	}

}
