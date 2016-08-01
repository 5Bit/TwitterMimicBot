package generatorSystem;

import java.util.ArrayList;
//import java.util.HashMap;
import java.util.Hashtable;
import java.util.Vector;

abstract class MarkovThreeState {

	ArrayList<String> knownSentences = new ArrayList<String>();
	public Hashtable<String, Vector<String[]>> markovChain = new Hashtable<String, Vector<String[]>>();
	
	
	

	
	long numberOfWordsTotal = 0;
	
}
