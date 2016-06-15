package generatorSystem;

import java.util.ArrayList;

abstract class MarkovThreeState {
	ArrayList<String> knownSentences = new ArrayList<String>();
	
	//TODO - might need to change to a hashmap
	double[][][]probabilities;
	
	
}
