package Debug;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.DefaultCaret;

public class Console extends JFrame 
{
	public static JTextArea textArea = new JTextArea();
	
	public Console() 
	{
		this.setTitle("Debug Log");
		this.setIconImage(null);
		this.setSize(500, 300);
		this.setResizable(true);
		this.setLocationRelativeTo(null);
		this.setAlwaysOnTop(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		textArea.setEditable(false);
		textArea.setMargin(new Insets(5, 5, 5, 5));
		textArea.setBackground(Color.black);
		textArea.setForeground(Color.white);
		
		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setPreferredSize(new Dimension(500, 300));
		scrollPane.setAutoscrolls(true);
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		DefaultCaret caret = (DefaultCaret)textArea.getCaret(); 
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		
		this.setVisible(true);		
	}
	
	public void CloseConsole ()
	{
		System.exit(0);
	}
}
