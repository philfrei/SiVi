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

import java.util.ArrayList;

import javax.swing.JPanel;

import com.adonax.simplexNoiseVisualizer.MixerModel;
import com.adonax.simplexNoiseVisualizer.NoiseData;
import com.adonax.simplexNoiseVisualizer.OctaveModel;
import com.adonax.simplexNoiseVisualizer.TextureModel;
import com.adonax.simplexNoiseVisualizer.TopPanelModel;
import com.adonax.simplexNoiseVisualizer.color.ColorAxis;
import com.adonax.simplexNoiseVisualizer.color.ColorBarPeg;
import com.adonax.simplexNoiseVisualizer.gradients.GradientGUI;
import com.adonax.simplexNoiseVisualizer.gradients.GradientGUIModel;
import com.adonax.simplexNoiseVisualizer.gradients.LinearGradientFunction;
import com.adonax.simplexNoiseVisualizer.gradients.RadialGradientFunction;
import com.adonax.simplexNoiseVisualizer.gradients.SinusoidalGradientFunction;

public class GalleryCard extends JPanel
{
	private static final long serialVersionUID = 1L;
		
	public GalleryCard (final TutorialFramework tf)
	{
		add(makeClassicCloud(tf));
		add(makeCirrusCloud(tf));
		add(makePerlinExampleCloud(tf));
		add(makeSunsetCloud(tf));
		add(makeTreeRings(tf));
		add(makeWoodGrain(tf));
		add(makeCrayons(tf));
		add(makeTerraVeins(tf));
		add(makeTerraMap(tf));
		add(makePlasmaField(tf));
		add(makeSolarFlare(tf));
		add(makeFieryFuzzball(tf));
		add(makeGoldFireThread(tf));
		add(makeCandelabra(tf));
	}
	
	TutorialDisplay makeClassicCloud(final TutorialFramework tf)
	{
		TopPanelModel settings = 
				new TopPanelModel(5, 256, 256, 6);
	
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
		MixerModel mixerModel = new MixerModel(
				weights, 1, MixerModel.MapMethod.CLAMP, 
				new NoiseData(settings.finalWidth, 
						settings.finalHeight)); 

		TextureModel sivi = new TextureModel(settings, octaveModels,
				mixerModel, new GradientGUIModel(), colorAxis);		
		
		TutorialDisplay pageDisplay = new TutorialDisplay(
				sivi, tf.topPanel);		
		
		return pageDisplay;
	}
	
	TutorialDisplay makeCirrusCloud(final TutorialFramework tf)
	{
		TopPanelModel settings = 
				new TopPanelModel(4, 256, 256, 6);
	
		OctaveModel[] octaveModels = new OctaveModel[settings.octaves];
		octaveModels[0] = new OctaveModel(0.5f, 4, 0, 0, 
				-1, 1, OctaveModel.NoiseNormalization.NONE);
		octaveModels[1] = new OctaveModel(0.75f, 8, 0, 0, 
				-1, 1, OctaveModel.NoiseNormalization.SMOOTH);
		octaveModels[2] = new OctaveModel(1.5f, 16, 0, 0, 
				-1, 1, OctaveModel.NoiseNormalization.SMOOTH);
		octaveModels[3] = new OctaveModel(3, 32, 0, 0, 
				-1, 1, OctaveModel.NoiseNormalization.ABS);
		
		ColorAxis colorAxis = new ColorAxis();
		ArrayList<ColorBarPeg> colorBarPegs = new ArrayList<ColorBarPeg>();
		colorBarPegs.add(new ColorBarPeg(0, 0, 0, 255, 255));
		colorBarPegs.add(new ColorBarPeg(255, 255, 255, 255, 255));
		colorAxis.setColorBarPegs(colorBarPegs);
		
		float[] weights = new float[settings.octaves];
		weights[0] = 0.5f;
		weights[1] = 0.25f;
		weights[2] = 0.125f;
		weights[3] = 0.0625f;
		MixerModel mixerModel = new MixerModel(
				weights, 1, MixerModel.MapMethod.CLAMP, 
				new NoiseData(settings.finalWidth, 
						settings.finalHeight)); 

		TextureModel sivi = new TextureModel(settings, octaveModels,
				mixerModel, new GradientGUIModel(), colorAxis);		
		
		TutorialDisplay pageDisplay = new TutorialDisplay(
				sivi, tf.topPanel);		
		
		return pageDisplay;
	}
	
