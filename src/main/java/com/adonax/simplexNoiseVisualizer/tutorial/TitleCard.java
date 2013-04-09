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
package com.adonax.simplexNoiseVisualizer.tutorial;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.adonax.simplexNoiseVisualizer.MixerModel;
import com.adonax.simplexNoiseVisualizer.NoiseData;
import com.adonax.simplexNoiseVisualizer.OctaveModel;
import com.adonax.simplexNoiseVisualizer.TextureModel;
import com.adonax.simplexNoiseVisualizer.TopPanelModel;
import com.adonax.simplexNoiseVisualizer.color.ColorAxis;
import com.adonax.simplexNoiseVisualizer.color.ColorBarPeg;
import com.adonax.simplexNoiseVisualizer.gradients.GradientGUIModel;

public class TitleCard extends JPanel 
{
	private static final long serialVersionUID = 1L;
	int buttonHeight = 32;
	int labelHeight = 32;
	
	BufferedImage image;
	
	public TitleCard (final TutorialFramework tf)
	{
		setLayout(new GridBagLayout());
		GridBagConstraints gbConstraints = new GridBagConstraints();
		gbConstraints.anchor = GridBagConstraints.LINE_START;

		TopPanelModel settings = new TopPanelModel(1, 700, 160, 6);

		OctaveModel[] octaveModels = new OctaveModel[settings.octaves];
		octaveModels[0] = new OctaveModel(128, 4, 0, 0, 
				-1, 1, OctaveModel.NoiseNormalization.SMOOTH);

		ColorAxis colorAxis = new ColorAxis();
		ArrayList<ColorBarPeg> colorBarPegs = new ArrayList<ColorBarPeg>();
		colorBarPegs.add(new ColorBarPeg(0, 0, 0, 0, 255));
		colorBarPegs.add(new ColorBarPeg(255, 224, 196, 0, 255));
		colorAxis.setColorBarPegs(colorBarPegs);
		
		float[] weights = new float[settings.octaves];
		weights[0] = 1.0f;
		MixerModel mixerModel = new MixerModel(weights, 1, 
				MixerModel.MapMethod.CLAMP, 
				new NoiseData(settings.finalWidth, 
						settings.finalHeight)); 
		
		TextureModel sivi = new TextureModel(settings, octaveModels,
				mixerModel, new GradientGUIModel(), colorAxis);
		
		TutorialDisplay pageDisplay = new TutorialDisplay(
				sivi, tf.topPanel);
		
		gbConstraints.gridx = 0;
		gbConstraints.gridwidth = 3;
		gbConstraints.gridy = 0;
		add(pageDisplay, gbConstraints);
		gbConstraints.gridwidth = 1;
		
		
		// BUTTONS AND LABELS  -- 1st Row
		Dimension buttonDimension = new Dimension(48, 24);
		
		JButton introButton = new JButton();
		introButton.setPreferredSize(buttonDimension);
		introButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				tf.setCard("Intro Card");
			}
		});
		gbConstraints.gridx = 0;
		gbConstraints.gridy = 1;
		add(introButton, gbConstraints);
		
		JLabel introLabel = new JLabel(" Introduction");
		introLabel.setFont(new java.awt.Font(
				"Serif", java.awt.Font.ITALIC, 24) );
		gbConstraints.gridx = 1;
		gbConstraints.gridy = 1;
		add(introLabel, gbConstraints);
		
		//  -- 2nd Row
		JButton smoothNoiseButton = new JButton();
		smoothNoiseButton.setPreferredSize(buttonDimension);
		smoothNoiseButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				tf.setCard("Smooth Noise");				
			}
		});
		gbConstraints.gridx = 0;
		gbConstraints.gridy = 2;
		add(smoothNoiseButton, gbConstraints);

		JLabel smoothNoiseLabel = new JLabel(" Smooth (and turbulent) noise: the basics");
		smoothNoiseLabel.setFont(new java.awt.Font(
				"Serif", java.awt.Font.PLAIN, 24) );
		gbConstraints.gridx = 1;
		gbConstraints.gridy = 2;
		add(smoothNoiseLabel, gbConstraints);
		
		// -- 3rd Row
		JButton treeRingButton = new JButton();
		treeRingButton.setPreferredSize(buttonDimension);
		treeRingButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				tf.setCard("Tree Rings");				
			}
		});
		gbConstraints.gridx = 0;
		gbConstraints.gridy = 3;		
		add(treeRingButton, gbConstraints);

		JLabel treeRingLabel = new JLabel("Tree Rings: clamp vs. wrap-around transform");
		treeRingLabel.setFont(new java.awt.Font(
				"Serif", java.awt.Font.PLAIN, 24) );
		gbConstraints.gridx = 1;
		gbConstraints.gridy = 3;
		add(treeRingLabel, gbConstraints);

		JButton cloudButton = new JButton();
		cloudButton.setPreferredSize(buttonDimension);
		cloudButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				tf.setCard("Classic Clouds");
			}
		});
		gbConstraints.gridx = 0;
		gbConstraints.gridy = 4;
		add(cloudButton, gbConstraints);

		JLabel cloudLabel = new JLabel("Classic Clouds: using fractals, sum 1/f noise");
		cloudLabel.setFont(new java.awt.Font(
				"Serif", java.awt.Font.PLAIN, 24) );
		gbConstraints.gridx = 1;
		gbConstraints.gridy = 4;
		add(cloudLabel, gbConstraints);
		
		// -- 4th Row
		JButton terrainButton = new JButton();
		terrainButton.setPreferredSize(buttonDimension);
		terrainButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				tf.setCard("Planet Terrain");
			}
		});
		gbConstraints.gridx = 0;
		gbConstraints.gridy = 5;
		add(terrainButton, gbConstraints);

		JLabel terrainLabel = new JLabel("Planet Terrain: using a color map");
		terrainLabel.setFont(new java.awt.Font(
				"Serif", java.awt.Font.PLAIN, 24) );
		gbConstraints.gridx = 1;
		gbConstraints.gridy = 5;
		add(terrainLabel, gbConstraints);		
	
		JButton colorMapButton = new JButton();
		colorMapButton.setPreferredSize(buttonDimension);
		colorMapButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				tf.setCard("ColorMap Card");
			}
		});
		gbConstraints.gridx = 0;
		gbConstraints.gridy = 12;
		add(colorMapButton, gbConstraints);
	
		JLabel colorMapLabel = new JLabel("ColorMap Source");
		colorMapLabel.setFont(new java.awt.Font(
				"Serif", java.awt.Font.ITALIC, 24) );
		gbConstraints.gridx = 1;
		gbConstraints.gridy = 12;
		add(colorMapLabel, gbConstraints);

		// 5th Row
		JButton flareButton = new JButton();
		flareButton.setPreferredSize(buttonDimension);
		flareButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				tf.setCard("Flare Card");
			}
		});
		gbConstraints.gridx = 0;
		gbConstraints.gridy = 6;
		add(flareButton, gbConstraints);

		JLabel flaresLabel = new JLabel("Solar Flares: modulating a gradient function");
		flaresLabel.setFont(new java.awt.Font(
				"Serif", java.awt.Font.PLAIN, 24) );
