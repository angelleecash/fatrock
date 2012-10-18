package info.chenliang.ds;

import info.chenliang.debug.Assert;

public class Vector2d {
	public float x,y;

	public Vector2d(float x, float y) {
		super();
		this.x = x;
		this.y = y;
	}
	
	public Vector2d(Vector2d v)
	{
		this(v, 1.0f);
	}
	
	public Vector2d(Vector2d v, float scale)
	{
		copy(v);
		scale(scale);
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}
	
	public void scale(float factor)
	{
		x *= factor;
		y *= factor;
	}
	
	public Vector2d scale2(float factor)
	{
		Vector2d v = new Vector2d(x, y);
		v.scale(factor);
		
		return v;
	}
	
	public Vector2d clone()
	{
		return new Vector2d(x, y);
	}
	
	public void copy(Vector2d v)
	{
		this.x = v.x;
		this.y = v.y;
	}
	
	public float length()
	{
		return (float)Math.sqrt(x*x + y*y);
	}
	
	public void normalize()
	{
		float length = length();
		Assert.judge(!Precision.getInstance().equals(length ,0), "Can not normalize a length 0 vector2d.");
		float factor = 1/length;
		scale(factor);
	}
	
	public Vector2d add(Vector2d v)
	{
		return new Vector2d(x + v.x, y + v.y);
	}
	
	public Vector2d minus(Vector2d v)
	{
		return add(v.scale2(-1));
	}
	
	public void set(float x, float y)
	{
		this.x = x;
		this.y = y;
	}
}
