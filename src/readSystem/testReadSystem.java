package readSystem;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.junit.Test;

public class testReadSystem {
	
	
	@Test
	public void txtFileReadAndPrint()
	{
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		ReadTxtFile reader = new ReadTxtFile();
		String[] target = {"\\Users\\Cuin\\Documents\\GitHub\\TwitterMimicBot\\TestFiles", "TantarusTweetSamples.txt"};
		System.out.println("Testing target.\n target: " + target[0] + "\\" + target[1]);
		try {
			reader.setTarget(target);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("The file was not found.");
			e.printStackTrace();
			fail();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			System.out.println("The encoding provided was not supported");
			e.printStackTrace();
			fail();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("There was an error reading from the file.");
			e.printStackTrace();
			fail();
		}
		
		ArrayList<String> temp = reader.toArrayList();
		if(temp == null) fail();
		int lineCount = 0;
		for(String str: temp)
		{
			System.out.println("Line " + lineCount + " : " + str);
			out.println(str);
			++lineCount;
		}
		//System.out.println("Number of characters: " + );
		System.out.println("Number of lines:" + lineCount);
	}

}