	TutorialDisplay makePerlinExampleCloud(final TutorialFramework tf)
	{
		TopPanelModel settings = 
				new TopPanelModel(7, 256, 256, 6);
	
		OctaveModel[] octaveModels = new OctaveModel[settings.octaves];
		octaveModels[0] = new OctaveModel(1, 1, 0, 0, 
				-1, 1, OctaveModel.NoiseNormalization.ABS);
		octaveModels[1] = new OctaveModel(2, 2, 0, 0, 
				-1, 1, OctaveModel.NoiseNormalization.ABS);
		octaveModels[2] = new OctaveModel(4, 4, 0, 0, 
				-1, 1, OctaveModel.NoiseNormalization.ABS);
		octaveModels[3] = new OctaveModel(8, 8, 0, 0, 
				-1, 1, OctaveModel.NoiseNormalization.ABS);
		octaveModels[4] = new OctaveModel(16, 16, 0, 0, 
				-1, 1, OctaveModel.NoiseNormalization.ABS);
		octaveModels[5] = new OctaveModel(32, 32, 0, 0, 
				-1, 1, OctaveModel.NoiseNormalization.ABS);
		octaveModels[6] = new OctaveModel(64, 64, 0, 0, 
				-1, 1, OctaveModel.NoiseNormalization.ABS);

		ColorAxis colorAxis = new ColorAxis();
		ArrayList<ColorBarPeg> colorBarPegs = new ArrayList<ColorBarPeg>();
		colorBarPegs.add(new ColorBarPeg(0, 116, 116, 255, 255));
		colorBarPegs.add(new ColorBarPeg(64, 116, 116, 255, 255));
		colorBarPegs.add(new ColorBarPeg(76, 116, 116, 148, 255));
		colorBarPegs.add(new ColorBarPeg(255, 255, 176, 148, 255));
		colorAxis.setColorBarPegs(colorBarPegs);

		LinearGradientFunction linearGradient = 
				new LinearGradientFunction(
						settings.finalWidth, 
						settings.finalHeight,
						0, 0, -1f, 0.75f);
		boolean[] selected = new boolean[3];
		selected[0] = true;

		GradientGUIModel gradientGUIModel = new GradientGUIModel(
				linearGradient, new RadialGradientFunction(),
				new SinusoidalGradientFunction(), selected);
		
		float[] weights = new float[settings.octaves];
		weights[0] = 0.64f;
		weights[1] = 0.32f;
		weights[2] = 0.16f;
		weights[3] = 0.08f;
		weights[4] = 0.04f;
		weights[5] = 0.02f;
		weights[6] = 0.01f;
		
		MixerModel mixerModel = new MixerModel(
				weights, 1, MixerModel.MapMethod.CLAMP, 
				linearGradient, settings); 

		TextureModel sivi = new TextureModel(settings, octaveModels,
				mixerModel, gradientGUIModel, colorAxis);		
		
		TutorialDisplay pageDisplay = new TutorialDisplay(
				sivi, tf.topPanel);		
		
		return pageDisplay;
	}
	
