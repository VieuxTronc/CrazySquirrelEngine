package Entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

public class Camera 
{
	private Vector3f position = new Vector3f(0,0,0);
	private float pitch; 
	private float roll;
	private float yaw;
	private float moveSpeed = 0.07f; 
	
	public Camera()
	{
		
	}

	public Vector3f getPosition() {return position;}
	public float getPitch() {return pitch;}
	public float getRoll() {return roll;}
	public float getYaw() {return yaw;}
	
	public void Move ()
	{
		//Forwards
		if(Keyboard.isKeyDown(Keyboard.KEY_Z))
		{
			position.z -= moveSpeed; 
		}
		//Backwards
		if(Keyboard.isKeyDown(Keyboard.KEY_S))
		{
			position.z += moveSpeed; 
		}
		//Left 
		if(Keyboard.isKeyDown(Keyboard.KEY_Q))
		{
			position.x -= moveSpeed; 
		}
		//Right
		if(Keyboard.isKeyDown(Keyboard.KEY_D))
		{
			position.x += moveSpeed; 
		}
		//Up
		if(Keyboard.isKeyDown(Keyboard.KEY_E))
		{
			position.y += moveSpeed; 
		}
		//Down
		if(Keyboard.isKeyDown(Keyboard.KEY_A))
		{
			position.y -= moveSpeed; 
		}
		//Reset position
		if(Keyboard.isKeyDown(Keyboard.KEY_NUMPAD0))
		{
			position = new Vector3f(0,0,0);
		}
	}
}
