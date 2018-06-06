package Core;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import Debug.Console;
import Debug.Debug;
import Entities.Camera;
import Entities.Entity;
import Entities.Player;
import Guis.GuiRenderer;
import Guis.GuiTexture;
import Models.Light;
import Models.RawModel;
import Models.TexturedModel;
import RenderEngine.DisplayManager;
import RenderEngine.Loader;
import RenderEngine.MasterRenderer;
import RenderEngine.ObjLoader;
import Scene.Scene;
import Terrains.Terrain;
import Textures.ModelTexture;
import Textures.TerrainTexture;
import Textures.TerrainTexturePack;

public class MainGameLoop 
{
	public static void main(String[] args) 
	{
		DisplayManager.CreateDisplay();
		
		ArrayList<Entity> entitiesList = new ArrayList<>();
		ArrayList<Terrain> terrainList = new ArrayList<>();
		
		//Debug
		Console console = new Console();
		
		//Editor window
		//new EditorWindow();
		
		Loader loader = new Loader(); 
		MasterRenderer renderer = new MasterRenderer();
		
		//Dragon
		RawModel dragonRawModel = ObjLoader.LoadObjModel("dragon", loader);
		TexturedModel dragonModel = new TexturedModel(dragonRawModel, new ModelTexture(loader.LoadTexture("dragonTexture"))); 
		
		//Grass
		TexturedModel grassModel = new TexturedModel(ObjLoader.LoadObjModel("grassModel", loader), new ModelTexture(loader.LoadTexture("grassTexture")));
		grassModel.getTexture().setHasTransparency(true);
		grassModel.getTexture().setUseFakeLightning(false);
		Entity grassEntity = new Entity(grassModel, new Vector3f(1, 1, -5), 0, 0, 0, 1);
		
		//Light
		Light sun = new Light(new Vector3f(0,100,0), new Vector3f(.4f,.4f,.4f));
		Light pointLight = new Light(new Vector3f(300,50,300), new Vector3f(0,10,0), new Vector3f(1, 0.01f, 0.002f));
		Light pointLight2 = new Light(new Vector3f(0,10,0), new Vector3f(10,10,0), new Vector3f(1, 0.02f, 0.005f));
		ArrayList<Light> lights = new ArrayList<>(); 
		lights.add(sun);
		lights.add(pointLight);
		lights.add(pointLight2);
		
		//Terrain
		TerrainTexture backgroundTexture = new TerrainTexture(loader.LoadTexture("grassy2"));
		TerrainTexture rTexture = new TerrainTexture(loader.LoadTexture("mud"));
		TerrainTexture gTexture = new TerrainTexture(loader.LoadTexture("grassFlowers"));
		TerrainTexture bTexture = new TerrainTexture(loader.LoadTexture("path"));
		
		TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture);
		TerrainTexture blendMap = new TerrainTexture(loader.LoadTexture("blendMap"));
		
		Terrain terrain = new Terrain(0, 0, loader, texturePack, blendMap, "heightmap");
		
		//Box 
		TexturedModel box = new TexturedModel(ObjLoader.LoadObjModel("box", loader), new ModelTexture(loader.LoadTexture("box")));
		Entity boxEntity = new Entity(box, new Vector3f(0, 50, 0), 0, 0, 0, 5);
		Entity boxEntity2 = new Entity(box, new Vector3f(300, 10, 300), 0, 0, 0, 5);
		
		//Fern
		ModelTexture fernModelTexture = new ModelTexture(loader.LoadTexture("fern"));
		fernModelTexture.setNumberOfRows(2);
		TexturedModel fern = new TexturedModel(ObjLoader.LoadObjModel("fern", loader), fernModelTexture);
		Entity fernEntity = new Entity(fern, 2, new Vector3f(60, 5, 65), 0, 0, 0, 1);
		
		//Player 
		Player player = new Player(dragonModel, new Vector3f(0, 2, -4), 0, 0, 0, 1);
		player.setPosition(new Vector3f(50, 5, 50));
		
		//Camera
		Camera camera = new Camera(player);
		
		//Scene
		entitiesList.add(grassEntity);
		terrainList.add(terrain);
		
		Scene scene_1 = new Scene("scene_1", entitiesList, lights, terrainList);
		Debug.DebugLog("SCENE - Name : " + scene_1.getSceneName().toString() + " // Created : " + scene_1.getCreation().format(DateTimeFormatter.BASIC_ISO_DATE).toString()); 
		
		//GUIS
		ArrayList<GuiTexture> guiList = new ArrayList<>();
		GuiTexture guiTexture = new GuiTexture(loader.LoadTexture("engine"), new Vector2f(0.5f, 0.5f), new Vector2f(0.25f, 0.25f));
		GuiTexture guiTexture2 = new GuiTexture(loader.LoadTexture("engine"), new Vector2f(0.4f, 0.4f), new Vector2f(0.25f, 0.25f));
		guiList.add(guiTexture);
		guiList.add(guiTexture2);
		GuiRenderer guiRenderer = new GuiRenderer(loader);
		
		while(!Display.isCloseRequested())
		{
			//game logic

			//rendering
			player.Move(terrain);
			camera.Move();
			
			renderer.ProcessEntity(boxEntity);
			renderer.ProcessEntity(boxEntity2);

			renderer.ProcessEntity(player);
			
			renderer.ProcessTerrain(terrain);
			
			renderer.ProcessEntity(grassEntity);
			renderer.ProcessEntity(fernEntity);
			
			renderer.Render(lights, camera);
			
			guiRenderer.Render(guiList);
			
			DisplayManager.UpdateDisplay();
		}
		
		guiRenderer.CleanUp();
		renderer.CleanUp();
		loader.CleanUp();
		console.CloseConsole();
		DisplayManager.CloseDisplay();
	}
}