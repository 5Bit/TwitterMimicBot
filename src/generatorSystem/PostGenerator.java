package generatorSystem;



import java.util.Hashtable;
import java.util.Random;
import java.util.Vector;

public class PostGenerator extends MarkovOneState {
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
	PostGenerator(Hashtable<String, Vector<String[]>> inMarkovChain) {
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
			
			Vector<String[]> strt = markovChain.get("__STRT");
			int sizeOfVector = strt.size();
			String[] nextWords = strt.get(rand.nextInt(sizeOfVector));

			returnSentence.append(nextWords[0]);
			returnSentence.append(" ");
			returnSentence.append(nextWords[1]);
			do {
				returnSentence.append(" ");
				
				//For debugging.
//				System.out.println("Choosing from " + nextWords[1]);
				
				Vector<String[]> tempSelect = markovChain.get(nextWords[1]);
				if(tempSelect == null) break; // TODO - REMOVE THIS EVIL CREATURE!
				int tempLength = tempSelect.size();
				nextWords = tempSelect.get(rand.nextInt(tempLength));

				returnSentence.append(nextWords[0]);
				returnSentence.append(" ");
				returnSentence.append(nextWords[1]);
				
				//For debugging.
				//System.out.println("Line in development is: " + returnSentence.toString());
				
				
			} while ((!nextWords[0].equals("__END") && returnSentence.length() <= sentenceLength) && !nextWords[1].equals("__END"));
			
		} while ((returnSentence.length() <= sentenceLength) && !sentenceAlreadyKnown(returnSentence.toString()));
		String returnItem = returnSentence.toString();
		
		if(returnItem.endsWith("__END")) returnItem = returnItem.substring(0, returnItem.length()-5);
		
		if(returnItem.endsWith(" __END ")) returnItem = returnItem.substring(0, returnItem.length()-7);
		
		return returnItem;

	}
	
	/**
	 * Returns the hashtable of the markov chain.
	 * @return
	 */
	public Hashtable<String, Vector<String[]>> getMarkovChain()
	{
		return markovChain;
	}

}
