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
