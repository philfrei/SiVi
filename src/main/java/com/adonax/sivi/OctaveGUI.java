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
package com.adonax.sivi;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.event.*;

import com.adonax.sivi.color.ColorMapSelectorGUI;

public class OctaveGUI extends JPanel
{
	private static final long serialVersionUID = 1L;

	private final TopPanel topPanel;
	
	private BufferedImage image;

	private int cols, rows;

	private volatile OctaveModel octaveModel;
	
	private JCheckBox scaleLock;
	private boolean scaleLocked;
	private float scaleRatio;
	private final JSlider xScaleSlider, yScaleSlider;
	private final JSlider xTranslateSlider, yTranslateSlider;
	private final JSlider minClampSlider, maxClampSlider;
	private final float SCALE_PRECISION = 8;
	private final float TRANSLATE_PRECISION = 4;
	private final float CLAMP_PRECISION = 1024;
	
	private JTextField xScaleVal;
	private JTextField yScaleVal;
	private JTextField xTranslationVal;
	private JTextField yTranslationVal;
	private JTextField minVal;
	private JTextField maxVal;
	
	
	// I/O, allows external source to set values
	// assumption: the ActionListener for each will update
	// the model.
	public void setXScaleVal(float val)
	{
		octaveModel = OctaveModel.setTextureParam(octaveModel,
				OctaveModel.Fields.XSCALE, val);
		xScaleVal.setText(String.valueOf(val));
		xScaleSlider.setValue(
				(int)Math.round(val * SCALE_PRECISION));
	}
	public void setYScaleVal(float val)
	{
		octaveModel = OctaveModel.setTextureParam(octaveModel,
				OctaveModel.Fields.YSCALE, val);
		yScaleVal.setText(String.valueOf(val));
		yScaleSlider.setValue(
				(int)Math.round(val * SCALE_PRECISION));
	}
	public void setXTranslationVal(float val)
	{
		octaveModel = OctaveModel.setTextureParam(octaveModel,
				OctaveModel.Fields.XTRANSLATE,
				val);
		xTranslationVal.setText(String.valueOf(val));
		xTranslateSlider.setValue(
				(int)Math.round(val * TRANSLATE_PRECISION));
	}
	public void setYTranslationVal(float val)
	{
		octaveModel = OctaveModel.setTextureParam(octaveModel,
				OctaveModel.Fields.YTRANSLATE,
				val);
		yTranslationVal.setText(String.valueOf(val));
		yTranslateSlider.setValue(
				(int)Math.round(val * TRANSLATE_PRECISION));
	}
	public void setMinVal(float val)
	{
		octaveModel = OctaveModel.setTextureParam(octaveModel,
				OctaveModel.Fields.MINCLAMP, val);
		minVal.setText(String.valueOf(val));
		minClampSlider.setValue((int)(val * 1024));
	}
	public void setMaxVal(float val)
	{
		octaveModel = OctaveModel.setTextureParam(octaveModel,
				OctaveModel.Fields.MAXCLAMP, val);
		maxVal.setText(String.valueOf(val));
		maxClampSlider.setValue((int)(val * 1024));
	}
	
	private ButtonGroup mappingOptions;
	private JRadioButton noMap, absMap, compress01Map;

	public void setMap(int i)
	{
		switch (i)
		{
		case 0:
			compress01Map.setSelected(true);
			break;
		case 1:
			absMap.setSelected(true);
			break;
		case 2:
			noMap.setSelected(true);
		}
	}
	
	OctaveGUI(TopPanel topPanel)
	{
		this(new OctaveModel(), topPanel);
	}
	
