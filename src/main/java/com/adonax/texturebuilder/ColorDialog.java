package com.adonax.texturebuilder;

import javax.swing.JDialog;

public class ColorDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ColorPanel colorPanel;
	
	public ColorDialog(int width, int height, 
			ColorAxis colorAxis, STBPanel host) 
	{
		colorPanel = new ColorPanel(width, height, 
				colorAxis, host);
		add(colorPanel);
	}

	public void setColorAxis(ColorAxis colorAxis)
	{
		colorPanel.setColorAxis(colorAxis);
	}

	public void updatePanel()
	{
		colorPanel.sortAndDisplay();
	}
	
}
