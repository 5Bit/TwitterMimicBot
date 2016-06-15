package generatorSystem;

import java.util.ArrayList;

public class PostGenerator {
	private int sentenceLength = 140;
	private boolean running = false, save = false;
	
	PostGenerator(String savedPattern)
	{
		//TODO
		
	}
	
	PostGenerator(ArrayList<ArrayList<String>> inKnown, double[][][] Probabilities)
	{
		//TODO
	}
	
	/**
	 * Toggles whether or not the save file for this pattern will be updated.
	 */
	public void toggleUpdateSave(){
		if(save == false) save = true;
		else save = false;
	}
	
	
	public String generate()
	{
		//TODO
		return null;
		
	}
	
	private void saveNewPostGeneratorPattern()
	{
		//TODO
		
	}
	
	public void stop()
	{
		//TODO
	}
	

}
