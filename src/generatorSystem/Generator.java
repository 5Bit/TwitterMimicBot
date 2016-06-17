package generatorSystem;

import java.util.ArrayList;

public class Generator {
	PostGenerator postGen = null;
	
	
	/**
	 * Takes a save file location, and loads it as a Pattern Analyzer.
	 * @param save
	 */
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
			temp.append("__STRT");
			temp.append(str);
			temp.append("__END");
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
	
}
