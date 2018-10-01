package Editor;

import java.awt.Canvas;
import java.awt.Dimension;

import javax.swing.JPanel;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.PixelFormat;

public class EditorViewport extends JPanel
{
	private static final long serialVersionUID = 1L;
	private static final int FPS_CAP = 120;
	private static long lastFrameTime; 
	private static float delta; 
	
	public EditorViewport ()
	{

	}
	
	public static void CreateViewport()
	{
		Canvas canvas = new Canvas()
		{
			private static final long serialVersionUID = 1L;

			@Override
            public void addNotify() 
			{
                super.addNotify();
                CreateViewport();
            }

            @Override
            public void removeNotify() 
			{
                CloseViewport();
                super.removeNotify();
            }
		};
	    canvas.setPreferredSize(new Dimension(500, 420));
	    canvas.setIgnoreRepaint(true);
	    
	    try 
	    {
			ContextAttribs attribs = new ContextAttribs(3,2).withForwardCompatible(true).withProfileCore(true); 
			Display.setDisplayMode(new org.lwjgl.opengl.DisplayMode(500, 420));
			Display.create(new PixelFormat(), attribs);
	        Display.setParent(canvas);
	    } 
		catch (LWJGLException e) 
		{
			e.printStackTrace();
		}
	    
		GL11.glViewport(0, 0, 500, 420);
		lastFrameTime = GetCurrentTime();
	    
	    JPanel canvasPanel = new JPanel();
	    canvasPanel.add(canvas);
	}
	
    public static void UpdateViewport ()
	{
		Display.sync(FPS_CAP);
		Display.update();
		long currentFrameTime = GetCurrentTime(); 
		delta = (currentFrameTime - lastFrameTime) / 1000f;
		lastFrameTime = currentFrameTime; 
	}
	
	public static void CloseViewport ()
	{
		Display.destroy();
	}
	
	//Time in milliseconds
	private static long GetCurrentTime ()
	{
		return Sys.getTime() * 1000 / Sys.getTimerResolution(); 
	}
	
	public static float GetFrameTimeSeconds()
	{
		return delta;
	}
}
