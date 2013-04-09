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
package com.adonax.simplexNoiseVisualizer.color;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;
import javax.swing.border.Border;

public class ColorBarEditingGUI extends JPanel 
	implements ActionListener
{
	private static final long serialVersionUID = 1L;

	private JLabel[] colorPad;
	private JTextField[] locationVal, redVal, greenVal, blueVal;
	private JButton[] delButton;
	private JButton flip, negative;
	private Font fixedFiller = new Font(Font.MONOSPACED, Font.PLAIN, 12);
	private Font smallerButton = new Font(Font.SANS_SERIF, Font.BOLD, 11);
	
	private ColorAxis colorAxis;
	private ArrayList<ColorBarPeg> colorBarPegs;
	private boolean useHsbLerp;
	
	public void setColorAxis(ColorAxis sourceCA)
	{
		colorAxis = sourceCA;
		this.colorBarPegs = colorAxis.getColorBarPegs();
	}
		
	final int BAR_HEIGHT = 16;
	final int ROWS = 10;
	
	public ColorBarEditingGUI(ColorAxis colorAxis) 
	{
		this.colorAxis = colorAxis;
		
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.PAGE_START;

		
		ImageIcon img = new ImageIcon();
		img.setImage(colorAxis.img);
		final JLabel topColorBar = new JLabel(img);
		Border blackBorder = BorderFactory.createLineBorder(Color.BLACK);
		topColorBar.setBorder(blackBorder);

		topColorBar.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent arg0)
			{}

			@Override
			public void mouseEntered(MouseEvent arg0)
			{}

			@Override
			public void mouseExited(MouseEvent arg0)
			{}

			@Override
			public void mousePressed(MouseEvent arg0)
			{
				int newPegLocation = arg0.getX();
				// adjust for clicks on the border
				newPegLocation -= 1;
				if (newPegLocation >= 0 && newPegLocation < 256)
				{
					addNewPeg(newPegLocation);
				}
			}

			@Override
			public void mouseReleased(MouseEvent arg0)
			{}
			});
		
		topColorBar.addMouseMotionListener(new MouseMotionListener(){

			@Override
			public void mouseDragged(MouseEvent arg0)
			{}

			@Override
			public void mouseMoved(MouseEvent arg0)
			{
				int newPegLocation = arg0.getX();
				// adjust for clicks on the border
				newPegLocation -= 1;
				if (newPegLocation >= 0 && newPegLocation < 256)
				{
					topColorBar.setToolTipText(
							String.valueOf(newPegLocation));
				}
				
			}});

		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 6;
		c.insets = new Insets(4, 0, 4, 0);
		add(topColorBar, c);
		c.gridwidth = 1;
		
		// Second ROW -- buttons		
		JPanel buttonPanel = new JPanel();
		
		flip = new JButton("reverse");
		flip.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				for (ColorBarPeg rd : colorBarPegs)
				{
					rd.setLocation(255 - rd.getLocation());
				}
				sortAndDisplay();
				update();
			}});
		buttonPanel.add(flip);
		
		negative = new JButton("negative");
		negative.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				for (ColorBarPeg rd : colorBarPegs)
				{
					rd.setrColor(255 - rd.getrColor());
					rd.setgColor(255 - rd.getgColor());
					rd.setbColor(255 - rd.getbColor());
				}
				sortAndDisplay();
				update();
			}
		});
		buttonPanel.add(negative);
	
		final JCheckBox hsbCheckBox = new JCheckBox("HSB lerp");
		hsbCheckBox.setBounds(206, 32, 100, 24);
		
		hsbCheckBox.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				useHsbLerp = hsbCheckBox.getModel().isSelected();
				sortAndDisplay();
				update();				
			}});
		buttonPanel.add(hsbCheckBox);
		
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 6;
		c.insets = new Insets(4, 0, 4, 0);
		add(buttonPanel, c);
		c.gridwidth = 1;
		
		// Rows for the color pegs

		colorPad = new JLabel[ROWS];
		locationVal = new JTextField[ROWS];
		redVal = new JTextField[ROWS];
		greenVal = new JTextField[ROWS];
		blueVal = new JTextField[ROWS];
		delButton = new JButton[ROWS];
		
		colorBarPegs = colorAxis.getColorBarPegs();

		c.insets =  new Insets(2, 1, 1, 1);
		c.fill = GridBagConstraints.BOTH;
		
		for (int i = 0; i < ROWS; i++)
		{
			c.ipady = 6; // intention: size to match DelButton
			
			locationVal[i] = new JTextField();
			locationVal[i].setHorizontalAlignment(JTextField.RIGHT);
			locationVal[i].setColumns(3);
			locationVal[i].setActionCommand("l:" + i);
			locationVal[i].addActionListener(this);
			c.gridx = 0;
			c.gridy = i + 2;
			add(locationVal[i], c);
			
			colorPad[i] = new JLabel("          " + i);
			colorPad[i].setFont(fixedFiller);
			colorPad[i].setBorder(blackBorder);
			colorPad[i].addMouseListener(new MouseListener(){

				@Override
				public void mouseClicked(MouseEvent arg0)
				{}

				@Override
				public void mouseEntered(MouseEvent arg0)
				{}

				@Override
				public void mouseExited(MouseEvent arg0)
				{}

				@Override
				public void mousePressed(MouseEvent arg0)
				{
					String s = ((JLabel)arg0.getSource()).getText();
					int cpIdx = Integer.valueOf(s.trim());
					recolorPeg(cpIdx);
				}

				@Override
				public void mouseReleased(MouseEvent arg0)
				{}
				});
			
			c.gridx = 1;
			add(colorPad[i], c );
			
			redVal[i] = new JTextField();
			redVal[i].setHorizontalAlignment(JTextField.RIGHT);
			redVal[i].setColumns(3);
			redVal[i].setActionCommand("r:" + i);
			redVal[i].addActionListener(this);
			c.gridx = 2;
			add(redVal[i], c);
			
			greenVal[i] = new JTextField();
			greenVal[i].setHorizontalAlignment(JTextField.RIGHT);
			greenVal[i].setColumns(3);
			greenVal[i].setActionCommand("g:" + i);
			greenVal[i].addActionListener(this);
			c.gridx = 3;
			add(greenVal[i], c);
			
			blueVal[i] = new JTextField();
			blueVal[i].setHorizontalAlignment(JTextField.RIGHT);
			blueVal[i].setColumns(3);
			blueVal[i].setActionCommand("b:" + i);
			blueVal[i].addActionListener(this);
			c.gridx = 4;

			add(blueVal[i], c);

			c.ipady = 0;

			delButton[i] = new JButton("Del");
			delButton[i].setFont(smallerButton);
			delButton[i].setActionCommand("d:" + i);
			delButton[i].addActionListener(this);
			c.gridx = 5;
			add(delButton[i], c);
		}
		
		// Last row, a spacer.
		//TODO: use a better spacer, smaller size too
		c.gridx = 0;
		c.gridy = ROWS + 2;
		c.weighty = 1.0;
		add(new JLabel(" "), c);

		sortAndDisplay();	

	}

		
	private void recolorPeg(int cpIdx)
	{
		ColorBarPeg cbPeg = colorBarPegs.get(cpIdx);
		Color pickedColor = new Color(
				cbPeg.getrColor(),
				cbPeg.getgColor(),
				cbPeg.getbColor(),
				255);
				
		if (colorThePeg(cbPeg, pickedColor))
		{
			sortAndDisplay();
			update();
		}
		
	}
	
	private void addNewPeg(int newPegLocation)
	{
		if (colorBarPegs.size() < ROWS)
		{
			int argb = colorAxis.getColorMap().get(newPegLocation);
			ColorBarPeg cbPeg = new ColorBarPeg(
					newPegLocation, 0, 0, 0, 255);
			if (colorThePeg(cbPeg, new Color(argb)))
			{
				colorBarPegs.add(cbPeg);
				sortAndDisplay();
				update();
			}
		}
	}
	
	private boolean colorThePeg(ColorBarPeg peg, Color color)
	{
		Color newColor = JColorChooser.showDialog(this, 
				"Pick a color", color);
		if (newColor == null) 
		{
			return false;
		}
		else
		{
			peg.setrColor(newColor.getRed());
			peg.setgColor(newColor.getGreen());
			peg.setbColor(newColor.getBlue());
		}
		return true;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) 
	{		
		String[] cmd = arg0.getActionCommand().split(":");
		
		// delButtons pack index as ActionCommand
		if (cmd[0].equals("d"))
		{
			int idx = Integer.valueOf(cmd[1]);
			colorBarPegs.remove(idx);
		}
		
		if (cmd[0].equals("l"))
		{
			int idx = Integer.valueOf(cmd[1]);
			colorBarPegs.get(idx).setLocation(
					Integer.valueOf(locationVal[idx].getText()));
		}

		if (cmd[0].equals("r"))
		{
			int idx = Integer.valueOf(cmd[1]);
			colorBarPegs.get(idx).setrColor(
					Integer.valueOf(redVal[idx].getText()));
		}

		if (cmd[0].equals("g"))
		{
			int idx = Integer.valueOf(cmd[1]);
			colorBarPegs.get(idx).setgColor(
					Integer.valueOf(greenVal[idx].getText()));
		}

		if (cmd[0].equals("b"))
		{
			int idx = Integer.valueOf(cmd[1]);
			colorBarPegs.get(idx).setbColor(
					Integer.valueOf(blueVal[idx].getText()));
		}

		sortAndDisplay();
		update();
	}
	
	public void sortAndDisplay()
	{
		Collections.sort(colorBarPegs, new ColorBarPegSort());
		
		for (int index = 0; index < ROWS; index++)
		{
			if (index < colorBarPegs.size())
			{
				locationVal[index].setText(
						String.valueOf(
								colorBarPegs.get(index).getLocation()));
				locationVal[index].setVisible(true);
				Color color = new Color( 
							colorBarPegs.get(index).getrColor(),
							colorBarPegs.get(index).getgColor(),
							colorBarPegs.get(index).getbColor(),
							colorBarPegs.get(index).getAlpha()
						);
				colorPad[index].setBackground(color);
				colorPad[index].setForeground(color);
				colorPad[index].setVisible(true);
				colorPad[index].setOpaque(true);
				redVal[index].setText(
						String.valueOf(colorBarPegs.get(index).getrColor()));
				redVal[index].setVisible(true);
				greenVal[index].setText(
						String.valueOf(colorBarPegs.get(index).getgColor()));
				greenVal[index].setVisible(true);
				blueVal[index].setText(
						String.valueOf(colorBarPegs.get(index).getbColor()));
				blueVal[index].setVisible(true);
				if (colorBarPegs.get(index).getLocation() == 0 ||
						colorBarPegs.get(index).getLocation() == 255)
				{
					delButton[index].setVisible(false);
				}
				else
				{
					delButton[index].setVisible(true);
				}	
			}
			else
			{
				locationVal[index].setVisible(false);
				colorPad[index].setVisible(false);
				redVal[index].setVisible(false);
				greenVal[index].setVisible(false);
				blueVal[index].setVisible(false);
				delButton[index].setVisible(false);
			}
		}
		repaint();
	}
	
	private void update()
	{
		colorAxis.setUseHsbLerp(useHsbLerp);
		colorAxis.update();
		
		ColorMapSelectorGUI.topPanel.updateOctaveDisplays();
	}

}

