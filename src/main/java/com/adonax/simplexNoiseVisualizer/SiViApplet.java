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

import javax.swing.*;

public class SiViApplet extends JApplet {
	private static final long serialVersionUID = 1L;
	
	private TopPanel topPanel;
	
	public void init() 
	{
		try 
		{
			SwingUtilities.invokeLater(new Runnable() {
			    public void run() 
			    {
			    	setSize(1108, 854); 
				  	topPanel = new TopPanel();
				    add(topPanel);
				    
				    JScrollPane scroll = new JScrollPane(topPanel);
				    add(scroll);
				    
				    MenuBar menuBar = new MenuBar(topPanel);
				    setJMenuBar(menuBar.create());
				    
			    }
			});
	    } 
		catch (Exception e) 
	    {
	        System.err.println("createGUI didn't complete successfully");
	    }
	}
}
		
