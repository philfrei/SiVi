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

import javax.swing.*;
import javax.swing.border.TitledBorder;

public class ColorBarSet extends JPanel {

	private int width, height;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ColorBar[] cb;
	
	ColorAxis colorAxis;

	final int EDIT = 0;
	final int COPY = 1;
	final int PASTE = 2;
	int mode;
	
	
	ColorBarSet(int bars, int left, int top, int width, int height, 
			ColorAxis[] colorAxis, STBPanel host)
	{
		setLayout(null);
		
		this.width = width;
		this.height = height;
		
		TitledBorder combineTitledBorder = BorderFactory.createTitledBorder("Color Axis");
		setBorder(combineTitledBorder);
		Insets insets = combineTitledBorder.getBorderInsets(this);

		cb = new ColorBar[bars];
		for (int i = 0; i < bars; i++)
		{
			cb[i] = new ColorBar(left, top, 256, 24, 
					colorAxis[i], host, this);
			cb[i].setBounds(insets.left+10, insets.top + i*32 + 4 + 32, insets.left + 256 + 10, insets.top + 24);
			add(cb[i]);
		}	
		
		JPanel controlPanel = new JPanel();
		controlPanel.setBounds(insets.left, insets.top, insets.left + width, insets.top + 32);
		controlPanel.setOpaque(false);
		
		JLabel cpInstructions = new JLabel("Click BAR to:");
		controlPanel.add(cpInstructions);
		
		ButtonGroup modeOptions = new ButtonGroup();
		
		JRadioButton editButton = new JRadioButton("Edit");
		editButton.setSelected(true);
		modeOptions.add(editButton);
		editButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0) {
				mode = EDIT;
			}});
		controlPanel.add(editButton);
		
		JRadioButton copyButton = new JRadioButton("Copy");
		modeOptions.add(copyButton);
		copyButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0) {
				mode = COPY;
			}});
		controlPanel.add(copyButton);
		
		JRadioButton pasteButton = new JRadioButton("Paste");
		modeOptions.add(pasteButton);
		pasteButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0) {
				mode = PASTE;
			}});
		controlPanel.add(pasteButton);
		
		add(controlPanel);
					
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(width, height);
	}
}
