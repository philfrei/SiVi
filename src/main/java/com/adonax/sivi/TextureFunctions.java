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
package com.adonax.sivi;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

import com.adonax.sivi.color.ColorMap;
import com.adonax.sivi.utils.FloatArrayFunctions;
import com.adonax.sivi.utils.NoiseEngine;
import com.adonax.sivi.utils.NoiseEngines;
/**
 * Pure functions to generate and combine textures.
 */
public class TextureFunctions {

	// TODO unclear this is best location
	// TODO unclear these are best names
	private static NoiseEngines.Sources noiseEngine = NoiseEngines.Sources.OPEN_SIMPLEX;
	private static NoiseEngine noiseMachine = noiseEngine.getNoiseEngine();
	private static float latticeDFactor = 1/256f; // default
	public static NoiseEngines.Sources getNoiseSource() 
	{
		return noiseEngine;
	}
	public static void setNoiseEngine(NoiseEngines.Sources noiseEngine)
	{
		TextureFunctions.noiseEngine = noiseEngine;
		TextureFunctions.noiseMachine = noiseEngine.getNoiseEngine();
		TextureFunctions.latticeDFactor = noiseEngine.getLatticeFactor();
	}
	
	
	/**
	 * Generate a 2D noise data array from a single octave channel
	 * source. Noise data may or may not be normalized in this 
	 * step. This simpler version is intended for use with the individual
	 * octave channels of the OctaveGUI.
	 *
	 * This is a pure function.
	 *
	 * @param width     image width
	 * @param height    image height
	 * @param om        texture parameters from a single OctaveModel
	 *
	 * @return TextureData
	 */
	public static NoiseData makeNoiseDataArray(int width, 
			int height, OctaveModel om) 
	{
		float[] noiseArray = new float[width * height];
		
		float yFactor = om.yScale * latticeDFactor;
		float xFactor = om.xScale * latticeDFactor;
		
		for (int i = 0, n = width * height; i < n; i ++)
		{
			float y = (((i/width) % height) * yFactor) + om.yTranslate;
			float x = ((i % width) * xFactor) + om.xTranslate;

			float noiseVal = (float) noiseMachine.noise(x, y);
			noiseVal = Math.min(
					Math.max(noiseVal, om.minClamp), om.maxClamp);

			if (om.normalize == OctaveModel.NoiseNormalization.SMOOTH) {
				noiseVal = (noiseVal + 1) / 2;
			} else if (om.normalize == OctaveModel.NoiseNormalization.ABS) {
				noiseVal = Math.abs(noiseVal);
			}
			
			noiseArray[i] = noiseVal;
		}

		return new NoiseData(width, height, noiseArray);
	}

	/**
	 * Generate a 2D noise data array from an array of octave 
	 * channels. The 2D SimplexNoise function is called. Noise data 
	 * may or may not be normalized in this step depending on values 
	 * from the OctaveModel, and may or may not be used to modulate 
	 * a gradient function, depending on settings from the MixerModel. 
	 *
	 * This is a pure function.
	 *
	 * @param width     image width
	 * @param height    image height
	 * @param om        texture parameters from a single OctaveModel
	 * @param mm		mixer & gradient settings/data from MixerModel
	 * 
	 * @return TextureData
	 */
	public static NoiseData makeNoiseDataArray(TopPanelModel tpm,  
			OctaveModel[] om, MixerModel mm) 
	{
		return makeNoiseDataArray(tpm, om, mm, 0, 0, 0, 2);
	}

