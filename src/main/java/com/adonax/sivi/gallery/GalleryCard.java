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
package com.adonax.sivi.gallery;

import java.util.ArrayList;

import javax.swing.JPanel;

import com.adonax.sivi.MixerModel;
import com.adonax.sivi.NoiseData;
import com.adonax.sivi.OctaveModel;
import com.adonax.sivi.TextureModel;
import com.adonax.sivi.TopPanelModel;
import com.adonax.sivi.color.ColorAxis;
import com.adonax.sivi.color.ColorBarPeg;
import com.adonax.sivi.gradients.GradientGUI;
import com.adonax.sivi.gradients.GradientGUIModel;
import com.adonax.sivi.gradients.LinearGradientFunction;
import com.adonax.sivi.gradients.RadialGradientFunction;
import com.adonax.sivi.gradients.SinusoidalGradientFunction;

public class GalleryCard extends JPanel
{
	private static final long serialVersionUID = 1L;
		
	public GalleryCard (final GalleryDialog tf)
	{
		add(makeClassicCloud(tf));
		add(makeCirrusCloud(tf));
		add(makePerlinExampleCloud(tf));
		add(makeTreeRings(tf));
		add(makeWoodPlank(tf));
		add(makeTerraMap(tf));
		add(makeCrayonyTexture(tf));
		add(makePlasmaField(tf));
		add(makeSolarFlare(tf));
		add(makeFieryFuzzball(tf));
		add(makeCandelabra(tf));
	}
	
	TutorialDisplay makeClassicCloud(final GalleryDialog tf)
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
		colorBarPegs.add(new ColorBarPeg(0, 0, 0, 255, 255));
		colorBarPegs.add(new ColorBarPeg(255, 255, 255, 255, 255));
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
	
	TutorialDisplay makeCirrusCloud(final GalleryDialog tf)
	{
		TopPanelModel settings = 
				new TopPanelModel(4, 256, 256, 6);
	
		OctaveModel[] octaveModels = new OctaveModel[settings.octaves];
		octaveModels[0] = new OctaveModel(0.25f, 5, 0, 0, 
				-1, 1, OctaveModel.NoiseNormalization.NONE);
		octaveModels[1] = new OctaveModel(0.5f, 10, 0, 0, 
				-1, 1, OctaveModel.NoiseNormalization.SMOOTH);
		octaveModels[2] = new OctaveModel(1f, 20, 0, 0, 
				-1, 1, OctaveModel.NoiseNormalization.SMOOTH);
		octaveModels[3] = new OctaveModel(2, 40, 0, 0, 
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
	
	TutorialDisplay makePerlinExampleCloud(final GalleryDialog tf)
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
						0, 0, -0.5f, 0.5f);
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
		float masterWeight = 0.68f;
		
		MixerModel mixerModel = new MixerModel(
				weights, masterWeight, MixerModel.MapMethod.CLAMP, 
				linearGradient, settings); 

		TextureModel sivi = new TextureModel(settings, octaveModels,
				mixerModel, gradientGUIModel, colorAxis);		
		
		TutorialDisplay pageDisplay = new TutorialDisplay(
				sivi, tf.topPanel);		
		
		return pageDisplay;
	}
	

	TutorialDisplay makeTreeRings(final GalleryDialog tf)
	{
		TopPanelModel settings = 
				new TopPanelModel(1, 256, 256, 6);
		
		OctaveModel[] octaveModels = new OctaveModel[1];
		octaveModels[0] = new OctaveModel(2, 2, 0, 0, 
				-1, 1, OctaveModel.NoiseNormalization.NONE);
		
		ColorAxis colorAxis = new ColorAxis();
		ArrayList<ColorBarPeg> colorBarPegs = new ArrayList<ColorBarPeg>();
		colorBarPegs.add(new ColorBarPeg(0, 154, 122, 29, 255));
		colorBarPegs.add(new ColorBarPeg(255, 192, 160, 64, 255));
		colorAxis.setColorBarPegs(colorBarPegs);

		RadialGradientFunction radialGradient = 
				new RadialGradientFunction(128, 128, 128, 0, 15);
		boolean[] selected = new boolean[3];
		selected[1] = true;
		
		GradientGUIModel gradientGUIModel = new GradientGUIModel(
				new LinearGradientFunction(), radialGradient, 
				new SinusoidalGradientFunction(), selected);

		float masterWeight = 4;
		float[] weights = new float[1];
		weights[0] = 1;
		MixerModel mixerModel = new MixerModel(weights, 
				masterWeight, MixerModel.MapMethod.RING, 
				radialGradient, settings); 

		
		TextureModel sivi = new TextureModel(settings, octaveModels,
				mixerModel, gradientGUIModel, colorAxis);		
		
		TutorialDisplay pageDisplay = new TutorialDisplay(
				sivi, tf.topPanel);		
		
		return pageDisplay;
	}
	
