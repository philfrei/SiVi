package com.adonax.texturebuilder;

import java.awt.EventQueue;

import javax.swing.JFrame;


public class SimplexTextureBuilder {
	
	public static STBFrame frame;
	
	public static void main(String[] args) {

	    EventQueue.invokeLater(new Runnable(){
		    
	    	public void run()
	    	{	
	    		frame = new STBFrame();
	    		frame.setDefaultCloseOperation(
	    				JFrame.EXIT_ON_CLOSE);
	    		frame.setVisible(true);
	    	}
	    });
	}
}