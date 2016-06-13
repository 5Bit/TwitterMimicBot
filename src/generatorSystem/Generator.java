package generatorSystem;

import java.util.ArrayList;

class Generator {
	//PatternAnalyzer pa = null;
	
	public Generator(String save)
	{
		//TODO
	}
	
	/**
	 * Takes an ArrayList of strings of lines, processes them for the Pattern Analyzer,
	 * and creates a pattern Analyzer to hold within it.
	 * @param lines
	 */
	public Generator(ArrayList<String> lines)
	{
		
		ArrayList<String> modifiedWithBuffers = new ArrayList<String>();
		for(String str: lines)
		{
			StringBuilder temp = new StringBuilder();
			temp.append("__");
			temp.append(str);
			temp.append("__");
			modifiedWithBuffers.add(temp.toString());
		}
		
		
		//TODO
	}
	
	public void run()
	{
		//TODO
	}
	
	private double timer()
	{
		//TODO - make seperate class?
		return 0;
	}
	
}