	TutorialDisplay makeWoodPlank(final GalleryDialog tf)
	{
		TopPanelModel settings = 
				new TopPanelModel(1, 500, 80, 6);
		
		OctaveModel[] octaveModels = new OctaveModel[1];
		octaveModels[0] = new OctaveModel(2, 7, 0, 0, 
				-1, 1, OctaveModel.NoiseNormalization.NONE);
		
		ColorAxis colorAxis = new ColorAxis();
		ArrayList<ColorBarPeg> colorBarPegs = new ArrayList<ColorBarPeg>();
		colorBarPegs.add(new ColorBarPeg(0, 128, 96, 0, 255));
		colorBarPegs.add(new ColorBarPeg(48, 94, 62, 0, 255));
		colorBarPegs.add(new ColorBarPeg(255, 128, 96, 0, 255));
		colorAxis.setColorBarPegs(colorBarPegs);

		LinearGradientFunction linearGradient = 
				new LinearGradientFunction(settings.finalWidth, 
						settings.finalHeight, 0, 0, 5, 0);
		
		SinusoidalGradientFunction sinusoidalGradient = 
				new SinusoidalGradientFunction( 0, 0, 160, 0, 12, 0);
		boolean[] selected = new boolean[3];
		selected[0] = true;
		selected[2] = true;
		
		GradientGUIModel gradientGUIModel = new GradientGUIModel(
				linearGradient, new RadialGradientFunction(), 
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
	
	TutorialDisplay makeCrayonyTexture(final GalleryDialog tf)
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
				weights, 1.6f, MixerModel.MapMethod.CLAMP, 
				new NoiseData(settings.finalWidth, 
						settings.finalHeight));	

		TextureModel sivi = new TextureModel(settings, octaveModels,
				mixerModel, new GradientGUIModel(), colorAxis);		

		TutorialDisplay pageDisplay = new TutorialDisplay(
				sivi, tf.topPanel);		
		
		return pageDisplay;
	}
	
