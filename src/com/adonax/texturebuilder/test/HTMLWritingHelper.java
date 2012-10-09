package com.adonax.texturebuilder.test;

import java.awt.EventQueue;
import java.io.IOException;
import java.net.URL;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

class HTMLWritingHelper extends JPanel
{
	HTMLWritingHelper(int width, int height)
	{
		setLayout(null);
		
		
//		JEditorPane textArea = new JEditorPane();
		JTextPane textArea = new JTextPane();
		textArea.setEditable(false);
		textArea.setBounds(0, 0, 700, 370);
		
		
//		URL introURL = TutorialFramework.class.getResource(
//				"smoothNoise.rtf");
		URL introURL = HTMLWritingHelper.class.getResource(
				"classicClouds.html");

		try
		{
			textArea.setPage(introURL);
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		JScrollPane scroller = new JScrollPane(textArea);
		scroller.setBounds(0, 0, 700, 370);
		add(scroller);		
		
		
 
	}


	public static void main(String[] args) {

	    EventQueue.invokeLater(new Runnable(){
		    
	    	public void run()
	    	{	
	    		JFrame frame = new JFrame();
	    		frame.setDefaultCloseOperation(
	    				JFrame.EXIT_ON_CLOSE);
				/*
				 *  Added a bit of space to account for the Frame's 
				 *  border & title bar.
				 */	    		
	    		frame.setSize(728, 414);
	    		frame.setTitle("HTML Helper");
	    		HTMLWritingHelper panel = 
	    				new HTMLWritingHelper(720, 370);
	    		panel.setBounds(0, 0, 720, 370);
	    		frame.add(panel);
	    		frame.setVisible(true);
	    	}
	    });
	}
	
	private static final long serialVersionUID = 1L;
	
}

	