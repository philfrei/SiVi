package com.adonax.animatedFlame;

import javax.swing.JApplet;
import javax.swing.SwingUtilities;

public class FlameApplet extends JApplet
{
	private static final long serialVersionUID = 1L;

	final int hSize = 256;
	final int vSize = 128;
	
	public void init() {
        //Execute a job on the event-dispatching thread; creating this applet's GUI.
        try {
            SwingUtilities.invokeLater(new Runnable() {
                public void run() 
                {
                	
                	setSize(hSize + 8, vSize + 34); 
                	
    	    		Fireplace fp = new Fireplace(hSize, vSize);
    	    		
    	    		add(fp);
    	    		setVisible(true);
    	    		
    	    		Animator animator = new Animator(fp, fp.getFlames());
    	    		animator.start();
                }
            });
        } catch (Exception e) {
            System.err.println("createGUI didn't complete successfully");
        }
    }
}
