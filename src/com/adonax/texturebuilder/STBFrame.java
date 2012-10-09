package com.adonax.texturebuilder;

import javax.swing.JFrame;

public class STBFrame extends JFrame
{
	
	public STBPanel stbPanel;
	
	private static final long serialVersionUID = 1L;

	// CONSTRUCTOR
	public STBFrame(){
		
		setTitle("Simplex Noise Texture Builder");
		/*
		 *  Added a bit of space to account for the Frame's 
		 *  border & title bar. The Game screen, where the 
		 *  animation takes place will by 500 x 500.
		 */
		setSize(1108, 854); 
	    
		// Area where animation will be drawn.
	   stbPanel = new STBPanel(1100, 820, this);
	   
	    // Place the GameScreen on our outermost frame.
	    add(stbPanel);
	
	}
}