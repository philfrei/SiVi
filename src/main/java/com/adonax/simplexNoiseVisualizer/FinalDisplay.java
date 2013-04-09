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
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.*;
import javax.swing.border.TitledBorder;

public class FinalDisplay extends JPanel
{
	private static final long serialVersionUID = 1L;

	private BufferedImage image;
		
	public FinalDisplay(final TopPanelModel settings)
	{	
		TitledBorder combineTitledBorder = 
				BorderFactory.createTitledBorder(
						"Final Texture");
		setBorder(combineTitledBorder);
		
		JPanel imagePanel = new JPanel() 
		{

			private static final long serialVersionUID = 1L;
			
			@Override
			public void paintComponent(Graphics g) {
				g.drawImage(image, 0, 0, null);
			}
			
			@Override
			public Dimension getPreferredSize() {
				return new Dimension(settings.finalWidth, 
						settings.finalHeight);
			}
		};
		
		add(imagePanel);
	}
	
	public void update(BufferedImage image)
	{
		this.image = image;
		repaint();
	}
}
