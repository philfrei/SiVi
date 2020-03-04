package com.adonax.sivi;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.adonax.sivi.gradients.GradientGUI;
import com.adonax.sivi.gradients.GradientGUIModel;

public class MixerGUI extends JPanel
{
	private static final long serialVersionUID = 1L;

	public static TopPanel topPanel;
	
	private JSlider[] weightSlider;
	private JTextField[] weightTextField;
	private JSlider masterSlider;
	private JTextField masterTextField;
	private volatile MixerModel mixerModel;
	final DecimalFormat decimals2formatter = new DecimalFormat("#.##");
	
	private GradientGUI gradientGUI;
	public GradientGUI getGradientGUI()
	{
		return gradientGUI;
	}

	public MixerModel getMixerModel() 
	{
		return mixerModel;
	}
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
		
		int heightForGUI = 150 + channels * 25;
		setPreferredSize(new Dimension(270, heightForGUI));
		setMaximumSize(new Dimension(380, heightForGUI));
		setAlignmentY(Component.TOP_ALIGNMENT);
		
//		Border debugBorder  = BorderFactory.createDashedBorder(Color.red);
//		setBorder(debugBorder);
		
		weightSlider = new JSlider[channels];
		weightTextField = new JTextField[channels];
		
		setLayout(new GridBagLayout());
		GridBagConstraints gbConstraints = new GridBagConstraints();
		gbConstraints.anchor = GridBagConstraints.LINE_START;

		gradientGUI = new GradientGUI(this, ggm);
//		gradientGUI.setMinimumSize(new Dimension(300,100));
		Border gradientGUIBorder = BorderFactory.createRaisedBevelBorder();
		TitledBorder combinedGradientGUITitledBorder = 
				BorderFactory.createTitledBorder(gradientGUIBorder,
						"Modulate a gradient function");
		gradientGUI.setBorder(combinedGradientGUITitledBorder);
		gbConstraints.fill = GridBagConstraints.HORIZONTAL;
		gbConstraints.gridx = 0;
		gbConstraints.gridy = 1;
		gbConstraints.gridwidth = 3;
		add(gradientGUI, gbConstraints);
		gbConstraints.gridwidth = 1;	// restore default
		gbConstraints.fill = GridBagConstraints.NONE; // restores default
		
		JPanel mixPanel = new JPanel();
		Border border = BorderFactory.createRaisedBevelBorder();
		TitledBorder combineTitledBorder = 
				BorderFactory.createTitledBorder(border, "Mix");
		mixPanel.setBorder(combineTitledBorder);
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
		
		/*
		 * Master slider, 0 to 100, representing 0 to 4
		 */
		masterSlider = new JSlider(0, 100, (int)(mixerModel.master * 25));
		masterTextField = new JTextField();
		masterTextField.setColumns(3);
		masterTextField.setText(decimals2formatter.format(mixerModel.master));
		masterSlider.addChangeListener(new ChangeListener()
		{
			@Override
			public void stateChanged(ChangeEvent arg0)
			{
				float masterValue = masterSlider.getValue() * 0.04f;
				masterTextField.setText(decimals2formatter.format(masterValue));
				mixerModel = MixerModel.updateMixSetting(mixerModel, 
						MixerModel.Fields.MASTER, masterValue);
				topPanel.remix();
			}
		});
		masterTextField.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				int sliderVal = (int)((Float.valueOf(
						masterTextField.getText()) * 25));
				sliderVal = Math.max(0, Math.min(100, sliderVal));
				masterSlider.setValue(sliderVal);
				masterTextField.setText(
						decimals2formatter.format(masterSlider.getValue() * 0.04f));
			}
		});
		gbConstraints.gridx = 1;
		mixPanel.add(masterSlider, gbConstraints);
		gbConstraints.gridx = 2;
		mixPanel.add(masterTextField,  gbConstraints);
		
		gbConstraints.gridx = 0;
		gbConstraints.gridy = 0;
		gbConstraints.gridwidth = 3;
		add(mixPanel, gbConstraints);
		gbConstraints.gridwidth = 1;
	}
	
	/*
	 * 100 increments, 0 to 100, representing 0 to 1 weight.
	 */
	private JSlider makeWeightingSlider(final int idx) 
	{
		final JSlider weightSlider = new JSlider(0, 100, 
				(int)(mixerModel.weights[idx] * 100));
		weightSlider.addChangeListener(new ChangeListener(){

			@Override
			public void stateChanged(ChangeEvent arg0) 
			{
				int weight = weightSlider.getValue();
				weightTextField[idx].setText(
						decimals2formatter.format(weight / 100f));
				updateModelWeights();
				topPanel.remix();
			}
		});

		return weightSlider;
	}
	
	private JTextField makeTextField(final int idx)
	{
		JTextField tf = new JTextField();
		tf.setColumns(3);
		tf.setText(decimals2formatter.format(mixerModel.weights[idx]));
		tf.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				weightSlider[idx].setValue(
						(int)((Float.valueOf(weightTextField[idx].getText())
								* 100)));
				
				weightTextField[idx].setText(
						decimals2formatter.format(
								weightSlider[idx].getValue()/ 100f));
			}
		});
	
		return tf;		
	}

	private void updateModelWeights()
	{
		mixerModel = MixerModel.updateMixSetting(mixerModel, 
				MixerModel.Fields.WEIGHTS, getWeights());
	}
	
	private float[] getWeights()
	{
		int channels = topPanel.getAppSettings().octaves;
		float[] weights = new float[channels];
		for (int i = 0; i < channels; i++)
		{
			weights[i] = weightSlider[i].getValue();
			weights[i] /= 100f;
			weights[i] *= (masterSlider.getValue()/25f);
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
