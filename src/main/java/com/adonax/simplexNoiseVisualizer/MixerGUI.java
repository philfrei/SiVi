package com.adonax.simplexNoiseVisualizer;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.adonax.simplexNoiseVisualizer.gradients.GradientGUI;
import com.adonax.simplexNoiseVisualizer.gradients.GradientGUIModel;

public class MixerGUI extends JPanel
{
	private static final long serialVersionUID = 1L;

	public static TopPanel topPanel;
	
	private JSlider[] weightSlider;
	private JTextField[] weightTextField;
	private JSlider masterSlider;
	private volatile MixerModel mixerModel;
	
	private ButtonGroup colorMappingOptions;
	private JRadioButton clampOption, ringOption;
	
	private GradientGUI gradientGUI;
	public GradientGUI getGradientGUI()
	{
		return gradientGUI;
	}

	public MixerModel getMixerModel() {return mixerModel;}
	public void setMixerModel(MixerModel mixerModel)
	{
		this.mixerModel = mixerModel;
	}
	
	public MixerGUI(final TopPanel topPanel, MixerModel mm)
	{
		this(topPanel, mm, new GradientGUIModel());
	}
	
	public MixerGUI(final TopPanel topPanel, MixerModel mm, 
			GradientGUIModel ggm)
	{
		MixerGUI.topPanel = topPanel;
		this.mixerModel = mm;
		
		int channels = topPanel.getAppSettings().octaves;
		
		weightSlider = new JSlider[channels];
		weightTextField = new JTextField[channels];
		
		setLayout(new GridBagLayout());
		GridBagConstraints gbConstraints = new GridBagConstraints();
		gbConstraints.anchor = GridBagConstraints.LINE_START;

		gradientGUI = new GradientGUI(this, ggm);
		gradientGUI.setMinimumSize(new Dimension(300,100));
		gbConstraints.gridx = 0;
		gbConstraints.gridy = 0;
		gbConstraints.gridwidth = 3;
		add(gradientGUI, gbConstraints);
		gbConstraints.gridwidth = 1;
		
		JPanel mixPanel = new JPanel();
		TitledBorder mixBorder = 
				BorderFactory.createTitledBorder("Mix");
		mixPanel.setBorder(mixBorder);
		mixPanel.setLayout(new GridBagLayout());
		// note: mm weights are pre-multiplied by master
		for (int i = 0; i < channels; i++)
		{
			weightSlider[i] = makeWeightingSlider(i);
			weightTextField[i] = makeTextField(i);
			
			int gbRow = i + 1;
			gbConstraints.gridx = 0;
			gbConstraints.gridy = gbRow;
			mixPanel.add(new JLabel(String.valueOf(i)), gbConstraints);
			
			gbConstraints.gridx = 1;
			mixPanel.add(weightSlider[i], gbConstraints);

			gbConstraints.gridx = 2;
			mixPanel.add(weightTextField[i], gbConstraints);
		}
		
		gbConstraints.gridx = 0;
		gbConstraints.gridy = channels + 1;
		mixPanel.add(new JLabel("All"), gbConstraints);
		
		masterSlider = new JSlider(0, 100, (int)(mixerModel.master * 25));
		gbConstraints.gridx = 1;
		masterSlider.addChangeListener(new ChangeListener()
		{
			@Override
			public void stateChanged(ChangeEvent arg0)
			{
				updateModelWeights();
				topPanel.remix();
			}
		});
		mixPanel.add(masterSlider, gbConstraints);
//		updateModelWeights(); //TODO: check if okay
		
		gbConstraints.gridx = 0;
		gbConstraints.gridy = 1;
		gbConstraints.gridwidth = 3;
		add(mixPanel, gbConstraints);
		gbConstraints.gridwidth = 1;
		
		colorMappingOptions = new ButtonGroup();
		clampOption = new JRadioButton("Clamp");
		ringOption = new JRadioButton("Ring");
		colorMappingOptions.add(clampOption);
		colorMappingOptions.add(ringOption);
		switch (mixerModel.mapping)
		{
		case CLAMP: clampOption.setSelected(true); break;
		case RING: ringOption.setSelected(true);
		}
		
		JPanel  colorMappingOptionPanel = new JPanel();
		colorMappingOptionPanel.setLayout(new FlowLayout());

		colorMappingOptionPanel.add(clampOption, BorderLayout.LINE_START);
		colorMappingOptionPanel.add(ringOption, BorderLayout.LINE_END);

		gbConstraints.gridx = 0;
		gbConstraints.gridy = 2;
		gbConstraints.fill = GridBagConstraints.HORIZONTAL;
		gbConstraints.gridwidth = 0;
		add(colorMappingOptionPanel, gbConstraints);
		gbConstraints.gridwidth = 1;

		clampOption.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				mixerModel = 
						MixerModel.updateMixSetting(mixerModel, 
						MixerModel.Fields.MAPPING, 
						MixerModel.MapMethod.CLAMP);
			
				topPanel.remix();
			}
		});
		
		ringOption.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				mixerModel = MixerModel.updateMixSetting(mixerModel, 
						MixerModel.Fields.MAPPING, 
						MixerModel.MapMethod.RING);
				
				topPanel.remix();
			}
		});
	}
	
	private JSlider makeWeightingSlider(final int idx) 
	{
//		final JSlider weightSlider = new JSlider(0, 100, 
//				(int)(mixerModel.weights[idx] * 100));
		final JSlider weightSlider = new JSlider(0, 100, 
				(int)((mixerModel.weights[idx] * 100)
						/ mixerModel.master));
		weightSlider.addChangeListener(new ChangeListener(){

			@Override
			public void stateChanged(ChangeEvent arg0) 
			{
				int weight = weightSlider.getValue();
				weightTextField[idx].setText(
						String.valueOf(weight));
				updateModelWeights();
				topPanel.remix();
			}
		});

		return weightSlider;
	}
	
	private JTextField makeTextField(final int idx)
	{
		JTextField tf = new JTextField();
		tf.setColumns(2);
//		tf.setText(String.valueOf((int)
//				(mixerModel.weights[idx] * 100)));
		tf.setText(String.valueOf((int)
				((mixerModel.weights[idx] * 100) / 
						mixerModel.master)));
		tf.addActionListener(new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				weightSlider[idx].setValue(Integer.valueOf(
						weightTextField[idx].getText()));
			}
		});
	
		return tf;		
	}

	private void updateModelWeights()
	{
		mixerModel = MixerModel.updateMixSetting(mixerModel, 
				MixerModel.Fields.WEIGHTS, getWeights());
		
		/* NOTE: if we include weights or gradients in 
		 * the OctaveGUI displays, we will need to call 
		 * TopPanel.update() instead of .remix(). But there 
		 * would be a significant performance cost.
		 */
//		topPanel.remix();
	}
	
	private float[] getWeights()
	{
		int channels = topPanel.getAppSettings().octaves;
		float[] weights = new float[channels];
		for (int i = 0; i < channels; i++)
		{
			weights[i] = weightSlider[i].getValue();
			weights[i] /= 100f;
			weights[i] *= (masterSlider.getValue()/25.f);
		}
		return weights;
	}

	public void updateGradientNoiseData(float[] gradientNoiseData)
	{
		NoiseData nd = new NoiseData(
				topPanel.getAppSettings().finalWidth,
				topPanel.getAppSettings().finalHeight,
				gradientNoiseData);
		mixerModel = new MixerModel(mixerModel.weights,
				mixerModel.master, mixerModel.mapping,	nd);
		topPanel.remix();
	}
}
