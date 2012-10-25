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
		int index = y*width + x;
		if(index >= 0 && index < zBuffer.length)
		{
			return zBuffer[index];	
		}
		else
		{
			return zBufferComparer.getDefaultZ();
		}
		
	}

	@Override
	public void reset() {
		Arrays.fill(zBuffer, zBufferComparer.getDefaultZ());
	}

	@Override
	public void setZ(int x, int y, float z) {
		zBuffer[y*width + x] = z;
	}

	
}