	TutorialDisplay makeSunsetCloud(final TutorialFramework tf)
	{
		TopPanelModel settings = 
				new TopPanelModel(5, 256, 256, 6);
	
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
		colorBarPegs.add(new ColorBarPeg(0, 255, 204, 153, 255));
		colorBarPegs.add(new ColorBarPeg(64, 222, 178, 166, 255));
		colorBarPegs.add(new ColorBarPeg(191, 31, 25, 243, 255));
		colorBarPegs.add(new ColorBarPeg(255, 0, 0, 255, 255));
		colorAxis.setColorBarPegs(colorBarPegs);
		
		LinearGradientFunction linearGradient = 
				new LinearGradientFunction(
						settings.finalWidth, 
						settings.finalHeight,
						0, 0, 0.8f, -0.5f);
		boolean[] selected = new boolean[3];
		selected[0] = true;
		
		GradientGUIModel gradientGUIModel = new GradientGUIModel(
				linearGradient, new RadialGradientFunction(),
				new SinusoidalGradientFunction(), selected);
		
		
		float[] weights = new float[settings.octaves];
		weights[0] = 0.48f;
		weights[1] = 0.24f;
		weights[2] = 0.12f;
		weights[3] = 0.06f;
		weights[4] = 0.03f;
		MixerModel mixerModel = new MixerModel(
				weights, 1, MixerModel.MapMethod.CLAMP, 
				linearGradient, settings); 

		TextureModel sivi = new TextureModel(settings, octaveModels,
				mixerModel, gradientGUIModel, colorAxis);		
		
		TutorialDisplay pageDisplay = new TutorialDisplay(
				sivi, tf.topPanel);		
		
		return pageDisplay;
	}

	TutorialDisplay makeTreeRings(final TutorialFramework tf)
	{
		TopPanelModel settings = 
				new TopPanelModel(1, 256, 256, 6);
		
		OctaveModel[] octaveModels = new OctaveModel[1];
		octaveModels[0] = new OctaveModel(2, 2, 0, 0, 
				-1, 1, OctaveModel.NoiseNormalization.NONE);
		float masterWeight = 4;
		float[] weights = new float[1];
		weights[0] = 1;
		MixerModel mixerModel = new MixerModel(weights, 
				masterWeight, MixerModel.MapMethod.RING, 
				new NoiseData(settings.finalWidth, 
						settings.finalHeight)); 
		
		ColorAxis colorAxis = new ColorAxis();
		ArrayList<ColorBarPeg> colorBarPegs = new ArrayList<ColorBarPeg>();
		colorBarPegs.add(new ColorBarPeg(0, 154, 122, 29, 255));
		colorBarPegs.add(new ColorBarPeg(255, 192, 160, 64, 255));
		colorAxis.setColorBarPegs(colorBarPegs);

		
		TextureModel sivi = new TextureModel(settings, octaveModels,
				mixerModel, new GradientGUIModel(), colorAxis);		
		
		TutorialDisplay pageDisplay = new TutorialDisplay(
				sivi, tf.topPanel);		
		
		return pageDisplay;
	}
	
	TutorialDisplay makeWoodGrain(final TutorialFramework tf)
	{
		TopPanelModel settings = 
				new TopPanelModel(1, 256, 256, 6);
		
		OctaveModel[] octaveModels = new OctaveModel[1];
		octaveModels[0] = new OctaveModel(2, 2, 0, 0, 
				-1, 1, OctaveModel.NoiseNormalization.NONE);
		
		ColorAxis colorAxis = new ColorAxis();
		ArrayList<ColorBarPeg> colorBarPegs = new ArrayList<ColorBarPeg>();
		colorBarPegs.add(new ColorBarPeg(0, 64, 32, 0, 255));
		colorBarPegs.add(new ColorBarPeg(255, 128, 96, 0, 255));
		colorAxis.setColorBarPegs(colorBarPegs);

		SinusoidalGradientFunction sinusoidalGradient = 
				new SinusoidalGradientFunction( 0, 0, 160, 0, 8, 0);
		boolean[] selected = new boolean[3];
		selected[2] = true;
		
		GradientGUIModel gradientGUIModel = new GradientGUIModel(
				new LinearGradientFunction(), new RadialGradientFunction(),
				sinusoidalGradient, selected);
		
		float masterWeight = 4;
		float[] weights = new float[1];
		weights[0] = 1;
		MixerModel mixerModel = new MixerModel(weights, 
				masterWeight, MixerModel.MapMethod.RING, 
				sinusoidalGradient,	settings); 
		
		TextureModel sivi = new TextureModel(settings, octaveModels,
				mixerModel, gradientGUIModel, colorAxis);		
		
		TutorialDisplay pageDisplay = new TutorialDisplay(
				sivi, tf.topPanel);		
		
		return pageDisplay;
	}
	
