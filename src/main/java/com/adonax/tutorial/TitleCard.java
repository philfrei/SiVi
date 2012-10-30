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
package com.adonax.tutorial;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.adonax.texturebuilder.ColorAxis;
import com.adonax.texturebuilder.ColorBarPeg;
import com.adonax.tutorial.utilities.MakeTexturedImage;

public class TitleCard extends JPanel 
{
	private static final long serialVersionUID = 1L;
	int width, height;
	int buttonHeight = 32;
	int labelHeight = 32;
	
	BufferedImage image;
	
	public TitleCard (int width, int height, final TutorialFramework tf)
	{
		setLayout(null);
		
		this.width = width;
		this.height = height;
		
		ColorAxis colorAxis = new ColorAxis();
		ArrayList<ColorBarPeg> colorBarPegs = new ArrayList<ColorBarPeg>();
		colorBarPegs.add(new ColorBarPeg(0, 0, 0, 0, 255));
		colorBarPegs.add(new ColorBarPeg(255, 224, 196, 0, 255));
		colorAxis.setColorBarPegs(colorBarPegs);
		
		float[] xScales = {128};
		float[] yScales = {4};
		float[] xTranslations = {0};
		float[] yTranslations = {0};
		float[] volumes = {1};
		
		image = MakeTexturedImage.make(720, 200, colorAxis, 
				xScales, yScales, xTranslations, yTranslations,
				volumes, 0);
		
		JButton introButton = new JButton();
		introButton.setBounds(50, 270, 48, buttonHeight);
		introButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				tf.setCard("Intro Card");
			}
		});
		add(introButton);
		
		JLabel introLabel = new JLabel("Introduction to this App");
		introLabel.setFont(new java.awt.Font(
				"Serif", java.awt.Font.PLAIN, 24) );
		introLabel.setBounds(106, 270, 300, labelHeight);
		add(introLabel);
		
		JButton templateButton = new JButton();
		templateButton.setBounds(width - 88, 270, 48, buttonHeight);
		templateButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				tf.setCard("Template Card");
			}
		});
		add(templateButton);

		JLabel templateLabel = new JLabel("Code Template");
		templateLabel.setFont(new java.awt.Font(
				"Serif", java.awt.Font.ITALIC, 24) );
		templateLabel.setBounds(width - (88 + 150), 270, 
				300, labelHeight);
		add(templateLabel);
		
		JButton perlinButton = new JButton();
		perlinButton.setBounds(width - 88, 320, 48, buttonHeight);
		perlinButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				tf.setCard("Simplex Card");
			}
		});
		add(perlinButton);

		JLabel perlinLabel = new JLabel("Simplex Source");
		perlinLabel.setFont(new java.awt.Font(
				"Serif", java.awt.Font.ITALIC, 24) );
		perlinLabel.setBounds(width - (93 + 150), 320, 
				300, labelHeight);
		add(perlinLabel);
		
		JButton smoothNoiseButton = new JButton();
		smoothNoiseButton.setBounds(50, 320, 48, buttonHeight);
		smoothNoiseButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				tf.setCard("Smooth Noise");				
			}
		});
		add(smoothNoiseButton);

		JLabel smoothNoiseLabel = new JLabel("Smooth (and Turbulent) noise: the basics");
		smoothNoiseLabel.setFont(new java.awt.Font(
				"Serif", java.awt.Font.PLAIN, 24) );
		smoothNoiseLabel.setBounds(106, 320, 600, labelHeight);
		add(smoothNoiseLabel);

		JButton treeRingButton = new JButton();
		treeRingButton.setBounds(50, 370, 48, buttonHeight);
		treeRingButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				tf.setCard("Tree Rings");				
			}
		});
		add(treeRingButton);

		JLabel treeRingLabel = new JLabel("Tree Rings: mod div vs. clamping");
		treeRingLabel.setFont(new java.awt.Font(
				"Serif", java.awt.Font.PLAIN, 24) );
		treeRingLabel.setBounds(106, 370, 400, labelHeight);
		add(treeRingLabel);

		JButton cloudButton = new JButton();
		cloudButton.setBounds(50, 420, 48, buttonHeight);
		cloudButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				tf.setCard("Classic Clouds");
			}
		});
		add(cloudButton);

		JLabel cloudLabel = new JLabel("Classic Fluffy White Clouds: sum 1/f noise");
		cloudLabel.setFont(new java.awt.Font(
				"Serif", java.awt.Font.PLAIN, 24) );
		cloudLabel.setBounds(106, 420, 494, labelHeight);
		add(cloudLabel);
		
		JButton terrainButton = new JButton();
		terrainButton.setBounds(50, 470, 48, buttonHeight);
		terrainButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				tf.setCard("Planet Terrain");
			}
		});
		add(terrainButton);

		JLabel terrainLabel = new JLabel("Planet Terrain: using a Color Map");
		terrainLabel.setFont(new java.awt.Font(
				"Serif", java.awt.Font.PLAIN, 24) );
		terrainLabel.setBounds(106, 470, 494, labelHeight);
		add(terrainLabel);		
	
	
		JButton colorMapButton = new JButton();
		colorMapButton.setBounds(width - 88, 470, 48, buttonHeight);
		colorMapButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				tf.setCard("ColorMap Card");
			}
		});
		add(colorMapButton);
	
		JLabel colorMapLabel = new JLabel("ColorMap Source");
		colorMapLabel.setFont(new java.awt.Font(
				"Serif", java.awt.Font.ITALIC, 24) );
		colorMapLabel.setBounds(width - (120 + 150), 470, 
				300, labelHeight);
		add(colorMapLabel);

		JButton flamesButton = new JButton();
		flamesButton.setBounds(50, 520, 48, buttonHeight);
		flamesButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				tf.setCard("Flames Card");
			}
		});
		add(flamesButton);

		JLabel flamesLabel = new JLabel("Flames: modulating a function");
		flamesLabel.setFont(new java.awt.Font(
				"Serif", java.awt.Font.PLAIN, 24) );
		flamesLabel.setBounds(106, 520, 494, labelHeight);
		add(flamesLabel);		
		
	}	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D)g;
		
		RenderingHints hint = new RenderingHints( 
				RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON );
			
		g2.setRenderingHints( hint );
	
		// Background
		g2.setColor(new Color(255, 255, 255));
		g2.fillRect(0, 0, width, height);

		g2.drawImage(image, 40, 40, null);
		
		g2.setColor(new Color(255, 255, 255));
		g2.setFont( new java.awt.Font(
				"Serif", java.awt.Font.BOLD, 56) );
		g2.drawString( "Basic Procedural Texture", 70, 100);
		g2.drawString( "Building in Java", 70, 160);
		g2.setColor(new Color(0, 255, 255));
		g2.drawString( "with Simplex Noise", 275, 220);
	}
}
