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
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.adonax.simplexNoiseVisualizer.MixerModel;
import com.adonax.simplexNoiseVisualizer.NoiseData;
import com.adonax.simplexNoiseVisualizer.OctaveModel;
import com.adonax.simplexNoiseVisualizer.TextureModel;
import com.adonax.simplexNoiseVisualizer.TopPanelModel;
import com.adonax.simplexNoiseVisualizer.color.ColorAxis;
import com.adonax.simplexNoiseVisualizer.color.ColorBarPeg;
import com.adonax.simplexNoiseVisualizer.gradients.GradientGUIModel;

public class CloudCard extends JPanel
{
	private static final long serialVersionUID = 1L;
		
	public CloudCard (final TutorialFramework tf)
	{
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		NavigationPanel navigater = new NavigationPanel(tf, 
				"Tree Rings", "Title Card", "Planet Terrain");
		navigater.setPreferredSize(new Dimension(450, 32));
		navigater.setMaximumSize(new Dimension(450, 32));
		navigater.setAlignmentX(CENTER_ALIGNMENT);
		add(navigater);
		
		// Title graphic
		TopPanelModel settings = new TopPanelModel(5, 700, 160, 6);
	
		OctaveModel[] octaveModels = new OctaveModel[settings.octaves];
		octaveModels[0] = new OctaveModel(2, 2, 0, 0, 
				-1, 1, OctaveModel.NoiseNormalization.SMOOTH);
		octaveModels[1] = new OctaveModel(4, 4, 0, 0, 
				-1, 1, OctaveModel.NoiseNormalization.SMOOTH);
		octaveModels[2] = new OctaveModel(8, 8, 0, 0, 
				-1, 1, OctaveModel.NoiseNormalization.SMOOTH);
		octaveModels[3] = new OctaveModel(16, 16, 0, 0, 
				-1, 1, OctaveModel.NoiseNormalization.SMOOTH);
		octaveModels[4] = new OctaveModel(32, 32, 0, 0, 
				-1, 1, OctaveModel.NoiseNormalization.SMOOTH);
		
		ColorAxis colorAxis = new ColorAxis();
		ArrayList<ColorBarPeg> colorBarPegs = new ArrayList<ColorBarPeg>();
		colorBarPegs.add(new ColorBarPeg(0, 255, 255, 255, 255));
		colorBarPegs.add(new ColorBarPeg(255, 0, 0, 255, 255));
		colorAxis.setColorBarPegs(colorBarPegs);
		
		float[] weights = new float[settings.octaves];
		weights[0] = 0.516129f;
		weights[1] = 0.2580645f;
		weights[2] = 0.12903225f;
		weights[3] = 0.06451613f;
		weights[4] = 0.032258064f;
		MixerModel mixerModel = new MixerModel(weights, 1, 
				MixerModel.MapMethod.CLAMP, 
				new NoiseData(settings.finalWidth, 
						settings.finalHeight)); 

		TextureModel sivi = new TextureModel(settings, octaveModels,
				mixerModel, new GradientGUIModel(), colorAxis);		
		
		TutorialDisplay pageDisplay = new TutorialDisplay(
				sivi, tf.topPanel);
		pageDisplay.setAlignmentX(CENTER_ALIGNMENT);
		add(pageDisplay);
		
		// Text content
		JEditorPane textArea = new JEditorPane();
		textArea.setEditable(false);
	
		URL introURL = TutorialFramework.class.getResource(
				"pageContent/classicClouds.html");
		try
		{
			textArea.setPage(introURL);
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		
		JScrollPane scroller = new JScrollPane(textArea);
		scroller.setPreferredSize(new Dimension(750, 470));
		scroller.setAlignmentX(CENTER_ALIGNMENT);
		add(scroller);
	}
}
