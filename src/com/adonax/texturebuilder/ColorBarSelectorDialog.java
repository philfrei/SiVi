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
