package RenderEngine;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import Models.RawModel;
import Shaders.TerrainShader;
import Terrains.Terrain;
import Textures.TerrainTexturePack;
import Utils.Maths;

public class TerrainRenderer 
{
	private TerrainShader shader;
	
	public TerrainRenderer (TerrainShader _shader, Matrix4f _projectionMatrix)
	{
		shader = _shader; 
		shader.Start();
		shader.LoadProjectionMatrix(_projectionMatrix);
		shader.ConnectTextureUnits();
		shader.Stop();
	}
	
	public void Render (ArrayList<Terrain> terrains)
	{
		for (Terrain terrain : terrains) 
		{
			PrepareTerrain(terrain);
			LoadModelMatrix(terrain);
			GL11.glDrawElements(GL11.GL_TRIANGLES, terrain.getModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
			UnbindTexturedModels();
		}
	}
	
	private void PrepareTerrain (Terrain terrain)
	{
		RawModel rawModel = terrain.getModel();
		
		GL30.glBindVertexArray(rawModel.getVaoId());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);
		
		BindTextures(terrain);
		
		shader.LoadShineVariables(1, 0);
	}
	
	private void BindTextures (Terrain terrain) //Bind all the textures to the shader programm
	{
		TerrainTexturePack texturePack = terrain.getTexturePack();
		
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texturePack.getBackgroundTexture().getTextureID());
		
		GL13.glActiveTexture(GL13.GL_TEXTURE1);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texturePack.getrTexture().getTextureID());
		
		GL13.glActiveTexture(GL13.GL_TEXTURE2);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texturePack.getgTexture().getTextureID());
		
		GL13.glActiveTexture(GL13.GL_TEXTURE3);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texturePack.getbTexture().getTextureID());
		
		GL13.glActiveTexture(GL13.GL_TEXTURE4);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, terrain.getBlendMap().getTextureID());
	}
	
	private void UnbindTexturedModels ()
	{
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(2);
		GL30.glBindVertexArray(0);
	}
	
	private void LoadModelMatrix (Terrain terrain)
	{
		Matrix4f transformationMatrix = Maths.CreateTransformMatrix(new Vector3f(terrain.getX(), 0, terrain.getZ()), 0, 0, 0, 1);
		shader.LoadTransformationMatrix(transformationMatrix);
	}
}
