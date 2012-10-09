package com.adonax.tutorial;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;


public class SimplexCard extends JPanel
{
	private static final long serialVersionUID = 1L;
	
	int width, height;
	BufferedImage image;
	Rectangle imageRect;
	boolean hover;
	final TutorialFramework tf;
	
	public SimplexCard (int width, int height, final TutorialFramework tf)
	{
		setLayout(null);
		
		this.width = width;
		this.height = height;
		this.tf = tf;
		
		NavigationPanel np = new NavigationPanel(tf, 
				"Template Card", "Title Card", "ColorMap Card");
		np.setBounds((width - 428)/2, 0, 428, 32);
		add(np);

		JEditorPane textArea = new JEditorPane();
		textArea.setEditable(false);
	
		URL introURL = TutorialFramework.class.getResource(
				"pageContent/SimplexNoise");
		try
		{
			textArea.setPage(introURL);
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		JScrollPane scroller = new JScrollPane(textArea);
		scroller.setBounds(50, 50, width - 100, height - 150);
		add(scroller);
	}
}
