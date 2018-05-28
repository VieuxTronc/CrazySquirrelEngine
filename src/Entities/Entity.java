package Entities;

import org.lwjgl.util.vector.Vector3f;

import Models.TexturedModel;

public class Entity 
{
	private TexturedModel model; 
	private Vector3f position; 
	private float rotX, rotY, rotZ; 
	private float scale;
	
	public Entity (TexturedModel _model, Vector3f _position, float _rotX, float _rotY, float _rotZ, float _scale)
	{
		model = _model; 
		position = _position;
		rotX = _rotX;
		rotY = _rotY;
		rotZ = _rotZ;
		scale = _scale;
	}

	public TexturedModel getModel() {return model;}
	public void setModel(TexturedModel model) {this.model = model;}

	public Vector3f getPosition() {return position;}
	public void setPosition(Vector3f position) {this.position = position;}

	public float getRotX() {return rotX;}
	public void setRotX(float rotX) {this.rotX = rotX;}

	public float getRotY() {return rotY;}
	public void setRotY(float rotY) {this.rotY = rotY;}

	public float getRotZ() {return rotZ;}
	public void setRotZ(float rotZ) {this.rotZ = rotZ;}

	public float getScale() {return scale;}
	public void setScale(float scale) {this.scale = scale;}
	
	public void IncreasePosition (float dx, float dy, float dz)
	{
		position.x += dx;
		position.y += dy;
		position.z += dz;
	}
	
	public void IncreaseRotation (float rx, float ry, float rz)
	{
		rotX += rx;
		rotY += ry;
		rotZ += rz;
	}
}
