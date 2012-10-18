package info.chenliang.fatrock;

import info.chenliang.ds.Vector3d;

public class DotLight extends Light {
	Vector3d position;
	float kc, kl, kq;
	
	public DotLight(Vector3d ambient, Vector3d diffuse, Vector3d specular,
			Vector3d position, float kc, float kl, float kq) {
		super(ambient, diffuse, specular);
		this.position = position;
		this.kc = kc;
		this.kl = kl;
		this.kq = kq;
	}
	
	public void light(Vertex3d v)
	{
		Vector3d color = new Vector3d(v.color);
		
		Vector3d l = position.minus(v.transformedPosition.degenerate());
		float distance = l.length();
		l.normalize();
		
		float dot = l.dot(v.normal);
		dot = dot < 0 ? 0: dot;
		
		float attenuation = dot/(kc + kl*distance);
		
		Vector3d ambient = new Vector3d(v.material.ambient.x*this.ambient.x, v.material.ambient.y*this.ambient.y, v.material.ambient.z*this.ambient.z);
		ambient.scale(attenuation);
		
		Vector3d diffuse = new Vector3d(v.material.diffuse.x*this.diffuse.x, v.material.diffuse.y*this.diffuse.y, v.material.diffuse.z*this.diffuse.z);
		diffuse.scale(attenuation);
		
		color = color.add(ambient);
		color = color.add(diffuse);
		
		color.clamp(0, 255);
		v.transformedColor.copy(color);
	}
}
