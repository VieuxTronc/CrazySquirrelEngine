package Debug;

public class Debug 
{
	public static void DebugLog (String msg)
	{
		Console.textArea.append("[LOG] - " + msg.toString() + "\n");
	}
	public static void WarningLog (String msg)
	{
		Console.textArea.append("[WARNING] - " + msg + "\n");
	}
	public static void ErrorLog (String msg)
	{
		Console.textArea.append("[ERROR] - " + msg + "\n");
	}
	
	public static void DebugBool (boolean msg)
	{
		Console.textArea.append("[LOG - BOOL] - " + msg + "\n");
	}
	
	public static void DebugLong (long msg)
	{
		Console.textArea.append("[LOG - BOOL] - " + msg + "\n");
	}
	
	public static void DebugFloat (float msg)
	{
		Console.textArea.append("[LOG - BOOL] - " + msg + "\n");
	}
}
