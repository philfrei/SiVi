package com.adonax.simplexNoiseVisualizer.color;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.TitledBorder;

import com.adonax.simplexNoiseVisualizer.TopPanel;

/*
 * Design notes: based upon ColorBarSet in previous iteration. 
 * 1st column is a radio button. 
 * 2nd Second is a ColorBar.
 * Selection makes ColorMap of associated ColorBar available to 
 * rest of program.
 */
public class ColorMapSelectorGUI extends JPanel
{
	static private final long serialVersionUID = 1L;

	static public TopPanel topPanel;
	
	static private ColorBar[] colorBars;
	static private int selected;
	
	static private int bars;
	
	static private ButtonGroup colorMaps;
	static private JRadioButton[] radioButtons;
	private	GridBagConstraints gbConstraints;
	
	static public ColorMap getColorMap()
	{
		return colorBars[selected].getColorAxis().getColorMap();
	}
	
	public ColorAxis getColorAxis()
	{
		return colorBars[selected].getColorAxis();
	}
	
	public void setSelected(int selected, boolean doUpdate)
	{
		ColorMapSelectorGUI.selected = selected;
		for (int i = 0; i < bars; i++)
		{
			if (i == selected)
			{
				radioButtons[i].setSelected(true);
			}
			else
			{
				radioButtons[i].setSelected(false);
			}
		}

		if (doUpdate) topPanel.updateOctaveDisplays();
	}
	
	// required for updates/loads
	public void setColorAxis(int idx, ColorAxis colorAxis)
	{	
		ColorBar newColorBar = new ColorBar();
		newColorBar.setColorAxis(colorAxis);
		
		for (int i = 0; i < bars; i++)
		{
			remove(colorBars[i]);
		}
		colorBars[idx] = newColorBar;
		colorBars[idx].setSize(256, 16);
		gbConstraints.gridx = 1;
		
		for (int i = 0; i < bars; i++)
		{
			gbConstraints.gridy = i;
			add(colorBars[i], gbConstraints);
		}	
		revalidate();
	}
	
	
	public ColorMapSelectorGUI(final TopPanel topPanel)
	{
		ColorMapSelectorGUI.topPanel = topPanel;
		bars = topPanel.getAppSettings().colorMaps;
		
		TitledBorder combineTitledBorder = 
				BorderFactory.createTitledBorder("Select a Color Map");
		setBorder(combineTitledBorder);
//		Insets insets = 
//				combineTitledBorder.getBorderInsets(this);
		
		setLayout(new GridBagLayout());
		gbConstraints = new GridBagConstraints();
		gbConstraints.anchor = GridBagConstraints.LINE_START;

		colorBars = new ColorBar[bars];
		radioButtons = new JRadioButton[bars];
		colorMaps = new ButtonGroup();
			
		for (int i = 0; i < bars; i++)
		{
			radioButtons[i] = new JRadioButton();
			radioButtons[i].setActionCommand(String.valueOf(i));
			colorMaps.add(radioButtons[i]);
			
			radioButtons[i].addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent arg0)
				{
					selected = Integer.parseInt(arg0.getActionCommand());
					topPanel.updateOctaveDisplays();
				}
			});
			
			colorBars[i] = new ColorBar(); 
			colorBars[i].setSize(256, 16);
			
			gbConstraints.gridx = 0;
			gbConstraints.gridy = i;
			add(radioButtons[i], gbConstraints);
			
			gbConstraints.gridx = 1;
			add(colorBars[i], gbConstraints);
		}
		
		selected = 0;
		radioButtons[selected].setSelected(true);
	}
}
