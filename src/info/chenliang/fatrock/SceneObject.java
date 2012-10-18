package info.chenliang.fatrock;

import info.chenliang.ds.Matrix4x4;
import info.chenliang.ds.Vector3d;

public class SceneObject {
	public SceneObject parent;
	public Vector3d position;
	public Mesh mesh;
	public Matrix4x4 translate;
	public Matrix4x4 rotation;
	
	public SceneObject(SceneObject parent, Vector3d position) {
		super();
		this.parent = parent;
		this.position = position;
		
		mesh = new Mesh();
		translate = new Matrix4x4();
		rotation = new Matrix4x4();
	}
	
	
}
