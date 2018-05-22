package RenderEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import Entities.*;
import Models.Light;
import Models.TexturedModel;
import Shaders.StaticShader;

public class MasterRenderer 
{
	private StaticShader shader = new StaticShader(); 
	private Renderer renderer = new Renderer(shader); 
	
	private Map<TexturedModel, ArrayList<Entity>> entities = new HashMap<TexturedModel, ArrayList<Entity>>(); 
	
	public void Render (Light sun, Camera cam)
	{
		renderer.Prepare();
		shader.Start();
		shader.LoadLight(sun);
		shader.LoadViewMatrix(cam);
		renderer.Render(entities);
		shader.Stop();
		entities.clear();
	}	
	
	public void CleanUp ()
	{
		shader.CleanUp();
	}
	
	public void ProcessEntity (Entity entity)
	{
		TexturedModel entityModel = entity.getModel();
		ArrayList<Entity> batch = entities.get(entityModel); 
		
		if(batch != null)
		{
			batch.add(entity);
		}
		else 
		{
			ArrayList<Entity> newBatch = new ArrayList<Entity>();
			newBatch.add(entity); 
			entities.put(entityModel, newBatch);
		}
	}
}
