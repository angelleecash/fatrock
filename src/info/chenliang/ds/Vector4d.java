package info.chenliang.ds;

public class Vector4d {
	public float x, y, z, w;

	public Vector4d()
	{
		set(0.0f, 0.0f, 0.0f, 1.0f);
	}
	
	public Vector4d(Vector4d v)
	{
		copy(v);
	}
	
	public Vector4d(float x, float y, float z, float w) {
		super();
		set(x, y, z, w);
	}
	
	public Vector4d(float x, float y, float z) {
		super();
		set(x, y, z, 1);
	}
	
	public void set(float x, float y, float z, float w)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}
	
	public void set(Vector3d v, float w)
	{
		set(v.x, v.y, v.z, w);
	}
	
	public Vector4d(Vector3d v, float w)
	{
		this(v.x, v.y, v.z, w);
	}
	
	public float dot(Vector4d v)
	{
		return x*v.x + y*v.y + z*v.z + w*v.w;
	}
	
	public Vector3d degenerate()
	{
		return new Vector3d(x, y, z);
	}
	
	public Vector4d add(Vector4d v)
	{
		return new Vector4d(x+v.x, y+v.y, z+v.z, w+v.w);
	}
	
	public void scale(float factor)
	{
		x *= factor;
		y *= factor;
		z *= factor;
		w *= factor;
	}
	
	public void copy(Vector4d v)
	{
		set(v.x, v.y, v.z, v.w);
	}
}
