package info.chenliang.ds;

public class Line2d {
	public Vector2d p1, p2;
	
	public Line2d(float x1, float y1, float x2, float y2)
	{
		p1 = new Vector2d(x1, y1);
		p2 = new Vector2d(x2, y2);
	}
	
	public float line2dFxy(float x, float y)
	{
		float x1 = p1.x;
		float y1 = p1.y;

		float x2 = p2.x;
		float y2 = p2.y;

		float fxy = y * (x2 - x1) - x * (y2 - y1) + x1 * y2 - x2 * y1;
		return fxy;
	}

	public boolean line2dPointOnLine(float x, float y)
	{
		return line2dFxy(x, y) == 0;
	}
}
