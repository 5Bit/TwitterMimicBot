package generatorSystem;

import java.util.ArrayList;
import java.util.Set;
import java.util.Vector;

public class WeightedGenerator {
	WeightedPostGenerator postGen = null;
	WeightedPatternAnalyzer pa = null;
	

	
	/**
	 * Constructs the Weighted generator, building a Weighted Pattern Analyzer and a
	 * weighted post generator.
	 * @param sourceLines
	 */
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
	
	/**
	 * on execution, generates and returns a new tweet.
	 * @return
	 */
	public String run()
	{
		return postGen.generate();
	}
	
	/**
	 * Returns the markov chain this generator has.
	 * @return
	 */
	public WeightedMarkovChain getMarkovChain()
	{
		return pa.markChain;
	}
	

	public void checkMarkovChain()
	{
		if(pa.markChain.markovChain.isEmpty()) System.out.println("The markov chain is empty - this aint good, yo.");
		
		Set<String> keys = pa.markChain.markovChain.keySet();
		
		//NOTE: Used for Showing storage within markov chain!
		// modified to see the weights as well.
		// Comment out rather than delete.
		for(String key: keys)
		{
			System.out.print(pa.markChain.weightHashTable.get(key.hashCode()) + "| " + key + " : ");
			Vector<String[]> tempStringVec = pa.markChain.markovChain.get(key);
			
			for(String[] s : tempStringVec)
			{
				for(int i = 0; i < s.length; i++)
					System.out.print(s[i] + " ");
				
				System.out.print(" :: ");
			}
			
			System.out.println("");
		}
		
		//Used for showing the known sentences within the markov chain!
//		for(String s: pa.markChain.knownSentences)
//		{
//			System.out.println(s);
//		}
	}

}
