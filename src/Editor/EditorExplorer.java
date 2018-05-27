package Editor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

public class EditorExplorer extends JPanel 
{
	private int prefX; 
	private int prefY;
	private JTree tree;
	
	public EditorExplorer(int Xsize, int Ysize) 
	{
		setLayout(new GridBagLayout());
		setBackground(Color.red);
		this.prefX = Xsize;
		this.prefY = Ysize;
		
		DefaultMutableTreeNode top = new DefaultMutableTreeNode("Root");
		createNodes(top);
		tree = new JTree(top);
		JScrollPane treeView = new JScrollPane(tree);
		//this.add(treeView);
		//treeView.setPreferredSize(this.getPreferredSize());
		
		add(treeView, new GridBagConstraints(0, 0, 2, 1, 1.0, 0.0,GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
	}
	
	@Override
	public Dimension getPreferredSize() 
	{
		return new Dimension(prefX, prefY);
	}
	
	private void createNodes(DefaultMutableTreeNode top) 
	{
	    DefaultMutableTreeNode category = null;
	    DefaultMutableTreeNode element = null;
	    
	    category = new DefaultMutableTreeNode("Root 1");
	    top.add(category);
	    
	    //original Tutorial
	    element = new DefaultMutableTreeNode(new TreeElement("Test","test2"));
	    category.add(element);
	    
	    //Tutorial Continued
	    element = new DefaultMutableTreeNode(new TreeElement("Test","test2"));
	    category.add(element);
	    
	    //Swing Tutorial
	    element = new DefaultMutableTreeNode(new TreeElement("Test","test2"));
	    category.add(element);

	    
	    category = new DefaultMutableTreeNode("Root 2");
	    top.add(category);

	    //VM
	    element = new DefaultMutableTreeNode(new TreeElement("Test","test2"));
	    category.add(element);

	    //Language Spec
	    element = new DefaultMutableTreeNode(new TreeElement("Test","test2"));
	    category.add(element);
	}
}

class TreeElement
{
	private String name;
	private String surName; 
	
	public TreeElement(String name, String url)
	{
		this.name = name;
		this.surName = url;
	}
}
