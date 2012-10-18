package info.chenliang.ds;

import info.chenliang.debug.Assert;

public class Vector3d {
	public float x=0.0f,y=0.0f,z=0.0f;

	public Vector3d(float x, float y, float z) {
		super();
		set(x, y, z);
	}
	
	public Vector3d()
	{
	}
	
	public Vector3d(Vector3d from, Vector3d to)
	{
		copy(to.minus(from));
	}
	
	public Vector3d(Vector3d v)
	{
		copy(v);
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

	public float getZ() {
		return z;
	}

	public void setZ(float z) {
		this.z = z;
	}
	
	public void set(float x, float y, float z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public void scale(float factor)
	{
		x *= factor;
		y *= factor;
		z *= factor;
	}
	
	public Vector3d scale2(float factor)
	{
		Vector3d v = new Vector3d(this);
		v.scale(factor);
		return v;
	}
	
	public Vector3d clone()
	{
		return new Vector3d(x, y, z);
	}
	
	public void copy(Vector3d v)
	{
		this.x = v.x;
		this.y = v.y;
		this.z = v.z;
	}
	
	public float length()
	{
		return (float)Math.sqrt(x*x + y*y + z*z);
	}
	
	public void normalize()
	{
		float length = length();
		Assert.judge(length != 0, "Can not normalize a length 0 vector3d.");
		x /= length;
		y /= length;
		z /= length;
	}
	
	public float dot(Vector3d v)
	{
		return x*v.x + y*v.y + z*v.z;
	}
	
	public Vector3d minus(Vector3d v)
	{
		Vector3d result = new Vector3d(v);
		result.scale(-1);
		return add(result);
	}
	
	public Vector3d add(Vector3d v)
	{
		Vector3d result = new Vector3d(this);
		result.x += v.x;
		result.y += v.y;
		result.z += v.z;
		return result;
	}
	
	public Vector3d cross(Vector3d v)
	{
		Vector3d result = new Vector3d();
		result.x = y*v.z - z*v.y;
		result.y = z*v.x - x*v.z;
		result.z = x*v.y - y*v.x;
		return result;
	}
	
	public Vector3d rotateAround(Vector3d n, float angle)
	{
		Assert.judge(Precision.getInstance().equals(1, n.length()), "n should be a unit vector!");
		
		//w = v_|_ X n
		//v' = w*sinx + v_|_cosx
		Vector3d vParallel = new Vector3d(n);
		vParallel.scale(dot(n));
		Vector3d vPerpendicular = minus(vParallel);
		
		Vector3d w = n.cross(vPerpendicular);
		float angleInRadian = (float)(Math.toRadians(angle));
		float sinx = (float)(Math.sin(angleInRadian));
		float cosx = (float)(Math.cos(angleInRadian));
		
		w.scale(sinx);
		vPerpendicular.scale(cosx);
		
		return w.add(vPerpendicular).add(vParallel);
	}
	
	public Vector3d scaleAlong(Vector3d n, float k)
	{
		Vector3d vParallel = new Vector3d(n);
		vParallel.scale(dot(n));
		
		Vector3d vPerpendicular = minus(vParallel);
		
		vParallel.scale(k);
		
		return vPerpendicular.add(vParallel);
	}
	
	public void clamp(float min, float max)
	{
		x = Math.min(x, max);
		x = Math.max(x, min);
		
		y = Math.min(y, max);
		y = Math.max(y, min);
		
		y = Math.min(y, max);
		y = Math.max(y, min);
	}
	
	public int asColor()
	{
		return 0xff000000|(int)(x) << 16 | (int)(y) << 8 | (int)(z);
	}
}
