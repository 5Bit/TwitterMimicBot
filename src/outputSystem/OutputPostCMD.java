package outputSystem;

public class OutputPostCMD implements OutputPoster {
	
	public void submit(String post) {
		System.out.println("Generated line:");
		System.out.println(post);
	}

}
