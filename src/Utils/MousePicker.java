package Utils;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;
import Entities.Camera;
import Terrains.Terrain;

public class MousePicker 
{
	private static final int RECURSION_COUNT = 200;
	private static final float RAY_RANGE = 600;
	
	private Vector3f currentRay; 
	
	private Matrix4f projectionMatrix; 
	private Matrix4f viewMatrix; 
	private Camera camera;
	
	private Terrain terrain;
	private Vector3f currentTerrainPoint;
	
	public MousePicker(Matrix4f projectionMatrix, Camera camera, Terrain terrain) 
	{
		this.projectionMatrix = projectionMatrix;
		this.camera = camera;
		viewMatrix = Maths.CreateViewMatrix(camera);
		this.terrain = terrain;
	} 
	
	public Vector3f GetCurrentTerrainPoint(){return currentTerrainPoint;}
	
	public Vector3f getCurrentRay(){return currentRay;}
	
	public void Update ()
	{
		viewMatrix = Maths.CreateViewMatrix(camera);
		currentRay = CalculateCurrentRay();
		
		if (intersectionInRange(0, RAY_RANGE, currentRay)) 
		{
			currentTerrainPoint = BinarySearch(0, 0, RAY_RANGE, currentRay);
		} 
		else 
		{
			currentTerrainPoint = null;
		}
	}
	
	private Vector3f CalculateCurrentRay()
	{
		float mouseX = Mouse.getX();
		float mouseY = Mouse.getY();
		Vector2f normalizedCoords = GetNormalizedDeviceCoordinates(mouseX, mouseY);
		Vector4f clipCoords = new Vector4f(normalizedCoords.x, normalizedCoords.y, -1, 1);
		Vector4f eyeCoords = ToEyeCoordinates(clipCoords);
		Vector3f worldRay = ToWorldCoordinates(eyeCoords);
		return worldRay;
	}
	
	private Vector3f ToWorldCoordinates (Vector4f eyecoords)
	{
		Matrix4f invertedView = Matrix4f.invert(viewMatrix, null);
		Vector4f rayWorld = Matrix4f.transform(invertedView, eyecoords, null);
		Vector3f mouseRay = new Vector3f(rayWorld.x, rayWorld.y, rayWorld.z);
		mouseRay.normalise();
		return mouseRay;
	}
	
	private Vector4f ToEyeCoordinates (Vector4f clipCoords)
	{
		Matrix4f invertedProjection = Matrix4f.invert(projectionMatrix, null);
		Vector4f eyeCoords = Matrix4f.transform(invertedProjection, clipCoords, null);
		return new Vector4f(eyeCoords.x, eyeCoords.y, -1f, 0f);
	}
	
	private Vector2f GetNormalizedDeviceCoordinates (float mouseX, float mouseY)
	{
		float x = (2f * mouseX) / Display.getWidth() - 1;
		float y = (2f * mouseY) / Display.getHeight() - 1;
		return new Vector2f(x, y);
	}
	
	//*****************************************************
	
	private Vector3f GetPointOnRay(Vector3f ray, float distance) 
	{
		Vector3f camPos = camera.getPosition();
		Vector3f start = new Vector3f(camPos.x, camPos.y, camPos.z);
		Vector3f scaledRay = new Vector3f(ray.x * distance, ray.y * distance, ray.z * distance);
		return Vector3f.add(start, scaledRay, null);
	}
	
	private Vector3f BinarySearch(int count, float start, float finish, Vector3f ray) 
	{
		float half = start + ((finish - start) / 2f);
		
		if (count >= RECURSION_COUNT) 
		{
			Vector3f endPoint = GetPointOnRay(ray, half);
			Terrain terrain = GetTerrain(endPoint.getX(), endPoint.getZ());
			
			if (terrain != null) 
			{
				return endPoint;
			} 
			else 
			{
				return null;
			}
		}
		if (intersectionInRange(start, half, ray)) 
		{
			return BinarySearch(count + 1, start, half, ray);
		} 
		else 
		{
			return BinarySearch(count + 1, half, finish, ray);
		}
	}

	private boolean intersectionInRange(float start, float finish, Vector3f ray) 
	{
		Vector3f startPoint = GetPointOnRay(ray, start);
		Vector3f endPoint = GetPointOnRay(ray, finish);
		
		if (!isUnderGround(startPoint) && isUnderGround(endPoint)) 
		{
			return true;
		} 
		else 
		{
			return false;
		}
	}

	private boolean isUnderGround(Vector3f testPoint) 
	{
		Terrain terrain = GetTerrain(testPoint.getX(), testPoint.getZ());
		float height = 0;
		
		if (terrain != null) 
		{
			height = terrain.GetHeightOfTerrain(testPoint.getX(), testPoint.getZ());
		}
		if (testPoint.y < height) 
		{
			return true;
		} 
		else 
		{
			return false;
		}
	}

	private Terrain GetTerrain(float worldX, float worldZ) 
	{
		return terrain;
	}
}
