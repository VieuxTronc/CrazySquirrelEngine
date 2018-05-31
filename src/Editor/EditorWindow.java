package Editor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class EditorWindow extends JFrame 
{
	private static final long serialVersionUID = 1L;
	private static final Color BLUE = new Color(200, 200, 255);

	public EditorWindow ()
	{
		//Frame
		JFrame frame = new JFrame("Crazy Squirrel Engine");
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	     
	    frame.setJMenuBar(new EditorMenuBar());
	     
	    //View port
     	ColorPanel viewport = new ColorPanel(BLUE, 500, 420);
     	
     	//Fill content panel
     	//Note that a JFrame's contentPane uses BorderLayout by default
	    frame.getContentPane().add(new EditorToolBar(800, 80), BorderLayout.NORTH);
	    frame.getContentPane().add(new EditorExplorer(300, 500), BorderLayout.WEST);
	    frame.getContentPane().add(viewport, BorderLayout.CENTER);
	    frame.getContentPane().add(new EditorConsole(800, 100), BorderLayout.SOUTH);
	    frame.setMinimumSize(new Dimension(1100, 800));
	    frame.pack();
	    frame.setLocationByPlatform(true);
	    frame.setVisible(true);
	}
	
}

class ColorPanel extends JPanel 
{
	private static final long serialVersionUID = 1L;
	private static final float FONT_POINTS = 24f;
	private int prefW;
	private int prefH;
	
	public ColorPanel(Color color, int prefW, int prefH) 
	{
		setBackground(color);
		this.prefW = prefW;
		this.prefH = prefH;
	
		// GBL can be useful for simply centering components
		setLayout(new GridBagLayout()); 
		String text = String.format("%d x %d", prefW, prefH);
		JLabel label = new JLabel(text, SwingConstants.CENTER);
		label.setFont(label.getFont().deriveFont(FONT_POINTS));
		label.setForeground(Color.gray);
		add(label);
	}
	
	@Override
	public Dimension getPreferredSize() 
	{
		return new Dimension(prefW, prefH);
	}
}
