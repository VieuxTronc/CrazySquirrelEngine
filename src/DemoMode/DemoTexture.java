package DemoMode;

import org.lwjgl.util.vector.Vector2f;

public class DemoTexture 
{
	private int texture; 
	private Vector2f position; 
	private Vector2f scale;
	
	public DemoTexture(int tex, Vector2f position, Vector2f scale) 
	{
		this.texture = tex; 
		this.position = position;
		this.scale = scale;
	}
	
	public DemoTexture(Vector2f position, Vector2f scale) 
	{ 
		this.position = position;
		this.scale = scale;
	}
	
	public int getTexture() {return texture;}
	
	public void setTexture(int texture) {this.texture = texture;}

	public Vector2f getPosition() {return position;}

	public Vector2f getScale() {return scale;} 
}
