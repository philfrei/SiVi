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
package com.adonax.tutorial;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;


public class ColorMapCard extends JPanel
{
	private static final long serialVersionUID = 1L;
	
	int width, height;
	BufferedImage image;
	Rectangle imageRect;
	boolean hover;
	final TutorialFramework tf;
	
	public ColorMapCard (int width, int height, final TutorialFramework tf)
	{
		setLayout(null);
		
		this.width = width;
		this.height = height;
		this.tf = tf;
		
		NavigationPanel np = new NavigationPanel(tf, 
				"Simplex Card", "Title Card", "Title Card");
		np.setBounds((width - 428)/2, 0, 428, 32);
		add(np);

		JEditorPane textArea = new JEditorPane();
		textArea.setEditable(false);
	
		URL introURL = TutorialFramework.class.getResource(
				"pageContent/ColorMap");
		try
		{
			textArea.setPage(introURL);
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		JScrollPane scroller = new JScrollPane(textArea);
		scroller.setBounds(50, 50, width - 100, height - 150);
		add(scroller);
	}
}
