package Skybox;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import Entities.Camera;
import RenderEngine.DisplayManager;
import Shaders.ShaderProgram;
import Utils.Maths;

public class SkyboxShader extends ShaderProgram 
{
	private static final String VERTEX_FILE = "src/skybox/SkyboxVertexShader.txt";
    private static final String FRAGMENT_FILE = "src/skybox/SkyboxFragmentShader.txt";
    
    private static final float 	ROTATION_SPEED = 1f;
     
    private int location_projectionMatrix;
    private int location_viewMatrix;
    private int location_fogColor; 
    private int location_cubeMap; 
    private int location_cubeMap2; 
    private int location_blendFactor;
    
    private float rotation = 0;
     
    public SkyboxShader() 
    {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }
     
    public void LoadProjectionMatrix(Matrix4f matrix)
    {
        super.LoadMatrix(location_projectionMatrix, matrix);
    }
 
    public void LoadViewMatrix(Camera camera)
    {
        Matrix4f matrix = Maths.CreateViewMatrix(camera);
        matrix.m30 = 0;
        matrix.m31 = 0;
        matrix.m32 = 0;
        rotation += ROTATION_SPEED * DisplayManager.GetFrameTimeSeconds();
        Matrix4f.rotate((float) Math.toRadians(rotation), new Vector3f(0, 1, 0), matrix, matrix);
        super.LoadMatrix(location_viewMatrix, matrix);
    }
     
    @Override
    protected void GetAllUniformLocations() 
    {
        location_projectionMatrix = super.GetUniformLocation("projectionMatrix");
        location_viewMatrix = super.GetUniformLocation("viewMatrix");
        location_fogColor = super.GetUniformLocation("fogColor");
        location_blendFactor = super.GetUniformLocation("blendFactor");
        location_cubeMap = super.GetUniformLocation("cubeMap");
        location_cubeMap2 = super.GetUniformLocation("cubeMap2");
    }
 
    @Override
    protected void BindAttributes() 
    {
        super.BindAttribute(0, "position");
    }
    
    public void LoadFogColor (float r, float g, float b)
    {
    	super.LoadVector(location_fogColor, new Vector3f(r, g, b));
    }
    
    public void LoadBlendFactor (float factor)
    {
    	super.LoadFloat(location_blendFactor, factor);
    }
    
    public void ConnectTextureUnits ()
    {
    	super.LoadInt(location_cubeMap, 0);
    	super.LoadInt(location_cubeMap2, 1);
    }
}
