package Skybox;

import java.nio.file.attribute.FileOwnerAttributeView;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import Entities.Camera;
import Shaders.ShaderProgram;
import Utils.Maths;

public class SkyboxShader extends ShaderProgram 
{
	private static final String VERTEX_FILE = "src/skybox/SkyboxVertexShader.txt";
    private static final String FRAGMENT_FILE = "src/skybox/SkyboxFragmentShader.txt";
     
    private int location_projectionMatrix;
    private int location_viewMatrix;
    private int location_fogColor; 
     
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
        super.LoadMatrix(location_viewMatrix, matrix);
    }
     
    @Override
    protected void GetAllUniformLocations() 
    {
        location_projectionMatrix = super.GetUniformLocation("projectionMatrix");
        location_viewMatrix = super.GetUniformLocation("viewMatrix");
        location_fogColor = super.GetUniformLocation("fogColor");
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
}
