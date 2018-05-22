package Models;

public class RawModel 
{
	private int VaoId;
	private int vertexCount;
	
	public  RawModel (int _VaoID, int _vertexCount)
	{
		VaoId = _VaoID; 
		vertexCount = _vertexCount; 
	}

	public int getVaoId() {return VaoId;}
	public int getVertexCount() {return vertexCount;}
}
