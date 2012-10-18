package info.chenliang.fatrock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Mesh {
	public List<Vertex3d> vertices;
	public List<Triangle> triangles;
	public Map<Integer, List<Triangle>> vertex2TriangleMap;
	public Mesh()
	{
		vertices = new ArrayList<Vertex3d>();
		triangles = new ArrayList<Triangle>();
		vertex2TriangleMap = new HashMap<Integer, List<Triangle>>();
	}
	
	public void updateNormals()
	{
		for(int i=0;i < triangles.size();i ++)
		{
			Triangle triangle = triangles.get(i);
			triangle.updateNormal();
		}
		
		for(int i=0;i < vertices.size();i ++)
		{
			Vertex3d v = vertices.get(i);
			v.normal.set(0, 0, 0);
			
			List<Triangle> joinedTriangles = vertex2TriangleMap.get(i);
			if(joinedTriangles.size() != 3)
			{
				System.out.println("!!!");
			}
			for(int j=0;j < joinedTriangles.size();j++)
			{
				Triangle triangle = joinedTriangles.get(j);
				v.normal = v.normal.add(triangle.normal);
			}
			
			/*
			for(int j=0;j < triangles.size();j ++)
			{
				Triangle triangle = triangles.get(j);
				if(triangle.v1 == i || triangle.v2 == i || triangle.v3 == i)
				{
					v.normal = v.normal.add(triangle.normal);
				}
			}
			*/
			
			v.normal.normalize();
		}
	}
	
}
