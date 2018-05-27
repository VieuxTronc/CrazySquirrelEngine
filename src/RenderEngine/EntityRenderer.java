package RenderEngine;

import java.util.ArrayList;
import java.util.Map;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import Entities.Entity;
import Models.RawModel;
import Models.TexturedModel;
import Shaders.StaticShader;
import Textures.ModelTexture;
import Utils.Maths;

public class EntityRenderer 
{
	private StaticShader shader; 
	
	public EntityRenderer (StaticShader shader, Matrix4f projectionMatrix)
	{
		this.shader = shader;
		shader.Start();
		shader.LoadProjectionMatrix(projectionMatrix);
		shader.Stop();
	}
	
	public void Render (Map<TexturedModel, ArrayList<Entity>> entities)
	{
		for(TexturedModel model:entities.keySet())
		{
			PrepareTexturedModels(model);
			ArrayList<Entity> batch = entities.get(model); 
			for(Entity entity:batch)
			{
				PrepareInstance(entity);
				GL11.glDrawElements(GL11.GL_TRIANGLES, model.getModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
			}
		}
		UnbindTexturedModels();
	}
	
	private void PrepareTexturedModels (TexturedModel model)
	{
		RawModel rawModel = model.getModel();
		GL30.glBindVertexArray(rawModel.getVaoId());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);
		ModelTexture texture = model.getTexture();
		
		if (texture.isHasTransparency())
		{
			MasterRenderer.DisableCulling();
		}
		
		shader.LoadFakeLightning(texture.isUseFakeLightning());
		shader.LoadShineVariables(texture.getShineDamper(), texture.getReflectivity());
		
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, model.getTexture().getID());
	}
	
	private void UnbindTexturedModels ()
	{
		MasterRenderer.EnableCulling();
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(2);
		GL30.glBindVertexArray(0);
	}
	
	private void PrepareInstance (Entity entity)
	{
		Matrix4f transformationMatrix = Maths.CreateTransformMatrix(entity.getPosition(), entity.getRotX(), entity.getRotY(), entity.getRotZ(), entity.getScale());
		shader.LoadTransformationMatrix(transformationMatrix);
	}
}