	/**
	 * Generate a 2D or 3D noise data array from an array of octave channels 
	 * and X, Y, Z translation increments. Intended for use when generating 
	 * a series of images for animation. If Z input is 0, we use the 2D, else we
	 * use the 3D SimplexNoise function. Noise data may or may not be normalized 
	 * in this step depending on values from the OctaveModel, and may or may not
	 * be used to modulate a gradient function, depending on settings from the 
	 * MixerModel. 
	 *
	 * This is a pure function.
	 *
	 * @param width     image width
	 * @param height    image height
	 * @param om        texture parameters from a single OctaveModel
	 * @param mm		mixer & gradient settings/data from MixerModel
	 * @param x			x translation component
	 * @param y			y translation component
	 * @param z			float z-axis value for 3D SimplexNoise function
	 * 
	 * @return TextureData
	 */
	public static NoiseData makeNoiseDataArray(TopPanelModel tpm, OctaveModel[] om, 
			MixerModel mm, float xTranslate, float yTranslate, float zTranslate, int dimensions)
	{
		int width = tpm.finalWidth;
		int height = tpm.finalHeight;
		boolean singleCenterColumn = false;
		boolean singleCenterRow = false;
	
//		if (tpm.isVerticallySymmetric) {
//			width /= 2;
//			if (tpm.finalWidth % 2 ==1) {
//				singleCenterColumn = true;
//				width++;
//			}
//		}
		
//		if (tpm.isHorizontallySymmetric) {
//			height /= 2;
//			if (tpm.finalHeight % 2 ==1) {
//				singleCenterRow = true;
//				height++;
//			}
//		}
		
		float[] noiseArray = new float[width * height];
		float x, y, noiseVal;
		int octLen = om.length;
	
		float xFactor, yFactor;
		
		final float[] mixWeights = new float[octLen];
		for (int i = 0; i < octLen; i ++)
		{
			mixWeights[i] = mm.weights[i] * mm.master;
		}
		
		for (int j = 0; j < octLen; j++)
		{	
			xFactor = om[j].xScale * latticeDFactor;
			yFactor = om[j].yScale * latticeDFactor;
			
			for (int i = 0, n = width * height; i < n; i ++)
			{
				y = (((i/width) % height) * yFactor) + om[j].yTranslate + yTranslate * om[j].yScale;
				x = ((i % width) * xFactor)	+ om[j].xTranslate + xTranslate * om[j].xScale;
	
				if (dimensions == 2) 
				{
					noiseVal = (float) noiseMachine.noise(x, y);
				}
				else if (dimensions == 3)
				{
					noiseVal = (float) noiseMachine.noise(x, y, zTranslate);
				}
				else
				{
					noiseVal = 0;
					System.out.println("unprogrammed number of dimensions" 
							+ " in function 'noiseDataMaker'");
				}
				noiseVal = Math.min(
						Math.max(noiseVal, om[j].minClamp), om[j].maxClamp);
	
				if (om[j].normalize == OctaveModel.NoiseNormalization.SMOOTH) {
					noiseVal = (noiseVal + 1) / 2;
				} else if (om[j].normalize == OctaveModel.NoiseNormalization.ABS) {
					noiseVal = Math.abs(noiseVal);
				}
				
				noiseArray[i] += noiseVal * mixWeights[j];
			}
		}
		
		for (int i = 0, n = width * height; i < n; i ++)
		{
			noiseArray[i] += mm.gradientData.noiseArray[i];
		}
		
		if (tpm.isHorizontallySymmetric) {
			noiseArray = FloatArrayFunctions.makeHorizonallySymmetricArray(
					noiseArray, width, height, singleCenterRow);
			height *= 2;
		}
		if (tpm.isVerticallySymmetric) {	
			noiseArray = FloatArrayFunctions.makeVerticallySymmetricArray(
					noiseArray, width, height, singleCenterColumn);
			width *= 2;
		}
		
		return new NoiseData(width, height, noiseArray);
	}
	
	/**
	 * Create an image from a single noise data array.
	 * 
	 * @param noiseData
	 * @param colorMap
	 * @return
	 */
	public static BufferedImage makeImage(NoiseData noiseData, 
			MixerModel mixerModel, ColorMap colorMap)
	{
		BufferedImage image = new BufferedImage(noiseData.width, 
				noiseData.height, BufferedImage.TYPE_INT_ARGB);
		WritableRaster raster = image.getRaster();
		
		int[] pixel = new int[4]; 
		
		for (int j = 0; j < noiseData.height; j++)
		{
			for (int i = 0; i < noiseData.width; i++)
			{
				float noiseVal = noiseData.noiseArray[i + j * noiseData.width];
				switch (mixerModel.mapping)
				{
				case CLAMP: noiseVal = Math.min(Math.max(0, noiseVal), 1.0f);
					break;
				case RING:
					noiseVal = noiseVal - (float)Math.floor(noiseVal);
				}
				
				int idx = (int)(noiseVal * 255);
				
				pixel[0] = ColorMap.extractRed(colorMap.get(idx));
				pixel[1] = ColorMap.extractGreen(colorMap.get(idx));
				pixel[2] = ColorMap.extractBlue(colorMap.get(idx));
				pixel[3] = 255;
				
				raster.setPixel(i, j, pixel);
			}
		}
		
		return image;
	}
}