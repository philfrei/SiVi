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
	private BufferedImage image;

	private int cols, rows;

	private float xScale;
	private float yScale;
	private float xTranslate;
	private float yTranslate;
	private float minClamp;
	private float maxClamp;
	
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
		xScale = val;
		xScaleVal.setText(String.valueOf(val));
		xScaleSlider.setValue((int)Math.round(val)*8);
	}
	public void setYScaleVal(float val)
	{
		yScale = val;
		yScaleVal.setText(String.valueOf(val));
		yScaleSlider.setValue((int)Math.round(val)*8);
	}
	public void setXTranslationVal(float val)
	{
		xTranslate = val;
		xTranslationVal.setText(String.valueOf(val));
		xTranslateSlider.setValue((int)Math.round(val)*4);
	}
	public void setYTranslationVal(float val)
	{
		yTranslate = val;
		yTranslationVal.setText(String.valueOf(val));
		yTranslateSlider.setValue((int)Math.round(val)*4);
	}
	public void setMinVal(float val)
	{
		minClamp = val;
		minVal.setText(String.valueOf(val));
		minClampSlider.setValue((int)(val * 1024));
	}
	public void setMaxVal(float val)
	{
		maxClamp = val;
		maxVal.setText(String.valueOf(val));
		maxClampSlider.setValue((int)(val * 1024));
	}
	
	
	private JTextField functionText;
	
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
	
	
	ColorAxis colorAxis;  // accessed from outside
	public void setColorAxis(ColorAxis colorAxis)
	{
		this.colorAxis = colorAxis;
	}
	
	
	private final STBPanel host;
	
	SimplexTextureSource(final int left, final int top, int width, int height, 
			ColorAxis colorAxis, final STBPanel host)
	{
		this.host = host;
		this.colorAxis = colorAxis;
		
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		JLabel xScaleLbl = new JLabel("X Scale");
		JLabel yScaleLbl = new JLabel("Y Scale");
		JLabel xTranslateLbl = new JLabel("X Trans");
		JLabel yTranslateLbl = new JLabel("Y Trans");
		JLabel minLbl = new JLabel("Min");
		JLabel maxLbl = new JLabel("Max");

		c.anchor = GridBagConstraints.LINE_START;

		c.gridx = 1;
		c.gridy = 1;
		add(xScaleLbl, c);

		c.gridx = 1;
		c.gridy = 2;
		add(yScaleLbl, c);

		c.gridx = 1;
		c.gridy = 3;
		add(xTranslateLbl, c);

		c.gridx = 1;
		c.gridy = 4;
		add(yTranslateLbl, c);

		c.gridx = 1;
		c.gridy = 5;
		add(minLbl, c);

		c.gridx = 1;
		c.gridy = 6;
		add(maxLbl, c);

 		c.anchor = GridBagConstraints.CENTER;

		scaleLock = new JCheckBox();
		scaleLock.setSelected(false);
		scaleLocked = false;

		c.gridx = 0;
		c.gridy = 1;
		c.gridheight = 2;
		add(scaleLock, c);
		c.gridheight = 1;

		scaleLock.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				scaleRatio = xScale / yScale;				
				scaleLocked = scaleLock.getModel().isSelected();
//				System.out.println("scaleRatio:" + scaleRatio
//						+ " locked:" + scaleLocked);
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

		c.gridx = 2;
		c.gridy = 1;
		xScaleSlider.setPreferredSize(sliderDim);
		add(xScaleSlider, c);

		c.gridx = 2;
		c.gridy = 2;
		yScaleSlider.setPreferredSize(sliderDim);
		add(yScaleSlider, c);

		c.gridx = 2;
		c.gridy = 3;
		xTranslateSlider.setPreferredSize(sliderDim);
		add(xTranslateSlider, c);

		c.gridx = 2;
		c.gridy = 4;
		yTranslateSlider.setPreferredSize(sliderDim);
		add(yTranslateSlider, c);

		c.gridx = 2;
		c.gridy = 5;
		minClampSlider.setPreferredSize(sliderDim);
		add(minClampSlider, c);

		c.gridx = 2;
		c.gridy = 6;
		maxClampSlider.setPreferredSize(sliderDim);
		add(maxClampSlider, c);
		
		xScaleSlider.addChangeListener(new ChangeListener(){

			@Override
			public void stateChanged(ChangeEvent e)
			{
				xScale = xScaleSlider.getValue() / PRECISION;
				xScaleVal.setText(String.valueOf(xScale));
				if (scaleLocked)
				{
					scaleLocked = false;
					yScale = xScale / scaleRatio;
					yScaleVal.setText(String.valueOf(yScale));
					yScaleSlider.setValue((int)Math.round(yScale * PRECISION));
					scaleLocked = true;
				}
				update();
			}
		});
		
		yScaleSlider.addChangeListener(new ChangeListener(){

			@Override
			public void stateChanged(ChangeEvent e) 
			{
//				System.out.println("ySlider, locked?:" + scaleLocked);
				yScale = yScaleSlider.getValue() / PRECISION;
				yScaleVal.setText(String.valueOf(yScale));
				if (scaleLocked)
				{
					scaleLocked = false;
					xScale = yScale * scaleRatio;
					xScaleVal.setText(String.valueOf(xScale));
					xScaleSlider.setValue((int)Math.round(xScale * PRECISION));
					scaleLocked = true;
				}

				update();
			}
		});
		
		xTranslateSlider.addChangeListener(new ChangeListener()
		{
			@Override
			public void stateChanged(ChangeEvent e) {
				xTranslate = xTranslateSlider.getValue() / 4f;
				xTranslationVal.setText(String.valueOf(xTranslate));
				update();
			}
		});

		yTranslateSlider.addChangeListener(new ChangeListener()
		{
			@Override
			public void stateChanged(ChangeEvent e) 
			{
				yTranslate = yTranslateSlider.getValue() / 4f;
				yTranslationVal.setText(String.valueOf(yTranslate));
				update();
			}
		});

		minClampSlider.addChangeListener(new ChangeListener()
		{
			@Override
			public void stateChanged(ChangeEvent e) 
			{
				minClamp = minClampSlider.getValue()/1024f;
				minVal.setText(String.valueOf(minClamp));
				update();
			}
		});

		maxClampSlider.addChangeListener(new ChangeListener()
		{
			@Override
			public void stateChanged(ChangeEvent e) 
			{
				maxClamp = maxClampSlider.getValue()/1024f;
				maxVal.setText(String.valueOf(maxClamp));
				update();
			}
		});
		
		xScale = 1;
		xScaleVal = new JTextField(String.valueOf(xScale));
		yScale = 1;
		yScaleVal = new JTextField(String.valueOf(yScale));
		xTranslate = 0;
		xTranslationVal = new JTextField(String.valueOf(xTranslate));
		yTranslate = 0;
		yTranslationVal = new JTextField(String.valueOf(yTranslate));
		minClamp = -1;
		minVal = new JTextField(String.valueOf(minClamp));
		maxClamp = 1;
		maxVal = new JTextField(String.valueOf(maxClamp));

		c.gridx = 3;
		c.gridy = 1;
		xScaleVal.setColumns(4);
		add(xScaleVal, c);

		c.gridx = 3;
		c.gridy = 2;
		yScaleVal.setColumns(4);
		add(yScaleVal, c);

		c.gridx = 3;
		c.gridy = 3;
		xTranslationVal.setColumns(4);
		add(xTranslationVal, c);

		c.gridx = 3;
		c.gridy = 4;
		yTranslationVal.setColumns(4);
		add(yTranslationVal, c);

		c.gridx = 3;
		c.gridy = 5;
		minVal.setColumns(4);
		add(minVal, c);

		c.gridx = 3;
		c.gridy = 6;
		maxVal.setColumns(4);
		add(maxVal, c);
		
		xScaleVal.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				xScale = Float.valueOf(xScaleVal.getText());
				xScaleSlider.setValue((int)(xScale * PRECISION));
				update();
			}
		});
		
		yScaleVal.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
