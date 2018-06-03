package Entities;

import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;
import Utils.Maths;

public class Camera 
{
	private Vector3f position = new Vector3f(0,0,0);
	private float pitch; // 2.5 to 80
	private float roll;
	private float yaw;
	
	//private float moveSpeed = 0.9f;
	private Player player; 
	private float distanceFromPlayer = 50; //26 to 250
	private float minDistToPlayer = 26;
	private float maxDistToPlayer = 250;
	private float minPitch = 2.5f;
	private float maxPitch = 80; 
	private float angleAroundPlayer = 0; 
	
	public Camera(Player _player)
	{
		this.player = _player; 
		InitializeCameraRotation();
	}

	public Vector3f getPosition() {return position;}
	public void setPosition(Vector3f position) {this.position = position;}

	public float getPitch() {return pitch;}
	public float getRoll() {return roll;}
	public float getYaw() {return yaw;}
	
	public void Move ()
	{
		CalculateZoom();
		CalculatePitch();
		CalculateAngleAroundPlayer();
		float horizontalDistance = CalculateHorizontalDistance();
		float verticlalDistance = CalculateVerticalDistance();
		CalculateCameraPostition(horizontalDistance, verticlalDistance);
		this.yaw = 180 - (player.getRotY() + angleAroundPlayer);
	}
	
	private void InitializeCameraRotation ()
	{
		this.pitch = minPitch; 
	}
	
	private void CalculateCameraPostition (float horizontalDist, float verticalDist)
	{
		float theta = player.getRotY() + angleAroundPlayer;
		float offSetX = (float) (horizontalDist * Math.sin(Math.toRadians(theta)));
		float offSetZ = (float) (horizontalDist * Math.cos(Math.toRadians(theta)));
		position.x = player.getPosition().x - offSetX;
		position.y = player.getPosition().y + verticalDist;
		position.z = player.getPosition().z - offSetZ;
	}
	
	private float CalculateHorizontalDistance()
	{
		return (float) (distanceFromPlayer * Math.cos(Math.toRadians(pitch)));
	}
	
	private float CalculateVerticalDistance()
	{
		return (float) (distanceFromPlayer * Math.sin(Math.toRadians(pitch)));
	}
	
	private void CalculateZoom()
	{
		float zoomLevel = Mouse.getDWheel() * 0.1f;
		distanceFromPlayer -= zoomLevel;
		distanceFromPlayer = Maths.Clamp(distanceFromPlayer, minDistToPlayer, maxDistToPlayer);
	}
	
	private void CalculatePitch ()
	{
		if(Mouse.isButtonDown(1))
		{
			float pitchChange = Mouse.getDY() * 0.1f;
			pitch -= pitchChange;
			pitch = Maths.Clamp(pitch, minPitch, maxPitch);
		}
	}
	
	private void CalculateAngleAroundPlayer ()
	{
		if(Mouse.isButtonDown(0))
		{
			float angleChange = Mouse.getDX() * 0.3f;
			angleAroundPlayer -= angleChange;
		}
	}
}