	OctaveGUI(OctaveModel om, TopPanel topPanel)
	{
		octaveModel = om;
		this.topPanel = topPanel;
		
		setLayout(new GridBagLayout());
		GridBagConstraints gbConstraints = new GridBagConstraints();

		Border border = new EtchedBorder();
		setBorder(border);
		
		JLabel xScaleLbl = new JLabel("X Scale");
		JLabel yScaleLbl = new JLabel("Y Scale");
		JLabel xTranslateLbl = new JLabel("X Trans");
		JLabel yTranslateLbl = new JLabel("Y Trans");
		JLabel minLbl = new JLabel("Min");
		JLabel maxLbl = new JLabel("Max");

		gbConstraints.anchor = GridBagConstraints.LINE_START;

		// all gridy values, we could move up by -1!!
		gbConstraints.gridx = 1;
		gbConstraints.gridy = 1;
		add(xScaleLbl, gbConstraints);

		gbConstraints.gridx = 1;
		gbConstraints.gridy = 2;
		add(yScaleLbl, gbConstraints);

		gbConstraints.gridx = 1;
		gbConstraints.gridy = 3;
		add(xTranslateLbl, gbConstraints);

		gbConstraints.gridx = 1;
		gbConstraints.gridy = 4;
		add(yTranslateLbl, gbConstraints);

		gbConstraints.gridx = 1;
		gbConstraints.gridy = 5;
		add(minLbl, gbConstraints);

		gbConstraints.gridx = 1;
		gbConstraints.gridy = 6;
		add(maxLbl, gbConstraints);

 		gbConstraints.anchor = GridBagConstraints.CENTER;

		scaleLock = new JCheckBox();
		scaleLock.setSelected(false);
		scaleLocked = false;

		gbConstraints.gridx = 0;
		gbConstraints.gridy = 1;
		gbConstraints.gridheight = 2;
		add(scaleLock, gbConstraints);
		gbConstraints.gridheight = 1;

		scaleLock.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				scaleRatio = octaveModel.xScale / 
						octaveModel.yScale;				
				scaleLocked = scaleLock.getModel().isSelected();
			}});
		
		xScaleSlider = new JSlider(1, 1024, 
				(int)(om.xScale * SCALE_PRECISION));
		yScaleSlider = new JSlider(1, 1024, 
				(int)(om.yScale * SCALE_PRECISION));
		xTranslateSlider = 	new	JSlider(-1024, 1024, 
				(int)(om.xTranslate * TRANSLATE_PRECISION));
		yTranslateSlider = new JSlider(-1024, 1024, 
				(int)(om.yTranslate * TRANSLATE_PRECISION));
		minClampSlider = new JSlider(-1024, 1024, 
				(int)(om.minClamp * CLAMP_PRECISION));
		maxClampSlider = new JSlider(-1024, 1024, 
				(int)(om.maxClamp * CLAMP_PRECISION));

		int sliderWidth = 110;
		int sliderHeight = xScaleSlider.getPreferredSize().height;
		Dimension sliderDim = new Dimension(sliderWidth, sliderHeight);

		gbConstraints.gridx = 2;
		gbConstraints.gridy = 1;
		xScaleSlider.setPreferredSize(sliderDim);
		add(xScaleSlider, gbConstraints);

		gbConstraints.gridx = 2;
		gbConstraints.gridy = 2;
		yScaleSlider.setPreferredSize(sliderDim);
		add(yScaleSlider, gbConstraints);

		gbConstraints.gridx = 2;
		gbConstraints.gridy = 3;
		xTranslateSlider.setPreferredSize(sliderDim);
		add(xTranslateSlider, gbConstraints);

		gbConstraints.gridx = 2;
		gbConstraints.gridy = 4;
		yTranslateSlider.setPreferredSize(sliderDim);
		add(yTranslateSlider, gbConstraints);

		gbConstraints.gridx = 2;
		gbConstraints.gridy = 5;
		minClampSlider.setPreferredSize(sliderDim);
		add(minClampSlider, gbConstraints);

		gbConstraints.gridx = 2;
		gbConstraints.gridy = 6;
		maxClampSlider.setPreferredSize(sliderDim);
		add(maxClampSlider, gbConstraints);
		
		xScaleSlider.addChangeListener(new ChangeListener(){

			@Override
			public void stateChanged(ChangeEvent e)
			{
				octaveModel = OctaveModel.setTextureParam(octaveModel,
						OctaveModel.Fields.XSCALE, 
						xScaleSlider.getValue() / SCALE_PRECISION);

				xScaleVal.setText(String.valueOf(octaveModel.xScale));
				if (scaleLocked)
				{
					scaleLocked = false;
					octaveModel = OctaveModel.setTextureParam(octaveModel,
							OctaveModel.Fields.YSCALE, 
							octaveModel.xScale / scaleRatio);
					yScaleVal.setText(String.valueOf(octaveModel.yScale));
					yScaleSlider.setValue(
							(int)Math.round(octaveModel.yScale * SCALE_PRECISION));
					scaleLocked = true;
				}
				update();
				remix();
			}
		});
		
		yScaleSlider.addChangeListener(new ChangeListener(){

			@Override
			public void stateChanged(ChangeEvent e) 
			{
				octaveModel = OctaveModel.setTextureParam(octaveModel,
						OctaveModel.Fields.YSCALE,
						yScaleSlider.getValue() / SCALE_PRECISION);
				yScaleVal.setText(
						String.valueOf(octaveModel.yScale));
				if (scaleLocked)
				{
					scaleLocked = false;
					octaveModel = OctaveModel.setTextureParam(octaveModel,
							OctaveModel.Fields.XSCALE,
							octaveModel.yScale * scaleRatio);
							
					xScaleVal.setText(String.valueOf(octaveModel.xScale));
					xScaleSlider.setValue((int)Math.round(
							octaveModel.xScale * SCALE_PRECISION));
					scaleLocked = true;
				}

				update();
				remix();
			}
		});
		
		xTranslateSlider.addChangeListener(new ChangeListener()
		{
			@Override
			public void stateChanged(ChangeEvent e) {
				octaveModel = OctaveModel.setTextureParam(octaveModel,
						OctaveModel.Fields.XTRANSLATE,
						xTranslateSlider.getValue() / TRANSLATE_PRECISION);
				xTranslationVal.setText(
						String.valueOf(octaveModel.xTranslate));
				update();
				remix();
			}
		});

		yTranslateSlider.addChangeListener(new ChangeListener()
		{
			@Override
			public void stateChanged(ChangeEvent e) 
			{
				octaveModel = OctaveModel.setTextureParam(octaveModel,
						OctaveModel.Fields.YTRANSLATE, 
						yTranslateSlider.getValue() / TRANSLATE_PRECISION);
				yTranslationVal.setText(
						String.valueOf(octaveModel.yTranslate));
				update();
				remix();
			}
		});

		minClampSlider.addChangeListener(new ChangeListener()
		{
			@Override
			public void stateChanged(ChangeEvent e) 
			{
				octaveModel = OctaveModel.setTextureParam(octaveModel,
						OctaveModel.Fields.MINCLAMP,
						minClampSlider.getValue() / CLAMP_PRECISION);
				minVal.setText(
						String.valueOf(octaveModel.minClamp));
				update();
				remix();
			}
		});

		maxClampSlider.addChangeListener(new ChangeListener()
		{
			@Override
			public void stateChanged(ChangeEvent e) 
			{
				octaveModel = OctaveModel.setTextureParam(octaveModel,
						OctaveModel.Fields.MAXCLAMP,
						maxClampSlider.getValue() / CLAMP_PRECISION);
				maxVal.setText(
						String.valueOf(octaveModel.maxClamp));
				update();
				remix();
			}
		});
		
		
		xScaleVal = new JTextField(String.valueOf(octaveModel.xScale));
		yScaleVal = new JTextField(String.valueOf(octaveModel.yScale));
		xTranslationVal = new JTextField(
				String.valueOf(octaveModel.xTranslate));
		yTranslationVal = new JTextField(
				String.valueOf(octaveModel.yTranslate));
		minVal = new JTextField(
				String.valueOf(octaveModel.minClamp));
		maxVal = new JTextField(
				String.valueOf(octaveModel.maxClamp));

		gbConstraints.gridx = 3;
		gbConstraints.gridy = 1;
		xScaleVal.setColumns(4);
		add(xScaleVal, gbConstraints);

		gbConstraints.gridx = 3;
		gbConstraints.gridy = 2;
		yScaleVal.setColumns(4);
		add(yScaleVal, gbConstraints);

		gbConstraints.gridx = 3;
		gbConstraints.gridy = 3;
		xTranslationVal.setColumns(4);
		add(xTranslationVal, gbConstraints);

		gbConstraints.gridx = 3;
		gbConstraints.gridy = 4;
		yTranslationVal.setColumns(4);
		add(yTranslationVal, gbConstraints);

		gbConstraints.gridx = 3;
		gbConstraints.gridy = 5;
		minVal.setColumns(4);
		add(minVal, gbConstraints);

		gbConstraints.gridx = 3;
		gbConstraints.gridy = 6;
		maxVal.setColumns(4);
		add(maxVal, gbConstraints);
		
		xScaleVal.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				xScaleSlider.setValue(
						(int)(Float.valueOf(xScaleVal.getText())
								* SCALE_PRECISION));
			}
		});
		
		yScaleVal.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				yScaleSlider.setValue((int)(Float.valueOf(
						yScaleVal.getText()) * SCALE_PRECISION));
			}
		});		
		
		xTranslationVal.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				xTranslateSlider.setValue((int)(Float.valueOf(
						xTranslationVal.getText()) * TRANSLATE_PRECISION));
			}
		});
		
		yTranslationVal.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				yTranslateSlider.setValue((int)(Float.valueOf(
						yTranslationVal.getText()) * TRANSLATE_PRECISION));
			}
		});

		minVal.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				minClampSlider.setValue((int)(Double.valueOf(
						minVal.getText()) * CLAMP_PRECISION));
			}
		});

		maxVal.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				maxClampSlider.setValue((int)(Double.valueOf(
						maxVal.getText()) * CLAMP_PRECISION));
			}
		});
		
		mappingOptions = new ButtonGroup();
		noMap = new JRadioButton("none");
		absMap = new JRadioButton("|v|");
		compress01Map = new JRadioButton("(v+1)/2");

		switch(om.normalize)
		{
		case SMOOTH: compress01Map.setSelected(true); break; 
		case ABS: absMap.setSelected(true); break;
		case NONE: noMap.setSelected(true); break;
		}
		
		mappingOptions.add(noMap);
		mappingOptions.add(absMap);
		mappingOptions.add(compress01Map);
		compress01Map.setBounds(2, 224, 74, 24);
		absMap.setBounds(78, 224, 54, 24);
		noMap.setBounds(134, 224, 54, 24);

		JPanel radioPanel = new JPanel();
		radioPanel.setLayout(new FlowLayout());
		radioPanel.add(compress01Map);
		radioPanel.add(absMap);
		radioPanel.add(noMap);

		gbConstraints.gridx = 0;
		gbConstraints.gridy = 7;
		gbConstraints.gridwidth = 4;
		add(radioPanel, gbConstraints);
		gbConstraints.gridwidth = 1;


		absMap.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				octaveModel = OctaveModel.setTextureParam(octaveModel,
						OctaveModel.Fields.NORMALIZE,
						OctaveModel.NoiseNormalization.ABS);
				update();
				remix();
			}
		});
		
		compress01Map.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				octaveModel = OctaveModel.setTextureParam(octaveModel,
						OctaveModel.Fields.NORMALIZE,
						OctaveModel.NoiseNormalization.SMOOTH);
				update();
				remix();
			}
		});
		
		noMap.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// NOTE: noMap data range undefined for ColorMap
				octaveModel = OctaveModel.setTextureParam(octaveModel,
						OctaveModel.Fields.NORMALIZE,
						OctaveModel.NoiseNormalization.NONE);
				update();
				remix();
			}
		});

		// image building variables, initializations
		cols = TopPanel.XPIXELS;
		rows = TopPanel.YPIXELS;
		
		image = new BufferedImage(cols, rows, BufferedImage.TYPE_INT_ARGB);

		JPanel imagePanel = new JPanel() {

			private static final long serialVersionUID = 1L;

			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(image, 0, 0, null);
			}

			@Override
			public Dimension getPreferredSize() {
				return new Dimension(cols, rows);
			}
		};

		gbConstraints.gridx = 0;
		gbConstraints.gridy = 8;
		gbConstraints.gridwidth = 4;
		gbConstraints.anchor = GridBagConstraints.LINE_START;
		add(imagePanel, gbConstraints);
		gbConstraints.gridwidth = 1;
		gbConstraints.anchor = GridBagConstraints.CENTER;
	}
	
	private void remix()
	{
		topPanel.remix();
	}
	
	public void update()
	{
		NoiseData noiseData = TextureFunctions.makeNoiseDataArray(
				cols, rows, octaveModel);
		
		image = TextureFunctions.makeImage(
				noiseData, 
				topPanel.mixerGUI.getMixerModel(), 
				ColorMapSelectorGUI.getColorMap());
		
		repaint();

	}

	public OctaveModel getOctaveModel() {
		return octaveModel;
	}
}
