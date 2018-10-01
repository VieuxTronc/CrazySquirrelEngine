package Debug;

import org.lwjgl.input.Keyboard;

public class DebugInputs 
{
	public static boolean isDemoMode = true; 
	
	public void Listen ()
	{
		while (Keyboard.next()) 
		{
		    if (Keyboard.getEventKeyState()) 
		    {
		        if (Keyboard.getEventKey() == Keyboard.KEY_F1) 
		        {
		        	isDemoMode = !isDemoMode; 
		        }
		        if (Keyboard.getEventKey() == Keyboard.KEY_F5) 
		        {
		        	//Debug.DebugLog("UPDATE");
		        }
		    }
		    else 
		    {
		        if (Keyboard.getEventKey() == Keyboard.KEY_F1) 
		        {
		        	//System.out.println("F1 Key Released");
		        }
		    }
		}
	}
}