	TutorialDisplay makeTerraMap(final GalleryDialog tf)
	{
		TopPanelModel settings = 
				new TopPanelModel(7, 512, 256, 6);
		
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
		colorBarPegs.add(new ColorBarPeg(0, 192, 192, 192, 255));
		colorBarPegs.add(new ColorBarPeg(32, 204, 204, 128, 255));
		colorBarPegs.add(new ColorBarPeg(64, 0, 128, 0, 255));
		colorBarPegs.add(new ColorBarPeg(92, 96, 176, 0, 255));
		colorBarPegs.add(new ColorBarPeg(94, 128, 192, 0, 255));
		colorBarPegs.add(new ColorBarPeg(99, 224, 224, 128, 255));
		colorBarPegs.add(new ColorBarPeg(100, 144, 160, 204, 255));
		colorBarPegs.add(new ColorBarPeg(128, 48, 48, 255, 224));
		colorBarPegs.add(new ColorBarPeg(255, 0, 0, 192, 128));
		colorAxis.setColorBarPegs(colorBarPegs);
		
		
		float[] weights = new float[settings.octaves];
		float masterWeight = 0.8f;
		weights[0] = 0.64f;
		weights[1] = 0.32f;
		weights[2] = 0.16f;
		weights[3] = 0.08f;
		weights[4] = 0.04f;
		weights[5] = 0.02f;
		weights[6] = 0.01f;
		
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
	
	TutorialDisplay makePlasmaField(final GalleryDialog tf)
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
	
	TutorialDisplay makeSolarFlare(final GalleryDialog tf)
	{
		TopPanelModel settings = new TopPanelModel(4, 350, 350, 6);
		
		OctaveModel[] octaveModels = new OctaveModel[settings.octaves];
		octaveModels[0] = new OctaveModel(3, 3, 0, 0, 
				-1, 1, OctaveModel.NoiseNormalization.ABS);
		octaveModels[1] = new OctaveModel(6, 6, 0, 0, 
				-1, 1, OctaveModel.NoiseNormalization.ABS);
		octaveModels[2] = new OctaveModel(12, 12, 0, 0, 
				-1, 1, OctaveModel.NoiseNormalization.ABS);
		octaveModels[3] = new OctaveModel(24, 24, 0, 0, 
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
						settings.finalWidth / 4,
						0, -0.75f);
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
				weights, 1f,	MixerModel.MapMethod.CLAMP, 
				radialGradient,	settings);

		TextureModel sivi = new TextureModel(settings, octaveModels,
				mixerModel, gradientGUIModel, colorAxis);		
		
		TutorialDisplay pageDisplay = new TutorialDisplay(
				sivi, tf.topPanel);		
		
		return pageDisplay;
	}
	
	TutorialDisplay makeFieryFuzzball(final GalleryDialog tf)
	{
		TopPanelModel settings = new TopPanelModel(3, 350, 350, 6);
		
		OctaveModel[] octaveModels = new OctaveModel[settings.octaves];
		octaveModels[0] = new OctaveModel(6, 24, 0, 0, 
				-1, 1, OctaveModel.NoiseNormalization.ABS);
		octaveModels[1] = new OctaveModel(12, 48, 0, 0, 
				-1, 1, OctaveModel.NoiseNormalization.ABS);
		octaveModels[2] = new OctaveModel(24, 96, 0, 0, 
				-1, 1, OctaveModel.NoiseNormalization.ABS);
		
		ColorAxis colorAxis = new ColorAxis();
		ArrayList<ColorBarPeg> colorBarPegs = new ArrayList<ColorBarPeg>();
		colorBarPegs.add(new ColorBarPeg(0, 255, 255, 102, 255));
		colorBarPegs.add(new ColorBarPeg(112, 217, 64, 26, 255));
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
		
		float masterWeight = 0.24f;
		float[] weights = new float[settings.octaves];
		weights[0] = 0.56f;
		weights[1] = 0.28f;
		weights[2] = 0.14f;
		
		MixerModel mixerModel = new MixerModel(weights, 
				masterWeight, MixerModel.MapMethod.CLAMP,
				radialGradient,	settings);

		TextureModel sivi = new TextureModel(settings, octaveModels,
				mixerModel, gradientGUIModel, colorAxis);		
		
		TutorialDisplay pageDisplay = new TutorialDisplay(
				sivi, tf.topPanel);		
		
		return pageDisplay;
	}
	
	TutorialDisplay makeCandelabra(final GalleryDialog tf)
	{
		TopPanelModel settings = new TopPanelModel(4, 400, 400, 6);
		
		OctaveModel[] octaveModels = new OctaveModel[settings.octaves];
		octaveModels[0] = new OctaveModel(10, 2, 0, 0, 
				-1, 1, OctaveModel.NoiseNormalization.ABS);
		octaveModels[1] = new OctaveModel(20, 4, 0, 0, 
				-1, 1, OctaveModel.NoiseNormalization.ABS);
		octaveModels[2] = new OctaveModel(40, 8, 0, 0, 
				-1, 1, OctaveModel.NoiseNormalization.ABS);
		octaveModels[3] = new OctaveModel(80, 16, 0, 0, 
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
				new SinusoidalGradientFunction( 0, 0, 62, 0, 1.5f, 0.5f);
		
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