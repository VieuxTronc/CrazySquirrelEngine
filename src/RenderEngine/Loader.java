package RenderEngine;
 
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import Models.RawModel;

public class Loader 
{
	private ArrayList<Integer> vaos = new ArrayList<Integer>(); 
	private ArrayList<Integer> vbos = new ArrayList<Integer>(); 
	private ArrayList<Integer> textures = new ArrayList<Integer>();
	
	public RawModel loadToVAO (float[] positions, float[] textureCoord, float[] normals, int[] indices)
	{
		int VaoID = CreateVAO(); 
		BindIndicesBuffer(indices);
		StoreDataAttributeList(0, 3, positions);
		StoreDataAttributeList(1, 2, textureCoord);
		StoreDataAttributeList(2, 3, normals);
		UnbindVAO();
		return new RawModel(VaoID, indices.length); 
	}
	 
	public int LoadTexture (String fileName)
	{
		Texture texture = null;
		
		try
		{
			texture = TextureLoader.getTexture("PNG", new FileInputStream("res/"+fileName+".png")); 
			
			//Mip map generation
			GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR_MIPMAP_LINEAR);
			GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL14.GL_TEXTURE_LOD_BIAS, - 0.4f); //Texture resolution
		}
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		int textureID = texture.getTextureID(); 
		textures.add(textureID);
		return textureID; 
	}
	
	public void CleanUp () 
	{
		for(int vao:vaos)
		{
			GL30.glDeleteVertexArrays(vao);
		}
		for(int vbo:vbos)
		{
			GL15.glDeleteBuffers(vbo);
		}
		for(int texture:textures)
		{
			GL11.glDeleteTextures(texture);
		}
	}
	
	private int CreateVAO ()
	{
		int VaoID = GL30.glGenVertexArrays();
		vaos.add(VaoID); 
		GL30.glBindVertexArray(VaoID);
		return VaoID; 
	}
	
	private void StoreDataAttributeList (int attributeMember, int coordinateSize, float [] data)
	{
		int VboID = GL15.glGenBuffers();
		vbos.add(VboID); 
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, VboID);
		FloatBuffer buffer = StoreDataInFloatBuffer(data); 
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STREAM_DRAW);
		GL20.glVertexAttribPointer(attributeMember, coordinateSize, GL11.GL_FLOAT, false, 0, 0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	}
	
	private void UnbindVAO ()
	{
		GL30.glBindVertexArray(0);
	}
	
	private void BindIndicesBuffer (int[] indices)
	{
		int VboID = GL15.glGenBuffers();
		vbos.add(VboID);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, VboID);
		IntBuffer buffer = StoreDataInIntBuffer(indices);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
	}
	
	private IntBuffer StoreDataInIntBuffer (int[] data)
	{
		IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer; 
	}
	
	private FloatBuffer StoreDataInFloatBuffer (float [] data)
	{
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length); 
		buffer.put(data);
		buffer.flip();
		return buffer; 
	}
}
