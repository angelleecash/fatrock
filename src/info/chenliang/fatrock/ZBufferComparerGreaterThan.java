package info.chenliang.fatrock;

public class ZBufferComparerGreaterThan implements ZBufferComparer{

	public boolean compare(float oldZ, float newZ){
		return newZ > oldZ;
	}
	

}
