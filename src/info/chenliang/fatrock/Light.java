package info.chenliang.fatrock;

import info.chenliang.ds.Vector3d;

public class Light {
	Vector3d ambient;
	Vector3d diffuse;
	Vector3d specular;
	
	public Light(Vector3d ambient, Vector3d diffuse, Vector3d specular) {
		super();
		this.ambient = ambient;
		this.diffuse = diffuse;
		this.specular = specular;
	}
	
	public void light(Vertex3d v)
	{
	}
}
