package Textures;

//Use fake lightning because quad's normal point to each other 
//We set the normal to go upwards

public class ModelTexture 
{
	public int textureID; 
	
	private float shineDamper = 1; 
	private float reflectivity = 0; 
	
	private boolean hasTransparency = false;
	private boolean useFakeLightning = false; 
	
	public boolean isUseFakeLightning() {return useFakeLightning;}
	public void setUseFakeLightning(boolean useFakeLightning) {this.useFakeLightning = useFakeLightning;}
	
	public boolean isHasTransparency() {return hasTransparency;}
	public void setHasTransparency(boolean hasTransparency) {this.hasTransparency = hasTransparency;}
	
	public float getShineDamper() {return shineDamper;}
	public void setShineDamper(float shineDamper) {this.shineDamper = shineDamper;}

	public float getReflectivity() {return reflectivity;}
	public void setReflectivity(float reflectivity) {this.reflectivity = reflectivity;}

	public ModelTexture (int id)
	{
		textureID = id; 
	}
	
	public int getID ()
	{
		return textureID; 
	}
}
