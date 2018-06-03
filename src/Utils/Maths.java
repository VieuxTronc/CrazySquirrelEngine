package Utils;

import java.util.Vector;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import Entities.Camera;

public class Maths 
{
	public static Matrix4f CreateTransformMatrix (Vector3f translation, float rx, float ry, float rz, float scale)
	{
		Matrix4f matrix4f = new Matrix4f(); 
		matrix4f.setIdentity(); 
		Matrix4f.translate(translation, matrix4f, matrix4f); 
		Matrix4f.rotate((float) Math.toRadians(rx), new Vector3f(1,0,0), matrix4f, matrix4f);
		Matrix4f.rotate((float) Math.toRadians(ry), new Vector3f(0,1,0), matrix4f, matrix4f);
		Matrix4f.rotate((float) Math.toRadians(rz), new Vector3f(0,0,1), matrix4f, matrix4f);
		Matrix4f.scale(new Vector3f(scale, scale, scale), matrix4f, matrix4f);
		return matrix4f;
	}
	
	public static Matrix4f CreateViewMatrix (Camera cam)
	{
		Matrix4f viewMatrix = new Matrix4f(); 
		viewMatrix.setIdentity(); 
		Matrix4f.rotate((float) Math.toRadians(cam.getPitch()), new Vector3f(1,0,0), viewMatrix, viewMatrix);
		Matrix4f.rotate((float) Math.toRadians(cam.getYaw()), new Vector3f(0,1,0), viewMatrix, viewMatrix);
		Matrix4f.rotate((float) Math.toRadians(cam.getRoll()), new Vector3f(0,0,1), viewMatrix, viewMatrix);
		Vector3f cameraPos = cam.getPosition(); 
		Vector3f negativeCameraPos = new Vector3f(-cameraPos.x, -cameraPos.y, -cameraPos.z);
		Matrix4f.translate(negativeCameraPos, viewMatrix, viewMatrix); 
		return viewMatrix; 
	}
	
	public static float Clamp (float value, float min, float max)
	{
		if(value < min)
		{
			return min; 
		}
		else if (value > max)
		{
			return max; 
		}
		else 
		{
			return value;
		}
	}
	
	//Return the height of a point in a triangle at the player's position
	//I need to refresh my mind about barycentric ...
	public static float BarryCentric(Vector3f p1, Vector3f p2, Vector3f p3, Vector2f pos) 
	{
		float det = (p2.z - p3.z) * (p1.x - p3.x) + (p3.x - p2.x) * (p1.z - p3.z);
		float l1 = ((p2.z - p3.z) * (pos.x - p3.x) + (p3.x - p2.x) * (pos.y - p3.z)) / det;
		float l2 = ((p3.z - p1.z) * (pos.x - p3.x) + (p1.x - p3.x) * (pos.y - p3.z)) / det;
		float l3 = 1.0f - l1 - l2;
		return l1 * p1.y + l2 * p2.y + l3 * p3.y;
	}
}

