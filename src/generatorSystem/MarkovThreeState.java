package generatorSystem;

import java.util.ArrayList;
import java.util.HashMap;

abstract class MarkovThreeState {
	ArrayList<String> knownSentences = new ArrayList<String>();
	
	//TODO - might need to change to a hashmap
//	double[][][]probabilities;
	
	HashMap<String[], Integer> markovChain = new HashMap<String[], Integer>();
	long numberOfWordsTotal = 0;
	
}
