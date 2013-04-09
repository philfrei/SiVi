/*
 *  This file is part of SiVi.
 *
 *  SiVi is free software: you can redistribute it and/or modify it
 *  under the terms of the GNU General Public License as published
 *  by the Free Software Foundation, either version 3 of the 
 *  License, or (at your option) any later version.
 *
 *  SiVi is distributed in the hope that it will be useful, but
 *  WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public 
 *  License along with SiVi.  If not, see 
 *  <http://www.gnu.org/licenses/>.
 */
package com.adonax.simplexNoiseVisualizer;

import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JScrollPane;


/**
 * Main class for launching the app.
 */
public class SiViApplication {
	
	public static void main(String[] args) {

	    EventQueue.invokeLater(new Runnable(){
		    
	    	public void run()
	    	{	
	    		AppFrame frame = new AppFrame();
	    		frame.setDefaultCloseOperation(
	    				JFrame.EXIT_ON_CLOSE);
	    		frame.setVisible(true);
	    	}
	    });
	}
}

class AppFrame extends JFrame
{
	
	private static final long serialVersionUID = 1L;

	private JScrollPane scroll;
	
	// CONSTRUCTOR
	public AppFrame(){
		
		setTitle("Simplex Noise Texture Visualizer");
	    
	    TopPanel topPanel = new TopPanel();
	
	    scroll = new JScrollPane(topPanel);
	    scroll.setPreferredSize(new Dimension(1150, 850));
	    add(scroll);
	    
	    MenuBar menuBar = new MenuBar(topPanel);
	    setJMenuBar(menuBar.create());
	    pack();
	}	
}