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
package com.adonax.sivi;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

public class FinalDisplay extends JPanel
{
	private static final long serialVersionUID = 1L;

	private BufferedImage image;
	
	public BufferedImage getImage()
	{
		return image;
	}
	
	public FinalDisplay()
	{	
		Border border = BorderFactory.createRaisedBevelBorder();
		TitledBorder combineTitledBorder = 
				BorderFactory.createTitledBorder(border, "Final Texture");
		setBorder(combineTitledBorder);
	}
	
	// reminder: this will be called repeatedly by animation logic
	public void update(BufferedImage image, TopPanelModel tpm)
	{
		StringBuilder sb = new StringBuilder("Final Texture");
		if (tpm.isHorizontallySymmetric || tpm.isVerticallySymmetric) {
			sb.append(" with ");
			if (tpm.isHorizontallySymmetric) {
				sb.append("y-axis");
				if (tpm.isVerticallySymmetric) {
					sb.append(" and x-axis reflections");
				} else {
					sb.append(" reflection");
				}
			} else {
				if (tpm.isVerticallySymmetric) {
					sb.append("x-axis reflection");
				}
			}		
		}
		
		setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createRaisedBevelBorder(), sb.toString()));
		
		this.image = image;
		repaint();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		g.clearRect(0, 0, getWidth(), getHeight());
		g.drawImage(image, 6, 16, null);
	}	
}