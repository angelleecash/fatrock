package info.chenliang.ds;

public class Bounds {
	private Rect rect;
	
	public Bounds()
	{
		rect = new Rect();
		rect.left = Float.MAX_VALUE;
		rect.right = Float.MIN_VALUE;
		rect.top = Float.MAX_VALUE;
		rect.bottom = Float.MIN_VALUE;
	}
	
	public void join(float x, float y)
	{
		rect.left = Math.min(rect.left, x);
		rect.right = Math.max(rect.right, x);
		rect.top = Math.min(rect.top, y);
		rect.bottom = Math.max(rect.bottom, y);
	}
	
	public float left()
	{
		return rect.left;
	}
	
	public float right()
	{
		return rect.right;
	}
	
	public float top()
	{
		return rect.top;
	}
	
	public float bottom()
	{
		return rect.bottom;
	}
}
