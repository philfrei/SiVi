package com.adonax.simplexNoiseVisualizer.tutorial;

import java.awt.Dimension;
import java.io.IOException;
import java.net.URL;

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
import com.adonax.simplexNoiseVisualizer.gradients.GradientGUIModel;


public class SmoothNoiseCard extends JPanel
{
	private static final long serialVersionUID = 1L;
	
	SmoothNoiseCard(TutorialFramework tf)
	{
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

		NavigationPanel navigater = new NavigationPanel(tf, 
				"Intro Card", "Title Card", "Tree Rings");
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
		weights[0] = 1;
		MixerModel mixerModel = new MixerModel(weights, 1, 
				MixerModel.MapMethod.CLAMP, 
				new NoiseData(settings.finalWidth, 
						settings.finalHeight)); 
		
		ColorAxis colorAxis = new ColorAxis();

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
				"pageContent/smoothNoise.html");
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
