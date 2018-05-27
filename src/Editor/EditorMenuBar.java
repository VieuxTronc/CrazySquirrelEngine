package Editor;

import javax.swing.JMenu;
import javax.swing.JMenuBar;

public class EditorMenuBar extends JMenuBar
{
	public EditorMenuBar ()
	{
	    JMenu menu = new JMenu("Files");	    
	    JMenu subMenu = new JMenu("Load Scene");
	    JMenu subMenu2 = new JMenu("Save Scene");
	    menu.add(subMenu);
	    menu.add(subMenu2);
	    this.add(menu);
	}
}
