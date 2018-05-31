package Scene;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import Entities.Entity;
import Models.Light;
import Terrains.Terrain;

public class Scene implements Serializable
{
	private static final long serialVersionUID = 1L;
	private String sceneName; 
	private LocalDateTime creation; 
	private LocalDateTime lastSave; 
	private ArrayList<Entity> entityList;
	private ArrayList<Light> lightList;
	private ArrayList<Terrain> terrainList;
	
	public Scene (String _name, ArrayList<Entity> _entityList, ArrayList<Light> _lightList, ArrayList<Terrain> _terrainList)
	{
		sceneName = _name;
		creation = LocalDateTime.now();
		setEntityList(new ArrayList<>(_entityList));
		setLightList(new ArrayList<>(_lightList));
		setTerrainList(new ArrayList<>(_terrainList));
	}

	public String getSceneName() {return sceneName;}
	public void setSceneName(String sceneName) {this.sceneName = sceneName;}

	public LocalDateTime getLastSave() {return lastSave;}
	public void setLastSave(LocalDateTime lastSave) {this.lastSave = lastSave;}

	public LocalDateTime getCreation() {return creation;}
	
	public ArrayList<Entity> getEntityList() {return entityList;}
	public void setEntityList(ArrayList<Entity> entityList) {this.entityList = entityList;}

	public ArrayList<Light> getLightList() {return lightList;}
	public void setLightList(ArrayList<Light> lightList) {this.lightList = lightList;}
	
	public ArrayList<Terrain> getTerrainList() {return terrainList;}
	public void setTerrainList(ArrayList<Terrain> terrainList) {this.terrainList = terrainList;}
	
	public void AddEntityToScene (Entity _entity)
	{
		entityList.add(_entity);
	}
	
	public void AddTerrainToScene (Terrain _terrain)
	{
		terrainList.add(_terrain);
	}
	
	public void AddLightToScene (Light _light)
	{
		lightList.add(_light);
	}
	
	public void SaveScene ()
	{
		lastSave = LocalDateTime.now();
		
		
		
		
		
	}
}
