package Shaders;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import Debug.Debug;
import DemoMode.DemoShader;

public abstract class ShaderProgram 
{
	private int programID; 
	private int vertexShaderID; 
	private int fragmentShaderID; 
	private static FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);
	
	public ShaderProgram (String vertexFile, String fragmentFile)
	{
		vertexShaderID = LoadShader(vertexFile, GL20.GL_VERTEX_SHADER);
		fragmentShaderID = LoadShader(fragmentFile, GL20.GL_FRAGMENT_SHADER);
		
		programID = GL20.glCreateProgram(); 
		GL20.glAttachShader(programID, vertexShaderID);
		GL20.glAttachShader(programID, fragmentShaderID);
		BindAttributes();
		GL20.glLinkProgram(programID);
		GL20.glValidateProgram(programID); 
		GetAllUniformLocations();
	}
	
	public ShaderProgram (String fragmentFile)
	{
		fragmentShaderID = LoadShader(fragmentFile, GL20.GL_FRAGMENT_SHADER);
		
		programID = GL20.glCreateProgram(); 
		GL20.glAttachShader(programID, vertexShaderID);
		GL20.glAttachShader(programID, fragmentShaderID);
		BindAttributes();
		GL20.glLinkProgram(programID);
		GL20.glValidateProgram(programID); 
		GetAllUniformLocations();
	}
	
	protected abstract void GetAllUniformLocations();
	
	protected int GetUniformLocation (String uniformName)
	{
		return GL20.glGetUniformLocation(programID, uniformName);
	}
	 
	public void Start ()
	{
		GL20.glUseProgram(programID);
	}
	
	public void Stop ()
	{
		GL20.glUseProgram(0);
	}
	
	public void CleanUp()
	{
		Stop(); 
		GL20.glDetachShader(programID, vertexShaderID);
		GL20.glDetachShader(programID, fragmentShaderID);
		GL20.glDeleteShader(vertexShaderID);
		GL20.glDeleteShader(fragmentShaderID);
		GL20.glDeleteProgram(programID);
	} 
	
	protected abstract void BindAttributes (); 
	
	protected void BindAttribute (int attribute, String variableName)
	{
		GL20.glBindAttribLocation(programID, attribute, variableName);
	}
	
	protected void LoadInt(int location, int value) 
	{
		GL20.glUniform1i(location, value);
	}
	
	protected void LoadFloat(int location, float value) 
	{
		GL20.glUniform1f(location, value);
	}
	
	protected void LoadVector(int location, Vector3f vector) 
	{
		GL20.glUniform3f(location, vector.x, vector.y, vector.z);
	}
	
	protected void LoadBoolean(int location, Boolean value) 
	{
		float toLoad = 0;
		
		if(value)
		{
			toLoad = 1;
		}
		
		GL20.glUniform1f(location, toLoad);
	}
	
	protected void Load2DVector(int location, Vector2f vector) 
	{
		GL20.glUniform2f(location, vector.x, vector.y);
	}
	
	protected void LoadMatrix(int location, Matrix4f matrix) 
	{
		matrix.store(matrixBuffer);
		matrixBuffer.flip();
		GL20.glUniformMatrix4(location, false, matrixBuffer);
	}
	
	private static int LoadShader (String file, int type)
	{
		StringBuilder shaderSource = new StringBuilder(); 
		
		try
		{
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line;
			
			while((line = reader.readLine()) != null)
			{
				shaderSource.append(line).append("\n");
			}
			
			reader.close();
		}
		catch (IOException e) 
		{
			System.err.println("Could not read file");
			e.printStackTrace();
			return 0; 
			//System.exit(-1);
		}
		
		int shaderID = GL20.glCreateShader(type);
		GL20.glShaderSource(shaderID, shaderSource);
		GL20.glCompileShader(shaderID);
		
		if(GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE)
		{
			LoadShader(DemoShader.getDefaultFragmentFile(), GL20.GL_FRAGMENT_SHADER);
			Debug.DebugLog(GL20.glGetShaderInfoLog(shaderID, 500));
			Debug.DebugLog("Could not compile shader");
		}
				
		return shaderID; 
	}
	
	public void RefreshShader (String file)
	{
		CleanUp();
		
		//vertexShaderID = LoadShader(file2, GL20.GL_VERTEX_SHADER);
		fragmentShaderID = LoadShader(file, GL20.GL_FRAGMENT_SHADER);
		
		programID = GL20.glCreateProgram(); 
		//GL20.glAttachShader(programID, vertexShaderID);
		GL20.glAttachShader(programID, fragmentShaderID);
		BindAttributes();
		GL20.glLinkProgram(programID);
		GL20.glValidateProgram(programID); 
		GetAllUniformLocations();
	}
}
