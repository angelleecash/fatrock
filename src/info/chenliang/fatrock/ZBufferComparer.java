package info.chenliang.fatrock;

public interface ZBufferComparer {
	public boolean compare(float oldZ, float newZ);
	public float getDefaultZ();
}
