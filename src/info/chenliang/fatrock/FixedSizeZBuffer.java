package info.chenliang.fatrock;

import java.util.Arrays;

public class FixedSizeZBuffer extends ZBuffer {
	public float[] zBuffer;
	
	public FixedSizeZBuffer(int width, int height, ZBufferComparer  zBufferComparer)
	{
		super(width, height, zBufferComparer);
		zBuffer = new float[width*height];
	}
	
	@Override
	public float getZ(int x, int y) {
		return zBuffer[y*width + x];
	}

	@Override
	public void reset(float z) {
		Arrays.fill(zBuffer, z);
	}

	@Override
	public void setZ(int x, int y, float z) {
		zBuffer[y*width + x] = z;
	}

	
}
