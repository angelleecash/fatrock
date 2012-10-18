package info.chenliang.fatrock;

import info.chenliang.ds.Matrix4x4;
import info.chenliang.ds.Vector4d;

public class ViewFrustum {
	public Plane3d left;
	public Plane3d right;
	public Plane3d top;
	public Plane3d bottom;
	public Plane3d near;
	public Plane3d far;
	
	public ViewFrustum()
	{
		left = new Plane3d();
		right = new Plane3d();
		top = new Plane3d();
		bottom = new Plane3d();
		near = new Plane3d();
		far = new Plane3d();
	}
	
	public void update(Matrix4x4 m)
	{
		Vector4d v;
		float reciprocal;
		Vector4d row1, row2;
		
		//left plane set up
		row1 = m.getRow(0);
		row1.scale(-1);
		row2 = m.getRow(3);
		row2.scale(-1);
		v = row1.add(row2);
		
		left.normal = v.degenerate();
		left.distance = v.w;
		reciprocal = 1/left.normal.length(); 
		left.normal.scale(reciprocal);
		left.distance *= reciprocal;
		
		//right plane set up
		row1 = m.getRow(0);
		row2 = m.getRow(3);
		row2.scale(-1);
		v = row1.add(row2);
		
		right.normal = v.degenerate();
		right.distance = v.w;
		reciprocal = 1/right.normal.length(); 
		right.normal.scale(reciprocal);
		right.distance *= reciprocal;
		
		//top plane set up
		row1 = m.getRow(1);
		row2 = m.getRow(3);
		row2.scale(-1);
		v = row1.add(row2);
		
		top.normal = v.degenerate();
		top.distance = v.w;
		reciprocal = 1/top.normal.length(); 
		top.normal.scale(reciprocal);
		top.distance *= reciprocal;
		
		//bottom plane set up
		row1 = m.getRow(1);
		row1.scale(-1);
		row2 = m.getRow(3);
		row2.scale(-1);
		v = row1.add(row2);
		
		bottom.normal = v.degenerate();
		bottom.distance = v.w;
		reciprocal = 1/bottom.normal.length(); 
		bottom.normal.scale(reciprocal);
		bottom.distance *= reciprocal;
		
		//near plane set up
		row1 = m.getRow(2);
		row1.scale(-1);
		v = row1;
		near.normal = v.degenerate();
		near.distance = v.w;
		reciprocal = 1/near.normal.length(); 
		near.normal.scale(reciprocal);
		near.distance *= reciprocal;
		
		//far plane set up
		row1 = m.getRow(2);
		row2 = m.getRow(3);
		row2.scale(-1);
		v = row1.add(row2);
		
		far.normal = v.degenerate();
		far.distance = v.w;
		reciprocal = 1/far.normal.length(); 
		far.normal.scale(reciprocal);
		far.distance *= reciprocal;
	}
}
