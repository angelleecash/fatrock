package info.chenliang.fatrock;

public abstract class ZBuffer {
	public int width;
	public int height;
	public ZBufferComparer  zBufferComparer;
	
	public ZBuffer(int width, int height, ZBufferComparer  zBufferComparer)
	{
		this.width = width;
		this.height = height;
		this.zBufferComparer = zBufferComparer;
	}
	
	public abstract float getZ(int x, int y);
	public abstract void setZ(int x, int y, float z);
	public abstract void reset(float z);
}
