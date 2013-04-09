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
package com.adonax.simplexNoiseVisualizer;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

import com.adonax.simplexNoiseVisualizer.color.ColorMap;
import com.adonax.simplexNoiseVisualizer.utils.SimplexNoise;

/**
 * Pure functions to generate and combine textures.
 */
public class TextureFunctions {

	
	/**
	 * Generate a noise data array from a single octave channel
	 * source. Noise data may or may not be normalized in this 
	 * step.
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

		for (int i = 0, n = width * height; i < n; i ++)
		{
			//TODO: build 256.0f into the OctaveModel "scale" factors
			float y = (((i/width) % height) * om.yScale / 256.0f)  
					+ om.yTranslate;
			float x = ((i % width) * om.xScale / 256.0f)
					+ om.xTranslate;

			float noiseVal = (float) SimplexNoise.noise(x, y);
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
	 * Generate a noise data array from an array of octave 
	 * channels. Noise data may or may not be normalized in this 
	 * step, and may or may not be used to modulate a gradient
	 * function.
	 *
	 * This is a pure function.
	 *
	 * @param width     image width
	 * @param height    image height
	 * @param om        texture parameters from a single OctaveModel
	 *
	 * @return TextureData
	 */
	public static NoiseData makeNoiseDataArray(int width, int height, 
			OctaveModel[] om, MixerModel mm) 
	{
		float[] noiseArray = new float[width * height];

		for (int j = 0, m = om.length; j < m; j++)
		{
			for (int i = 0, n = width * height; i < n; i ++)
			{
				//TODO: build 256.0f into the OctaveModel "scale" factors
				float y = (((i/width) % height) * om[j].yScale / 256.0f)  
						+ om[j].yTranslate;
				float x = ((i % width) * om[j].xScale / 256.0f)
						+ om[j].xTranslate;
	
				float noiseVal = (float) SimplexNoise.noise(x, y);
				noiseVal = Math.min(
						Math.max(noiseVal, om[j].minClamp), om[j].maxClamp);
	
				if (om[j].normalize == OctaveModel.NoiseNormalization.SMOOTH) {
					noiseVal = (noiseVal + 1) / 2;
				} else if (om[j].normalize == OctaveModel.NoiseNormalization.ABS) {
					noiseVal = Math.abs(noiseVal);
				}
				
				noiseArray[i] += noiseVal * mm.weights[j];
			}
		}
		
		for (int i = 0, n = width * height; i < n; i ++)
		{
			noiseArray[i] += mm.gradientData.noiseArray[i];
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