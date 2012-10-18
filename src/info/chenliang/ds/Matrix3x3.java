package info.chenliang.ds;

public class Matrix3x3 {
	public float m00, m01, m02;
	public float m10, m11, m12;
	public float m20, m21, m22;
	
	public Matrix3x3()
	{
		set(1, 0, 0,
			0, 1, 0,
			0, 0, 1);
	}
	
	public Matrix3x3(float m00, float m01, float m02,
					 float m10, float m11, float m12,
					 float m20, float m21, float m22)
	{
		set(m00, m01, m02,
			m10, m11, m12,
			m20, m21, m22);
	}
	
	public Matrix3x3(Vector3d xAxis, Vector3d yAxis, Vector3d zAxis)
	{
		set(xAxis, yAxis, zAxis);
	}
	
	public static Matrix3x3 buildRotateMatrix(Vector3d n, float angle)
	{
		Vector3d xAxis = new Vector3d(1, 0, 0).rotateAround(n, angle);
		Vector3d yAxis = new Vector3d(0, 1, 0).rotateAround(n, angle);
		Vector3d zAxis = new Vector3d(0, 0, 1).rotateAround(n, angle);
		
		Matrix3x3 m = new Matrix3x3();
		m.set(xAxis.x, yAxis.x, zAxis.x,
			  xAxis.y, yAxis.y, zAxis.y,
			  xAxis.z, yAxis.z, zAxis.z);
		return m;
	}
	
	public static Matrix3x3 buildScaleMatrix(Vector3d n, float k)
	{
		Vector3d xAxis = new Vector3d(1, 0, 0).scaleAlong(n, k);
		Vector3d yAxis = new Vector3d(0, 1, 0).scaleAlong(n, k);
		Vector3d zAxis = new Vector3d(0, 0, 1).scaleAlong(n, k);
		
		return new Matrix3x3(xAxis, yAxis, zAxis);
	}
	
	public void set(float m00, float m01, float m02,
					float m10, float m11, float m12,
					float m20, float m21, float m22)
	{
		this.m00 = m00; this.m01 = m01; this.m02 = m02;
		this.m10 = m10; this.m11 = m11; this.m12 = m12;
		this.m20 = m20; this.m21 = m21; this.m22 = m22;
	}
	
	public void set(Vector3d xAxis, Vector3d yAxis, Vector3d zAxis)
	{
		set(xAxis.x, xAxis.y, xAxis.z,
			yAxis.x, yAxis.y, yAxis.z,
			zAxis.x, zAxis.y, zAxis.z);
	}
	
	public Matrix3x3 identity()
	{
		return new Matrix3x3();
	}
	
	public Vector3d transform(Vector3d v)
	{
		Vector3d result = new Vector3d();
		result.x = new Vector3d(m00, m01, m02).dot(v);
		result.y = new Vector3d(m10, m11, m12).dot(v);
		result.z = new Vector3d(m20, m21, m22).dot(v);
		return result;
	}
}
