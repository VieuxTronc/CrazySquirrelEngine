package Models;

import Textures.ModelTexture;

public class TexturedModel 
{
	private RawModel model; 
	private ModelTexture texture; 
	
	public TexturedModel(RawModel _model, ModelTexture _texture)
	{
		model = _model; 
		texture = _texture; 
	}

	public RawModel getModel() {return model;}
	public ModelTexture getTexture() {return texture;}
}
