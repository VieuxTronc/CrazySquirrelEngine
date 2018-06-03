package Entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;
import Debug.Debug;
import Models.TexturedModel;
import RenderEngine.DisplayManager;

public class Player extends Entity 
{
	private static final float RUN_SPEED = 30; 
	private static final float TURN_SPEED = 150;
	private static final float GRAVITY = - 70;
	private static final float JUMP_POWER = 30;
	
	private static final float TERRAIN_HEIGHT = 0;
	
	private float currentSpeed = 0;
	private float currentTurnSpeed = 0; 
	private float upwardsSpeed = 0; 
	
	private boolean isInAir = false; 
	
	public Player(TexturedModel _model, Vector3f _position, float _rotX, float _rotY, float _rotZ, float _scale) 
	{
		super(_model, _position, _rotX, _rotY, _rotZ, _scale);
	}
	
	public float getCurrentSpeed() {return currentSpeed;}
	
	public float getCurrentTurnSpeed() {return currentTurnSpeed;}
	
	public void Move ()
	{
		CheckInputs();
		super.IncreaseRotation(0, currentTurnSpeed * DisplayManager.GetFrameTimeSeconds(),  0);
		float distance = currentSpeed * DisplayManager.GetFrameTimeSeconds();
		float dx = (float) (distance * Math.sin(Math.toRadians(super.getRotY())));
		float dz = (float) (distance * Math.cos(Math.toRadians(super.getRotY())));
		super.IncreasePosition(dx, 0, dz);
		upwardsSpeed += GRAVITY * DisplayManager.GetFrameTimeSeconds();
		super.IncreasePosition(0, upwardsSpeed * DisplayManager.GetFrameTimeSeconds(), 0);
		if(super.getPosition().y < TERRAIN_HEIGHT)
		{
			upwardsSpeed = 0;
			isInAir = false;
			super.getPosition().y = TERRAIN_HEIGHT;
		}
	}
	
	private void CheckInputs ()
	{
		if(Keyboard.isKeyDown(Keyboard.KEY_Z))
		{
			this.currentSpeed = RUN_SPEED;
		}
		else if (Keyboard.isKeyDown(Keyboard.KEY_S)) 
		{
			this.currentSpeed = - RUN_SPEED;
		}
		else
		{	
			this.currentSpeed = 0;
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_D))
		{
			this.currentTurnSpeed = - TURN_SPEED;
		}
		else if (Keyboard.isKeyDown(Keyboard.KEY_Q)) 
		{
			this.currentTurnSpeed = TURN_SPEED;
		}
		else
		{	
			this.currentTurnSpeed = 0;
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_SPACE))
		{
			Jump();
		}
	}
	
	private void Jump ()
	{
		if(isInAir == false)
		{
			upwardsSpeed = JUMP_POWER;
			isInAir = true;
		}
	}

}
