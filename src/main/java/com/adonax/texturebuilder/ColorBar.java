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

import java.awt.*;
import java.awt.event.*;

import javax.swing.JComponent;

public class ColorBar extends JComponent implements MouseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int width, height;
	private boolean hover;

	private ColorBarEditorDialog colorDialog;
	private ColorAxis colorAxis;
	private ColorBarSet parent;
	private STBPanel grandParent;
	
	public void setColorAxis(ColorAxis colorAxis)
	{
		this.colorAxis = colorAxis;
	}
	public ColorAxis getColorAxis()
	{
		return colorAxis;
	}
	
	
	ColorBar(int left, int top, int width, int height, 
			ColorAxis colorAxis, STBPanel grandParent,
			ColorBarSet parent)
	{
		
		this.colorAxis = colorAxis;
		this.parent = parent;
		this.grandParent = grandParent;
		
		this.width = width;
		this.height = height;
		
		
		colorDialog = new ColorBarEditorDialog(300, 330, colorAxis, grandParent);
		colorDialog.setBounds(left, top - 16, 308, 364);
		colorDialog.setModal(true);
		
		addMouseListener(this);
	}
		
	@Override
	public void mouseClicked(MouseEvent arg0) 
	{
		if (parent.mode == parent.EDIT)
		{
			colorDialog.updatePanel();
			colorDialog.setVisible(true);
			
		}
		if (parent.mode == parent.COPY)
		{
			parent.colorAxis = this.colorAxis.copy();
		}
		if (parent.mode == parent.PASTE)
		{
			this.colorAxis = parent.colorAxis.copy();
			colorDialog.setColorAxis(colorAxis);
			grandParent.update();
		}
	}
	
	@Override
	public void mouseEntered(MouseEvent arg0) {
		hover = true;
		repaint();
	}
	
	@Override
	public void mouseExited(MouseEvent arg0) {
		hover = false;
		repaint();
	}

	@Override
	public void mousePressed(MouseEvent arg0) {} 
	
	@Override
	public void mouseReleased(MouseEvent arg0) {}
	
	public void paintComponent(Graphics g){
		Graphics2D g2 = (Graphics2D)g;
		
		g2.setPaint(hover ? Color.RED : Color.BLACK);
		g2.fillRect(0, 0, width, height);
		
		g2.drawImage(colorAxis.img, 0, 4, null);
	}
}
