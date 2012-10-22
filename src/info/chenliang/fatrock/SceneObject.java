package info.chenliang.fatrock;

import info.chenliang.ds.Matrix3x3;
import info.chenliang.ds.Matrix4x4;
import info.chenliang.ds.Vector3d;

public class SceneObject {
	public SceneObject parent;
	public Mesh mesh;
	
	public Matrix4x4 transform;
	
	public SceneObject(SceneObject parent, Vector3d position) {
		super();
		this.parent = parent;
		mesh = new Mesh();
		transform = new Matrix4x4();
	}
	
	public void rotate(Vector3d around, float angle)
	{
		Vector3d n = new Vector3d(around);
		n.normalize();
		
		Matrix3x3 r = Matrix3x3.buildRotateMatrix(n, angle);
		transform.set(r);
	}
	
	public void translate(Vector3d position)
	{
		transform.m03 += position.x;
		transform.m13 += position.y;
		transform.m23 += position.z;
	}
	
	public void update()
	{
		for(int i = 0; i < mesh.vertices.size(); i++) 
		{
			Vertex3d v = mesh.vertices.get(i);
			v.transformedPosition = transform.transform(v.position);
		}
	}
}
