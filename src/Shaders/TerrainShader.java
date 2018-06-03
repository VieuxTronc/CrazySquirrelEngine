package Shaders;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import Entities.Camera;
import Models.Light;
import Utils.Maths;

public class TerrainShader extends ShaderProgram 
{
	private static final String VERTEX_FILE = "src/Shaders/TerrainVertexShader.txt"; 
	private static final String FRAGMENT_FILE = "src/Shaders/TerrainFragmentShader.txt";
	
	private int location_transformationMatrix; 
	private int location_projectionMatrix; 
	private int location_viewMatrix;
	
	private int location_lightPosition;
	private int location_lightColor;
	
	private int location_shineDamper;
	private int location_reflectivity;
	
	private int location_skyColor; 
	
	private int location_backgroundTexture; 
	private int location_rTexture; 
	private int location_gTexture; 
	private int location_bTexture; 
	private int location_blendMap;  
	
	public TerrainShader ()
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
		location_skyColor = super.GetUniformLocation("skyColor");
		location_backgroundTexture = super.GetUniformLocation("");
		location_rTexture = super.GetUniformLocation("rTexture");
		location_gTexture = super.GetUniformLocation("gTexture");
		location_bTexture = super.GetUniformLocation("bTexture");
		location_blendMap = super.GetUniformLocation("blendMap");
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
	
	public void LoadSkyColor (float r, float g, float b)
	{
		super.LoadVector(location_skyColor, new Vector3f(r,g,b));
	}
	
	public void ConnectTextureUnits ()
	{
		super.LoadInt(location_backgroundTexture, 0);
		super.LoadInt(location_rTexture, 1);
		super.LoadInt(location_gTexture, 2);
		super.LoadInt(location_bTexture, 3);
		super.LoadInt(location_blendMap, 4);
	}
}