	TutorialDisplay makeCrayons(final TutorialFramework tf)
	{
		TopPanelModel settings = 
				new TopPanelModel(2, 256, 256, 6);

		OctaveModel[] octaveModels = new OctaveModel[2];
		octaveModels[0] = new OctaveModel(120, 120, 0, 0, 
				-1, 1, OctaveModel.NoiseNormalization.ABS);
		octaveModels[1] = new OctaveModel(50, 1.5f, 0, 0, 
				-1, 1, OctaveModel.NoiseNormalization.NONE);

		ColorAxis colorAxis = new ColorAxis();
		ArrayList<ColorBarPeg> colorBarPegs = new ArrayList<ColorBarPeg>();
		colorBarPegs.add(new ColorBarPeg(0, 102, 102, 0, 255));
		colorBarPegs.add(new ColorBarPeg(255, 0, 102, 0, 255));
			colorAxis.setColorBarPegs(colorBarPegs);

		float[] weights = new float[2];
		weights[0] = 0.75f;
		weights[1] = 0.25f;
		MixerModel mixerModel = new MixerModel(
				weights, 2, MixerModel.MapMethod.CLAMP, 
				new NoiseData(settings.finalWidth, 
						settings.finalHeight));	

		TextureModel sivi = new TextureModel(settings, octaveModels,
				mixerModel, new GradientGUIModel(), colorAxis);		

		TutorialDisplay pageDisplay = new TutorialDisplay(
				sivi, tf.topPanel);		
		
		return pageDisplay;
	}
	
	TutorialDisplay makeTerraMap(final TutorialFramework tf)
	{
		TopPanelModel settings = 
				new TopPanelModel(7, 400, 256, 6);
		
		OctaveModel[] octaveModels = new OctaveModel[settings.octaves];
		octaveModels[0] = new OctaveModel(1, 1, 0, 0, 
				-1, 1, OctaveModel.NoiseNormalization.SMOOTH);
		octaveModels[1] = new OctaveModel(2, 2, 0, 0, 
				-1, 1, OctaveModel.NoiseNormalization.SMOOTH);
		octaveModels[2] = new OctaveModel(4, 4, 0, 0, 
				-1, 1, OctaveModel.NoiseNormalization.SMOOTH);
		octaveModels[3] = new OctaveModel(8, 8, 0, 0, 
				-1, 1, OctaveModel.NoiseNormalization.SMOOTH);
		octaveModels[4] = new OctaveModel(16, 16, 0, 0, 
				-1, 1, OctaveModel.NoiseNormalization.SMOOTH);
		octaveModels[5] = new OctaveModel(32, 32, 0, 0, 
				-1, 1, OctaveModel.NoiseNormalization.SMOOTH);
		octaveModels[6] = new OctaveModel(64, 64, 0, 0, 
				-1, 1, OctaveModel.NoiseNormalization.SMOOTH);

		ColorAxis colorAxis = new ColorAxis();
		ArrayList<ColorBarPeg> colorBarPegs = new ArrayList<ColorBarPeg>();
		colorBarPegs.add(new ColorBarPeg(0, 255, 255, 255, 255));
		colorBarPegs.add(new ColorBarPeg(40, 153, 153, 153, 255));
		colorBarPegs.add(new ColorBarPeg(64, 0, 128, 0, 255));
		colorBarPegs.add(new ColorBarPeg(92, 96, 176, 0, 255));
		colorBarPegs.add(new ColorBarPeg(99, 224, 224, 128, 255));
		colorBarPegs.add(new ColorBarPeg(100, 204, 204, 204, 255));
		colorBarPegs.add(new ColorBarPeg(128, 64, 64, 255, 255));
		colorBarPegs.add(new ColorBarPeg(255, 0, 0, 192, 255));
		colorAxis.setColorBarPegs(colorBarPegs);
		
		
		float[] weights = new float[settings.octaves];
		float masterWeight = 0.75f;
		weights[0] = 0.64f * masterWeight;
		weights[1] = 0.32f * masterWeight;
		weights[2] = 0.16f * masterWeight;
		weights[3] = 0.08f * masterWeight;
		weights[4] = 0.04f * masterWeight;
		weights[5] = 0.02f * masterWeight;
		weights[6] = 0.01f * masterWeight;
		
		MixerModel mixerModel = new MixerModel(weights, 
				masterWeight, MixerModel.MapMethod.CLAMP, 
				new NoiseData(settings.finalWidth, 
						settings.finalHeight));	

		TextureModel sivi = new TextureModel(settings, octaveModels,
				mixerModel, new GradientGUIModel(), colorAxis);		

		TutorialDisplay pageDisplay = new TutorialDisplay(
				sivi, tf.topPanel);		
		
		return pageDisplay;
	}
	
