package Editor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.DefaultCaret;

public class EditorConsole extends JPanel 
{
	private JTextArea textArea = new JTextArea();
	private int prefX; 
	private int prefY;
	
	public EditorConsole(int Xsize, int Ysize) 
	{
		this.prefX = Xsize;
		this.prefY = Ysize; 

		textArea.setEditable(false);
		textArea.setMargin(new Insets(5, 5, 5, 5));
		textArea.setBackground(Color.black);
		textArea.setForeground(Color.white);
		textArea.setCaretColor(Color.white);
		textArea.setLineWrap(true);

		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setPreferredSize(new Dimension(prefX, prefY));
		scrollPane.setAutoscrolls(true);
		
		//Caret is the cursor
		DefaultCaret caret = (DefaultCaret)textArea.getCaret(); 
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		
		setLayout(new GridBagLayout());
		add(scrollPane, new GridBagConstraints(0, 0, 2, 1,1.0, 0.0,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,new Insets(0, 0, 0, 0), 0, 0));
		
		this.setVisible(true);		
	}
	
	@Override
	public Dimension getPreferredSize() 
	{
		return new Dimension(prefX, prefY);
	}
}
