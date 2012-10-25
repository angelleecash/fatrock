package info.chenliang.ds;

public class Precision {
	private static float error = 0.0001f;
	private static Precision instance;
	
	public static Precision getInstance()
	{
		if(instance == null)
		{
			instance = new Precision();
		}
		
		return instance;
	}
	
	
	public boolean equals(float f1, float f2)
	{
		return Math.abs(f1-f2) < error;
	}
}
