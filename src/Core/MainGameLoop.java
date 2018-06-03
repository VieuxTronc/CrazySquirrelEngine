package Core;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;
import Debug.Console;
import Debug.Debug;
import Entities.Camera;
import Entities.Entity;
import Entities.Player;
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
		ArrayList<Light> ligthList = new ArrayList<>();
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
		Light light = new Light(new Vector3f(0,0,-20), new Vector3f(1,1,1));
		
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
		Entity boxEntity = new Entity(box, new Vector3f(55, 5, 55), 0, 0, 0, 5);
		
		Entity boxEntity2 = new Entity(box, new Vector3f(55, 5, 80), 0, 0, 0, 5);
		
		//Player 
		Player player = new Player(dragonModel, new Vector3f(0, 2, -4), 0, 0, 0, 1);
		player.setPosition(new Vector3f(50, 5, 50));
		
		//Camera
		Camera camera = new Camera(player);
		
		//Scene
		entitiesList.add(grassEntity);
		ligthList.add(light);
		terrainList.add(terrain);
		
		Scene scene_1 = new Scene("scene_1", entitiesList, ligthList, terrainList);
		Debug.DebugLog("SCENE - Name : " + scene_1.getSceneName().toString() + " // Created : " + scene_1.getCreation().format(DateTimeFormatter.BASIC_ISO_DATE).toString()); 
		
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
			
			renderer.Render(light, camera);
			DisplayManager.UpdateDisplay();
		}
		
		renderer.CleanUp();
		loader.CleanUp();
		console.CloseConsole();
		DisplayManager.CloseDisplay();
	}
}