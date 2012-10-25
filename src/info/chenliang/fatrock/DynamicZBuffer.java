package info.chenliang.fatrock;

import android.util.SparseArray;

public class DynamicZBuffer extends ZBuffer {
	private SparseArray<Float> zBuffer;
	
	public DynamicZBuffer(int width, int height, ZBufferComparer zBufferComparer)
	{
		super(width, height, zBufferComparer);
		zBuffer = new SparseArray<Float>();
	}
	
	@Override
	public float getZ(int x, int y) {
		Float f = zBuffer.get(x << 16 | y);
		if(f == null)
		{
			f = zBufferComparer.getDefaultZ();
		}
		return f;
	}

	@Override
	public void setZ(int x, int y, float z) {
		zBuffer.put(x << 16 | y , z);
	}

	@Override
	public void reset() {
		zBuffer.clear();
	}

}
