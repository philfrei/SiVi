package com.adonax.sivi;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class ColorMapPreprocessingGUI extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private ButtonGroup colorMappingOptions;
	private JRadioButton clampOption, ringOption;
	
	public ColorMapPreprocessingGUI(final TopPanel topPanel)
	{
		colorMappingOptions = new ButtonGroup();
		clampOption = new JRadioButton("Clamp");
		ringOption = new JRadioButton("Ring");
		colorMappingOptions.add(clampOption);
		colorMappingOptions.add(ringOption);
		switch (topPanel.mixerGUI.getMixerModel().mapping)
		{
			case CLAMP: clampOption.setSelected(true); break;
			case RING: ringOption.setSelected(true);
		}

		this.setLayout(new FlowLayout());
		this.add(clampOption, BorderLayout.LINE_START);
		this.add(ringOption, BorderLayout.LINE_END);
		
		clampOption.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				topPanel.mixerGUI.setMixerModel( 
						MixerModel.updateMixSetting(topPanel.mixerGUI.getMixerModel(), 
						MixerModel.Fields.MAPPING, 
						MixerModel.MapMethod.CLAMP));
//TODO				System.out.println("ColorMapPreprocessingGUI.clampOption, actionPerformed, "
//						+ "mixerGUI:" + topPanel.mixerGUI.hashCode());
				topPanel.remix();
			}
		});
		
		ringOption.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				topPanel.mixerGUI.setMixerModel(
						MixerModel.updateMixSetting(topPanel.mixerGUI.getMixerModel(), 
						MixerModel.Fields.MAPPING, 
						MixerModel.MapMethod.RING));
				topPanel.remix();
			}
		});	
	}
	
	public void updateColorMapPreprocessingGUI(MixerModel.MapMethod mapMethod)
	{
		// TODO test, is this going to cause some sort of circularity?
		switch (mapMethod)
		{
		case CLAMP: clampOption.setSelected(true);
			break;
		case RING: ringOption.setSelected(true);
		}
	}
}