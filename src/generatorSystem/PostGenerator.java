package generatorSystem;

import java.util.ArrayList;

public class PostGenerator extends MarkovThreeState{
	private int sentenceLength = 140;
	private boolean running = false, save = false;
	
	PostGenerator(String savedPattern)
	{
		//TODO
		
	}
	
	// Used for creating a postGenerator!
	PostGenerator()
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
		//Will need to do the percentage calculations here for randomizing the generation. 
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
