package info.chenliang.ds;

public class Matrix4x4 {
	public float m00, m01, m02, m03;
	public float m10, m11, m12, m13;
	public float m20, m21, m22, m23;
	public float m30, m31, m32, m33;
	
	public Matrix4x4()
	{
		set(1, 0, 0, 0, 
			0, 1, 0, 0, 
			0, 0, 1, 0, 
			0, 0, 0, 1);
	}
	
	public void set(float m00, float m01, float m02, float m03,
					float m10, float m11, float m12, float m13,
					float m20, float m21, float m22, float m23,
					float m30, float m31, float m32, float m33)
	{
		this.m00 = m00; this.m01 = m01; this.m02 = m02; this.m03 = m03;
		this.m10 = m10; this.m11 = m11; this.m12 = m12; this.m13 = m13;
		this.m20 = m20; this.m21 = m21; this.m22 = m22; this.m23 = m23;
		this.m30 = m30; this.m31 = m31; this.m32 = m32; this.m33 = m33;
	}
	
	public Matrix4x4 multiply(Matrix4x4 m)
	{
		Matrix4x4 result = new Matrix4x4();
		
		result.m00 = new Vector4d(m00, m01, m02, m03).dot(new Vector4d(m.m00, m.m10, m.m20, m.m30));
		result.m01 = new Vector4d(m00, m01, m02, m03).dot(new Vector4d(m.m01, m.m11, m.m21, m.m31));
		result.m02 = new Vector4d(m00, m01, m02, m03).dot(new Vector4d(m.m02, m.m12, m.m22, m.m32));
		result.m03 = new Vector4d(m00, m01, m02, m03).dot(new Vector4d(m.m03, m.m13, m.m23, m.m33));
		
		result.m10 = new Vector4d(m10, m11, m12, m13).dot(new Vector4d(m.m00, m.m10, m.m20, m.m30));
		result.m11 = new Vector4d(m10, m11, m12, m13).dot(new Vector4d(m.m01, m.m11, m.m21, m.m31));
		result.m12 = new Vector4d(m10, m11, m12, m13).dot(new Vector4d(m.m02, m.m12, m.m22, m.m32));
		result.m13 = new Vector4d(m10, m11, m12, m13).dot(new Vector4d(m.m03, m.m13, m.m23, m.m33));
		
		result.m20 = new Vector4d(m20, m21, m22, m23).dot(new Vector4d(m.m00, m.m10, m.m20, m.m30));
		result.m21 = new Vector4d(m20, m21, m22, m23).dot(new Vector4d(m.m01, m.m11, m.m21, m.m31));
		result.m22 = new Vector4d(m20, m21, m22, m23).dot(new Vector4d(m.m02, m.m12, m.m22, m.m32));
		result.m23 = new Vector4d(m20, m21, m22, m23).dot(new Vector4d(m.m03, m.m13, m.m23, m.m33));
		
		result.m30 = new Vector4d(m30, m31, m32, m33).dot(new Vector4d(m.m00, m.m10, m.m20, m.m30));
		result.m31 = new Vector4d(m30, m31, m32, m33).dot(new Vector4d(m.m01, m.m11, m.m21, m.m31));
		result.m32 = new Vector4d(m30, m31, m32, m33).dot(new Vector4d(m.m02, m.m12, m.m22, m.m32));
		result.m33 = new Vector4d(m30, m31, m32, m33).dot(new Vector4d(m.m03, m.m13, m.m23, m.m33));
		
		return result;
	}
	
	public Vector4d transform(Vector4d v)
	{
		Vector4d result = new Vector4d(0, 0, 0, 1);
		
		result.x = new Vector4d(m00, m01, m02, m03).dot(v);
		result.y = new Vector4d(m10, m11, m12, m13).dot(v);
		result.z = new Vector4d(m20, m21, m22, m23).dot(v);
		result.w = new Vector4d(m30, m31, m32, m33).dot(v);
		
		return result;
	}
	
	public Vector4d getRow(int row)
	{
		if(row == 0)
		{
			return new Vector4d(m00, m01, m02, m03);
		}
		else if(row == 1)
		{
			return new Vector4d(m10, m11, m12, m13);
		}
		else if(row == 2)
		{
			return new Vector4d(m20, m21, m22, m23);
		}
		else if(row == 3)
		{
			return new Vector4d(m30, m31, m32, m33);
		}
		
		throw new RuntimeException("Only row index 0, 1, 2 ,3 are valid row index.");
	}
	
	public Vector3d rotate(Vector3d v)
	{
		Vector3d result = new Vector3d(0, 0, 0);
		
		result.x = new Vector3d(m00, m01, m02).dot(v);
		result.y = new Vector3d(m10, m11, m12).dot(v);
		result.z = new Vector3d(m20, m21, m22).dot(v);
		
		return result;
	}
}
