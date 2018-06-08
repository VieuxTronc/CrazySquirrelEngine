package Skybox;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;

import Debug.Debug;
import Entities.Camera;
import Models.RawModel;
import RenderEngine.DisplayManager;
import RenderEngine.Loader;

public class SkyboxRenderer 
{
	private static final float SIZE = 500f;
	private static final String[] TEXTURE_FILES = {"right", "left", "top", "bottom", "back", "front"};
	private static final String[] TEXTURE_FILES_2 = {"nightright", "nightleft", "nighttop", "nightbottom", "nightback", "nightfront"};
	private static final float[] VERTICES = 
	{
		-SIZE,  SIZE, -SIZE,
	    -SIZE, -SIZE, -SIZE,
	    SIZE, -SIZE, -SIZE,
	     SIZE, -SIZE, -SIZE,
	     SIZE,  SIZE, -SIZE,
	    -SIZE,  SIZE, -SIZE,

	    -SIZE, -SIZE,  SIZE,
	    -SIZE, -SIZE, -SIZE,
	    -SIZE,  SIZE, -SIZE,
	    -SIZE,  SIZE, -SIZE,
	    -SIZE,  SIZE,  SIZE,
	    -SIZE, -SIZE,  SIZE,

	     SIZE, -SIZE, -SIZE,
	     SIZE, -SIZE,  SIZE,
	     SIZE,  SIZE,  SIZE,
	     SIZE,  SIZE,  SIZE,
	     SIZE,  SIZE, -SIZE,
	     SIZE, -SIZE, -SIZE,

	    -SIZE, -SIZE,  SIZE,
	    -SIZE,  SIZE,  SIZE,
	     SIZE,  SIZE,  SIZE,
	     SIZE,  SIZE,  SIZE,
	     SIZE, -SIZE,  SIZE,
	    -SIZE, -SIZE,  SIZE,

	    -SIZE,  SIZE, -SIZE,
	     SIZE,  SIZE, -SIZE,
	     SIZE,  SIZE,  SIZE,
	     SIZE,  SIZE,  SIZE,
	    -SIZE,  SIZE,  SIZE,
	    -SIZE,  SIZE, -SIZE,

	    -SIZE, -SIZE, -SIZE,
	    -SIZE, -SIZE,  SIZE,
	     SIZE, -SIZE, -SIZE,
	     SIZE, -SIZE, -SIZE,
	    -SIZE, -SIZE,  SIZE,
	     SIZE, -SIZE,  SIZE	
	};
	
	private RawModel cube;
	private int dayTexture; 
	private int nightTexture;
	private SkyboxShader shader; 
	private float time = 0;
	
	public SkyboxRenderer(Loader loader, Matrix4f projectionMatrix)
	{
		cube = loader.loadToVAO(VERTICES, 3);
		dayTexture = loader.LoadCubeMap(TEXTURE_FILES);
		nightTexture = loader.LoadCubeMap(TEXTURE_FILES_2);
		shader = new SkyboxShader();
		shader.Start();
		shader.ConnectTextureUnits();
		shader.LoadProjectionMatrix(projectionMatrix);
		shader.Stop();
	}
	
	public void Render (Camera camera, float r, float g, float b)
	{
		shader.Start();
		shader.LoadViewMatrix(camera);
		shader.LoadFogColor(r, g, b);
		GL30.glBindVertexArray(cube.getVaoId());
		GL20.glEnableVertexAttribArray(0);
		BindTextures();
		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, cube.getVertexCount());
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
		shader.Stop();
	}
	
	private void BindTextures ()
	{
		time += DisplayManager.GetFrameTimeSeconds() * 1000;
		time %= 24000;
		Debug.DebugLog("" + time);
		int texture1;
		int texture2;
		float blendFactor;
		
		if(time >=0 && time < 5000)
		{
			texture1 = this.nightTexture; 
			texture2 = this.nightTexture;
			blendFactor = (time - 0) / (5000 - 0);
		}
		else if (time >= 5000 && time < 8000) 
		{
			texture1 = this.nightTexture; 
			texture2 = this.dayTexture;
			blendFactor = (time - 5000) / (8000 - 5000);
		}
		else if (time >= 8000 && time < 21000) 
		{
			texture1 = this.dayTexture; 
			texture2 = this.dayTexture;
			blendFactor = (time - 8000) / (21000 - 8000);
		}
		else  
		{
			texture1 = this.dayTexture; 
			texture2 = this.nightTexture;
			blendFactor = (time - 21000) / (24000 - 21000);
		}
		
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL13.GL_TEXTURE_CUBE_MAP, texture1);
		GL13.glActiveTexture(GL13.GL_TEXTURE1);
		GL11.glBindTexture(GL13.GL_TEXTURE_CUBE_MAP, texture2);
		shader.LoadBlendFactor(blendFactor);
	}
}
