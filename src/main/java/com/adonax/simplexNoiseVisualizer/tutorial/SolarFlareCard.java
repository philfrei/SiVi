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
import com.adonax.simplexNoiseVisualizer.OctaveModel;
import com.adonax.simplexNoiseVisualizer.TextureModel;
import com.adonax.simplexNoiseVisualizer.TopPanelModel;
import com.adonax.simplexNoiseVisualizer.color.ColorAxis;
import com.adonax.simplexNoiseVisualizer.color.ColorBarPeg;
import com.adonax.simplexNoiseVisualizer.gradients.GradientGUIModel;
import com.adonax.simplexNoiseVisualizer.gradients.LinearGradientFunction;
import com.adonax.simplexNoiseVisualizer.gradients.RadialGradientFunction;
import com.adonax.simplexNoiseVisualizer.gradients.SinusoidalGradientFunction;

public class SolarFlareCard extends JPanel
{
	private static final long serialVersionUID = 1L;
	
	public SolarFlareCard (final TutorialFramework tf)
	{
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		NavigationPanel navigater = new NavigationPanel(tf,
				"Planet Terrain", "Title Card", "Template Card");
		navigater.setPreferredSize(new Dimension(450, 32));
		navigater.setMaximumSize(new Dimension(450, 32));
		navigater.setAlignmentX(CENTER_ALIGNMENT);
		add(navigater);
		
		// Title graphic
		TopPanelModel settings = new TopPanelModel(4, 700, 160, 6);
	
		OctaveModel[] octaveModels = new OctaveModel[settings.octaves];
		octaveModels[0] = new OctaveModel(2, 2, 0, 0, 
				-1, 1, OctaveModel.NoiseNormalization.ABS);
		octaveModels[1] = new OctaveModel(4, 4, 0, 0, 
				-1, 1, OctaveModel.NoiseNormalization.ABS);
		octaveModels[2] = new OctaveModel(8, 8, 0, 0, 
				-1, 1, OctaveModel.NoiseNormalization.ABS);
		octaveModels[3] = new OctaveModel(16, 16, 0, 0, 
				-1, 1, OctaveModel.NoiseNormalization.ABS);
		
		ColorAxis colorAxis = new ColorAxis();
		ArrayList<ColorBarPeg> colorBarPegs = new ArrayList<ColorBarPeg>();
		colorBarPegs.add(new ColorBarPeg(0, 255, 255, 102, 255));
    	colorBarPegs.add(new ColorBarPeg(127, 204, 0, 0, 255));
		colorBarPegs.add(new ColorBarPeg(255, 0, 0, 0, 255));
		colorAxis.setColorBarPegs(colorBarPegs);
		
		RadialGradientFunction radialGradient = 
				new RadialGradientFunction(
						settings.finalWidth / 2, 
						settings.finalHeight / 2,
						settings.finalWidth / 2,
						1, -0.5f);
		boolean[] selected = new boolean[settings.octaves];
		selected[1] = true;
		
		GradientGUIModel gradientGUIModel = new GradientGUIModel(
				new LinearGradientFunction(), radialGradient,
				new SinusoidalGradientFunction(), selected);
		
		float[] weights = new float[settings.octaves];
		weights[0] = 0.56f;
		weights[1] = 0.28f;
		weights[2] = 0.14f;
		weights[3] = 0.07f;
		MixerModel mixerModel = new MixerModel(weights, 1, 
				MixerModel.MapMethod.CLAMP, radialGradient,
				settings);

		TextureModel sivi = new TextureModel(settings, octaveModels,
				mixerModel, gradientGUIModel, colorAxis);		
		
		TutorialDisplay pageDisplay = new TutorialDisplay(
				sivi, tf.topPanel);
		pageDisplay.setAlignmentX(CENTER_ALIGNMENT);
		add(pageDisplay);		

		// Text content
		JEditorPane textArea = new JEditorPane();
		textArea.setEditable(false);
	
		URL introURL = TutorialFramework.class.getResource(
				"pageContent/Flames.html");
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
