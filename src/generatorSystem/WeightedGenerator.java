package generatorSystem;

import java.util.ArrayList;

public class WeightedGenerator {
	WeightedPostGenerator postGen = null;
	WeightedPatternAnalyzer pa = null;
	

	
	public WeightedGenerator(ArrayList<String> sourceLines)
	{
		pa = new WeightedPatternAnalyzer(sourceLines);

		postGen = new WeightedPostGenerator(pa.markChain);
	}
	
	/**
	 * Returns the weighted post generator.
	 * @return
	 */
	public WeightedPostGenerator getPostGeneratorData()
	{
		return postGen;
	}
	
	
	public String run()
	{
		return postGen.generate();
	}
	
	/**
	 * Returns the markov chain this generator has.
	 * @return
	 */
	public WeightedThreeStateMarkovChain getMarkovChain()
	{
		//TODO - safety - code so it returns a copy.
		return pa.markChain;
	}

}
