package Entities;

import org.lwjgl.util.vector.Vector3f;

public class Camera 
{
	private Vector3f position = new Vector3f(0,0,0);
	private float pitch; 
	private float roll;
	private float yaw;
	private float moveSpeed = 0.9f; 
	
	public Camera()
	{
		
	}

	public Vector3f getPosition() {return position;}
	public void setPosition(Vector3f position) {this.position = position;}

	public float getPitch() {return pitch;}
	public float getRoll() {return roll;}
	public float getYaw() {return yaw;}
	
	public void Move ()
	{
	
	}
}
