package Core;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;
import Debug.Console;
import Editor.EditorWindow;
import Entities.*;
import Models.*;
import RenderEngine.*;
import Terrains.Terrain;
import Textures.*;

public class MainGameLoop 
{
	public static void main(String[] args) 
	{
		DisplayManager.CreateDisplay();
		
		//Debug
		new Console();
		
		//Editor window
		//new EditorWindow();
		
		Loader loader = new Loader(); 

		RawModel model = ObjLoader.LoadObjModel("dragon", loader); 
		TexturedModel staticModel = new TexturedModel(model, new ModelTexture(loader.LoadTexture("dragonTexture"))); 
		//ModelTexture texture = staticModel.getTexture();
		//texture.setShineDamper(10);
		//texture.setReflectivity(1);
		
		Entity entity = new Entity(staticModel, new Vector3f(0,-4,-25), 0, 0, 0, 1);
		Light light = new Light(new Vector3f(0,0,-20), new Vector3f(1,1,1));
		
		Terrain terrain = new Terrain(-1, -1, loader, new ModelTexture(loader.LoadTexture("ground")));
		Terrain terrain2 = new Terrain(-2, -2, loader, new ModelTexture(loader.LoadTexture("ground")));
		
		Camera camera = new Camera();
		
		MasterRenderer renderer = new MasterRenderer();
		
		while(!Display.isCloseRequested())
		{
			//game logic

			//rendering
			//entity.IncreasePosition(0, 0, 0);
			entity.IncreaseRotation(0, 0.2f, 0);
			
			camera.Move();
			//Debug.DebugLog(camera.getPosition().toString());
			
			renderer.ProcessTerrain(terrain);
			renderer.ProcessTerrain(terrain2);
			
			renderer.ProcessEntity(entity);
			renderer.Render(light, camera);
			DisplayManager.UpdateDisplay();
		}
		
		renderer.CleanUp();
		loader.CleanUp();
		DisplayManager.CloseDisplay();
	}

}