//		flaresLabel.setBounds(106, 520, 494, labelHeight);
		gbConstraints.gridx = 1;
		gbConstraints.gridy = 6;
		add(flaresLabel, gbConstraints);			

		JButton perlinButton = new JButton();
		perlinButton.setPreferredSize(buttonDimension);
		perlinButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				tf.setCard("Simplex Card");
			}
		});
		gbConstraints.gridx = 0;
		gbConstraints.gridy = 10;
		add(perlinButton, gbConstraints);

		JLabel perlinLabel = new JLabel("Simplex Source");
		perlinLabel.setFont(new java.awt.Font(
				"Serif", java.awt.Font.ITALIC, 24) );
		gbConstraints.gridx = 1;
		gbConstraints.gridy = 10;
		add(perlinLabel, gbConstraints);

		JButton templateButton = new JButton();
		templateButton.setPreferredSize(buttonDimension);
		templateButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				tf.setCard("Template Card");
			}
		});
		gbConstraints.gridx = 0;
		gbConstraints.gridy = 11;
		add(templateButton, gbConstraints);

		JLabel templateLabel = new JLabel("Code Template");
		templateLabel.setFont(new java.awt.Font(
				"Serif", java.awt.Font.ITALIC, 24) );
	//	templateLabel.setBounds(width - (88 + 150), 270, 
	//			300, labelHeight);
		gbConstraints.gridx = 1;
		gbConstraints.gridy = 11;
		add(templateLabel, gbConstraints);
		
	}	
//	@Override
//	protected void paintComponent(Graphics g) {
//		super.paintComponent(g);
//		
//		Graphics2D g2 = (Graphics2D)g;
//		
//		RenderingHints hint = new RenderingHints( 
//				RenderingHints.KEY_TEXT_ANTIALIASING,
//				RenderingHints.VALUE_TEXT_ANTIALIAS_ON );
//			
//		g2.setRenderingHints( hint );
//	
//		// Background
//		g2.setColor(new Color(255, 255, 255));
//		g2.fillRect(0, 0, width, height);
//
//		g2.drawImage(image, 40, 40, null);
//		
//		g2.setColor(new Color(255, 255, 255));
//		g2.setFont( new java.awt.Font(
//				"Serif", java.awt.Font.BOLD, 56) );
//		g2.drawString( "Basic Procedural Texture", 70, 100);
//		g2.drawString( "Building in Java", 70, 160);
//		g2.setColor(new Color(0, 255, 255));
//		g2.drawString( "with Simplex Noise", 275, 220);
//	}
}
