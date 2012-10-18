package info.chenliang.fatrock;

import info.chenliang.ds.Vector3d;

public class DirectionLight extends Light {
	public Vector3d direction;

	public DirectionLight(Vector3d ambient, Vector3d diffuse,
			Vector3d specular, Vector3d direction) {
		super(ambient, diffuse, specular);
		this.direction = direction;
		this.direction.normalize();
	}
	
	public void light(Vertex3d v)
	{
		Vector3d color = new Vector3d(v.color);
		Vector3d ambient = new Vector3d(v.material.ambient.x*this.ambient.x, v.material.ambient.y*this.ambient.y, v.material.ambient.z*this.ambient.z);
		
		float dot = direction.scale2(-1).dot(v.normal);
		dot = dot < 0 ? 0 : dot;
		Vector3d diffuse = new Vector3d(v.material.diffuse.x*this.diffuse.x, v.material.diffuse.y*this.diffuse.y, v.material.diffuse.z*this.diffuse.z);
		diffuse.scale(dot);
		
		color = color.add(ambient);
		color = color.add(diffuse);
//		color.x *= diffuse.x;
//		color.y *= diffuse.y;
//		color.z *= diffuse.z;		
		
		color.clamp(0, 255);
		v.transformedColor.copy(color);
	}
}
