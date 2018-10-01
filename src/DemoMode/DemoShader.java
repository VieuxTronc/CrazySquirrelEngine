package DemoMode;

import org.lwjgl.util.vector.Matrix4f;
import RenderEngine.DisplayManager;
import Shaders.ShaderProgram;

public class DemoShader extends ShaderProgram
{
	//private static final String VERTEX_FILE = "src/DemoMode/DemoVertexShader.txt"; 
	private static final String FRAGMENT_FILE = "src/DemoMode/DemoFragmentShader.txt";
	private static final String DEFAULT_FRAGMENT_FILE = "src/DemoMode/DefaultDemoFragmentShader.txt";
	     
	private int location_transformationMatrix;
    private int location_time;
    private int location_screenSize; 
    
    public static String getDefaultFragmentFile() {return DEFAULT_FRAGMENT_FILE;}
    
    public DemoShader() 
    {
        super(FRAGMENT_FILE);
    }
     
    @Override
    protected void GetAllUniformLocations() 
    {
        location_transformationMatrix = super.GetUniformLocation("transformationMatrix");
    }
 
    @Override
    protected void BindAttributes() 
    {
        super.BindAttribute(0, "position");
        super.BindAttribute(1, "time");
        super.BindAttribute(2, "size");
    }
    
    public void LoadTime (long time)
    {
    	float _time = (float)time / 1000;
    	super.LoadFloat(location_time, _time);
    }
    
    public void loadTransformation(Matrix4f matrix)
    {
        super.LoadMatrix(location_transformationMatrix, matrix);
    }
    
    public void LoadScreenSize ()
    {
    	super.Load2DVector(location_screenSize, DisplayManager.GetDisplaySize());
    }
    
    public void UpdateShader ()
    {
    	super.RefreshShader(FRAGMENT_FILE);
    }
}
