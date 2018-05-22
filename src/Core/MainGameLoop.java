package Core;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import Debug.Console;
import Debug.Debug;
import Entities.*;
import Models.*;
import RenderEngine.*;
import Textures.*;

public class MainGameLoop 
{
	public static void main(String[] args) 
	{
		DisplayManager.CreateDisplay();
		
		//Debug
		Console console = new Console();
		Debug.DebugLog("Log");
		Debug.WarningLog("Warning log");
		
		Loader loader = new Loader(); 

		RawModel model = ObjLoader.LoadObjModel("dragon", loader); 
		TexturedModel staticModel = new TexturedModel(model, new ModelTexture(loader.LoadTexture("dragonTexture"))); 
		//ModelTexture texture = staticModel.getTexture();
		//texture.setShineDamper(10);
		//texture.setReflectivity(1);
		
		Entity entity = new Entity(staticModel, new Vector3f(0,-4,-25), 0, 0, 0, 1);
		Light light = new Light(new Vector3f(0,0,-20), new Vector3f(1,1,1));
		
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
			renderer.ProcessEntity(entity);
			renderer.Render(light, camera);
			DisplayManager.UpdateDisplay();
		}
		
		renderer.CleanUp();
		loader.CleanUp();
		console.CloseConsole();
		DisplayManager.CloseDisplay();
	}

}