	TutorialDisplay makeTerraVeins(final TutorialFramework tf)
	{
		TopPanelModel settings = 
				new TopPanelModel(1, 256, 256, 6);
		
		OctaveModel[] octaveModels = new OctaveModel[settings.octaves];
		octaveModels[0] = new OctaveModel(4, 4, 0, 0, 
				-1, 1, OctaveModel.NoiseNormalization.ABS);
		
		ColorAxis colorAxis = new ColorAxis();
		ArrayList<ColorBarPeg> colorBarPegs = new ArrayList<ColorBarPeg>();
		colorBarPegs.add(new ColorBarPeg(0, 255, 255, 255, 255));
		colorBarPegs.add(new ColorBarPeg(40, 153, 153, 153, 255));
		colorBarPegs.add(new ColorBarPeg(64, 0, 128, 0, 255));
		colorBarPegs.add(new ColorBarPeg(92, 96, 176, 0, 255));
		colorBarPegs.add(new ColorBarPeg(99, 224, 224, 128, 255));
		colorBarPegs.add(new ColorBarPeg(100, 204, 204, 204, 255));
		colorBarPegs.add(new ColorBarPeg(128, 64, 64, 255, 255));
		colorBarPegs.add(new ColorBarPeg(255, 0, 0, 192, 255));
		colorAxis.setColorBarPegs(colorBarPegs);
		
		
		float[] weights = new float[settings.octaves];
		float masterWeight = 1f;
		weights[0] = 1f * masterWeight;
		
		MixerModel mixerModel = new MixerModel(weights, 
				masterWeight, MixerModel.MapMethod.CLAMP, 
				new NoiseData(settings.finalWidth, 
						settings.finalHeight));	

		TextureModel sivi = new TextureModel(settings, octaveModels,
				mixerModel, new GradientGUIModel(), colorAxis);		

		TutorialDisplay pageDisplay = new TutorialDisplay(
				sivi, tf.topPanel);		
		
		return pageDisplay;
	}
	
		
	
	
	TutorialDisplay makePlasmaField(final TutorialFramework tf)
	{
		TopPanelModel settings = new TopPanelModel(4, 255, 255, 6);
		
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
						1, -0.6f);
		boolean[] selected = new boolean[3];
		
		GradientGUIModel gradientGUIModel = new GradientGUIModel(
				new LinearGradientFunction(), radialGradient,
				new SinusoidalGradientFunction(), selected);
		
		float[] weights = new float[settings.octaves];
		weights[0] = 0.56f;
		weights[1] = 0.28f;
		weights[2] = 0.14f;
		weights[3] = 0.07f;

		MixerModel mixerModel = new MixerModel(
				weights, 1, MixerModel.MapMethod.CLAMP, 
				new NoiseData(settings.finalWidth, 
						settings.finalHeight));

		TextureModel sivi = new TextureModel(settings, octaveModels,
				mixerModel, gradientGUIModel, colorAxis);		
		
		TutorialDisplay pageDisplay = new TutorialDisplay(
				sivi, tf.topPanel);		
		
