package generatorSystem;

import java.util.Hashtable;

public class WeightedThreeStateMarkovChain extends MarkovThreeState{
	
	//Will hold the weights - key is the hash, value is the weight for that word.
	public Hashtable<Integer, Integer> weightHashTable = new Hashtable<Integer, Integer>();
	
	

}
