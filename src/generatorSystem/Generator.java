package generatorSystem;

import java.util.ArrayList;

public class Generator {
	PostGenerator postGen = null;
	
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
		
		
		PatternAnalyzer pa = new PatternAnalyzer(modifiedWithBuffers);
		
		postGen = pa.toPostGenerator();
	}
	
	public PostGenerator getPostGeneratorData(){
		return postGen;
	}
	
	public String run()
	{
		return postGen.generate();
	}
	
	private double timer()
	{
		//TODO - make separate class?
		return 0;
	}
	
}
