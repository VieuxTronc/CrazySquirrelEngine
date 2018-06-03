package Shaders;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import Entities.Camera;
import Models.Light;
import Utils.Maths;

public class StaticShader extends ShaderProgram
{
	private static final String VERTEX_FILE = "src/Shaders/VertexShader.txt"; 
	private static final String FRAGMENT_FILE = "src/Shaders/FragmentShader.txt";
	
	private int location_transformationMatrix; 
	private int location_projectionMatrix; 
	private int location_viewMatrix;
	
	private int location_lightPosition;
	private int location_lightColor;
	
	private int location_shineDamper;
	private int location_reflectivity;
	
	private int location_fakeLighting;
	
	private int location_skyColor;
	
	private int location_numberOfRows; 
	private int location_offset;
	
	public StaticShader ()
	{
		super(VERTEX_FILE, FRAGMENT_FILE); 
	} 
	
	@Override
	protected void BindAttributes() 
	{
		super.BindAttribute(0, "position");
		super.BindAttribute(1, "textureCoords");
		super.BindAttribute(2, "normal");
	}
	
	@Override
	protected void GetAllUniformLocations ()
	{
		location_transformationMatrix = super.GetUniformLocation("transformationMatrix");
		location_projectionMatrix = super.GetUniformLocation("projectionMatrix");
		location_viewMatrix = super.GetUniformLocation("viewMatrix");
		location_lightPosition = super.GetUniformLocation("lightPosition");
		location_lightColor = super.GetUniformLocation("lightColor");
		location_shineDamper = super.GetUniformLocation("shineDamper");
		location_reflectivity = super.GetUniformLocation("reflectivity"); 
		location_fakeLighting = super.GetUniformLocation("useFakeLightning");
		location_skyColor = super.GetUniformLocation("skyColor");
		location_numberOfRows = super.GetUniformLocation("numberOfRows");
		location_offset = super.GetUniformLocation("Offset");
	}
	
	public void LoadTransformationMatrix(Matrix4f mat)
	{
		super.LoadMatrix(location_transformationMatrix, mat);
	}
	
	public void LoadProjectionMatrix(Matrix4f projection)
	{
		super.LoadMatrix(location_projectionMatrix, projection);
	}
	
	public void LoadViewMatrix (Camera cam)
	{
		Matrix4f viewMatrix = Maths.CreateViewMatrix(cam);
		super.LoadMatrix(location_viewMatrix, viewMatrix);
	}
	
	public void LoadLight (Light light)
	{
		super.LoadVector(location_lightPosition, light.getPosition());
		super.LoadVector(location_lightColor, light.getColor());
	}
	
	public void LoadShineVariables (float damper, float reflectivity)
	{
		super.LoadFloat(location_shineDamper, damper);
		super.LoadFloat(location_reflectivity, reflectivity);
	}
	
	public void LoadFakeLightning (boolean useFakeLightning)
	{
		super.LoadBoolean(location_fakeLighting, useFakeLightning);
	}
	
	public void LoadSkyColor (float r, float g, float b)
	{
		super.LoadVector(location_skyColor, new Vector3f(r,g,b));
	}
	
	public void LoadNumberOfRows (int rows)
	{
		super.LoadFloat(location_numberOfRows, rows);
	}
	
	public void LoadOffset (float x, float y)
	{
		super.Load2DVector(location_offset, new Vector2f(x, y));
	}
}
