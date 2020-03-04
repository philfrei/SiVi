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
package com.adonax.sivi.color;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.*;
import java.io.IOException;

import javax.swing.*;
import javax.swing.border.Border;

public class ColorBar extends JPanel implements MouseListener,
	MouseMotionListener, Transferable {

	private static final long serialVersionUID = 1L;
	
	private final ColorAxis colorAxis;
	private JLabel colorBarImage;
	private Border blackBorder, redBorder;
	private boolean isEditing;
	
	private static DataFlavor colorBarFlavor;
	public static DataFlavor getColorBarFlavor()
	{
		return colorBarFlavor;
	}
	
	public void setColorAxis(ColorAxis colorAxis)
	{
		colorAxis.copyTo(this.colorAxis);
	}
	public ColorAxis getColorAxis()
	{
		return colorAxis;
	}
	
	ColorBar()
	{
		
		this.colorAxis = new ColorAxis();
		
        String html =
                "<html><p><font color=\"#000000\" " +
                "size=\"2\" face=\"SanSerif\"" +
                ">CLICK TO EDIT, DRAG TO COPY" +
                "</font></p></html>";
        setToolTipText(html);
		
		ImageIcon img = new ImageIcon();
		img.setImage(colorAxis.img);
		colorBarImage = new JLabel(img);
		blackBorder = BorderFactory.createLineBorder(Color.BLACK);
		redBorder = BorderFactory.createLineBorder(Color.RED);
		colorBarImage.setBorder(blackBorder);

		add(colorBarImage);
		
		addMouseListener(this);
		addMouseMotionListener(this);

//		String colorBarType = DataFlavor.javaJVMLocalObjectMimeType 
//				+ ";class=com.adonax.simplexNoiseVisualizer.color.ColorBar";	
//
//		String iAm = this.getClass().getName();
//		String colorBarType = DataFlavor.javaJVMLocalObjectMimeType 
//				+ ";class=\"" + iAm + "\"";	
//		try
//		{
//			colorBarFlavor = new DataFlavor(colorBarType);
////			colorBarFlavor = new DataFlavor(this.getClass(), 
////					"ColorBar");
//		} 
//		catch (ClassNotFoundException e)
//		{
//			e.printStackTrace();
//		}
//		
		setTransferHandler(new ColorBarTransferHandler());
	}
		
	@Override
	public void mouseClicked(MouseEvent arg0) {}
	
	@Override
	public void mouseEntered(MouseEvent arg0) {
		colorBarImage.setBorder(redBorder);
		repaint();
	}
	
	@Override
	public void mouseExited(MouseEvent arg0) {
		colorBarImage.setBorder(blackBorder);
		repaint();
	}

	@Override
	public void mousePressed(MouseEvent arg0) 
	{
		isEditing = true;
	} 
	
	@Override
	public void mouseReleased(MouseEvent arg0) 
	{
		if (isEditing)
		{
			JDialog colorDialog = new JDialog(){
				private static final long serialVersionUID = 1L;
			};
			colorDialog.add(new ColorBarEditingGUI(colorAxis));
			colorDialog.setTitle("Edit Color Map");
			colorDialog.setBounds(0, 0, 360, 420);
			colorDialog.setModal(true);
//			colorDialog.setAlwaysOnTop(true);
			colorDialog.setVisible(true);
		}	
	}
	
	@Override
	public void mouseDragged(MouseEvent e)
	{
		isEditing = false;
		JComponent comp = (JComponent)e.getSource();
		TransferHandler th = comp.getTransferHandler();
		th.exportAsDrag(comp, e, TransferHandler.COPY);
		
		colorBarImage.setBorder(blackBorder);
		repaint();	
	}
	@Override
	public void mouseMoved(MouseEvent e) 
	{
		repaint();
	}
	
	// For Transferable interface
	
	@Override
	public Object getTransferData(DataFlavor arg0)
			throws UnsupportedFlavorException, IOException
	{
		if (!arg0.equals(colorBarFlavor))
		{
			throw new UnsupportedFlavorException(arg0);
		}
		return (Object)this;
	}
	
	@Override
	public DataFlavor[] getTransferDataFlavors()
	{
		DataFlavor[] flavors = {colorBarFlavor};
		return flavors;
	}
	
	@Override
	public boolean isDataFlavorSupported(DataFlavor arg0)
	{
		if (arg0.equals(colorBarFlavor)) return true;
		return false;
	}
	
	
	public void createDataFlavor()
	{
		String iAm = this.getClass().getName();
		String colorBarType = DataFlavor.javaJVMLocalObjectMimeType 
				+ ";class=\"" + iAm + "\"";	
		
//		String colorBarType = DataFlavor.javaJVMLocalObjectMimeType 
//		+ ";class=\"com/adonax/simplexNoiseVisualizer/color/ColorBar\"";	
	
		try
		{
			colorBarFlavor = new DataFlavor(colorBarType);
//			colorBarFlavor = new DataFlavor(this.getClass(), 
//					"ColorBar");
		} 
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		
		setTransferHandler(new ColorBarTransferHandler());
		
	}
}
