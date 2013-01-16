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
import java.awt.image.*;

import javax.swing.*;
import javax.swing.event.*;

public class SimplexTextureSource extends JPanel
{
	private static final long serialVersionUID = 1L;

	private BufferedImage image;

	private int cols, rows;

	private volatile TextureParams textureParams;
	
	private JCheckBox scaleLock;
	private boolean scaleLocked;
	private float scaleRatio;
	private final JSlider xScaleSlider, yScaleSlider;
	private final JSlider xTranslateSlider, yTranslateSlider;
	private final JSlider minClampSlider, maxClampSlider;
	private final float PRECISION = 8;
	
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
		textureParams = setTextureParam(
				TextureParams.Fields.XSCALE, val);
		xScaleVal.setText(String.valueOf(val));
		xScaleSlider.setValue((int)Math.round(val)*8);
	}
	public void setYScaleVal(float val)
	{
		textureParams = setTextureParam(
				TextureParams.Fields.YSCALE, val);
		yScaleVal.setText(String.valueOf(val));
		yScaleSlider.setValue((int)Math.round(val)*8);
	}
	public void setXTranslationVal(float val)
	{
		textureParams = setTextureParam(
				TextureParams.Fields.XTRANSLATE,
				val);
		xTranslationVal.setText(String.valueOf(val));
		xTranslateSlider.setValue((int)Math.round(val)*4);
	}
	public void setYTranslationVal(float val)
	{
		textureParams = setTextureParam(
				TextureParams.Fields.YTRANSLATE,
				val);
		yTranslationVal.setText(String.valueOf(val));
		yTranslateSlider.setValue((int)Math.round(val)*4);
	}
	public void setMinVal(float val)
	{
		textureParams = setTextureParam(
				TextureParams.Fields.MINCLAMP, val);
		minVal.setText(String.valueOf(val));
		minClampSlider.setValue((int)(val * 1024));
	}
	public void setMaxVal(float val)
	{
		textureParams = setTextureParam(
				TextureParams.Fields.MAXCLAMP, val);
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
		
	private ColorAxis colorAxis;  
	private BufferedImage colorMapImg; 
	public void setColorAxis(ColorAxis colorAxis)
	{
		this.colorAxis = colorAxis;
		updateColorAxis();
	}
	
	private void updateColorAxis()
	{
		textureParams = setTextureParam(
				TextureParams.Fields.COLORMAP,
				new ColorMap(colorAxis.data));

		colorMapImg = colorAxis.img;
	}
	
	private final STBPanel host;
	
	SimplexTextureSource(final int left, final int top, int width, int height, 
			ColorAxis colorAxis, final STBPanel host)
	{
		this.host = host;
		this.colorAxis = colorAxis;
		colorMapImg = colorAxis.img;
		this.textureParams = new TextureParams(1f, 1f, 0f, 0f, 
				-1f, 1f, 
				TextureParams.NoiseNormalization.SMOOTH, 
				new ColorMap(colorAxis.data));
		
		setLayout(new GridBagLayout());
		GridBagConstraints gbConstraints = new GridBagConstraints();

		JLabel xScaleLbl = new JLabel("X Scale");
		JLabel yScaleLbl = new JLabel("Y Scale");
		JLabel xTranslateLbl = new JLabel("X Trans");
		JLabel yTranslateLbl = new JLabel("Y Trans");
		JLabel minLbl = new JLabel("Min");
		JLabel maxLbl = new JLabel("Max");

		gbConstraints.anchor = GridBagConstraints.LINE_START;

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
				scaleRatio = textureParams.xScale / 
						textureParams.yScale;				
				scaleLocked = scaleLock.getModel().isSelected();
			}});
		
		xScaleSlider = new JSlider(1, 1024, 8);
		yScaleSlider = new JSlider(1, 1024, 8);
		xTranslateSlider = new JSlider(-1024, 1024, 0);
		yTranslateSlider = new JSlider(-1024, 1024, 0);
		minClampSlider = new JSlider(-1024, 1024, -1024);
		maxClampSlider = new JSlider(-1024, 1024, 1024);

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
				textureParams = setTextureParam(
						TextureParams.Fields.XSCALE, 
						xScaleSlider.getValue() / PRECISION);

				xScaleVal.setText(String.valueOf(textureParams.xScale));
				if (scaleLocked)
				{
					scaleLocked = false;
					textureParams = setTextureParam(
							TextureParams.Fields.YSCALE, 
							textureParams.xScale / scaleRatio);
					yScaleVal.setText(String.valueOf(textureParams.yScale));
					yScaleSlider.setValue(
							(int)Math.round(textureParams.yScale * PRECISION));
					scaleLocked = true;
				}
				update();
			}
		});
		
		yScaleSlider.addChangeListener(new ChangeListener(){

			@Override
			public void stateChanged(ChangeEvent e) 
			{
				textureParams = setTextureParam(
						TextureParams.Fields.YSCALE,
						yScaleSlider.getValue() / PRECISION);
				yScaleVal.setText(
						String.valueOf(textureParams.yScale));
				if (scaleLocked)
				{
					scaleLocked = false;
					textureParams = setTextureParam(
							TextureParams.Fields.XSCALE,
							textureParams.yScale * scaleRatio);
							
					xScaleVal.setText(String.valueOf(textureParams.xScale));
					xScaleSlider.setValue((int)Math.round(
							textureParams.xScale * PRECISION));
					scaleLocked = true;
				}

				update();
			}
		});
		
		xTranslateSlider.addChangeListener(new ChangeListener()
		{
			@Override
			public void stateChanged(ChangeEvent e) {
				textureParams = setTextureParam(
						TextureParams.Fields.XTRANSLATE,
						xTranslateSlider.getValue() / 4f);
				xTranslationVal.setText(
						String.valueOf(textureParams.xTranslate));
				update();
			}
		});

		yTranslateSlider.addChangeListener(new ChangeListener()
		{
			@Override
			public void stateChanged(ChangeEvent e) 
			{
				textureParams = setTextureParam(
						TextureParams.Fields.YTRANSLATE, 
						yTranslateSlider.getValue() / 4f);
				yTranslationVal.setText(
						String.valueOf(textureParams.yTranslate));
				update();
			}
		});

		minClampSlider.addChangeListener(new ChangeListener()
		{
			@Override
			public void stateChanged(ChangeEvent e) 
			{
				textureParams = setTextureParam(
						TextureParams.Fields.MINCLAMP,
						minClampSlider.getValue()/1024f);
				minVal.setText(
						String.valueOf(textureParams.minClamp));
				update();
			}
		});

		maxClampSlider.addChangeListener(new ChangeListener()
		{
			@Override
			public void stateChanged(ChangeEvent e) 
			{
				textureParams = setTextureParam(
						TextureParams.Fields.MAXCLAMP,
						maxClampSlider.getValue()/1024f);
				maxVal.setText(
						String.valueOf(textureParams.maxClamp));
				update();
			}
		});
		
		
		xScaleVal = new JTextField(String.valueOf(textureParams.xScale));
		yScaleVal = new JTextField(String.valueOf(textureParams.yScale));
		xTranslationVal = new JTextField(
				String.valueOf(textureParams.xTranslate));
		yTranslationVal = new JTextField(
				String.valueOf(textureParams.yTranslate));
		minVal = new JTextField(
				String.valueOf(textureParams.minClamp));
		maxVal = new JTextField(
				String.valueOf(textureParams.maxClamp));

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
				textureParams = setTextureParam(
						TextureParams.Fields.XSCALE,
						Float.valueOf(xScaleVal.getText()));
				xScaleSlider.setValue(
						(int)(textureParams.xScale * PRECISION));
				update();
			}
		});
		
		yScaleVal.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				textureParams = setTextureParam(
						TextureParams.Fields.YSCALE,
						Float.valueOf(yScaleVal.getText()));
				yScaleSlider.setValue(
						(int)(textureParams.yScale * PRECISION));
				update();
			}
		});		
		
		xTranslationVal.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				textureParams = setTextureParam(
						TextureParams.Fields.XTRANSLATE,
						Math.round(Double.valueOf(
						xTranslationVal.getText()) * 4));
				xTranslateSlider.setValue(
						(int)textureParams.xTranslate);
				update();
			}
		});
		
		yTranslationVal.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				textureParams = setTextureParam(
						TextureParams.Fields.YTRANSLATE,
						Math.round(Double.valueOf(
								yTranslationVal.getText()) * 4));
				yTranslateSlider.setValue(
						(int)textureParams.yTranslate);
				update();
			}
		});

		minVal.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				textureParams = setTextureParam(
						TextureParams.Fields.MINCLAMP,
						Math.round(Double.valueOf(
								minVal.getText()) * 1024.00));
				minClampSlider.setValue(
						(int)textureParams.minClamp);
				update();
			}
		});

		maxVal.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				textureParams = setTextureParam(
						TextureParams.Fields.MAXCLAMP,
						Math.round(Double.valueOf(
								maxVal.getText()) * 1024.00));
				maxClampSlider.setValue(
						(int)textureParams.maxClamp);
				update();
			}
		});
		
		mappingOptions = new ButtonGroup();
		noMap = new JRadioButton("none");
		absMap = new JRadioButton("|v|");
		compress01Map = new JRadioButton("(v+1)/2");
		compress01Map.setSelected(true); // default
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
				textureParams = setTextureParam(
						TextureParams.Fields.NORMALIZE,
						TextureParams.NoiseNormalization.ABS);
				update();
			}
		});
		
		compress01Map.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				textureParams = setTextureParam(
						TextureParams.Fields.NORMALIZE,
						TextureParams.NoiseNormalization.SMOOTH);
				update();
			}
		});
		
		noMap.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// NOTE: noMap data range undefined for ColorMap
				textureParams = setTextureParam(
						TextureParams.Fields.NORMALIZE,
						TextureParams.NoiseNormalization.NONE);
				update();
			}
		});

		JPanel colorAxisImagePanel = new JPanel() {

			private static final long serialVersionUID = 1L;

			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(colorMapImg, 0, 0, null);
			}

			@Override
			public Dimension getPreferredSize() {
				return new Dimension(
						colorMapImg.getWidth(), 
						colorMapImg.getHeight());
			}
		};
		colorAxisImagePanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent mouseEvent) {
				host.cbSelector.setBounds(left, top, 272, STBPanel.BARS * 32 + 34);
				host.cbSelector.setCallback(getSelf());
				host.cbSelector.setVisible(true);
				repaint();
			}
		});

        gbConstraints.gridx = 0;
		gbConstraints.gridy = 0;
		gbConstraints.gridwidth = 4;
		gbConstraints.anchor = GridBagConstraints.LINE_START;
		add(colorAxisImagePanel, gbConstraints);
		gbConstraints.gridwidth = 1;
		gbConstraints.anchor = GridBagConstraints.CENTER;

		
		// image building variables, initializations
		cols = 256;
		rows = 256;
		
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
	
	private SimplexTextureSource getSelf() {return this;}
	
	public void update()
	{
		updateColorAxis();

		TextureData data = 
				TextureFunctions.generate(
						cols, rows, textureParams);

		for (int j = 0;  j < data.height;  j++) {
			for (int i = 0;  i < data.width;  i++) {
				int idx = (int)(data.noiseArray[i][j] * 255);
				int pixel = 0;
				if (textureParams.normalize == 
						TextureParams.NoiseNormalization.SMOOTH ||
						textureParams.normalize == 
						TextureParams.NoiseNormalization.ABS) 
				{
					int argb = data.colorMap.data[idx];
					pixel = ColorAxis.calculateARGB(255,
							ColorAxis.getRed(argb),
							ColorAxis.getGreen(argb),
							ColorAxis.getBlue(argb));
				} else if (textureParams.normalize == TextureParams.NoiseNormalization.NONE) {
					pixel = ColorAxis.calculateARGB(255, idx, idx, idx);
				}

				image.setRGB(i, j, pixel);
			}
		}

		repaint();
		host.remix();
	}

	public TextureParams getTextureParams() {
		return textureParams;
	}

	public TextureParams setTextureParam(TextureParams.Fields field, 
			Object value) 
	{
		float xScale = textureParams.xScale;
		float yScale = textureParams.yScale;
		float xTranslate = textureParams.xTranslate;
		float yTranslate = textureParams.yTranslate;
		float minClamp = textureParams.minClamp;
		float maxClamp = textureParams.maxClamp;
		TextureParams.NoiseNormalization normalize 
				= textureParams.normalize;
		ColorMap colorMap = textureParams.colorMap;

		switch (field)
		{
		case XSCALE: xScale = (Float)value; break;
		case YSCALE: yScale = (Float)value; break;
		case XTRANSLATE: xTranslate = (Float)value; break;
		case YTRANSLATE: yTranslate = (Float)value; break;
		case MINCLAMP: minClamp = (Float)value; break;
		case MAXCLAMP: maxClamp = (Float)value; break;
		case NORMALIZE: normalize = 
				(TextureParams.NoiseNormalization)value; break;
		case COLORMAP: colorMap = (ColorMap)value; break;
		}
		
		return new TextureParams(
				xScale,
				yScale,
				xTranslate,
				yTranslate,
				minClamp,
				maxClamp,
				normalize,
				colorMap
		);
	}

}
