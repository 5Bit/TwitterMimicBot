package generatorSystem;

import java.util.ArrayList;

class PostGenerator {
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
	
	
	public void generate()
	{
		//TODO
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
