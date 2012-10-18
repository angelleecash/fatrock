package info.chenliang.fatrock;

public class ZBufferComparerLessThan implements ZBufferComparer{

	public boolean compare(float oldZ, float newZ){
		return newZ < oldZ;
	}
	
}
