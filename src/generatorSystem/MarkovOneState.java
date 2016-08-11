package generatorSystem;

import java.util.ArrayList;
//import java.util.HashMap;
import java.util.Hashtable;
import java.util.Vector;

abstract class MarkovOneState {

	//used to remember known sentences.
	public ArrayList<String> knownSentences = new ArrayList<String>();
	
	// this is the markov chain. There are many like it, but this one is mine.
	public Hashtable<String, Vector<String[]>> markovChain = new Hashtable<String, Vector<String[]>>();
	
}
