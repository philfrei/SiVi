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

package com.adonax.sivi.gallery;

import java.awt.Dimension;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.adonax.sivi.TopPanel;

public class GalleryDialog extends JDialog {

	private static final long serialVersionUID = 1L;

	public final TopPanel topPanel;
	
	public GalleryDialog(TopPanel topPanel)
	{
	    setTitle("Gallery");
	    this.topPanel = topPanel;
	    
	    JPanel galleryCard = new GalleryCard(this);
	    galleryCard.setPreferredSize(new Dimension(950, 1800));
	    
	    JScrollPane scroll = new JScrollPane(galleryCard,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	    scroll.setPreferredSize(new Dimension(1000, 1000));
	
	    add(scroll);
	}
}
