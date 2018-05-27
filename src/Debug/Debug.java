package Debug;

public class Debug 
{
	public static void DebugLog (String msg)
	{
		Console.textArea.append("[LOG] - " + msg + "\n");
	}
	public static void WarningLog (String msg)
	{
		Console.textArea.append("[WARNING] - " + msg + "\n");
	}
	public static void ErrorLog (String msg)
	{
		Console.textArea.append("[ERROR] - " + msg + "\n");
	}
}
