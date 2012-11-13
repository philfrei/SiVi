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
package com.adonax.tutorial.utilities;

import java.awt.image.BufferedImage;

import com.adonax.texturebuilder.ColorAxis;
import com.adonax.utils.SimplexNoise;

public class MakeTexturedImage 
{

	public static BufferedImage make(int width, int height,
			ColorAxis colorAxis, float[] xScale, float[] yScale,
			float[] xTranslate, float[] yTranslate,
			float[] volume, int mapMode)
	{
		BufferedImage image = new BufferedImage(width, height, 
				BufferedImage.TYPE_INT_ARGB);

		float noiseSum = 0;
		
		int octaves = xScale.length;
		
		float[] noiseVal = new float[octaves];
		float[] xIncr = new float[octaves];
		float[] yIncr = new float[octaves];
		float[] xSum = new float[octaves];
		float[] ySum = new float[octaves];

		int cbIdx = 0;
		
		for (int idx = 0; idx < octaves; idx++)
		{
			ySum[idx] = 0;
			yIncr[idx] = yScale[idx] / 256;
			xIncr[idx] = xScale[idx] / 256;
		}
		
		for (int j = 0; j < height; j++)
		{
			for (int idx = 0; idx < octaves; idx++)
			{
				ySum[idx] += yIncr[idx];
				xSum[idx] = 0;
			}
			for (int i = 0; i < width; i++)
			{
				noiseSum = 0;
				for (int idx = 0; idx < octaves; idx++)
				{
					xSum[idx] += xIncr[idx];
					noiseVal[idx] = (float)SimplexNoise.noise(
							xSum[idx] + xTranslate[idx],
							ySum[idx] + yTranslate[idx]);
					noiseVal[idx] *= volume[idx];
				
					// section with MODE, ignore for now
					noiseSum += noiseVal[idx];
				}
				
				switch (mapMode)
				{
				case 0:
					cbIdx = (int)Math.min(Math.max(
							((noiseSum + 1) / 2) * 256, 0), 255);
					break;
				case 1:
					cbIdx = (int)Math.min(Math.max(
							(Math.abs(noiseSum) * 256), 0), 255);
					break;
				case 2:
					cbIdx = (int)(noiseSum * 256);
					while (cbIdx < 0) cbIdx += 256;
					cbIdx %= 256;
					break;
				case 3: // yAxis
					noiseSum += j/(float)height;
					cbIdx = (int)Math.min(
							Math.max(noiseSum * 256, 0), 255);
				}				
				

				image.setRGB(i, j, colorAxis.data[cbIdx]);
			}
		}
		
		return image;
	}
	
}
