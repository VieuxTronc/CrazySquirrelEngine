package RenderEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;

import Entities.Camera;
import Entities.Entity;
import Models.Light;
import Models.TexturedModel;
import Shaders.StaticShader;
import Shaders.TerrainShader;
import Skybox.SkyboxRenderer;
import Terrains.Terrain;

public class MasterRenderer 
{
	private static final float FOV = 70;
	private static final float NEAR_PLANE = 0.1f;
	private static final float FAR_PLANE = 1000;
	
	private static final float RED = 0.5f;
	private static final float GREEN = 0.5f;
	private static final float BLUE = 0.5f;
	
	private Matrix4f projectionMatrix; 
	
	private StaticShader shader = new StaticShader(); 
	private EntityRenderer renderer; 
	
	private Map<TexturedModel, ArrayList<Entity>> entities = new HashMap<TexturedModel, ArrayList<Entity>>();
	private ArrayList<Terrain> terrains = new ArrayList<Terrain>();
	
	private TerrainRenderer terrainRenderer;
	private TerrainShader terrainShader = new TerrainShader();
	
	private SkyboxRenderer skyboxRenderer;
	
	public MasterRenderer (Loader loader)
	{
		EnableCulling();
		CreateProjectionMatrix();
		renderer = new EntityRenderer(shader, projectionMatrix);
		terrainRenderer = new TerrainRenderer(terrainShader, projectionMatrix);
		skyboxRenderer = new SkyboxRenderer(loader, projectionMatrix);
	}
	
	public static void EnableCulling ()
	{
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glCullFace(GL11.GL_BACK);
	}
	
	public static void DisableCulling ()
	{
		GL11.glDisable(GL11.GL_CULL_FACE);
	}
	
	public void Render (ArrayList<Light> lights, Camera cam)
	{
		Prepare();
		
		//Entity rendering 
		shader.Start();
		shader.LoadSkyColor(RED, GREEN, BLUE);
		shader.LoadLights(lights);
		shader.LoadViewMatrix(cam);
		renderer.Render(entities);
		shader.Stop();
		
		//Terrain rendering 
		terrainShader.Start();
		terrainShader.LoadSkyColor(RED, GREEN, BLUE);
		terrainShader.LoadLights(lights);
		terrainShader.LoadViewMatrix(cam);
		terrainRenderer.Render(terrains);
		terrainShader.Stop();
		
		skyboxRenderer.Render(cam, RED, GREEN, BLUE);
		
		terrains.clear();
		entities.clear();
	}	
	
	public void ProcessTerrain (Terrain terrain)
	{
		terrains.add(terrain);
	}
	
	public void CleanUp ()
	{
		shader.CleanUp();
		terrainShader.CleanUp();
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
	
	public void Prepare ()
	{
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glClearColor(RED, GREEN, BLUE, 1);
	}
	
	private void CreateProjectionMatrix ()
	{
		float aspectRatio = (float) Display.getWidth() / (float) Display.getHeight();
		float y_scale = (float) ((1f / Math.tan(Math.toRadians(FOV / 2F))) * aspectRatio);
		float x_scale = y_scale / aspectRatio; 
		float frustum_length = FAR_PLANE - NEAR_PLANE;
		
		projectionMatrix = new Matrix4f(); 
		projectionMatrix.m00 = x_scale; 
		projectionMatrix.m11 = y_scale;
		projectionMatrix.m22 = - ((FAR_PLANE + NEAR_PLANE) / frustum_length);
		projectionMatrix.m23 = -1; 
		projectionMatrix.m32 = - ((2 * NEAR_PLANE * FAR_PLANE) / frustum_length);
		projectionMatrix.m33 = 0;
	}
}
