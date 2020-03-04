package com.adonax.sivi.color;

import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import javax.swing.JComponent;
import javax.swing.TransferHandler;

public class ColorBarTransferHandler extends TransferHandler
{
	private static final long serialVersionUID = 1L;

	public int getSourceActions(JComponent source)
	{
		return COPY;
	}
	
	protected Transferable createTransferable(JComponent source)
	{
		ColorBar cb = (ColorBar) source;
		ColorBar transfer = new ColorBar();
		transfer.setColorAxis(cb.getColorAxis());
		return transfer;
	}
	
	public boolean canImport(TransferSupport support)
	{
		if (support.getDataFlavors()[0].equals(ColorBar.getColorBarFlavor()))
		{
			return (support.getUserDropAction() == TransferHandler.COPY);
		}
		else return false;	
	}
	
	public boolean importData(TransferSupport support)
	{
		ColorBar targetCB = (ColorBar)support.getComponent();
		
		Transferable transferable = support.getTransferable(); 
		ColorBar sourceCB = new ColorBar();
		try
		{
			sourceCB = (ColorBar)transferable.getTransferData(
					ColorBar.getColorBarFlavor());
		} catch (UnsupportedFlavorException e)
		{
			e.printStackTrace();
			return false;
		} catch (IOException e)
		{
			e.printStackTrace();
			return false;
		}		

		// if we get this far, no reason following won't work
		targetCB.setColorAxis(sourceCB.getColorAxis());
		ColorMapSelectorGUI.topPanel.updateOctaveDisplays(); // should this be in mouse handler?
		return true;
	}
}
