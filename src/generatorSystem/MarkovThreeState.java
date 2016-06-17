package generatorSystem;

import java.util.ArrayList;
//import java.util.HashMap;
import java.util.Hashtable;
import java.util.Vector;

abstract class MarkovThreeState {
	//TODO - reach goal of a three state check here!
	// First get running on a two state check, then a three.
	//CURRENT DESIGN: TWO STATE CHECK
	ArrayList<String> knownSentences = new ArrayList<String>();
	
	
	Hashtable<String, Vector<String>> markovChain = new Hashtable<String, Vector<String>>();
	
	long numberOfWordsTotal = 0;
	
}
