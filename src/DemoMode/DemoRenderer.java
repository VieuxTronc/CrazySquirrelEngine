package DemoMode;

import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL44;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import Debug.Debug;
import Debug.DebugInputs;
import Models.RawModel;
import RenderEngine.Loader;
import Terrains.Terrain;
import Utils.Maths;

public class DemoRenderer 
{
	private final RawModel quad;
	private DemoShader shader; 
	
	public DemoRenderer (Loader loader)
	{
		float [] positions = { -1, 1, -1, -1, 1, 1, 1, -1};
		quad = loader.loadToVAO(positions, 2);
		shader = new DemoShader(); 
	}
	
	public void Render (DemoTexture demo)
	{
		if(DebugInputs.isDemoMode)
		{
			shader.Start();
			GL30.glBindVertexArray(quad.getVaoId());
			GL20.glEnableVertexAttribArray(0);
			GL20.glEnableVertexAttribArray(1);
			
			//Gui texture transparency
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			GL11.glDisable(GL11.GL_DEPTH_TEST);
			
			GL13.glActiveTexture(GL13.GL_TEXTURE0);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, demo.getTexture());
			Matrix4f matrix = Maths.CreateTransformationMatrix(demo.getPosition(), demo.getScale());
			shader.loadTransformation(matrix);
			shader.LoadTime(Sys.getTime());
			shader.LoadScreenSize();
			GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, quad.getVertexCount());
			
			GL11.glEnable(GL11.GL_DEPTH_TEST);
			GL20.glDisableVertexAttribArray(0);
			GL20.glDisableVertexAttribArray(1);
			GL30.glBindVertexArray(0);
			shader.Stop();	

		    if (Keyboard.getEventKeyState()) 
		    {
		        if (Keyboard.getEventKey() == Keyboard.KEY_F5) 
		        {
		        	Debug.DebugLog("UPDATED SHADER");
		        	UpdateShader();
		        }
		    }
		}
	}
	
	public void CleanUp ()
	{
		shader.CleanUp();
	}
	
	public void UpdateShader ()
	{
		shader.UpdateShader();
	}
}
