package generatorSystem;

public class WeightedPostGenerator {
	
	private final int sentenceLength = 140;
	private WeightedThreeStateMarkovChain markovChain = null;
	
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
		
		//TODO
		
		
		return null;
	}
	
}
