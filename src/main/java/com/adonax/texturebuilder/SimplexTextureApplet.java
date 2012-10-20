package com.adonax.texturebuilder;

import javax.swing.JApplet;
import javax.swing.SwingUtilities;

public class SimplexTextureApplet extends JApplet {
	private static final long serialVersionUID = 1L;

	   public void init() {
	        //Execute a job on the event-dispatching thread; creating this applet's GUI.
	        try {
	            SwingUtilities.invokeLater(new Runnable() {
	                public void run() 
	                {
	                	setSize(1108, 854); 
	            	  	STBPanel sfp = new STBPanel(1100, 820, null);
	            	    add(sfp);
	                }
	            });
	        } catch (Exception e) {
	            System.err.println("createGUI didn't complete successfully");
	        }
	    }
}
