package info.chenliang.fatrock;

import java.util.Arrays;

import info.chenliang.ds.Vector2d;
import info.chenliang.ds.Vector3d;
import info.chenliang.ds.Vector4d;

public class CubeSceneObject extends SceneObject {
	public float size;
	public Material material;
	
	public CubeSceneObject(SceneObject parent, Vector3d position, float size, Material material) {
		super(parent, position);
		this.size = size;
		
		translate.m03 = position.x;
		translate.m13 = position.y;
		translate.m23 = position.z;
		
		this.material = material;
		
		float sizeHalf = size*.5f;
		
		boolean useGray = true;
		if(useGray)
		{
			// 0 ~ 3
			mesh.vertices.add(new Vertex3d(new Vector4d(-sizeHalf, -sizeHalf, -sizeHalf), new Vector3d(0, 0, 0)));
			mesh.vertices.add(new Vertex3d(new Vector4d(sizeHalf, -sizeHalf, -sizeHalf), new Vector3d(0, 0, 0)));
			mesh.vertices.add(new Vertex3d(new Vector4d(sizeHalf, -sizeHalf, sizeHalf), new Vector3d(0, 0, 0)));
			mesh.vertices.add(new Vertex3d(new Vector4d(-sizeHalf, -sizeHalf, sizeHalf), new Vector3d(0, 0, 0)));
					
			// 4 ~ 7
			mesh.vertices.add(new Vertex3d(new Vector4d(-sizeHalf, sizeHalf, -sizeHalf), new Vector3d(0, 0, 0)));
			mesh.vertices.add(new Vertex3d(new Vector4d(sizeHalf, sizeHalf, -sizeHalf), new Vector3d(0, 0, 0)));
			mesh.vertices.add(new Vertex3d(new Vector4d(sizeHalf, sizeHalf, sizeHalf), new Vector3d(0, 0, 0)));
			mesh.vertices.add(new Vertex3d(new Vector4d(-sizeHalf, sizeHalf, sizeHalf), new Vector3d(0, 0, 0)));
		}
		else
		{
			// 0 ~ 3
			mesh.vertices.add(new Vertex3d(new Vector4d(-sizeHalf, -sizeHalf, -sizeHalf), new Vector3d(255, 0, 0)));
			mesh.vertices.add(new Vertex3d(new Vector4d(sizeHalf, -sizeHalf, -sizeHalf), new Vector3d(0, 255, 0)));
			mesh.vertices.add(new Vertex3d(new Vector4d(sizeHalf, -sizeHalf, sizeHalf), new Vector3d(0, 0, 255)));
			mesh.vertices.add(new Vertex3d(new Vector4d(-sizeHalf, -sizeHalf, sizeHalf), new Vector3d(255, 255, 0)));
			
			// 4 ~ 7
			mesh.vertices.add(new Vertex3d(new Vector4d(-sizeHalf, sizeHalf, -sizeHalf), new Vector3d(255, 0, 255)));
			mesh.vertices.add(new Vertex3d(new Vector4d(sizeHalf, sizeHalf, -sizeHalf), new Vector3d(0, 255, 255)));
			mesh.vertices.add(new Vertex3d(new Vector4d(sizeHalf, sizeHalf, sizeHalf), new Vector3d(125, 125, 0)));
			mesh.vertices.add(new Vertex3d(new Vector4d(-sizeHalf, sizeHalf, sizeHalf), new Vector3d(0, 125, 125)));
		}
		
		
		mesh.triangles.add(new Triangle(mesh, 0, 1, 3, new Vector2d(0.0f, 1.0f), new Vector2d(1.0f, 1.0f), new Vector2d(0.0f, 0.0f)));//0
		mesh.triangles.add(new Triangle(mesh, 3, 1, 2, new Vector2d(0.0f, 0.0f), new Vector2d(1.0f, 1.0f), new Vector2d(1.0f, 0.0f)));//1
		
		mesh.triangles.add(new Triangle(mesh, 7, 5, 4, new Vector2d(0.0f, 0.0f), new Vector2d(1.0f, 1.0f), new Vector2d(0.0f, 1.0f)));//2
		mesh.triangles.add(new Triangle(mesh, 7, 6, 5, new Vector2d(0.0f, 0.0f), new Vector2d(1.0f, 0.0f), new Vector2d(1.0f, 1.0f)));//3
		
		mesh.triangles.add(new Triangle(mesh, 0, 5, 1, new Vector2d(0.0f, 1.0f), new Vector2d(1.0f, 0.0f), new Vector2d(1.0f, 1.0f)));//4
		mesh.triangles.add(new Triangle(mesh, 0, 4, 5, new Vector2d(0.0f, 1.0f), new Vector2d(0.0f, 0.0f), new Vector2d(1.0f, 0.0f)));//5
		
		mesh.triangles.add(new Triangle(mesh, 2, 1, 5, new Vector2d(1.0f, 1.0f), new Vector2d(0.0f, 1.0f), new Vector2d(0.0f, 0.0f)));//6
		mesh.triangles.add(new Triangle(mesh, 5, 6, 2, new Vector2d(0.0f, 0.0f), new Vector2d(1.0f, 0.0f), new Vector2d(1.0f, 1.0f)));//7
		
		mesh.triangles.add(new Triangle(mesh, 3, 2, 6, new Vector2d(1.0f, 1.0f), new Vector2d(0.0f, 1.0f), new Vector2d(0.0f, 0.0f)));//8
		mesh.triangles.add(new Triangle(mesh, 3, 6, 7, new Vector2d(1.0f, 1.0f), new Vector2d(0.0f, 0.0f), new Vector2d(1.0f, 0.0f)));//9
		
		mesh.triangles.add(new Triangle(mesh, 3, 4, 0, new Vector2d(0.0f, 1.0f), new Vector2d(1.0f, 0.0f), new Vector2d(1.0f, 1.0f)));//10
		mesh.triangles.add(new Triangle(mesh, 4, 3, 7, new Vector2d(1.0f, 0.0f), new Vector2d(0.0f, 1.0f), new Vector2d(0.0f, 0.0f)));//11
		
		mesh.vertex2TriangleMap.put(0, Arrays.asList(mesh.triangles.get(0), mesh.triangles.get(4), mesh.triangles.get(10)));
		mesh.vertex2TriangleMap.put(1, Arrays.asList(mesh.triangles.get(1), mesh.triangles.get(4), mesh.triangles.get(6)));
		mesh.vertex2TriangleMap.put(2, Arrays.asList(mesh.triangles.get(1), mesh.triangles.get(6), mesh.triangles.get(8)));
		mesh.vertex2TriangleMap.put(3, Arrays.asList(mesh.triangles.get(0), mesh.triangles.get(8), mesh.triangles.get(10)));
		mesh.vertex2TriangleMap.put(4, Arrays.asList(mesh.triangles.get(2), mesh.triangles.get(5), mesh.triangles.get(11)));
		mesh.vertex2TriangleMap.put(5, Arrays.asList(mesh.triangles.get(2), mesh.triangles.get(4), mesh.triangles.get(6)));
		mesh.vertex2TriangleMap.put(6, Arrays.asList(mesh.triangles.get(3), mesh.triangles.get(7), mesh.triangles.get(8)));
		mesh.vertex2TriangleMap.put(7, Arrays.asList(mesh.triangles.get(2), mesh.triangles.get(9), mesh.triangles.get(11)));
		
		for(Vertex3d v : mesh.vertices)
		{
			v.material = material;
		}
	}
	
	
}
