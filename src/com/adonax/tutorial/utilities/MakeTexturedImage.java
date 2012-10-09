package com.adonax.tutorial.utilities;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

import com.adonax.texturebuilder.ColorAxis;
import com.adonax.utils.SimplexNoise;

public class MakeTexturedImage 
{

	public static BufferedImage make(int width, int height,
			ColorAxis colorAxis, double[] xScale, double[] yScale,
			double[] xTranslate, double[] yTranslate,
			double[] volume, int mapMode)
	{
		BufferedImage image = new BufferedImage(width, height, 
				BufferedImage.TYPE_INT_ARGB);
		WritableRaster raster = image.getRaster();
		
		int pixel[] = new int[4]; 
		double noiseSum = 0;
		
		int octaves = xScale.length;
		
		double[] noiseVal = new double[octaves];
		double[] xIncr = new double[octaves];
		double[] yIncr = new double[octaves];
		double[] xSum = new double[octaves];
		double[] ySum = new double[octaves];

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
					noiseVal[idx] = SimplexNoise.noise(
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
							((noiseSum + 1) / 2) * 255, 0), 255);
					break;
				case 1:
					cbIdx = (int)Math.min(Math.max(
							(Math.abs(noiseSum) * 255), 0), 255);
					break;
				case 2:
					cbIdx = (int)(noiseSum * 255);
					while (cbIdx < 0) cbIdx += 256;
					cbIdx %= 256;					
				}				
				
				
				pixel[0] = colorAxis.data[cbIdx][0];
				pixel[1] = colorAxis.data[cbIdx][1];
				pixel[2] = colorAxis.data[cbIdx][2];
				pixel[3] = colorAxis.data[cbIdx][3];

				raster.setPixel(i, j, pixel);
			}
		}
		
		return image;
	}
	
}
