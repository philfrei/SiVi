package com.adonax.animatedFlame;

import java.awt.EventQueue;

import javax.swing.JFrame;

public class AppTop
{

	/**
	 * @param args
	 */
	public static void main(String[] args) {

//		final int hSize = 256;
//		final int vSize = 128;
		
		final int hSize = 1000;
		final int vSize = 200;
		
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
	    		frame.setSize(hSize + 8, vSize + 34);
	    		frame.setTitle("Animated Flames");
	    		
	    		Fireplace fp = new Fireplace(hSize, vSize);
	    		
	    		frame.add(fp);
	    		frame.setVisible(true);
	    		
	    		Animator animator = new Animator(fp, fp.getFlames());
	    		animator.start();
	    		
	    	}
	    });
	}
}