//				System.out.println("yScaleVal:" + yScaleVal.getText()
//						+ " yScale:" + yScale
//						+ " yScaleSlider:" + yScaleSlider.getValue()
//						+ " newScaleVal:" + (int)(yScale * PRECISION));
				yScale = Float.valueOf(yScaleVal.getText());
				yScaleSlider.setValue((int)(yScale * PRECISION));
				update();
			}
		});		
		
		xTranslationVal.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				xTranslate = Math.round(Double.valueOf(
						xTranslationVal.getText()) * 4);
				xTranslateSlider.setValue((int)xTranslate);
				update();
			}
		});
		
		yTranslationVal.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				yTranslate = Math.round(Double.valueOf(yTranslationVal.getText()) * 4);
				yTranslateSlider.setValue((int)yTranslate);
				update();
			}
		});

		minVal.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				minClamp = Math.round(Double.valueOf(minVal.getText()) * 1024.00);
				minClampSlider.setValue((int)minClamp);
				update();
			}
		});

		maxVal.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				maxClamp = Math.round(Double.valueOf(maxVal.getText()) * 1024.00);
				maxClampSlider.setValue((int)maxClamp);
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

		c.gridx = 0;
		c.gridy = 7;
		c.gridwidth = 4;
		add(radioPanel, c);
		c.gridwidth = 1;


		absMap.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				update();
			}
		});
		
		compress01Map.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				update();
			}
		});
		
		noMap.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// NOTE: noMap data range undefined for ColorMap
				update();
			}
		});

		JPanel colorAxisImagePanel = new JPanel() {
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(SimplexTextureSource.this.colorAxis.img, 0, 0, null);
			}

			@Override
			public Dimension getPreferredSize() {
				return new Dimension(SimplexTextureSource.this.colorAxis.img.getWidth(), SimplexTextureSource.this.colorAxis.img.getHeight());
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

        c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 4;
		c.anchor = GridBagConstraints.LINE_START;
		add(colorAxisImagePanel, c);
		c.gridwidth = 1;
		c.anchor = GridBagConstraints.CENTER;

		
		// image building variables, initializations
		cols = 256;
		rows = 256;
		
		image = new BufferedImage(cols, rows, BufferedImage.TYPE_INT_ARGB);

		JPanel imagePanel = new JPanel() {
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

		c.gridx = 0;
		c.gridy = 8;
		c.gridwidth = 4;
		c.anchor = GridBagConstraints.LINE_START;
		add(imagePanel, c);
		c.gridwidth = 1;
		c.anchor = GridBagConstraints.CENTER;
	}
	
	private SimplexTextureSource getSelf() {return this;}
	
	public void update()
	{
		TextureParams tp = getTextureParams();
		TextureData data = TextureFunctions.generate(cols, rows, tp);

		for (int j = 0;  j < data.height;  j++) {
			for (int i = 0;  i < data.width;  i++) {
				int idx = (int)(data.noiseArray[i][j] * 255);
				int pixel = 0;
				if (tp.normalize == TextureParams.NoiseNormalization.SMOOTH ||
						tp.normalize == TextureParams.NoiseNormalization.ABS) {
					int argb = data.colorMap.data[idx];
					pixel = ColorAxis.calculateARGB(255,
							ColorAxis.getRed(argb),
							ColorAxis.getGreen(argb),
							ColorAxis.getBlue(argb));
				} else if (tp.normalize == TextureParams.NoiseNormalization.NONE) {
					pixel = ColorAxis.calculateARGB(255, idx, idx, idx);
				}

				image.setRGB(i, j, pixel);
			}
		}

//		functionText.setText("noise((x / width) * " 
//			+ xScaleVal.getText() + " + " 
//			+ xTranslationVal.getText()
//			+ ", (y / height) * "
//			+ yScaleVal.getText() + " + " 
//			+ yTranslationVal.getText() + ");");
		repaint();
		host.remix();
	}

	public TextureParams getTextureParams() {
		TextureParams.NoiseNormalization normalize;

		if (mappingOptions.getSelection().equals(compress01Map.getModel())) {
			normalize = TextureParams.NoiseNormalization.SMOOTH;
		} else if (mappingOptions.getSelection().equals(absMap.getModel())) {
			normalize = TextureParams.NoiseNormalization.ABS;
		} else {
			normalize = TextureParams.NoiseNormalization.NONE;
		}

		return new TextureParams(
				xScale,
				yScale,
				xTranslate,
				yTranslate,
				minClamp,
				maxClamp,
				normalize,
				new ColorMap(colorAxis.data)
		);
	}
}
