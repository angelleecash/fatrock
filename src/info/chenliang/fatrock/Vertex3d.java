package info.chenliang.fatrock;

import info.chenliang.ds.Vector2d;
import info.chenliang.ds.Vector3d;
import info.chenliang.ds.Vector4d;

public class Vertex3d {
	public Vector4d position;
	public Vector4d transformedPosition;
	public Vector3d color;
	public Vector3d transformedColor;
	public Vector3d normal = new Vector3d();
	
	public Material material;
	public Vector2d texturePosition;
	
	public Vertex3d(Vector4d position, Vector3d color)
	{
		this.position = position;
		this.color = color;
		this.transformedPosition = new Vector4d(this.position);
		this.transformedColor = new Vector3d(this.color);
	}
	
	public Vertex3d(Vertex3d v)
	{
		this.position = new Vector4d(v.position);
		this.color = new Vector3d(v.color);
		this.transformedPosition = new Vector4d(this.position);
		this.transformedColor = new Vector3d(this.color);
		this.texturePosition = new Vector2d(texturePosition);
	}
}
