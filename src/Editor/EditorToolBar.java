package Editor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

public class EditorToolBar extends JPanel 
{
	private int prefX; 
	private int prefY;
	
	public EditorToolBar(int Xsize, int Ysize) 
	{
		this.prefX = Xsize;
		this.prefY = Ysize;
		setBackground(Color.yellow);
		
		FlowLayout flowLayout = new FlowLayout();
		this.setLayout(flowLayout);
		
		JButton b1 = new JButton("");
		b1.setPreferredSize(new Dimension(50, 50));
		JButton b2 = new JButton("");
		b2.setPreferredSize(new Dimension(50, 50));
		JButton b3 = new JButton("");
		b3.setPreferredSize(new Dimension(50, 50));
		JButton b4 = new JButton("");
		b4.setPreferredSize(new Dimension(50, 50));
		JButton b5 = new JButton("");
		b5.setPreferredSize(new Dimension(50, 50));
		
		this.add(b1);
		this.add(b2);
		this.add(b3);
		this.add(b4);
		this.add(b5);
	}
	
	@Override
	public Dimension getPreferredSize() 
	{
		return new Dimension(prefX, prefY);
	}
}
