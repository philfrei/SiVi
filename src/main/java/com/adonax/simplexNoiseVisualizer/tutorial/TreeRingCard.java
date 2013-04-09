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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

import com.adonax.simplexNoiseVisualizer.MixerModel;
import com.adonax.simplexNoiseVisualizer.NoiseData;
import com.adonax.simplexNoiseVisualizer.OctaveModel;
import com.adonax.simplexNoiseVisualizer.TextureModel;
import com.adonax.simplexNoiseVisualizer.TopPanelModel;
import com.adonax.simplexNoiseVisualizer.color.ColorAxis;
import com.adonax.simplexNoiseVisualizer.color.ColorBarPeg;
import com.adonax.simplexNoiseVisualizer.gradients.GradientGUIModel;

public class TreeRingCard extends JPanel
{
	private static final long serialVersionUID = 1L;
	
	public TreeRingCard (TutorialFramework tf)
	{
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		NavigationPanel navigater = new NavigationPanel(tf, 
				"Smooth Noise", "Title Card", "Classic Clouds");
		navigater.setPreferredSize(new Dimension(450, 32));
		navigater.setMaximumSize(new Dimension(450, 32));
		navigater.setAlignmentX(CENTER_ALIGNMENT);
		add(navigater);
		
		// Title graphic
		TopPanelModel settings = new TopPanelModel(1, 700, 160, 6);
	
		OctaveModel[] octaveModels = new OctaveModel[1];
		octaveModels[0] = new OctaveModel(2, 2, 0, 0, 
				-1, 1, OctaveModel.NoiseNormalization.SMOOTH);

		float[] weights = new float[1];
		weights[0] = 4;
		MixerModel mixerModel = new MixerModel(weights, 4, 
				MixerModel.MapMethod.RING, 
				new NoiseData(settings.finalWidth, 
						settings.finalHeight)); 
		
		ColorAxis colorAxis = new ColorAxis();
		ArrayList<ColorBarPeg> colorBarPegs = new ArrayList<ColorBarPeg>();
		colorBarPegs.add(new ColorBarPeg(0, 64, 32, 0, 255));
		colorBarPegs.add(new ColorBarPeg(255, 128, 96, 0, 255));
		colorAxis.setColorBarPegs(colorBarPegs);
		
		TextureModel sivi = new TextureModel(settings, octaveModels,
				mixerModel, new GradientGUIModel(), colorAxis);		
		
		TutorialDisplay pageDisplay = new TutorialDisplay(
				sivi, tf.topPanel);
		pageDisplay.setAlignmentX(CENTER_ALIGNMENT);
		add(pageDisplay);
		
		// Text content
		JTextPane textArea = new JTextPane();
		textArea.setEditable(false);
	
		URL introURL = TutorialFramework.class.getResource(
				"pageContent/treeRings.html");

		try
		{
			textArea.setPage(introURL);
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		JScrollPane scroller = new JScrollPane(textArea);
		scroller.setPreferredSize(new Dimension(750, 470));
		scroller.setAlignmentX(CENTER_ALIGNMENT);
		add(scroller);
	}
}
