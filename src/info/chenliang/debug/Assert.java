package info.chenliang.debug;

public class Assert {
	public static void judge(boolean expression, String message)
	{
		if(!expression)
		{
			throw new RuntimeException(message);
		}
	}
}
