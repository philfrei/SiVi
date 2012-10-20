package com.adonax.texturebuilder;

import javax.swing.JDialog;

public class ColorBarSelectorDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ColorBarSelectorPanel cbsp;
	
	ColorBarSelectorDialog(ColorAxis[] colorAxisSet)
	{
		cbsp = new ColorBarSelectorPanel(colorAxisSet,
				this);
		add(cbsp);
	}
	
	public void setCallback(SimplexTextureSource sts) {
		cbsp.setCallback(sts);
	}
	
}
