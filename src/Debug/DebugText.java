package Debug;

import java.awt.Font;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;

public class DebugText 
{
	public TrueTypeFont font; 
	
	public DebugText ()
	{
		init();
	}
	
	public void init() 
	{
	    // load a default java font
	    Font awtFont = new Font("Times New Roman", Font.BOLD, 24);
	    font = new TrueTypeFont(awtFont, false);
    }
	
	public void Render ()
	{
		//Color.white.bind();
		//font.drawString(10, 10, "FPS: ", Color.red);
	}
}
