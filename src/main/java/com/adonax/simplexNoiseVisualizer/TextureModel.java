package com.adonax.simplexNoiseVisualizer;

import com.adonax.simplexNoiseVisualizer.color.ColorAxis;
import com.adonax.simplexNoiseVisualizer.gradients.GradientGUIModel;

public class TextureModel
{
	public final TopPanelModel appSettings;
	public final OctaveModel[] octaveModels;
	public final MixerModel mixerModel;
	public final GradientGUIModel gradientGUIModel;
	//TODO:  should be peg set and HSB, array of these, and selector
	public final ColorAxis colorAxis;
	
	public TextureModel(TopPanelModel appSettings, 
			OctaveModel[] octaveModels, MixerModel mixerModel, 
			GradientGUIModel gradientGUIModel, ColorAxis colorAxis)
	{
		this.appSettings = appSettings;
		this.octaveModels = octaveModels;
		this.mixerModel = mixerModel;
		this.gradientGUIModel = gradientGUIModel;
		this.colorAxis = colorAxis;
	}
}