		return pageDisplay;
	}
	
	TutorialDisplay makeSolarFlare(final TutorialFramework tf)
	{
		TopPanelModel settings = new TopPanelModel(4, 350, 350, 6);
		
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
//		colorBarPegs.add(new ColorBarPeg(48, 245, 205, 82, 255));
    	colorBarPegs.add(new ColorBarPeg(127, 204, 0, 0, 255));
		colorBarPegs.add(new ColorBarPeg(255, 0, 0, 0, 255));
		colorAxis.setColorBarPegs(colorBarPegs);
		
		RadialGradientFunction radialGradient = 
				new RadialGradientFunction(
						settings.finalWidth / 2, 
						settings.finalHeight / 2,
						settings.finalWidth / 2,
						1, -0.6f);
		boolean[] selected = new boolean[3];
		selected[1] = true;
		
		GradientGUIModel gradientGUIModel = new GradientGUIModel(
				new LinearGradientFunction(), radialGradient,
				new SinusoidalGradientFunction(), selected);
		
		float[] weights = new float[settings.octaves];
		weights[0] = 0.56f;
		weights[1] = 0.28f;
		weights[2] = 0.14f;
		weights[3] = 0.07f;

		MixerModel mixerModel = new MixerModel(
				weights, 1,	MixerModel.MapMethod.CLAMP, 
				radialGradient,	settings);

		TextureModel sivi = new TextureModel(settings, octaveModels,
				mixerModel, gradientGUIModel, colorAxis);		
		
		TutorialDisplay pageDisplay = new TutorialDisplay(
				sivi, tf.topPanel);		
		
		return pageDisplay;
	}
	
	TutorialDisplay makeFieryFuzzball(final TutorialFramework tf)
	{
		TopPanelModel settings = new TopPanelModel(4, 350, 350, 6);
		
		OctaveModel[] octaveModels = new OctaveModel[settings.octaves];
		octaveModels[0] = new OctaveModel(4, 16, 0, 0, 
				-1, 1, OctaveModel.NoiseNormalization.ABS);
		octaveModels[1] = new OctaveModel(8, 32, 0, 0, 
				-1, 1, OctaveModel.NoiseNormalization.ABS);
		octaveModels[2] = new OctaveModel(16, 64, 0, 0, 
				-1, 1, OctaveModel.NoiseNormalization.ABS);
		octaveModels[3] = new OctaveModel(32, 128, 0, 0, 
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
						1.2f, 0);
		boolean[] selected = new boolean[3];
		selected[1] = true;
		
		GradientGUIModel gradientGUIModel = new GradientGUIModel(
				new LinearGradientFunction(), radialGradient,
				new SinusoidalGradientFunction(), selected);
		
		float masterWeight = 0.25f;
		float[] weights = new float[settings.octaves];
		weights[0] = 0.56f * masterWeight;
		weights[1] = 0.28f * masterWeight;
		weights[2] = 0.14f * masterWeight;
		weights[3] = 0.07f * masterWeight;
		
		MixerModel mixerModel = new MixerModel(weights, 
				masterWeight, MixerModel.MapMethod.CLAMP,
				radialGradient,	settings);

		TextureModel sivi = new TextureModel(settings, octaveModels,
				mixerModel, gradientGUIModel, colorAxis);		
		
		TutorialDisplay pageDisplay = new TutorialDisplay(
				sivi, tf.topPanel);		
		
		return pageDisplay;
	}

	TutorialDisplay makeGoldFireThread(final TutorialFramework tf)
	{
		TopPanelModel settings = new TopPanelModel(4, 300, 300, 6);
		
		OctaveModel[] octaveModels = new OctaveModel[settings.octaves];
		octaveModels[0] = new OctaveModel(8, 2, 0, 0, 
				-1, 1, OctaveModel.NoiseNormalization.ABS);
		octaveModels[1] = new OctaveModel(16, 4, 0, 0, 
				-1, 1, OctaveModel.NoiseNormalization.ABS);
		octaveModels[2] = new OctaveModel(32, 8, 0, 0, 
				-1, 1, OctaveModel.NoiseNormalization.ABS);
		octaveModels[3] = new OctaveModel(64, 16, 0, 0, 
				-1, 1, OctaveModel.NoiseNormalization.ABS);
		
		ColorAxis colorAxis = new ColorAxis();
		ArrayList<ColorBarPeg> colorBarPegs = new ArrayList<ColorBarPeg>();
		colorBarPegs.add(new ColorBarPeg(0, 255, 255, 204, 255));
		colorBarPegs.add(new ColorBarPeg(56, 255, 198, 0, 255));
		colorBarPegs.add(new ColorBarPeg(255, 2, 1, 0, 255));
		colorAxis.setColorBarPegs(colorBarPegs);
		colorAxis.setUseHsbLerp(true);
		
		LinearGradientFunction linearGradient = 
				new LinearGradientFunction(
						settings.finalWidth, 
						settings.finalHeight,
						0, 0, 0, 0.75f);
		
		boolean[] selected = new boolean[3];
		selected[0] = true;

		
		GradientGUIModel gradientGUIModel = new GradientGUIModel(
				linearGradient, new RadialGradientFunction(),
				new SinusoidalGradientFunction(), selected);
		
		float masterWeight = 1;
		float[] weights = new float[settings.octaves];
		weights[0] = 0.32f * masterWeight;
		weights[1] = 0.16f * masterWeight;
		weights[2] = 0.08f * masterWeight;
		weights[3] = 0.04f * masterWeight;
		
		MixerModel mixerModel = new MixerModel(weights, 
				masterWeight, MixerModel.MapMethod.CLAMP,
				linearGradient,	settings);

		TextureModel sivi = new TextureModel(settings, octaveModels,
				mixerModel, gradientGUIModel, colorAxis);		
		
		TutorialDisplay pageDisplay = new TutorialDisplay(
				sivi, tf.topPanel);		
		
		return pageDisplay;
	}
	
	TutorialDisplay makeCandelabra(final TutorialFramework tf)
	{
		TopPanelModel settings = new TopPanelModel(4, 400, 400, 6);
		
		OctaveModel[] octaveModels = new OctaveModel[settings.octaves];
		octaveModels[0] = new OctaveModel(8, 2, 0, 0, 
				-1, 1, OctaveModel.NoiseNormalization.ABS);
		octaveModels[1] = new OctaveModel(16, 4, 0, 0, 
				-1, 1, OctaveModel.NoiseNormalization.ABS);
		octaveModels[2] = new OctaveModel(32, 8, 0, 0, 
				-1, 1, OctaveModel.NoiseNormalization.ABS);
		octaveModels[3] = new OctaveModel(64, 16, 0, 0, 
				-1, 1, OctaveModel.NoiseNormalization.ABS);
		
		ColorAxis colorAxis = new ColorAxis();
		ArrayList<ColorBarPeg> colorBarPegs = new ArrayList<ColorBarPeg>();
		colorBarPegs.add(new ColorBarPeg(0, 255, 255, 204, 255));
		colorBarPegs.add(new ColorBarPeg(56, 255, 198, 0, 255));
		colorBarPegs.add(new ColorBarPeg(255, 2, 1, 0, 255));
		colorAxis.setColorBarPegs(colorBarPegs);
		colorAxis.setUseHsbLerp(true);
		
		LinearGradientFunction linearGradient = 
				new LinearGradientFunction(
						settings.finalWidth, 
						settings.finalHeight,
						0, 0, 1.1f, 0);
		
		RadialGradientFunction radialGradient = 
				new RadialGradientFunction(
						settings.finalWidth / 2, 
						settings.finalHeight / 2,
						236, 0, -0.5f);

		SinusoidalGradientFunction sinusoidalGradient = 
				new SinusoidalGradientFunction( 0, 0, 62, 0, 2, 0.5f);
		
		boolean[] selected = new boolean[3];
		for (int i = 0; i < 3; i++)
		{
			selected[i] = true;
		}
		
		GradientGUIModel gradientGUIModel = new GradientGUIModel(
				linearGradient, radialGradient,
				sinusoidalGradient, selected);
		
		float masterWeight = 1;
		float[] weights = new float[settings.octaves];
		weights[0] = 0.16f * masterWeight;
		weights[1] = 0.16f * masterWeight;
		weights[2] = 0.16f * masterWeight;
		weights[3] = 0.16f * masterWeight;
		
		MixerModel mixerModel = new MixerModel(weights, 
				masterWeight, MixerModel.MapMethod.CLAMP,
				GradientGUI.createGradientFunctionData(
						settings.finalWidth,
						settings.finalHeight,
						gradientGUIModel));

		TextureModel sivi = new TextureModel(settings, octaveModels,
				mixerModel, gradientGUIModel, colorAxis);		
		
		TutorialDisplay pageDisplay = new TutorialDisplay(
				sivi, tf.topPanel);		
		
		return pageDisplay;
	}
	
}
