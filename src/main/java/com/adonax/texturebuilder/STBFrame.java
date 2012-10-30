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