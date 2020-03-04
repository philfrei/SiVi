package com.adonax.sivi;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import com.adonax.sivi.color.ColorMapSelectorGUI;

public class ColorMapGUI extends JPanel {

	private static final long serialVersionUID = 1L;

	public ColorMapGUI(ColorMapPreprocessingGUI colorMapPreprocessingGUI,
			ColorMapSelectorGUI colorMapSelectorGUI)
	{
		setAlignmentY(Component.TOP_ALIGNMENT);
		
		Border border = BorderFactory.createRaisedBevelBorder();
		TitledBorder tbPreprocessingGUI = 
				BorderFactory.createTitledBorder(border,
						"Color Map Preproccesing");
		colorMapPreprocessingGUI.setBorder(tbPreprocessingGUI);
		colorMapPreprocessingGUI.setPreferredSize(new Dimension(300, 60));
		colorMapPreprocessingGUI.setMaximumSize(new Dimension(400, 60));		
		
		TitledBorder tbSelectorGUI = 
				BorderFactory.createTitledBorder(border,
						"Color Map Selector");
		colorMapSelectorGUI.setBorder(tbSelectorGUI);
		colorMapSelectorGUI.setPreferredSize(new Dimension(300, 204));
		colorMapSelectorGUI.setMaximumSize(new Dimension(400, 204));
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		add(colorMapPreprocessingGUI);
		add(colorMapSelectorGUI);
	}	
}
