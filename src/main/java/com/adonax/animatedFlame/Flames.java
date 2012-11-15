package com.adonax.animatedFlame;

import java.awt.image.BufferedImage;

import com.adonax.tutorial.utilities.ColorMap;
import com.adonax.utils.SimplexNoise;

public class Flames
{
	private int width, height;
	private BufferedImage image;

	private int[] pixel;
	private float noiseSum;
	private int octaves;
	private int[] colorMap;
	
	private final float[] xScales = {6/128f, 16/128f};
	private final float[] yScales = {3/128f, 8/128f};
	private final float[] octaveAmplitudes  = {8, 3};

	
//	private final float[] xScales = {4/128f, 8/128f, 16/128f};
//	private final float[] yScales = {2/128f, 4/128f, 8/128f};
//	private final float[] octaveAmplitudes  = {8, 4, 2 };
	
//	private final float[] xScales = {2/128f, 4/128f, 8/128f, 16/128f, 32/128f};
//	private final float[] yScales = {1/128f, 2/128f, 4/128f, 8/128f, 16/128f};
//	private final float[] octaveAmplitudes  = {16, 8, 4, 2, 1};

//	private float ani;
//	private float aniAmount = 0.125f / 4;
	private float aniZ;
	private float aniZincr = 0.125f;/// 4;
	
	private float verticalFactor;
	private int nSum;
	
	private int amplitudeSum;
	{
		for (int i = 0; i < octaves; i++) 
			amplitudeSum += octaveAmplitudes[i];
	}
	
	public BufferedImage getImage()
	{
		return image;
	}
	
	
	Flames(int width, int height)
	{
		this.width = width;
		this.height = height;
		
		// ColorBar
		int[][] colorBarPegs = {
				{0, 0, 0, 255, 255},
				{128, 204, 0, 0, 255},
				{255, 255, 255, 102, 255}};
		colorMap = ColorMap.makeMap(colorBarPegs);
		
		image = new BufferedImage(width, height,
	            BufferedImage.TYPE_INT_ARGB);
	    pixel = new int[4]; //[0]=r, [1]=g, [2]=b, [3]=alpha
	    pixel[3] = 255;
	    
	    octaves = xScales.length;

	    for (float amp : octaveAmplitudes)
	    {
	    	amp = amp / amplitudeSum;
	    }
	    
	    verticalFactor = 256f / height; 
	    
	}
	
	public void update()
	{
		aniZ += aniZincr;
	    for (int y = 0; y < height; y++)
	    {
	    	for (int x = 0; x < width; x++)
	        {
	    		noiseSum = 0;
	    		for (int i = 0; i < octaves; i++)
	    		{
	    			noiseSum += SimplexNoise.noise(x * xScales[i], 
	    					y * yScales[i] + aniZ, aniZ)
	    					* ( octaveAmplitudes[i] );
	    		}
	    		
	    		nSum = (int)(noiseSum * 6 + y * verticalFactor);
	    		
                // clamp
                if (nSum > 255) nSum = 255;
	            if (nSum < 0) nSum = 0;
	
//	            pixel[0] = colorMap[nSum][0];
//	            pixel[1] = colorMap[nSum][1];
//	            pixel[2] = colorMap[nSum][2];
//	            
//	            raster.setPixel(x, y, pixel);

                image.setRGB(x, y, colorMap[nSum]);
	        }
	    }
	}
	
}
